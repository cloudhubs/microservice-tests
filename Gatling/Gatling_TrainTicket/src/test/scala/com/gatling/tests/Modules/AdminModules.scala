package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.HeaderModules.*

object AdminModules {

  val configPage = exec(http("Config List Page")
    .get("/admin_config.html")
    .headers(mainPageHeader)
    .resources(http("Get Configs")
      .get("/api/v1/adminbasicservice/adminbasic/configs")
      .headers(apiV1Header)))
    .pause(6)

  /**
   * Function to complete either add and update of a certain item
   *
   * operation = either Add or Update
   * endpoint = the needed endpoint depending on item to update/add
   * file_path = the file path to the json file
   */
  val action = exec(http("${operation} Config")
    .post("/api/v1/#{endpoint}")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/#{file_path}")))
    .pause(8)

  /**
   * Function to execute a delete of a given item
   * 
   * type = the type of item to be deleted
   * endpoint = the needed endpoint for deletion
   * delete_id = the id of the item to delete
   */
  val delete = exec(http("Delete ${type}")
    .delete("/api/v1/#{endpoint}/#{delete_id}")
    .headers(apiV1Header))
    .pause(4)

  val contactsPage = exec(http("Contacts List Page")
    .get("/admin_contacts.html")
    .headers(mainPageHeader)
    .resources(http("Get Contacts")
      .get("/api/v1/adminbasicservice/adminbasic/contacts")
      .headers(apiV1Header)))
    .pause(5)

  val adminOrderPage = exec(http("Admin Order List Page")
    .get("/admin.html")
    .headers(mainPageHeader)
    .resources(http("Get Users")
      .get("/api/v1/userservice/users")
      .headers(apiV1Header),
      http("Get Orders")
        .get("/api/v1/adminorderservice/adminorder")
        .headers(apiV1Header),
      http("Get Travels")
        .get("/api/v1/admintravelservice/admintravel")
        .headers(apiV1Header)))
    .pause(6)

  val pricePage = exec(http("Price List Page")
    .get("/admin_price.html")
    .headers(mainPageHeader)
    .resources(http("Get Prices")
      .get("/api/v1/adminbasicservice/adminbasic/prices")
      .headers(apiV1Header)))
    .pause(5)

  val routePage = exec(http("Route List Page")
    .get("/admin_route.html")
    .headers(mainPageHeader)
    .resources(http("Get Routes")
      .get("/api/v1/adminrouteservice/adminroute")
      .headers(apiV1Header)))
    .pause(4)

  val stationPage = exec(http("Station List Page")
    .get("/admin_station.html")
    .headers(mainPageHeader)
    .resources(http("Get Stations")
      .get("/api/v1/adminbasicservice/adminbasic/stations")
      .headers(apiV1Header)))
    .pause(5)

  val trainPage = exec(http("Train List Page")
    .get("/admin_train.html")
    .headers(mainPageHeader)
    .resources(http("Get Trains")
      .get("/api/v1/adminbasicservice/adminbasic/trains")
      .headers(apiV1Header)))
    .pause(7)

  val travelPage = exec(http("Travel List Page")
    .get("/admin_travel.html")
    .headers(mainPageHeader)
    .resources(http("Get Trains")
      .get("/api/v1/trainservice/trains")
      .headers(apiV1Header),
      http("Get Stations")
        .get("/api/v1/stationservice/stations")
        .headers(apiV1Header),
      http("Get Routes")
        .get("/api/v1/routeservice/routes")
        .headers(apiV1Header),
      http("Get Travel Info")
        .get("/api/v1/admintravelservice/admintravel")
        .headers(apiV1Header)))
    .pause(6)

  val userPage = exec(http("User List Page")
    .get("/admin_user.html")
    .headers(mainPageHeader)
    .resources(http("Get Users")
      .get("/api/v1/adminuserservice/users")
      .headers(apiV1Header)))
    .pause(5)

  val adminLoginPage = exec(http("Go to Login Page")
    .get("/adminlogin.html")
    .headers(mainPageHeader))
    .pause(3)

  val adminLogin = exec(http("Send Login Request")
    .post("/api/v1/users/login")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/Login/admin_login.json")))

    val adminHomePage = exec(http("Go to Admin Page")
      .get("/admin.html")
      .resources(http("Get User List")
        .get("/api/v1/userservice/users")
        .headers(apiV1Header),
      http("Get Order List")
        .get("/api/v1/adminorderservice/adminorder")
        .headers(apiV1Header),
      http("Get Travel List")
        .get("/api/v1/admintravelservice/admintravel")
        .headers(apiV1Header)))
}
