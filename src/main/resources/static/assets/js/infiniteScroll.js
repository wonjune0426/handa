var challengeList = null;
var challengeCount = null;

function getList(element, category, sortType, createdate, count){

	$.ajax({
		type : 'GET',
		url : '/challenge-list',
		dataType : 'json',
 		data:{
			category: category, 
			sortType: sortType,
			createdate : createdate,
			count : count,
			searchWord : "search"
		},  
		contentType : 'application/json',
		success: function(res){
			challengeList = res['challengeList'];
			challengeCount = res['challengeCount'];
			
			const subCategoryName = res['subCategoryName'];

			var data = "";
			data += "<div class='row gy-4'>";
				
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
			data += "<div id ='end-list' class='row gy-4'></div>";
				 
			$(element).html(data);
			$(element).attr('id', 'list-container');	
		},
		error: function(){
			alert('실패!');
		}
	});
}

function infiniteScroll(category, sortType, createdate, count, searchWord){
	var page = 0;
	var challenge = 12;
	
	const $listEnd = document.querySelector('#end');
	const observer = new IntersectionObserver((entries) => {
		if(page < 1){
			getList("#list-container", category, sortType, createdate, count, searchWord);
		}else{
			var pageTotal = (challengeCount % challenge == 0 ? challengeCount / challenge : challengeCount / challenge + 1);
			
			if(page != pageTotal){
				var length = challengeList.length;
				
				getList("#end-list", category, sortType, challengeList[length-1]['createdate'], challengeList[length-1]['joinVO']['count'], searchWord);
			}else				
				$('#end').hide();
		}
		page++;
	})

	observer.observe($listEnd);
}