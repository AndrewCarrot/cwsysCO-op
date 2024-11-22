import {useState} from 'react';
import {Container, Row, Col, Image, Button, Form} from 'react-bootstrap';
import {BsFillEyeFill, BsFillEyeSlashFill} from 'react-icons/bs';
import './Login.scss';
import logo from '../../assets/cwspider.png';
import {useLogin} from '../../hooks/useLogin';

const Login = () => {
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const {formData, error, handleInputChange, handleSubmit} = useLogin();

  const togglePasswordVisibility = () => {
    setIsPasswordVisible((prev) => !prev);
  };

  return (
    <Container fluid className='login-container'>
      <Row className='login-row'>
        <Col xl={12} className='login-logo'>
          <Image src={logo} rounded />
        </Col>
        <Col xs={12} md={{span: 6, offset: 3}} className='login-form'>
          <Form onSubmit={handleSubmit}>
            <Form.Group className='mb-3' controlId='usernameOrEmail'>
              <Form.Label>Nazwa Użytkownika</Form.Label>
              <Form.Control
                type='text'
                placeholder='Wprowadź nazwę użytkownika'
                value={formData.usernameOrEmail}
                onChange={handleInputChange}
              />
            </Form.Group>

            <Form.Group className='mb-3' controlId='password'>
              <Form.Label>Hasło</Form.Label>
              <div className='login-form-password'>
                <div className='login-form-password-field'>
                  <Form.Control
                    type={isPasswordVisible ? 'text' : 'password'}
                    placeholder='Password'
                    value={formData.password}
                    onChange={handleInputChange}
                  />
                </div>
                <div className='login-form-password-icon'>
                  {isPasswordVisible ? (
                    <BsFillEyeFill onClick={togglePasswordVisibility} className='--icon-visible' />
                  ) : (
                    <BsFillEyeSlashFill onClick={togglePasswordVisibility} />
                  )}
                </div>
              </div>
            </Form.Group>

            {error && <p className='text-danger'>{error}</p>}

            <div className='login-form-submit'>
              <Button variant='primary' type='submit' className='login-form-submit-button'>
                Zaloguj
              </Button>
            </div>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default Login;
