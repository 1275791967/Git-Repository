jQuery(function($) {
    
    var selPv = $("[name='province']");
    var selCt = $("[name='city']");
    
    $.post("management/system/callAttribution/queryProvinces", function(data) {
        
        if(data != null) {
            
            selPv.empty();
            selPv.append("<option + value=''>全部</option>");
            $.each(data, function(key, value) {
                selPv.append("<option + value='" + value + "'>" + value + "</option>");
            });
        }
    }, "json").done(function(){
        var pro = $("#province_val").val();
        
        if (pro!=null && pro!="") {

            selPv.val(pro);
        	$.post("management/system/callAttribution/queryCities/" + encodeURI(encodeURI(pro)), function(data) {
                
                if(data != null) {
                    selCt.empty();
                    selCt.append("<option + value=''>全部</option>");
                    
                    $.each(data, function(key, value) {
                        selCt.append("<option + value='" + value + "'>" + value + "</option>");
                    });
                }
            }, "json").done(function(){
                var city = $("#city_val").val();
                selCt.val(city);
            });
        }
        
    });
    
    $(document).on("change", "[name='province']", function(e) { //删除设备
        
        $this = $(this);
        
        $.post("management/system/callAttribution/queryCities/" + encodeURI(encodeURI($this.val())), function(data) {
            
            if(data != null) {
                selCt.empty();
                selCt.append("<option + value=''>全部</option>");
                
                $.each(data, function(key, value) {
                    selCt.append("<option + value='" + value + "'>" + value + "</option>");
                });
            }
        }, "json")
    });
});
