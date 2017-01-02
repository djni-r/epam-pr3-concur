package by.malinouski.uber.generator;

import by.malinouski.uber.manager.Manager;
import by.malinouski.uber.taxi.Taxi;

public class TaxiGenerator {

    public static void generateTaxis(int number) {
        for (int i = 0; i < number; i++) {
            Taxi taxi = new Taxi();
            taxi.setLocation(LocationGenerator.generateLocation());
            Manager.getInstance().addTaxi(taxi);
        }
    }
}
