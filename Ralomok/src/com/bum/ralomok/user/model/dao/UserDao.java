package com.bum.ralomok.user.model.dao;

import java.sql.SQLException;

import com.bum.ralomok.user.model.dto.UserDto;

public interface UserDao {

	UserDto login(UserDto userDto) throws SQLException;
	
	void regist(UserDto userDto) throws SQLException;
	
	void delete(String id) throws SQLException;
		
	void updateState(String id, int state) throws SQLException;

	void updateRating(UserDto userDto, int rating, int result) throws SQLException;
	
	UserDto updateUser(UserDto userDto) throws SQLException;
}
