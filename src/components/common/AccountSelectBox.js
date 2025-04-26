import React from "react";
import Box from "@mui/material/Box";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";

const AccountSelectBox = ({ accounts, selectedAccount, handleChange }) => {
  return (
    <Box sx={{ minWidth: 120 }}>
      <FormControl fullWidth>
        <InputLabel id="account-label">Account</InputLabel>
        <Select
          labelId="account-label"
          value={selectedAccount}
          label="Account"
          onChange={handleChange}
        >
          {accounts && accounts.length > 0 ? (
            accounts.map((acc) => (
              <MenuItem value={acc.accountId} key={acc.accountId}>
                {acc.accountName}
              </MenuItem>
            ))
          ) : (
            <MenuItem disabled>No accounts available</MenuItem>
          )}
        </Select>
      </FormControl>
    </Box>
  );
};

export default AccountSelectBox;
