import React from 'react';
import slide1 from '../assets/slide1.jpg';
import slide2 from '../assets/slide2.jpg';
import slide3 from '../assets/slide3.jpg';
import './Carousel.css';

const images = [slide1, slide2, slide3];
const texts = [
  '🎉 10월 22일 극장 대개봉!',
  '📅 2025.10.22 Coming Soon',
  '🎬 10월 22일 개봉 예정',
];

const positions = ['center center', 'center center', 'center center'];


const Carousel = ({ currentSlide, setCurrentSlide }) => {
  const handleArrowClick = (direction) => {
    if (direction === 'left') {
      setCurrentSlide((prev) => (prev === 0 ? images.length - 1 : prev - 1));
    } else {
      setCurrentSlide((prev) => (prev === images.length - 1 ? 0 : prev + 1));
    }
  };

  return (
    <div
      className="carousel"
      style={{
        backgroundImage: `url(${images[currentSlide]})`,
        backgroundSize: 'contain',
        backgroundPosition: positions[currentSlide],
        backgroundRepeat: 'no-repeat',
        backgroundColor: 'black', // ✅ 여백을 자연스럽게 처리
 
      }}
    >
      <div className="arrow left" onClick={() => handleArrowClick('left')}>◀</div>
      <div className="arrow right" onClick={() => handleArrowClick('right')}>▶</div>
      <div className="release-text">{texts[currentSlide]}</div>
      <div className="dots">
        {images.map((_, index) => (
          <span
            key={index}
            className={`dot ${currentSlide === index ? 'active' : ''}`}
            onClick={() => setCurrentSlide(index)}
          />
        ))}
      </div>
    </div>
  );
};

export default Carousel;