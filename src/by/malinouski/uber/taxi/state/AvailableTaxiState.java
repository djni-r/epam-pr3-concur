/**
 * 
 */
package by.malinouski.uber.taxi.state;

/**
 * @author makarymalinouski
 *
 */
public class AvailableTaxiState implements TaxiState {

    /**
     * 
     */
    public AvailableTaxiState() {
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override 
    public String toString() {
        return "Available";
    }

}
