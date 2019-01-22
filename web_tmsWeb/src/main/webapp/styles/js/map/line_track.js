/**
 * 轨迹查询
 * 
 */
(function ($) {

    var prevDevNum = 0;//用户保存上一次加载的设备数量
    var initDeviceState = false; //标注查询设备运行状态，true运行中
    var prevRecordNum = 0;//用户保存上一次加载的设备数量
    var initRecordState = false; //标注查询设备运行状态，true运行中
    //地图对象
    var map;

    $(document).on("change", "#queryTrackPageCtx :checkbox[name='ids']", function (e) {

        var cbxPath = new Array();
        var cbxIds = new Array();

        $this = $(this);

        var checked = $this.prop("checked");
        var val = $this.val();

        var allOverlay = map.getOverlays();

        $.each(allOverlay, function (index, item) {

            if (item instanceof BMap.Polyline && item.type === "cbxLine") {
                map.removeOverlay(item);
            }

            if (item instanceof BMap.Marker) {
                if (item.id === val) {
                    item.setIcon(
                            new BMap.Icon("styles/res/images/icon.png",
                                    new BMap.Size(20, 25),
                                    {
                                        imageOffset: checked ? new BMap.Size(-46, -21) : new BMap.Size(0, -21)
                                    }
                            )
                            );
                }

                $("#queryTrackPageCtx :checkbox:checked[name='ids']").each(function (i) {

                    var value = $(this).val();
                    if (item.id === value) {
                        var point = item.getPosition();
                        point.id = value;

                        if ($.inArray(value, cbxIds) === -1) {
                            cbxPath.push(point);
                            cbxIds.push(value);
                        }
                    }
                });
            }
        });

        //升序排列，也就是按照时间先后顺序排列
        cbxIds.sort(function (a, b) {
            return a - b;
        });

        var resultCbxPath = new Array();
        $.each(cbxIds, function (index, value) {
            $.each(cbxPath, function (idx, val) {
                if (val.id === value) {
                    resultCbxPath.push(val);
                }
            });
        });

        var plOpt = {
            strokeColor: "#F00",
            strokeWeight: "4",
            strokeOpacity: "0.6"
        };
        //添加轨迹
        if (resultCbxPath.length >= 2) {
            var polyline = new BMap.Polyline(resultCbxPath, plOpt);
            polyline.type = 'cbxLine';
            map.addOverlay(polyline);
        }

    });

    jQuery(function ($) {
        // 初始化百度地图
        initMap();

    });

    function initMap() {

        var mapStatus = createMap();     // 创建地图

        if (mapStatus) {
            startAppService();
        }
    }

    function createMap() {

        try {
            map = new BMap.Map("map_canvas");

            setMapEvent();   // 设置地图事件

            addMapControl(); // 向地图添加控件

            return true;
        } catch (e) {
            $("#map_canvas").html("<div class='divider'></div><div>&nbsp;&nbsp;地图加载异常，请保证网络通畅，否则无法使用地图功能</div>");
            return false;
        }
    }

    /**
     * 启动定时任务等程序
     */
    function startAppService() {
        //获取地图显示的中心点
        $.post("management/map/getMapCenter", function (item) {

            map.centerAndZoom(new BMap.Point(item.longitude, item.latitude), item.zoom);

            //指定地图显示的范围，不允许移动到范围外
            //var bounds = new BMap.Bounds(new BMap.Point(item.minLon, item.minLat), new BMap.Point(item.maxLon, item.maxLat));
            try {
                //BMapLib.AreaRestriction.setBounds(map, bounds);
            } catch (e) {
                alert(e);
            }
        }, "json");

        try {
            loadDeviceMarker();

            loadTrackPolyline();

            /*
             setInterval(function(){
             //设置全局ajax等待为false
             //loadDeviceMarker(); // 向地图添加覆盖物
             
             //查询轨迹
             setTimeout(function() {
             //loadTrackPolyline();
             }, 500);
             }, 5000);
             */
        } catch (e) {
            alert(e);
        }
    }

    /**
     * 地图上加载所有监控设备信息
     */
    function loadDeviceMarker() {
        $.ajaxSettings.global = false;
        $.post("management/site/queryAllNum", function (devNum) {

            $.ajaxSettings.global = true;

            if (devNum === 0 || isNaN(devNum) || devNum === null)
                return; //假如不是数字或空，则终止运行

            if (devNum !== 0) {
                if (prevDevNum !== devNum) {
                    //保存这一次的设备数量
                    prevDevNum = devNum;
                    //标注运行状态为true
                    initDeviceState = true;
                    //加载所有监控设备
                    $.ajaxSettings.global = false;
                    $.post("management/site/queryAll", function (data) {
                        $.ajaxSettings.global = true;
                        var allOverlay = map.getOverlays();
                        $.each(allOverlay, function (index, item) {
                            if (item instanceof BMap.Marker) {
                                map.removeOverlay(item);
                            }
                        });

                        $.each(data, function (index, item) {
                            //如果无法查寻后台（即session失效），则item为空，这时候跳转到登录界面
                            var point = new BMap.Point(item.longitude, item.latitude);
                            var marker = new BMap.Marker(point, {
                                icon: new BMap.Icon(
                                        "styles/res/images/icon.png",
                                        new BMap.Size(20, 25), {
                                    imageOffset: new BMap.Size(0, -21)
                                })
                            });
                            var label = new BMap.Label(item.siteName, {
                                offset: new BMap.Size(25, 5)
                            });

                            marker.id = item.id;
                            marker.alarm = false;
                            //设置显示的标题
                            marker.setLabel(label);
                            //marker.enableDragging();    //可拖拽
                            //添加单击和右键调用菜单行为
                            addMarkerHandler(marker);
                            //添加标记
                            map.addOverlay(marker);
                        });
                    }, "json");
                }

            } else {
                prevDevNum = devNum;

                if (initDeviceState) {

                    var allOverlay = map.getOverlays();
                    $.each(allOverlay, function (index, item) {
                        if (item instanceof BMap.Marker) {
                            map.removeOverlay(item);
                        }
                    });
                    initDeviceState = false;
                }
            }

        }, "json");
    }

    /**
     * 加载轨迹查询
     */
    function loadTrackPolyline() {
        var content = $("[name='content']", navTab.getCurrentPanel()).val();

        if (content !== "") {
            var obj = {
                type: $("[name='type']", navTab.getCurrentPanel()).val(),
                content: content,
                startDate: $("[name='startDate']", navTab.getCurrentPanel()).val(),
                endDate: $("[name='endDate']", navTab.getCurrentPanel()).val()
            };
            $.ajaxSettings.global = false;
            $.post("management/analysis/lineTrack/queryNumByCondition", obj, function (recordNum) {
                $.ajaxSettings.global = true;

                if (recordNum === 0 || isNaN(recordNum) || recordNum === null)
                    return; //假如不是数字或空，则终止运行
                if (recordNum !== 0) {
                    if (prevRecordNum !== recordNum) {
                        //保存这一次的设备数量
                        prevRecordNum = recordNum;
                        //标注运行状态为true
                        initRecordState = true;
                        //加载所有监控设备
                        $.ajaxSettings.global = false;
                        $.post("management/analysis/lineTrack/queryByCondition", obj, function (data) {
                            $.ajaxSettings.global = true;
                            var allOverlay = map.getOverlays();

                            $.each(allOverlay, function (index, item) {
                                if (item instanceof BMap.Marker) {
                                    map.removeOverlay(item);
                                }
                                if (item instanceof BMap.Polyline) {
                                    map.removeOverlay(item);
                                }

                            });

                            var plOpt = {
                                strokeColor: "#0F0",
                                strokeWeight: "4",
                                strokeOpacity: "0.6"
                            };
                            //存放路径信息
                            var plPath = new Array();

                            var devLabel = {}; //存放监控号码经过设备的时间列表，用于判断是否多次经过该设备

                            $.each(data, function (index, item) {
                                //如果无法查寻后台（即session失效），则item为空，这时候跳转到登录界面
                                var point = new BMap.Point(item.longitude, item.latitude);
                                //将位置信息添加到线路中
                                plPath.push(point);

                                var title = "";

                                if (typeof (devLabel[item.siteID]) === "undefined") {
                                    devLabel[item.siteID] = item.recordTime;
                                    title = item.recordTime;
                                } else {
                                    title = devLabel[item.siteID] + "<br/>" + item.recordTime;
                                }

                                var marker = new BMap.Marker(point, {
                                    icon: new BMap.Icon(
                                            "styles/res/images/icon.png",
                                            new BMap.Size(20, 25), {
                                        imageOffset: new BMap.Size(0, -21)
                                    })
                                });
                                var label = new BMap.Label(title, {
                                    offset: new BMap.Size(50, 25)
                                });
                                marker.id = item.id;
                                marker.siteID = item.siteID;
                                //设置显示的标题
                                marker.setLabel(label);
                                //marker.enableDragging();    //可拖拽
                                //添加单击和右键调用菜单行为
                                addMarkerHandler(marker);
                                //添加标记
                                map.addOverlay(marker);
                            });
                            //添加轨迹
                            var polyline = new BMap.Polyline(plPath, plOpt);
                            map.addOverlay(polyline);
                        }, "json");
                    }

                } else {
                    prevRecordNum = recordNum;

                    if (initRecordState) {

                        var allOverlay = map.getOverlays();
                        $.each(allOverlay, function (index, item) {
                            if (item instanceof BMap.Marker) {
                                map.removeOverlay(item);
                            }
                        });
                        initRecordState = false;
                    }
                }
            }, "json");
        }
    }

    /**
     * 覆盖物添加事件 包括单击事件和右键菜单
     * @param {} target
     */
    function addMapHandler(target) {

        //地图右键菜
        target.addEventListener("rightclick", function (e) {
            var thisObj = this;
            // 自定义右键菜单内容
            var menu = new BMap.ContextMenu();
            var txtMenuItem = [{
                    text: '放大',
                    callback: function () {
                        map.zoomIn();
                    }
                }, {
                    text: '缩小',
                    callback: function () {
                        map.zoomOut();
                    }
                }, {
                    text: '位置信息',
                    callback: function () {
                        //map.setZoom(18)
                        var opts = {
                            width: 200,
                            title: "坐标信息",
                            enableMessage: false
                        };
                        var pt = e.point;
                        var address = "无法识别出地址信息";
                        var geo = new BMap.Geocoder();
                        geo.getLocation(pt, function (evt) {
                            var addComp = evt.addressComponents;
                            //获取地址信息
                            address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                            content = "<p><b>地址:</b>" + address + "</p>"
                                    + "<p><b>经度:</b>" + e.point.lng + "</p>"
                                    + "<p><b>纬度:</b>" + e.point.lat + "</p>";

                            alertMsg.info(content);
                            //var infoWindow = new BMap.InfoWindow(content, opts);
                            //thisObj.openInfoWindow(infoWindow);
                        });
                    }
                }];

            for (var i = 0; i < txtMenuItem.length; i++) {
                menu.addItem(new BMap.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback, 100));
                if (i === 1) {
                    menu.addSeparator();
                }
            }
            this.addContextMenu(menu);
        });

    }

    /**
     * 覆盖物添加事件 包括单击事件和右键菜单
     * @param {} marker
     */
    function addMarkerHandler(marker) {

        //单击事件
        marker.addEventListener("click", function (e) {
            var target = e.target;
            if (target.alarm === true) { //假如有告警，则显示告警列表
                $.pdialog.open("management/alarm/monitor/queryAlarmBySiteID/" + target.siteID, "monitorAlarmDialog", "设备与告警信息", {
                    width: 700,
                    height: 500,
                    max: false,
                    mask: true,
                    mixable: true,
                    minable: true,
                    resizable: true,
                    drawable: true,
                    fresh: true,
                    close: "function",
                    param: "{msg:'message'}"
                });
            } else {
                $.post("management/site/query/" + target.siteID, function (item) {
                    if (item !== null) {
                        var opts = {
                            width: 200,
                            title: "站点信息",
                            enableMessage: false
                        };
                        var content = "<p><b>站点名称</b>：" + item.siteName + "</p>";
                        content += "<p><b>地址:</b>" + item.address + "</p>"
                                + "<p><b>经度:</b>" + item.longitude + "</p>"
                                + "<p><b>纬度:</b>" + item.latitude + "</p>";
                        
                        var infoWindow = new BMap.InfoWindow(content, opts);
                        target.openInfoWindow(infoWindow);
                    }
                }, "json");
            }
        });
    }

    // 向地图添加控件
    function addMapControl() {
        var scaleControl = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_RIGHT});
        scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
        map.addControl(scaleControl);
        var navControl = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_LARGE});
        map.addControl(navControl);
        var overviewControl = new BMap.OverviewMapControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT, isOpen: true});
        //map.addControl(overviewControl);
    }

    function setMapEvent() {
        map.enableScrollWheelZoom();
        map.enableKeyboard();
        map.enableDragging();
        map.enableDoubleClickZoom();
        //添加右键菜单
        addMapHandler(map);
    }

})(jQuery);

