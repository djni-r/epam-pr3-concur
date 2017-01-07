/**
 * 
 */
package by.malinouski.uber.taxi.state;


/**
 * @author makarymalinouski
 *
 */
public class ArrivingTaxiState implements TaxiState {

    @Override
    public boolean isAvailable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isReady() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toString() {
        return "Arriving";
    }

}
