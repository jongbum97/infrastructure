package com.bum.ralomok.user.model.service;

import java.sql.SQLException;

import com.bum.ralomok.user.model.dto.UserDto;

public interface UserService {
	
	UserDto login(UserDto userDto) throws SQLException;
	
	void regist(UserDto userDto) throws SQLException;
	
	void delete(String id) throws SQLException;
	
	void updateState(String id, int state) throws SQLException;
	
	int updateRating(UserDto winner, UserDto loser);
	
	UserDto updateUser(UserDto userDto) throws SQLException;
}