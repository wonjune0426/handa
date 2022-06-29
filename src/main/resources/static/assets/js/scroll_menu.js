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
      