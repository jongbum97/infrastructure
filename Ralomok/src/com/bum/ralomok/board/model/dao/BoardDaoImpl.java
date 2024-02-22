package com.bum.ralomok.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.bum.ralomok.board.model.dto.BoardDto;
import com.bum.ralomok.board.model.dto.StoneDto;
import com.bum.ralomok.board.model.service.BoardServiceImpl;
import com.bum.ralomok.user.model.dto.ChatDto;
import com.bum.ralomok.util.DBUtil;

public class BoardDaoImpl implements BoardDao {

	private static BoardDao boardDao = new BoardDaoImpl();
	private List<ChatDto> list = new LinkedList<>();
	
	private DBUtil dbUtil = DBUtil.getInstance();

	public static BoardDao getBoardDao() {
		return boardDao;
	}
	

	@Override
	public List<StoneDto> loadNextSequence(int boardNum, int sequence) throws SQLException {
		List<StoneDto> list = new ArrayList<>();
		String sql = " select * from stone where sequence > ? and boardNum=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sequence);
			pstmt.setInt(2, boardNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				StoneDto stoneDto = new StoneDto();
				stoneDto.setSequence(rs.getInt("sequence"));
				stoneDto.setBoardNum(rs.getInt("boardNum"));
				stoneDto.setType(rs.getInt("type"));
				stoneDto.setX(rs.getInt("x"));
				stoneDto.setY(rs.getInt("y"));
				list.add(stoneDto);
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public StoneDto deleteSequence(int boardNum) throws SQLException {
		String sql1 = " select * from stone where boardNum=? and type!=3 order by sequence desc limit 1;";
		String sql2 = " delete from stone where sequence=?;";
		String sql3 = " insert into stone (boardNum, type, x, y) value ( ?, ?, ?, ?);";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StoneDto stoneDto = new StoneDto();
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				stoneDto.setSequence(rs.getInt("sequence"));
				stoneDto.setBoardNum(rs.getInt("boardNum"));
				stoneDto.setType(3);
				int x = rs.getInt("x");
				int y = rs.getInt("y");
				if(boardNum==1) BoardServiceImpl.getBoardService().deleteStone(1, x, y);
				else if(boardNum==2) BoardServiceImpl.getBoardService().deleteStone(2, x, y);
				stoneDto.setX(x);
				stoneDto.setY(y);
			}
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, stoneDto.getSequence());
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = conn.prepareStatement(sql3);
			pstmt.setInt(1, boardNum);
			pstmt.setInt(2, 3);
			pstmt.setInt(3, stoneDto.getX());
			pstmt.setInt(4, stoneDto.getY());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
		return stoneDto;
	}

	@Override
	public void insertSequence(StoneDto stoneDto) throws SQLException {
		String sql = " insert into stone (boardNum, type, x, y) value ( ?, ?, ?, ?);";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stoneDto.getBoardNum());
			pstmt.setInt(2, stoneDto.getType());
			pstmt.setInt(3, stoneDto.getX());
			pstmt.setInt(4, stoneDto.getY());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}


	@Override
	public void endGame(int boardNum) throws SQLException {
		String sql = " delete from stone where boardNum=?;";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}


	@Override
	public List<ChatDto> loadNextChat(int boardNum, int sequence) {
		List<ChatDto> list2 = new LinkedList<>();
		for (int i = 0; i < list.size(); i++) {
			ChatDto chatDto = list.get(i);
			if(chatDto.getSequence()>sequence &&chatDto.getBoardNum()==boardNum) {
				list2.add(chatDto);
			}
		}
		return list2;
	}


	@Override
	public void insertChat(ChatDto chatDto) {
		list.add(chatDto);
	}


	@Override
	public void deleteChat(int boardNum) {
		for (int i = list.size()-1; i >=0; i--) {
			if(list.get(i).getBoardNum()==boardNum) list.remove(i);
		}
	}

}
