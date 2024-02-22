<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/include/head.jsp"%>
<%@include file="/include/nav.jsp"%>

<div class="p-5"></div>
<div class="row m-auto p-5 py-0 my-0" style="width:1200px">
	<div class="p-1 mx-auto row" id="time" style="width:700px">0초  <div class="p-1 m-1 ms-2 me-5 rounded-pill bg-primary" style="width:0%"></div></div>
</div>
<main class="row m-auto p-5 py-0" style="width:1200px">
	<div class="my-0 border p-1 mx-auto" style="width:200px;height:586px">
		<div class="row m-1 p-0">
			<div class="m-1 my-2 text-center border row" id="player1">
				<div class="col-7">
					<div class="">흑 <i class="bi bi-circle-fill"></i></div>
					<div class="" id="p1Name">empty</div>
				</div>
				<div class="col-4 py-2" id="p1">
					<input class="btn btn-outline-success py-1 px-2" type="button" id="p1in" value="입장">
				</div>
			</div>
			<div class="m-1 my-2 text-center border row" id="player2">
				<div class="col-7">
					<div class="">백 <i class="bi bi-circle"></i></div>
					<div class="" id="p2Name">empty</div>
				</div>
				<div class="col-4 py-2" id="p2">
					<input class="btn btn-outline-success py-1 px-2" type="button" id="p2in" value="입장">
				</div>
			</div>
			<div class="m-1 my-2 py-2 px-1 text-center border row" id="player2">
				<div class="m-auto col pe-1"><input class="btn btn-outline-primary py-1 px-2" id="rollback" type="button" value="무르기"></div>
				<div class="m-auto col ps-1"><input class="btn btn-outline-primary py-1 px-2" id="giveup" type="button" value="기권"></div>
			</div>
			<div class="m-1 my-2 text-center border" id="watch">
				<div>접속자</div>
				<div class="text-center" id="see" style="height:330px">
				</div>
			</div>
		</div>
	</div>

	<div class="mx-auto border my-0"
		style="width:586px; height:586px; background-image:url('${root}/assets/img/오목판.jpg'); background-size : 95%;background-repeat : no-repeat;background-position:center;padding:7px">

		<c:forEach var="i" begin="0" end="18" step="1">
			<div class="row m-0 p-0" id="board">
				<c:forEach var="j" begin="0" end="18" step="1">
					<div class="col m-0" style="width: 30px; height: 30px; padding: 3px">
						<div class="stone rounded-circle p-0 m-0 w-100 h-100"
							id="${i},${j}"></div>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</div>

	<div class="border p-1 mx-auto my-0 " style="width:300px;height:586px">
		<div class="row m-1 p-0">
			<div class="m-1 text-center"> 채팅창 </div>
			<div class="m-1 p-1 border" id="chats" style="overflow:auto; width: 290px; height: 450px;">
	
			</div>
			<input class="m-1" id="content" type="text" style="width:210px"><button id="chatting" style="width:50px" class="m-1">전송</button>
		</div>
	</div>

</main>


<script src="${root}/assets/js/board2.js"></script>

<%@include file="/include/footer.jsp"%>