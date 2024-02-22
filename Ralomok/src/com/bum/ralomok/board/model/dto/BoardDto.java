package com.bum.ralomok.board.model.dto;

import java.util.List;

import com.bum.ralomok.user.model.dto.ChatDto;
import com.bum.ralomok.user.model.dto.UserDto;

public class BoardDto {
	
	private int boardState;
	private int turn;
	private int time;
	private UserDto player1;
	private UserDto player2;
	private List<UserDto> watch;
	private List<ChatDto> chat;
	private List<StoneDto> stone;
	private int rollback;
	private int result;
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getRollback() {
		return rollback;
	}
	public void setRollback(int rollback) {
		this.rollback = rollback;
	}
	public int getBoardState() {
		return boardState;
	}
	public void setBoardState(int boardState) {
		this.boardState = boardState;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public UserDto getPlayer1() {
		return player1;
	}
	public void setPlayer1(UserDto player1) {
		this.player1 = player1;
	}
	public UserDto getPlayer2() {
		return player2;
	}
	public void setPlayer2(UserDto player2) {
		this.player2 = player2;
	}
	public List<UserDto> getWatch() {
		return watch;
	}
	public void setWatch(List<UserDto> watch) {
		this.watch = watch;
	}
	public List<ChatDto> getChat() {
		return chat;
	}
	public void setChat(List<ChatDto> chat) {
		this.chat = chat;
	}
	public List<StoneDto> getStone() {
		return stone;
	}
	public void setStone(List<StoneDto> stone) {
		this.stone = stone;
	}
	@Override
	public String toString() {
		return "BoardDto [boardState=" + boardState + ", turn=" + turn + ", time=" + time + ", player1=" + player1
				+ ", player2=" + player2 + ", watch=" + watch + ", chat=" + chat + ", stone=" + stone + ", rollback="
				+ rollback + "]";
	}
	

	
}
