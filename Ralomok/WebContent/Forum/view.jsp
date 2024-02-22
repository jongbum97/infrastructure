<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
<%@include file="/include/head.jsp" %>
<%@include file="/include/nav.jsp" %>

	<div class="m-5 p-3"> </div>
	
		<main class="row justify-content-center">
			<div class="col-lg-8 col-md-10 col-sm-12">
				<h2 class="my-3 py-3 shadow-sm bg-light text-center">
					<mark class="sky">글보기</mark>
				</h2>
			</div>
			<div class="col-lg-8 col-md-10 col-sm-12">
				<div class="row my-2">
					<h2 class="text-secondary px-5">${articles.articleNo}.
						${articles.subject}</h2>
				</div>
				<div class="row">
					<div class="col-md-8">
						<div class="clearfix align-content-center">
							<img class="avatar me-2 float-md-start bg-light p-2"
								src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
							<p>
								<span class="fw-bold">${articles.userId}</span> <br /> <span
									class="text-secondary fw-light"> ${articles.registerTime}
									조회 : ${articles.hit}
								</span>
							</p>
						</div>
					</div>
					<div class="col-md-4 align-self-center text-end">댓글 : 17</div>
					<div class="divider mb-3"></div>
					<div class="text-secondary">
						${articles.content}
					</div>
					<div class="divider mt-3 mb-3"></div>
					<div class="d-flex justify-content-end">
						<button type="button" id="btn-list"
							class="btn btn-outline-primary mb-3">글목록</button>
						<button type="button" id="btn-mv-modify"
							class="btn btn-outline-success mb-3 ms-1">글수정</button>
						<button type="button" id="btn-delete"
							class="btn btn-outline-danger mb-3 ms-1">글삭제</button>
					</div>
				</div>
			</div>
		</main>
	
<%@include file="/include/footer.jsp" %>