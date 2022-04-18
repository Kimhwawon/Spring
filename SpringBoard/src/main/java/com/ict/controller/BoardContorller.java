package com.ict.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ict.domain.BoardVO;
import com.ict.domain.Criteria;
import com.ict.domain.PageMaker;
import com.ict.domain.SearchCriteria;
import com.ict.mapper.BoardMapper;
import com.ict.service.BoardService;

import lombok.extern.log4j.Log4j;

// 컨트롤러가 컨트롤러 기능을 할 수 있도록 처리해주세요.
@Controller
@Log4j
public class BoardContorller {
	
	// 컨트롤러는 Service만 호출하도록 구조를 바꿉니다.
	// Service를 BoardController 내부에서 쓸 수 있도록 선언/주입 해주세요. 
	@Autowired
	private BoardService service; // (실제 주입되는 요소는 
	
	
	// 전체 회원을 보려면, 회원목록을 들고오는 메서드를 실행해야 하고
	// 그러면, 그 메서드를 보유하고 있는 클래스를 선언하고 주입해줘야 합니다.
	// DB접근시 사용하는 BoardMapper를 선언하고 주입해주세요.
	// 참고) BoardMapperTests.java
	
	//@Autowired
	//private BoardMapper boardMapper; // 얘가 잇어야 db에 접근 가능
	
	// 전체 글 목록을 볼 수 있는 페이지인 boardList.jsp로 연결되는
	// boardList 주솔르 get방식으로 선언해주세요. 
	// 메서드 내부에서는 boardMapper의 .getAllList를 호출해 그 결과를 바인딩합니다.
	@GetMapping("/boardList")
	// @RequestParam(name="사용할변수명", defaultValue="지정하고싶은기본값") 변수 왼쪽에 저렇게 붙여주면 처리완료.
	// @PathVariable의 경우 defaultValue를 직접 줄 수 없으나, required=false를 이용해 필수입력을 안받게 처리한 후
	// 컨트롤러 내부에서 디폴트값을 인력해 줄수 있다. 
	// 기본형 자료는 null을 저장할 수 없기 때문에 wrapper class를 이용해 Long을 선언합니다.
	public String boardList(SearchCriteria cri,  Model model) {
	//	if(pageNum == null) {
		//	pageNum = 1L; // Long형은 숫자 뒤에 L을 붙여야 대입됩니다. 
		//}
		// model.addAttribute("바인딩이름",바인딩 자료)
		List<BoardVO> boardList = service.getList(cri);
		
		
		
		log.info("넘어온 글 관련 정보 목록 : " +boardList);
		model.addAttribute("boardList",boardList );
		
		// 버튼 처리를 위해 추가로 페이지메이커 생성 및 세팅
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri); // cri입력
		int countPage = service.countPageNum(cri); // 131대신 실제로 DB내 글 개수를 받아옴
		pageMaker.setTotalBoard(countPage); //calcData()호출도 되면서 순식간에 prev, next, startPage, endPage 세팅
		model.addAttribute("pageMaker", pageMaker);
		return "boardList";	
	}
	
	
	// 글 하나만 조회할 수 있는 디테일 페이지인 boardDetail.jsp로 연결되는
	// boardDetail 주소를 get방식으로 선언해주세요.
	// 주소 뒤에 ?bno=번호 형식으로 적힌 번호 글만 조회합니다.
	@GetMapping(value="/boardDetail/{bno}")
	public String boardDetail(@PathVariable long bno, Model model) {
		BoardVO board = service.select(bno);
		model.addAttribute("board",board );
		return "boardDetail";
	}
	
	
	// insert페이지를 위한 폼으로 연결되는 컨트롤러를 먼저 만들겠습니다.
	// get방식으로 /boardInsert 주소를 접속시 form페이지로 연결됩니다. 
	// 폼 페이지의 이름은 boardForm.jsp입니다. 
	@GetMapping(value="/boardInsert")
	public String boardInsertForm() {
		return"boardForm";
	}
	// boardInsert인데 post 방식을 처리하는 메서드를 새로 만들어주세요
	// BoardVO를 입력받도록 해 주시면 실제로는 BoardVO의 멤버변수명으로 들어오는 자료를 입력받습니다. 
	// 입력받은 BoardVO를 토대로 mapper쪽의 insert 메서드를 실행해 주시고
	// 리다이렉트는 return "redirect:/목적지 주소" 형식으로 리턴구문을 작성하면 됩니다.
	// boardList로 돌려보내주세요
	@PostMapping(value="/boardInsert")
	public String boardInsert(BoardVO board) {
		// 폼에서 날린 데이터 들어오는지 디버깅
		log.info("들어온 데이터 디버깅 : " + board);
		// insert로직 실행
		service.insert(board);
		return "redirect:/boardList";
	}
	
	
	// 글삭제 로직은 post방식으로 진행합니다.
	// /boardDelete주소로 처리하고
	// bno를 받아서 해당 글을 삭제합니다.
	// 글 삭제 버튼은 detail페이지 하단에 form으로 만들어서 bno를 hidden으로 전달하는
	// submit버튼을 생성해서 처리하게 해주세요. 
	@PostMapping(value="/boardDelete")
	public String boardDelete(long bno, SearchCriteria cri, RedirectAttributes rttr) {
		log.info("pageNum 정보 :" + cri.getPageNum());
		log.info("SearchType 정보 :" + cri.getSearchType());
		log.info("Keyword 정보 :" + cri.getKeyword());
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		// 리다이렉트 주소에 페이지번호, 검색조건, 키워드 전달
		// rttr.addAllAttributes(Hashmap); 으로 전달시 한 번만 호출해 데이터를 전달할 수 있습니다. 
	//	Map<String, Object> parameters = new HashMap<>();
	//	parameters.put("pageNum", cri.getPageNum());
	//	parameters.put("searchType", cri.getSearchType());
	//	parameters.put("keyword", cri.getKeyword());
	//	log.info("전달 직전 : " + parameters);
	//	 rttr.addAllAttributes(parameters);
		// 삭제로직 실행
		service.delete(bno);
		return "redirect:/boardList";
	}
	
	
	
	// /boardUpdateForm를 post방식으로 접속하는 form연결 메서드를 만들겠습니다.
	// update로직은 이미 데이터가 입력이 되어 있어야 합니다.
	// 따라서 내가 수정하고자 하는 글의 정보를 VO로 받아온다음 
	// 폼 페이지에 포워딩해서 기입해놔야 합니다.
	// 폼페이지 이름은 boardUpdateForm.jsp 입니다. 
	@PostMapping(value="/boardUpdateForm")
	public String boardUpdateForm( long bno, Model model) {
		BoardVO board = service.select(bno);
			model.addAttribute("board", board);
		return "boardUpdateForm";
	}
	// boardUpdate를 post방식으로 접속하는 메소드를 만들겠습니다.
	// update(BoardVO)를 실행해서, 폼에서 날려준 데이터를 토대로
	// 해당 글의 내용이 수정되도록 만들어주시면 됩니다.
	// 수정 후에는 수정요청이 들어온 글 번호의 디테일페이지로 리다이렉트 시켜주세요.
	@PostMapping(value="/boardUpdate")
											// keyword, searchType, pageNum을 받기 위해 선언
	public String boardUpdate(BoardVO board, SearchCriteria cri, RedirectAttributes rttr ) {
		// SearchCriteria가 제대로 받아오는지 체크
		log.info("수정로직입니다." + board);
		log.info("검색 키워드 : " + cri.getKeyword());
		log.info("검색조건 :" + cri.getSearchType());
		log.info("진입 페이지번호 :" + cri.getPageNum());
		log.info("전체 데이터통합 조회 : " + cri);
		// 리다이렉트시 주소창 뒤에 파라미터 쿼리스트링 형식으로 붙이기
		// rttr.addAttribute("파라미터명",전달자료);
		// 는 호출되면면 redirect 주소 뒤에 파라미터를 붙여줍니다.
		// rttr.addFlashAttribute()는 넘어간 페이지에서 파라미터를
		// 쓸 수 있도록 전달하는것으로 둘의 역할이 다르니 주의 하세요. 
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		//  update호출
		service.update(board);
		// redirect:주소?글번=getter
		return "redirect:/boardDetail/" + board.getBno();
	}
	
}












