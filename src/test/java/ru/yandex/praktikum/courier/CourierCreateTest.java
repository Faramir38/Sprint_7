package ru.yandex.praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.SamokatConst;


import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = SamokatConst.SAMOKAT_URL;
    }

    //курьера возможно создать (проверяем что после логина созданного курьера, возвращается код 200)
    @Test
    @DisplayName("POST /api/v1/courier создаёт курьера")
    public void courierCanBeCreated() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        //Act
        CourierClient.create(courier);
        Response response = CourierClient.login(courier);
        //Assert
        response.then().statusCode(200);

        CourierId id = response.body().as(CourierId.class);
        CourierClient.delete(id.getId());
    }

    //нельзя создать двух одинаковых курьеров (проверяем, что после повторного создания курьера возвращается код 409)
    @Test
    @DisplayName("POST /api/v1/courier возвращает код 409 при попытке повтороного создания курьера")
    public void createReturns409WhenCreatedTwice() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        //Act
        CourierClient.create(courier);
        CourierClient.create(courier).then()
                //Assert
                .statusCode(409).and().assertThat().body("message", equalTo("Этот логин уже используется"));

        Response response = CourierClient.login(courier);
        CourierId id = response.body().as(CourierId.class);
        CourierClient.delete(id.getId());
    }

    //если создать пользователя с логином, который уже есть, возвращается ошибка
    @Test
    @DisplayName("POST /api/v1/courier возвращает код 409 при попытке создания курьера с существующим логином")
    public void createReturns409IfLoginRepeats() {
        //Arrange
        Courier courier1 = CourierGenerator.fullRandom();
        Courier courier2 = CourierGenerator.fullRandom();
        courier2.setLogin(courier1.getLogin());
        //Act
        CourierClient.create(courier1);
        CourierClient.create(courier2).then()
                //Assert
                .statusCode(409).and().assertThat().body("message", equalTo("Этот логин уже используется"));

        Response response = CourierClient.login(courier1);
        CourierId id = response.body().as(CourierId.class);
        CourierClient.delete(id.getId());
    }


    //запрос возвращает правильный код ответа
    @Test
    @DisplayName("POST /api/v1/courier возвращает код 201 при успешном создании курьера")
    public void createReturns201WhenSuccessful() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        //Act
        CourierClient.create(courier).then()
               //Assert
                .statusCode(201);

        Response response = CourierClient.login(courier);
        CourierId id = response.body().as(CourierId.class);
        CourierClient.delete(id.getId());
    }


    //успешный запрос возвращает ok: true
    @Test
    @DisplayName("POST /api/v1/courier возвращает 'ok: true' при успешном создания курьера")
    public void createReturnsOKMessageWhenSuccessful() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        //Act
        CourierClient.create(courier).then()
                //Assert
                .assertThat().body("ok",equalTo(true));

        Response response = CourierClient.login(courier);
        CourierId id = response.body().as(CourierId.class);
        CourierClient.delete(id.getId());
    }


}
