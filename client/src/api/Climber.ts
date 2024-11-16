import axios from 'axios';
import {BASE_URL} from '../utils/consts';
import {climberData} from '../types/types';

export const createClimber = async (climberData: climberData) => {
  try {
    const response = await axios.post(`${BASE_URL}/api/climber/new`, climberData);
    return response.data;
  } catch (error) {
    console.error('Error creating a new climber:', error);
    throw error;
  }
};
