<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/include/head.jsp"%>
<%@include file="/include/nav.jsp"%>

<main>
<section>
	<div class="container p-5"></div>
	<div class="mt-5 mx-auto p-5 border border-3 border-success rounded-4"
		style="width: 400px">
		<form id="form-login" action="" method="POST">
			<input type="hidden" name="action" value="login">
			<div class="form-floating m-3">
				<input type="text" class="form-control" id="id"
					placeholder="Enter id" name="id"> <label for="email">ID</label>
			</div>
			<div class="form-floating m-3">
				<input type="password" class="form-control" id="password"
					placeholder="Enter password" name="password"> <label
					for="pwd">Password</label>
			</div>
			<div class="form-floating m-3 d-flex justify-content-center">
				<button type="button" class="btn btn-outline-success m-3"
					id="btn-login">로그인</button>
				<button type="button" class="btn btn-outline-success m-3"
					onclick="window.location.href='${root}/index.jsp'">이전으로</button>
			</div>
		</form>
	</div>
</section>
<section class="container" style="height: 300px;"></section>
</main>

<script>
	document.querySelector("#btn-login").addEventListener("click", function() {
		if (!document.querySelector("#id").value) {
			alert("아이디 입력!!");
			return;
		} else if (!document.querySelector("#password").value) {
			alert("비밀번호 입력!!");
			return;
		} else {
			let form = document.querySelector("#form-login");
			form.setAttribute("action", "${root}/user");
			form.submit();
		}
	});
</script>

<%@include file="/include/footer.jsp"%>