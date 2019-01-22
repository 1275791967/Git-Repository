jQuery(function($){
    
    var arrIDs = new Array();
    
    $(document).on("change", ":checkbox[name='ids']", function(e) {
        
        $this = $(this);
        
        if($this.attr("checked")) {
            arrIDs.push($this.val());
        } else {
            $.each(arrIDs, function(index, value){
                if($this.val()==value) arrIDs.splice(index, 1);
            });
        }
        $("#status").val(arrIDs);
    });
    
});