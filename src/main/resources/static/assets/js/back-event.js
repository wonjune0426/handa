/**
 * 
 */
 
window.onpageshow = function(event) {
	if ( event.persisted || (window.performance && window.performance.navigation.type == 2)) {
		// 이전 페이지의 url
		var referrer = document.referrer;
		window.location.href = referrer;
	}
}