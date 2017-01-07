package by.malinouski.uber.generator;

import java.util.concurrent.TimeUnit;


public class TimeCountGenerator extends Thread {

    
    public void run() {
        int time = 0;
        while (true) {
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
