import {Navigate} from 'react-router-dom';
import {useTokenValidation} from '../../hooks/useTokenValidation';

const ProtectedRoute = ({children}: { children: JSX.Element }) => {
  const [isTokenValid] = useTokenValidation();

  if (!isTokenValid) {
    sessionStorage.removeItem('token');
    return <Navigate to='/' replace />;
  }

  return children;
};

export default ProtectedRoute;
