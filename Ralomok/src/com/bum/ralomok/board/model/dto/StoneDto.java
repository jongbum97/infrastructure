package com.bum.ralomok.board.model.dto;

public class StoneDto {
	private int sequence;
	private int boardNum;
	private int type;
	private int x;
	private int y;
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "StoneDto [sequence=" + sequence + ", boardNum=" + boardNum + ", type=" + type + ", x=" + x + ", y=" + y
				+ "]";
	}
}
