<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/include/head.jsp"%>
<%@include file="/include/nav.jsp"%>

<main>
<section>
	<div class="p-5"></div>
	<div class="mt-5 mx-auto p-5 border border-3 border-success rounded-4"
		style="width: 400px">
		<div class="d-flex justify-content-center fs-2 p-3">마이페이지</div>
		<form id="form-update" action="" method="POST">
			<input type="hidden" name="action" value="update">
			<div class="form-floating m-3">
				<input type="text" class="form-control" id="id"
					placeholder="Enter id" name="id" readonly
					value="${userDto.id}"> <label for="email">ID</label>
			</div>
			<div class="form-floating m-3">
				<input type="password" class="form-control" id="password"
					placeholder="Enter password" name="password"
					value="${userDto.password}"> <label for="pwd">Password</label>
			</div>
			<div class="form-floating m-3">
				<input type="text" class="form-control" id="name"
					placeholder="Enter name" name="name" value="${userDto.name}">
				<label for="email">Name</label>
			</div>
			<div class="form-floating m-3 d-flex justify-content-center">
				<button type="button" class="btn btn-outline-success m-3"
					id="btn-update">수정하기</button>
				<button type="button" class="btn btn-outline-success m-3"
					id="btn-delete">탈퇴하기</button>
			</div>
		</form>
	</div>
</section>
</main>
<script>
	document.querySelector("#btn-update").addEventListener("click", function() {
		if (document.querySelector("#id").value.length<3) {
			alert("아이디를 3자이상 입력해주세요");
			return;
		} else if (document.querySelector("#password").value<3) {
			alert("비밀번호를 3자이상 입력해주세요");
			return;
		} else if (document.querySelector("#name").value<3) {
			alert("이름을 3자이상 입력해주세요");
			return;
		} else {
            if(confirm("수정하시겠습니까?")){
                let form = document.querySelector("#form-update");
                form.setAttribute("action", "${root}/user");
                form.submit();
            }
		}
	});

	document.querySelector("#btn-delete").addEventListener("click", function() {
		if (confirm("정말 탈퇴하시겠습니까?")) {
			location.href = "${root}/user?action=delete";
		}
	});
</script>

<%@include file="/include/footer.jsp"%>