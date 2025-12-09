import React, { useState } from 'react';
import axios from 'axios';

const Booking = () => {
  const [reservation, setReservation] = useState({
    userCode: '',
    movieNum: '',
    scheduleNum: '',
    seatCode: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setReservation((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        ...reservation,
        paymentTime: new Date().toISOString(),
        status: '예약완료', // 반드시 백엔드가 허용하는 값으로 설정
      };

      const response = await axios.post('/reservation/create', payload);

      if (typeof response.data === 'object') {
        alert(response.data.message || JSON.stringify(response.data));
      } else {
        alert(response.data);
      }
    } catch (error) {
      const errorMessage =
        error.response?.data?.message ||
        error.response?.data ||
        error.message ||
        '예매 실패';
      alert(errorMessage);
    }
  };

  return (
    <div style={{ padding: '100px', textAlign: 'center' }}>
      <h1>예매 페이지</h1>
      <p>여기에서 영화 예매를 진행할 수 있어요!</p>

      <form onSubmit={handleSubmit} style={{ marginTop: '30px' }}>
        <input
          type="number"
          name="userCode"
          placeholder="회원 코드"
          value={reservation.userCode}
          onChange={handleChange}
          required
        /><br /><br />
        <input
          type="number"
          name="movieNum"
          placeholder="영화 번호"
          value={reservation.movieNum}
          onChange={handleChange}
          required
        /><br /><br />
        <input
          type="number"
          name="scheduleNum"
          placeholder="일정 번호"
          value={reservation.scheduleNum}
          onChange={handleChange}
          required
        /><br /><br />
        <input
          type="number"
          name="seatCode"
          placeholder="좌석 번호"
          value={reservation.seatCode}
          onChange={handleChange}
          required
        /><br /><br />
        <button type="submit">예매하기</button>
      </form>
    </div>
  );
};

export default Booking;