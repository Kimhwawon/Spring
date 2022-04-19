package com.ict.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.domain.ReplyVO;
import com.ict.service.ReplyService;

@RestController
@RequestMapping("/replies") // 접속시 기본주소replies가 기본적으로 붙음
public class ReplyController {

	@Autowired
	private ReplyService service;

		// consumes(소비)는 이 메서드의 파라미터를 넘겨줄때 어떤 형식으로 념겨줄지
		// 를 설정하는데 기본적으로 rest방식에서는 json을 사용합니다.
		// produeces는 입력받은 데이터를 토대로 로직을 실행한 다음
		// 사용자에게 결과로 보여줄 데이터의 형식(즉, 리턴자료형)을 나타냅니다.
		// 아래 메서드는 json을 사용하므로 무조건 jackson-databind 가 추가해야 작동
		@PostMapping(value="", consumes="application/json", produces= {MediaType.TEXT_PLAIN_VALUE})
		// PRODUCES에 TEXT_PLAIN_VALUE를 줬으므로 String 리턴
		public ResponseEntity<String> register(
				// rest컨트롤러에서 받는 파라밈터 앞에 @RequestBody 어노테이션이 붙어야 consumes와 연결됨
				@RequestBody ReplyVO vo){
			// 깡통 entity를 먼저 생성 (에러 나는 경우랑 안 나는 경우를 대비해서 빈 ResponseEntity를 셍성함)
			ResponseEntity<String>entity = null;
			try {
				// 먼저 글쓰기 로직 실행 후 에러가 없다면...
				service.addReply(vo);
				// 문제없이 다음줄로 넘어왔다면 성공했을때 화면에 띄울 ResponseEntity 생성
				entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
												  // 성공했을때 뜨는 문구 (한글음 깨짐)
			}catch(Exception e) {
				// catch로 넘어왔다라는건 글쓰기 로직에 문제가 생긴 상황
				entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}                                      // 에러메세지를띄어라
			// 위의 try블럭이나 catch블럭에서 얻어온 entity변수 리턴
			return entity;
		}

	
		
		
		// 전체 글 목록을 요청하는 list 메서드
		@GetMapping(value="/all/{bno}",
				// 단일 숫자데이터 bno만 넣기도 하고
				// PathVariable 어노테이션으로 이미 입력데이터가
				// 명시되었으므로 consumes는 따로 주지 않아도 됩니다.
				// produces는 댓글 목록이 xml로도, json으로도 표현될수 있도록
				// 아래와 같이 2개를 모두 얹습니다.
				// jackson-dataformat-xml을 추가해야 xml도 작동합니다. 
				produces = {MediaType.APPLICATION_XML_VALUE,
							MediaType.APPLICATION_JSON_UTF8_VALUE})
		public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno")Long bno){
			  // 리턴자료무조건(사실상디폴트)꺽소ㅣ사이에 리턴할 자료
			
			ResponseEntity<List<ReplyVO>> entity = null;
			
			try {
				entity = new ResponseEntity<>(service.listReply(bno), HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace(); // 이게 있어야 에러를 콘솔에 찍을수 있음
				entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return entity;
		}
		
}
