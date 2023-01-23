package ru.yandex.praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;


import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest extends CourierBaseTest {


    //курьера возможно создать (проверяем что после логина созданного курьера, возвращается код 200)
    @Test
    @DisplayName("POST /api/v1/courier создаёт курьера")
    public void courierCanBeCreated() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        //Act
        if (CourierClient.createCourier(courier).statusCode() == SC_CREATED) {
            setCourierToDelete(courier);
        }
        Response response = CourierClient.loginCourier(courier);
        //Assert
        response.then().statusCode(SC_OK);

    }

    //нельзя создать двух одинаковых курьеров (проверяем, что после повторного создания курьера возвращается код 409)
    @Test
    @DisplayName("POST /api/v1/courier возвращает код 409 при попытке повтороного создания курьера")
    public void createReturns409WhenCreatedTwice() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        //Act
        if (CourierClient.createCourier(courier).statusCode() == SC_CREATED) {
            setCourierToDelete(courier);
        }
        CourierClient.createCourier(courier).then()
                //Assert
                .statusCode(SC_CONFLICT).and().assertThat().body("message", equalTo("Этот логин уже используется"));
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
        if (CourierClient.createCourier(courier1).statusCode() == SC_CREATED) {
            setCourierToDelete(courier1);
        }
        CourierClient.createCourier(courier2).then()
                //Assert
                .statusCode(SC_CONFLICT).and().assertThat().body("message", equalTo("Этот логин уже используется"));

    }


    //запрос возвращает правильный код ответа
    @Test
    @DisplayName("POST /api/v1/courier возвращает код 201 при успешном создании курьера")
    public void createReturns201WhenSuccessful() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        //Act
        Response response = CourierClient.createCourier(courier);
        if (response.statusCode() == SC_CREATED) {
            setCourierToDelete(courier);
        }

        //Assert
        response.then().statusCode(SC_CREATED);
    }


    //успешный запрос возвращает ok: true
    @Test
    @DisplayName("POST /api/v1/courier возвращает 'ok: true' при успешном создания курьера")
    public void createReturnsOKMessageWhenSuccessful() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        //Act
        Response response = CourierClient.createCourier(courier);
        if (response.statusCode() == SC_CREATED) {
            setCourierToDelete(courier);
        }
        //Assert
         response.then().assertThat().body("ok",equalTo(true));

    }


}
