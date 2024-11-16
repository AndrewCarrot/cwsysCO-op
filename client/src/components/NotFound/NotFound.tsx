import {Link} from 'react-router-dom';
import {Image} from 'react-bootstrap';
import notFound from '../../assets/notfound.png';
import './NotFound.scss';

const NotFound = () => {
  return (
    <div className='not-found'>
      <div className='not-found body'>
        <Image src={notFound} rounded />      
        <h1>404 - Strona nie znaleziona</h1>
        <p>Przepraszamy, ale ta strona nie istnieje.</p>
        <Link to='/'>Powrót na stronę główną</Link>
      </div>
    </div>
  );
};

export default NotFound;
