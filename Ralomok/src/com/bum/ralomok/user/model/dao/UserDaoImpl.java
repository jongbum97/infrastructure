package com.bum.ralomok.user.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bum.ralomok.user.model.dto.UserDto;
import com.bum.ralomok.util.DBUtil;

public class UserDaoImpl implements UserDao {
	
	private static UserDao userDao = new UserDaoImpl();
	private DBUtil dbUtil = DBUtil.getInstance();
	
	private UserDaoImpl() {};
	
	public static UserDao getUserDao() {
		return userDao;
	}
	

	@Override
	public UserDto login(UserDto userDto) throws SQLException {
		String sql = " select * from user where id=? and password=?;";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDto user = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userDto.getId());
			pstmt.setString(2, userDto.getPassword());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new UserDto();
				user.setId(rs.getString("id"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setWin(Integer.parseInt(rs.getString("win")));
				user.setLose(Integer.parseInt(rs.getString("lose")));
				user.setRating(Integer.parseInt(rs.getString("rating")));
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return user;
	}

	@Override
	public void regist(UserDto userDto) throws SQLException {
		String sql = " insert into user (id, password, name) value (?, ?, ?);";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userDto.getId());
			pstmt.setString(2, userDto.getPassword());
			pstmt.setString(3, userDto.getName());
			pstmt.executeUpdate();
			
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public void delete(String id) throws SQLException {
		String sql = " delete from user where id=?;";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}


	@Override
	public void updateState(String id, int state) throws SQLException {
		String sql = " update user set state=? where id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, state);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public void updateRating(UserDto userDto, int rating, int result) throws SQLException {
		String sql= "";
		if(result>0) sql = " update user set win = win+1, rating=? where id=?";
		else sql = " update user set lose = lose+1, rating=? where id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rating);
			pstmt.setString(2, userDto.getId());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public UserDto updateUser(UserDto userDto) throws SQLException {
		String sql = " update user set password=?, name=? where id=?;";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(3, userDto.getId());
			pstmt.setString(1, userDto.getPassword());
			pstmt.setString(2, userDto.getName());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
		return userDto;
	}

}
