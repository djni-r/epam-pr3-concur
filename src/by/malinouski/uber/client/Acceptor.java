package by.malinouski.uber.client;

import by.malinouski.uber.manager.Manager;
import by.malinouski.uber.taxi.Taxi;

public class Acceptor {

    
    public Acceptor() {
        // TODO Auto-generated constructor stub
    }
    
    public void accept(Client client, Taxi taxi) {
        ClientState state = new ClientState();
        Manager manager = Manager.getInstance();
        
        state.waiting();
        client.changeState(state);
        
        manager.sendTaxi(client, taxi);
    }

}
