package com.ict.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j //pom.xml에서 junit 4.12로, log4j는 1.2.17버전, exclusions태그 삭제, scope 주석
public class UploadController {
	
	// 썸네일인지 아닌지
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
					
			return contentType.startsWith("image");
		}catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private String getFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date(); //java.util
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
		
	}
	
	
	@GetMapping("uploadForm")
	public void uploadForm() {
	
		log.info("upload form");
	}
	
	@PostMapping(value="/uploadFormAction", 
				produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void uploadFormPost(MultipartFile[] uploadFile) {
		
		// AttachFileDTO는 파일 한 개의 정보를 저장합니다.
		// 현재 파일 업로드는 여러 파일을 동시에 업로드하므로 List<AttachFileDTO>를 받도록 합니다.
		List<AttachFileDTO> list = new ArrayList<>();
		
		// 어떤 폴더에 저장할것인지 위치지정
		String uploadFolder = "C:\\upload_data\\temp";
		
		// 폴더생성 (폴더 유무체크)
				File uploadPath = new File(uploadFolder, getFolder());
				log.info("upload path : " + uploadPath);
				
				if(uploadPath.exists() == false) {
					uploadPath.mkdirs();
				}
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("---------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("last file name : " + uploadFileName);
			
			// uuid 발급 부분
			UUID uuid = UUID.randomUUID();
						
			uploadFileName = uuid.toString() + "-" + uploadFileName;
		
			
			
		//	File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
		//	File saveFile = new File(uploadPath,uploadFileName);
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				// 이 아래부터 썸네일 생성로직
				if(checkImageType(saveFile)) {
					FileOutputStream thumbnail = 
							new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();					
				}
			} catch(Exception e) {
				log.error(e.getMessage());
			}			
		} // end for
		
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("ajax post update!");
		
		String uploadFolder = "C:\\upload_data\\temp";
		
		// 폴더생성 (폴더 유무체크)
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("upload path : " + uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("-------------------");
			log.info("Upload file name : " + multipartFile.getOriginalFilename());
			log.info("Upload file siza : " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			log.info("last file name : " + uploadFileName);
			
			// uuid 발급 부분
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "-" + uploadFileName;
			
		//	File saveFile = new File(uploadFolder, uploadFileName);
		//	File saveFile = new File(uploadPath, uploadFileName);
			

			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				// 이 아래부터 썸네일 생성로직
				if(checkImageType(saveFile)) {
					FileOutputStream thumbnail = 
							new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();					
				}
			} catch(Exception e) {
				log.error(e.getMessage());
			}			
		} // end for
		
	}
	


}
