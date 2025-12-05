import React from 'react';
import Header from '../components/Header';
import NavBar from '../components/NavBar';
import Carousel from '../components/Carousel';
import './MainLayout.css'; // ✅ 스타일 파일 import


function MainLayout({ currentSlide, setCurrentSlide, isLoggedIn, setIsLoggedIn }) {
  return (
     <div className="main-layout">
      <Header isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} /> 
      <NavBar />
      <Carousel currentSlide={currentSlide} setCurrentSlide={setCurrentSlide} />
    </div>
  );
}



export default MainLayout;