package ru.yandex.praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierLoginParameterizedTest extends CourierBaseTest{
    private final String login;
    private final String password;


    public CourierLoginParameterizedTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters(name = "Тестовые данные: login:{0} password:{1}")
    public static Object[][] getloginData() {
        return new Object[][] {
                {"", "origin"},
                {null, "origin"},
                {"origin", null},
                {"origin", ""}
        };
    }


//    для авторизации нужно передать все обязательные поля;
//    если какого-то поля нет, запрос возвращает ошибку;
    @Test
    @DisplayName("POST /api/v1/courier/login возвращает код 400, если не задан логин или пароль" )
    public void loginReturns400WhenHasNoField() {
        //Arrange
        Courier courier = CourierGenerator.fullRandom();
        CourierLogin courierLogin = new CourierLogin(courier);

        if (login == null) {
            courierLogin.setLogin(login);
        } else if (!login.equals("origin")){
            courierLogin.setLogin(login);
        }

        if (password == null) {
            courierLogin.setPassword(password);
        } else if (!password.equals("origin")){
            courierLogin.setPassword(login);
        }

        Response response = CourierClient.createCourier(courier);
        if (response.statusCode() == SC_CREATED) {
            setCourierToDelete(courier);
        }

        //Act
        CourierClient.loginCourier(courierLogin).then()
                //Assert
                .statusCode(SC_BAD_REQUEST).and().assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }


}
