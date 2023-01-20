package ru.yandex.praktikum.orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.SamokatConst;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateParameterizedTest {

    private final String color;

    public OrderCreateParameterizedTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные: color:{0}")
    public static Object[][] getCreateData() {
        return new Object[][] {
                {"BLACK"},
                {"GREY"},
                {"BLACK,GREY"},
                {""}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = SamokatConst.SAMOKAT_URL;
    }

//можно указать один из цветов — BLACK или GREY;
//можно указать оба цвета;
//можно совсем не указывать цвет;
    @Test
    @DisplayName("POST /api/v1/orders может принимать любое сочетание цветов")
    public void createAcceptDifferentColor() {
        //Arrange
        Order order = OrderGenerator.random();
        String[] colorArray = color.split(",");
        order.setColor(colorArray);
        //Act
        OrdersClient.create(order).then()
                //Assert
                .statusCode(201).and().assertThat().body("track", notNullValue());
    }


}
