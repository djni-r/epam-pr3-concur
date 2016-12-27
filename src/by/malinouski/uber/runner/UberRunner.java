package by.malinouski.uber.runner;

import by.malinouski.uber.generator.ClientActivityGenerator;
import by.malinouski.uber.manager.Manager;

public class UberRunner {

    public UberRunner() {
        // TODO Auto-generated constructor stub
    }
    
    public static void main(String[] args) {
        
        Manager manager = Manager.getInstance();
        manager.initTaxis(10);
        
        ClientActivityGenerator.generateClientActivity(8);
    }

}
