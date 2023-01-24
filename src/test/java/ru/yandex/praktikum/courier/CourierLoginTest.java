package ru.yandex.praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.Test;


import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest extends CourierBaseTest {

//    курьер может авторизоваться;
    @Test
    @DisplayName("POST /api/v1/courier/login возвращает код 200 при успешном логине")
    public void loginReturns200WhenSuccessful() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        Response response_del = CourierClient.createCourier(courier);
        if (response_del.statusCode() == SC_CREATED) {
            setCourierToDelete(courier);
        }

        CourierLogin courierLogin = new CourierLogin(courier);
        //Act
        Response response = CourierClient.loginCourier(courierLogin);
        //Assert
        response.then().statusCode(SC_OK);


    }

//    успешный запрос возвращает id.
    @Test
    @DisplayName("POST /api/v1/courier/login возвращает 'id' при успешном логине")
    public void loginReturnsIdWhenSuccessful() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        Response response_del = CourierClient.createCourier(courier);
        if (response_del.statusCode() == SC_CREATED) {
            setCourierToDelete(courier);
        }
        CourierLogin courierLogin = new CourierLogin(courier);
        //Act
        Response response = CourierClient.loginCourier(courierLogin);
        //Assert
        response.then().assertThat().body("id", notNullValue());

    }

//    если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("POST /api/v1/courier/login возвращает код 404 при неверных логине и пароле" )
    public void loginReturns404WhenLoginAndPassWrong() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        CourierLogin courierLogin = new CourierLogin(courier);
        //Act
        CourierClient.loginCourier(courierLogin).then()
                //Assert
                .statusCode(SC_NOT_FOUND).and().assertThat().body("message", equalTo("Учетная запись не найдена"));

    }

//    система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    @DisplayName("POST /api/v1/courier/login возвращает код 404 при неверном логине" )
    public void loginReturns404WhenLoginWrong() {
        //Arrange
        Faker faker = new Faker();
        Courier courier = CourierGenerator.fullRandom();
        Response response = CourierClient.createCourier(courier);
        if (response.statusCode() == SC_CREATED) {
            setCourierToDelete(courier);
        }

        CourierLogin courierLogin = new CourierLogin(courier);
        courierLogin.setLogin(faker.text()
                .text(4, 6,true, false, false));
        //Act
        CourierClient.loginCourier(courierLogin)
        //Assert
            .then().statusCode(SC_NOT_FOUND).and().assertThat().body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("POST /api/v1/courier/login возвращает код 404 при неверном пароле" )
    public void loginReturns404WhenPassWrong() {
        //Arrange
        Faker faker = new Faker();
        Courier courier = CourierGenerator.fullRandom();
        Response response = CourierClient.createCourier(courier);
        if (response.statusCode() == SC_CREATED) {
            setCourierToDelete(courier);
        }
        CourierLogin courierLogin = new CourierLogin(courier);
        courierLogin.setPassword(faker.text()
                .text(4, 6,true, false, true));
        //Act
        CourierClient.loginCourier(courierLogin)
        //Assert
            .then().statusCode(SC_NOT_FOUND).and().assertThat().body("message", equalTo("Учетная запись не найдена"));


    }


}
