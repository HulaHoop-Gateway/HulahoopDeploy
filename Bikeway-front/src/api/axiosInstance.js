import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://43.201.205.26:8081", // Bikeway-back 실제 주소
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: true,
});

export default axiosInstance;
