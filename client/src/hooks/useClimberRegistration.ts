import {useState} from 'react';
import {createClimber} from '../api/Climber';
import type {ChangeEvent} from 'react';
import {climberData} from '../types/types';
import {validateEmail, validatePhoneNumber, isNotEmpty, validateDate} from '../utils/validateForm';
import {empyFieldFeedback, wrongEmail, wrongPhone, apiErrorMessage} from '../utils/consts';

const useClimberRegistration = () => {
  const [formData, setFormData] = useState<climberData>({
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    dateOfBirth: null,
  });

  const [errors, setErrors] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    dateOfBirth: '',
  });

  const [registerSuccessful, setRegisterSuccessful] = useState(false);
  const [apiError, setApiError] = useState('');
  const [loading, setLoading] = useState(false);

  const validateForm = () => {
    setErrors({
      firstName: '',
      lastName: '',
      email: '',
      phoneNumber: '',
      dateOfBirth: '',
    });

    const validators: { field: keyof climberData; validate: (value: any) => boolean; error: string }[] = [
      {field: 'firstName', validate: isNotEmpty, error: empyFieldFeedback},
      {field: 'lastName', validate: isNotEmpty, error: empyFieldFeedback},
      {field: 'email', validate: validateEmail, error: wrongEmail},
      {field: 'phoneNumber', validate: validatePhoneNumber, error: wrongPhone},
      {field: 'dateOfBirth', validate: validateDate, error: empyFieldFeedback},
    ];

    const newErrors = validators.reduce((acc, {field, validate, error}) => {
      if (!validate(formData[field])) {
        acc[field] = error;
      }
      return acc;
    }, {} as typeof errors);

    setErrors(newErrors);

    return Object.values(newErrors).every((error) => !error);
  };

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    const {id, value} = e.target;
    setFormData({...formData, [id]: value});
    setErrors({...errors, [id]: ''});
  };

  const handleDateChange = (date: Date | null) => {
    setFormData({...formData, dateOfBirth: date});
    setErrors({...errors, dateOfBirth: ''});
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return;

    setLoading(true);
    try {
      await new Promise((resolve) => setTimeout(resolve, 700));
      await createClimber(formData);
      setRegisterSuccessful(true);
    } catch (error) {
      console.error(error);
      setApiError(apiErrorMessage);
    } finally {
      setLoading(false);
    }
  };

  const resetForm = () => {
    setFormData({
      firstName: '',
      lastName: '',
      email: '',
      phoneNumber: '',
      dateOfBirth: null,
    });
    setErrors({
      firstName: '',
      lastName: '',
      email: '',
      phoneNumber: '',
      dateOfBirth: '',
    });
    setRegisterSuccessful(false);
  };

  return {
    formData,
    errors,
    registerSuccessful,
    handleInputChange,
    handleDateChange,
    handleSubmit,
    resetForm,
    apiError,
    loading,
  };
};

export default useClimberRegistration;
