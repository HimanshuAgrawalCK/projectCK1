import React from "react";
import { showToast } from "../../../common/Toaster";

const handleCopy = (json) => {
  navigator.clipboard.writeText(json);
  showToast("Copied to clipboard", 200);
};

const constantsOfpage3 = [
  <p>
    Go to <a href="#">Cost and Usage Reports</a> in the Billing Dashboard and
    click on report.
  </p>,
  <>
    <p>
      Name the report as shown below and select the Include resource IDs
      checkbox -
    </p>
    <div className="copy-box">
      <button
        className="copy-button"
        onClick={() => handleCopy(`ck-tuner-275595855473-hourly-cur`)}>
        <svg
          xmlns="images/copy-text.svg"
          width="24"
          height="24"
          fill="currentColor"
          viewBox="0 0 24 24">
          <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v16h14c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 18H8V7h11v16z" />
        </svg>
      </button>
      <pre>ck-tuner-275595855473-hourly-cur</pre>
    </div>

    <span
      style={{
        marginTop: "20px",
        color: "grey",
        display: "flex",
        flexDirection: "column",
      }}>
      Ensure that the following configuration is checked
      <div style={{ margin_left: "30px" }}>
        <input type="checkbox" checked disabled />
        <label style={{ marginLeft: "8px", color: "#000" }}>
          Include Resource IDs
        </label>
      </div>
      <img
        src="images/onboarding_page3_image1.png"
        alt="Onboarding Image Page 3 Image 1"
      />
    </span>
  </>,

  <div style={{ display: "flex", flexDirection: "column" }}>
    <span>
      {" "}
      In Configure S3 Bucket, provide the name of the S3 bucket that was created
      -
    </span>
    <div
      style={{
        marginTop: "20px",
        color: "grey",
        display: "flex",
        flexDirection: "column",
      }}>
      Ensure that the following configuration is checked
      <div style={{ margin_left: "30px" }}>
        <input type="checkbox" checked disabled />
        <label style={{ marginLeft: "8px", color: "#000" }}>
          The following default policy will be applied to your bucket{" "}
        </label>
      </div>
    </div>
    <span>
      Click on <strong>Save.</strong>
    </span>
    <img
      src="images/onboarding_page3_image2.png"
      alt="Onboarding Image 2 Page 3"
    />
  </div>,

  <div style={{ display: "flex", flexDirection: "column", gap: "14px" }}>
    <span>
      In the <i>Delivery options </i>section, enter the below-mentioned Report
      path prefix -
    </span>

    <div className="copy-box">
      <button
        className="copy-button"
        onClick={() => handleCopy(`275595855473`)}>
        <svg
          xmlns="images/copy-text.svg"
          width="24"
          height="24"
          fill="currentColor"
          viewBox="0 0 24 24">
          <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v16h14c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 18H8V7h11v16z" />
        </svg>
      </button>
      <pre>275595855473</pre>
    </div>
    <img
      src="images/onboarding_page3_image3.png"
      alt="Onboarding Image 3 Page 3"
    />
  </div>,
  <span>
    Click on Next. Now, review the configuration of the Cost and Usage Report.
    Once satisfied, click on Create Report.
  </span>,
];

export default constantsOfpage3;
