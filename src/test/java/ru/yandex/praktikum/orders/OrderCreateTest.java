package ru.yandex.praktikum.orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.SamokatConst;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderCreateTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = SamokatConst.SAMOKAT_URL;
    }

    //тело ответа содержит track.
    @Test
    @DisplayName("POST /api/v1/orders возвращает в теле 'track'")
    public void createContainsTrackWhenSuccessful() {
        //Arrange
        Order order = OrderGenerator.random();
        //Act
        OrdersClient.create(order).then()
        //Assert
        .statusCode(201).and().assertThat().body("track", notNullValue());

    }

}
