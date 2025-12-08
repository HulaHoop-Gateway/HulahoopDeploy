FROM node:20-alpine

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
COPY .env.docker .env

EXPOSE 3000

# 빌드 실행 (메모리 절약 및 성능 최적화)
RUN npm run build

EXPOSE 3000

# 프로덕션 모드로 시작
CMD ["npm", "run", "start"]


