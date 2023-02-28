package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import scala.util.Random

object NavigationModules {

  //Adding an item to the cart
  val addItem = exec(http("Add Item to Cart")
    .post("/Cart/AddToCart")
    .formParam("id", "${itemId}")
    .check(status.is(200)))
    .pause(4)

  //View the users cart
  val viewCart = exec(http("View Cart")
    .get("/Cart"))
    .pause(3)

  //Update the cart with given item and quantity
  val updateCart = exec(http("Update Cart")
    .post("/Cart")
    .formParam("quantities[0].Key", "${key}")
    .formParam("quantities[0].Value", "${quantity}"))
    .pause(7)

  //Start the checkout process
  val selectCheckout = exec(http("Click Checkout")
    .post("/Cart")
    .formParam("quantities[0].Key", "${key}")
    .formParam("quantities[0].Value", "${quantity}"))
    .pause(7)

  //Submit checkout information with given parameters
  val submitCheckout = exec(http("Submit Checkout")
    .post("/Order/Checkout")
    .formParam("Street", "#{street}")
    .formParam("City", "#{city}")
    .formParam("State", "#{state}")
    .formParam("Country", "#{country}")
    .formParam("CardNumber", "#{cardNum}")
    .formParam("CardHolderName", "#{cardHolder}")
    .formParam("CardExpirationShort", "#{cardExp}")
    .formParam("CardSecurityNumber", "#{securityNum}")
    .formParam("orderitems[0].ProductName", ".NET Black & White Mug")
    .formParam("orderitems[0].UnitPrice", "8.5")
    .formParam("orderitems[0].Units", "1")
    .formParam("Total", "8.5")
    .formParam("action", "[ Place Order ]")
    .formParam("ZipCode", "#{zip}"))
    .pause(8)

  //View the past orders of the user
  val viewPastOrders = exec(http("Past Order Page")
    .get("/Order"))
    .pause(3)

  //Cancel the order
  val cancelOrder = exec(http("Cancel Order")
    .get("/Order/cancel?orderId=1"))
    .pause(4)

  //Filter items with given parameters
  val filterItems = exec(http("Filter Items on ${filter_1_name} and ${filter_2_name}")
    .post("/")
    .formParam("BrandFilterApplied", "#{filter_1}") //dynamically filter using parameters from csv
    .formParam("TypesFilterApplied", "#{filter_2}"))
    .pause(2)

  //Navigate to different pages on the home page
  val navigateHomePage = exec(http("GoTo Page ${pageNumber}")
    .get("/?page=#{pageNumber}"))
    .pause(4)

  //View order details of given order
  val viewOrderDetails = exec(http("View Second Order")
    .get("/Order/Detail?orderId=1"))
    .pause(5)
}
