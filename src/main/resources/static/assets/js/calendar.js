/**
*	마이페이지의 일정 관리(달력)
*/

function addCalendar(member_id){
	// 캘린더 div
	var calendarEl = document.getElementById('calendar');
		
	// full-calendar 생성하기      
	var calendar = new FullCalendar.Calendar(calendarEl, {
				  
		 // 해더에 표시할 툴바        
		 headerToolbar: {          
		 	left: 'prev,next today',          
		 	center: 'title',          
		 	right: 'dayGridMonth,listMonth'      
		 },    
		 
		 expandRows: true, 					// 화면에 맞게 높이 재설정   
		 initialView: 'dayGridMonth', 		// 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
		 navLinks: true, 					// 날짜를 선택하면 Day 캘린더나 Week 캘린더로 링크      
		 nowIndicator: true, 				// 현재 시간 마크        
		 dayMaxEvents: true, 				// 이벤트가 오버되면 높이 제한 (+ 몇 개식으로 표현)        
		 locale: 'ko', 						// 한국어 설정  

		 events: function(_info, successCallback, _failureCallback){
			$.ajax({
				type: 'GET',
				url: "/mypage/challenge-all",
				dataType : 'json',
				contentType : 'application/json; charset=UTF-8',
				data : {
					member_id : member_id
				},
				success: function(data){
					var listProduce = data['listProduce'];	// 생성 챌린지 
					var listPart = data['listPart'];		// 참여 챌린지
					
					var events = [];
					
					// 생성 챌린지 추가
					for(const produce of listProduce){
						if(produce['maincategory_name'] == "취미"){
							events.push({
								title : '[' + produce['subcategory_name'] + '] ' + produce['challenge_name'],
								start: produce['startdate'],
								end: produce['enddate'],
								color: '#80caa2',
								url: '/challenge/detail?challenge_id=' + produce['challenge_id'] 
							});
						}
						else{
							events.push({
								title : '[' + produce['subcategory_name'] + '] ' + produce['challenge_name'],
								start: produce['startdate'],
								end: produce['enddate'],
								color: '#86a0e6',
								url: '/challenge/detail?challenge_id=' + produce['challenge_id'] 
							});
						}
					}
					
					// 참여 챌린지 추가
					for(var j = 0; j < listPart.length; j++){
						if(listPart[j]['maincategory_name'] == "취미"){
							events.push({
								title : '[' + listPart[j]['subcategory_name'] + '] ' + listPart[j]['challenge_name'],
								start: listPart[j]['startdate'],
								end: listPart[j]['enddate'],
								color: '#80caa2',//'#8cc084',
								url: '/challenge/detail?challenge_id=' + listPart[j]['challenge_id'] 
							});
						}
						else{
							events.push({
								title : '[' + listPart[j]['subcategory_name'] + '] ' + listPart[j]['challenge_name'],
								start: listPart[j]['startdate'],
								end: listPart[j]['enddate'],
								color: '#86a0e6',
								url: '/challenge/detail?challenge_id=' + listPart[j]['challenge_id'] 
							});
						}
					}
					successCallback(events);
				}
			})
		}
	});         
		 
	calendar.render();    // 캘린더 랜더링  
}