(function($) {
	jQuery(function($){
		
		$("[valueIdentifier]", navTab.getCurrentPanel()).on("change", function() {
			$(this).css("background-color","#CCCC33");
	        $("[paramIdentifier='" + $(this).attr("valueIdentifier") +  "']").attr("checked", true);
	    });
		
		/*
		$(".queryParameter", navTab.getCurrentPanel()).on("click", function() {
			 $this = $(this);
			 var rel = $this.attr("rel");
			 var type = $this.attr("type");
			 
			 queryParameterCmd(this, rel, type);
		 });
		*/
	});
})(jQuery);    

/**
 * 设置参数请求
 * @returns
 */
function setParameterCmd(obj, relId, type) {

	var thisParent = $(obj).parents(".unitBox:first");
	
	var checkedParam = $(":checkbox[name='ids']:checked", thisParent);
    if (checkedParam.size() <= 0) {
    	alertMsg.warn("没有选择要提交的参数");
        return;
    }
    var deviceID = $("[name='status']", navTab.getCurrentPanel()).val();
    // 创建要提交的参数信息
    var jsonObj = {};
    jsonObj.deviceID = deviceID;
    
    var valueMap = {};
    var dateMap = {};
    
    var paramAttribute = 0;
    var identifier = "";
    var value = "";
    
    checkedParam.each(function() {
    	
        var tdValueEle = $(this).parent().nextUntil(".record-time").filter(".parameter-value");
        
        paramAttribute = tdValueEle.attr("paramAttribute");
        
        if (paramAttribute == 2) {
            identifier = tdValueEle.attr("identifier");
            value = tdValueEle.find("[valueIdentifier]").val();
            //保存当前值信息
            valueMap[identifier] = value;
            //保存参数的当前时间，供刷新时使用
            var modifyDate = tdValueEle.attr("modifyDate");
            dateMap[identifier] = modifyDate;
            //alert(paramAttribute + ":" + identifier + ":" + value + ":" + modifyDate);
        }
    });
    
    jsonObj.paramValMap = valueMap;
    
    //提交查询请求
    $.ajaxSettings.global = false;
    $.post("management/parameterValue/paramValueSetAsk", jsonObj, function(json) {
        $.ajaxSettings.global = true;
        //提示提交成功
        DWZ.ajaxDone(json);
        //获取当前时间
        var startDate = new Date();
        //设置定时查询任务，查询接收后结束该任务
        var paramQueryTask = setInterval(function(){
            var endDate = new Date();
            
            var interTime = Math.floor((endDate.getTime() - startDate.getTime())/1000); //相差秒数
            
            if (interTime>=120) { //当持续等待2分钟（120s）后，取消该任务
                alertMsg.warn('指定时间内内没有返回值，查询失败');
                //清理定时器
                clearInterval(paramQueryTask);
            } else {
                $.ajaxSettings.global = false;
                $.post("management/parameterValue/paramValueModifyDate", jsonObj, function(mapJson) {
                    $.ajaxSettings.global = true;
                    
                    if (mapJson != null && mapJson.paramDateMap != null) {
                        var valueSize = 0;
                        var queryCount = 0;
                        var paramValObj = mapJson.paramValMap;
                        //传递的key和val分别是参数identifier和更新时间
                        $.each(mapJson.paramDateMap, function(key, valDate) {
                            
                            valueSize++;
                            
                            if (valDate!=null && valDate != dateMap[key]) { //如果数据库时间和当前时间不一致，认为数据库已经获取了查询数据，可以认为设备数据返回了。
                                queryCount++;
                                
                                delete dateMap[key];              //删除已经更新时间的日期
                                delete jsonObj.paramValMap[key];  //删除已经更新时间的参数
                            }
                        });
                        
                        if (queryCount > 0) {
                            var data ={};
                            data.status = deviceID;
                            data.type=type;
                            
                            $(relId).loadUrl("management/parameterValue/paramcategory", data, function() {
                            	$.each(valueMap, function(name, value) {
                            		//设置未查询的结果为选中状态
                            		$("[paramIdentifier=' " + name + "']").attr("checked", true);
                            		//设置未设置值未未选中时设置值
                            		$("[valueIdentifier='" + name + "']").val(valueMap[name])
                            	});
                            });
                        }
                        
                        if (valueSize==queryCount) {
                            $("table th :checkbox", thisParent).attr("checked", false);
                            
                            alertMsg.correct("参数设置完成");
                            clearInterval(paramQueryTask);
                        }
                    } else {
                        clearInterval(paramQueryTask);
                    }
                }, "json");
            }
        }, 3000);
    }, "json");
}

/**
 * 查询参数请求
 * @returns
 */

function queryParameterCmd(obj, relId, type) {
	
	var thisParent = $(obj).parents(".unitBox:first");
	
	var checkedParam = $(":checkbox[name='ids']:checked", thisParent);
    if (checkedParam.size() <= 0) {
    	alertMsg.warn("没有选择要提交的参数");
        return;
    }
    var deviceID = $("[name='status']", navTab.getCurrentPanel()).val();
    // 创建要提交的参数信息
    var jsonObj = {};
    jsonObj.deviceID = deviceID;
    var valueMap = {};
    var dateMap = {};
    
    checkedParam.each(function() {
        
        var tdValueEle = $(this).parent().nextUntil(".record-time").filter(".parameter-value");
        
        var identifier = tdValueEle.attr("identifier");
        //保存当前值信息
        valueMap[identifier] = "";
        //保存参数的当前时间，供刷新时使用
        var modifyDate = tdValueEle.attr("modifyDate");
        dateMap[identifier] = modifyDate;
    });
    jsonObj.paramValMap=valueMap;
    
    $.ajaxSettings.global = false;
    $.post("management/parameterValue/paramValueQueryAsk", jsonObj, function(json) {
        $.ajaxSettings.global = true;
        //提示提交成功
        DWZ.ajaxDone(json);
        
        var startDate = new Date();
        
        //设置定时查询任务，查询接收后结束该任务
        var paramQueryTask = setInterval(function(){
            var endDate = new Date();
            
            var interTime = Math.floor((endDate.getTime() - startDate.getTime())/1000); //相差秒数
            
            if (interTime>=120) { //当持续等待2分钟（120s）后，取消该任务
                alertMsg.warn('指定时间内内没有返回值，查询失败');
                
                clearInterval(paramQueryTask);
            } else {
                $.ajaxSettings.global = false;
                $.post("management/parameterValue/paramValueModifyDate", jsonObj, function(mapJson) {
                    $.ajaxSettings.global = true;
                    if (mapJson != null && mapJson.paramDateMap != null) {
                        var valueSize = 0;
                        var queryCount = 0;
                        var paramValObj = mapJson.paramValMap;
                        //传递的key和val分别是参数identifier和更新时间
                        $.each(mapJson.paramDateMap, function(key, valDate) {
                            
                            valueSize++;
                            
                            if ( valDate != null && valDate != dateMap[key]) { //如果数据库时间和当前时间不一致，认为数据库已经获取了查询数据，可以认为设备数据返回了。
                                queryCount++;
                                
                                delete dateMap[key];              //删除已经更新时间的日期
                                delete jsonObj.paramValMap[key];  //删除已经更新时间的参数
                            }
                        });
                        
                        if (queryCount > 0) {
                            var data ={};
                            data.status = deviceID;
                            data.type=type;
                            
                            $(relId).loadUrl("management/parameterValue/paramcategory", data, function(){
                            	//设置未查询的结果为选中状态
                        		$.each(valueMap, function(name, value) {
                            		//设置未查询的结果为选中状态
                            		$("[paramIdentifier=' " + name + "']").attr("checked", true);
                            	});
                            });
                        }
                        
                        if (valueSize==queryCount) {
                            $("table th :checkbox", thisParent).attr("checked", false);
                            
                            alertMsg.correct("查询完成");
                            clearInterval(paramQueryTask);
                        }
                    } else {
                        clearInterval(paramQueryTask);
                    }
                }, "json");
            }
        }, 3000);
    }, "json");
}  
    

    