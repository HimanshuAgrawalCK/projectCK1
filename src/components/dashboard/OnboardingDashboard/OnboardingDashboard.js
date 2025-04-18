import React, { useState } from "react";
import Header from "../../common/Header";
import Sidebar from "../../common/Sidebar";
import "../../../styles/OnboardingDashboard.css";
import Page1 from "./Page1";
import Page2 from "./Page2";
import Page3 from "./Page3";
import { showToast } from "../../common/Toaster";
import { addAccount } from "../../../api/Api";
import DonePage from "./DonePage";

export default function OnboardingDashboard() {
  const [page, setPage] = useState(0); // starting from Page1
  const [isWelcome, setIsWelcome] = useState(true);
  const [done, setDone] = useState(false);
  const [formData, setFormData] = useState({
    accountId: "",
    accountName: "",
    arn: "",
  });

  const handleCancel = () => {
    setFormData({
      accountId: "",
      accountName: "",
      arn: "",
    });
    setIsWelcome(!isWelcome);
    setPage(0);
  };

  const handleNext = () => {
    // Check if current page is 1 (Page1) and form is incomplete
    if (
      page === 0 &&
      (!formData.accountId.trim() ||
        !formData.accountName.trim() ||
        !formData.arn.trim())
    ) {
      showToast("Please fill all fields");
      return; // stop navigation
    }

    // Otherwise proceed
    setPage((prevPage) =>
      prevPage < Object.keys(pages).length - 1 ? prevPage + 1 : prevPage
    );
  };

  const handlePrev = () => {
    setPage((prevPage) => (prevPage > 0 ? prevPage - 1 : prevPage));
  };

  const handleSubmit = async() => {
    try{
      const response = await addAccount(formData);
      setDone(true);
      showToast("Account added successfully",200);
      setFormData({
        accountId: "",
        accountName: "",
        arn: "",
      });
    } catch(error){
      showToast(error.response.data);
      handleRestart();
    }
  };

  const handleRestart = () => {
    setDone(false);
    setIsWelcome(true);
    setPage(0);
    setFormData({
      accountId: "",
      accountName: "",
      arn: "",
    });
  };
  

  const pages = {
    0: (
      <Page1 setPage={setPage} formData={formData} setFormData={setFormData} />
    ),
    1: <Page2 setPage={setPage} />,
    2: <Page3 formData={formData} />,
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
  
        <div className="onboarding_dashboard_content">
          {done && <DonePage handleRestart={handleRestart}/>}
  
          {!done && isWelcome && (
            <>
              <h1>Welcome</h1>
              <button type="button" onClick={handleCancel}>
                Start Onboarding
              </button>
            </>
          )}
  
          {!done && !isWelcome && (
            <>
              {pages[page]}
              <div className="navigation_buttons">
                <div className="navigation_button_left">
                  <button type="button" className="buttontype1" onClick={handleCancel}>
                    Cancel
                  </button>
                </div>
                <div className="navigation_button_right">
                  {page !== 0 && (
                    <button type="button" className="buttontype1" onClick={handlePrev}>
                      {page === 1 && "Back - Create an IAM Policy"}
                      {page === 2 && "Back - Customer Management Policies"}
                    </button>
                  )}
                  {page !== Object.keys(pages).length - 1 && (
                    <button
                      type="button"
                      className="buttontype2"
                      onClick={handleNext}
                      disabled={page === Object.keys(pages).length - 1}
                    >
                      {page === 0 && "Next - Add Customer Management Policies"}
                      {page === 1 && "Next - Cost and Usage Report"}
                    </button>
                  )}
                  {page === Object.keys(pages).length - 1 && (
                    <button
                      type="submit"
                      className="buttontype2"
                      onClick={handleSubmit}
                    >
                      Submit
                    </button>
                  )}
                </div>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
  
}
