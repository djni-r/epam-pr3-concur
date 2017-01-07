package by.malinouski.uber.generator;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TimeCountGenerator extends Thread {
    
    static final Logger LOGGER = LogManager.getLogger(TimeCountGenerator.class);
    
    public void run() {
        int time = 0;
        while (true) {
            System.out.println(time++ + "...");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

}
