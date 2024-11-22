import {Navigate} from 'react-router-dom';
import {useTokenValidation} from '../../hooks/useTokenValidation';
import Spinner from 'react-bootstrap/Spinner';

const ProtectedRoute = ({children}: { children: JSX.Element }) => {
  const [isTokenValid] = useTokenValidation();

  if (isTokenValid === null) {
    return (
      <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh'}}>
        <Spinner animation='border' role='status'>
          <span className='visually-hidden'>Loading...</span>
        </Spinner>
      </div>
    );
  }

  if (!isTokenValid) {
    sessionStorage.removeItem('token');
    return <Navigate to='/' replace />;
  }

  return children;
};

export default ProtectedRoute;
