import { useEffect, useState } from "react";
import { getDistinctValues } from "../../../api/Api";
import "../../../styles/FilterSidebar.css";
import RestartAltIcon from '@mui/icons-material/RestartAlt';

export default function FilterSidebar({ columnList , onFilterChange}) {
  const [expandedColumn, setExpandedColumn] = useState(null);
  const [distinctValues, setDistinctValues] = useState({});
  const [selectedFilters, setSelectedFilters] = useState({});
  const [loading, setLoading] = useState({});
  const [error, setError] = useState({});
  const transformFiltersForBackend = (filters) => {
    const transformed = [];
    for (const column in filters) {
      filters[column].forEach((value) => {
        transformed.push({ column: column, value: value });
      });
    }
    return transformed;
  };
  


  useEffect(()=>{
    const backendFilters = transformFiltersForBackend(selectedFilters);
    console.log("Selected Filters are : ",backendFilters);
    onFilterChange(backendFilters)
  },[selectedFilters])

  const handleColumnClick = async (column) => {
    if (expandedColumn === column) {
      setExpandedColumn(null);
      return;
    }

    setExpandedColumn(column);

    if (!distinctValues[column]) {
      setLoading((prev) => ({ ...prev, [column]: true }));
      setError((prev) => ({ ...prev, [column]: null }));

      try {
        const response = await getDistinctValues(column);

        if (Array.isArray(response)) {
          setDistinctValues((prev) => ({
            ...prev,
            [column]: response,
          }));
        } else {
          throw new Error("Unexpected response format");
        }
      } catch (error) {
        setError((prev) => ({ ...prev, [column]: "Failed to load values" }));
        console.error("Error fetching distinct values:", error);
      } finally {
        setLoading((prev) => ({ ...prev, [column]: false }));
      }
    }
  };

  const handleCheckboxChange = (column, value) => {
    setSelectedFilters((prev) => {
      const currentValues = prev[column] || [];
      if (currentValues.includes(value)) {
        return {
          ...prev,
          [column]: currentValues.filter((item) => item !== value),
        };
      } else {
        return {
          ...prev,
          [column]: [...currentValues, value],
        };
      }
    });
  };

  const handleResetFilters = () => {
    setSelectedFilters({});
  };

  return (
    <div className="filter-sidebarr">
      <div className="filter-header">
        <h2 className="filter-title">Filters</h2>
        <button className="reset-button" onClick={handleResetFilters}>
          <RestartAltIcon/>
        </button>
      </div>

      {columnList.map((column) => (
        <div key={column} className="column-section">
          <div className="column-header">
            <input
              type="checkbox"
              checked={expandedColumn === column}
              onChange={() => handleColumnClick(column)}
            />
            <span className="column-label">{column}</span>
          </div>

          {expandedColumn === column && (
            <div className="options-section">
              {loading[column] ? (
                <div className="loading">Loading...</div>
              ) : error[column] ? (
                <div className="error">{error[column]}</div>
              ) : distinctValues[column]?.length === 0 ? (
                <div className="no-values">No values available</div>
              ) : (
                <div className="value-vertical-scroll">
                  {distinctValues[column].map((value) => (
                    <div className="value-item" key={value}>
                      <label>
                        <input
                          type="checkbox"
                          checked={
                            selectedFilters[column]?.includes(value) || false
                          }
                          onChange={() => handleCheckboxChange(column, value)}
                        />
                        {value}
                      </label>
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}
        </div>
      ))}
    </div>
  );
}
