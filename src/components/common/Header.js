import React from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/Header.css";
import { logOut } from "../../api/Api"
import { showToast } from "./Toaster";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../../redux/Action";


export default function Header() {
    const dispatch = useDispatch();
    const {name} = useSelector((state)=> state.userDetails) || {};
  const logoutButton = async () => {
    try{
        const res = await logOut();
        dispatch(logout());
        // sessionStorage.removeItem("user");
        navigate("/");
    }
    catch(error){
        showToast(error);
    }
  };

  const navigate = useNavigate();
  return (
    <header className="header">
      <div className="header-left">
        <img src="images/cloudbalance.png" alt="CloudBalance" />
      </div>
      <div className="header-middle">
        <span>User Management Dashboard</span>
      </div>
      <div className="header-right">
        <span>{name}</span>
        <button className="logout" onClick={logoutButton}>
          Logout
        </button>
      </div>
    </header>
  );
}
