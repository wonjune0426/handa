/**
* Template Name: Logis - v1.0.1
* Template URL: https://bootstrapmade.com/logis-bootstrap-logistics-website-template/
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/
document.addEventListener('DOMContentLoaded', () => {
  "use strict";

  /**
   * Preloader
   */
  const preloader = document.querySelector('#preloader');
  if (preloader) {
    window.addEventListener('load', () => {
      preloader.remove();
    });
  }

  /**
   * Sticky header on scroll
   */
  const selectHeader = document.querySelector('#header');

  /*
  if (selectHeader) {
    document.addEventListener('scroll', () => {
      window.scrollY > 100 ? selectHeader.classList.add('sticked') : selectHeader.classList.remove('sticked');
    });
  }
  */


  /**
   * Scroll top button
   */
  const scrollTop = document.querySelector('.scroll-top');
  if (scrollTop) {
    const togglescrollTop = function() {
      window.scrollY > 100 ? scrollTop.classList.add('active') : scrollTop.classList.remove('active');
    }
    window.addEventListener('load', togglescrollTop);
    document.addEventListener('scroll', togglescrollTop);
    scrollTop.addEventListener('click', window.scrollTo({
      top: 0,
      behavior: 'smooth'
    }));
  }
  
  const scrollTop2 = document.querySelector('.scroll-top2');
  if (scrollTop2) {
    const togglescrollTop = function() {
      window.scrollY > 100 ? scrollTop2.classList.add('active') : scrollTop2.classList.remove('active');
    }
    window.addEventListener('load', togglescrollTop);
    document.addEventListener('scroll', togglescrollTop);
    scrollTop2.addEventListener('click', function() {
		location.href="/challenge";
	});
  }
  
  const scrollTop3 = document.querySelector('.scroll-top3');
  if (scrollTop3) {
    const togglescrollTop = function() {
      window.scrollY > 100 ? scrollTop3.classList.add('active') : scrollTop3.classList.remove('active');
    }
    window.addEventListener('load', togglescrollTop);
    document.addEventListener('scroll', togglescrollTop);
    scrollTop3.addEventListener('click', function() {
		location.href="/qna";
	});
  }

  /**
   * Mobile nav toggle
   */
  const mobileNavShow = document.querySelector('.mobile-nav-show');
  const mobileNavHide = document.querySelector('.mobile-nav-hide');

  document.querySelectorAll('.mobile-nav-toggle').forEach(el => {
    el.addEventListener('click', function(event) {
      event.preventDefault();
      mobileNavToogle();
    })
  });

  function mobileNavToogle() {
    document.querySelector('body').classList.toggle('mobile-nav-active');
    mobileNavShow.classList.toggle('d-none');
    mobileNavHide.classList.toggle('d-none');
  }

  /**
   * Hide mobile nav on same-page/hash links
   */
  document.querySelectorAll('#navbar a').forEach(navbarlink => {

    if (!navbarlink.hash) return;

    let section = document.querySelector(navbarlink.hash);
    if (!section) return;

    navbarlink.addEventListener('click', () => {
      if (document.querySelector('.mobile-nav-active')) {
        mobileNavToogle();
      }
    });

  });

  /**
   * Toggle mobile nav dropdowns
   */
  const navDropdowns = document.querySelectorAll('.navbar .dropdown > a');

  navDropdowns.forEach(el => {
    el.addEventListener('click', function(event) {
      if (document.querySelector('.mobile-nav-active')) {
        event.preventDefault();
        this.classList.toggle('active');
        this.nextElementSibling.classList.toggle('dropdown-active');

        let dropDownIndicator = this.querySelector('.dropdown-indicator');
        dropDownIndicator.classList.toggle('bi-chevron-up');
        dropDownIndicator.classList.toggle('bi-chevron-down');
      }
    })
  });

  /**
   * Initiate pURE cOUNTER
   */
  new PureCounter();

  /**
   * Initiate glightbox
   */
  const glightbox = GLightbox({
    selector: '.glightbox'
  });
  
  function swiperClick (activeBanner, bannerLink) {
	
	  // Swiper 클릭 이벤트
	  $(".swiper-wrapper").on("click", function (e) {
			e.preventDefault();			
			 
			 console.log('swiper click: ', bannerLink);
			// 현재 띄워져있는 슬라이드의 링크로 이동
			//location.href = bannerLink;
		});	
	}

  /**
   * Init swiper slider with 1 slide at once in desktop view
   */
  new Swiper('.slides-1', {
    speed: 600,
    loop: true,
    autoplay: {
      delay: 5000,
      disableOnInteraction: false
    },
    slidesPerView: 'auto',
    pagination: {
      el: '.swiper-pagination',
      type: 'bullets',
      clickable: true
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    on: {
		slideChangeTransitionStart: function() {
			//배너 이미지 변경 
		  const activeBanner = document.querySelector('.swiper-slide-duplicate-active');
		  const bannerImg = activeBanner.dataset.bannerImg;
		  const changeBanner = document.querySelector('.main-banner .swiper-slide-active');
		  
		  const bannerLink = activeBanner.dataset.bannerLink;
		  
		  //console.log(activeBanner, bannerImg, changeBanner);
		  
		  changeBanner.style.background = 'url(' + bannerImg + ') no-repeat';
		  changeBanner.style.backgroundSize = 'cover';
		  changeBanner.style.backgroundPosition = 'center';
		  changeBanner.style.transition = 'background-image 0.5s ease-out';
		  // changeBanner.style.opacity = '0.3';
		   
		},
		click: function() {
			const activeBanner = document.querySelector('.swiper-slide-duplicate-active');
			const bannerLink = activeBanner.dataset.bannerLink;
			
			// console.log(bannerLink);
			
			location.href = bannerLink;
		},

	}
	
  });

	new Swiper('.slides-2', {
    speed: 600,
    loop: true,
    autoplay: {
      delay: 5000,
      disableOnInteraction: false
    },
    slidesPerView: 'auto',
    pagination: {
      el: '.swiper-pagination',
      type: 'bullets',
      clickable: true
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    
    on: {
		slideChangeTransitionStart: function() {
			//배너 이미지 변경 
		  const activeBanner = document.querySelector('.event-banner .swiper-slide-duplicate-active');
		  const bannerImg = activeBanner.dataset.bannerImg;
		  const changeBanner = document.querySelector('.event-banner .swiper-slide-active');
		  const activeBannerColor = document.querySelector('.event-banner .swiper-slide-duplicate-active .testimonial-item');
		  const changeBannerColor = document.querySelector('.event-banner');
		  
		  // console.log("change , ", bannerLink);
		  
		  changeBannerColor.style.background = activeBannerColor.dataset.bannerColor;
		  changeBanner.style.background = 'url(' + bannerImg + ') no-repeat';
		  changeBanner.style.backgroundSize = 'cover';
		  changeBanner.style.backgroundPosition = 'center';
		  changeBanner.style.transition = 'background-image 0.5s ease-out';
		   
		},
	}
	
  });

  /**
   * Animation on scroll function and init
   */
  function aos_init() {
    AOS.init({
      duration: 1000,
      easing: 'ease-in-out',
      once: true,
      mirror: false
    });
  }
  window.addEventListener('load', () => {
    aos_init();
  });
  
  

});