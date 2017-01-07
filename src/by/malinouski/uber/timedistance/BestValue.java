package by.malinouski.uber.timedistance;

import by.malinouski.uber.taxi.Taxi;

public class BestValue {

    private Taxi taxi;
    private TimeDistance timeDistance;
    
    public BestValue(Taxi t, TimeDistance td) {
        taxi = t;
        timeDistance = td;
    }
    
    public Taxi getTaxi() {
        return taxi;
    }
    
    public TimeDistance getTimeDistance() {
        return timeDistance;
    }
    
    

}
