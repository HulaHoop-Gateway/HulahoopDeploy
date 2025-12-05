import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainLayout from './pages/MainLayout';
import Login from './pages/Login';
import History from './pages/History';
import Booking from './pages/Booking';
import CinemaList from './pages/CinemaList';
import Movies from './pages/Movies';

function App() {
  const [currentSlide, setCurrentSlide] = useState(0);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    // 앱 시작 시 sessionStorage에 정보가 있으면 로그인 유지 (창 닫으면 자동 로그아웃)
    const userCode = sessionStorage.getItem('userCode');
    if (userCode) {
      setIsLoggedIn(true);
    } else {
      setIsLoggedIn(false);
    }
  }, []);



  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={
            <MainLayout
              currentSlide={currentSlide}
              setCurrentSlide={setCurrentSlide}
              isLoggedIn={isLoggedIn}
              setIsLoggedIn={setIsLoggedIn}
            />
          }
        />
        <Route path="/login" element={<Login setIsLoggedIn={setIsLoggedIn} />} />
        <Route
          path="/history"
          element={
            <History
              isLoggedIn={isLoggedIn}
              setIsLoggedIn={setIsLoggedIn}
            />
          }
        />
        <Route path="/booking" element={<Booking />} />
        <Route path="/cinemas" element={<CinemaList />} />
        <Route path="/movies" element={<Movies />} />
      </Routes>
    </Router>
  );
}

export default App;