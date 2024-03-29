import Vue, {createApp} from 'vue'
import App from './App.vue'
import VueClickAway from "vue3-click-away";
import Router from "@/Router/router";
import '@/Assets/css/index.css';
import CKEDITOR from '@ckeditor/ckeditor5-vue';
import SimpleWebWorker from 'simple-web-worker'

import ToastPlugin from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';

import BaseApi from "@/Services/BaseApi";

const app = createApp(App);

app.provide('axios', BaseApi)
app.use(CKEDITOR)
app.use(ToastPlugin);
app.config.globalProperties.$worker = SimpleWebWorker;

app.use(VueClickAway);
app.use(Router);
app.mount('#app')

