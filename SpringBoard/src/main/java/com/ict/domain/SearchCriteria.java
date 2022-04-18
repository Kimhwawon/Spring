package com.ict.domain;

import lombok.Data;

@Data
public class SearchCriteria extends Criteria {
	
	private String searchType; //조회타입
	private String keyword; // 조회키워드

}
