import React, { useState } from "react";
import "../styles/Login.css";
import { loginUser } from "../api/Api";
import { showToast } from "../components/common/Toaster";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { loginSuccess } from "../redux/Action";
import Page404 from "./common/Page404";

function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const dispatch = useDispatch();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const data = await loginUser(email, password);

      const userData = {
        id: data.id,
        email: data.email,
        name: data.name,
      }
      localStorage.setItem("userData", JSON.stringify(userData));
      localStorage.setItem("token", data.token);
      localStorage.setItem("role", data.role);
      localStorage.setItem("isLoggedIn", true);
      dispatch(loginSuccess(data.token,data.role,userData))

      if (data?.role === "CUSTOMER") {
        navigate("/awsservicesdashboard");
      } else {
        navigate("/usermanagementdashboard");
      }
      showToast("Login successful", 200);
    } catch (error) {
      showToast(
        error?.response?.data?.message || "Login failed",
        error?.response?.data?.status || 500
      );
    }
  };

  return (
    <div className="login-wrapper">
          <img src="/images/cloudbalancelogo (2).png" alt="CloudKeeper" style={{width:"40%"}}/>
      <div className="login-box">
        <form className="login-form" onSubmit={handleLogin}>
          <label htmlFor="email">Email</label>
          <input
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            type="email"
            id="email"
            placeholder="Enter your email"
            required
          />

          <label htmlFor="password">Password</label>
          <input
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            type="password"
            id="password"
            placeholder="Enter your password"
            required
          />

          <button type="submit" className="login-button">
            LOGIN
          </button>
        </form>
      </div>
    </div>
  );
}

export default Login;
