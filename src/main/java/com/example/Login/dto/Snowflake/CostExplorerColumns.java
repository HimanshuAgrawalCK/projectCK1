package com.example.Login.dto.Snowflake;

import lombok.Getter;

@Getter
public enum CostExplorerColumns {

    LINKED_ACCOUNT_ID("Linked Account ID", "LINKEDACCOUNTID"),
    START_DAY("Start Day", "MYCLOUD_STARTDAY"),
    START_MONTH("Start Month", "MYCLOUD_STARTMONTH"),
    START_YEAR("Start Year", "MYCLOUD_STARTYEAR"),
    OPERATION("Operation", "LINEITEM_OPERATION"),
    USAGE_TYPE("Usage Type", "LINEITEM_USAGETYPE"),
    INSTANCE_TYPE("Instance Type", "MYCLOUD_INSTANCETYPE"),
    OPERATING_SYSTEM("Operating System", "MYCLOUD_OPERATINGSYSTEM"),
    PRICING_TYPE("Pricing Type", "MYCLOUD_PRICINGTYPE"),
    REGION_NAME("Region Name", "MYCLOUD_REGIONNAME"),
    USAGE_START_DATE("Usage Start Date", "USAGESTARTDATE"),
    DATABASE_ENGINE("Database Engine", "PRODUCT_DATABASEENGINE"),
    PRODUCT_NAME("Product Name", "PRODUCT_PRODUCTNAME"),
    UNBLENDED_COST("Unblended Cost", "LINEITEM_UNBLENDEDCOST"),
    USAGE_AMOUNT("Usage Amount", "LINEITEM_USAGEAMOUNT"),
    GROUP_TYPE("Group Type", "MYCLOUD_COST_EXPLORER_USAGE_GROUP_TYPE"),
    UNIT("Pricing Unit", "PRICING_UNIT"),
    CHARGE_TYPE("Charge Type", "CHARGE_TYPE"),
    AVAILABILITY_ZONE("Availability Zone", "AVAILABILITYZONE"),
    TENANCY("Tenancy", "TENANCY");

    private final String displayName;
    private final String dbColumnName;

    CostExplorerColumns(String displayName, String dbColumnName) {
        this.displayName = displayName;
        this.dbColumnName = dbColumnName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDbColumnName() {
        return dbColumnName;
    }

    public static CostExplorerColumns fromDisplayName(String displayName) {
        for (CostExplorerColumns column : values()) {
            if (column.displayName.equalsIgnoreCase(displayName)) {
                return column;
            }
        }
        throw new IllegalArgumentException("Invalid display name: " + displayName);
    }

    public static CostExplorerColumns fromDbColumn(String dbColumnName) {
        for (CostExplorerColumns column : values()) {
            if (column.dbColumnName.equalsIgnoreCase(dbColumnName)) {
                return column;
            }
        }
        throw new IllegalArgumentException("Invalid DB column name: " + dbColumnName);
    }
}

