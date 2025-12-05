import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import './Header.css';

function Header({ isLoggedIn, setIsLoggedIn }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    sessionStorage.removeItem('userCode');
    setIsLoggedIn(false);
    navigate('/');
  };

  return (
    <header className="header">
      <div className="header-left"></div>
      <div className="header-center">
        <img src={logo} alt="Logo" className="logo main-logo" onClick={() => navigate('/')} />
      </div>
      <div className="header-right">
        {isLoggedIn ? (
          <span onClick={handleLogout} className="auth-link">로그아웃</span>
        ) : (
          <Link to="/login" className="auth-link">로그인</Link>
        )}
      </div>
    </header>
  );
}

export default Header;