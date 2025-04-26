import React, { useEffect, useState } from "react";
import "../../../styles/CostExplorer.css";
import Sidebar from "../../common/Sidebar";
import Header from "../../common/Header";
import AccountSelectBox from "../../common/AccountSelectBox"; 
import GroupBySection from "./GroupBySection";
import FusionChartComponent from "./FusionChartComponent";
import { getAllColumns } from "../../../api/Api";

export default function CostExplorer() {
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState("");
  const [groupBy, setGroupBy] = useState("");
  const [showFilterSidebar, setShowFilterSidebar] = useState(false);
  const [columns, setColumns] = useState([]);

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const res = [
          { accountId: "1", accountName: "Account 1" },
          { accountId: "2", accountName: "Account 2" },
        ];
        const columnData = await getAllColumns();
        setColumns(columnData);
        setAccounts(res);
        if (res.length > 0) {
          setSelectedAccount(res[0].accountId);
        }
      } catch (err) {
        console.error("Error fetching accounts", err);
      }
    };
    fetchAccounts();
  }, []);

  const handleAccountChange = (e) => {
    setSelectedAccount(e.target.value);
  };

  const toggleFilterSidebar = () => {
    setShowFilterSidebar((prev) => !prev);
  };

  return (
    <div className="dashboard_wrapper">
      <div className="header_wrapper">
        <Header />
      </div>
      <div className="main_container">
        <div className="sidebar_wrapper">
          <Sidebar />
        </div>
        <div className="cost_explorer_container">
          <div className="cost_explorer_header">
            <div className="header_titleAndAccount">
              <h1>Cost Explorer</h1>
              <AccountSelectBox
                accounts={accounts}
                selectedAccount={selectedAccount}
                handleChange={handleAccountChange}
              />
            </div>
            <span>How to always be aware of cost changes and history</span>
          </div>

          <div className="cost_explorer_content">
  <div className="group_by_section">
    <GroupBySection
      groupBy={groupBy}
      setGroupBy={setGroupBy}
      toggleFilterSidebar={toggleFilterSidebar}
    />
  </div>

  <div className="fusionchart_and_filters">
    <div className="fusionchart_chart">
      <FusionChartComponent
        xKey="PRODUCT_PRODUCTNAME"
        yKey="TOTAL_AMOUNT"
        caption="Spend by Region"
        xAxisLabel="Region"
        yAxisLabel="Cost (₹)"
        chartType="column2d"
        requestPayload={{
          groupByName: "Product Name",
          accountId: "111111111111",
          startMonth: 3,
          startYear: 2025,
          endMonth: 5,
          endYear: 2025,
        }}
      />
      <div className="fusionchart_additional_content">
        <h3>More Cost Analysis Coming Soon...</h3>
        <p>Some graphs or tables can be placed here.</p>
      </div>
    </div>

    {showFilterSidebar && (
      <div className="fusionchart_filters">
        <h3>Available Columns</h3>
        <ul>
          {columns.length > 0 ? (
            columns.map((column, index) => (
              <li key={index}>{column}</li>
            ))
          ) : (
            <p>No columns found.</p>
          )}
        </ul>
      </div>
    )}
  </div>
</div>

        </div>
      </div>
    </div>
  );
}
