package ru.yandex.praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.SamokatConst;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = SamokatConst.SAMOKAT_URL;
    }

//    курьер может авторизоваться;
    @Test
    @DisplayName("POST /api/v1/courier/login возвращает код 200 при успешном логине")
    public void loginReturns200WhenSuccessful() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        CourierClient.create(courier);
        CourierLogin courierLogin = new CourierLogin(courier);
        //Act
        Response response = CourierClient.login(courierLogin);
        //Assert
        response.then().statusCode(200);

        CourierId id = response.body().as(CourierId.class);
        CourierClient.delete(id.getId());

    }

//    успешный запрос возвращает id.
    @Test
    @DisplayName("POST /api/v1/courier/login возвращает 'id' при успешном логине")
    public void loginReturnsIdWhenSuccessful() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        CourierClient.create(courier);
        CourierLogin courierLogin = new CourierLogin(courier);
        //Act
        Response response = CourierClient.login(courierLogin);
        //Assert
        response.then().assertThat().body("id", notNullValue());

        CourierId id = response.body().as(CourierId.class);
        CourierClient.delete(id.getId());
    }

//    если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("POST /api/v1/courier/login возвращает код 404 при неверных логине и пароле" )
    public void loginReturns404WhenLoginAndPassWrong() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        CourierLogin courierLogin = new CourierLogin(courier);
        //Act
        CourierClient.login(courierLogin).then()
                //Assert
                .statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));

    }

//    система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    @DisplayName("POST /api/v1/courier/login возвращает код 404 при неверном логине" )
    public void loginReturns404WhenLoginWrong() {
        //Arrange
        Faker faker = new Faker();
        Courier courier = CourierGenerator.fullRandom();
        CourierClient.create(courier);
        CourierLogin courierLogin = new CourierLogin(courier);
        courierLogin.setLogin(faker.text()
                .text(4, 6,true, false, false));
        //Act
        CourierClient.login(courierLogin)
        //Assert
            .then().statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));

        CourierId id = CourierClient.login(courier).body().as(CourierId.class);
        CourierClient.delete(id.getId());

    }

    @Test
    @DisplayName("POST /api/v1/courier/login возвращает код 404 при неверном пароле" )
    public void loginReturns404WhenPassWrong() {
        //Arrange
        Faker faker = new Faker();
        Courier courier = CourierGenerator.fullRandom();
        CourierClient.create(courier);
        CourierLogin courierLogin = new CourierLogin(courier);
        courierLogin.setPassword(faker.text()
                .text(4, 6,true, false, true));
        //Act
        CourierClient.login(courierLogin)
        //Assert
            .then().statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));

        CourierId id = CourierClient.login(courier).body().as(CourierId.class);
        CourierClient.delete(id.getId());

    }


}
