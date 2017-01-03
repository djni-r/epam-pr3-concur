package by.malinouski.uber.client;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.uber.manager.Manager;
import by.malinouski.uber.taxi.Taxi;
import by.malinouski.uber.taxi.state.BusyTaxiState;

public class ClientThread extends Thread {

    static final Logger LOGGER = LogManager.getLogger(ClientThread.class);
    private Manager manager;
    private Client client;
    
    public ClientThread(Client client) {
        super(String.valueOf(client.getClientId()));
        manager = Manager.getInstance();
        this.client = client;
    }

    public void run() {
        LOGGER.info(String.format("Client %s thread started. %s", 
                client.getClientId(), client.getLocation()));
        Taxi taxi = manager.chooseTaxiFor(client);
        
        while(!taxi.getTaxiState().isReady()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
        
        LOGGER.info(String.format("Got into taxi %d. Riding", taxi.getTaxiId()));
        taxi.setTaxiState(new BusyTaxiState());
    }
}
