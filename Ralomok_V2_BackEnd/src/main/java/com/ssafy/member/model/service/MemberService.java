package com.ssafy.member.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.ssafy.member.model.MemberDto;

public interface MemberService {

	void joinMember(MemberDto memberDto) throws Exception;
	
}
