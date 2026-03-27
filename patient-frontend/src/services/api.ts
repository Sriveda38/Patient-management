import axios from "axios";



const API_URL = "http://localhost:8080"; // your backend URL

export const loginUser = async (email: string, password: string) => {
  const response = await axios.post(`${API_URL}/login`, {
    email,
    password,
  });

  return response.data;
};