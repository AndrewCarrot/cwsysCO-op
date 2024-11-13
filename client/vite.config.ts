import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    host: '0.0.0.0', // Allows access from Docker
    port: 3000,
    watch: {
      usePolling: true, // Enable polling for file changes in Docker
    },
  },
});