FROM node:20

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
COPY .env.docker .env

EXPOSE 5174

CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0", "--port", "5174"]
