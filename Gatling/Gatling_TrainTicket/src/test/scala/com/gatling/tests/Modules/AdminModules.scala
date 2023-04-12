package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.structure.ChainBuilder

object AdminModules {

  //Go to configuration page and get needed resources
  val configPage: ChainBuilder = exec(http("Config List Page")
    .get("/admin_config.html")
    .headers(mainPageHeader)
    .resources(http("Get Configs")
      .get("/api/v1/adminbasicservice/adminbasic/configs") //Get configs
      .headers(apiV1Header)))
    .pause(6)

  //Go to contacts page and get needed resources
  val contactsPage: ChainBuilder = exec(http("Contacts List Page")
    .get("/admin_contacts.html")
    .headers(mainPageHeader)
    .resources(http("Get Contacts")
      .get("/api/v1/adminbasicservice/adminbasic/contacts") //Get contacts
      .headers(apiV1Header)))
    .pause(5)

  //Go to price list page and get needed resources
  val pricePage: ChainBuilder = exec(http("Price List Page")
    .get("/admin_price.html")
    .headers(mainPageHeader)
    .resources(http("Get Prices")
      .get("/api/v1/adminbasicservice/adminbasic/prices") //Get prices
      .headers(apiV1Header)))
    .pause(5)

  //Go to route list page and get needed resources
  val routePage: ChainBuilder = exec(http("Route List Page")
    .get("/admin_route.html")
    .headers(mainPageHeader)
    .resources(http("Get Routes")
      .get("/api/v1/adminrouteservice/adminroute") //Get routes
      .headers(apiV1Header)))
    .pause(4)

  //Go to station list page and get needed resources
  val stationPage: ChainBuilder = exec(http("Station List Page")
    .get("/admin_station.html")
    .headers(mainPageHeader)
    .resources(http("Get Stations")
      .get("/api/v1/adminbasicservice/adminbasic/stations") //Get stations
      .headers(apiV1Header)))
    .pause(5)

  //Go to train list page and get needed resources
  val trainPage: ChainBuilder = exec(http("Train List Page")
    .get("/admin_train.html")
    .headers(mainPageHeader)
    .resources(http("Get Trains")
      .get("/api/v1/adminbasicservice/adminbasic/trains") //Get trains
      .headers(apiV1Header)))
    .pause(7)

  //Go to travel list page and get needed resources
  val travelPage: ChainBuilder = exec(http("Travel List Page")
    .get("/admin_travel.html")
    .headers(mainPageHeader)
    .resources(http("Get Trains")
      .get("/api/v1/trainservice/trains") //Get trains
      .headers(apiV1Header),
      http("Get Stations")
        .get("/api/v1/stationservice/stations") //Get stations
        .headers(apiV1Header),
      http("Get Routes")
        .get("/api/v1/routeservice/routes") //Get routes
        .headers(apiV1Header),
      http("Get Travel Info")
        .get("/api/v1/admintravelservice/admintravel") //Get travel
        .headers(apiV1Header)))
    .pause(6)

  //Go to user list page and get needed resources
  val userPage: ChainBuilder = exec(http("User List Page")
    .get("/admin_user.html")
    .headers(mainPageHeader)
    .resources(http("Get Users")
      .get("/api/v1/adminuserservice/users") //Get users
      .headers(apiV1Header)))
    .pause(5)
}
