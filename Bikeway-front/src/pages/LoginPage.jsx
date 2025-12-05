import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../api/axiosInstance';
import './LoginPage.css';
import logo from '../assets/logo.png';


function LoginPage({ isLoggedIn, setIsLoggedIn }) {
  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await axiosInstance.post('/api/member/login', {
        id: id,
        password: pw,
      });

      console.log('로그인 응답:', res.data);

      if (res.data.success) {
        alert('로그인 성공!');
        setIsLoggedIn(true);
        sessionStorage.setItem('memberCode', res.data.member_code);
        setTimeout(() => {
          navigate('/');
        }, 100);
      } else {
        setIsLoggedIn(false);
        alert('로그인 실패: 아이디 또는 비밀번호가 틀렸습니다.');
        navigate('/');
      }
    } catch (err) {
      console.error('로그인 오류:', err);
      setIsLoggedIn(false);

      if (err.response && err.response.status === 401) {
        alert('로그인 실패: 아이디 또는 비밀번호가 틀렸습니다.');
      } else {
        alert('서버 오류로 로그인에 실패했습니다.');
      }

      navigate('/');
    }
  };

  return (
    <div className="login-page">
      <header className="nav">
        <div className="nav-inner">
          <img
            src={logo}
            alt="BIKEWAY 로고"
            className="logo login-logo"
            onClick={() => navigate('/')}
            style={{ cursor: 'pointer' }}
          />
          <nav className="nav-links">
            {isLoggedIn ? (
              <button
                className="nav-item logout-button"
                onClick={() => {
                  setIsLoggedIn(false);
                  navigate('/');
                }}
              >
                로그아웃
              </button>
            ) : (
              <a href="/login" className="nav-item">로그인</a>
            )}

            <a
              href={isLoggedIn ? '/history' : '#'}
              className="nav-item usage-link"
              onClick={(e) => {
                if (!isLoggedIn) {
                  e.preventDefault();
                  alert('로그인이 필요한 서비스입니다.');
                }
              }}
            >
              이용내역
            </a>

            <a href="#" className="nav-item" onClick={(e) => e.preventDefault()}>BIKES</a>
            <a href="#" className="nav-item" onClick={(e) => e.preventDefault()}>CONTACT</a> {/* ✅ CONTACT로 수정 */}
          </nav>
        </div>
      </header>


      {/* 로그인 폼 */}
      <div className="login-container">
        <form className="login-form" onSubmit={(e) => { e.preventDefault(); handleLogin(); }}>
          <h2>로그인</h2>
          <input
            type="text"
            placeholder="아이디"
            value={id}
            onChange={(e) => setId(e.target.value)}
          />
          <input
            type="password"
            placeholder="비밀번호"
            value={pw}
            onChange={(e) => setPw(e.target.value)}
          />
          <button onClick={handleLogin}>로그인</button>
        </form>
      </div>
    </div>
  );
}

export default LoginPage;