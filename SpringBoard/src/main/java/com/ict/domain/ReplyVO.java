package com.ict.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class ReplyVO {
	
	private Long rno;
	private Long bno;
	private String reply;
	private String replysr;
	private Date replyDate;
	private Date updateDate;

}
