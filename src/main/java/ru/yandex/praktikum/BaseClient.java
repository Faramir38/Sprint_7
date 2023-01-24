package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseClient {

    protected static RequestSpecification requestSpecification = RestAssured.given()
            .baseUri(SamokatConst.SAMOKAT_URL);

}
