import React from "react";
import { showToast } from "../../../common/Toaster";

const jsonToCopy = `{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "AWS": "arn:aws:iam::951485052809:root"
      },
      "Action": "sts:AssumeRole",
      "Condition": {
        "StringEquals": {
          "sts:ExternalId": "Um9oaXRDS19ERUZBVUxUZDIzOTJkZTgtN2E0OS00NWQ3LTg3MzItODkyM2ExZTIzMjQw"
        }
      }
    },
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "s3.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}`;

const handleCopy = (json) => {
  navigator.clipboard.writeText(json);
  showToast("Copied to clipboard",200);

};


const constantsOfPage1 = [
  <p>
    Log into AWS account & <a href="#">Create an IAM Role</a>.
  </p>,
  <>
    <p>
      In the Trusted entity type section, select Custom trust policy. Replace
      the prefilled policy with the policy provided below -
    </p>
    <div className="json-container">
    <button className="copy-button" onClick={()=>handleCopy(jsonToCopy)}>
        <svg
          xmlns="images/copy-text.svg"
          width="24"
          height="24"
          fill="currentColor"
          viewBox="0 0 24 24"
        >
          <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v16h14c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 18H8V7h11v16z" />
        </svg>
      </button>
      <pre>
        <code>
        {jsonToCopy}
        </code>
      </pre>
    </div>
  </>,
  <p>
    Click on <strong>Next</strong> to go to the <i>Add permissions page</i>. We would not be adding any
    permissions for now because the permission policy content will be dependent
    on the AWS Account ID retrieved from the IAM Role. Click on <strong>Next.</strong>
  </p>,
<>
  <p>In the
  <i>Role name field</i>,
  enter the below-mentioned role name,
  and click on  
  <strong>Create Role -</strong></p>
  <div className="copy-box">
  <button className="copy-button" onClick={()=>handleCopy(jsonToCopy)}>
        <svg
          xmlns="images/copy-text.svg"
          width="24"
          height="24"
          fill="currentColor"
          viewBox="0 0 24 24"
        >
          <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v16h14c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 18H8V7h11v16z" />
        </svg>
      </button>
      <pre>CK-Tuner-Role-Dev2</pre>
  </div>
</>
  ,

  <p>Go to the newly create IAM Role and copy the Role ARN -
  </p>,
<>
  <p>Paste the copied Role ARN below -
  </p>
  <img src="images/onboarding_page1_image1.png" alt="Onboarding Page 1" />  
</>
,


];

export default constantsOfPage1;
