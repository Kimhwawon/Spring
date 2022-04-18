package com.ict.persistence;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ict.domain.BoardVO;
import com.ict.domain.SearchCriteria;
import com.ict.mapper.BoardMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	
		@Autowired
		private BoardMapper boardMapper;
		
		// 텍스트용 메서드의 이름은 testGetList입니다.
		// 테스트 코드가 실행될 수 있도록 작성해주세요. 
		@Test
		public void testGetList() {
		//	log.info(boardMapper.getList());
	//		List<BoardVO>result = boardMapper.getList();
	//		log.info("저장된 게시물 정보 : " + result);
		}
		
		// insert를 실행할 테스트코드를 하단에 작성해보겠습니다.
		//@Test
		public void testInsert() {
			// vo를 입력받는 insert 메서드 특성상
			// title, content, writer가 채워진 vo를 먼저 생성해야 합니다. 
			BoardVO vo = new BoardVO();
			// 디버깅
			log.info("채워넣기 전: " + vo);
			
			// 입력할 글에 대한 제목, 글쓴이, 본문을 vo에 넣어줍니다.
			vo.setTitle("새로넣는글");
			vo.setContent("새로넣는 본문");
			vo.setWriter("새로넣는글쓴이");
			
			log.info("채워넣은 후 : " + vo);
			// vo내부에 데이터가 바인딩된걸 확인했으니 메서드 호출
			boardMapper.insert(vo);
			
		}
		
		// select 메서드에 대한 테스트 코드 작성
		//@Test
		public void getSelect() {
			// 가져오기 (글 번호는 두 번째로 큰 번호로 해주세요.)
			BoardVO vo = boardMapper.select(41);
			// 로그 찍기
			log.info(vo);
		}
		
		// delete 메서드에 대한 테스트 코드 작성 후
		// 삭제여부를 sqldeveloper나 상단의 getALlList()로 학인해보세요.
	//	@Test	
		public void delete() {
			boardMapper.delete(21);		 
		}
		
		
		// update 메서드에 대한 테스트 코드를 작성해주신 다음
		// 수정여부를 getAllList()로 확인해보세요.
	//	@Test
		public void testUpdate() {
			BoardVO board = new BoardVO();
			log.info("전달 데이터 아직 입력 안됨 : " + board);
			board.setTitle("바꿀제목");
			board.setContent("바뀐본문");
			board.setBno(3);
			log.info("전달 데이터가 입력된 vo : " + board);
			boardMapper.update(board);
			
		}
		
	//	@Test
		public void testUpdate2() {
			boardMapper.update2("up2로 바꾼제목", "up2로 바꾼 본문", 2);
		}
		
		// 검색어 검색조건 실행 제대로 되는지 여부 테스트
		@Test
		public void testSearchGetList() {
			SearchCriteria cri = new SearchCriteria();
			cri.setKeyword("테스트");
			cri.setSearchType("t");
			
			boardMapper.getList(cri);
		}
		
		
		
		
		
		
		
		
}
