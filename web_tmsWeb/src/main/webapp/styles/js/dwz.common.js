/**
 * reference dwz.util.date.js
 * @author ZhangHuihua@msn.com
 * 
 */
(function ($) {
    $(document).on("click", ".searchContent  .add-device", function (e) { //添加设备

        $(".searchContent:last", navTab.getCurrentPanel()).after($("<ul></ul>").addClass("searchContent"));

        var serachCtxLast = $(".searchContent:last", navTab.getCurrentPanel());
        var categoryID = $(":hidden[name='type']", navTab.getCurrentPanel()).val();
        serachCtxLast.loadUrl("management/analysis/trackQuery/add", {type: categoryID});
    });

    $(document).on("click", ".searchContent .remove-device", function (e) { //删除设备
        $(this).parents(".searchContent:first").remove();
    });

    // 当轨迹类型改变时，重置设备选项列表
    $(document).on("change", "#track-type", function (e) {
        var thisObj = $(this);

        var preType = $("input:hidden[name='prevType']");

        $("input:hidden[name='type']", navTab.getCurrentPanel()).val(thisObj.val());

        if ($(this).val() != preType.val() && (preType.val() == 1 && thisObj.val() != 1) || (thisObj.val() == 1 && preType.val() != 1)) {

            var categoryID = $("#track-type", navTab.getCurrentPanel()).val();

            var deviceIDs = 0;
            var startDate = "";
            var endDate = "";
            var size = $(".searchContent", navTab.getCurrentPanel()).size();
            $(".searchContent", navTab.getCurrentPanel()).each(function (index, domEle) {
                if (index < size - 1) {
                    deviceIDs += $(this).find("select[name='status']").val() + ",";
                    startDate += $(this).find("input[name='startDate']").val() + ",";
                    endDate += $(this).find("input[name='endDate']").val() + ",";
                } else {
                    deviceIDs += $(this).find("select[name='status']").val();
                    startDate += $(this).find("input[name='startDate']").val();
                    endDate += $(this).find("input[name='endDate']").val();
                }

            }).remove();

            $(".searchBar", navTab.getCurrentPanel()).loadUrl("management/analysis/trackQuery/add", {
                type: categoryID,
                keywords: "trackTypeSwitch",
                startDate: startDate,
                endDate: endDate
            });
        }
        //保存上一次选择的选择项
        preType.val(thisObj.val());
    }).change();

    // 当轨迹类型改变时，重置设备选项列表
    $(document).on("change", "#date-type", function (e) {
        var thisObj = $(this);

        var preType = $("input:hidden[name='type']", navTab.getCurrentPanel());

        if ($(this).val() != preType.val() && (preType.val() == 1 && thisObj.val() != 1) || (preType.val() != 1 && thisObj.val() == 1)) {

            $("#setting-view", navTab.getCurrentPanel()).loadUrl("management/analysis/collisionAnalysis/site_list?type=" + $(this).val());

            //清理条件设置列表
            $("table.list tbody", navTab.getCurrentPanel()).empty();
        }
        //保存上一次选择的选择项
        preType.val(thisObj.val());
    }).change();
    /*
     $(document).on("click", "#addSetting", function(e) {
     
     var startDate = $(":text[name='startDate']",navTab.getCurrentPanel()).val();
     var endDate = $(":text[name='endDate']",navTab.getCurrentPanel()).val();
     
     var devID = $("[name='status']",navTab.getCurrentPanel()).find("option:selected").val();
     var devName = $("[name='status']",navTab.getCurrentPanel()).find("option:selected").text();
     
     if(devID=="") {
     alertMsg.error("站点不能为空，请选择站点...");
     return;
     }
     
     $("table.list tbody", navTab.getCurrentPanel()).append('<tr>'  
     + '<input type="hidden" name="deviceVoList[' + index + '].status" value="' + devID + '"/>'
     + '<input type="hidden" name="deviceVoList[' + index + '].startDate" value="' + startDate + '"/>'
     + '<input type="hidden" name="deviceVoList[' + index + '].endDate" value="' + endDate + '"/>'
     + '<td><a href="management/device/detail/' + devID +'" target="dialog" maxable="false" minable="false" resizable="false" mask="true" title="监控设备信息" style="color:red">' + devName + '</a></td>'
     + '<td>' + startDate + '</td>'
     + '<td>' + endDate + '</td>'
     + '<td><a title="删除" href="#" class="btnDel">删除</a></td>'
     + '</tr>'
     );
     
     });
     */

    //存放选择的IP，用于伴随分析市使用
    var arrIDs = new Array();
    //定义伴随分析选择设备时触发的行为
    $(document).off("change", "#devicePageContent :checkbox[name='ids']").on("change", "#devicePageContent :checkbox[name='ids']", function (e) {

        $this = $(this);

        if ($this.attr("checked")) {
            arrIDs.push($this.val());
        } else {
            $.each(arrIDs, function (index, value) {
                if ($this.val() == value)
                    arrIDs.splice(index, 1);
            });
        }

        $("#historyIds", navTab.getCurrentPanel()).val(arrIDs);

        $("#resetState", navTab.getCurrentPanel()).val("false");

        $(":button[type='submit']", navTab.getCurrentPanel()).click();
    });
})(jQuery);
 