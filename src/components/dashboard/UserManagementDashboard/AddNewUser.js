import React, { useState } from "react";
import "../../../styles/AddNewUser.css";
import { useEffect } from "react";
import { fetchRoles, getAllAccounts, createUser } from "../../../api/Api";
import { showToast } from "../../common/Toaster";
import AccountDropdown from "../Accounts";

export default function AddNewUser({ onBack }) {
  const obj = {
    email: "",
    password: "",
    role: "CUSTOMER", // default role,
    name: "",
    accounts: [],
  };
  const [formData, setFormData] = useState(obj);
  const [listing, setListing] = useState([]);

  const [roles, setRoles] = useState([]);

  useEffect(() => {
    const getRoles = async () => {
      try {
        const res = await fetchRoles();
        setRoles(res);
        console.log("Roles fetched successfully", res);
      } catch (err) {
        console.error("Error fetching roles", err);
      }
    };

    getRoles();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({ ...prev, [name]: value, accounts: listing }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const accountIdSet = new Set(
        Array.from(listing).map((acc) => acc.accountId)
      );
      accountIdSet.forEach((ele) => console.log(ele));
      const finalData = {
        ...formData,
        accounts: Array.from(accountIdSet),
      };
      const response = await createUser(finalData);
      console.log("User created " + response);
      alert("User created successfully");
      setFormData({
        name: "",
        email: "",
        password: "",
        role: "",
        accounts: [],
      });
      showToast("User created successfully", 200);
    } catch (err) {
      debugger;
      console.error("Error creating user");
      showToast(err?.response?.data, err?.response?.status);
      debugger;
      alert("Error creating user");
    }
  };

  return (
    <>
      <div className="add-user-form-container">
        <h4>Add New User</h4>
        <form className="add-user-form" onSubmit={handleSubmit}>
          <label>
            Name:
            <input
              type="text"
              name="name"
              placeholder="Enter full name"
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
              placeholder="Enter email address"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </label>

          <label>
            Password:
            <input
              type="password"
              name="password"
              placeholder="Enter a password"
              value={formData.password}
              onChange={handleChange}
              required
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
          <button type="submit">Create User</button>
          <button type="button" className="back-button" onClick={onBack}>
            {" "}
            Back
          </button>
        </form>
      </div>
      {formData.role === "CUSTOMER" && (
        <AccountDropdown setListing={setListing} />
      )}
    </>
  );
}
