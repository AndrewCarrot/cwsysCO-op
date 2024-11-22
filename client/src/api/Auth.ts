import axios from 'axios';
import {BASE_URL} from '../utils/consts';
import {loginData} from '../types/types';

export const loginUser = async (loginData: loginData) => {
  try {
    const response = await axios.post(`${BASE_URL}/api/auth/login`, loginData);
    const {token} = response.data;

    sessionStorage.setItem('token', token);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const validateToken = async (token: string | null) => {
  if (!token) {
    console.error('Token is null or undefined');
    return null;
  }

  try {
    const response = await axios.post(`${BASE_URL}/api/auth/validate`, {token});
    return response;
  } catch (error) {
    throw error;
  }
};
