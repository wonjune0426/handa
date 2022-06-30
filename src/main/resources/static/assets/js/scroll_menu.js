$('.detail_menu').each(function(index){
	$(this).attr('menu-index', index);
}).click(function(){
	var index = $(this).attr('menu-index');
	
	$('.detail_menu[menu-index=' + index + ']').addClass('activeMenu');
	$('.detail_menu[menu-index=' + index + ']').removeClass('menuTab');
	
	$('.detail_menu[menu-index!=' + index + ']').removeClass('activeMenu');
	$('.detail_menu[menu-index!=' + index + ']').addClass('menuTab');
})

$(function() {
        var scrollMenu = $("#scrollMenu").offset().top;
        
        $("#scrollMenu").click(function(){
			$("#scrollMenu").addClass("fixed");
		})
        $(window).scroll(function() {
          var window = $(this).scrollTop();

          if(scrollMenu <= window) {
            $("#scrollMenu").addClass("fixed");
          } else {
            $("#scrollMenu").removeClass("fixed");
          }
        })
      });
      