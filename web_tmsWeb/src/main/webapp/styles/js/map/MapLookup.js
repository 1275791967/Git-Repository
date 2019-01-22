/**
 * 查找地图返回功能，获取经纬度和地址信息
 */
 
//地图对象
var lookupMap;

jQuery(function($) {
    
    // 初始化百度地图
    var mapStatus = createLookupMap();     // 创建地图
    
    if (mapStatus) { //能够获取Map对象（即能够联网）
        
        //获取地图显示的中心点
        $.post("management/map/getMapCenter",function(item) {
            //alert(item.longitude + ":" + item.latitude + ":" + item.zoom);        
            lookupMap.centerAndZoom(new BMap.Point(item.longitude, item.latitude), item.zoom);
        },"json");
        
        //加载设备信息
        $.post("management/device/queryAllNum", function(devNum) {
            
            /*
            $.post("management/device/bounds",function(item) {        
                lookupMap.centerAndZoom(new BMap.Point((item.minLon + item.maxLon)/2, (item.minLat + item.maxLat)/2), 14);
            }, "json");
            */
            
            try {
                //如果有设备，则加载设备列表
                if (devNum!=0 && devNum!=null && !isNaN(devNum)) {
                    loadLookupDeviceMarker();
                }
            } catch (e) {
                alert(e);
            }
        });
    }
    
});

function createLookupMap() {
    
    try {
        lookupMap = new BMap.Map("lookupMap");
        
        setLookupMapEvent();   // 设置地图事件
        
        //addLookupMapControl(); // 向地图添加控件
        
        return true;
    } catch(e) {
        $("#lookupMap").html("<div class='divider'></div><div>&nbsp;&nbsp;地图加载异常，请保证网络通畅，否则无法使用地图功能</div>");
        return false;
    }
}

/**
 * 地图上加载所有监控设备信息
 */
function loadLookupDeviceMarker() {
    //加载所有监控设备
    $.post("management/device/queryAll", function(data) {
        
        $.each(data,function(index, item) {
            //如果无法查寻后台（即session失效），则item为空，这时候跳转到登录界面
            var point = new BMap.Point(item.longitude, item.latitude);
            var marker = new BMap.Marker(point, {
                icon : new BMap.Icon("styles/res/images/icon.png",
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
            //添加标记
            lookupMap.addOverlay(marker);
        });
    }, "json");
}

// 向地图添加控件
function addLookupMapControl() {
    var scaleControl = new BMap.ScaleControl({anchor:BMAP_ANCHOR_TOP_RIGHT});
    scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
    lookupMap.addControl(scaleControl);
    var navControl = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_RIGHT,type:BMAP_NAVIGATION_CONTROL_LARGE});
    lookupMap.addControl(navControl);
    var overviewControl = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:true});
    lookupMap.addControl(overviewControl);
}

function setLookupMapEvent() {
    lookupMap.enableScrollWheelZoom();
    lookupMap.enableKeyboard();
    lookupMap.enableDragging();
    lookupMap.enableDoubleClickZoom();
    //添加单击获取经纬度事件
    addLookupMapHandler(lookupMap);
}

/**
 * 覆盖物添加事件
 * @param {} target
 * @param {} window
 */
function addLookupMapHandler(target) {
    var isLookupMap = false;
    target.addEventListener("dblclick", function(event) { 
        //$("#messageInfo span").html("经度:" + event.point.lng + "&nbsp;纬度:" + event.point.lat);
        
         //获取地址的数据地址
        var pt = event.point;
        var address = "无法识别出地址信息";
        
        if(onlineMap) {
            var geo = new BMap.Geocoder();
            geo.getLocation(pt, function(evt) {
                
                 var addComp = evt.addressComponents;
                 //获取地址信息
                 address = addComp.province +  addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                 //查询返回结果值
                 $.bringBack({longitude:event.point.lng, latitude:event.point.lat, address:address });
            });
        } else {
            
            if(!isLookupMap) {
                $.bringBack({longitude:event.point.lng, latitude:event.point.lat});
                isLookupMap = true;
            }
        }
        
    });
}