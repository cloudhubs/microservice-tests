package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

object HeaderModules {

  val httpProtocolTrainTicket = http
    .baseUrl("http://192.168.3.205:32677")
    .inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")

  val mainPageHeader = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
    "Cache-Control" -> "max-age=0",
    "If-Modified-Since" -> "Mon, 29 Aug 2022 07:25:34 GMT",
    "If-None-Match" -> "630c69ee-46c7")

  val apiV1Header = Map(
    "Accept" -> "application/json, text/plain, text/javascript, */*; q=0.01",
    "Content-Type" -> "application/json",
    "Origin" -> "http://192.168.3.205:32677",
    "X-Requested-With" -> "XMLHttpRequest",
    "authorization" -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZHNlX21pY3Jvc2VydmljZSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpZCI6IjRkMmE0NmM3LTcxY2ItNGNmMS1iNWJiLWI2ODQwNmQ5ZGE2ZiIsImlhdCI6MTY3NjQ1NDc0NywiZXhwIjoxNjc2NDU4MzQ3fQ.21_qzgFtYVOc5SNqC_2XDLkXtouFJiP3jti_IuvkYYY")

  val orderListHeader = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
    "Cache-Control" -> "max-age=0",
    "If-Modified-Since" -> "Mon, 29 Aug 2022 07:25:34 GMT",
    "If-None-Match" -> "630c69ee-82d3")

  val searchTripHeader = Map(
    "Accept" -> "application/json, text/javascript, */*; q=0.01",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Content-Type" -> "application/json",
    "Origin" -> "http://192.168.3.205:32677",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
    "X-Requested-With" -> "XMLHttpRequest")

  val selectTripHeader = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",
    "Upgrade-Insecure-Requests" -> "1",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
}
