jQuery(function($){
    // 选择设备类型的控制行为
    $("#categoryID", $.pdialog.getCurrent()).on("change", function(event){
         if ($(this).val()==20) {
            $("#deviceNo-title", $.pdialog.getCurrent()).html("设备编号");
            $("#deviceName-title", $.pdialog.getCurrent()).html("MAC地址");
        } else {
            $("#deviceNo-title", $.pdialog.getCurrent()).html("城市代码");
            $("#deviceName-title", $.pdialog.getCurrent()).html("设备名称");
        }
    }).change();
    
    $("#commCategory", $.pdialog.getCurrent()).on("change", function(event){
        if ($(this).val()==1) {
            $("#port", $.pdialog.getCurrent()).val("9107");
        } else if ($(this).val()==2) {
            $("#port", $.pdialog.getCurrent()).val("9101");
        }
    }).change();
    /*
    //控制设备添加或编辑行为
    $("#form_wrapper").find("button[type='submit']").on("click", function(e) {
        
        e.preventDefault();
        
        //alert("经度：" + $('[name="location.longitude"]').val() +  "纬度:" + $('[name="location.latitude"]').val());
        
        $('[name="longitude"]').val($('[name="location.longitude"]').val());
        $('[name="latitude"').val($('[name="location.latitude"]').val());
        $('[name="address"]').val($('[name="location.address"]').val());
        
        //alert("经度：" + $('[name="longitude"]').val() +  "纬度:" + $('[name="latitude"]').val());
        
        //如果是现代浏览器，则为true，如果是IE则为false
        if ($.support.cssFloat ){ 
            $(this).off("click").click();
        } else { 
            window.event.returnValue = false;
            $(this).off("click").click();
            //$(this).click();
        }
    });
    */
});
