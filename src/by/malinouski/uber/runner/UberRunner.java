package by.malinouski.uber.runner;

import by.malinouski.uber.client.Acceptor;
import by.malinouski.uber.client.Caller;
import by.malinouski.uber.client.Client;
import by.malinouski.uber.taxi.Taxi;

public class UberRunner {

    public UberRunner() {
        // TODO Auto-generated constructor stub
    }
    
    public static void main(String[] args) {
        new Thread() { 
            public void run() {
                Client client = new Client();
                Taxi taxi = new Caller().askTaxiFor(client);
                new Acceptor().accept(client, taxi);
                
                // TODO continue
            }
            
        }.start();
    }

}
