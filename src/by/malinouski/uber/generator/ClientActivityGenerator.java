package by.malinouski.uber.generator;

import by.malinouski.uber.client.Client;

public class ClientActivityGenerator {

    public ClientActivityGenerator() {
        // TODO Auto-generated constructor stub
    }
    
    public static void generateClientActivity(int number) {
        for (int i = 0; i < number; i++) {
            Client client = new Client();
            client.setLocation(LocationGenerator.generateLocation());
            client.setTargetLocation(LocationGenerator.generateLocation());
            client.start();
        }
    }

}
