import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  define: {
    global: 'window',
  },
  server: {
    host: '0.0.0.0', // Docker에서 외부 접속 허용
    port: 5174,  // 로컬 개발과 동일한 포트
    allowedHosts: true, // Nginx 프록시 접속 허용
    allowedHosts: true, // Nginx 프록시 접속 허용
    proxy: {
      '/api': {
        target: 'http://blue-back:8090',
        changeOrigin: true,
        secure: false,
      },
    },
  },
});
