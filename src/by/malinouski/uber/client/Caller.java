/**
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 */
package by.malinouski.uber.client;

import by.malinouski.uber.manager.Manager;
import by.malinouski.uber.taxi.Taxi;

/**
 * This class is an action class, which purpose is to
 * make a call by client for getting a taxi. 
 * 
 * @author makarymalinouski
 */
public class Caller {

    public Caller() {
        // TODO Auto-generated constructor stub
    }
    
    
    /**
     * In this method client notifies the Manager
     * that it wants a taxi and receives one as a return
     * 
     * @param Client that needs a taxi
     * @return Taxi that was chosen by manager to perform the service 
     */
    public Taxi askTaxiFor(Client client) {
        ClientState state = new ClientState();
        state.calling();
        client.changeState(state);
        
        Manager manager = Manager.getInstance();
        return manager.chooseTaxiFor(client);
        
    }

}
