// 입력 없이 검색 버튼 클릭 시
document.getElementById("search-form").addEventListener("submit", function(e){
	let search = document.getElementById("search");
	if(search.value.length == 0){
		alert('검색어를 입력해주세요');
		e.preventDefault();
	}
});