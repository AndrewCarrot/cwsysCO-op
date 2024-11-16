import {expect, test} from 'vitest';
import {validateEmail, validatePhoneNumber, isNotEmpty, validateDate} from '../utils/validateForm';
import {emails, phoneNumbers, strings, dates} from './testData';

test('validateEmail: valid email should return true', () => {
  expect(validateEmail(emails.correct)).toBe(true);
});

test('validateEmail: invalid emails should return false', () => {
  expect(validateEmail(emails.incorrect1)).toBe(false);
  expect(validateEmail(emails.incorrect2)).toBe(false);
  expect(validateEmail(emails.incorrect3)).toBe(false);
  expect(validateEmail(emails.incorrect4)).toBe(false);
});

test('validatePhoneNumber: valid phone number should return true', () => {
  expect(validatePhoneNumber(phoneNumbers.correct)).toBe(true);
});

test('validatePhoneNumber: invalid phone numbers should return false', () => {
  expect(validatePhoneNumber(phoneNumbers.short)).toBe(false);
  expect(validatePhoneNumber(phoneNumbers.long)).toBe(false);
  expect(validatePhoneNumber(phoneNumbers.nonNumeric)).toBe(false);
});

test('isNotEmpty: non-empty string should return true', () => {
  expect(isNotEmpty(strings.nonEmpty)).toBe(true);
});

test('isNotEmpty: empty or whitespace-only strings should return false', () => {
  expect(isNotEmpty(strings.empty)).toBe(false);
  expect(isNotEmpty(strings.whitespace)).toBe(false);
});

test('validateDate: valid date should return true', () => {
  expect(validateDate(dates.valid)).toBe(true);
});

test('validateDate: null or future dates should return false', () => {
  expect(validateDate(dates.nullDate)).toBe(false);
});
