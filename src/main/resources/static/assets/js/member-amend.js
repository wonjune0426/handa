$(document).ready(function() {

	/* 	var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content"); */

	function loadSwal(type, text) {
		Swal.fire({
			icon: type,
			text: text
		})
	}

	$(".login-btn-div button").on("click", function(e) {

		e.preventDefault();

		// console.log("버튼 쿨릭 테스트")
		const memberId = $("#member_id").val();
		const memberName = $("#member-name").val();
		const memberCall = $("#member-call").val();
		const memberEmail = $("#e-mail").val();
		const gender = $('input[name=gender]');
		const affiliates = $('select[name=affiliates_id]');
		const position = $('select[name=position_id]');

		// 전화번호 작성여부 확인 & 중간에 '-' 표시 넣기
		if (memberCall == "") {

			loadSwal('error', '전화번호를 작성 해주세요.');

			return;

		}

		// email 작성여부 확인 & 형식에 맞게 작성했는지 확인
		if (memberEmail == "") {

			loadSwal('error', '이메일을 작성 해주세요.');

			return;

		}

		// 회사, 직급 선택여부 확인
		if (affiliates.val() == 0 || position.val() == 0) {

			loadSwal('error',
				'회사/직급 정보를 선택 해주세요.');

			return;

		}

		// 폼 전송
		$(".php-email-form").attr("action", "/member/amend/" + memberId);
		$(".php-email-form").submit();
	});

	$("#ex_filename").change(function() {

		console.log('버튼 동작확인'); // 로그 찍힘

		const uploadFile = $('input[name=uploadFile]')[0].files[0];

		const form = $('#uploadForm')[0];
		const formData = new FormData(form);

		const profileName = $('input[name=profile_name]');
		const profileUUID = $('input[name=profile_uuid]');
		const profilePath = $('input[name=profile_path]');

		formData.append("uploadFile", uploadFile);

		// console.log(uploadFile); // 로그 찍힘
		// console.log(uploadFile.type); // 로그 찍힘

		if (!(uploadFile.type == "image/png"
			|| uploadFile.type == "image/jpeg"
			|| uploadFile.type == "image/jpg" 
			|| uploadFile.type == "image/gif")) {
			loadSwal('error', '이미지 파일만 업로드 하실 수 있습니다.');
			return;
		}

		$.ajax({
			type: "POST",
			url: "http://172.27.0.174:8080/uploadFile",
			data: formData,
			dataType: "json",
			contentType: false,
			processData: false,
			success: function(data) {
				const uploadPath = data.profile_path.replace(/\\/gi, "/");

				const profileURL = "http://172.27.0.174:8080/viewFile?fname="
					+ uploadPath
					+ "/"
					+ data.profile_uuid
					+ "_"
					+ data.profile_name;

				$('.image--cover').attr("src", profileURL);

				profileName.val(data.profile_name);
				profileUUID.val(data.profile_uuid);
				profilePath.val(uploadPath);
			},
			error: function(request, status, error) {
				console.log("code:"
						+ request.status
						+ "\n"
						+ "message:"
						+ request.responseText
						+ "\n"
						+ "error:"
						+ error);
			}
		});
	});

	$(".change-pw").on("click", function(e) {
		e.preventDefault();

		$('.modal').toggleClass('show');
	});

	$(".amend-pw-btn").on("click", function(e) {
		e.preventDefault();
		
		const password = $("#password").val();
		const passwordCheck = $("#password-check").val();
		const checkPWInput = $('.check-password-input');

		if (password == "" || passwordCheck == "" ) {
			checkPWInput.text("빈칸없이 모두 입력해주세요.")
			
			return;
		}
		
		const regPassword = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,13}$/;
		if (!regPassword.test(password)) {
			checkPWInput.text('비밀번호가 조건에 맞지 않습니다. 8~13자 영문, 숫자 조합을 사용하여 입력하세요.');
			
			return;
		}
		
		if (password != passwordCheck) {
			checkPWInput.text("비밀번호와 비밀번호 확인이 일치하지 않습니다.")
			
			return;
		}
		
		
		let timerInterval;

		$.ajax({
			type: "POST",
			url: "/member/change-pw",
			data: {
				'password': $("#password").val(),
				'member_id': $("#member_id").val()
			},
			success: function(data) {
				console.log(data);
				
				Swal.fire({
					icon: 'success',
					title: '변경이 완료되었습니다.',
					timer: 800,
					didOpen: () => {
					timerInterval = setInterval(() => {
						
					}, 100)
				},
				willClose: () => {
					
					clearInterval(timerInterval)
					
					},
				});
				
				/*$('.modal').css('display', 'none');
				$('.modal').attr('aria-hidden', 'true');
				$('body').removeClass('modal-open');*/
				
				$('.modal').modal('hide')
			},
		});
	});

	$('#modal-close').on("click", function(e) {
		$('.modal').toggleClass('show')
	});

});