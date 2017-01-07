package by.malinouski.uber.runner;

import by.malinouski.uber.generator.ClientActivityGenerator;
import by.malinouski.uber.generator.TaxiGenerator;
import by.malinouski.uber.generator.TimeCountGenerator;

public class UberRunner {

    public static void main(String[] args) {
        
        TaxiGenerator.generateTaxis(2);
        
        Thread timeCount = new TimeCountGenerator();
        timeCount.setDaemon(true);
        timeCount.start();
        
        ClientActivityGenerator.generateClientActivity(4);
        
    }
}
