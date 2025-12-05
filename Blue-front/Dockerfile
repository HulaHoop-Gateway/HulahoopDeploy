FROM node:20

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
COPY .env.docker .env

EXPOSE 5173

# Vite dev server 실행 (proxy 사용)
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0", "--port", "5173"]
