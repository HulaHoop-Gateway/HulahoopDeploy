// api/axiosAdmin.ts
import axios from "axios";

const axiosAdmin = axios.create({
  baseURL: "http://43.201.205.26:8000", // Red-back 실제 주소
  headers: {
    "Content-Type": "application/json",
  },
});

// ----------------------
// 요청 인터셉터
// ----------------------
// ----------------------
// 요청 인터셉터
// ----------------------
axiosAdmin.interceptors.request.use(
  (config) => {
    const token =
      typeof window !== "undefined" ? localStorage.getItem("admin_jwt") : null;

    if (token) {
      config.headers.Authorization = `Bearer ${token}`; // 공백 제거
    }

    return config;
  },
  (error) => Promise.reject(error)
);

// ----------------------
// 응답 인터셉터
// ----------------------
axiosAdmin.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      if (typeof window !== "undefined") {
        localStorage.removeItem("admin_jwt");
        localStorage.removeItem("admin_name"); // 이름도 삭제
        window.location.href = "/"; // 로그인 페이지가 루트('/')임
      }
    }
    return Promise.reject(error);
  }
);

export default axiosAdmin;
