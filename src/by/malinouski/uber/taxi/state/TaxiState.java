package by.malinouski.uber.taxi.state;

public interface TaxiState {
    
    int minutesTillEnd();

    boolean isAvailable();

    boolean isReady();

    boolean isDone();

}
