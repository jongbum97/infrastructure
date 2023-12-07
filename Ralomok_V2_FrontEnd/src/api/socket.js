// const {BACKEND_SOCKET_URL} = import.meta.env;

let socket = null;

function open(onOpen, onMessage, onClose, onError){
    socket = new WebSocket('ws://localhost:8080/game');

    socket.onopen = onOpen;
    
    socket.onmessage = onMessage;
    
    socket.onclose = onClose;
    
    socket.onerror = onError;

}

function close(){
    socket.close();
}

function send(object){
    socket.send(JSON.stringify(object));
}



export { open, close, send };
