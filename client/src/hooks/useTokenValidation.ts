import {useState, useEffect} from 'react';
import {validateToken} from '../api/Auth';

export const useTokenValidation = (): [boolean | null, () => void] => {
  const [isTokenValid, setIsTokenValid] = useState<boolean | null>(null);
  const token: string | null = sessionStorage.getItem('token');

  const validate = async () => {
    if (!token) {
      setIsTokenValid(false);
      return;
    }

    try {
      const response = await validateToken(token);
      setIsTokenValid(response?.status === 200);
    } catch (error) {
      console.error('Error during token validation:', error);
      setIsTokenValid(false);
    }
  };

  useEffect(() => {
    validate();
  }, [token]);

  return [isTokenValid, validate];
};
