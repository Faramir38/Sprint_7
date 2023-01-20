package ru.yandex.praktikum.courier;

import net.datafaker.Faker;

public class CourierGenerator {
    //создание случайного курьера

    public static Courier fullRandom() {
        Faker faker = new Faker();

        return new Courier(
                faker.text().text(4, 6,true, false, false),
                faker.text().text(4, 6,true, false, true),
                faker.name().firstName());
    }

}
