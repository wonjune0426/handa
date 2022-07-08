// 입력 없이 검색 버튼 클릭 시
document.getElementById("search-form").addEventListener("submit", function(e){
	let search = document.getElementById("search");
	if(search.value.length == 0){
		window.location.href = "/list";
		e.preventDefault();
	}
});