import React, { useState, useEffect, useRef } from "react";
import "../../styles/AccountDropdown.css";
import { getAllAccounts } from "../../api/Api";

export default function AccountDropdown({ setListing, assigned = null, unassigned = null }) {
  const initialized = useRef(false);
  const [assignedAccounts, setAssignedAccounts] = useState(new Set());
  const [unassignedAccounts, setUnassignedAccounts] = useState(new Set());
  const [searchText, setSearchText] = useState("");
  const [dropdownVisible, setDropdownVisible] = useState(false);

  useEffect(() => {
    if (initialized.current) return;

    const initAccounts = async () => {
      try {
        if (assigned && unassigned) {
          // Coming from EditUser, accounts are already separated
          const assignedSet = new Set(assigned);
          const unassignedSet = new Set(unassigned);

          setAssignedAccounts(assignedSet);
          setUnassignedAccounts(unassignedSet);
          setListing(Array.from(assignedSet));
        } else {
          // Coming from AddNewUser, fetch all accounts
          const allAccounts = await getAllAccounts(0);
          const unassignedSet = new Set(allAccounts);

          setAssignedAccounts(new Set());
          setUnassignedAccounts(unassignedSet);
        }

        initialized.current = true;
      } catch (error) {
        console.error("Error initializing account data", error);
      }
    };

    initAccounts();
  }, [assigned, unassigned, setListing]);


  

  const handleAssign = (acc) => {
    setUnassignedAccounts((prev) => {
      const updated = new Set(prev);
      updated.delete(acc);
      return updated;
    });

    setAssignedAccounts((prev) => {
      const updated = new Set(prev);
      updated.add(acc);
      return updated;
    });

    setListing((prev) => {
      const updated = new Set(prev);
      updated.add(acc);
      return Array.from(updated);
    });
  };

  const handleUnassign = (acc) => {
    setAssignedAccounts((prev) => {
      const updated = new Set(prev);
      updated.delete(acc);
      return updated;
    });

    setUnassignedAccounts((prev) => {
      const updated = new Set(prev);
      updated.add(acc);
      return updated;
    });

    setListing((prev) =>
      Array.from(prev).filter((item) => item.accountId !== acc.accountId)
    );
  };

  const filteredUnassigned = Array.from(unassignedAccounts).filter((acc) =>
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
              {Array.from(assignedAccounts).map((acc) => (
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
