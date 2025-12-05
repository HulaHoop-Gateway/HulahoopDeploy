import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function RequireAuth({ isLoggedIn, children }) {
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoggedIn) {
      alert('로그인이 필요한 서비스입니다.');
      navigate('/');
    }
  }, [isLoggedIn, navigate]);

  return isLoggedIn ? children : null;
}

export default RequireAuth;