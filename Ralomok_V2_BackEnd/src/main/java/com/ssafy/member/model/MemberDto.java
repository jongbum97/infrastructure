package com.ssafy.member.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

	private String userId;
	private String userPwd;
	private String emailId;
	private String emailDomain;
	private String joinDate; 

}
