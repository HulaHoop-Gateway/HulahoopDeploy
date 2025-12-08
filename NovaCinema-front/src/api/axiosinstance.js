import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "", // ✅ Vite Proxy를 타도록 설정 (http://cinema-back:8082로 전달됨)
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: false, // 세션 쿠키도 안 쓰면 false 유지
});

// ✅ JWT 자동 첨부 부분 제거
// axiosInstance.interceptors.request.use((config) => {
//   const token = localStorage.getItem("user_jwt");
//   if (token) {
//     config.headers.Authorization = `Bearer ${token}`;
//   }
//   return config;
// });

export default axiosInstance;