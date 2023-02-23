package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
object NavigationModules {

  //Accessing the home page of the application
  val homePage = exec(http("Home Page")
    .get("/"))
    .pause(3)

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

  val loginPage = exec(http("Sign In Page")
    .get("/Account/SignIn"))
    .pause(4)

  val login = exec(http("Submit Sign In")
    .post("http://host.docker.internal:5105/Account/Login?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3Dmvc%26redirect_uri%3Dhttp%253A%252F%252Fhost.docker.internal%253A5100%252Fsignin-oidc%26response_type%3Dcode%2520id_token%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub%26response_mode%3Dform_post%26nonce%3D638114104999505444.MDczNGIxNjMtNDhmNS00ODRjLTk1MGItNTkzZTk5NWEwYzRiYjRkOWQyZmYtNGVjMS00ODFkLWFmOTItMTEyYmQwNDNkYzU1%26state%3DCfDJ8HI2B0m15WhGp3yVc3O5P6meCJTffNO4HVoh0b2BXP-AcH5ZEz09PhggPpidoAxtCw4kMeDLioDr0MC1SL35WaLMYy7BQDyYCKbqNwNnMYaLaOsl9RhPRDT6nSt1-7TS1X0a9uecrRqwYnyR2XmKXI-CvMYdLPJoiK5k_moGWmqQ7GotZEWbnoi__O0wTMH5LN2Jwor6t8dur6tCCJ1OH2jIqbSsati-xWN2Qen0EWwbSItTci2JyJo_vNqKjFXlTqs5VEJwxzXVivAH3dc9abgFdJdBldZr82q_-8ICZ59kU6ZoLO1IEObDC-lyMQBS7AY7z5glKjqqWGEBYUKf2iw%26x-client-SKU%3DID_NETSTANDARD2_0%26x-client-ver%3D6.10.0.0")
    .formParam("ReturnUrl", "/connect/authorize/callback?client_id=mvc&redirect_uri=http%3A%2F%2Fhost.docker.internal%3A5100%2Fsignin-oidc&response_type=code%20id_token&scope=openid%20profile%20orders%20basket%20webshoppingagg%20orders.signalrhub&response_mode=form_post&nonce=638114104999505444.MDczNGIxNjMtNDhmNS00ODRjLTk1MGItNTkzZTk5NWEwYzRiYjRkOWQyZmYtNGVjMS00ODFkLWFmOTItMTEyYmQwNDNkYzU1&state=CfDJ8HI2B0m15WhGp3yVc3O5P6meCJTffNO4HVoh0b2BXP-AcH5ZEz09PhggPpidoAxtCw4kMeDLioDr0MC1SL35WaLMYy7BQDyYCKbqNwNnMYaLaOsl9RhPRDT6nSt1-7TS1X0a9uecrRqwYnyR2XmKXI-CvMYdLPJoiK5k_moGWmqQ7GotZEWbnoi__O0wTMH5LN2Jwor6t8dur6tCCJ1OH2jIqbSsati-xWN2Qen0EWwbSItTci2JyJo_vNqKjFXlTqs5VEJwxzXVivAH3dc9abgFdJdBldZr82q_-8ICZ59kU6ZoLO1IEObDC-lyMQBS7AY7z5glKjqqWGEBYUKf2iw&x-client-SKU=ID_NETSTANDARD2_0&x-client-ver=6.10.0.0")
    .formParam("Email", "#{email}")
    .formParam("Password", "#{password}")
    .formParam("RememberMe", "false")
    .resources(http("Authorizing Sign In")
      .post("/signin-oidc")
      /*.formParam("code", "C7oOYpKN3ZkG3-lsJz6qrKWHSF2bq7bs1Dpevr7ZRKE")
      .formParam("id_token", "eyJhbGciOiJSUzI1NiIsImtpZCI6IjZCN0FDQzUyMDMwNUJGREI0RjcyNTJEQUVCMjE3N0NDMDkxRkFBRTEiLCJ0eXAiOiJKV1QiLCJ4NXQiOiJhM3JNVWdNRnY5dFBjbExhNnlGM3pBa2ZxdUUifQ.eyJuYmYiOjE2NzU4MTM3MDgsImV4cCI6MTY3NTgyMDkwOCwiaXNzIjoibnVsbCIsImF1ZCI6Im12YyIsIm5vbmNlIjoiNjM4MTE0MTA0OTk5NTA1NDQ0Lk1EY3pOR0l4TmpNdE5EaG1OUzAwT0RSakxUazFNR0l0TlRrelpUazVOV0V3WXpSaVlqUmtPV1F5Wm1ZdE5HVmpNUzAwT0RGa0xXRm1PVEl0TVRFeVltUXdORE5rWXpVMSIsImlhdCI6MTY3NTgxMzcwOCwiY19oYXNoIjoiN2l4RklXYmJoZmF2TUxTaUM4MDBQUSIsInNfaGFzaCI6Ik03RE1abkNLcGZsZm5LcFZwSjBWS2ciLCJzaWQiOiJfWFpwVlhOZW5COWZaVUI5YWVNM0FnIiwic3ViIjoiMTY5YzIxYjQtY2QzZS00NGUzLWI2MzctOGE3ODlmMzc0MzYyIiwiYXV0aF90aW1lIjoxNjc1ODEzNzA4LCJpZHAiOiJsb2NhbCIsInByZWZlcnJlZF91c2VybmFtZSI6ImRlbW91c2VyQG1pY3Jvc29mdC5jb20iLCJ1bmlxdWVfbmFtZSI6ImRlbW91c2VyQG1pY3Jvc29mdC5jb20iLCJuYW1lIjoiRGVtb1VzZXIiLCJsYXN0X25hbWUiOiJEZW1vTGFzdE5hbWUiLCJjYXJkX251bWJlciI6IjQwMTI4ODg4ODg4ODE4ODEiLCJjYXJkX2hvbGRlciI6IkRlbW9Vc2VyIiwiY2FyZF9zZWN1cml0eV9udW1iZXIiOiI1MzUiLCJjYXJkX2V4cGlyYXRpb24iOiIxMi8yNSIsImFkZHJlc3NfY2l0eSI6IlJlZG1vbmQiLCJhZGRyZXNzX2NvdW50cnkiOiJVLlMuIiwiYWRkcmVzc19zdGF0ZSI6IldBIiwiYWRkcmVzc19zdHJlZXQiOiIxNTcwMyBORSA2MXN0IEN0IiwiYWRkcmVzc196aXBfY29kZSI6Ijk4MDUyIiwiZW1haWwiOiJkZW1vdXNlckBtaWNyb3NvZnQuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwaG9uZV9udW1iZXIiOiIxMjM0NTY3ODkwIiwicGhvbmVfbnVtYmVyX3ZlcmlmaWVkIjpmYWxzZSwiYW1yIjpbInB3ZCJdfQ.ZhnkKbDMVyte0vCcfZUMuIt29Lf4JZkB7QHMEMao7Zex7x7numlxNXupehOIld2Yjd-WZ9v7o8nIc2B_Fk54zvNq7sclC-VAGwaX1UztK54LOhtUh7cC9YYDxgtEb5gXfcia6OfTCH7r3s7WLe8FYG9Q_PPdaHSaPcJPkfNfW85fGq6_KIYGd2x_U6rV9tB7WC7Dv2Fvta8EyEPF8R4ypNRgO28_5icxA8iWQ4yFwKj9GAAjQJ2sxfuRh-uERCrHLTAP1gm-1_LxwDdXmjnWgPVV94Xuq94LWf07dOCb8Dhzd1P2_HdNlOF3kL87yLBE-uZmvjHSG8iRc2JiSu7_0Q")
      .formParam("scope", "openid profile orders basket webshoppingagg orders.signalrhub")
      .formParam("state", "CfDJ8HI2B0m15WhGp3yVc3O5P6meCJTffNO4HVoh0b2BXP-AcH5ZEz09PhggPpidoAxtCw4kMeDLioDr0MC1SL35WaLMYy7BQDyYCKbqNwNnMYaLaOsl9RhPRDT6nSt1-7TS1X0a9uecrRqwYnyR2XmKXI-CvMYdLPJoiK5k_moGWmqQ7GotZEWbnoi__O0wTMH5LN2Jwor6t8dur6tCCJ1OH2jIqbSsati-xWN2Qen0EWwbSItTci2JyJo_vNqKjFXlTqs5VEJwxzXVivAH3dc9abgFdJdBldZr82q_-8ICZ59kU6ZoLO1IEObDC-lyMQBS7AY7z5glKjqqWGEBYUKf2iw")
      .formParam("session_state", "5vSr9Ckw1DiHlLxLyzOth-wb2P7tN7gyEoaJWcpp1iE.nlZcEtbKe-4LIJNe6oL8Rg")*/))
    .pause(5)

  val logout = exec(http("Logout")
    .post("/Account/SignOut"))
    .pause(2)

  val viewOrderDetails = exec(http("View Second Order")
    .get("/Order/Detail?orderId=#{orderId}"))
    .pause(5)
}
