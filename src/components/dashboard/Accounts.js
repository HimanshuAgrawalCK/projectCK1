import React, { useState, useEffect, useRef } from "react";
import "../../styles/AccountDropdown.css";
import { getAllAccounts } from "../../api/Api";

export default function AccountDropdown({ setListing, assigned = null, unassigned = null }) {
  const initialized = useRef(false);
  const [assignedAccounts, setAssignedAccounts] = useState([]);
  const [unassignedAccounts, setUnassignedAccounts] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [dropdownVisible, setDropdownVisible] = useState(false);

  useEffect(() => {
    if (initialized.current) return;

    const initAccounts = async () => {
      try {
        if (assigned && unassigned) {
          // Coming from EditUser
          setAssignedAccounts(assigned);
          setUnassignedAccounts(unassigned);
          setListing(assigned);
        } else {
          // Coming from AddNewUser
          const allAccounts = await getAllAccounts(0);
          setUnassignedAccounts(allAccounts);
        }

        initialized.current = true;
      } catch (error) {
        console.error("Error initializing account data", error);
      }
    };

    initAccounts();
  }, [assigned, unassigned, setListing]);

  useEffect(() => {
    setListing(assignedAccounts);
  }, [assignedAccounts, setListing]);
  

  const handleAssign = (acc) => {
    const updatedAssigned = [...assignedAccounts, acc];
    const updatedUnassigned = unassignedAccounts.filter((a) => a.accountId !== acc.accountId);

    setAssignedAccounts(updatedAssigned);
    setUnassignedAccounts(updatedUnassigned);
    setListing(updatedAssigned);
  };

  const handleUnassign = (acc) => {
    const updatedAssigned = assignedAccounts.filter((a) => a.accountId !== acc.accountId);
    const updatedUnassigned = [...unassignedAccounts, acc];

    setAssignedAccounts(updatedAssigned);
    setUnassignedAccounts(updatedUnassigned);
    setListing(updatedAssigned);
  };

  const filteredUnassigned = unassignedAccounts.filter((acc) =>
    acc.accountName?.toLowerCase().startsWith(searchText.toLowerCase())
  );

  const toggleDropdown = (e) => {
    e.preventDefault();
    setDropdownVisible(!dropdownVisible);
  };

  return (
    <div className="dropdown-container">
      <button type="button" onClick={toggleDropdown} className="dropdown-toggle">
        Manage Account Id(s)
      </button>

      {dropdownVisible && (
        <div className="dropdown-box">
          <input
            type="text"
            className="search-bar"
            placeholder="Search"
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
          />
          <div className="lists-container">
            <div className="account-list">
              <div className="list-header">Unassigned</div>
              {filteredUnassigned.map((acc) => (
                <div key={acc.accountId} className="account-item">
                  <input type="checkbox" checked={false} onChange={() => handleAssign(acc)} />
                  <span>
                    {acc.accountName} ({acc.accountId})
                  </span>
                </div>
              ))}
            </div>
            <div className="arrow-buttons">
              <button type="button" disabled>&rarr;</button>
              <button type="button" disabled>&larr;</button>
            </div>
            <div className="account-list">
              <div className="list-header">Assigned</div>
              {assignedAccounts.map((acc) => (
                <div key={acc.accountId} className="account-item">
                  <input type="checkbox" checked={true} onChange={() => handleUnassign(acc)} />
                  <span>
                    {acc.accountName} ({acc.accountId})
                  </span>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
