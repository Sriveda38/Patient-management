import { Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Patient from "./pages/Paitent";
import Layout from "./components/Layout";
import AddPatient from "./pages/AddPatient";


function App() {
  return (
    
      <Routes>

        <Route path="/login" element={<Login />} />

        
        <Route element={<Layout />}>
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/patient" element={<Patient />} />
          <Route path="/addPatient" element={<AddPatient/> } />
        </Route>

      </Routes>
    
  );
}

export default App;