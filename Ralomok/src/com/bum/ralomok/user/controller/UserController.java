package com.bum.ralomok.user.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bum.ralomok.board.model.dto.BoardDto;
import com.bum.ralomok.board.model.service.BoardServiceImpl;
import com.bum.ralomok.user.model.dto.UserDto;
import com.bum.ralomok.user.model.service.UserService;
import com.bum.ralomok.user.model.service.UserServiceImpl;


@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserService userService = UserServiceImpl.getUserService();
	ServletContext application;
	
	@Override
	public void init() {
		application = getServletContext();
		BoardDto boardDto = new BoardDto();
		boardDto.setBoardState(0);
		boardDto.setPlayer1(null);
		boardDto.setPlayer2(null);
		boardDto.setTime(100);
		boardDto.setWatch(new LinkedList<>());
		application.setAttribute("board1", boardDto);
		BoardDto boardDto2 = new BoardDto();
		boardDto2.setBoardState(0);
		boardDto2.setPlayer1(null);
		boardDto2.setPlayer2(null);
		boardDto2.setTime(100);
		boardDto2.setWatch(new LinkedList<>());
		application.setAttribute("board2", boardDto2);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String root = request.getContextPath();
		
		if(action.equals("login")) {								//로그인 하면 id, password, name, state 저장
			UserDto userDto = new UserDto();
			userDto.setId(request.getParameter("id"));
			userDto.setPassword(request.getParameter("password"));
			try {
				userDto = userService.login(userDto);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(userDto!=null) {
				userDto.setState(-1);
				HttpSession session = request.getSession();			//SessionScope에 유저정보 저장
				System.out.println("로그인"+userDto);
				session.setAttribute("userDto", userDto);
				session.setMaxInactiveInterval(60*60*9);
				response.sendRedirect(root+"/index.jsp");			//board.jsp로 redirect
			}else {
				System.out.println("로그인 실패 "+request.getParameter("id"));
				request.getRequestDispatcher("/user/login.jsp?msg=아이디랑 비밀번호를 확인 해주세요").forward(request, response);
			}
			
		}else if(action.equals("logout")) {
			HttpSession session = request.getSession();
			System.out.println("로그아웃 "+(UserDto)session.getAttribute("userDto"));
			session.invalidate();
			request.setCharacterEncoding("utf-8");
			request.getRequestDispatcher("/index.jsp?msg=로그아웃 되었습니다.").forward(request, response);
			
		}else if(action.equals("regist")) {
			UserDto userDto = new UserDto();
			userDto.setId(request.getParameter("id"));
			userDto.setPassword(request.getParameter("password"));
			userDto.setName(request.getParameter("name"));
			try {
				userService.regist(userDto);
				System.out.println("회원가입 "+userDto);
				request.getRequestDispatcher("/user/login.jsp?msg=회원가입 완료").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				request.getRequestDispatcher("/user/login.jsp?msg=사용중인 아이디 입니다.").forward(request, response);
			}
		}else if(action.equals("enter1")) {
			HttpSession session = request.getSession();
			UserDto userDto = (UserDto) session.getAttribute("userDto");
			ServletContext application = getServletContext();
			BoardDto boardDto =(BoardDto) application.getAttribute("board1");
			if(userDto==null) {
				request.setCharacterEncoding("utf-8");
				request.getRequestDispatcher("/index.jsp?msg=로그인 후 이용해 주세요.").forward(request, response);
			}else {
				userDto.setState(0);
				userDto.setRecentStone(-1);
				userDto.setRecentChat(-1);
				userDto.setConnectionTime(new Date().getSeconds());
				session.setAttribute("userDto", userDto);
				boardDto.getWatch().add(userDto);
				application.setAttribute("board1", boardDto);
				System.out.println("1번방 입장 "+userDto);
				BoardDto board2 = (BoardDto)application.getAttribute("board2");
				if(board2.getPlayer1()!=null && board2.getPlayer1().getId().equals(userDto.getId())) board2.setPlayer1(null);
				if(board2.getPlayer2()!=null && board2.getPlayer2().getId().equals(userDto.getId())) board2.setPlayer2(null);
				List<UserDto> watch = board2.getWatch();
				for (int i = 0; i < watch.size(); i++) {
					if(watch.get(i).getId().equals(userDto.getId())) {
						watch.remove(i);
						break;
					}
				}
				board2.setWatch(watch);
				application.setAttribute("board2", board2);
				response.sendRedirect(root+"/board/board1.jsp");
			}
		}else if(action.equals("enter2")) {
			HttpSession session = request.getSession();
			UserDto userDto = (UserDto) session.getAttribute("userDto");
			ServletContext application = getServletContext();
			BoardDto boardDto =(BoardDto) application.getAttribute("board2");
			if(userDto==null) {
				request.setCharacterEncoding("utf-8");
				request.getRequestDispatcher("/index.jsp?msg=로그인 후 이용해 주세요.").forward(request, response);
			}else {
				userDto.setState(0);
				userDto.setRecentStone(-1);
				userDto.setRecentChat(-1);
				userDto.setConnectionTime(new Date().getSeconds());
				session.setAttribute("userDto", userDto);
				boardDto.getWatch().add(userDto);
				application.setAttribute("board2", boardDto);
				System.out.println("2번방 입장 "+userDto);
				BoardDto board1 = (BoardDto)application.getAttribute("board1");
				if(board1.getPlayer1()!=null && board1.getPlayer1().getId().equals(userDto.getId())) board1.setPlayer1(null);
				if(board1.getPlayer2()!=null && board1.getPlayer2().getId().equals(userDto.getId())) board1.setPlayer2(null);
				List<UserDto> watch = board1.getWatch();
				for (int i = 0; i < watch.size(); i++) {
					if(watch.get(i).getId().equals(userDto.getId())) {
						watch.remove(i);
						break;
					}
				}
				board1.setWatch(watch);
				application.setAttribute("board1", board1);
				response.sendRedirect(root+"/board/board2.jsp");
			}
		}else if(action.equals("delete")) {
			HttpSession session = request.getSession();
			UserDto userDto = (UserDto) session.getAttribute("userDto");
			try {
				userService.delete(userDto.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			session.invalidate();
			request.setCharacterEncoding("utf-8");
			request.getRequestDispatcher("/index.jsp?msg=탈퇴가 완료되었습니다.").forward(request, response);
		}else if(action.equals("update")) {
			HttpSession session = request.getSession();
			UserDto userDto = (UserDto) session.getAttribute("userDto");
			UserDto user = new UserDto();
			user.setId(request.getParameter("id"));
			user.setPassword(request.getParameter("password"));
			user.setName(request.getParameter("name"));
			try {
				userService.updateUser(user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			userDto.setId(user.getId());
			userDto.setPassword(user.getPassword());
			userDto.setName(user.getName());
			request.setCharacterEncoding("utf-8");
			request.getRequestDispatcher("/index.jsp?msg=수정이 완료되었습니다.").forward(request, response);
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
