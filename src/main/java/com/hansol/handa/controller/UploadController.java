package com.hansol.handa.controller;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hansol.handa.domain.UserVO;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@Slf4j
@Controller
public class UploadController {

	@Value("${handa.upload.path}") 
	private String uploadServerPath;

	@PostMapping("/uploadFile")
	@ResponseBody
	public ResponseEntity<UserVO> uploadFile(MultipartFile uploadFile) {

		String uploadFolderPath = getFolder();

		log.info("uploadFolderPath: " + uploadFolderPath);

		// 파일 업로드 할 경로 지정
		File uploadPath = new File(uploadServerPath, getFolder());

		log.info("upload Path: " + uploadPath);

		// 업로드 할 경로에 폴더가 존재하지 않으면 오늘 날짜로 폴더 만들기
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();

		}

		// 업로드 된 파일 정보 확인
		log.info("-------------------------------------");
		log.info("Upload File Name : " + uploadFile.getOriginalFilename());
		log.info("upload File Size : " + uploadFile.getSize());
		log.info("-------------------------------------");

		UserVO fileDTO = new UserVO();

		String uploadFileName = uploadFile.getOriginalFilename();

		fileDTO.setProfile_name(uploadFileName);

		// 고유한 파일명으로 저장
		UUID uuid = UUID.randomUUID();
		uploadFileName = uuid.toString() + "_" + uploadFileName;

		log.info("uuid_uploadFileName: " + uploadFileName);

		try {

			File saveFile = new File(uploadPath, uploadFileName);
			log.info("------------------------------");
			log.info("saveFile: " + saveFile);
			uploadFile.transferTo(saveFile);

			fileDTO.setProfile_uuid(uuid.toString());
			fileDTO.setProfile_path(uploadFolderPath);

			log.info("fileDTO 저장된 값 확인: " + fileDTO);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(fileDTO, HttpStatus.OK);
	}

	// 날짜 폴더 생성
	private String getFolder() {

		log.info("getFolder()-----------------------------------------------------------------------");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		log.info("날짜 : " + str);

		return str.replace("-", File.separator);

	}

	// 업로드한 파일 미리보기
	@GetMapping("/viewFile")
	@ResponseBody
	public ResponseEntity<byte[]> viewFile(String fname) {
		log.info("fname : " + fname);

		File file = new File(uploadServerPath + fname);

		log.info("file : " + file);

		ResponseEntity<byte[]> result = null;

		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
