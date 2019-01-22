jQuery(function($){
        
    //参数查询
    $("#queryParameter", navTab.getCurrentPanel()).click(function() {
        
        var checkedParam = $(":checkbox[name='ids']:checked", navTab.getCurrentPanel());
        if (checkedParam.size() <= 0) {
            alert("没有选择要提交的参数");
            return;
        }
        // 创建要提交的参数信息
        var jsonObj = {};
        jsonObj.deviceID = $("[name='status']", navTab.getCurrentPanel()).val();
        var valueMap = {};
        var dateMap = {};
        
        checkedParam.each(function() {
            $this = $(this);
            
            var identifier = $this.parent().next().next().find("[name='identifier']").val();
            var value = "";
            
            valueMap[identifier] = value;
            
            //保存参数的当前时间，供刷新时使用
            var modifyDate = $this.parent().next().next().find("[name='modifyDate']").val();
            //alert(modifyDate);
            dateMap[identifier] = modifyDate;
        });
        jsonObj.paramValMap=valueMap;
        
        $.post("management/parameterValue/paramValueQueryAsk", jsonObj, function(json) {
            //提示提交成功
            DWZ.ajaxDone(json);
            
            var startDate = new Date();
            
            //设置定时查询任务，查询接收后结束该任务
            var paramQueryTask = setInterval(function(){
                var endDate = new Date();
                
                var interTime = Math.floor((endDate.getTime() - startDate.getTime())/1000); //相差秒数
                
                if (interTime>=120) { //当持续等待2分钟（120s）后，取消该任务
                    alertMsg.info('指定时间内内没有返回值，查询失败');
                    
                    clearInterval(paramQueryTask);
                } else {
                    
                    $.post("management/parameterValue/paramValueModifyDate", jsonObj, function(mapJson) {
                        
                        if (mapJson != null && mapJson.paramDateMap != null) {
                            var valueSize = 0;
                            var queryCount = 0;
                            var paramValObj = mapJson.paramValMap;
                            //传递的key和val分别是参数identifier和更新时间
                            $.each(mapJson.paramDateMap, function(key, valDate) {
                                
                                valueSize++;
                                //alert((dateMap[key]==null) + ":" + (dateMap[key]=="") + ":" + (valDate==null) + ":" + (valDate==""));
                                if (valDate != dateMap[key] && valDate != null) { //如果数据库时间和当前时间不一致，认为数据库已经获取了查询数据，可以认为设备数据返回了。
                                    queryCount++;
                                    
                                    delete dateMap[key];              //删除已经更新时间的日期
                                    delete jsonObj.paramValMap[key];  //删除已经更新时间的参数
                                    
                                    var hidIdEle = $(":hidden[name='identifier'][value='" + key +"']", navTab.getCurrentPanel());
                                    
                                    var hidValEle = hidIdEle.siblings(":hidden[name='value']");
                                    hidValEle.val(paramValObj[key]);
                                    
                                    var paramAttr = hidIdEle.siblings("[name='paramAttribute']");
                                    
                                    var paramerValEle = hidIdEle.siblings("[name='paramerValue']");
                                    if (parseInt(paramAttr.val())==1) {
                                        paramerValEle.html(paramValObj[key]);
                                    } else {
                                        paramerValEle.val(paramValObj[key]);
                                    }
                                    var hidModifyDateEle = hidIdEle.siblings(":hidden[name='modifyDate']");
                                    hidModifyDateEle.val(valDate);
                                    
                                    var recordTimeEle = hidIdEle.parent().siblings("td.recordTime");
                                    recordTimeEle.html(valDate);
                                    
                                    //时间更新的取消选择
                                    var checkEle = hidIdEle.parent("td").prev().prev().find(":checkbox").attr("checked", false);
                                }
                                
                            });
                            if (valueSize==queryCount) {
                                alertMsg.info("查询完成");
                                clearInterval(paramQueryTask);
                            }
                        } else {
                            clearInterval(paramQueryTask);
                        }
                        
                    }, "json");
                    
                }
                
            }, 3000);
            
        }, "json");
    });
    
    //参数查询
    $("#setParameter", navTab.getCurrentPanel()).click(function() {
        
        var checkedParam = $(":checkbox[name='ids']:checked", navTab.getCurrentPanel());
        if (checkedParam.size() <= 0) {
            alert("没有选择要提交的参数");
            return;
        }
        // 创建要提交的参数信息
        var jsonObj = {};
        jsonObj.deviceID = $("[name='status']", navTab.getCurrentPanel()).val();
        
        var valueMap = {};
        var dateMap = {};
        
        var paramAttribute = 0;
        var identifier = "";
        var value = "";
        
        checkedParam.each(function() {
            $this = $(this);
            
            paramAttribute = $this.parent().next().next().find("[name='paramAttribute']").val();
            
            if (paramAttribute == 2) {
                identifier = $this.parent().next().next().find("[name='identifier']").val();
                value = $this.parent().next().next().find("[name='paramerValue']").val();
            
                valueMap[identifier] = value;
                
                //保存参数的当前时间，供刷新时使用
                var modifyDate = $this.parent().next().next().find("[name='modifyDate']").val();
                dateMap[identifier] = modifyDate;
            }
        });
        jsonObj.paramValMap=valueMap;
        
        $.post("management/parameterValue/paramValueSetAsk", jsonObj, function(json) {
            //提示提交成功
            DWZ.ajaxDone(json);
            
            var startDate = new Date();
            
            //设置定时查询任务，查询接收后结束该任务
            var paramQueryTask = setInterval(function(){
                var endDate = new Date();
                
                var interTime = Math.floor((endDate.getTime() - startDate.getTime())/1000); //相差秒数
                //alert("startDate:" + endDate.getTime() + " endDate:" + endDate.getTime() + " interTime:" + interTime);
                if (interTime>=120) { //当持续等待2分钟（120s）后，取消该任务
                    alertMsg.info('指定时间内内没有返回值，查询失败');
                    
                    clearInterval(paramQueryTask);
                } else {
                    
                    $.post("management/parameterValue/paramValueModifyDate", jsonObj, function(mapJson) {
                        
                        if (mapJson != null && mapJson.paramDateMap != null) {
                            var valueSize = 0;
                            var queryCount = 0;
                            var paramValObj = mapJson.paramValMap;
                            //传递的key和val分别是参数identifier和更新时间
                            $.each(mapJson.paramDateMap, function(key, valDate) {
                                
                                valueSize++;
                                
                                if (valDate != dateMap[key]) { //如果数据库时间和当前时间不一致，认为数据库已经获取了查询数据，可以认为设备数据返回了。
                                    queryCount++;
                                    
                                    delete dateMap[key];              //删除已经更新时间的日期
                                    delete jsonObj.paramValMap[key];  //删除已经更新时间的参数
                                    
                                    var hidIdEle = $(":hidden[name='identifier'][value='" + key +"']", navTab.getCurrentPanel());
                                    
                                    var hidValEle = hidIdEle.siblings(":hidden[name='value']");
                                    hidValEle.val(paramValObj[key]);
                                    
                                    var paramAttr = hidIdEle.siblings("[name='paramAttribute']");
                                    
                                    var paramerValEle = hidIdEle.siblings("[name='paramerValue']");
                                    if (parseInt(paramAttr.val())==1) {
                                        paramerValEle.html(paramValObj[key]);
                                    } else {
                                        paramerValEle.val(paramValObj[key]);
                                    }
                                    var hidModifyDateEle = hidIdEle.siblings(":hidden[name='modifyDate']");
                                    hidModifyDateEle.val(valDate);
                                    
                                    var recordTimeEle = hidIdEle.parent().siblings("td.recordTime");
                                    recordTimeEle.html(valDate);
                                    
                                    //时间更新的取消选择
                                    var checkEle = hidIdEle.parent("td").prev().prev().find(":checkbox").attr("checked", false);
                                }
                                
                            });
                            if (valueSize==queryCount) {
                                alertMsg.info("参数设置完成");
                                clearInterval(paramQueryTask);
                            }
                        } else {
                            clearInterval(paramQueryTask);
                        }
                        
                    }, "json");
                }
                
            }, 3000);
            
        }, "json");
    });
});


    