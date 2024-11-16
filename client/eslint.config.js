// eslint.config.js (Flat Config)

import eslintPluginReact from 'eslint-plugin-react';
import typescriptParser from '@typescript-eslint/parser';
import eslintPluginTypeScript from '@typescript-eslint/eslint-plugin';

export default [
  {
    files: ['**/*.{js,jsx,ts,tsx}'],
    languageOptions: {
      parser: typescriptParser,
      ecmaVersion: 'latest',
      sourceType: 'module',
    },
    plugins: {
      react: eslintPluginReact,
      '@typescript-eslint': eslintPluginTypeScript,
    },
    rules: {
      'quotes': ['error', 'single'], // Enforce single quotes in JavaScript strings
      'jsx-quotes': ['error', 'prefer-single'], // Enforce single quotes in JSX attributes
      'max-len': ['error', {code: 120}], // Limit line length to 120 characters
      'eol-last': ['error', 'always'], // Require newline at the end of files
      'semi': ['error', 'always'], // Enforce semicolons at the end of statements
      'indent': ['error', 2], // Enforce 2-space indentation
      'comma-dangle': ['error', 'only-multiline'], // Only multiline trailing commas
      'react/prop-types': 'off', // Disable prop-types as we're using TypeScript
      'react/react-in-jsx-scope': 'off', // Not needed in React 17+ with new JSX transform
      '@typescript-eslint/explicit-module-boundary-types': 'off', // No need to enforce return types
      '@typescript-eslint/no-unused-vars': ['warn'], // Ignore unused vars prefixed with _
      'object-curly-spacing': ['error', 'never'], // Disallow spaces inside curly braces
    },
    settings: {
      react: {
        version: 'detect', // Automatically detect React version
      },
    },
  },
];
