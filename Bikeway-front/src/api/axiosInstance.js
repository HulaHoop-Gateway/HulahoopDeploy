import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "", // Nginx 프록시를 통해 요청 (HTTPS 지원)
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: true,
});

export default axiosInstance;
