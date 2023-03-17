package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.adminLoginScenario
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.LoginModule.*
import com.gatling.tests.Modules.LoginModule.*

class RouteListTest extends Simulation {
  val headers_2 = Map(
    "Accept" -> "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

  val headers_6 = Map(
    "Accept" -> "application/json, text/plain, */*",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Content-Type" -> "application/json;charset=UTF-8",
    "Origin" -> "http://192.168.3.205:32677",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

  val headers_9 = Map(
    "Accept" -> "application/json, text/javascript, */*; q=0.01",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Content-Type" -> "application/json",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
    "X-Requested-With" -> "XMLHttpRequest",
    "authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiIzZDdhNjkwOC0xOGFjLTRjODgtOTgzZi00M2IzOGU5N2NiZWIiLCJpYXQiOjE2Nzg5NTI0NjgsImV4cCI6MTY3ODk1NjA2OH0.j2prQZGiV9EmbRl9rcCkX0mBR6Z6ww2Botumm9H7D44")

  val headers_10 = Map(
    "Accept" -> "application/json, text/plain, */*",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
    "authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWQiOiIzZDdhNjkwOC0xOGFjLTRjODgtOTgzZi00M2IzOGU5N2NiZWIiLCJpYXQiOjE2Nzg5NTI0NjgsImV4cCI6MTY3ODk1NjA2OH0.j2prQZGiV9EmbRl9rcCkX0mBR6Z6ww2Botumm9H7D44")

  //Scenario that tests adding route
  val routeAdd: ScenarioBuilder = scenario("Admins Adding Route")
    //.exec(adminLoginScenario) //Log into system as admin
    .exec(http("Home Page")
      .get("/")
      .resources(http("Fonts")
        .get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
        http("Generate Code")
          .get("/api/v1/verifycode/generate")
          .headers(headers_2),
        http("Generate Code")
          .get("/api/v1/verifycode/generate")
          .headers(headers_2)))
    .pause(4)
    .exec(http("Admin Login Page")
      .get("/adminlogin.html")
      .resources(http("Fonts")
        .get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3")))
    .pause(6)
    .exec(http("Submit Admin Login")
      .post("/api/v1/users/login")
      .headers(headers_6)
      .body(RawFileBody("com/gatling/tests/newtest/0006_request.json"))
      .resources(http("Admin Page")
        .get("/admin.html"),
        http("Fonts")
          .get("/assets/fonts/fontawesome-webfont.woff2?v=4.6.3"),
        http("Get Users")
          .get("/api/v1/userservice/users")
          .headers(headers_9),
        http("Get Admin Orders")
          .get("/api/v1/adminorderservice/adminorder")
          .headers(headers_10),
        http("Get Travel")
          .get("/api/v1/admintravelservice/admintravel")
          .headers(headers_9)))
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "adminrouteservice/adminroute",
        "file_path" -> "RouteListAdmin/add_route_form.json")
      newSession
    }
    //Go to route page and complete add
    .exec(routePage, completeAction, routePage)
    .pause(1)

  //Scenario that tests deleting route
  val routeDelete: ScenarioBuilder = scenario("Admins Deleting Route")
    .exec(adminLoginScenario)
    .exec { session => //Set up session information
      val newSession = session.setAll("delete_id" -> "0b23bd3e-876a-4af3-b920-c50a90c90b04",
        "endpoint" -> "adminrouteservice/adminroute",
        "type" -> "Route")
      newSession
    }
    //Go to route page and delete route
    .exec(routePage, delete, routePage)
    .pause(1)

  val checkRoute = scenario("Check Route Endpoints")
    .exec(
      http("Get Cheapest Route")
        .post("/api/v1/routeplanservice/routePlan/cheapestRoute"))
    .exec(
      http("Get Cheapest Travel Plan")
        .post("/api/v1/travelplanservice/travelPlan/cheapest"))
    .exec(
      http("Get Payment")
        .get("/api/v1/paymentservice/payment")
        .headers(apiV1Header))
    .exec(
      http("Get Routes (Route Service)")
        .get("/api/v1/routeservice/routes"))

  setUp(
    routeAdd.inject(rampUsers(1).during(15)),
    //routeDelete.inject(rampUsers(5).during(15)),
    //checkRoute.inject(rampUsers(5).during(10))
  ).protocols(httpProtocolTrainTicket)
}