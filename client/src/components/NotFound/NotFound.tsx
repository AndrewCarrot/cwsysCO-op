import {Link} from 'react-router-dom';

const NotFound = () => {
  return (
    <div style={{textAlign: 'center', padding: '50px'}}>
      <h1>404 - Strona nie znaleziona</h1>
      <p>Przepraszamy, ale ta strona nie istnieje.</p>
      <Link to='/'>Powrót na stronę główną</Link>
    </div>
  );
};

export default NotFound;
