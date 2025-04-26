import React, { useEffect, useState } from "react";
import "../../../styles/GroupBySection.css";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { getAllColumns } from "../../../api/Api";

export default function GroupBySection({ groupBy, setGroupBy, toggleFilterSidebar }) {
  const [columns, setColumns] = useState([]);
  const [loading, setLoading] = useState(true);
  const visibleCount = 6;

  useEffect(() => {
    const fetchColumns = async () => {
      try {
        const columnData = await getAllColumns();
        setColumns(columnData);
        if (columnData.length > 0) {
          setGroupBy([columnData[0]]);
        }
      } catch (err) {
        console.error("Error fetching columns", err);
      } finally {
        setLoading(false);
      }
    };

    fetchColumns();
  }, [setGroupBy]);

  const handleSelect = (selected) => {
    const updatedColumns = columns.filter((col) => col !== selected);
    const reordered = [selected, ...updatedColumns];
    setColumns(reordered);
    setGroupBy([selected]);
  };

  const visibleColumns = columns.slice(0, visibleCount);
  const hiddenColumns = columns.slice(visibleCount);

  return (
    <div className="group_by_section">
      <span>Group by: </span>
      {loading ? (
        <span>Loading...</span>
      ) : (
        <>
          {visibleColumns.map((column) => (
            <Button
              key={column}
              variant="contained"
              className={groupBy.includes(column) ? "active_column" : "not_active_column"}
              sx={{
                textTransform: "none",
                backgroundColor: groupBy.includes(column) ? "#093ca2" : "#fff",
                color: groupBy.includes(column) ? "#fff" : "#093ca2",
                transition: "all 0.3s ease",
              }}
              onClick={() => handleSelect(column)}>
              <span className="group_by_column">{column}</span>
            </Button>
          ))}

          {hiddenColumns.length > 0 && (
            <Box sx={{ minWidth: 140 }}>
              <FormControl fullWidth>
                <InputLabel id="more-columns-label">More</InputLabel>
                <Select
                  labelId="more-columns-label"
                  id="more-columns"
                  value=""
                  label="More"
                  onChange={(e) => handleSelect(e.target.value)}
                  sx={{ backgroundColor: "transparent", color: "white" }}>
                  {hiddenColumns.map((column) => (
                    <MenuItem key={column} value={column}>
                      {column}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Box>
          )}
        </>
      )}
      <button className="toggle_filter_btn" onClick={toggleFilterSidebar}>
        Toggle Filters
      </button>
    </div>
  );
}
