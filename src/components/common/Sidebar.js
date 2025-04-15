import React from "react";
import { NavLink } from "react-router-dom";
import "../../styles/Sidebar.css";
import { useSelector } from "react-redux";

export default function Sidebar() {
    const role = useSelector((state)=>state.role)
    return (
    <div className="sidebar">
      <nav className="sidebar-links">
        {/* Admin-only routes */}
        {role !== "CUSTOMER" && (
          <>
            <NavLink
              to="/usermanagementdashboard"
              className="nav-link"
              activeclassname="active">
              User Management
            </NavLink>
        </>
        )}
        {role==="ADMIN" && (

            <NavLink
            to="/onboardingdashboard"
            className="nav-link"
            activeclassname="active">
              Onboarding
            </NavLink>
            )}

        {/* Common routes */}
        <NavLink
          to="/costexplorerdashboard"
          className="nav-link"
          activeclassname="active">
          Cost Explorer
        </NavLink>
        <NavLink
          to="/awsservicesdashboard"
          className="nav-link"
          activeclassname="active">
          AWS Services
        </NavLink>
      </nav>
    </div>
  );
}
