import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './MainBanner.css';
import image1 from '../assets/image1.jpg';
import image2 from '../assets/image2.jpg';
import logo from '../assets/logo.png';


function MainBanner({ isLoggedIn, setIsLoggedIn }) {
  const [slide, setSlide] = useState(0);
  const navigate = useNavigate();

  const images = [image1, image2];
  const titles = ['PUSH THE LIMIT', 'RIDE BEYOND LIMITS'];
  const descriptions = [
    'BIKEWAY는 도시와 자연을 연결하는 자전거 브랜드입니다.',
    '도시를 달리는 가장 스마트한 선택~',
  ];

  const prevSlide = () => {
    setSlide((slide - 1 + images.length) % images.length);
  };

  const nextSlide = () => {
    setSlide((slide + 1) % images.length);
  };

  const handleLogout = () => {
    sessionStorage.removeItem('memberCode'); // ✅ 로그인 정보 제거
    setIsLoggedIn(false);                  // ✅ 상태 false로 변경
    navigate('/');                         // ✅ 메인페이지로 이동
  };

  const handleUsageClick = () => {
    if (isLoggedIn) {
      navigate('/usage'); // ✅ 로그인 상태면 이동
    } else {
      alert('로그인이 필요한 서비스입니다.'); // 로그인 안 되어 있으면 alert
    }
  };



  return (
    <div className="banner">
      <img src={images[slide]} className="background" alt="배경" />

      <header className="nav">
        <div className="nav-inner">
          <img src={logo} alt="BIKEWAY 로고" className="logo main-logo" />
          <nav className="nav-links">
            {isLoggedIn ? (
              <button className="nav-item logout-button" onClick={handleLogout}>로그아웃</button>
            ) : (
              <Link className="nav-item login-button" to="/login">로그인</Link>
            )}
            <button className="nav-item usage-link" onClick={handleUsageClick}>이용내역</button> {/* ✅ 수정 */}


            {/* ✅ 아무 반응 없는 버튼 */}
            <button className="nav-item" onClick={(e) => e.preventDefault()}>BIKES</button>
            <button className="nav-item" onClick={(e) => e.preventDefault()}>CONTACT</button>
          </nav>
        </div>
      </header>

      {slide === 0 && (
        <div className="content slide1">
          <h1 className="headline">{titles[slide]}</h1>
          <p className="subtext">{descriptions[slide]}</p>
        </div>
      )}

      {slide === 1 && (
        <div className="smart-box">
          <p className="smart-text">{descriptions[slide]}</p>
        </div>
      )}

      <div className="controls">
        <button onClick={() => setSlide(0)} className={slide === 0 ? 'active' : ''}></button>
        <button onClick={() => setSlide(1)} className={slide === 1 ? 'active' : ''}></button>
      </div>

      <div className="arrows">
        <button className="main-banner-arrow left" onClick={prevSlide}>←</button>
        <button className="main-banner-arrow right" onClick={nextSlide}>→</button>
      </div>
    </div>
  );
}

export default MainBanner;