jQuery(function($){
    
    $(document).off("click", "#addSetting").on("click", "#addSetting", function(e) {
        
        var startDate = $(":text[name='startDate']", navTab.getCurrentPanel()).val();
        var endDate = $(":text[name='endDate']", navTab.getCurrentPanel()).val();
        
        var siteID = $("#siteID", navTab.getCurrentPanel()).val();
        var siteName = $("#siteName", navTab.getCurrentPanel()).val();
        if(startDate==="") {
            alertMsg.error("开始时间不能为空，请选择开始时间...");
            return;
        }
        if(endDate==="") {
            alertMsg.error("结束时间不能为空，请选择结束时间...");
            return;
        }
        if(siteID==="") {
            alertMsg.error("站点不能为空，请选择站点...");
            return;
        }
        var index = $("table.list tbody tr", navTab.getCurrentPanel()).size();
        
        $("table.list tbody", navTab.getCurrentPanel()).append('<tr target="slt_objId" rel="' + index + '">'  
                + '<input type="hidden" name="deviceVoList[' + index + '].siteID" value="' + siteID + '"/>'
                + '<input type="hidden" name="deviceVoList[' + index + '].content" value="' + siteName + '"/>'
                + '<input type="hidden" name="deviceVoList[' + index + '].startDate" value="' + startDate + '"/>'
                + '<input type="hidden" name="deviceVoList[' + index + '].endDate" value="' + endDate + '"/>'
                + '<td><a href="management/site/detail/' + siteID +'" target="dialog" maxable="false" minable="false" resizable="false" mask="true" title="站点信息" style="color:red">' + siteName + '</a></td>'
                + '<td>' + startDate + '</td>'
                + '<td>' + endDate + '</td>'
                + '<td><a title="删除" href="#" class="btnDel delItemNode">删除</a></td>'
                + '</tr>');
    });
    
    $(document).off("click", ".delItemNode").on("click", ".delItemNode", function(e) {
        $(this).parents("tr").remove();
        
        $("table.list tbody tr", navTab.getCurrentPanel()).each(function(index) {
            
            $(this).attr("rel", index).find(":hidden")
                .eq(0).attr("name","deviceVoList[" + index + "].siteID").end()
                .eq(1).attr("name","deviceVoList[" + index + "].content").end()
                .eq(2).attr("name","deviceVoList[" + index + "].startDate").end()
                .eq(3).attr("name","deviceVoList[" + index + "].endDate").end();
        });
    });
});