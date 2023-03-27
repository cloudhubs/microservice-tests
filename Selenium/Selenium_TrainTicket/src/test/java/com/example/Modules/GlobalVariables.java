/**
 * Contains the global variables to be used for testing
 */

package com.example.Modules;

public class GlobalVariables {
    // The values for the username and password
    public static final String CLIENT_USERNAME = "fdse_microservice";
    public static final String CLIENT_PASSWORD = "111111";
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "222222";

    public static final String A_USERNAME = "admin";
    public static final String A_PASSWORD = "222222";

    // The list of management services offered to a client
    public static final String CLIENT_MGT_LIST[] = new String[]
            { "Ticket Reserve", "Order List", "Consign List", "Advanced Search", "Execute Flow" };
    public static final String CLIENT_FLOW_LIST[] = new String[]
            { "Ticket Collect", "Enter Station" };

    // The list of management services offered to an admin
    public static final String ADMIN_MGT_LIST[] = new String[]
            { "Order", "Route", "Travel", "User", "Basic" };
    public static final String ADMIN_BASIC_LIST[] = new String[]
            { "Contact", "Station", "Train", "Price", "Config" };



}
