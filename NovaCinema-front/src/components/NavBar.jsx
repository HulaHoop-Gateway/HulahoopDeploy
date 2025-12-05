import React from 'react';
import { useNavigate } from 'react-router-dom';
import './NavBar.css';

function NavBar({ isLoggedIn, setIsLoggedIn }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    sessionStorage.removeItem('userCode');
    setIsLoggedIn(false);
    navigate('/');
  };

  const goIfLoggedIn = (path) => {
    const userCode = sessionStorage.getItem('userCode');
    if (!userCode) {
      alert('로그인이 필요한 서비스입니다.');
      return;
    }
    navigate(path);
  };

  return (
    <nav className="menu-bar">
      <ul>
        <li><button onClick={() => goIfLoggedIn('/booking')}>예매</button></li>
        <li><button onClick={() => goIfLoggedIn('/history')}>이용내역</button></li>
        <li><button onClick={() => navigate('/movies')}>영화</button></li>
        <li><button onClick={() => navigate('/cinemas')}>영화관</button></li>
        <li><button onClick={() => navigate('/events')}>이벤트</button></li>
        <li><button onClick={() => navigate('/store')}>스토어</button></li>
        {isLoggedIn && (
          <li><button onClick={handleLogout}>로그아웃</button></li>
        )}
      </ul>
    </nav>
  );
}

export default NavBar;