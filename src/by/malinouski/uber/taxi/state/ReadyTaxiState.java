/**
 * 
 */
package by.malinouski.uber.taxi.state;

/**
 * @author makarymalinouski
 *
 */
public class ReadyTaxiState implements TaxiState {

    /* (non-Javadoc)
     * @see by.malinouski.uber.taxi.state.TaxiState#howLong()
     */
    @Override
    public int minutesTillEnd() {
        // TODO Auto-generated method stub
        return 0;
    }

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

}
