package by.malinouski.uber.generator;

import java.util.concurrent.TimeUnit;


public class TimeCountGenerator {

    public TimeCountGenerator() {
        // TODO Auto-generated constructor stub
    }
    
    public static void generateTimeCount() {
        int time = 0;
        while (Thread.activeCount() != 1) {
            System.out.println(time++);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
