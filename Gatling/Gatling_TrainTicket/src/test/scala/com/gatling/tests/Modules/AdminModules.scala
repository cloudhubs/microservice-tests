package com.gatling.tests.Modules

import io.gatling.http.Predef.http
import com.gatling.tests.Modules.HeaderModules.*

object AdminModules {

  val configPage: Any = exec(http("Config List Page")
    .get("/admin_config.html")
    .headers(mainPageHeader)
    .resources(http("Get Configs")
      .get("/api/v1/adminbasicservice/adminbasic/configs")
      .headers(apiV1Header)))
    .pause(6)

  /**Invalid json in add_config_invalid*/
  val addConfig: Any = exec(http("Add Config")
    .post("/api/v1/adminbasicservice/adminbasic/configs")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/ConfigListAdmin/addconfig/0003_request.json")))
    .pause(8)

  /**Same as addConfig*/
  val updateConfig: Any = exec(http("Update Config")
    .put("/api/v1/adminbasicservice/adminbasic/configs")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/ConfigListAdmin/updateconfig/0003_request.json")))
    .pause(7)

  val deleteConfig: Any = exec(http("request_3")
    .delete("/api/v1/adminbasicservice/adminbasic/configs/TestConfig")
    .headers(apiV1Header))
    .pause(1)

  val contactsPage: Any = exec(http("Contacts List Page")
    .get("/admin_contacts.html")
    .headers(mainPageHeader)
    .resources(http("Get Contacts")
      .get("/api/v1/adminbasicservice/adminbasic/contacts")
      .headers(apiV1Header)))
    .pause(5)

  val addContact: Any = exec(http("Add Contact")
    .post("/api/v1/adminbasicservice/adminbasic/contacts")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/ContactListAdmin/addcontact/0003_request.json")))
    .pause(7)

  val updateContact: Any = exec(http("Update Contact")
    .post("/api/v1/adminbasicservice/adminbasic/contacts")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/ContactListAdmin/updatecontact/0003_request.json")))
    .pause(7)

  val deleteContact: Any = exec(http("Delete Contact")
    .delete("/api/v1/adminbasicservice/adminbasic/contacts/5729b7bf-f1c8-4d82-8773-9bbb682489f5")
    .headers(apiV1Header))
    .pause(6)

  val adminOrderPage: Any = exec(http("Admin Order List Page")
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

  val addOrderAdmin: Any = exec(http("Add Order")
    .post("/api/v1/adminorderservice/adminorder")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/OrderListAdmin/addorder/0008_request.json")))
    .pause(7)

  val updateOrder: Any = exec(http("Add Order")
    .post("/api/v1/adminorderservice/adminorder")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/OrderListAdmin/updateorder/0008_request.json")))
    .pause(7)

  val deleteOrder: Any = exec(http("Delete Order")
    .delete("/api/v1/adminorderservice/adminorder/301f39ba-f31d-4795-bac2-cbc8909a7e97/G1237")
    .headers(apiV1Header))
    .pause(5)

  val pricePage: Any = exec(http("Price List Page")
    .get("/admin_price.html")
    .headers(mainPageHeader)
    .resources(http("Get Prices")
      .get("/api/v1/adminbasicservice/adminbasic/prices")
      .headers(apiV1Header)))
    .pause(5)

  val addPrice: Any = exec(http("Add Price")
    .post("/api/v1/adminbasicservice/adminbasic/prices")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/PriceListAdmin/addprice/0003_request.json")))
    .pause(6)

  val updatePrice: Any = exec(http("Update Price")
    .put("/api/v1/adminbasicservice/adminbasic/prices")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/PriceListAdmin/updateprice/0003_request.json")))
    .pause(6)

  val deletePrice: Any = exec(http("Delete Price")
    .delete("/api/v1/adminbasicservice/adminbasic/prices/dd0e572e-7443-420c-8280-7d8215636069")
    .headers(apiV1Header))
    .pause(5)

  val routePage: Any = exec(http("Route List Page")
    .get("/admin_route.html")
    .headers(mainPageHeader)
    .resources(http("Get Routes")
      .get("/api/v1/adminrouteservice/adminroute")
      .headers(apiV1Header)))
    .pause(4)

  val addRoute: Any = exec(http("Add Route")
    .post("/api/v1/adminrouteservice/adminroute")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/RouteListAdmin/addroute/0003_request.json")))
    .pause(4)

  val deleteRoute: Any = exec(http("Delete Route")
    .delete("/api/v1/adminrouteservice/adminroute/0b23bd3e-876a-4af3-b920-c50a90c90b04")
    .headers(apiV1Header))
    .pause(5)

  val stationPage: Any = exec(http("Station List Page")
    .get("/admin_station.html")
    .headers(mainPageHeader)
    .resources(http("Get Stations")
      .get("/api/v1/adminbasicservice/adminbasic/stations")
      .headers(apiV1Header)))
    .pause(5)

  val addStation: Any = exec(http("Add Station")
    .post("/api/v1/adminbasicservice/adminbasic/stations")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/StationListAdmin/addstation/0003_request.json")))
    .pause(5)

  val deleteStation: Any = exec(http("Delete Station")
    .delete("/api/v1/adminbasicservice/adminbasic/stations/5307f68c-dc6d-4461-a262-354be961827f")
    .headers(apiV1Header))
    .pause(5)

  val trainPage: Any = exec(http("Train List Page")
    .get("/admin_train.html")
    .headers(mainPageHeader)
    .resources(http("Get Trains")
      .get("/api/v1/adminbasicservice/adminbasic/trains")
      .headers(apiV1Header)))
    .pause(7)

  val addTrain: Any = exec(http("Add Train")
    .post("/api/v1/adminbasicservice/adminbasic/trains")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/TrainListAdmin/addtrain/0003_request.json")))
    .pause(5)

  val deleteTrain: Any = exec(http("Delete Train")
    .delete("/api/v1/adminbasicservice/adminbasic/trains/4d02f2f3-d08e-4bae-bf38-dc2c955f7afd")
    .headers(apiV1Header))
    .pause(5)

  val travelPage: Any = exec(http("Travel List Page")
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

  val addTravel: Any = exec(http("Add Travel")
    .put("/api/v1/admintravelservice/admintravel")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/TravelListAdmin/updatetravelinvalid/0006_request.json")))
    .pause(5)

  val deleteTravel: Any = exec(http("Delete Travel")
    .delete("/api/v1/admintravelservice/admintravel/Z1235")
    .headers(apiV1Header))
    .pause(5)

  val userPage: Any = exec(http("User List Page")
    .get("/admin_user.html")
    .headers(mainPageHeader)
    .resources(http("Get Users")
      .get("/api/v1/adminuserservice/users")
      .headers(apiV1Header)))
    .pause(5)

  val addUser: Any = exec(http("Add User")
    .post("/api/v1/adminuserservice/users")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/UserListAdmin/adduser/0003_request.json")))
    .pause(6)

  val deleteUser: Any = exec(http("Delete User")
    .delete("/api/v1/adminuserservice/users/6b5b0b6d-b233-4443-89e4-d28c72dc237b")
    .headers(apiV1Header))
    .pause(5)
}
