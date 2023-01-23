package ru.yandex.praktikum.courier;

import io.qameta.allure.Step;
import net.datafaker.Faker;

public class CourierGenerator {
    //создание случайного курьера

    @Step ("Создание случаных данных курьера")
    public static Courier fullRandom() {
        Faker faker = new Faker();

        return new Courier(
                faker.text().text(4, 6,true, false, false),
                faker.text().text(4, 6,true, false, true),
                faker.name().firstName());
    }

}
