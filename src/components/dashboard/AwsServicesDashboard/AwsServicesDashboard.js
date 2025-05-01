import React, { useEffect, useState } from "react";
import { getAllAccounts } from "../../../api/Api";
import { useSelector } from "react-redux";
import { useNavigate, useLocation, Outlet } from "react-router-dom";
import Header from "../../common/Header";
import Sidebar from "../../common/Sidebar";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import ButtonGroup from "@mui/material/ButtonGroup";
import "../../../styles/AwsServiceDashboard.css";
import AccountSelectBox from "../../common/AccountSelectBox";

function VariantButtonGroup({ onServiceSelect, selectedService }) {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'start', '& > *': { m: 1 } }}>
      <ButtonGroup className="option-box" variant="outlined">
        <Button
          onClick={() => onServiceSelect("ec2")}
          className={selectedService === "ec2" ? "active-button" : ""}
        >
          EC2
        </Button>
        <Button
          onClick={() => onServiceSelect("rds")}
          className={selectedService === "rds" ? "active-button" : ""}
        >
          RDS
        </Button>
        <Button
          onClick={() => onServiceSelect("asg")}
          className={selectedService === "asg" ? "active-button" : ""}
        >
          ASG
        </Button>
      </ButtonGroup>
    </Box>
  );
}

export default function AwsServicesDashboard() {
  const { userDetails } = useSelector((state) => state.user);
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState("");
  const [selectedService, setSelectedService] = useState("");
  const [hasNavigatedInitially, setHasNavigatedInitially] = useState(false); // NEW

  const navigate = useNavigate();
  const location = useLocation();

  // Extract selected service from the path
  useEffect(() => {
    const pathParts = location.pathname.split("/");
    const serviceFromPath = pathParts[pathParts.length - 1];
    setSelectedService(serviceFromPath);
  }, [location.pathname]);

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const res = await getAllAccounts(userDetails.id);
        setAccounts(res);

        // Initial account + navigation only if not already done
        if (res.length > 0 && !hasNavigatedInitially) {
          const defaultAccount = res[0].accountId;
          setSelectedAccount(defaultAccount);

          // If no service is selected in path, default to ec2
          const pathParts = location.pathname.split("/");
          const lastPart = pathParts[pathParts.length - 1];
          const isServicePath = ["ec2", "rds", "asg"].includes(lastPart);

          navigate(`/awsservicesdashboard/${isServicePath ? lastPart : "ec2"}`, {
            state: { selectedAccount: defaultAccount },
            replace: true,
          });

          setHasNavigatedInitially(true);
        }
      } catch (err) {
        console.error("Error fetching accounts", err);
      }
    };

    fetchAccounts();
  }, [userDetails.id, hasNavigatedInitially, navigate, location.pathname]);

  const handleChange = (e) => {
    setSelectedAccount(e.target.value);
    navigate(`/awsservicesdashboard/${selectedService}`, {
      state: { selectedAccount: e.target.value },
    });
  };

  const handleServiceSelect = (service) => {
    setSelectedService(service);
    navigate(`/awsservicesdashboard/${service}`, {
      state: { selectedAccount },
    });
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
        <div className="aws_services_dashboard">
          <div className="aws_services_title">
            <h3>Scheduler</h3>
            <AccountSelectBox
              accounts={accounts}
              selectedAccount={selectedAccount}
              handleChange={handleChange}
            />
          </div>

          <VariantButtonGroup
            onServiceSelect={handleServiceSelect}
            selectedService={selectedService}
          />

          {/* This is where the table gets rendered based on route */}
          <Outlet context={{ selectedAccount }} />
        </div>
      </div>
    </div>
  );
}
