import { useEffect, useState } from 'react';
import axiosInstance from '../api/axiosInstance';
import './UsageHistoryPage.css'; // 스타일은 여기에
import logo from '../assets/logo.png';
import { useNavigate, Link } from 'react-router-dom';

function UsageHistoryPage({ setIsLoggedIn }) {
  const [reservations, setReservations] = useState([]);
  const navigate = useNavigate();



  useEffect(() => {
    const memberCode = sessionStorage.getItem('memberCode');
    if (!memberCode || memberCode === 'undefined') return;

    axiosInstance
      .get(`/api/reservation/${memberCode}`)
      .then((res) => setReservations(res.data))
      .catch((err) => console.error('이용내역 불러오기 실패:', err));
  }, []);

  const handleLogout = () => {
    sessionStorage.removeItem('memberCode');
    setIsLoggedIn(false);
    navigate('/', { replace: true });
  };

  const handleLogoClick = () => {
    navigate('/', { replace: true }); // ✅ 메인페이지로 이동
  };



  return (
    <div className="usage-history-page">
      {/* ✅ 메인페이지와 동일한 네비게이션 */}
      <header className="nav">
        <div className="nav-inner">
          <img
            src={logo}
            alt="BIKEWAY 로고"
            className="logo usage-logo "
            onClick={handleLogoClick}
            style={{ cursor: 'pointer' }}
          />
          <nav className="nav-links">
            <button
              onClick={handleLogout}
              className="nav-item logout-button"
            >
              로그아웃
            </button>
            <Link to="/usage" className="nav-item">이용내역</Link>
            <span className="nav-item" style={{ cursor: 'default' }}>BIKES</span>
            <span className="nav-item" style={{ cursor: 'default' }}>CONTACT</span>
          </nav>
        </div>
      </header>

      {/* ✅ 페이지 제목 */}
      <h2 className="page-title">이용내역</h2>

      {/* ✅ 이용내역 테이블 */}
      {reservations.length === 0 ? (
        <p className="empty-message">이용내역이 없습니다.</p>
      ) : (
        <table className="usage-table">
          <thead>
            <tr>
              <th>예약번호</th>
              <th>전화번호</th>
              <th>자전거번호</th>
              <th>자전거종류</th>
              <th>예약날짜시간</th>
              <th>이용시작시간</th>
              <th>이용종료시간</th>
              <th>요금(시간당)</th>
              <th>이용시간</th>
              <th>총금액</th>
              <th>상태</th>
            </tr>
          </thead>
          <tbody>
            {reservations.map((r, index) => (
              <tr key={r.recordNum || index}>
                <td>{r.recordNum}</td>
                <td>{r.phoneNumber}</td>
                <td>{r.bicycleCode}</td>
                <td>{r.bicycleType}</td>
                <td>{r.reservationDateTime}</td>
                <td>{r.startTime}</td>
                <td>{r.endTime}</td>
                <td>{r.ratePerHour}원</td>
                <td>{r.durationHours}시간</td>
                <td>{r.totalAmount}원</td>
                <td>{r.state}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default UsageHistoryPage;
