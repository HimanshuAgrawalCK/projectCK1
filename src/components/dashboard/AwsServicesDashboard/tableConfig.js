import { ec2Instance, rdsInstance , asgInstance } from "../../../api/Api";


const tableConfigs = {
    EC2: {
      columns: [
        { key: "instanceId", label: "Resource ID" },
        { key: "instanceName", label: "Instance Name" },
        { key: "region", label: "Region" },
        { key: "status", label: "Status" },
      ],
      fetchFunction: ec2Instance
    },
    RDS: {
      columns: [
        {key:"resourceId", label:"Resource ID"},
        { key: "resourceName", label: "Resource Name" },
        { key: "engine", label: "Engine" },
        { key: "region", label: "Region" },
        { key: "status", label: "Status" },
      ],
      fetchFunction: rdsInstance
    },
    ASG: {
      columns: [
          { key: "resourceId", label: "Resource ID" }, //
        { key: "name", label: "ASG Name" }, //
        { key: "region", label: "Region" },
        { key: "desiredCapacity", label: "Desired Capacity" },
        { key: "minSize", label: "Min Size" },
        { key: "maxSize", label: "Max Size" },
        { key: "status", label: "Status" }, //
      ],
      fetchFunction: asgInstance
    }
  };

  export default tableConfigs;
  