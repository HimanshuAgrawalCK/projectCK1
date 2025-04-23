import React, { useEffect, useState } from "react";
import "../../../styles/EditUser.css";
import Sidebar from "../../common/Sidebar";
import Header from "../../common/Header";
import {
  fetchRoles,
  getAllAccounts,
  updateUser,
  getUserDetails,
} from "../../../api/Api";
import { toast } from "react-toastify";
import AccountDropdown from "../Accounts";

export default function EditUser({ userId = 0, onBack }) {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    role: "",
    accounts: [],
  });

  const [roles, setRoles] = useState([]);
  const [listing, setListing] = useState([]);
  const [assigned, setAssigned] = useState([]);
  const [unassigned, setUnassigned] = useState([]);


  useEffect(() => {
    const fetchData = async () => {
      try {
        const user = await getUserDetails(userId);
        const allRoles = await fetchRoles();
        const allAccounts = await getAllAccounts(0);
        if (user.role === "CUSTOMER") {
          const response = await getAllAccounts(userId);
          console.log("The assigned accounts response : ", response);
          setAssigned(response);

          const assignedIds = new Set(response.map((a) => a.accountId));
          console.log("assignedIds", assignedIds);
          const unassignedAccounts = allAccounts.filter(
            (acc) => !assignedIds.has(acc.accountId)
          );
          console.log("unassignedAccounts", unassignedAccounts);
          setUnassigned(unassignedAccounts);
          setListing(assigned);
        } else {
            setUnassigned(allAccounts);
            setAssigned([]);
            setListing([]);
        }

        setFormData({
          name: user.name,
          email: user.email,
          role: user.role,
          accounts: user.accounts.map((acc) => acc.accountId),
        });

        setRoles(allRoles);
      } catch (error) {
        console.error("Error fetching user details", error);
      }
    };

    fetchData();
    
  }, [userId]);

    
  

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      console.log("Form data before submission: ", formData);
      console.log("Listing before submission: ", listing);
      const finalData = {
        ...formData,
        accounts: Array.from(listing).map((acc) => acc.accountId),
      };
      const response = await updateUser(userId, finalData);
      console.log(response)
      toast.success("User updated successfully!");
      if (onBack) onBack();
    } catch (error) {
      console.error("Failed to update user", error);
      toast.error("Failed to update user");
    }
  };

  return (
    <>
      <Header />
      <div className="edit-user-page">
        <Sidebar />
        <div className="edit-user-form-container">
          <h2>Edit User</h2>
          <form className="edit-user-form" onSubmit={handleSubmit}>
            <label>
              Name:
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
                required
              />
            </label>

            <label>
              Email:
              <input
                type="email"
                name="email"
                value={formData.email}
                readOnly
              />
            </label>

            <label>
              Role:
              <select name="role" value={formData.role} onChange={handleChange}>
                {roles.map((role) => (
                  <option key={role} value={role}>
                    {role}
                  </option>
                ))}
              </select>
            </label>

            {formData.role === "CUSTOMER" && (
              <AccountDropdown
                assigned={assigned}
                unassigned={unassigned}
                setListing={setListing}
              />
            )}

            <button type="submit">Update User</button>
            <button type="button" className="back-button" onClick={onBack}>
              Back
            </button>
          </form>
        </div>
      </div>
    </>
  );
}
