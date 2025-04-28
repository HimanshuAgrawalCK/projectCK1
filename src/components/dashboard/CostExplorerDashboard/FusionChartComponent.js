import React, { useState, useEffect } from "react";
import FusionCharts from "fusioncharts";
import ReactFC from "react-fusioncharts";
import Column2D from "fusioncharts/fusioncharts.charts"; // fusioncharts.charts includes mscolumn2d
import FusionTheme from "fusioncharts/themes/fusioncharts.theme.fusion";
import { getChartDataWithGroupAndFilters } from "../../../api/Api";
import { showToast } from "../../common/Toaster";

ReactFC.fcRoot(FusionCharts, Column2D, FusionTheme);

// Utility to generate random hex color
const getRandomColor = () => {
  const letters = "0123456789ABCDEF";
  let color = "#";
  for (let i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
};

const FusionChartComponent = ({
  groupByName,
  filters,
  accountId,
  startMonth,
  startYear,
  endMonth,
  endYear,
  chartType = "mscolumn2d",
  setBackendData
}) => {
  const [chartData, setChartData] = useState(null);


  const buildCostExplorerRequest = () => ({
    groupByName: String(groupByName),
    filters,
    accountId: Number(accountId),
    startMonth: startMonth ? Number(startMonth) : null,
    startYear: startYear ? Number(startYear) : null,
    endMonth: endMonth ? Number(endMonth) : null,
    endYear: endYear ? Number(endYear) : null,
  });

  const handleFetchData = async () => {
    const requestBody = buildCostExplorerRequest();

    try {
      const data = await getChartDataWithGroupAndFilters(requestBody);
      {data.rowNum===0 && showToast("No Matching row",100);}
      setBackendData(data.result);
      const serviceColors = {};
      const monthSet = new Set();
      const serviceSet = new Set();
      const processedData = [];
      
      let usageMonthForOther = null;
      for (const item of data.result) {
        if (item[String(groupByName)] && item["USAGE_MONTH"]) {
          usageMonthForOther = item["USAGE_MONTH"];
          break;
        }
      }

      data.result.forEach((item) => {
        let serviceName = item[String(groupByName)];

        if (serviceName == null && item["label"] !== "Other") {
          serviceName = "Unknown"; // handle null GroupType
        }

        if (serviceName && item["USAGE_MONTH"]) {
          monthSet.add(item["USAGE_MONTH"]);
          serviceSet.add(serviceName);
          processedData.push({
            service: serviceName,
            month: item["USAGE_MONTH"],
            amount: item["TOTAL_AMOUNT"],
          });

          if (!serviceColors[serviceName]) {
            serviceColors[serviceName] = getRandomColor();
          }
        } else if (item["label"] === "Other") {
          if (usageMonthForOther) {
            monthSet.add(usageMonthForOther);
            serviceSet.add("Other");
            processedData.push({
              service: "Other",
              month: usageMonthForOther,
              amount: item["value"],
            });

            if (!serviceColors["Other"]) {
              serviceColors["Other"] = getRandomColor();
            }
          }
        }
      });

      const months = Array.from(monthSet).sort();
      const services = Array.from(serviceSet);

      const categories = months.map((month) => ({ label: month }));

      const datasets = services.map((service) => ({
        seriesname: service,
        color: serviceColors[service],
        data: months.map((month) => {
          const item = processedData.find(
            (d) => d.service === service && d.month === month
          );
          return { value: item ? item.amount : 0 };
        }),
      }));

      setChartData({
        categories: [{ category: categories }],
        dataset: datasets,
      });
    } catch (error) {
      console.error("Failed to fetch chart data", error);
    }
  };

  // Auto reload chart when important props change
  useEffect(() => {
    if (accountId) {
      handleFetchData();
    }
  }, [
    groupByName,
    filters,
    accountId,
    startMonth,
    startYear,
    endMonth,
    endYear,
  ]);

  return (
    <div className="fusionchart_main">
      {chartData && (
        <ReactFC
          type={chartType}
          width="100%"
          height="400"
          dataFormat="json"
          dataSource={{
            chart: {
              caption: "AWS Cost Explorer (Month-wise Service-wise)",
              xAxisName: "Month",
              yAxisName: "Total Amount",
              theme: "fusion",
              showLegend: 1,
              legendPosition: "bottom",
            },
            categories:chartData.categories,
            dataset: chartData.dataset
          }}
        />
      )}
    </div>
  );
};

export default FusionChartComponent;
