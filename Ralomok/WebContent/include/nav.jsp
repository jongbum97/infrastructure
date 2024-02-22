<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<body>

	<nav class="navbar navbar-expand navbar-light bg-light shadow fixed-top">
		<div class="container-md">
			<a class="navbar-brand" href="${root}/index.jsp"> <img
				class="d-inline-block align-text-center"
				src="${root}/assets/img/ralo.png" width="80" height="60" alt="Logo" />
				<strong>Ralomok</strong>
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#mynavbar">
				<span class="navbar-toggler-icon">
				</span>
			</button>
			<div class="collapse navbar-collapse" id="mynavbar">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link"
						href="${root}/user?action=enter1">1번방</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${root}/user?action=enter2">2번방</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${root}/">게시판</a></li>
				</ul>
				<c:if test="${empty userDto}">
					<ul class="navbar-nav ms-auto" id="mynavbar">
						<li class="nav-item"><a class="nav-link"
							href="${root}/user/login.jsp">로그인</a></li>
						<li class="nav-item"><a class="nav-link"
							href="${root}/user/regist.jsp">회원가입</a></li>
					</ul>
				</c:if>
				<c:if test="${!empty userDto}">
					<ul class="navbar-nav ms-auto" id="mynavbar">
						<li class="nav-item"><a class="nav-link">${userDto.name}님
								반갑습니다</a></li>
						<li class="nav-item"><a class="nav-link"
							href="${root}/user?action=logout">로그아웃</a></li>
						<li class="nav-item"><a class="nav-link"
							href="${root}/user/update.jsp">마이페이지</a></li>
					</ul>
				</c:if>
			</div>
		</div>
	</nav>