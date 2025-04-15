import React, { useState, useEffect } from "react";
import "../../../styles/UserManagementDashboard.css";
import Sidebar from "../../common/Sidebar";
import Header from "../../common/Header";
import { fetchUsers } from "../../../api/Api";
import AddNewUser from "./AddNewUser";
import EditUser from "./EditUser";
import { useSelector } from "react-redux";

export default function UserManagementDashboard() {
  const [loading, setLoading] = useState(true);
  const [users, setUsers] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [addUser, setAddUser] = useState(false);
  const [editUserId, setEditUserId] = useState(null);

  const userDetails = useSelector((state) => state);

  useEffect(() => {
    const getUsers = async () => {
      try {
        const res = await fetchUsers(page, 8);
        setUsers(res?.content);
        setTotalPages(res?.totalPages);
      } catch (error) {
        console.error("Error fetching users", error);
      } finally {
        setLoading(false);
      }
    };
    getUsers();
  }, [page]);

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
    contentToRender = <EditUser userId={editUserId} onBack={handleBackFromEdit} />;
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
              onClick={() => setAddUser(true)}
            >
              Add User +
            </button>
          )}

          <table className="user-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>{user.roleName}</td>
                  <td>
                    <button
                      className="edit-button"
                      onClick={() => handleEditButton(user.id)}
                    />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="pagination-buttons">
          <button
            onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
            disabled={page === 0}
          >
            Previous
          </button>

          <span>
            Page {page + 1} of {totalPages}
          </span>

          <button
            onClick={() =>
              setPage((prev) => Math.min(prev + 1, totalPages - 1))
            }
            disabled={page >= totalPages - 1}
          >
            Next
          </button>
        </div>
      </>
    );
  }

  return (
    <>
      <Header />
      <div className="userDashboard">
        <Sidebar />
        <div className="userDashboard-content">
          {contentToRender}
        </div>
      </div>
    </>
  );
}
