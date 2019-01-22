(function($) {
    
    var prevDevNum = 0;//用户保存上一次加载的设备数量
    var prevMonAlarmNum = 0;//用户保存上一次加载的监控对象告警数量
    var initDeviceState = false; //标注查询设备运行状态，true运行中
    var initMonitorAlarmStatus = false;//标注监控对象运行状态，true运行中
    
    //地图对象
    var map;
    
    jQuery(function($){
        // 初始化百度地图
        initMap();
        
    });
    function addAlarmMusic() {
    	//加载背景音乐，并自动播放
        $('#bg_music').empty().append('<embed id="m_bg_music" loop=true volume="80" autostart=true hidden=true src="styles/res/mp3/alarm.mp3" />');
        $('#bg_music_btn').click(function(){
            var state = $('#bg_music_btn').attr('state');
            if(state === '1') {
                $('#bg_music_btn').attr('state','0');
                $('#bg_music_btn').html('打开背景音乐');
                $('#m_bg_music').remove();
            } else if(state === '0') {
                $('#bg_music_btn').attr('state','1');
                $('#m_bg_music').remove();
                $('#bg_music_btn').html('关闭背景音乐');
                $('#bg_music').append('<embed id="m_bg_music" loop=true  volume="60" autostart=true hidden=true src="guoan.mp3" />');
            }
        });
    }
    
    function initMap() {
        
        var mapStatus = createMap();     // 创建地图
        //初始化监控对象告警
        //setInterval(function(){ initMonitorAlarm(); },5000);
        if (mapStatus) {
            
            startAppService();
               
            setTimeout(function() {
                //地图工具栏的行为控制
                toolBarHandler();
            }, 2000);
        }
    }
    
    function createMap() {
        
        try {
            map = new BMap.Map("allmap");
            
            setMapEvent();   // 设置地图事件
            
            addMapControl(); // 向地图添加控件
            return true;
        } catch(e) {
            $("#allmap").html("<div class='divider'></div><div>&nbsp;&nbsp;地图加载异常，请保证网络通畅，否则无法使用地图功能</div>");
            return false;
        }
        
    }
    
    /**
     * 启动定时任务等程序
     */
    function startAppService() {
        //获取地图显示的中心点
        $.post("management/map/getMapCenter",function(item) {
            //alert(item.longitude + ":" + item.latitude + ":" + item.zoom);        
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
            
            setInterval(function(){
                 //设置全局ajax等待为false
                loadDeviceMarker(); // 向地图添加覆盖物
            }, 5000);
            
        } catch (e) {
            alert(e);
        }
    }
    
    /**
     * 地图上加载所有监控设备信息
     */
    function loadDeviceMarker() {
        $.ajaxSettings.global = false;
        $.post("management/site/queryAllNum", function(devNum) {
            
            $.ajaxSettings.global = true;
            if(isNaN(devNum) || devNum === null) console.log(devNum.statusCode + "" + devNum.message);
            if(isNaN(devNum) || devNum === null) return; //假如不是数字或空，则终止运行
            
            if (devNum !== 0) {
                if (prevDevNum !== devNum)  {
                    //保存这一次的设备数量
                    prevDevNum = devNum;
                    //标注运行状态为true
                    initDeviceState = true;
                    //加载所有监控设备
                    $.ajaxSettings.global = false;
                    $.post("management/site/queryAll", function(data) {
                        $.ajaxSettings.global = true;
                        var allOverlay = map.getOverlays();
                          $.each(allOverlay, function(index, item){
                              if(item instanceof BMap.Marker){
                                map.removeOverlay(item);
                            }
                          });
                        
                        $.each(data,function(index, item) {
                            //如果无法查寻后台（即session失效），则item为空，这时候跳转到登录界面
                            var point = new BMap.Point(item.longitude, item.latitude);
                            var marker = new BMap.Marker(point, {
                                icon : new BMap.Icon(
                                        "styles/res/images/icon.png",
                                        new BMap.Size(20, 25), {
                                            imageOffset : new BMap.Size(0, -21)
                                        })
                            });
                            var label = new BMap.Label(item.siteName, {
                                        offset : new BMap.Size(25, 5)
                                    });
                            marker.id = item.id;
                            //marker.categoryID = item.categoryID;
                            marker.alarm = false;
                            //设置显示的标题
                            marker.setLabel(label);
                            marker.name = item.siteName;
                            //marker.enableDragging();    //可拖拽
                            //添加单击和右键调用菜单行为
                            addMarkerHandler(marker);
                            //添加标记
                            map.addOverlay(marker);
                        });
                    }, "json");
                }
                
                // 查询监控对象告警
                setTimeout(function() {
                    initMonitorAlarm();
                }, 3000);
            } else {
                prevDevNum = devNum;
                
                if (initDeviceState) {
                    
                    var allOverlay = map.getOverlays();
                      $.each(allOverlay, function(index, item){
                          if(item instanceof BMap.Marker){
                            map.removeOverlay(item);
                        }
                      });
                    initDeviceState = false;
                }
            }
            
        }, "json");
    }
    
    /**
     * 初始化监控对象告警
     */
    function initMonitorAlarm() {
        
        $.ajaxSettings.global = false;
        
        $.post("management/alarm/monitor/monitor_alarm_num", function(alarmNum) {
            
            $.ajaxSettings.global = true;
            
            if (alarmNum!==0 && alarmNum!==null) {
                
                if (prevMonAlarmNum !== alarmNum)  {
                    //添加告警音乐
                    if (prevMonAlarmNum < alarmNum) addAlarmMusic();
                    //标准运行状态为true
                    initMonitorAlarmStatus = true;
                    
                    $.ajaxSettings.global = false;
                    $.post("management/alarm/monitor/monitor_alarm_sites", function(data) {
                        $.ajaxSettings.global = true;
                        
                        var alarmSize = data.length; //保存后台查询回来的告警设备的数量
                        var deviceSize = 0; //保存在地图上匹配的告警设备数量
                        var allOverlay = map.getOverlays();
                        
                        $.each(allOverlay, function(index, overlayItem) {
                            if(overlayItem instanceof BMap.Marker) {
                                if(overlayItem.alarm === true) {
                                      //overlayItem.setAnimation(null);
                                      //先将所有告警图标重置为正常图标
                                    overlayItem.setIcon(
                                        new BMap.Icon("styles/res/images/icon.png", 
                                            new BMap.Size(20, 25), {
                                                imageOffset : new BMap.Size(0, -21)
                                            }
                                        )
                                    );
                                    overlayItem.alarm = false;
                                }
                                  
                                $.each(data, function(index, deviceID) {
                                    if (overlayItem.id === deviceID) { 
                                        deviceSize++;
                                        overlayItem.alarm = true;
                                         //overlayItem.setAnimation(BMAP_ANIMATION_BOUNCE);
                                         //将告警图标设置为
                                         overlayItem.setIcon(
                                            new BMap.Icon("styles/res/images/alarm.gif", 
                                                new BMap.Size(20, 25), {
                                                    imageOffset : new BMap.Size(0, 0)
                                                }
                                            )
                                        );
                                    }
                                });
                            }
                        });
                        
                        if (alarmSize===deviceSize && deviceSize!==0) {
                            prevMonAlarmNum = alarmNum;
                        }
                    }, "json");
                }
            } else {
                prevMonAlarmNum = alarmNum;
                if (initMonitorAlarmStatus) {
                    var allOverlay = map.getOverlays();
                    $.each(allOverlay, function(index, overlayItem) {
                        if(overlayItem instanceof BMap.Marker){
                            if(overlayItem.alarm === true) {
                                  //overlayItem.setAnimation(null);
                                  //先将所有告警图标重置为正常图标
                                overlayItem.setIcon(
                                    new BMap.Icon("styles/res/images/icon.png", 
                                        new BMap.Size(20, 25), {
                                            imageOffset : new BMap.Size(0, -21)
                                        }
                                    )
                                );
                              }
                        };
                    });
                    initMonitorAlarmStatus = false;
                }
            }
        }, "json");
    }
    
    function toolBarHandler() {
        //锁定解锁切换按钮
        $("#lockToggle").toggle(
            function(e) {
                var allOverlay = map.getOverlays();
                  $.each(allOverlay, function(index, item){
                      if(item instanceof BMap.Marker){
                        item.enableDragging();    //可拖拽
                    }
                  });
                  $(this).addClass("unlock").removeClass("lock").find("span").html("解锁状态").attr("title","设备处于解锁状态，可以移动修改设备经纬度和地址信息");
              },
              function(e) {
                var allOverlay = map.getOverlays();
                  $.each(allOverlay, function(index, item){
                      if(item instanceof BMap.Marker){
                        item.disableDragging();    //可拖拽
                    }
                  });
                  $(this).addClass("lock").removeClass("unlock").find("span").html("锁定状态").attr("title","设备处于锁定状态，不可以移动设备");
        });
        
        //刷新按钮
        $("#refreshMap").on("click", function(e){
            
            loadDeviceMarker(); // 向地图添加覆盖物
            
            setTimeout(function(){
               initMonitorAlarm();
            }, 1000);
        });
        
        //搜索地址
        var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
            {"input" : "suggestId"
            ,"location" : map
        });
        ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
        var str = "";
            var _value = e.fromitem.value;
            var value = "";
            if (e.fromitem.index > -1) {
                value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
            }    
            str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
            
            value = "";
            if (e.toitem.index > -1) {
                _value = e.toitem.value;
                value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
            }    
            str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
            G("searchResultPanel").innerHTML = str;
        });
    
        var searchVal;
        ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
        var _value = e.item.value;
            searchVal = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
            G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + searchVal;
            
            setPlace();
        });
    
        function setPlace(){
            map.clearOverlays();    //清除地图上所有覆盖物
            function myFun(){
                var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
                map.centerAndZoom(pp, 18);
                map.addOverlay(new BMap.Marker(pp));    //添加标注
            }
            var local = new BMap.LocalSearch(map, { //智能搜索
              onSearchComplete: myFun
            });
            local.search(searchVal);
        }
        
        function G(id) {
            return document.getElementById(id);
        }
        
    }
    /**
     * 覆盖物添加事件 包括单击事件和右键菜单
     * @param {} target
     */
    function addMapHandler(target) {
        //地图右键菜
        target.addEventListener("rightclick", function(e) {
            
            var thisObj = e.target;
            
            // 自定义右键菜单内容
            var menu = new BMap.ContextMenu();
            var txtMenuItem = [{
                        text : '添加站点',
                        callback : function() {
                            //打开添加监控设备窗口
                            openAddSiteDialog(e);
                        }
                    }, {
                        text : '设置中心点',
                        callback : function() {
                            //打开设置中心点
                            openSetCenterDialog(e);
                        }
                    }, {
                        text : '位置信息',
                        callback : function() {
                            //map.setZoom(18)
                            var opts = {
                                width : 200,
                                title : "坐标信息",
                                enableMessage : false
                            };
                            var pt = e.point;
                            var address = "无法识别出地址信息";
                            
                            var content = "";
                            
                            if(onlineMap) {
                                var geo = new BMap.Geocoder();
                                geo.getLocation(pt, function(evt) {
                                     var addComp = evt.addressComponents;
                                     //获取地址信息
                                     address = addComp.province +  addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                                     content = "<p><b>地址:</b>" + address + "</p>"
                                        + "<p><b>经度:</b>" + e.point.lng + "</p>"
                                        + "<p><b>纬度:</b>" + e.point.lat + "</p>"
                                        + "<p><b>缩放等级:</b>" + map.getZoom() + "</p>";
                                     alertMsg.info(content);
                                     
                                });
                            } else {
                                content = "<p><b>经度:</b>" + e.point.lng + "</p>"
                                    + "<p><b>纬度:</b>" + e.point.lat + "</p>"
                                    + "<p><b>缩放等级:</b>" + map.getZoom() + "</p>";
                                
                                alertMsg.info(content);
                            }
                            
                            var geo = new BMap.Geocoder();
                            geo.getLocation(pt, function(evt) {
                                 var addComp = evt.addressComponents;
                                 //获取地址信息
                                 address = addComp.province +  addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                                 content = "<p><b>地址:</b>" + address + "</p>"
                                    + "<p><b>经度:</b>" + e.point.lng + "</p>"
                                    + "<p><b>纬度:</b>" + e.point.lat + "</p>";
                                
                                alertMsg.info(content);
                                //var infoWindow = new BMap.InfoWindow(content, opts);
                                //thisObj.openInfoWindow(infoWindow);
                            });
                            
                        }
                    }, {
                        text : '放大',
                        callback : function() {
                            map.zoomIn();
                        }
                    }, {
                        text : '缩小',
                        callback : function() {
                            map.zoomOut();
                        }
                    }];
            for (var i = 0; i < txtMenuItem.length; i++) {
                menu.addItem(new BMap.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback, 100));
                if (i === 0) {
                    menu.addSeparator();
                } else if (i === 1) {
                	menu.addSeparator();
                }
            }
            
            this.addContextMenu(menu);
        });
        
        target.addEventListener("click", function(e) { 
            $("#messageInfo span").html("经度:" + e.point.lng + "&nbsp;纬度:" + e.point.lat);
        });
    }
    
    /**
     * 覆盖物添加事件 包括单击事件和右键菜单
     * @param {} marker
     */
    function addMarkerHandler(marker) {
        //单击事件
        marker.addEventListener("click", function(e) {
            
            var target = e.target;
            if (target.alarm === true) { //假如有告警，则显示告警列表
                $.pdialog.open("management/alarm/monitor/queryAlarmBySiteID/" + target.id, "monitorAlarmDialog", "设备与告警信息",{
                    width: 800,
                    height: 600,
                    max: false,
                    mask: true,
                    mixable: true,
                    minable: true,
                    resizable: true,
                    drawable: true,
                    fresh: true,
                    close: "function", 
                    param: "{msg:'message'}"
                }, "json");
            } else {
                $.post("management/site/query/" + target.id, function(item){
                    var opts = {
                        width : 200,
                        title : "站点信息",
                        enableMessage : false
                    };
                    
                    var content = "<p><b>站点名称</b>：" + item.siteName + "</p>";
                    
                    content += "<p><b>地址:</b>" + item.address + "</p>"
                        + "<p><b>经度:</b>" + item.longitude + "</p>"
                        + "<p><b>纬度:</b>" + item.latitude + "</p>";
                    
                    var infoWindow = new BMap.InfoWindow(content, opts);
                    target.openInfoWindow(infoWindow);
                }, "json");
            }
            
        });
        //通过拖动可以改变地址和经纬度拖动后事件事件
        marker.addEventListener("dragend", function(event) {
            
            var target = event.target;
            var pt = event.point;
            
            if (onlineMap) {
                var geo = new BMap.Geocoder();
                 //获取地址的数据地址
                 var address = "无法识别出地址信息";
                 geo.getLocation(pt, function(evt) {
                     var addComp = evt.addressComponents;
                     //获取地址信息
                     address = addComp.province +  addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                     
                     var params = {
                         id: target.id,
                         longitude: pt.lng,
                         latitude: pt.lat,
                         address:address
                     };
                     
                     $.post("management/site/update", params, DWZ.ajaxDone, "json");
                });
            } else {
                var params = {
                         id: target.id,
                         longitude: pt.lng,
                         latitude: pt.lat
                     };
                $.post("management/site/update", params, DWZ.ajaxDone, "json");
            }
            
        });
        //哟间菜单事件
        marker.addEventListener("rightclick", function(e) {
            // 自定义右键菜单内容
            var menu = new BMap.ContextMenu();
            var txtMenuItem = [{
                        text : '编辑站点',
                        callback : function() {
                            openEditDialog(e);
                        }
                    }, {
                        text : '删除站点',
                        callback : function() {
                            deleteDeviceMarker(e);
                        }
                    }, {
                        text : '添加设备',
                        callback : function() {
                            openAddDeviceDialog(e);
                        }
                    }, {
                        text : '参数查询设置',
                        callback : function() {
                        	openParameterDialog(e);
                        }
                    }, {
                        text : '监控设备告警',
                        callback : function() {
                        	openDeviceAlarmDialog(e);
                        }
                    }, {
                        text : '监控对象告警',
                        callback : function() {
                        	openMonitorAlarmDialog(e);
                        }
                    }, {
                        text : '区域监控告警',
                        callback : function() {
                        	openAreaAlarmDialog(e);
                        }
                    }];
            
            if (marker.categoryID===1) {
            	txtMenuItem.push({
                    text : 'IMSI分析',
                    callback : function() {
                    	openImsiDialog(e);
                    }
                }, {
                    text : 'IMEI分析',
                    callback : function() {
                    	openImeiDialog(e);
                    }
                });
            }
            
            
            for (var i = 0; i < txtMenuItem.length; i++) {
                menu.addItem(new BMap.MenuItem(
                        txtMenuItem[i].text,
                        txtMenuItem[i].callback, 100));
                if (i === 1) {
                    menu.addSeparator();
                } else if (i === 2) {
                    menu.addSeparator();
                } else if (i === 5) {
                    menu.addSeparator();
                }
            }
            this.addContextMenu(menu);
        });
    }
    
    /**
     * 打开监控设备告警窗口
     * @param {} event
     */
    function openDeviceAlarmDialog(event) {
    	var target = event.target;
        var id = target.id;
        alert(id + ":" + target.name)
    	navTab.openTab("deviceAlarmLiNav_" + id, "management/alarm/device?siteID=" + id, { title: "设备参数告警[" + target.name + "]", fresh: true});
    }
    
    /**
     * 打开监控设备经过的监控对象告警窗口
     * @param {} event
     */
    function openMonitorAlarmDialog(event) {
    	var target = event.target;
        var id = target.id;
    	navTab.openTab("monitorAlarmLiNav_" + id, "management/alarm/monitor?siteID=" + id, { title: "监控对象告警[" + target.name + "]", fresh: true});
    }

    /**
     * 打开经过监控设备的区域监控对警窗口
     * @param {} event
     */
    function openAreaAlarmDialog(event) {
    	var target = event.target;
        var id = target.id;
    	navTab.openTab("areaAlarmLiNav_" + id, "management/alarm/area?siteID=" + id, { title: "区域监控告警[" + target.name + "]", fresh: true});
    }
    
    /**
     * 打开IMSI分析
     * @param {} event
     */
    function openImsiDialog(event) {
    	var target = event.target;
        var id = target.id;
    	navTab.openTab("imsiAnalysisLiNav", "management/analysis/imsiAnalysis?status=" + id, { title: "IMSI分析", fresh: true});
    }

    /**
     * 打开IMEI分析
     * @param {} event
     */
    function openImeiDialog(event) {
    	var target = event.target;
        var id = target.id;
    	navTab.openTab("imeiAnalysisLiNav", "management/analysis/imeiAnalysis?status=" + id, { title: "IMEI分析", fresh: true});
    }
    /**
     * 打开设置中心点窗口
     * @param {} event
     */
    function openSetCenterDialog(event) {
        
         $.pdialog.open("management/map//openSetMapCenter?longitude=" + event.point.lng + "&latitude="+event.point.lat + "&zoom=" + map.getZoom(), "setMapCenterNav", "设置中心点",{
            width:800,
            height:300,
            max:false,
            mask:true,
            mixable:true,
            minable:true,
            resizable:true,
            drawable:true,
            fresh:true,
            close:"function", 
            param:"{msg:'message'}"
        });
    }
    /**
     * 打开添加站点窗口
     * @param {} event
     */
    function openAddSiteDialog(event) {
        
        var pt = event.point;
        
        var address = "无法识别出地址信息";
        var data = {
            siteID: event.target.id,
            longitude: pt.lng,
            latitude: pt.lat,
            address: encodeURI(address)
        };
        if (onlineMap) {
            //获取地址的数据地址
            var geo = new BMap.Geocoder();
            geo.getLocation(pt, function(evt){
                var addComp = evt.addressComponents;
                 //获取地址信息
                 address = addComp.province +  addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                 data.address = encodeURI(address);
                 
                 addDialog("management/site/add", data, "siteAddNav", "添加站点");
             });
        } else {
            addDialog("management/site/add", data, "siteAddNav", "添加站点");
        }
    }
    
    var addDialog = function(url, data, nav, titile) {
        $.pdialog.open(url, nav, titile, {
            width:730,
            height:400,
            max:false,
            mask:true,
            mixable:true,
            minable:true,
            resizable:true,
            drawable:true,
            fresh:true,
            close:"function", 
            param:"{msg:'message'}",
            data: data
        });
    };
    /**
     * 打开添加设备窗口
     * @param {} event
     */
    function openAddDeviceDialog(event) {
        var pt = event.point;
        
        var address = "无法识别出地址信息";
        var data = {
            siteID: event.target.id
        };
        $.pdialog.open("management/device/add", "deviceAddNav", "添加设备", {
            width:900,
            height:600,
            max:false,
            mask:true,
            mixable:true,
            minable:true,
            resizable:true,
            drawable:true,
            fresh:true,
            close:"function", 
            param:"{msg:'message'}",
            data: data
        });
    }
    
    
    
    /**
     * 打开编辑设备窗口
     * @param {} event
     */
    function openParameterDialog(event) {
        var target = event.target;
        var id = target.id;
        /*
        $.pdialog.open("management/parameterValue?status=" + id, "queryParamValueNav_" + id, "监控设备[" + target.name +  "]参数" ,{
            width:900,
            height:520,
            max:false,
            mask:true,
            mixable:true,
            minable:true,
            resizable:true,
            drawable:true,
            fresh:true,
            close:"function", 
            param:"{msg:'message'}"
        });
        */
        navTab.openTab("queryParamValueNav_" + id, "management/parameterValue?status=" + id, { title: "查询设置参数[" + target.name + "]", fresh: true});
    }
    /**
     * 打开编辑设备窗口
     * @param {} event
     */
    function openEditDialog(event) {
        var target = event.target;
        var id = target.id;
        
        $.pdialog.open("management/site/edit/" + id, "siteEditNav_" + id, "编辑站点[" + target.name + "]",{
            width:800,
            height:600,
            max:false,
            mask:true,
            mixable:true,
            minable:true,
            resizable:true,
            drawable:true,
            fresh:true,
            close:"function", 
            param:"{msg:'message'}"
        });
        
    }
    
    /**
     * 删除监控设备节点标记
     * @param {} event
     */
    function deleteDeviceMarker(event) {
        alertMsg.confirm("你确定要删除该监控设备吗？", {
            okCall: function(){
                $.post("management/site/delete/" + event.target.id, function(json) {
                    
                    DWZ.ajaxDone(json); //提示操作成功
                    //从地图上删除节点
                    map.removeOverlay(event.target);
                }, "json");
                
            }
        });
    }
    
    // 向地图添加控件
    function addMapControl() {
        
        if(onlineMap) { //在线地图提供城市切换插件
            var size = new BMap.Size(5, 5);
            map.addControl(new BMap.CityListControl({
                anchor: BMAP_ANCHOR_TOP_RIGHT,
                offset: size /*,
                 //切换城市之间事件
                 onChangeBefore: function(){
                    alert('before');
                 },
                 //切换城市之后事件
                 onChangeAfter:function(){
                   alert('after');
                 }*/
            }));
        }
        
        /*
        var scaleControl = new BMap.ScaleControl({anchor:BMAP_ANCHOR_TOP_RIGHT});
        scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
        map.addControl(scaleControl);
        var navControl = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_RIGHT,type:BMAP_NAVIGATION_CONTROL_LARGE});
        map.addControl(navControl);
        var overviewControl = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:true});
        map.addControl(overviewControl);
        */
    }
    
    function setMapEvent() {
        map.enableScrollWheelZoom();
        map.enableKeyboard();
        map.enableDragging();
        map.enableDoubleClickZoom();
        
        //添加右键菜单
        addMapHandler(map);
    }
    //获取marker的属性
    function overlay_style(e){
        var p = e.target;
        if(p instanceof BMap.Marker){
            alert("该覆盖物是点，点的坐标是：" + p.getPosition().lng + "," + p.getPosition().lat);   
        }else if(p instanceof BMap.Circle){
            alert("该覆盖物是圆，圆的半径是：" + p.getRadius() + "，圆的中心点坐标是：" + p.getCenter().lng + "," + p.getCenter().lat);   
        }else if(p instanceof BMap.Polyline){
            alert("该覆盖物是折线，所画点的个数是：" + p.getPath().length);   
        } else if(p instanceof BMap.Map) {
            alert("该覆盖物是点，点的坐标是：" + p.getPosition().lng + "," + p.getPosition().lat);  
        } else{
            alert("无法获知该覆盖物类型");
        }
    }
})(jQuery);