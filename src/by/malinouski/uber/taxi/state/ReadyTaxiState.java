/**
 * 
 */
package by.malinouski.uber.taxi.state;

/**
 * @author makarymalinouski
 *
 */
public class ReadyTaxiState implements TaxiState {


    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public String toString() {
        return "Ready";
    }

}
