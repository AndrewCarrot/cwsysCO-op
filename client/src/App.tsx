import { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [count, setCount] = useState(0);

  // Use useEffect to fetch data when the component mounts
  useEffect(() => {
    // Fetch data from localhost:8080/coach/all
    fetch('http://localhost:8080/api/coach/all')
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        console.log('Fetched data:', data); // Log the fetched data
      })
      .catch((error) => {
        console.error('Error fetching data:', error); // Log any errors
      });
  }, []); // Empty dependency array means this runs once on component mount
   
  return (
    <>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.tsx</code> and save to test
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  );
}

export default App;
