import {BrowserRouter, Routes, Route} from 'react-router-dom';
import Login from './components/Login/Login';
import Register from './components/Register/Register';
import Dashboard from './components/Dashboard/Dashboard';
import Users from './components/Users/Users';
import Sections from './components/Sections/Sections';
import Groups from './components/Groups/Groups';
import NotFound from './components/NotFound/NotFound'; 

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Login />} />
        <Route path='/register' element={<Register />} />

        <Route path='/dashboard' element={<Dashboard />} />
        <Route path='/users' element={<Users />} />
        <Route path='/groups' element={<Groups />} />
        <Route path='/sections' element={<Sections />} />

        <Route path='*' element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
