<script setup>
import { useChatStore } from '@/stores/chat';
import {storeToRefs} from 'pinia'
import { ref } from 'vue';

const store = useChatStore();
const {userName, texts, state} = storeToRefs(store);

const message = ref({
    name: userName.value,
    data: ''
});

const send = () => {
    console.log(message.value);
    store.sendData(message);
}
</script>

<template>
    <main class="container p-5 m-5">
        <div class="container border p-5">
            <div>
                <input type="button" value="연결하기" @click="store.connect" v-if="state=='disconnected'">
                <input type="button" value="종료하기" @click="store.disconnect" v-if="state=='connected'">
            </div>
            <div class="mx-5 my-1">
                <h1 class="text-center">채팅방</h1>
            </div>
            <div class="mx-5 my-1 border" style="height: 500px;">
                <div class="m-2" v-for="text in texts">
                    {{ text }}
                </div>
            </div>
            <div class="mx-5 my-1 text-center border">
                <input class="m-2 w-50" type="text" placeholder="채팅내용" v-model="message.data">
                <input class="m-2" type="button" value="전송" @click="send">
            </div>
        </div>
    </main>
</template>

<style scoped>

</style>