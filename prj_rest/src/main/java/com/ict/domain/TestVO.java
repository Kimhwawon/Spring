package com.ict.domain;

import lombok.Data;

@Data // 순환참조 문제가 있음
public class TestVO {

	private Integer mno; // 멤버넘버
	private String name;
	private Integer age;
	
}
