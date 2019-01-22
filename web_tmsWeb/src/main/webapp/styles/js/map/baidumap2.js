(function($) {
    
    var prevDevNum = 0;//用户保存上一次加载的设备数量
    var prevMonAlarmNum = 0;//用户保存上一次加载的监控对象告警数量
    var initDeviceState = false; //标注查询设备运行状态，true运行中
    var initMonitorAlarmStatus = false;//标注监控对象运行状态，true运行中
    
    jQuery(function($){
        
        //地图对象
        var map;
        
        // 初始化百度地图
        initMap();
        
    });
    
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
            //初始化监控对象
            //initMonitor();
        }
    }
    
    function createMap() {
        
        try {
            map = new BMap.Map("device_map");
            
            setMapEvent();   // 设置地图事件
            
            addMapControl(); // 向地图添加控件
            return true;
        } catch(e) {
            $("#device_map").html("<div class='divider'></div><div>&nbsp;&nbsp;地图加载异常，请保证网络通畅，否则无法使用地图功能</div>");
            return false;
        }
        
    }
    
    /**
     * 启动定时任务等程序
     */
    function startAppService() {
        
        //指定中心点
        $.post("management/device/queryAllNum", function(devNum) {
            
            if (devNum==0 || devNum==null || isNaN(devNum)) {
                map.centerAndZoom("北京", 14);
                
            } else {
                //获取地图显示的中心点
                $.post("management/map/getMapCenter",function(item) {
                    //alert(item.longitude + ":" + item.latitude + ":" + item.zoom);        
                    map.centerAndZoom(new BMap.Point(item.longitude, item.latitude), item.zoom);
                    
                    //指定地图显示的范围，不允许移动到范围外
                    /*
                    var bounds = new BMap.Bounds(new BMap.Point(item.minLon, item.minLat), new BMap.Point(item.maxLon, item.maxLat));
                    try {    
                        //BMapLib.AreaRestriction.setBounds(map, bounds);
                    } catch (e) {
                        alert(e);
                    }
                    */
                }, "json");
            }
            
            try {
                loadDeviceMarker();
                
                setInterval(function(){
                     //设置全局ajax等待为false
                    loadDeviceMarker(); // 向地图添加覆盖物
                    //$.ajaxSettings.global = true;   //恢复全局等待界面为true
                }, 5000);
                
            } catch (e) {
                alert(e);
            }
        }, "json");
        
    }
    
    /**
     * 地图上加载所有监控设备信息
     */
    function loadDeviceMarker() {
        $.ajaxSettings.global = false;
        $.post("management/device/queryAllNum", function(devNum) {
            
            $.ajaxSettings.global = true;
            
            if(isNaN(devNum) || devNum == null) return; //假如不是数字或空，则终止运行
            
            if (devNum != 0) {
                if (prevDevNum != devNum)  {
                    //保存这一次的设备数量
                    prevDevNum = devNum;
                    //标注运行状态为true
                    initDeviceState = true;
                    //加载所有监控设备
                    $.ajaxSettings.global = false;
                    $.post("management/device/queryAll", function(data) {
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
                            var label = new BMap.Label(item.deviceName, {
                                        offset : new BMap.Size(25, 5)
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
            
            if (alarmNum!=0 && alarmNum!=null) {
                
                if (prevMonAlarmNum != alarmNum)  {
                    //标准运行状态为true
                    initMonitorAlarmStatus = true;
                    
                    $.ajaxSettings.global = false;
                    
                    $.post("management/alarm/monitor/monitor_alarm_devices", function(data) {
                        
                        $.ajaxSettings.global = true;
                        
                        var alarmSize = data.length; //保存后台查询回来的告警设备的数量
                        
                        var deviceSize = 0; //保存在地图上匹配的告警设备数量
                        
                        var allOverlay = map.getOverlays();
                        
                        $.each(allOverlay, function(index, overlayItem) {
                            
                            if(overlayItem instanceof BMap.Marker) {
                                
                                if(overlayItem.alarm == true) {
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
                                      
                                      if(overlayItem.id == deviceID) {
                                          
                                          deviceSize++;
                                          
                                          overlayItem.alarm = true
                                          
                                          //overlayItem.setAnimation(BMAP_ANIMATION_BOUNCE);
                                          //将告警图标设置为
                                        overlayItem.setIcon(
                                            new BMap.Icon("styles/res/images/icon.png", 
                                                new BMap.Size(20, 25), {
                                                    imageOffset : new BMap.Size(-46, -21)
                                                }
                                            )
                                        );
                                    }
                                  });
                            }
                        });
                        
                        if (alarmSize==deviceSize && deviceSize!=0) {
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
                            if(overlayItem.alarm == true) {
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
        
        $("#refreshMap").on("click", function(e){
            
            loadDeviceMarker(); // 向地图添加覆盖物
            
            setTimeout(function(){
               initMonitorAlarm();
            }, 3000);
        });
        
        
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
    
        var myValue;
        ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
        var _value = e.item.value;
            myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
            G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
            
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
            local.search(myValue);
        }
        
        function G(id) {
            return document.getElementById(id);
        }
        
    }
    /**
     * 覆盖物添加事件 包括单击事件和右键菜单
     * @param {} target
     * @param {} window
     */
    function addMapHandler(target) {
        //地图右键菜
        target.addEventListener("rightclick", function(e) {
            var thisObj = this;
            // 自定义右键菜单内容
            var menu = new BMap.ContextMenu();
            var txtMenuItem = [{
                        text : '添加监控设备',
                        callback : function() {
                            //打开添加监控设备窗口
                            openAddDialog(e);
                        }
                    },{
                        text : '放大',
                        callback : function() {
                            map.zoomIn()
                        }
                    }, {
                        text : '缩小',
                        callback : function() {
                            map.zoomOut()
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
                    }];
            for (var i = 0; i < txtMenuItem.length; i++) {
                menu.addItem(new BMap.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback, 100));
                if (i == 0) {
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
     * @param {} target
     * @param {} window
     */
    function addMarkerHandler(marker) {
        //单击事件
        marker.addEventListener("click", function(e) {
            
            var target = e.target;
            if (target.alarm == true) { //假如有告警，则显示告警列表
                $.pdialog.open("management/alarm/monitor/queryAlarmByDeviceID/" + target.id, "monitorAlarmDialog", "设备与告警信息",{
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
                $.post("management/device/query/" + target.id, function(item){
                    var opts = {
                        width : 200,
                        title : "设备信息",
                        enableMessage : false
                    };
                    
                    var content = "<p><b>标记名称</b>：" + item.deviceName + "</p>";
                        
                    if(item.categoryID==1) {
                        content += "<p><b>城市代码</b>：" + item.deviceNumber + "</p><p><b>设备名称</b>：" + item.attachNumber + "</p>"
                    } else {
                        content += "<p><b>设备编号</b>：" + item.deviceNumber + "</p><p><b>MAC地址</b>：" + item.attachNumber + "</p>"
                    }
                    
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
            
            var geo = new BMap.Geocoder();
             //获取地址的数据地址
             var pt = event.point;
             var address = "无法识别出地址信息";
             geo.getLocation(pt, function(evt) {
                 var addComp = evt.addressComponents;
                 //获取地址信息
                 address = addComp.province +  addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                 var deviceObj = {
                     id: target.id,
                     longitude: event.point.lng,
                     latitude: event.point.lat,
                     address:address
                 };
                 
                 $.post("management/device/update?id=" + target.id + "&longitude=" + event.point.lng + "&latitude="+event.point.lat + "&address=" + encodeURI(address), 
                         DWZ.ajaxDone , 
                         "json");
            });
        });
        //哟间菜单事件
        marker.addEventListener("rightclick", function(e) {
            // 自定义右键菜单内容
            var menu = new BMap.ContextMenu();
            var txtMenuItem = [{
                        text : '查询设置参数',
                        callback : function() {
                            openParameterDialog(e);
                        }
                    }, {
                        text : '编辑监控设备',
                        callback : function() {
                            openEditDialog(e);
                        }
                    }, {
                        text : '删除监控设备',
                        callback : function() {
                            deleteDeviceMarker(e);
                        }
                    }, {
                        text : '告警信息',
                        callback : function() {
                            map.zoomIn()
                        }
                    }];
            for (var i = 0; i < txtMenuItem.length; i++) {
                menu.addItem(new BMap.MenuItem(
                        txtMenuItem[i].text,
                        txtMenuItem[i].callback, 100));
                if (i == 2) {
                    menu.addSeparator();
                }
            }
            this.addContextMenu(menu);
        });
        
    }
    
    /**
     * 打开添加设备窗口
     * @param {} event
     */
    function openAddDialog(event) {
        
        var geo = new BMap.Geocoder();
         //获取地址的数据地址
         var pt = event.point;
         var address = "无法识别出地址信息";
         geo.getLocation(pt, function(evt){
             var addComp = evt.addressComponents;
             //获取地址信息
             address = addComp.province +  addComp.city + addComp.district + addComp.street + addComp.streetNumber;
             
             $.pdialog.open("management/device/add?longitude=" + event.point.lng + "&latitude="+event.point.lat + "&address=" + encodeURI(address), "deviceNav", "添加监控设备",{
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
                param:"{msg:'message'}"
            });
         });
    }
    /**
     * 打开编辑设备窗口
     * @param {} event
     */
    function openParameterDialog(event) {
        $.pdialog.open("management/parameterValue?status=" + event.target.id, "queryParamValueNav", "监控设备参数",{
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
    }
    /**
     * 打开编辑设备窗口
     * @param {} event
     */
    function openEditDialog(event) {
        $.pdialog.open("management/device/edit/" + event.target.id, "deviceNav", "编辑监控设备",{
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
                $.post("management/device/delete/" + event.target.id, function(json) {
                    
                    DWZ.ajaxDone(json); //提示操作成功
                    //从地图上删除节点
                    map.removeOverlay(event.target);
                }, "json");
                
            }
        });
    }
    
    /**
     * 初始化监控对象
     */
    function initMonitor() {
        $("#monitorTree").loadUrl("management/load_monitor_list");
    }
    // 向地图添加控件
    function addMapControl() {
        var scaleControl = new BMap.ScaleControl({anchor:BMAP_ANCHOR_TOP_RIGHT});
        scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
        map.addControl(scaleControl);
        var navControl = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_RIGHT,type:BMAP_NAVIGATION_CONTROL_LARGE});
        map.addControl(navControl);
        var overviewControl = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:true});
        map.addControl(overviewControl);
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
})(jQuery)