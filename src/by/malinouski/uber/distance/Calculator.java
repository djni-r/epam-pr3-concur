/*
 * Makary Malinouski
 * 
 * This is project 3 for Epam Java web-development course. 
 * The theme of the project is Concurrency.
 * The task is to simulate Uber.
 */
package by.malinouski.uber.distance;

import java.util.Collection;

import by.malinouski.uber.client.Client;
import by.malinouski.uber.taxi.Taxi;

/**
 * @author makarymalinouski
 * This class' purpose is Distance calculation
 */
public class Calculator {

    public Calculator() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Calculates distance between client and taxi
     * @param Taxi
     * @param Client
     * @return TimeDistance
     */
    public TimeDistance calcTimeDistance(Taxi taxi, Client client) {
        throw new RuntimeException("Not yet implemented");
    }
    
    public Taxi calcBestValue(Collection<Taxi> taxis, Client client) {
        TimeDistance bestTimeDist;
        for (Taxi taxi : taxis) { 
            if (taxi.getState().isAvalable()) {
                TimeDistance timeDist = calcTimeDistance(taxi, client);
                // continue
            }
        }
        throw new RuntimeException("Not yet implemented");
    }
}
