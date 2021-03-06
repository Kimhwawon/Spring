package com.ict.mapper;

import java.util.List;

import com.ict.domain.ReplyVO;

public interface ReplyMapper {
	// 특정 게시판 bno번 글의 전체 댓글 목록 가져오기
	// bno번 글에 대한 정보가 있어야함.
	public List<ReplyVO> getList(Long bno); // 불러오기
	
	public void create(ReplyVO vo); // 작성하기
	
	public void update(ReplyVO vo); // 수정하기
	
	// 댓글 삭제시는 단일 댓글 하나만 삭제해야 하므로 댓글번호를 받는다. 
	public void delete(Long rno); // 지우기
	
	// 0502 댓글 번호를 통해 글 번호유추하기
	public Long getBno(Long rno);
	
	// 0503
	// bno번글에 달린 댓글을 다 삭제하는 쿼리문 생성
	public void deleteAllReplies(Long bno);
	

}
