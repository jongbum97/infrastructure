package com.bum.ralomok.util;

public class Renju {
	
	private int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1}, dc = {0, 1, 1, 1, 0, -1, -1, -1};
	private static Renju instance = new Renju();
	private Renju() {};
	
	public static Renju getInstance() {
		return instance;
	}
	
	public int check(int[][] board, int type, int x, int y) {
		if(!three(board, type, x, y)) return 3;
		if(!four(board, type, x, y)) return 4;
		if(!six(board, type, x, y)) return 6;
		return 0;
		
	}
	
	public int maxCount(int[][] board, int type, int x, int y) {
		int max = 0;
		for (int i = 0; i < 4; i++) {
			int count = 1;
			int c = 1;
			while (true) {
				int a = x + dr[i]*c;
				int b = y + dc[i]*c;
				if(a<0||a>18||b<0||b>18) break;
				if(board[a][b]==type) {
					count++; c++;
				}else if(board[a][b]==0) {
					break;
				}else {
					break;
				}
			}
			c = 1;
			while (true) {
				int a = x + dr[i+4]*c;
				int b = y + dc[i+4]*c;
				if(a<0||a>18||b<0||b>18) break;
				if(board[a][b]==type) {
					count++; c++;
				}else if(board[a][b]==0) {
					break;
				}else {
					break;
				}
			}
			max = Math.max(max, count);
		}
		return max;
		
	}

	public boolean six(int[][] board, int type, int x, int y) {
		for (int i = 0; i < 4; i++) {
			int count = 0;
			int c = 1;
			while (true) {
				int a = x + dr[i]*c;
				int b = y + dc[i]*c;
				if(a<0||a>18||b<0||b>18) break;
				if(board[a][b]==type) {
					count++; c++;
				}else if(board[a][b]==0) {
					break;
				}else {
					break;
				}
			}
			c = 1;
			while (true) {
				int a = x + dr[i+4]*c;
				int b = y + dc[i+4]*c;
				if(a<0||a>18||b<0||b>18) break;
				if(board[a][b]==type) {
					count++; c++;
				}else if(board[a][b]==0) {
					break;
				}else {
					break;
				}
			}
			if(count>=5) return false;
		}
		return true;
		
	}

	public boolean four(int[][] board, int type, int x, int y) {
		int[] ans = new int[8];
		
		for (int i = 0; i < 8; i++) {
			int count = 0;
			boolean blank = false;
			int c = 1;
			while (true) {
				int a = x + dr[i]*c;
				int b = y + dc[i]*c;
				if(a<0||a>18||b<0||b>18) break;
				if(board[a][b]==type) {
					count++; c++;
				}else if(board[a][b]==0) {
					if(!blank) {
						blank = true; c++;
					}else {
						break;
					}
				}else {
					break;
				}
			}
			c = 1;
			while (true) {
				int a = x + dr[(i+4)%8]*c;
				int b = y + dc[(i+4)%8]*c;
				if(a<0||a>18||b<0||b>18) break;
				if(board[a][b]==type) {
					count++; c++;
				}else if(board[a][b]==0) {
					if(!blank) blank = true;
					break;
				}else {
					break;
				}
			}
			if(!blank) continue;
			if(count==3) ans[i] = 1;
		}
		
		int cnt = 0;
		for (int i = 0; i < 4; i++) {
			if(ans[i]==1 || ans[i+4]==1) cnt++;
		}
		if(cnt>=2) return false;
		else return true;
	}

	public boolean three(int[][] board, int type, int x, int y) {
		int[] ans = new int[8];
		
		outer : for (int i = 0; i < 8; i++) {
			int count = 0;
			boolean blank = false;
			int c = 1;
			while (true) {
				int a = x + dr[i]*c;
				int b = y + dc[i]*c;
				if(a<0||a>18||b<0||b>18) continue outer;
				if(board[a][b]==type) {
					count++; c++;
				}else if(board[a][b]==0) {
					if(!blank) {
						blank = true; c++;
					}else {
						break;
					}
				}else {
					continue outer;
				}
			}
			c = 1;
			while (true) {
				int a = x + dr[(i+4)%8]*c;
				int b = y + dc[(i+4)%8]*c;
				if(a<0||a>18||b<0||b>18) continue outer;
				if(board[a][b]==type) {
					count++; c++;
				}else if(board[a][b]==0) {
					break;
				}else {
					continue outer;
				}
			}
			if(count==2) ans[i]++;
			else if(count>=3) {
				ans[i] = -1; ans[(i+4)%8] = -1;
			}
		}
		
		int cnt = 0;
		for (int i = 0; i < 4; i++) {
			if(ans[i]==1 || ans[i+4]==1) cnt++;
		}
		if(cnt>=2) return false;
		else return true;
	}


}
