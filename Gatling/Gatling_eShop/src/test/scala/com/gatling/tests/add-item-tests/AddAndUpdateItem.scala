package com.gatling.tests.addItem

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class AddAndUpdateItem extends Simulation {

  val httpProtocol = http
    .baseUrl("http://host.docker.internal:5100")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*""", """.*\.PNG""", """.*/pic/""", """.*\.svg""", """.*/js/site.js.*""", """.*/hub/notificationhub/.*"""), WhiteList())
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")


  val addItem = exec(http("Home Page")
    .get("/"))
    .pause(3)
    .exec(http("Add Item to Cart")
      .post("/Cart/AddToCart")
      .formParam("id", "11")
      .formParam("__RequestVerificationToken", "CfDJ8HI2B0m15WhGp3yVc3O5P6kWRecVlATohMdinW-buiGCStez0hkF1Pp2l4s5-oXWOnBrSEN-mUPRZrkZS8Tq-9q84NuuzIb8ZfdGMFFIofJwJOfawcicY2kWgzAJ23bjCyCUm4EDbN30b3wdajeRRY6FbqoYxo9_GCX9XrZ7vQi25nNP9gFqpKQlLQlCFBbvJA"))
    .pause(2)
    .exec(http("View Cart")
      .get("/Cart"))
    .pause(3)

  val updateItem = exec(http("Update Cart")
    .post("/Cart")
    .formParam("quantities[0].Key", "f0f94a1b-1c89-4c11-b853-6c798f67f6cd")
    .formParam("quantities[0].Value", "1")
    .formParam("quantities[1].Key", "08d297a0-6e6a-48c2-884c-5ff7c61ff1cd")
    .formParam("quantities[1].Value", "1")
    .formParam("quantities[2].Key", "fe477dfc-68c0-4d20-89dd-4d4dea697f64")
    .formParam("quantities[2].Value", "3")
    .formParam("quantities[3].Key", "13080d0e-9dd2-4131-9821-fde5dd0f1ab9")
    .formParam("quantities[3].Value", "2")
    .formParam("quantities[4].Key", "0683d3f4-992d-467d-9db0-87cee62b9807")
    .formParam("quantities[4].Value", "1")
    .formParam("name", "")
    .formParam("__RequestVerificationToken", "CfDJ8HI2B0m15WhGp3yVc3O5P6kW_YETMzcubcrFllddlRIWKd-qMC9EIIS0MeW5EpHwQmSTFHLfrXwlykomLoDOekmF93Q1BbEdn9dTvHsSxqzQdlnr49i9AiHF7Cv3_kes17mVw8BReU1GbTdxJDiuMpSse9G4eh3fetZKnC35PAPEnjNbffttrmreyHDom3A91Q"))
    .pause(3)
    .exec(http("Home Page")
      .get("/"))
    .pause(1)

  val users1 = scenario("Users1").exec(addItem)

  val users2 = scenario("Users2").exec(addItem, updateItem)

  setUp(
    users1.inject(rampUsers(6).during(10)),
    users2.inject(rampUsers(4).during(10))
  ).protocols(httpProtocol)
}