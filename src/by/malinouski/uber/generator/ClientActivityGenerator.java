package by.malinouski.uber.generator;

import by.malinouski.uber.client.Client;
import by.malinouski.uber.client.ClientThread;
import by.malinouski.uber.manager.Manager;

public class ClientActivityGenerator {

    public static void generateClientActivity(int number) {
        for (int i = 0; i < number; i++) {
            Client client = new Client();
            client.setLocation(LocationGenerator.generateLocation());
            client.setTargetLocation(LocationGenerator.generateLocation());
            Manager.getInstance().chooseTaxiFor(client);
//            new ClientThread(client).start();
        }
    }

}
