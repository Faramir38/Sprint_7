package ru.yandex.praktikum.orders;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
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
        OrdersClient.createOrder(order).then()
                //Assert
                .statusCode(SC_CREATED).and().assertThat().body("track", notNullValue());
    }


}
