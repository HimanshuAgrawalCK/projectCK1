import React from "react";
import { showToast } from "../../../common/Toaster";

const jsonContent1 = `{
    "Version": "2012-10-17",
    "Statement": [
      {
        "Sid": "CostAudit",
        "Effect": "Allow",
        "Action": [
          "dms:Describe*",
          "dms:List*",
          "kafka:Describe*",
          "kafka:Get*",
          "kafka:List*",
          "mq:Describe*",
          "mq:List*",
          "route53resolver:Get*",
          "route53resolver:List*",
          "memorydb:Describe*",
          "savingsplans:Describe*",
          "cloudsearch:Describe*",
          "quicksight:Describe*",
          "quicksight:List*",
          "codepipeline:Get*",
          "codepipeline:List*",
          "codebuild:List*",
          "codebuild:Get*",
          "codebuild:Describe*",
          "codebuild:BatchGet*",
          "codedeploy:List*",
          "codedeploy:BatchGet*",
          "codedeploy:Get*",
          "mediaconnect:Describe*",
          "mediaconnect:List*",
          "mediaconvert:Describe*",
          "mediaconvert:Get*",
          "mediaconvert:List*",
          "medialive:Describe*",
          "medialive:List*",
          "mediapackage:Describe*",
          "mediapackage:List*",
          "mediapackage-vod:Describe*",
          "mediapackage-vod:List*",
          "mediastore:DescribeObject",
          "mediastore:Get*",
          "mediastore:List*",
          "mediatailor:Describe*",
          "mediatailor:Get*",
          "mediatailor:List*",
          "ec2:Describe*",
          "elasticache:Describe*",
          "events:DescribeEventBus",
          "events:List*",
          "elasticloadbalancing:Describe*",
          "kinesis:List*",
          "kinesis:Describe*",
          "kinesisanalytics:Describe*",
          "kinesisanalytics:List*",
          "dynamodb:Describe*",
          "dynamodb:List*",
          "cloudwatch:Describe*",
          "cloudwatch:List*",
          "cloudwatch:GetMetricStatistics",
          "ecr:GetLifecyclePolicy",
          "ecr:GetRepositoryPolicy",
          "ecr-public:DescribeRepositories",
          "ecr:List*",
          "ecr:Describe*",
          "lambda:List*",
          "lambda:GetPolicy",
          "lambda:GetAccountSettings",
          "lambda:GetFunctionConfiguration",
          "lambda:GetFunctionCodeSigningConfig",
          "lambda:GetFunctionConcurrency",
          "lambda:GetFunctionConfiguration",
          "rds:Describe*",
          "rds:ListTagsForResource",
          "sqs:GetQueueAttributes",
          "sqs:List*",
          "firehose:Describe*",
          "firehose:List*",
          "kafka:Describe*",
          "kafka:List*",
          "glue:GetDevEndpoint",
          "s3:GetBucketPolicy",
          "s3:List*",
          "network-firewall:Describe*",
          "network-firewall:List*",
          "elasticfilesystem:Describe*",
          "kms:Describe*",
          "kms:List*",
          "kms:GetKeyRotationStatus",
          "kms:GetKeyPolicy",
          "elasticmapreduce:List*",
          "es:Describe*",
          "es:List*",
          "es:Get*",
          "aoss:Get*",
          "aoss:List*",
          "logs:Describe*",
          "logs:List*",
          "application-autoscaling:Describe*",
          "route53:List*",
          "redshift:Describe*",
          "backup:Describe*",
          "backup:Get*",
          "backup:List*",
          "dlm:Get*",
          "dlm:List*",
          "sagemaker:List*",
          "lambda:Get*"
        ],
        "Resource": "*"
      },
      {
        "Sid": "BillingReadOnly",
        "Effect": "Allow",
        "Action": [
          "billingconductor:List*",
          "billing:ListBillingViews"
        ],
        "Resource": "*"
      },
      {
        "Sid": "ComputeOptimizerReadAccess",
        "Effect": "Allow",
        "Action": [
          "compute-optimizer:Get*"
        ],
        "Resource": "*"
      },
      {
        "Sid": "CostExplorerAccess",
        "Effect": "Allow",
        "Action": [
          "ce:Describe*",
          "ce:Get*",
          "ce:List*"
        ],
        "Resource": "*"
      },
      {
        "Sid": "CURReportDefinitions",
        "Effect": "Allow",
        "Action": [
          "organizations:Describe*",
          "organizations:List*"
        ],
        "Resource": "*"
      },
      {
        "Sid": "PricingAPIAccess",
        "Effect": "Allow",
        "Action": [
          "pricing:*"
        ],
        "Resource": "*"
      },
      {
        "Sid": "WellArchitectedAccess",
        "Effect": "Allow",
        "Action": [
          "wellarchitected:*"
        ],
        "Resource": "*"
      },
      {
        "Sid": "ReadOnlyForOrgServices",
        "Effect": "Allow",
        "Action": [
          "detective:Describe*",
          "detective:List*",
          "detective:Get*",
          "devops-guru:Describe*",
          "devops-guru:List*",
          "devops-guru:Get*",
          "devops-guru:Search*",
          "guardduty:Describe*",
          "guardduty:Get*",
          "guardduty:List*",
          "inspector:Describe*",
          "inspector:Get*",
          "inspector2:List*",
          "inspector2:Get*",
          "inspector2:Describe*",
          "macie2:Describe*",
          "macie2:Get*",
          "macie2:List*",
          "account:Get*",
          "account:ListRegions",
          "auditmanager:Get*",
          "auditmanager:List*",
          "controltower:Describe*",
          "controltower:Get*",
          "controltower:List*",
          "sso:Describe*",
          "sso:List*",
          "sso:Get*",
          "sso:Search*",
          "sso-directory:Describe*",
          "sso-directory:Get*",
          "sso-directory:List*",
          "sso-directory:Search*",
          "aws-marketplace:DescribeAgreement",
          "aws-marketplace:Get*",
          "aws-marketplace:List*",
          "aws-marketplace:ViewSubscriptions",
          "aws-marketplace:SearchAgreements",
          "networkmanager:DescribeGlobalNetworks",
          "networkmanager:Get*",
          "networkmanager:List*",
          "trustedadvisor:Describe*",
          "trustedadvisor:List*",
          "cloudtrail:Describe*",
          "cloudtrail:Get*",
          "cloudtrail:List*",
          "cloudtrail:LookupEvents",
          "cloudformation:Describe*",
          "cloudformation:Get*",
          "cloudformation:List*",
          "compute-optimizer:DescribeRecommendationExportJobs",
          "config:Describe*",
          "config:Get*",
          "config:List*",
          "ds:Describe*",
          "ds:Get*",
          "ds:List*",
          "fms:Get*",
          "fms:List*",
          "access-analyzer:Get*",
          "access-analyzer:List*",
          "healthlake:Describe*",
          "healthlake:GetCapabilities",
          "healthlake:List*",
          "healthlake:ReadResource",
          "healthlake:Search*",
          "health:Describe*",
          "license-manager:Get*",
          "license-manager:List*",
          "servicecatalog:Describe*",
          "servicecatalog:Get*",
          "servicecatalog:List*",
          "servicecatalog:ScanProvisionedProducts",
          "servicecatalog:Search*",
          "securityhub:Describe*",
          "securityhub:Get*",
          "securityhub:List*",
          "ssm:Describe*",
          "ssm:List*",
          "ram:Get*",
          "ram:List*",
          "servicequotas:Get*",
          "servicequotas:List*",
          "s3:Describe*",
          "license-manager:GetGrant",
          "license-manager:ListTokens",
          "license-manager-user-subscriptions:List*"
        ],
        "Resource": "*"
      }
    ]
  }`;

const jsonContent2 = `{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "CostExplorer",
      "Action": [
        "ce:StartSavingsPlansPurchaseRecommendationGeneration",
        "ce:UpdatePreferences"
      ],
      "Effect": "Allow",
      "Resource": "*"
    },
    {
      "Sid": "ListEC2SPReservations",
      "Effect": "Allow",
      "Action": [
        "ec2:DescribeCapacityReservations",
        "ec2:DescribeCapacityReservationFleets",
        "ec2:GetCapacityReservationUsage",
        "ec2:GetGroupsForCapacityReservation",
        "ec2:DescribeHostReservations",
        "ec2:DescribeHostReservationOfferings",
        "ec2:GetHostReservationPurchasePreview",
        "ec2:DescribeReservedInstancesOfferings",
        "ec2:DescribeReservedInstancesModifications",
        "ec2:DescribeReservedInstances",
        "ec2:GetReservedInstancesExchangeQuote",
        "ec2:DescribeReservedInstancesListings"
      ],
      "Resource": "*"
    },
    {
      "Sid": "ComputeOptimizerAccess",
      "Effect": "Allow",
      "Action": [
        "compute-optimizer:UpdateEnrollmentStatus",
        "compute-optimizer:PutRecommendationPreferences"
      ],
      "Resource": "*"
    },
    {
      "Sid": "ServiceLinkedRole",
      "Effect": "Allow",
      "Action": "iam:CreateServiceLinkedRole",
      "Resource": "arn:aws:iam::*:role/aws-service-role/compute-optimizer.amazonaws.com/AWSServiceRoleForComputeOptimizer*",
      "Condition": {
        "StringLike": {
          "iam:AWSServiceName": "compute-optimizer.amazonaws.com"
        }
      }
    },
    {
      "Sid": "ServiceLinkedRolePolicy",
      "Effect": "Allow",
      "Action": "iam:PutRolePolicy",
      "Resource": "arn:aws:iam::*:role/aws-service-role/compute-optimizer.amazonaws.com/AWSServiceRoleForComputeOptimizer"
    },
    {
      "Sid": "AllowRoleToInspectItself",
      "Effect": "Allow",
      "Action": [
        "iam:ListRolePolicies",
        "iam:GetRolePolicy"
      ],
      "Resource": "arn:aws:iam::275595855473:role/CK-Tuner-Role-dev2"
    }
  ]
}`;

const jsonContent3 = `{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": [
        "cur:ValidateReportDestination",
        "cur:DescribeReportDefinitions"
      ],
      "Resource": [
        "*"
      ],
      "Effect": "Allow",
      "Sid": "ReadCostAndUsageReport"
    },
    {
      "Action": [
        "s3:ListBucket",
        "s3:GetReplicationConfiguration",
        "s3:GetObjectVersionForReplication",
        "s3:GetObjectVersionAcl",
        "s3:GetObjectVersionTagging",
        "s3:GetObjectRetention",
        "s3:GetObjectLegalHold",
        "s3:GetObject"
      ],
      "Resource": [
        "arn:aws:s3:::ck-tuner-275595855473",
        "arn:aws:s3:::ck-tuner-275595855473/*"
      ],
      "Effect": "Allow",
      "Sid": "S3LimitedRead"
    },
    {
      "Action": [
        "s3:GetObjectVersionTagging",
        "s3:GetBucketVersioning",
        "s3:ReplicateObject",
        "s3:ReplicateDelete",
        "s3:ReplicateTags",
        "s3:ObjectOwnerOverrideToBucketOwner"
      ],
      "Resource": [
        "arn:aws:s3:::ck-tuner-cur-dev2-1000291",
        "arn:aws:s3:::ck-tuner-cur-dev2-1000291/*"
      ],
      "Effect": "Allow",
      "Sid": "S3Replicate"
    },
    {
      "Action": [
        "s3:PutObject",
        "s3:GetObject"
      ],
      "Resource": "arn:aws:s3:::ck-tuner-275595855473/CKTunerTestFile",
      "Effect": "Allow",
      "Sid": "S3ReplicationCheck"
    }
  ]
}`;

const jsonContent4 = `{
    "Version": "2012-10-17",
    "Statement": [
      {
        "Sid": "CostExplorer",
        "Action": [
          "ce:StartSavingsPlansPurchaseRecommendationGeneration",
          "ce:UpdatePreferences"
        ],
        "Effect": "Allow",
        "Resource": "*"
      },
      {
        "Sid": "ListEC2SPReservations",
        "Effect": "Allow",
        "Action": [
          "ec2:DescribeCapacityReservations",
          "ec2:DescribeCapacityReservationFleets",
          "ec2:GetCapacityReservationUsage",
          "ec2:GetGroupsForCapacityReservation",
          "ec2:DescribeHostReservations",
          "ec2:DescribeHostReservationOfferings",
          "ec2:GetHostReservationPurchasePreview",
          "ec2:DescribeReservedInstancesOfferings",
          "ec2:DescribeReservedInstancesModifications",
          "ec2:DescribeReservedInstances",
          "ec2:GetReservedInstancesExchangeQuote",
          "ec2:DescribeReservedInstancesListings"
        ],
        "Resource": "*"
      },
      {
        "Sid": "ComputeOptimizerAccess",
        "Effect": "Allow",
        "Action": [
          "compute-optimizer:UpdateEnrollmentStatus",
          "compute-optimizer:PutRecommendationPreferences"
        ],
        "Resource": "*"
      },
      {
        "Sid": "ServiceLinkedRole",
        "Effect": "Allow",
        "Action": "iam:CreateServiceLinkedRole",
        "Resource": "arn:aws:iam::*:role/aws-service-role/compute-optimizer.amazonaws.com/AWSServiceRoleForComputeOptimizer*",
        "Condition": {
          "StringLike": {
            "iam:AWSServiceName": "compute-optimizer.amazonaws.com"
          }
        }
      },
      {
        "Sid": "ServiceLinkedRolePolicy",
        "Effect": "Allow",
        "Action": "iam:PutRolePolicy",
        "Resource": "arn:aws:iam::*:role/aws-service-role/compute-optimizer.amazonaws.com/AWSServiceRoleForComputeOptimizer"
      },
      {
        "Sid": "AllowRoleToInspectItself",
        "Effect": "Allow",
        "Action": [
          "iam:ListRolePolicies",
          "iam:GetRolePolicy"
        ],
        "Resource": "arn:aws:iam::275595855473:role/CK-Tuner-Role-dev2"
      }
    ]
  }`;

const handleCopy = (json) => {
  navigator.clipboard.writeText(json);
  showToast("Copied to clipboard", 200);
};

const constantsOfpage2 = [
  <p>
    Go to the <a href="#">Create Policy </a>page.
  </p>,
  <>
    <p>
      Click on the <strong>JSON </strong> tab and paste the following policy and
      click on Next:
    </p>
    <div className="json-container">
      <button className="copy-button" onClick={() => handleCopy(jsonContent1)}>
        <svg
          xmlns="images/copy-text.svg"
          width="24"
          height="24"
          fill="currentColor"
          viewBox="0 0 24 24">
          <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v16h14c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 18H8V7h11v16z" />
        </svg>
      </button>
      <pre>
        <code>{jsonContent1}</code>
      </pre>
    </div>
  </>,
  <>
    <p>
      In the <strong>Name </strong> field, enter below-mentioned policy name and
      click on Create Policy
    </p>
    <div className="copy-box">
      <button
        className="copy-button"
        onClick={() => handleCopy(`cktuner-CostEditPolicy`)}>
        <svg
          xmlns="images/copy-text.svg"
          width="24"
          height="24"
          fill="currentColor"
          viewBox="0 0 24 24">
          <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v16h14c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 18H8V7h11v16z" />
        </svg>
      </button>
      <pre>cktuner-CostEditPolicy</pre>
    </div>
  </>,
  <p>
    Again, go to the <a>Create Policy </a> Page.
  </p>,
  <>
    <p>
      Click on the <strong>JSON</strong>tab and paste the following policy and
      click on Next:
    </p>
    <div className="json-container">
      <button className="copy-button" onClick={() => handleCopy(jsonContent2)}>
        <svg
          xmlns="images/copy-text.svg"
          width="24"
          height="24"
          fill="currentColor"
          viewBox="0 0 24 24">
          <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v16h14c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 18H8V7h11v16z" />
        </svg>
      </button>
      <pre>
        <code>{jsonContent2}</code>
      </pre>
    </div>
  </>,
  <>
    {/* <p> */}
      In the <strong>Name </strong> field, enter below-mentioned policy name and
      click on Create Policy
      <div className="copy-box">
        <button
          className="copy-button"
          onClick={() => handleCopy(`cktuner-SecAuditPolicy`)}>
          <svg
            xmlns="images/copy-text.svg"
            width="24"
            height="24"
            fill="currentColor"
            viewBox="0 0 24 24">
            <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v16h14c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 18H8V7h11v16z" />
          </svg>
        </button>
        <pre>cktuner-SecAuditPolicy</pre>
      </div>
    {/* </p> */}
  </>,
  <p>
    Again, go to the <a>Create Policy </a>Page.
  </p>,
  <>
    <p>
      Click on the <strong>JSON </strong> tab and paste the following policy and
      click on Next:
    </p>
    ,
    <div className="json-container">
      <button className="copy-button" onClick={() => handleCopy(jsonContent3)}>
        <svg
          xmlns="images/copy-text.svg"
          width="24"
          height="24"
          fill="currentColor"
          viewBox="0 0 24 24">
          <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v16h14c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 18H8V7h11v16z" />
        </svg>
      </button>
      <pre>
        <code>{jsonContent3}</code>
      </pre>
    </div>
  </>,
  <>
    <p>
      In the <strong>Name </strong>field, enter below-mentioned policy name and
      click on Create Policy
    </p>
    <div className="copy-box">
  <button className="copy-button" onClick={()=>handleCopy(`cktuner-TunerReadEssentials`)}>
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
      <pre>cktuner-TunerReadEssentials</pre>
  </div>
  </>,
  <>
    <p>
      Go to the <a href="#">CK-Tuner-Role</a>
    </p>
    ,
    <img src="images/onboarding_page2_image1.png" alt="onboarding Image 1" />
  </>,
  <>
    <p>
      In Permission policies, click on{" "}
      <strong>Add permissions {`>`} Attach Policy</strong>
    </p>
    <img src="images/onboarding_page2_image2.png" alt="onboarding Image 2" />
  </>,
  <>
    <p>
      Filter by Type {`>`} Customer managed then search for
      cktuner-CostAuditPolicy, cktuner-SecAuditPolicy,
      cktuner-TunerReadEssentials and select them.
    </p>
    <img src="images/onboarding_page2_image3.png" alt="onboarding Image 3" />
  </>,
  <p>
    Now, click on <strong>Add permissions</strong>
  </p>,
  <>
    <p>
      In Permission policies, click on Add permissions {`>`} Create inline
      policy
    </p>
    <img src="images/onboarding_page2_image4.png" alt="onboarding Image 4" />
  </>,
  <>
    <p>Click on the JSON tab and paste the following policy</p>
    <div className="json-container">
      <button className="copy-button" onClick={() => handleCopy(jsonContent4)}>
        <svg
          xmlns="images/copy-text.svg"
          width="24"
          height="24"
          fill="currentColor"
          viewBox="0 0 24 24">
          <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v16h14c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 18H8V7h11v16z" />
        </svg>
      </button>
      <pre>
        <code>{jsonContent4} </code>
      </pre>
    </div>
  </>,
  <p>
    Now, click on <strong>Review policy</strong>
  </p>,
  <>
  <p>
    In the <strong>Name</strong> field, enter the below-mentioned policy name
    and click on <strong> Create Policy</strong>
  </p>
  <div className="copy-box">
  <button className="copy-button" onClick={()=>handleCopy(`S3CrossAccountReplication`)}>
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
      <pre>S3CrossAccountReplication</pre>
  </div>
  </>,
];

export default constantsOfpage2;
