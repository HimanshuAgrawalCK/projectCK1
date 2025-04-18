import Login from "./components/Login";
import "./styles/App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ToastWrapper } from "./components/common/Toaster";
import UserManagementDashboard from "./components/dashboard/UserManagementDashboard/UserManagementDashboard";
import OnboardingDashboard from "./components/dashboard/OnboardingDashboard/OnboardingDashboard";
import AwsServicesDashboard from "./components/dashboard/AwsServicesDashboard/AwsServicesDashboard";
import CostExplorer from "./components/dashboard/CostExplorerDashboard/CostExplorer";
import PrivateRoute from "./components/PrivateRoute";
import Unauthorized from "./components/UnauthorizedAccess";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route
            path="/usermanagementdashboard"
            element={
              <PrivateRoute allowedRoles={["ADMIN", "READONLY"]}>
                <UserManagementDashboard />
              </PrivateRoute>
            }
          />
          <Route
            path="/onboardingdashboard"
            element={
              <PrivateRoute allowedRoles={["ADMIN"]}>
                <OnboardingDashboard />
              </PrivateRoute>
            }
          />
          <Route
            path="/awsservicesdashboard"
            element={
              <PrivateRoute allowedRoles={["ADMIN", "CUSTOMER", "READONLY"]}>
                <AwsServicesDashboard />
              </PrivateRoute>
            }
          />
          <Route
            path="/costexplorerdashboard"
            element={
              <PrivateRoute allowedRoles={["ADMIN", "CUSTOMER", "READONLY"]}>
                <CostExplorer />
              </PrivateRoute>
            }
          />
          <Route path="/login" element={<Login />} />
          <Route path="/unauthorized" element={<Unauthorized />} />
          <Route path="*" element={<h1>404 Page Not Found</h1>} />
        </Routes>
      </BrowserRouter>
      <ToastWrapper />
    </>
  );
}

export default App;
