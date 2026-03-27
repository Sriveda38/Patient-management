import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@mui/material";
import axios from "axios";
import "./Login.css";

function Login() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [loading, setLoading] = useState(false);

  // Handle input change
  const handleChange = (e: any) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  // Handle login submit
  const handleSubmit = async (e: any) => {
    e.preventDefault();

    try {
      setLoading(true);

      const res = await axios.post("http://localhost:8080/login", {
        email: formData.email,
        password: formData.password,
      });

      console.log(res.data)
      localStorage.setItem("token", res.data.token);
      localStorage.setItem("role", res.data.role)
      
      navigate("/dashboard");

    } catch (err) {
      console.error(err);
      alert("Invalid credentials");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h2>Login</h2>

        <form onSubmit={handleSubmit}>
          <input
            type="email"
            name="email"
            placeholder="Type your email"
            value={formData.email}
            onChange={handleChange}
            required
          />

          <input
            type="password"
            name="password"
            placeholder="Type your password"
            value={formData.password}
            onChange={handleChange}
            required
          />

          <Button
            type="submit"
            variant="contained"
            fullWidth
            disabled={loading}
            sx={{
              background: 'linear-gradient(90deg, #36D1DC, #5B86E5, #C86DD7)',
              color: '#fff',
              borderRadius: '30px',
              padding: '12px 0',
              fontSize: '16px',
              fontWeight: 'bold',
              textTransform: 'none',
              boxShadow: 'none',
              '&:hover': {
                background: 'linear-gradient(90deg, #36D1DC, #5B86E5, #C86DD7)',
                opacity: 0.9,
                boxShadow: 'none',
              },
            }}
          >
            {loading ? "Logging in..." : "LOGIN"}
          </Button>
        </form>
      </div>
    </div>
  );
}

export default Login;