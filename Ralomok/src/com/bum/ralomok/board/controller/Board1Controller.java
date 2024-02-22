package com.bum.ralomok.board.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bum.ralomok.board.model.dto.BoardDto;
import com.bum.ralomok.board.model.dto.StoneDto;
import com.bum.ralomok.board.model.service.BoardService;
import com.bum.ralomok.board.model.service.BoardServiceImpl;
import com.bum.ralomok.user.model.dto.ChatDto;
import com.bum.ralomok.user.model.dto.UserDto;
import com.bum.ralomok.user.model.service.UserService;
import com.bum.ralomok.user.model.service.UserServiceImpl;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@WebServlet("/board1")
public class Board1Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext application;
	BoardService boardService;
	
	@Override
	public void init() {
		application = getServletContext();
		boardService = BoardServiceImpl.getBoardService();
		try {
			boardService.endGame(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
       
	protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String root = request.getContextPath();
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		if(userDto==null) return;
		BoardDto boardDto =(BoardDto) application.getAttribute("board1");
		boardDto.setChat(new LinkedList<>());
		boardDto.setStone(new LinkedList<>());
		
		if("load".equals(action)) {
			if(boardDto.getBoardState()!=0) {
				if(boardDto.getPlayer1()!=null && boardDto.getPlayer1().getId().equals(userDto.getId())) {
					boardDto.setTime(boardDto.getTime()-1);
				}else if(boardDto.getPlayer1()==null &&  boardDto.getPlayer2().getId().equals(userDto.getId())){
					boardDto.setTime(boardDto.getTime()-1);
				}
			}
			List<StoneDto> list = null;
			try {
				list = boardService.loadNextSequence(1, userDto.getRecentStone());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(list!=null) {
				for (int i = 0; i < list.size(); i++) {
					userDto.setRecentStone(list.get(i).getSequence());
				}
			}
			boardDto.setStone(list);
			List<ChatDto> chats = boardService.loadNextChat(1, userDto.getRecentChat());
			if(chats.size()!=0) {
				for (int i = 0; i < chats.size(); i++) {
					userDto.setRecentChat(chats.get(i).getSequence());
				}
			}
			boardDto.setChat(chats);
			if(boardDto.getRollback()==3) boardDto.setRollback(0);
			if(boardDto.getRollback()!=0) {
				if(boardDto.getPlayer1()!=null && boardDto.getPlayer1().getId().equals(userDto.getId()) && boardDto.getRollback()==1) {
					boardDto.setRollback(3);
				}else if(boardDto.getPlayer2()!=null && boardDto.getPlayer2().getId().equals(userDto.getId()) && boardDto.getRollback()==2) {
					boardDto.setRollback(3);
				}
			}
			if(boardDto.getBoardState()==5) {
				try {
					int p = userDto.getRecentChat();
					int q = userDto.getRecentStone();
					userDto = UserServiceImpl.getUserService().login(userDto);
					userDto.setRecentChat(p);
					userDto.setRecentStone(q);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(boardDto.getBoardState()!=3 && boardDto.getBoardState()>0) {
				if(boardDto.getPlayer1()==null || boardDto.getPlayer2()==null) {
					boardDto.setBoardState(5);
					boardDto.setTime(0);
				}
			}
		}else if("put".equals(action)) {
			if(boardDto.getBoardState()!=4) return;
			int x = Integer.parseInt(request.getParameter("x"));
			int y = Integer.parseInt(request.getParameter("y"));
			StoneDto stoneDto = new StoneDto();
			stoneDto.setBoardNum(1);
			stoneDto.setX(x);
			stoneDto.setY(y);
			int result = -2;
			if(boardDto.getPlayer1().getId().equals(userDto.getId()) && boardDto.getTurn()==1) {
				stoneDto.setType(1);
				try {
					result = boardService.insertSequence(stoneDto);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(result==3) boardDto.setRollback(103);
				else if(result==4) boardDto.setRollback(104);
				else if(result==6) boardDto.setRollback(106);
				else if(result==-1) boardDto.setBoardState(1);
				else if(result==-2) {}
				else {
					boardDto.setTurn(2);
					boardDto.setTime(100);
				}
			}else if(boardDto.getPlayer2().getId().equals(userDto.getId()) && boardDto.getTurn()==2) {
				stoneDto.setType(2);
				try {
					result = boardService.insertSequence(stoneDto);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(result==-1) boardDto.setBoardState(2);
				else if(result==-2) {}
				else {
					boardDto.setTurn(1);
					boardDto.setTime(100);
				}
			}
			if(boardDto.getRollback()>100) {
				session.setAttribute("userDto", userDto);
				application.setAttribute("board1", boardDto);
				ObjectMapper mapper = new ObjectMapper();
				response.setCharacterEncoding("UTF-8");
				try {
					mapper.writeValue(response.getWriter(), boardDto);
				}catch (Exception e) {
					e.printStackTrace();
				}
				boardDto.setRollback(0);
				application.setAttribute("board1", boardDto);
				return;
			}
		}else if("p1".equals(action)) {
			if(!(boardDto.getBoardState()==0||boardDto.getBoardState()==3)) return;
			if(boardDto.getPlayer2()!=null && boardDto.getPlayer2().getId().equals(userDto.getId())) return;
			UserService userService = UserServiceImpl.getUserService();
			if(boardDto.getPlayer1()==null) {
				boardDto.setPlayer1(userDto);
			}else if(boardDto.getPlayer1().getId().equals(userDto.getId())) {
				boardDto.setPlayer1(null);
				boardDto.setBoardState(0);
			}
			if(boardDto.getPlayer1()!=null&&boardDto.getPlayer2()!=null) {
				boardDto.setBoardState(3);
				boardDto.setTime(11);
			}
		}else if("p2".equals(action)) {
			if(!(boardDto.getBoardState()==0||boardDto.getBoardState()==3)) return;
			if(boardDto.getPlayer1()!=null && boardDto.getPlayer1().getId().equals(userDto.getId())) return;
			UserService userService = UserServiceImpl.getUserService();
			if(boardDto.getPlayer2()==null) {
				boardDto.setPlayer2(userDto);
			}else if(boardDto.getPlayer2().getId().equals(userDto.getId())) {
				boardDto.setPlayer2(null);
				boardDto.setBoardState(0);
			}
			if(boardDto.getPlayer1()!=null&&boardDto.getPlayer2()!=null) {
				boardDto.setBoardState(3);
				boardDto.setTime(11);
			}
		}else if("start".equals(action)) {
			if(boardDto.getBoardState()==4) return;
			System.out.println("1번방 게임 시작!");
			boardDto.setBoardState(4);
			boardDto.setResult(0);
			boardDto.setTurn(1);
			boardDto.setTime(100);
			boardDto.setRollback(0);
		}else if("win".equals(action)) {
			if(boardDto.getBoardState()==5) return;
			int no = Integer.parseInt(request.getParameter("no"));
			UserService userService = UserServiceImpl.getUserService();
			int point = 0;
			if(no==1) {
				point = userService.updateRating(boardDto.getPlayer1(), boardDto.getPlayer2());
			}else if(no==2) {
				point = userService.updateRating(boardDto.getPlayer2(), boardDto.getPlayer1());
			}
			System.out.println("1번방 "+no+"번 플레이어 승리");
			boardDto.setResult(point);
			boardDto.setTurn(no);
			boardDto.setBoardState(5);
			boardDto.setTime(21);
		}else if("restart".equals(action)) {
			if(boardDto.getBoardState()==0) return;
			try {
				boardService.endGame(1);
				boardService.deleteChat(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			boardDto.setBoardState(0);
			boardDto.setPlayer1(null);
			boardDto.setPlayer2(null);
		}else if("chatting".equals(action)) {
			String content = request.getParameter("content");
			ChatDto chatDto = new ChatDto();
			chatDto.setBoardNum(1);
			chatDto.setContent(content);
			chatDto.setName(userDto.getName());
			boardService.insertChat(chatDto);
			System.out.println(chatDto);
		}else if("giveup".equals(action)) {
			if(boardDto.getBoardState()!=4) return;
			if(boardDto.getPlayer1().getId().equals(userDto.getId())) {
				boardDto.setBoardState(2);
			}else if(boardDto.getPlayer2().getId().equals(userDto.getId())) {
				boardDto.setBoardState(1);
			}
		}else if("rollback".equals(action)) {
			if(boardDto.getBoardState()!=4) return;
			int cnt = 0;
			if(boardDto.getPlayer1().getId().equals(userDto.getId())) {
				if(boardDto.getTurn()==1) {
					cnt=1; boardDto.setTurn(2);
				}
				else if(boardDto.getTurn()==2) {
					cnt=2; boardDto.setTurn(2);
				}
			}else if(boardDto.getPlayer2().getId().equals(userDto.getId())) {
				if(boardDto.getTurn()==1) {
					cnt=2; boardDto.setTurn(1);
				}
				else if(boardDto.getTurn()==2) {
					cnt=1; boardDto.setTurn(1);
				}
			}
			for (int i = 0; i < cnt; i++) {
				try {
					boardService.deleteSequence(1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}else if("rollbackRequest".equals(action)) {
			if(boardDto.getBoardState()!=4) return;
			if(boardDto.getPlayer1().getId().equals(userDto.getId())) {
				boardDto.setRollback(2);
			}else if(boardDto.getPlayer2().getId().equals(userDto.getId())) {
				boardDto.setRollback(1);
			}
		}
		userDto.setConnectionTime(new Date().getSeconds());
		List<UserDto> list = boardDto.getWatch();
		int sec = new Date().getSeconds();
		boolean check = false;
		for (int i = 0; i < list.size(); i++) {
			UserDto user = list.get(i);
			if(user.getId().equals(userDto.getId())) {
				if(check) {
					System.out.println("1번방 관전자 제거");
					list.remove(i--);
				}else {
					list.remove(i);
					list.add(i,userDto);
					check = true;
				}
			}else if (sec>10 && Math.abs(user.getConnectionTime()-sec)>10) {
				System.out.println("시간강퇴 "+user);
				if(boardDto.getPlayer1()!=null && boardDto.getPlayer1().getId().equals(user.getId())&&boardDto.getBoardState()==4) {
					boardDto.setBoardState(5);
					boardDto.setPlayer1(null);
				}
				if(boardDto.getPlayer2()!=null && boardDto.getPlayer2().getId().equals(user.getId())&&boardDto.getBoardState()==4) {
					boardDto.setBoardState(5);
					boardDto.setPlayer2(null);
				}
				list.remove(i);
			}
		}
		boardDto.setWatch(list);
		session.setAttribute("userDto", userDto);
		application.setAttribute("board1", boardDto);
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		try {
			mapper.writeValue(response.getWriter(), boardDto);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
