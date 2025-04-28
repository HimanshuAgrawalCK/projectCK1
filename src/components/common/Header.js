import React from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/Header.css";
import { logOut } from "../../api/Api"
import { showToast } from "./Toaster";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../../redux/Action";


export default function Header() {
    const dispatch = useDispatch();
    const {name} = useSelector((state)=> state.user.userDetails) || {};
  const logoutButton = async () => {
    try{
        const res = await logOut();
        dispatch(logout());
        // sessionStorage.removeItem("user");
        // navigate("/login");
    }
    catch(error){
        showToast(error);
    }
  };

  const navigate = useNavigate();
  return (
    <header className="header">
      <div className="header-left">
        <img src="images/cloudbalancelogo (2) (3).png" alt="CloudBalance" style={{width:"15vh"}}/>
      </div>
      <div className="header-middle">
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
