import React, { useEffect, useState } from "react";
import FusionCharts from "fusioncharts";
import Charts from "fusioncharts/fusioncharts.charts";
import ReactFusioncharts from "react-fusioncharts";
import FusionTheme from "fusioncharts/themes/fusioncharts.theme.fusion";
import { getChartDataWithGroupAndFilters } from "../../../api/Api";

// Resolves FusionCharts dependencies
ReactFusioncharts.fcRoot(FusionCharts, Charts, FusionTheme);

export default function FusionChartComponent({
  xKey,
  yKey,
  caption,
  xAxisLabel,
  yAxisLabel,
  chartType = "column2d",
  requestPayload // Optional dynamic filters, groupByName, date range, etc.
}) {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchChartData = async () => {
      try {
        const response = await getChartDataWithGroupAndFilters(requestPayload);
        const apiData = response.result || [];
        console.log(response);
        console.log(apiData);

        const formattedData = apiData.map((item) => ({
          label: item[xKey] || item["label"],
          value: item[yKey]?.toFixed?.(2) || item["value"]?.toFixed?.(2) || 0
        }));

        console.log(formattedData);

        setData(formattedData);
      } catch (error) {
        console.error("Error fetching chart data:", error);
      }
    };

    fetchChartData();
  }, [requestPayload, xKey, yKey]);

  const chartConfigs = {
    type: chartType,
    width: "100%",
    height: "400",
    dataFormat: "json",
    dataSource: {
      chart: {
        caption: caption || "Chart",
        xAxisName: xAxisLabel || xKey ,
        yAxisName: yAxisLabel || yKey ,
        theme: "fusion",
        baseFont: "Roboto",
        baseFontSize: "14",
        showValues: "1"
      },
      data
    }
  };

  return <ReactFusioncharts {...chartConfigs} />;
}
