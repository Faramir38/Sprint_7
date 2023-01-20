package ru.yandex.praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import net.datafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.SamokatConst;


import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierCreateParameterizedTest {

    private final String login;
    private final String password;
    private final String firstName;

    public CourierCreateParameterizedTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters(name = "Тестовые данные: login:{0} password:{1} firstName:{2}")
    public static Object[][] getCreateData() {
        Faker faker = new Faker();
        return new Object[][] {
                {"", faker.text().text(4, 6,true, false, true),
                        faker.name().firstName()},
                {null, faker.text().text(4, 6,true, false, true),
                        faker.name().firstName()},
                {faker.text().text(4, 6,true, false, false),
                        "", faker.name().firstName()},
                {faker.text().text(4, 6,true, false, false),
                        null, faker.name().firstName()},
                {faker.text().text(4, 6,true, false, false),
                        faker.text().text(4, 6,true, false, true),
                        ""},
                {faker.text().text(4, 6,true, false, false),
                        faker.text().text(4, 6,true, false, true),
                        null}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = SamokatConst.SAMOKAT_URL;
    }

    //чтобы создать курьера, нужно передать в ручку все обязательные поля,
    // если одного из полей нет, запрос возвращает ошибку
    @Test
    @DisplayName("POST /api/v1/courier возвращает код 400 если нет одного из обязательных полей")
    public void createReturns400WhenHasNoField() {
        //Arrange
        Courier courier = new Courier(login, password, firstName);
        //Act
        CourierClient.create(courier).then()
                //Assert
                .statusCode(400).and().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
