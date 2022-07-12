var challengeList = null;
var challengeCount = null;

function getList(category, sortType, createdate, count, searchWord, challengeType){

	$.ajax({
		type : 'GET',
		url : '/challenge-list',
		dataType : 'json',
 		data:{
			category: category, 
			sortType: sortType,
			createdate : createdate,
			count : count,
			searchWord : searchWord,
			challengeType : challengeType
		},  
		contentType : 'application/json',
		success: function(res){
			challengeList = res['challengeList'];		// 페이지 당 챌린지 리스트
			challengeCount = res['challengeCount'];		// 챌린지 총 개수
			
			const subCategoryName = res['subCategoryName'];	

			var data = "";
			if(challengeCount == 0)
				data += "<div class = 'row gy-4 text-center'><div></div><h1>검색 결과가 없습니다</h1><div></div></div>";
			else{
				for(var i = 0; i < challengeList.length; i++){
					data += "<div class='card-container col-lg-4 col-md-6' data-aos='fade-up' data-aos-delay='100'>";
					data += "<div class='card challenge-item'>";
					data += "<div class='card-img'>";
					data +=	"<img src='" + challengeList[i]['thumbnail'] + "' alt='' class='img-fluid challenge-thumbnail'>";
					data += "</div>";
		
					data += "<h3>";
							
					if(subCategoryName != null){
						data += "<a href='/challenge/detail?challenge_id=" + challengeList[i]['challenge_id'] + "' class='stretched-link'>[" + subCategoryName + "] " + challengeList[i]['challenge_name'] + "</a>";
					}else {
						data += "<a href='/challenge/detail?challenge_id=" + challengeList[i]['challenge_id'] + "' class='stretched-link'>[" + challengeList[i]['subcategory_name'] + "] " + challengeList[i]['challenge_name'] + "</a>";
					}
	
					data += "</h3>";
			
					data += "<p id='card-p'>" + challengeList[i]['startdate'] + " ~ " + challengeList[i]['enddate'] + "</p>";
					data += "<p id='card-p'> 참여인원 " + challengeList[i]['joinVO']['count'] + "명 </p>";
							
					data += "<div class='state'>";
					data += "<div class='open-dt'>" + challengeList[i]['createdate'].split(" ", 1) + " 생성 </div>"; 
					data +=	"<div class='state-label'>" + challengeList[i]['challenge_state'] + "</div>";
					data += "</div>";
								
					data += "</div>";
							
					data += "</div>";
				}
			
				data += "</div>";
			}
			
			$("#list-container").append(data);
		},
		error: function(){
			alert('실패!');
		}
	});
}

function infiniteScroll(category, sortType, createdate, count, searchWord, challengeType){
	
	var page = 0;			// 현재 페이지
	var challenge = 12;		// 한 페이지에 보여줄 챌린지 개수
	
	const $listEnd = document.querySelector('#end');
	const observer = new IntersectionObserver((entries) => {
		if(page < 1){
			// 맨 처음 페이지
			getList(category, sortType, createdate, count, searchWord, challengeType);
		}else{
			// 총 페이지 개수
			var pageTotal = ((challengeCount % challenge == 0) ? Math.round(challengeCount / challenge) : Math.round(challengeCount / challenge) + 1);
			//console.log(challengeCount);

			// 마지막 페이지 까지
			if(page != pageTotal){
				var length = challengeList.length;
				if(length == 0)
					$('#end').hide();
				else
					// 다음 페이지
					getList(category, sortType, challengeList[length-1]['createdate'], challengeList[length-1]['joinVO']['count'], searchWord, challengeType);
			}else				
				$('#end').hide();	// 요소 관찰 종료 (요소 숨김)
		}
		page++;
	})

	observer.observe($listEnd);
}