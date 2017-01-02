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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isReady() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isDone() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public String toString() {
        return "Ready";
    }

}
