  
import {Container, Row, Col, Image, Button, Form} from 'react-bootstrap';
import {useState} from 'react';
import './Login.scss';
import logo from '../../assets/cwspider.png';
import {BsFillEyeFill} from 'react-icons/bs';
import {BsFillEyeSlashFill} from 'react-icons/bs';

const Login = () => {
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);

  const togglePasswordVisbility = () => {
    setIsPasswordVisible((prev) => !prev);
  };

  return (
    <>
      <Container fluid className='login-container'>
        <Row className='login-row'>
          <Col xl={12} className='login-logo'>
            <Image src={logo} rounded />
          </Col>
          <Col xl={{span: 4, offset: 4}} className='login-form'>
            <Form>
              <Form.Group className='mb-3' controlId='formBasicEmail'>
                <Form.Label>Nazwa Użytkownika</Form.Label>
                <Form.Control type='text' placeholder='Wprowadź nazwę użytkownika' />
                <Form.Text className='text-muted'></Form.Text>
              </Form.Group>

              <Form.Group className='mb-3' controlId='formBasicPassword'>
                <Form.Label>Hasło</Form.Label>
                <div className='login-form-password'>
                  <div className='login-form-password-field'>
                    <Form.Control type={isPasswordVisible ? 'text' : 'password'} placeholder='Password' />
                  </div>
                  <div className='login-form-password-icon'>
                    {isPasswordVisible ? (
                      <BsFillEyeFill onClick={togglePasswordVisbility} className='--icon-visible'/>
                    ) : (
                      <BsFillEyeSlashFill onClick={togglePasswordVisbility} />
                    )}
                  </div>
                </div>
              </Form.Group>
              <div className='login-form-submit'>
                <Button variant='primary' type='submit' className='login-form-submit-button'>
                Zaloguj
                </Button>
              </div>
            </Form>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default Login;
