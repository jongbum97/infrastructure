package com.bum.ralomok.user.model.service;

import java.sql.SQLException;

import com.bum.ralomok.user.model.dao.UserDao;
import com.bum.ralomok.user.model.dao.UserDaoImpl;
import com.bum.ralomok.user.model.dto.UserDto;

public class UserServiceImpl implements UserService {
	
	private static UserService userService = new UserServiceImpl();
	private UserDao userDao = UserDaoImpl.getUserDao();
	
	private UserServiceImpl() {};
	
	public static UserService getUserService() {
		return userService;
	}
	

	@Override
	public UserDto login(UserDto userDto) throws SQLException {
		return userDao.login(userDto);
	}

	@Override
	public void regist(UserDto userDto) throws SQLException {
		userDao.regist(userDto);
	}

	@Override
	public void delete(String id) throws SQLException {
		userDao.delete(id);
	}

	@Override
	public void updateState(String id, int state) throws SQLException {
		userDao.updateState(id, state);
	}

	@Override
	public int updateRating(UserDto winner, UserDto loser) {
		int p1 = winner.getRating();
		int p2 = loser.getRating();
		double w1 = (1/(Math.pow(10, (p2-p1)/300d)+1));
		int p = (int) (20*(1-w1));
		try {
			userDao.updateRating(winner, p1+p, 1);
			userDao.updateRating(loser, p2-p, -1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
		
	}

	@Override
	public UserDto updateUser(UserDto userDto) throws SQLException {
		return userDao.updateUser(userDto);
	}

}
