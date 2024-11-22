import {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {loginUser} from '../api/Auth';

export const useLogin = () => {
  const [formData, setFormData] = useState({
    usernameOrEmail: '',
    password: '',
  });
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {id, value} = e.target;
    setFormData({...formData, [id]: value});
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    try {
      await loginUser(formData);
      navigate('/dashboard');
    } catch (error: any) {
      setError('Invalid username or password. Please try again.');
    }
  };

  return {
    formData,
    error,
    handleInputChange,
    handleSubmit,
  };
};
