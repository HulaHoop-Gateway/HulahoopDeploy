import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "", // Vite proxy를 사용하므로 relative path
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: true,
});

export default axiosInstance;
