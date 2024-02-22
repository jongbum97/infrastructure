package com.bum.ralomok.user.model.dto;

public class UserDto {
	
	private String id;
	private String password;
	private String name;
	private int state;
	private int recentStone;
	private int recentChat;
	private int win;
	private int lose;
	private int rating;
	private int connectionTime;
	
	
	public int getConnectionTime() {
		return connectionTime;
	}
	public void setConnectionTime(int connectionTime) {
		this.connectionTime = connectionTime;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getLose() {
		return lose;
	}
	public void setLose(int lose) {
		this.lose = lose;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getRecentStone() {
		return recentStone;
	}
	public void setRecentStone(int recentStone) {
		this.recentStone = recentStone;
	}
	public int getRecentChat() {
		return recentChat;
	}
	public void setRecentChat(int recentChat) {
		this.recentChat = recentChat;
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", password=" + password + ", name=" + name + ", state=" + state + ", recentStone="
				+ recentStone + ", recentChat=" + recentChat + ", win=" + win + ", lose=" + lose + ", rating=" + rating
				+ ", connectionTime=" + connectionTime + "]";
	}
	

	
	

	
	
}
