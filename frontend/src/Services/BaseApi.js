import axios from "axios";

const BASE_URL = process.env.VUE_APP_BACKEND_API_URL;
const api = axios.create({
    baseURL: BASE_URL
});

export default api;
