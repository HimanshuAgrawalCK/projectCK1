import React, { useEffect, useState } from "react";
import { useOutletContext, useParams } from "react-router-dom";
import FilterAltOutlinedIcon from '@mui/icons-material/FilterAltOutlined';
import tableConfigs from "./tableConfig";
import ContentCopy from "@mui/icons-material/ContentCopy";
import "../../../styles/AwsResourceTable.css";

const AwsResourceTable = () => {
  const { serviceType } = useParams(); // from route `/awsservicesdashboard/:serviceType`
  const { selectedAccount } = useOutletContext(); // from AwsServicesDashboard
  const [data, setData] = useState([]);
  const [openFilter, setOpenFilter] = useState(null); // currently open filter column
  const [searchTerms, setSearchTerms] = useState({}); // search text per column
  const [filters, setFilters] = useState({}); // selected filter per column
  const [columnFilters, setColumnFilters] = useState({});

  const getFilteredValues = (key) => {
    const values = data.map((item) => item[key]).filter(Boolean);
    const uniqueValues = [...new Set(values)];
    const term = searchTerms[key] || "";
    return uniqueValues.filter((val) =>
      val.toLowerCase().includes(term.toLowerCase())
    );
  };

  const [loading, setLoading] = useState(true);

  const config = tableConfigs[serviceType?.toUpperCase()];

  const handleSearchChange = (key, value) => {
    setSearchTerms((prev) => ({ ...prev, [key]: value }));
  };

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const getUniqueValues = (key) => {
    if (!data || data.length === 0) return [];

    const values = data
      .map((item) => item[key])
      .filter((v) => v !== undefined && v !== null && v !== "");

    const unique = [...new Set(values)];
    console.log(`Unique values for ${key}:`, unique); // DEBUG LOG

    return unique;
  };

  useEffect(() => {
    const fetchData = async () => {
      if (!config || !selectedAccount) return;

      setLoading(true);
      try {
        const res = await config.fetchFunction(selectedAccount);
        console.log(`Fetched ${serviceType} data`, res);

        setData(res);
      } catch (err) {
        console.error(`Error fetching ${serviceType} data`, err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [config, selectedAccount]);

  if (!config) {
    return (
      <div className="error-message">Invalid service type: {serviceType}</div>
    );
  }

  return (
    <div className="aws-resource-container">
      {loading ? (
        <div className="loading-spinner">Loading...</div>
      ) : (
        <div>
          <h3>{serviceType.toUpperCase()} Resources</h3>
        <div className="table-wrapper">
          <table className="aws-table">
            <thead>
              <tr>
                {config.columns.map((col) => (
                  <th key={col.key}>
                    <div className="column-header">
                      {col.label}
                      <span
                        className="filter-icon"
                        onClick={() =>
                          setOpenFilter(openFilter === col.key ? null : col.key)
                        }>
                        <FilterAltOutlinedIcon sx={{color:"white"}}/>
                      </span>

                      {openFilter === col.key && (
                        <div className="filter-popup">
                          <input
                            type="text"
                            placeholder="Search..."
                            value={searchTerms[col.key] || ""}
                            onChange={(e) =>
                              setSearchTerms((prev) => ({
                                ...prev,
                                [col.key]: e.target.value,
                              }))
                            }
                            />
                          <div className="filter-list">
                            <div
                              className={`filter-item ${
                                !columnFilters[col.key] ? "selected" : ""
                              }`}
                              onClick={() => {
                                setColumnFilters((prev) => ({
                                  ...prev,
                                  [col.key]: null,
                                }));
                                setOpenFilter(null); // close popup
                              }}>
                              All
                            </div>
                            {getFilteredValues(col.key).map((val) => (
                              <div
                              key={val}
                              className={`filter-item ${
                                columnFilters[col.key] === val
                                ? "selected"
                                : ""
                              }`}
                              onClick={() => {
                                setColumnFilters((prev) => ({
                                    ...prev,
                                    [col.key]:
                                    prev[col.key] === val ? null : val, // toggle
                                  }));
                                  setOpenFilter(null); // close popup
                                }}>
                                {val}
                              </div>
                            ))}
                          </div>
                        </div>
                      )}
                    </div>
                  </th>
                ))}
              </tr>
            </thead>

            <tbody>
              {data
                .filter((row) =>
                  Object.entries(columnFilters).every(
                    ([key, value]) => !value || row[key] === value
                  )
                )
                .map((row, idx) => (
                  <tr key={idx}>
                    {config.columns.map((col) => (
                      <td key={col.key}>
                        {col.label === "Resource ID" ? (
                          <div className="resource-id-cell">
                            <span
                              className="ellipsis-text"
                              title={row[col.key]}>
                              {row[col.key]}
                            </span>
                            <ContentCopy
                              fontSize="small"
                              className="copy-icon"
                              onClick={() =>
                                navigator.clipboard.writeText(row[col.key])
                              }
                            />
                          </div>
                        ) : (
                          row[col.key]
                        )}
                      </td>
                    ))}
                  </tr>
                ))}
            </tbody>
          </table>
        </div>
                </div>
      )}
    </div>
  );
};

export default AwsResourceTable;
