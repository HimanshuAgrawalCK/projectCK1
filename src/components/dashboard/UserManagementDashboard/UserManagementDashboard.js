import React, { useState, useEffect } from "react";
import "../../../styles/UserManagementDashboard.css";
import "../../../styles/OnboardingDashboard.css";

import Sidebar from "../../common/Sidebar";
import Header from "../../common/Header";
import { fetchUsers } from "../../../api/Api";
import AddNewUser from "./AddNewUser";
import EditUser from "./EditUser";
import { useDispatch, useSelector } from "react-redux";
import IconButton from "@mui/material/IconButton";
import EditIcon from "@mui/icons-material/Edit";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
} from "@mui/material";
import { logout } from "../../../redux/Action";

import { showToast } from "../../common/Toaster";

export default function UserManagementDashboard() {
  const [loading, setLoading] = useState(true);
  const [users, setUsers] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [addUser, setAddUser] = useState(false);
  const [editUserId, setEditUserId] = useState(null);
 const dispatch = useDispatch();
  const userDetails = useSelector((state) => state.user);

  useEffect(() => {
    const getUsers = async () => {
      try {
        const res = await fetchUsers(page, 10);
        setUsers(res?.content);
        setTotalPages(res?.totalPages);
      } catch (error) {
        showToast(error?.response?.data,error?.status);
        dispatch(logout());
        console.error("Error fetching users", error);
      } finally {
        setLoading(false);
      }
    };
    getUsers();
  }, [page,editUserId,addUser]);

  const handleEditButton = (userId) => {
    setEditUserId(userId);
  };

  const handleBackFromEdit = () => {
    setEditUserId(null);
  };

  const handleBackFromAdd = () => {
    setAddUser(false);
  };

  let contentToRender;

  if (editUserId) {
    contentToRender = (
      <EditUser userId={editUserId} onBack={handleBackFromEdit} />
    );
  } else if (addUser) {
    contentToRender = <AddNewUser onBack={handleBackFromAdd} />;
  } else {
    contentToRender = (
      <>
        <h1>Users</h1>
        <div>
          {userDetails?.role === "ADMIN" && (
            <button
              className="add-user-button"
              onClick={() => setAddUser(true)}>
              Add User +
            </button>
          )}

          <TableContainer component={Paper} className="user-table">
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>ID</TableCell>
                  <TableCell>Name</TableCell>
                  <TableCell>Email</TableCell>
                  <TableCell>Last Login Time</TableCell>
                  <TableCell>Role</TableCell>
                  {userDetails.role==="ADMIN" && 
                  <TableCell>Actions</TableCell>
                  }
                </TableRow>
              </TableHead>
              <TableBody>
                {users.map((user) => (
                  <TableRow key={user.id} hover>
                    <TableCell>{user.id}</TableCell>
                    <TableCell>{user.name}</TableCell>
                    <TableCell>{user.email}</TableCell>
                    <TableCell>{user.lastLoginTime}</TableCell>
                    <TableCell>{user.roleName}</TableCell>
                    {userDetails.role === "ADMIN" && (
                      <TableCell>
                        <IconButton
                          onClick={() => handleEditButton(user.id)}
                          aria-label="edit">
                          <EditIcon color="primary" />
                        </IconButton>
                      </TableCell>
                    )}
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </div>

        <div className="pagination-buttons">
          <button
            onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
            disabled={page === 0}>
            Previous
          </button>

          <span>
            Page {page + 1} of {totalPages}
          </span>

          <button
            onClick={() =>
              setPage((prev) => Math.min(prev + 1, totalPages - 1))
            }
            disabled={page >= totalPages - 1}>
            Next
          </button>
        </div>
      </>
    );
  }

  return (
    <div className="dashboard_wrapper">
      <div className="header_wrapper">
      <Header />
      </div>

      <div className="main_container">
        <div className="sidebar_wrapper">
        <Sidebar />
          </div>     
        <div className="userDashboard-content">{contentToRender}</div>
      </div>
    </div>
  );
}
