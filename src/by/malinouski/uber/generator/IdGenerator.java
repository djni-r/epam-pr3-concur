package by.malinouski.uber.generator;

public class IdGenerator {

    private static long taxiId;
    private static long clientId;
    
    static {
        taxiId = 10000;
        clientId = 100;
    }
    
    public IdGenerator() {
    }
    
    public static long generateTaxiId() {
        return taxiId++;
    }
    
    public static long generateClientId() {
        return clientId++;
    }

}
