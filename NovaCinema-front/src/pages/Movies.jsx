import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Movies = () => {
  const [movies, setMovies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get('http://localhost:8082/info/list')
      .then((res) => {
        console.log('🎬 영화 목록 응답:', res.data);
        setMovies(res.data);
      })
      .catch((err) => {
        console.error('영화 목록 불러오기 실패', err);
        setError('영화 정보를 불러올 수 없습니다.');
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const handleBooking = (movieNum) => {
    navigate(`/booking?movieNum=${movieNum}`);
  };

  return (
    <div style={{ padding: '2rem', paddingTop: '100px' }}>
      <h1 style={{ textAlign: 'center' }}>🎟️ 예매 페이지</h1>
      <p style={{ textAlign: 'center' }}>상영 중인 영화를 선택하세요!</p>

      {loading && <p>불러오는 중...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {movies.length > 0 && (
        <ul style={{ listStyle: 'none', padding: 0 }}>
          {movies.map((movie) => (
            <li
              key={movie.movieNum}
              style={{
                border: '1px solid #ccc',
                borderRadius: '8px',
                padding: '1rem',
                marginBottom: '1rem',
              }}
            >
              <h3>{movie.movieTitle}</h3>
              <p>⏱️ 상영 시간: {movie.runningTime}분</p>
              <p>🔞 관람 등급: {movie.audienceRating}</p>
              <button
                style={{ marginTop: '0.5rem' }}
                onClick={() => handleBooking(movie.movieNum)}
              >
                예매하기
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Movies;
