import Login from "./components/Login";
import "./styles/App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ToastWrapper } from "./components/common/Toaster";
import UserManagementDashboard from "./components/dashboard/UserManagementDashboard/UserManagementDashboard";
import OnboardingDashboard from "./components/dashboard/OnboardingDashboard/OnboardingDashboard";
import AwsServicesDashboard from "./components/dashboard/AwsServicesDashboard/AwsServicesDashboard";
import CostExplorer from "./components/dashboard/CostExplorerDashboard/CostExplorer";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="*" element={<Login />} />
          <Route
            path="/usermanagementdashboard"
            element={<UserManagementDashboard />}
          />
          <Route
            path="/onboardingdashboard"
            element={<OnboardingDashboard />}
          />
          <Route
            path="/awsservicesdashboard"
            element={<AwsServicesDashboard />}
          />
          <Route path="/costexplorerdashboard" element={<CostExplorer />} />
        </Routes>
      </BrowserRouter>
      <ToastWrapper />
    </>
  );
}

export default App;
