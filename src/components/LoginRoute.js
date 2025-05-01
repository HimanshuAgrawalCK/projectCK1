import { Navigate } from "react-router-dom";
import Login from "./Login.js";

const LoginRoute = () => {
  const isLoggedIn =
    localStorage.getItem("isLoggedIn") !== null &&
    localStorage.getItem("token") !== null &&
    localStorage.getItem("role") !== null &&
    localStorage.getItem("userData") !== null;

  const getRedirectPath = () => {
    const role = localStorage.getItem("role");
    return role === "CUSTOMER" ? "/awsservicesdashboard" : "/usermanagementdashboard";
  };

  return isLoggedIn ? <Navigate to={getRedirectPath()} /> : <Login />;
};

export default LoginRoute;
