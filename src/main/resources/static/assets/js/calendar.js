/**
*	마이페이지의 일정 관리(달력)
*/

document.addEventListener('DOMContentLoaded', function() {
	var calendarEl = document.getElementById('calendar');
	
		// full-calendar 생성하기      
		var calendar = new FullCalendar.Calendar(calendarEl, {
		        		  
		 // 해더에 표시할 툴바        
		 headerToolbar: {          
		 	left: 'prev,next today',          
		 	center: 'title',          
		 	right: 'dayGridMonth,listMonth'      
		 },    
		 
		 expandRows: true, // 화면에 맞게 높이 재설정   
		 initialView: 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
		 navLinks: true, // 날짜를 선택하면 Day 캘린더나 Week 캘린더로 링크      
		 nowIndicator: true, // 현재 시간 마크        
		 dayMaxEvents: true, // 이벤트가 오버되면 높이 제한 (+ 몇 개식으로 표현)        
		 locale: 'ko', // 한국어 설정  
		 //eventColor: 'blue',	// 이벤트 색상
		 
		 // 마우스 올렸을 때 설명 띄우기
//		 eventDidMount: function(info) {
//		        var tooltip = new Tooltip(info.el, {
//		          title: info.event.extendedProps.description,
//		          placement: 'top',
//		          trigger: 'hover',
//		          container: 'body'
//		        });
//		      }, 
   
		 // 이벤트 
		 events: [          
		 	{            
		 		title: 'test event',            
		 		start: '2022-06-24',
		 		description: '테스트 이벤트',
		 	}, 
		 	{            
		 		title: 'Click test',            
		 		url: 'http://google.com/', // 클릭시 해당 url로 이동 
		 		description: 'description for Repeating Event',
		 		start: '2022-06-28',    
		 		color: 'purple'  
		 	}        
		 	]
			});          
			calendar.render();    // 캘린더 랜더링  
		}); 
			