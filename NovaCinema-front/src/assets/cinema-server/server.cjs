const express = require('express');
const cors = require('cors');
const reservationRoutes = require('./routes/reservation.cjs');

const app = express();
app.use(cors());
app.use(express.json());

app.use('/api', reservationRoutes);

const PORT = 5000;
app.listen(PORT, () => {
  console.log(`서버 실행 중: http://localhost:${PORT}`);
});