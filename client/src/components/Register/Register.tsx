import {Container, Row, Col, Image, Button, Form, Modal, Spinner} from 'react-bootstrap';
import './Register.scss';
import logo from '../../assets/cwspider.png';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import useClimberRegistration from '../../hooks/useClimberRegistration';
import {useEffect, useState} from 'react';

const Register = () => {
  const {
    formData,
    errors,
    registerSuccessful,
    apiError,
    loading,
    handleInputChange,
    handleDateChange,
    handleSubmit,
    resetForm,
  } = useClimberRegistration();

  const [showApiError, setShowApiError] = useState(false);

  useEffect(() => {
    if (apiError) {
      setShowApiError(true);
      const timeout = setTimeout(() => setShowApiError(false), 4000);
      return () => clearTimeout(timeout);
    }
  }, [apiError]);

  return (
    <Container fluid className='register-container'>
      <Modal show={showApiError} onHide={() => setShowApiError(false)} centered>
        <Modal.Body>
          <div className='text-center text-danger'>{apiError}</div>
        </Modal.Body>
      </Modal>

      <Row className='register-row'>
        <Col xl={12} className='register-logo'>
          <Image src={logo} rounded />
        </Col>
        <Col xs={12} md={{span: 6, offset: 3}} className='register-form'>
          {!registerSuccessful ? (
            <Form onSubmit={handleSubmit}>
              <Form.Group className='mb-3' controlId='firstName'>
                <Form.Label>Imię</Form.Label>
                <Form.Control
                  type='text'
                  placeholder='Wprowadź imię'
                  value={formData.firstName}
                  onChange={handleInputChange}
                  isInvalid={!!errors.firstName}
                />
                <Form.Control.Feedback type='invalid'>
                  {errors.firstName}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className='mb-3' controlId='lastName'>
                <Form.Label>Nazwisko</Form.Label>
                <Form.Control
                  type='text'
                  placeholder='Wprowadź nazwisko'
                  value={formData.lastName}
                  onChange={handleInputChange}
                  isInvalid={!!errors.lastName}
                />
                <Form.Control.Feedback type='invalid'>
                  {errors.lastName}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className='mb-3' controlId='email'>
                <Form.Label>E-mail</Form.Label>
                <Form.Control
                  type='email'
                  placeholder='Wprowadź e-mail'
                  value={formData.email}
                  onChange={handleInputChange}
                  isInvalid={!!errors.email}
                />
                <Form.Control.Feedback type='invalid'>
                  {errors.email}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className='mb-3' controlId='phoneNumber'>
                <Form.Label>Numer telefonu</Form.Label>
                <Form.Control
                  type='text'
                  placeholder='Wprowadź numer telefonu'
                  value={formData.phoneNumber}
                  onChange={handleInputChange}
                  isInvalid={!!errors.phoneNumber}
                />
                <Form.Control.Feedback type='invalid'>
                  {errors.phoneNumber}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className='mb-3' controlId='dateOfBirth'>
                <Form.Label>Data urodzenia</Form.Label>
                <div>
                  <DatePicker
                    selected={formData.dateOfBirth}
                    onChange={handleDateChange}
                    dateFormat='dd-MM-yyyy'
                    placeholderText='Wybierz datę urodzenia'
                    className={`form-control ${errors.dateOfBirth ? 'is-invalid' : ''}`}
                    showPopperArrow={false}
                    maxDate={new Date()}
                  />
                </div>
              </Form.Group>
              {errors.dateOfBirth && (
                <div className='invalid-feedback invalid-feedback-date'>{errors.dateOfBirth}</div>
              )}
              <div className='register-form-submit'>
                <Button
                  variant='primary'
                  type='submit'
                  className='register-form-submit-button'
                  disabled={loading}
                >
                  {loading ? (
                    <>
                      <Spinner animation='border' size='sm' className='me-2' /> Rejestracja...
                    </>
                  ) : (
                    'Zarejestruj'
                  )}
                </Button>
              </div>
            </Form>
          ) : (
            <div className='success-feedback fade-in-scale'>
              <h2>Pomyślnie zarejestrowano wspinacza!</h2>
              <Button
                variant='primary'
                className='register-form-add-another-climber'
                onClick={resetForm}
              >
                Dodaj następnego wspinacza
              </Button>
            </div>
          )}
        </Col>
      </Row>
    </Container>
  );
};

export default Register;
