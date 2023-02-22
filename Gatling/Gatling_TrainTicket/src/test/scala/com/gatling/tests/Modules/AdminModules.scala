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

  /**Invalid json in add_config_invalid*/
  val addConfig = exec(http("Add Config")
    .post("/api/v1/adminbasicservice/adminbasic/configs")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/ConfigListAdmin/add_config_form.json")))
    .pause(8)

  /**Same as addConfig*/
  val updateConfig = exec(http("Update Config")
    .put("/api/v1/adminbasicservice/adminbasic/configs")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/ConfigListAdmin/update_config_form.json")))
    .pause(7)

  val deleteConfig = exec(http("request_3")
    .delete("/api/v1/adminbasicservice/adminbasic/configs/TestConfig")
    .headers(apiV1Header))
    .pause(4)

  val contactsPage = exec(http("Contacts List Page")
    .get("/admin_contacts.html")
    .headers(mainPageHeader)
    .resources(http("Get Contacts")
      .get("/api/v1/adminbasicservice/adminbasic/contacts")
      .headers(apiV1Header)))
    .pause(5)

  val addContact = exec(http("Add Contact")
    .post("/api/v1/adminbasicservice/adminbasic/contacts")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/ContactListAdmin/add_contact_form.json")))
    .pause(7)

  val updateContact = exec(http("Update Contact")
    .post("/api/v1/adminbasicservice/adminbasic/contacts")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/ContactListAdmin/update_contact_form.json")))
    .pause(7)

  val deleteContact = exec(http("Delete Contact")
    .delete("/api/v1/adminbasicservice/adminbasic/contacts/5729b7bf-f1c8-4d82-8773-9bbb682489f5")
    .headers(apiV1Header))
    .pause(6)

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

  val addOrderAdmin = exec(http("Add Order")
    .post("/api/v1/adminorderservice/adminorder")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/OrderListAdmin/add_order_form.json")))
    .pause(7)

  val updateOrder = exec(http("Add Order")
    .post("/api/v1/adminorderservice/adminorder")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/OrderListAdmin/updateorder/add_order_form.json")))
    .pause(7)

  val deleteOrder = exec(http("Delete Order")
    .delete("/api/v1/adminorderservice/adminorder/301f39ba-f31d-4795-bac2-cbc8909a7e97/G1237")
    .headers(apiV1Header))
    .pause(5)

  val pricePage = exec(http("Price List Page")
    .get("/admin_price.html")
    .headers(mainPageHeader)
    .resources(http("Get Prices")
      .get("/api/v1/adminbasicservice/adminbasic/prices")
      .headers(apiV1Header)))
    .pause(5)

  val addPrice = exec(http("Add Price")
    .post("/api/v1/adminbasicservice/adminbasic/prices")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/PriceListAdmin/add_price_form.json")))
    .pause(6)

  val updatePrice = exec(http("Update Price")
    .put("/api/v1/adminbasicservice/adminbasic/prices")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/PriceListAdmin/update_price_form.json")))
    .pause(6)

  val deletePrice = exec(http("Delete Price")
    .delete("/api/v1/adminbasicservice/adminbasic/prices/dd0e572e-7443-420c-8280-7d8215636069")
    .headers(apiV1Header))
    .pause(5)

  val routePage = exec(http("Route List Page")
    .get("/admin_route.html")
    .headers(mainPageHeader)
    .resources(http("Get Routes")
      .get("/api/v1/adminrouteservice/adminroute")
      .headers(apiV1Header)))
    .pause(4)

  val addRoute = exec(http("Add Route")
    .post("/api/v1/adminrouteservice/adminroute")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/RouteListAdmin/add_route_form.json")))
    .pause(4)

  val deleteRoute = exec(http("Delete Route")
    .delete("/api/v1/adminrouteservice/adminroute/0b23bd3e-876a-4af3-b920-c50a90c90b04")
    .headers(apiV1Header))
    .pause(5)

  val stationPage = exec(http("Station List Page")
    .get("/admin_station.html")
    .headers(mainPageHeader)
    .resources(http("Get Stations")
      .get("/api/v1/adminbasicservice/adminbasic/stations")
      .headers(apiV1Header)))
    .pause(5)

  val addStation = exec(http("Add Station")
    .post("/api/v1/adminbasicservice/adminbasic/stations")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/StationListAdmin/add_station_form.json")))
    .pause(5)

  val deleteStation = exec(http("Delete Station")
    .delete("/api/v1/adminbasicservice/adminbasic/stations/5307f68c-dc6d-4461-a262-354be961827f")
    .headers(apiV1Header))
    .pause(5)

  val trainPage = exec(http("Train List Page")
    .get("/admin_train.html")
    .headers(mainPageHeader)
    .resources(http("Get Trains")
      .get("/api/v1/adminbasicservice/adminbasic/trains")
      .headers(apiV1Header)))
    .pause(7)

  val addTrain = exec(http("Add Train")
    .post("/api/v1/adminbasicservice/adminbasic/trains")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/TrainListAdmin/add_train_form.json")))
    .pause(5)

  val deleteTrain = exec(http("Delete Train")
    .delete("/api/v1/adminbasicservice/adminbasic/trains/4d02f2f3-d08e-4bae-bf38-dc2c955f7afd")
    .headers(apiV1Header))
    .pause(5)

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

  val addTravel = exec(http("Add Travel")
    .put("/api/v1/admintravelservice/admintravel")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/TravelListAdmin/update_travel_invalid.json")))
    .pause(5)

  val deleteTravel = exec(http("Delete Travel")
    .delete("/api/v1/admintravelservice/admintravel/Z1235")
    .headers(apiV1Header))
    .pause(5)

  val userPage = exec(http("User List Page")
    .get("/admin_user.html")
    .headers(mainPageHeader)
    .resources(http("Get Users")
      .get("/api/v1/adminuserservice/users")
      .headers(apiV1Header)))
    .pause(5)

  val addUser = exec(http("Add User")
    .post("/api/v1/adminuserservice/users")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/UserListAdmin/add_user_form.json")))
    .pause(6)

  val deleteUser = exec(http("Delete User")
    .delete("/api/v1/adminuserservice/users/6b5b0b6d-b233-4443-89e4-d28c72dc237b")
    .headers(apiV1Header))
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
