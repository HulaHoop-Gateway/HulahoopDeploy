import React, { useEffect, useState } from 'react';

function CinemaListFromSeatsOnly() {
  const [seats, setSeats] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchSeats = async () => {
      try {
        const res = await fetch('/seat/list');
        if (!res.ok) throw new Error('서버 응답 오류');
        const data = await res.json();
        setSeats(data);
      } catch (err) {
        setError('조회 실패: ' + err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchSeats();
  }, []);

  const groupByCinemaAndTheater = () => {
    const cinemaMap = new Map();

    seats.forEach((seat) => {
      const theater = seat.theaterDTO;
      const cinema = theater?.cinemaFranchisedto;
      const branchNum = theater?.branchNum;
      const screeningNum = theater?.screeningNum;

      if (!cinema || !branchNum || !screeningNum) return;

      if (!cinemaMap.has(branchNum)) {
        cinemaMap.set(branchNum, {
          branchNum,
          branchName: cinema.branchName,
          address: cinema.address,
          theaters: new Map(),
        });
      }

      const cinemaGroup = cinemaMap.get(branchNum);

      if (!cinemaGroup.theaters.has(screeningNum)) {
        cinemaGroup.theaters.set(screeningNum, {
          screeningNum,
          screeningNumber: theater.screeningNumber,
          seats: [],
        });
      }

      cinemaGroup.theaters.get(screeningNum).seats.push(seat);
    });

    return Array.from(cinemaMap.values()).map((cinema) => ({
      ...cinema,
      theaters: Array.from(cinema.theaters.values()),
    }));
  };

  const groupedData = groupByCinemaAndTheater();

  return (
    <div style={{ padding: '2rem', paddingTop: '100px' }}>
      <h2>🎬 좌석 기반 영화관/상영관 목록</h2>

      {loading && <p>조회 중...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {groupedData.length === 0 && !loading && (
        <p>표시할 좌석 데이터가 없습니다.</p>
      )}

      {groupedData.map((cinema) => (
        <div key={cinema.branchNum} style={{ marginBottom: '2rem' }}>
          <h3>
            {cinema.branchName} ({cinema.branchNum})
          </h3>
          <p>{cinema.address}</p>

          {cinema.theaters.map((theater) => (
            <div
              key={theater.screeningNum}
              style={{
                marginLeft: '1rem',
                marginBottom: '1rem',
                backgroundColor: '#f0f8ff',
                padding: '0.5rem',
                borderRadius: '6px',
              }}
            >
              <strong>
                🎞️ 상영관 {theater.screeningNumber} (고유번호:{' '}
                {theater.screeningNum})
              </strong>
              <ul>
                {theater.seats.map((seat) => (
                  <li key={seat.seatCode}>
                    <strong>{seat.seatRealNum}</strong> / 유형:{seat.seatType} / 가격:{' '}
                    {seat.sale.toLocaleString()}원
                  </li>
                ))}
              </ul>
            </div>
          ))}
        </div>
      ))}
    </div>
  );
}


export default CinemaListFromSeatsOnly;

