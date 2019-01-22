jQuery(function($){
    /**
     * 查询指定MAC、IMSI或IMEI的历史记录信息
     */
    $(document).off("click", ":button#resetBtn").on("click", ":button#resetBtn", function() {
        
        $("#resetState").val("true");
        
        $(":button[type='submit']", navTab.getCurrentPanel()).click();
    });

    $(document).off("click", ".search-line-btn").on("click", ".search-line-btn", function() {
        
        var lineTable = $("#devicePageContent table tbody",navTab.getCurrentPanel());
        
        var type = $("[name='type']",navTab.getCurrentPanel()).val();
        var content = $("[name='content']",navTab.getCurrentPanel()).val();
        var startDate = $(":text[name='startDate']",navTab.getCurrentPanel()).val();
        var endDate = $(":text[name='endDate']",navTab.getCurrentPanel()).val();
        //alert(type + "#" + content + "#" + startDate + "#" + endDate);
        lineTable.loadUrl("management/analysis/alongAnalysis/searchLine", {
            type: type, 
            content: content,
            startDate:startDate, 
            endDate: endDate
        });
    });
});