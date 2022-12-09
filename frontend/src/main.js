import Vue, {createApp} from 'vue'
import App from './App.vue'
import VueClickAway from "vue3-click-away";
import Router from "@/Router/router";
import '@/Assets/css/index.css';
import CKEDITOR from '@ckeditor/ckeditor5-vue';
import SimpleWebWorker from 'simple-web-worker'
const app = createApp(App);
import BaseApi from "@/Services/BaseApi";
app.provide('axios', BaseApi)
app.use(CKEDITOR)
app.config.globalProperties.$worker = SimpleWebWorker;
app.use(VueClickAway);
app.use(Router);
app.mount('#app')
