package com.bum.ralomok.board.model.service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;

import com.bum.ralomok.board.model.dao.BoardDao;
import com.bum.ralomok.board.model.dao.BoardDaoImpl;
import com.bum.ralomok.board.model.dto.BoardDto;
import com.bum.ralomok.board.model.dto.StoneDto;
import com.bum.ralomok.user.model.dto.ChatDto;
import com.bum.ralomok.util.Renju;

public class BoardServiceImpl implements BoardService {
	
	private static BoardService boardService = new BoardServiceImpl();
	private BoardServiceImpl() {
		board1 = new int[19][19];
		board2 = new int[19][19];
	};
	private BoardDao boardDao = BoardDaoImpl.getBoardDao();
	int[][] board1;
	int[][] board2;
	
	public static BoardService getBoardService() {
		return boardService;
	}
	

	@Override
	public List<StoneDto> loadNextSequence(int boardNum, int sequence) throws SQLException {
		return boardDao.loadNextSequence(boardNum, sequence);
	}

	@Override
	public StoneDto deleteSequence(int boardNum) throws SQLException {
		return boardDao.deleteSequence(boardNum);
	}

	@Override
	public int insertSequence(StoneDto stoneDto) throws SQLException {
		Renju renju = Renju.getInstance();
		int n = stoneDto.getBoardNum();
		int x = stoneDto.getX();
		int y = stoneDto.getY();
		int type = stoneDto.getType();
		
		if(n==1) {
			if(board1[x][y]!=0) return -2;
			if(type==1) {
				int result = renju.check(board1, type, x, y);
				if(result>0) return result;
				board1[x][y] = type;
				boardDao.insertSequence(stoneDto);
				int count = renju.maxCount(board1, type, x, y);
				if(count==5) return -1;
			}else if(type==2) {
				board1[x][y] = type;
				boardDao.insertSequence(stoneDto);
				int count = renju.maxCount(board1, type, x, y);
				if(count>=5) return -1;
			}
		}else if(n==2){
			if(board2[x][y]!=0) return -2;
			if(type==1) {
				int result = renju.check(board2, type, x, y);
				if(result>0) return result;
				board2[x][y] = type;
				boardDao.insertSequence(stoneDto);
				int count = renju.maxCount(board2, type, x, y);
				if(count==5) return -1;
			}else if(type==2) {
				board2[x][y] = type;
				boardDao.insertSequence(stoneDto);
				int count = renju.maxCount(board2, type, x, y);
				if(count>=5) return -1;
			}
		}
		return 0;
	}

	@Override
	public void endGame(int boardNum) throws SQLException {
		if(boardNum==1) {
			board1 = new int[19][19];
		}else if(boardNum==2) {
			board2 = new int[19][19];
		}
		boardDao.endGame(boardNum);
	}


	@Override
	public List<ChatDto> loadNextChat(int boardNum, int sequence) {
		return boardDao.loadNextChat(boardNum, sequence);
	}


	@Override
	public void insertChat(ChatDto chatDto) {
		boardDao.insertChat(chatDto);
	}


	@Override
	public void deleteChat(int boardNum) {
		boardDao.deleteChat(boardNum);
	}
	
	public void deleteStone(int boardNum, int x, int y) {
		if(boardNum==1) {
			board1[x][y] = 0;
		}else if(boardNum==2) {
			board2[x][y] = 0;
		}
	}




}
