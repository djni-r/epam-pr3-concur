/**
 * 
 */
package by.malinouski.uber.taxi.state;


/**
 * @author makarymalinouski
 *
 */
public class RidingTaxiState implements TaxiState {

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public String toString() {
        return "Riding";
    }

}
