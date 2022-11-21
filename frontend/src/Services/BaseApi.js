import axios from "axios";

const api = axios.create({
    baseURL: process.env.VUE_APP_BACKEND_API_URL,
    timeout: 1000
});
export default api;