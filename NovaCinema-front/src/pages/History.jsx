import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import './History.css';

function History({ isLoggedIn, setIsLoggedIn }) {
  const [reservations, setReservations] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const phoneNumber = sessionStorage.getItem('phoneNumber');
    if (!phoneNumber) {
      setIsLoggedIn(false);
      navigate('/', { replace: true });
      return;
    }

    // ✅ 그룹화된 예약 내역 조회
    axios
      .get(`/reservation/history/grouped?phoneNumber=${phoneNumber}`)
      .then((res) => {
        console.log('🎬 예약 내역 응답:', res.data);
        // 응답이 배열이면 그대로, 객체면 빈 배열
        const data = Array.isArray(res.data) ? res.data : [];
        setReservations(data);
      })
      .catch((err) => console.error('예약 내역 불러오기 실패', err));
  }, [isLoggedIn]);

  const handleLogout = () => {
    sessionStorage.removeItem('userCode');
    sessionStorage.removeItem('phoneNumber');
    setIsLoggedIn(false);
    navigate('/');
  };

  return (
    <div className="history-page">
      {/* 헤더 */}
      <header className="history-header">
        <img
          src={logo}
          alt="Logo"
          className="logo history-logo"
          onClick={() => navigate('/')}
        />
        <div className="header-right">
          <button onClick={() => navigate('/mypage')}>마이페이지</button>
          <button onClick={handleLogout}>로그아웃</button>
        </div>
      </header>

      {/* 메뉴바 */}
      <nav className="menu-bar">
        <button onClick={() => navigate('/booking')}>예매</button>
        <button onClick={() => navigate('/history')}>이용내역</button>
        <button onClick={() => navigate('/movies')}>영화</button>
        <button onClick={() => navigate('/cinemas')}>영화관</button>
        <button onClick={() => navigate('/events')}>이벤트</button>
        <button onClick={() => navigate('/store')}>스토어</button>
      </nav>

      {/* 이용내역 섹션 */}
      <main className="history-section">
        <h2 className="section-title">이용내역</h2>

        {reservations.length === 0 ? (
          <p>예약 내역이 없습니다.</p>
        ) : (
          <table className="history-table">
            <thead>
              <tr>
                <th>예약번호</th>
                <th>좌석</th>
                <th>가격</th>
                <th>영화</th>
                <th>상영일</th>
                <th>영화관</th>
                <th>상영관</th>
                <th>전화번호</th>
                <th>상태</th>
                <th>결제시간</th>
              </tr>
            </thead>
            <tbody>
              {reservations.map((r) => (
                <tr key={r.firstReservationNum || r.bookingGroupId}>
                  <td>{r.firstReservationNum || '정보 없음'}</td>
                  <td>
                    {r.seatLabels && r.seatLabels.length > 0
                      ? r.seatLabels.join(', ')
                      : '정보 없음'}
                  </td>
                  <td>
                    {r.totalAmount != null
                      ? `${Number(r.totalAmount).toLocaleString()}원`
                      : '0원'}
                  </td>
                  <td>{r.scheduleDTO?.movieInfo?.movieTitle || '제목 없음'}</td>
                  <td>
                    {r.scheduleDTO?.screeningDate
                      ? new Date(r.scheduleDTO.screeningDate).toLocaleString()
                      : '날짜 없음'}
                  </td>
                  <td>
                    {r.scheduleDTO?.theaterInfo?.cinemaFranchisedto?.branchName ||
                      '지점 없음'}
                  </td>
                  <td>
                    {r.scheduleDTO?.theaterInfo?.screeningNumber
                      ? `${r.scheduleDTO.theaterInfo.screeningNumber}관`
                      : '정보 없음'}
                  </td>
                  <td>{r.phoneNumber || '정보 없음'}</td>
                  <td>{r.state}</td>
                  <td>
                    {r.paymentTime
                      ? new Date(r.paymentTime).toLocaleString()
                      : '시간 없음'}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}

        {/* 하단 버튼 */}
        <div className="history-actions">
          <button className="ticket-button">티켓 확인</button>
          <button className="inquiry-button">문의하기</button>
        </div>
      </main>
    </div>
  );
}

export default History;
