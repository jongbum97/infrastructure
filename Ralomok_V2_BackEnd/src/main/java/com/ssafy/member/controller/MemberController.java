package com.ssafy.member.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;
import com.ssafy.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;

@RestController
//@Controller
@RequestMapping("/user")
@Api(tags = { "MemberController" })
@Slf4j
public class MemberController {

//	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	@Value("${file.path}")
	private String uploadPath;
	
	private MemberService memberService;
	
	private JWTUtil jwtUtil;
	

	public MemberController(MemberService memberService, JWTUtil jwtUtil) {
		super();
		this.memberService = memberService;
		
		this.jwtUtil = jwtUtil;
	}
	

	@ApiOperation(value = "회원가입", notes = "회원의 정보를 받아 처리.")
	@PostMapping("/regist")
	public ResponseEntity<?> regist(@RequestBody MemberDto memberDto) {
		log.debug("memberDto : {}", memberDto);
		try {
			memberService.joinMember(memberDto);
			log.debug("회원가입 성공");
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

}
