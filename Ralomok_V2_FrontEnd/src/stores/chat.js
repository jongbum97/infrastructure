import { ref, computed} from 'vue'
import { open, close, send } from '../api/socket';
import { defineStore } from 'pinia'

export const useChatStore = defineStore('chat', () => {
  
    const userName = ref('');
    const texts = ref([]);
    const state = ref('disconnected')

    function connect(){
        open(
            // onOpen
            (object)=>{
                console.log('연결성공')
            },
            // onMessage
            ({data})=>{
                console.log(data)
                texts.value.push(data)
            },
            // onClose
            () => {
                console.log('연결종료')
            },
            // onError
            (error)=>{
                console.log(error)
            }
        );
        state.value = 'connected';
    }

    function sendData(object){
        send(object);
    }

    function disconnect(){
        close();
        state.value = 'disconnected';
    }

  return { userName, texts, state, connect, sendData, disconnect }
})
