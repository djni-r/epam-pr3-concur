package by.malinouski.uber.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.uber.client.Client;
import by.malinouski.uber.exception.NoTaxisAddedException;
import by.malinouski.uber.manager.Manager;

public class ClientActivityGenerator {
    static final Logger LOGGER = LogManager.getLogger(ClientActivityGenerator.class);
    
    public static void generateClientActivity(int number) {
        for (int i = 0; i < number; i++) {
            Client client = new Client();
            client.setLocation(LocationGenerator.generateLocation());
            client.setTargetLocation(LocationGenerator.generateLocation());
            Manager manager = Manager.getInstance();
            try {
                manager.chooseTaxiFor(client);

            } catch (NoTaxisAddedException e) {
                LOGGER.error("Cannot choose taxi. No taxis added.");
            }
   
        }
    }
}
