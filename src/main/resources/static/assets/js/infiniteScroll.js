/**
 * 
 */
/*
const $container = document.querySelector(".card-container")
let itemIndex = 3

// 무한 스크롤을 위한 옵저버
const lastObserver = new IntersectionObserver((entries) => {
  const lastItem = entries[0]
  if (!lastItem.isIntersecting) {
    return
  }
  // 2. 마지막 요소가 나타났다면, fetchItem 함수로 추가적인 아이템을 불러옵니다.
  else {
    fetchItem()
    // 3. 기존 마지막 요소의 인터섹션 감지를 해제하고, 새롭게 생성된 마지막 요소에의 인터섹션 여부를 감지합니다. 
    lastObserver.unobserve(lastItem.target)
    lastObserver.observe(document.querySelector('.card:last-child'))
  }
})

// 1. 처음 한번 마지막 요소의 인터섹션 여부를 감지합니다.
lastObserver.observe(document.querySelector('.card:last-child'))

// 요소 추가를 위한 함수
const fetchItem = () => {
  for (let index = itemIndex; index < itemIndex + 2; index++) {
    const newElement = document.createElement("div")
    $container.append(newElement)
  }
  itemIndex += 2
}
*/

// onload는 페이지의 모든 리소스가 로드 완료되었을 때 실행된다.
/*
window.onload = () => {
    const observerOption = {
        root: null,
        rootMargin: "0px 0px 30px 0px",
        threshold: 0.2
    }
    // IntersectionObserver 인스턴스 생성
    const io = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
			// entry와 observer 출력
			console.log('entry:', entry);
    		console.log('observer:', observer);
            // entry.isIntersecting: 특정 요소가 뷰포트와 20%(threshold 0.2) 교차되었으면
            if (entry.isIntersecting) {
                entry.target.src = entry.target.dataset.src
                observer.unobserve(entry.target)    // entry.target에 대해 관찰 종료
            }
        })
    }, observerOption)
    // lazy-img 클래스 요소 순회
    const lazyImgs = document.querySelectorAll(".card")
    lazyImgs.forEach(el => {
        io.observe(el)  // el에 대하여 관측 시작
    })
}
*/
/*
const $list = document.querySelector('.card-container');
const itemArr = new Array(21).fill().map((_, index) => index+1);
let [lastIndex, page] = [0,1];
const io = new IntersectionObserver(((entries, observer) => {
  entries.forEach(entry=>{
    if(entry.isIntersecting){
      if(Number(entry.target.dataset.index)===lastIndex){
        render();
      }
      observer.unobserve(entry.target);
      console.log(entry.target,'is unobserve');

    }
  })
}));

render();

    function render(){
        $list.innerHTML += itemArr.slice(lastIndex,lastIndex+6).map((item,index)=>`<div class="item page${page}" data-index="${index+1+lastIndex}">${item}</div>`).join('');
        lastIndex+=6;

        [...document.querySelectorAll(`.page${page}`)].forEach(elem=> {
            if(Number(elem.dataset.index)===lastIndex) io.observe(elem);
        });
        page+=1;
        console.log(lastIndex-6,'~',lastIndex,'items render');
    }
*/
//var categoryID;
//var sortTypeID;

function getList(category, sortType, createdate, count){
	//categoryID = category;
	//sortTypeID = sortType;
	
	$.ajax({
		type : 'GET',
		url : '/challenge-list',
		dataType : 'json',
 		data:{
			category: category, 
			sortType: sortType,
			createdate : createdate,
			count : count
		},  
		contentType : 'application/json',
		success: function(res){
			const challengeList = res['challengeList'];
			const subCategoryName = res['subCategoryName'];

			var data = "";
						
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
				data += "<p id='card-p'> 참여인원 " + challengeList[0]['joinVO']['count'] + "명 </p>";
							
				data += "<div class='state'>";
				data += "<div class='open-dt'>" + challengeList[i]['createdate'].split(" ", 1) + " 생성 </div>"; 
				data +=	"<div class='state-label'>" + challengeList[i]['challenge_state'] + "</div>";
				data += "</div>";
								
				data += "<div id ='endlist'></div>";
				data += "</div>";
							
				data += "</div>";
				data += "</div>"; 

			}
			
			$("#list-container").html(data);		
		},
		error: function(){
			alert('실패!');
		}
	});
}

//window.onload = function(){
	//const $listEnd = document.querySelector('#end-list');
	//const observer = new IntersectionObserver(entries => {
	//	getList(categoryID, sortTypeID, "2022-07-04 11:19:14", '1');
	//})

	//observer.observe($listEnd)
//}