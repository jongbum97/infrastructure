var url = "http://jongbum.site:8080/board2";

var interval = setInterval(()=>sendFetch("load"), 500);
var recentX = -1;
var recentY = -1;

function read(data){
    if(data.player1){
        document.getElementById("p1Name").innerHTML = data.player1.name;
        document.getElementById("p1in").setAttribute("value", "나가기");
    }else{
        document.getElementById("p1Name").innerHTML = "empty";
        document.getElementById("p1in").setAttribute("value", "입장");
    }
    if(data.player2){
        document.getElementById("p2Name").innerHTML = data.player2.name;
        document.getElementById("p2in").setAttribute("value", "나가기");
    }else{
        document.getElementById("p2Name").innerHTML = "empty";
        document.getElementById("p2in").setAttribute("value", "입장");
    }

    if(data.turn){
        var p1 = document.getElementById("player1");
        var p2 = document.getElementById("player2");
        if(data.turn==1){
            p1.setAttribute("class","m-1 my-2 text-center row border border-2 border-danger");
            p2.setAttribute("class","m-1 my-2 text-center row border");
        }else if(data.turn==2){
            p1.setAttribute("class","m-1 my-2 text-center row border");
            p2.setAttribute("class","m-1 my-2 text-center row border border-2 border-danger");
        }
    }
    for (let i = 0; i < 19; i++) {
        for (let j = 0; j < 19; j++) {
            var id = i+","+j;
            document.getElementById(id).setAttribute("class","stone rounded-circle p-0 m-0 w-100 h-100");
        }
    }
    if(data.stone){
        data.stone.forEach(element => {
            recentX = element.x;
            recentY = element.y;
            var type = element.type;
            var id = recentX+","+recentY;
            if(type==1) document.getElementById(id).style.backgroundColor = "black";
            else if(type==2) document.getElementById(id).style.backgroundColor = "white";
            else if(type==3) {
                recentX = -1;
                recentY = -1;   
                document.getElementById(id).style.backgroundColor = "";
            };
        });
    }
    if(data.boardState==0 || data.rollback!=0){
		recentX = -1;
		recentY = -1;
	}
    if(data.boardState==4 && recentX!=-1 && recentY!=-1){
		var recent = recentX+","+recentY;
		document.getElementById(recent).setAttribute("class", "stone rounded-circle p-0 m-0 w-100 h-100 border border-primary");
	}

    if(data.watch){
        var s = "";
        data.watch.forEach(element => {
        	s += `<div class="small" id="user" name="${element.name}" win="${element.win}" lose="${element.lose}" rating="${element.rating}">${element.name}(${element.rating})</div>`
        });
        document.getElementById("see").innerHTML = s;
    }

    if(data.chat){
        data.chat.forEach(element => {
            var chat = document.createElement('div');
            chat.innerHTML = element.name+" : "+element.content;
            var chats = document.getElementById("chats");
            chats.appendChild(chat);
            chats.scrollTop = chats.scrollHeight-chats.clientHeight;
        });
    }

    if(data.time){
        var time = Math.round(data.time/2);
        document.getElementById("time").innerHTML = `${time}초  <div class="p-1 m-1 ms-2 me-5 rounded-pill bg-primary" style="width:${time/50*85}%"></div>`;
    }

    if(data.boardState==3){
        if(data.time==0) sendFetch("start");
        alerts(Math.round(data.time/2)+"초 후에 게임이 시작됩니다", 1000, 50);
    }else if(data.boardState==4){
        if(data.time==0) sendFetch1("win","no", ((data.turn==1)? 2:1));
    }else if(data.boardState==1){
        sendFetch1("win","no", "1");
    }else if(data.boardState==2){
        sendFetch1("win","no", "2");
    }else if(data.boardState==5){
        if(data.player1 && data.player2){
            if(data.turn==1){
                alerts(`${data.player1.name}승리!!<br>${Math.round(data.time/2)}초 후에 게임이 종료됩니다<br>${data.player1.name}(${data.player1.rating}) +${data.result}<br>${data.player2.name}(${data.player2.rating}) -${data.result}`, 1000, 120);
            }else if(data.turn==2){
                alerts(`${data.player2.name}승리!!<br>${Math.round(data.time/2)}초 후에 게임이 종료됩니다<br>${data.player1.name}(${data.player1.rating}) -${data.result}<br>${data.player2.name}(${data.player2.rating}) +${data.result}`, 1000, 120);
            }
        }
        if(data.time==0) {
            setTimeout(()=>sendFetch("restart"), 500);
            setTimeout(()=>location.reload(), 1000);
        }
    }

    if(data.rollback==3){
        if(confirm("무르기 요청을 승락하시겠습니까?")){
            sendFetch("rollback");
        }
    }else if(data.rollback==103){
        alerts("금수(3x3) 입니다.", 1000, 50);
    }else if(data.rollback==104){
        alerts("금수(4x4) 입니다.", 1000, 50);
    }else if(data.rollback==106){
        alerts("금수(장목) 입니다.", 1000, 50);
    }
    
    document.querySelectorAll("#user").forEach(element => {
        element.addEventListener('click', function(){
            var name = this.getAttribute("name");
            var win = this.getAttribute("win");
            var lose = this.getAttribute("lose");
            var rating = this.getAttribute("rating");
        
            alerts(` 이름 : ${name} &nbsp;&nbsp;&nbsp;레이팅 : ${rating}<br> 승 : ${win} &nbsp;&nbsp;&nbsp;패 : ${lose}`, 3000, 70)
        })
    });
}

document.getElementById("p1in").addEventListener("click", function(){
    sendFetch("p1");
})

document.getElementById("p2in").addEventListener("click", function(){
    sendFetch("p2");
})

document.getElementById("rollback").addEventListener("click", function(){
    if(confirm("무르기 요청을 보내시겠습니까?")){
        sendFetch("rollbackRequest");
    }
})

document.getElementById("giveup").addEventListener("click", function(){
    if(confirm("정말 기권하시겠습니까?")){
        sendFetch("giveup");
    }
})

document.getElementById("chatting").addEventListener("click", function(){
    var content = document.getElementById("content").value;
    sendFetch1("chatting","content",content);
    document.getElementById("content").value = "";
})
document.getElementById('content').addEventListener('keydown',function(event){
    if(event.key=="Enter"){
        var content = document.getElementById("content").value;
        sendFetch1("chatting","content",content);
        document.getElementById("content").value = "";
    }
});











    var stones = document.querySelectorAll(".stone");
    stones.forEach(element => {
        element.addEventListener("mouseover",function(){
            if(element.style.backgroundColor=="") element.style.backgroundColor = "gray";
        })
        element.addEventListener("mouseout",function(){
            if(element.style.backgroundColor=="gray") element.style.backgroundColor = "";
        })
        element.addEventListener("click",function(){
            if(element.style.backgroundColor=="black"||element.style.backgroundColor=="white") return;
            var position = element.getAttribute("id").split(",");
            var x = position[0];
            var y = position[1];
            sendFetch2("put","x",x,"y",y);
        })
    });



function alerts(s, time, height){
    var ch = document.createElement('div');
    var wid = (window.innerWidth/2)-150;
    ch.setAttribute("class","border border-danger border-2 rounded-3 text-center p-2 shadow")
    ch.setAttribute("style","position:absolute; width:250px; height:"+height+"px; left:"+wid+"px; top:150px; background-color:white")
    ch.innerHTML = s;
    document.getElementById("board").appendChild(ch);
    setTimeout(()=> ch.remove(), time);
}

function sendFetch2(action, name1, value1, name2, value2){
    fetch(url+"?action="+action+"&"+name1+"="+value1+"&"+name2+"="+value2 , {
            credentials: "include"
        })
        .then(response => response.json())
        .then(data => read(data));
}
function sendFetch1(action, name1, value1){
    fetch(url+"?action="+action+"&"+name1+"="+value1 , {
            credentials: "include"
        })
        .then(response => response.json())
        .then(data => read(data));
}
function sendFetch(action){
    fetch(url+"?action="+action , {
            credentials: "include"
        })
        .then(response => response.json())
        .then(data => read(data));
}