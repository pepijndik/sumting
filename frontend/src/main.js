import Vue, {createApp} from 'vue'
import App from './App.vue'
import VueClickAway from "vue3-click-away";
import Router from "@/Router/router";
import '@/Assets/css/index.css';
const app = createApp(App);
app.use(VueClickAway);
app.use(Router);
app.mount('#app')
