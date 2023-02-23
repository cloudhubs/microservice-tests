package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
object NavigationModules {

  //Adding an item to the cart
  val addItem = exec(http("Add Item to Cart")
    .post("/Cart/AddToCart")
    .formParam("id", "${itemId}")) //Item can be between 1-14
    //.formParam("__RequestVerificationToken", "CfDJ8HI2B0m15WhGp3yVc3O5P6kWRecVlATohMdinW-buiGCStez0hkF1Pp2l4s5-oXWOnBrSEN-mUPRZrkZS8Tq-9q84NuuzIb8ZfdGMFFIofJwJOfawcicY2kWgzAJ23bjCyCUm4EDbN30b3wdajeRRY6FbqoYxo9_GCX9XrZ7vQi25nNP9gFqpKQlLQlCFBbvJA"))
    .pause(4)

  val viewCart = exec(http("View Cart")
    .get("/Cart"))
    .pause(3)

  val updateCart = exec(http("Update Cart")
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
    .pause(7)

  val selectCheckout = exec(http("Click Checkout")
    .post("/Cart")
    .formParam("quantities[0].Key", "43286de8-16ed-420c-9af0-fdc644b283c7")
    .formParam("quantities[0].Value", "1")
    .formParam("action", "[ Checkout ]"))
    .pause(7)

  val submitCheckout = exec(http("Submit Checkout")
    .post("/Order/Checkout")
    .formParam("Street", "1001 Speight Ave")
    .formParam("City", "Waco")
    .formParam("State", "TX")
    .formParam("Country", "United States of America")
    .formParam("CardNumber", "4118102062335380")
    .formParam("CardHolderName", "Sheldon Smith")
    .formParam("CardExpirationShort", "02/32")
    .formParam("CardSecurityNumber", "843")
    .formParam("orderitems[0].PictureUrl", "http://host.docker.internal:5202/c/api/v1/catalog/items/2/pic/")
    .formParam("orderitems[0].ProductName", ".NET Black & White Mug")
    .formParam("orderitems[0].UnitPrice", "8.5")
    .formParam("orderitems[0].Units", "1")
    .formParam("Total", "8.5")
    .formParam("action", "[ Place Order ]")
    .formParam("ZipCode", "76706")
    .formParam("RequestId", "fdb6265e-34e1-43be-885d-ac5f1571be78"))
    .pause(8)

  val viewPastOrders = exec(http("Past Order Page")
    .get("/Order"))
    .pause(3)

  val cancelOrder = exec(http("Cancel Order")
    .get("/Order/cancel?orderId=${orderId}"))
    .pause(4)

  //Filter items execution
  val filterItems = exec(http("Filter Items on ${filter_1_name} and ${filter_2_name}")
    .post("/")
    .formParam("BrandFilterApplied", "#{filter_1}") //dynamically filter using parameters from csv
    .formParam("TypesFilterApplied", "#{filter_2}"))
    .pause(2)

  //Nagivate to different pages on the home page
  val navigateHomePage = exec(http("GoTo Page ${pageNumber}")
    .get("/?page=#{pageNumber}"))
    .pause(4)

  val viewOrderDetails = exec(http("View Second Order")
    .get("/Order/Detail?orderId=#{orderId}"))
    .pause(5)
}
