package com.ssafy.member.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.model.MemberDto;

@Mapper
public interface MemberMapper {

	void joinMember(MemberDto memberDto) throws SQLException;
	
}
