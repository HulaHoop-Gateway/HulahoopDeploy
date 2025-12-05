import { useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import MainBanner from './components/MainBanner';
import LoginPage from './pages/LoginPage';
import UsageHistoryPage from './pages/UsageHistoryPage';


function LoginRequiredMessage() {
  return (
    <div style={{ textAlign: 'center', marginTop: '50px', fontSize: '20px', color: 'red' }}>
      로그인이 필요한 서비스입니다.
    </div>
  );
}




function App() {
  // ✅ 초기값을 sessionStorage에서 바로 읽어옴 (창 닫으면 자동 로그아웃)
  const [isLoggedIn, setIsLoggedIn] = useState(() => {
    const memberCode = sessionStorage.getItem('memberCode');
    return memberCode && memberCode !== 'undefined';
  });



  return (
    <BrowserRouter>
      <Routes>
        <Route path="/"
          element={<MainBanner isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />}
        />
        <Route
          path="/login"
          element={
            <LoginPage
              isLoggedIn={isLoggedIn}
              setIsLoggedIn={setIsLoggedIn} />}
        />
        <Route
          path="/usage"
          element={
            isLoggedIn
              ? <UsageHistoryPage setIsLoggedIn={setIsLoggedIn} />
              : <LoginRequiredMessage />
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;