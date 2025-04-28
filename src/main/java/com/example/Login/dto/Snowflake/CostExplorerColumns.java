package com.example.Login.dto.Snowflake;

import lombok.Getter;

@Getter
public enum CostExplorerColumns {

    PRODUCT_NAME("Product Name", "PRODUCT_PRODUCTNAME",true),
    LINKED_ACCOUNT_ID("Linked Account ID", "LINKEDACCOUNTID",false),
    START_DAY("Start Day", "MYCLOUD_STARTDAY",false),
    START_MONTH("Start Month", "MYCLOUD_STARTMONTH",false),
    START_YEAR("Start Year", "MYCLOUD_STARTYEAR",false),
    OPERATION("Operation", "LINEITEM_OPERATION",true),
    USAGE_TYPE("Usage Type", "LINEITEM_USAGETYPE",true),
    INSTANCE_TYPE("Instance Type", "MYCLOUD_INSTANCETYPE",true),
    OPERATING_SYSTEM("Operating System", "MYCLOUD_OPERATINGSYSTEM",true),
    PRICING_TYPE("Pricing Type", "MYCLOUD_PRICINGTYPE",true),
    REGION_NAME("Region Name", "MYCLOUD_REGIONNAME",true),
    USAGE_START_DATE("Usage Start Date", "USAGESTARTDATE",false),
    DATABASE_ENGINE("Database Engine", "PRODUCT_DATABASEENGINE",true),
    UNBLENDED_COST("Unblended Cost", "LINEITEM_UNBLENDEDCOST",false),
    USAGE_AMOUNT("Usage Amount", "LINEITEM_USAGEAMOUNT",false),
    GROUP_TYPE("Group Type", "MYCLOUD_COST_EXPLORER_USAGE_GROUP_TYPE",true),
    UNIT("Pricing Unit", "PRICING_UNIT",false),
    CHARGE_TYPE("Charge Type", "CHARGE_TYPE",true),
    AVAILABILITY_ZONE("Availability Zone", "AVAILABILITYZONE",true),
    TENANCY("Tenancy", "TENANCY",true);

    private final String displayName;
    private final String dbColumnName;
    private final boolean display;

    CostExplorerColumns(String displayName, String dbColumnName, boolean display) {
        this.displayName = displayName;
        this.dbColumnName = dbColumnName;
        this.display = display;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDbColumnName() {
        return dbColumnName;
    }

    public boolean getDisplay(){
        return display;
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

