const mysql = require('mysql2/promise');

const db = mysql.createPool({
  host: 'localhost',
  user: 'ohgiraffers',
  password: '',
  database: 'cinemadb',
});

module.exports = db;v