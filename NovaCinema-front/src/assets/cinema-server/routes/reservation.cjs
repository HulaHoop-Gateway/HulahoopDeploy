const express = require('express');
const router = express.Router();
const db = require('../db.cjs');

router.get('/history', async (req, res) => {
  try {
    const [rows] = await db.query('SELECT * FROM t_reservationinfo');
    res.json(rows);
  } catch (err) {
    console.error('예매 내역 조회 실패:', err);
    res.status(500).json({ error: '서버 오류' });
  }
});

module.exports = router; // ✅ 꼭 있어야 함