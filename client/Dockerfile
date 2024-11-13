# Use Node.js 20 as the base image
FROM node:20

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install dependencies, including nodemon
RUN npm install && npm install -g nodemon

# Copy the rest of the application files
COPY . .

# Expose Vite's default development port
EXPOSE 3000

# Start the Vite development server using nodemon to watch for .ts and .tsx files
CMD ["nodemon", "--watch", "src", "--ext", "ts,tsx", "--exec", "npm run dev -- --host"]
