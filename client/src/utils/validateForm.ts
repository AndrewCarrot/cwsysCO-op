export const validateEmail = (email: string): boolean => {
  const emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/i;
  return emailRegex.test(email);
};
  
export const validatePhoneNumber = (phoneNumber: string): boolean => {
  const phoneRegex = /^\d{9}$/;
  return phoneRegex.test(phoneNumber);
};
  
export const isNotEmpty = (value: string): boolean => {
  return value.trim().length > 0;
};
  
export const validateDate = (date: Date | null): boolean => {
  return date !== null;
};
