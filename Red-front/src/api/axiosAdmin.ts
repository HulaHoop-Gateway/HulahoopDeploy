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
axiosAdmin.interceptors.request.use(
  (config) => {
    const token =
      typeof window !== "undefined" ? localStorage.getItem("admin_jwt") : null;

    if (token) {
      config.headers.Authorization = `Bearer ${token} `;
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
        window.location.href = "/admin/login";
      }
    }
    return Promise.reject(error);
  }
);

export default axiosAdmin;
