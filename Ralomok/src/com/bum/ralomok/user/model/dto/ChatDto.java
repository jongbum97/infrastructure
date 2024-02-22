package com.bum.ralomok.user.model.dto;

public class ChatDto {
	private static int cnt;
	private int sequence;
	private int boardNum;
	private String name;
	private String content;
	
	public ChatDto(){
		this.sequence = ++cnt;
	}
	
	public int getSequence() {
		return sequence;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "ChatDto [sequence=" + sequence + ", boardNum=" + boardNum + ", name=" + name + ", content=" + content + "]";
	}
	
}
