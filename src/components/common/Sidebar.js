import React from "react";
import { NavLink } from "react-router-dom";
import "../../styles/Sidebar.css";
import { useSelector } from "react-redux";

export default function Sidebar() {
  const role = useSelector((state) => state.user.role);
  return (
    <div className="sidebar">
      <nav className="sidebar-links">
        {/* Admin-only routes */}
        {role !== "CUSTOMER" && (
          <>
            <NavLink
              to="/usermanagementdashboard"
              className={({ isActive }) =>
                isActive ? "nav-link active" : "nav-link"
              }>
              User Management
            </NavLink>
          </>
        )}
        {role === "ADMIN" && (
          <NavLink
            to="/onboardingdashboard"
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }>
            Onboarding
          </NavLink>
        )}

        {/* Common routes */}
        <NavLink
          to="/costexplorerdashboard"
          className={({ isActive }) =>
            isActive ? "nav-link active" : "nav-link"
          }>
          Cost Explorer
        </NavLink>
        <NavLink
          to="/awsservicesdashboard"
          className={({ isActive }) =>
            isActive ? "nav-link active" : "nav-link"
          }>
          AWS Services
        </NavLink>
      </nav>
    </div>
  );
}
