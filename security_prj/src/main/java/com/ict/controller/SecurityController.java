package com.ict.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ict.domain.MemberVO;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/secu/*")
@Controller
public class SecurityController {
	
//	@Autowired
//	private SecurityService service;
	
	@Autowired
	private PasswordEncoder pwen;
	
	// 비회원
	@PreAuthorize("permitAll")
	@GetMapping("/all")
	public void doAll() {
		log.info("모든사람이 접속 가능한 all 로직");
	}
	
	// 회원
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	@GetMapping("/member")
	public void doMember() {
		log.info("회원들이 접속 가능한 member 로직");
	}
	
	// 운영자
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("운영자만 접속 가능한 admin 로직");
	}
	
	
	//화원가입
	@PreAuthorize("permitAll")
	@GetMapping("/join")
	public void joinForm() {
		log.info("회원가입창 접속");
	}
	
	@PreAuthorize("permitAll")
	@PostMapping("/join")
	public void join(MemberVO vo, String[] role) {
		log.info("가입시 받는 데이터들 : " + vo);
		for(String r : role) {
		log.info(r);
		
	}
	}
}
