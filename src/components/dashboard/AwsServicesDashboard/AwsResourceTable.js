import React, { useEffect, useState } from "react";
import { useOutletContext, useParams } from "react-router-dom";
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import tableConfigs from "./tableConfig";
import ContentCopy from "@mui/icons-material/ContentCopy";
import "../../../styles/AwsResourceTable.css";
import NoDataExists from "./NoDataExists";
import { showToast } from "../../common/Toaster";

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
  useEffect(() => {
    // Reset filters and search terms when serviceType changes
    setSearchTerms({});
    setFilters({});
    setColumnFilters({});
    setOpenFilter(null);
    setData([]);
  }, [serviceType]);
  

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
        
        <div className="table-wrapper">
          {data.length==0 ? <><NoDataExists/></> :
          <table className="aws-table">
            <thead>
              <tr>
                {config.columns.map((col) => (
                  <th key={col.key}>
                    <div className="column-header1">
                      {col.label}
                      <span
                        className="filter-icon"
                        onClick={() =>
                          setOpenFilter(openFilter === col.key ? null : col.key)
                        }>
                        <FilterAltIcon sx={{color:"white"}}/>
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
                              onClick={() =>{
                                showToast("Copied to Clipboard",200);
                                navigator.clipboard.writeText(row[col.key])
                              }}
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
              }
        </div>
      )}
    </div>
  );
};

export default AwsResourceTable;