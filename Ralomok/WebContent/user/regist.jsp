<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/include/head.jsp"%>
<%@include file="/include/nav.jsp"%>

<main>
<section>
	<div class="p-5"></div>
	<div class="mt-5 p-5 mx-auto border border-3 border-success rounded-4"
		style="width: 400px">
		<div class="d-flex justify-content-center fs-2 p-3">회원가입</div>
		<form id="form-regist" action="" method="POST">
			<input type="hidden" name="action" value="regist">
			<div class="form-floating m-3">
				<input type="text" class="form-control" id="id"
					placeholder="Enter email" name="id"> <label for="email">ID</label>
			</div>
			<div class="form-floating m-3">
				<input type="password" class="form-control" id="password"
					placeholder="Enter password" name="password"> <label
					for="pwd">Password</label>
			</div>
			<div class="form-floating m-3">
				<input type="text" class="form-control" id="name"
					placeholder="Enter email" name="name"> <label for="email">Name</label>
			</div>

			<div class="form-floating m-3 d-flex justify-content-center">
				<button type="button" class="btn btn-outline-success m-3"
					id="btn-regist">회원가입</button>
				<button type="button" class="btn btn-outline-success m-3"
					onclick="window.location.href='${root}/index.jsp'">이전으로</button>
			</div>
		</form>
	</div>
</section>
<div class="container p-5"></div>
</main>
<script>
	document.querySelector("#btn-regist").addEventListener("click", function() {
		if (document.querySelector("#id").value.length<3) {
			alert("아이디를 3자이상 입력해주세요");
			return;
		} else if (document.querySelector("#password").value.length<3) {
			alert("비밀번호를 3자이상 입력해주세요");
			return;
		} else if (document.querySelector("#name").value.length<3) {
			alert("이름을 3자이상 입력해주세요");
			return;
		} else {
			let form = document.querySelector("#form-regist");
			form.setAttribute("action", "${root}/user");
			form.submit();
		}
	});
</script>

<%@include file="/include/footer.jsp"%>