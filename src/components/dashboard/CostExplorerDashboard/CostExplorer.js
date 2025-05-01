"use client"

import { useEffect, useState } from "react"
import "../../../styles/CostExplorer.css"
import Sidebar from "../../common/Sidebar"
import Header from "../../common/Header"
import AccountSelectBox from "../../common/AccountSelectBox"
import GroupBySection from "./GroupBySection"
import { getAllAccounts, getAllColumns } from "../../../api/Api"
import * as XLSX from "xlsx"

import FusionChartComponent from "./FusionChartComponent"
import FilterSidebar from "./FilterSidebar"
import ShowChartIcon from "@mui/icons-material/ShowChart"
import BarChartIcon from "@mui/icons-material/BarChart"
import { useSelector } from "react-redux"
import { showToast } from "../../common/Toaster"

export default function CostExplorer() {
  const handleDownloadExcel = () => {
    // Debugging: Check the tableData and usageMonths before proceeding
    console.log("Table Data:", tableData)
    console.log("Usage Months:", usageMonths)

    // If tableData is empty, don't proceed with the download
    if (!tableData || tableData.length === 0) {
      showToast("No Data Available", 100)
      console.log("No data to download")
      return
    }

    // Check if usageMonths is populated
    if (!usageMonths || usageMonths.length === 0) {
      console.log("No usage months to include in the Excel file")
      return
    }

    const worksheetData = [
      ["Service Name", ...usageMonths, "Total"], // Include 'Total' as the last column
      ...tableData.map((service) => [
        service.serviceName,
        ...usageMonths.map((month) => service.usageByMonth[month] || 0),
        service.total, // Add the total value for each service
      ]),
    ]

    const worksheet = XLSX.utils.aoa_to_sheet(worksheetData)
    const workbook = XLSX.utils.book_new()
    XLSX.utils.book_append_sheet(workbook, worksheet, "Service Usage")

    // Download the file
    XLSX.writeFile(workbook, "ServiceUsage.xlsx")
  }

  const [accounts, setAccounts] = useState([])
  const [selectedAccount, setSelectedAccount] = useState("")
  const [groupBy, setGroupBy] = useState("")
  const [tableData, setTableData] = useState([])
  const [usageMonths, setUsageMonths] = useState([])
  const [isLoading, setIsLoading] = useState(false)

  const [showFilterSidebar, setShowFilterSidebar] = useState(false)
  const [columns, setColumns] = useState([])

  const [startMonth, setStartMonth] = useState(12)
  const [startYear, setStartYear] = useState(2024)
  const [endMonth, setEndMonth] = useState(5)
  const [endYear, setEndYear] = useState(2025)
  const [isBarChart, setIsBarChart] = useState(true)
  const [chartData, setChartData] = useState([])
  const [filters, setFilters] = useState([])
  const { userDetails } = useSelector((state) => state.user)
  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const res = await getAllAccounts(userDetails.id)
        console.log("Response is :", res)
        const columnData = await getAllColumns()
        console.log("columnList", columnData)
        setColumns(columnData)
        setAccounts(res)
        if (res.length > 0) {
          setSelectedAccount(res[0].accountId)
        }
      } catch (err) {
        console.error("Error fetching accounts", err)
      }
    }
    fetchAccounts()
  }, [])

  const handleFiltersChange = (newFilters) => {
    setFilters(newFilters)
  }

  const handleChartData = (data) => {
    // Set the chart data
    setChartData(data)

    // Check if data is empty
    if (!data || data.length === 0) {
      setTableData([])
      setUsageMonths([])
      return
    }

    // Process chart data to extract tableData and usageMonths
    const months = [...new Set(data.map((item) => item["USAGE_MONTH"] || item.value))].sort()
    setUsageMonths(months)

    const serviceData = data.reduce((acc, item) => {
      const serviceName = item[groupBy] || item.label
      if (!acc[serviceName]) acc[serviceName] = []

      const amount = item["TOTAL_AMOUNT"] || item.value || 0
      acc[serviceName].push(amount)

      return acc
    }, {})

    const formattedTableData = Object.entries(serviceData).map(([serviceName, amounts]) => {
      const total = amounts.reduce((sum, amount) => sum + amount, 0) // Calculate total
      return {
        serviceName,
        usageByMonth: amounts.reduce((acc, amount, idx) => {
          acc[months[idx]] = amount // Map each month to the amount for that service
          return acc
        }, {}),
        total,
      }
    })

    setTableData(formattedTableData)
  }

  const handleAccountChange = (e) => {
    setSelectedAccount(e.target.value)
  }

  const toggleFilterSidebar = () => {
    setShowFilterSidebar((prev) => !prev)
  }

  const handleStartMonthChange = (e) => {
    setStartMonth(e.target.value)
  }

  const handleStartYearChange = (e) => {
    setStartYear(e.target.value)
  }

  const handleEndMonthChange = (e) => {
    setEndMonth(e.target.value)
  }

  const handleEndYearChange = (e) => {
    setEndYear(e.target.value)
  }

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
              <div style={{ display: "flex", flexDirection: "column" }}>
                <h1>Cost Explorer</h1>
                <span>How to always be aware of cost changes and history</span>
              </div>
              <AccountSelectBox
                accounts={accounts}
                selectedAccount={selectedAccount}
                handleChange={handleAccountChange}
              />
            </div>
          </div>

          <div className="cost_explorer_content">
            <div className="group_by">
              <GroupBySection groupBy={groupBy} setGroupBy={setGroupBy} toggleFilterSidebar={toggleFilterSidebar} />
            </div>
            <div className="fusionchart_container">
              {isLoading && (
                <div className="loading-indicator">
                  <div className="spinner"></div>
                  <p>Loading cost data...</p>
                </div>
              )}
              <div className={showFilterSidebar ? "fusionchart_wrapper" : "fusionchart_wrapper_without_filter"}>
                <div className="fusionchart_wrapper_header">
                  <pre>Cost ($)</pre>
                  <div className="wrapper_header_form">
                    {/* Start and End Month, Year Inputs */}
                    <div className="date_picker">
                      <div>
                        <select value={startMonth} onChange={handleStartMonthChange} className="month_year_dropdown">
                          {["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"].map(
                            (month, index) => (
                              <option key={month} value={index + 1}>
                                {month}
                              </option>
                            ),
                          )}
                        </select>
                      </div>
                      <div>
                        <select value={startYear} onChange={handleStartYearChange} className="month_year_dropdown">
                          {[2021, 2022, 2023, 2024, 2025].map((year) => (
                            <option key={year} value={year}>
                              {year}
                            </option>
                          ))}
                        </select>
                      </div>
                      <div>
                        <select value={endMonth} onChange={handleEndMonthChange} className="month_year_dropdown">
                          {["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"].map(
                            (month, index) => (
                              <option key={month} value={index + 1}>
                                {month}
                              </option>
                            ),
                          )}
                        </select>
                      </div>
                      <div>
                        <select value={endYear} onChange={handleEndYearChange} className="month_year_dropdown">
                          {[2021, 2022, 2023, 2024, 2025].map((year) => (
                            <option key={year} value={year}>
                              {year}
                            </option>
                          ))}
                        </select>
                      </div>
                    </div>
                    <div className="chart_type_toggle">
                      <button className={!isBarChart ? "active" : ""} onClick={() => setIsBarChart(false)}>
                        <ShowChartIcon />
                      </button>
                      <button className={isBarChart ? "active" : ""} onClick={() => setIsBarChart(true)}>
                        <BarChartIcon />
                      </button>
                    </div>
                  </div>
                </div>
                <FusionChartComponent
                  groupByName={groupBy}
                  filters={filters}
                  accountId={selectedAccount}
                  startMonth={startMonth}
                  startYear={startYear}
                  endMonth={endMonth}
                  endYear={endYear}
                  chartType={isBarChart ? "mscolumn2d" : "msline"}
                  setBackendData={handleChartData}
                  setIsLoading={setIsLoading} // Pass the loading state setter
                />

                <div className="additonal_content1">
                  <pre>We are showing up to top {chartData.length} results</pre>
                </div>
                <div className="table_section">
                  <div className="table_header">
                    <h3>{groupBy} Usage Table</h3>
                    <button onClick={handleDownloadExcel} className="excel_button">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        x="0px"
                        y="0px"
                        width="20"
                        height="20"
                        viewBox="0 0 24 24"
                        className="svg_icon"
                      >
                        <path d="M 12 3 L 2 5 L 2 19 L 12 21 L 12 3 z M 14 5 L 14 7 L 16 7 L 16 9 L 14 9 L 14 11 L 16 11 L 16 13 L 14 13 L 14 15 L 16 15 L 16 17 L 14 17 L 14 19 L 21 19 C 21.552 19 22 18.552 22 18 L 22 6 C 22 5.448 21.552 5 21 5 L 14 5 z M 18 7 L 20 7 L 20 9 L 18 9 L 18 7 z M 4.1757812 8.296875 L 5.953125 8.296875 L 6.8769531 10.511719 C 6.9519531 10.692719 7.0084063 10.902625 7.0664062 11.140625 L 7.0917969 11.140625 C 7.1247969 10.997625 7.1919688 10.779141 7.2929688 10.494141 L 8.3222656 8.296875 L 9.9433594 8.296875 L 8.0078125 11.966797 L 10 15.703125 L 8.2714844 15.703125 L 7.1582031 13.289062 C 7.1162031 13.204062 7.0663906 13.032922 7.0253906 12.794922 L 7.0097656 12.794922 C 6.9847656 12.908922 6.934375 13.079594 6.859375 13.308594 L 5.7363281 15.703125 L 4 15.703125 L 6.0605469 11.996094 L 4.1757812 8.296875 z M 18 11 L 20 11 L 20 13 L 18 13 L 18 11 z M 18 15 L 20 15 L 20 17 L 18 17 L 18 15 z"></path>
                      </svg>
                    </button>
                  </div>

                  <div className="table_wrapper">
                    <table>
                      <thead>
                        <tr>
                          <th>{groupBy}</th>
                          {[...new Set(chartData.map((item) => item["USAGE_MONTH"] || item.value))]
                            .sort() // Optional: if you want months sorted
                            .map((month, idx) => (
                              <th key={month + idx}>{month.toLocaleString()}</th>
                            ))}
                          <th>Total</th>
                        </tr>
                      </thead>

                      <tbody>
                        {Object.entries(
                          chartData.reduce((acc, item) => {
                            const serviceName = item[groupBy] || item.label
                            if (!acc[serviceName]) acc[serviceName] = []

                            // Ensure the amount is a number, or default to 0 if not
                            const amount = item["TOTAL_AMOUNT"] || item.value || 0
                            acc[serviceName].push(amount)

                            return acc
                          }, {}),
                        ).map(([serviceName, amounts], idx) => (
                          <tr key={idx}>
                            <td>{serviceName}</td>
                            {amounts.map((amount, index) => (
                              <td key={index}>{amount.toLocaleString()}</td>
                            ))}
                            <td>
                              {/* Add debugging here to log amounts */}
                              {console.log("Amounts for", serviceName, amounts)}

                              {/* Ensure the total is calculated only if amounts are populated */}
                              {amounts && amounts.length > 0
                                ? amounts.reduce((sum, amount) => sum + amount, 0).toLocaleString()
                                : 0}
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              {showFilterSidebar && (
                <div className="filter_sidebar">
                  <FilterSidebar columnList={columns} onFilterChange={handleFiltersChange} />
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
