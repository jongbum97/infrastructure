package com.bum.ralomok.board.model.service;

import java.sql.SQLException;
import java.util.List;

import com.bum.ralomok.board.model.dto.BoardDto;
import com.bum.ralomok.board.model.dto.StoneDto;
import com.bum.ralomok.user.model.dto.ChatDto;

public interface BoardService {

	List<StoneDto> loadNextSequence(int boardNum, int sequence) throws SQLException;

	StoneDto deleteSequence(int boardNum) throws SQLException;

	int insertSequence(StoneDto StoneDto) throws SQLException;
	
	void endGame(int boardNum) throws SQLException;
	
	List<ChatDto> loadNextChat(int boardNum, int sequence);
	
	void insertChat(ChatDto chatDto);
	
	void deleteChat(int boardNum);
		
	void deleteStone(int boardNum, int x, int y);
}
