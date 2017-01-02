/**
 * 
 */
package by.malinouski.uber.taxi.state;


/**
 * @author makarymalinouski
 *
 */
public class ArrivingTaxiState implements TaxiState {

//    private static LocalTime endTime;
    
//    public ArrivingTaxiState(int minutesTillEnd) {
//        endTime = LocalTime.now().plusMinutes(minutesTillEnd);
//    }
    
//    /* (non-Javadoc)
//     * @see by.malinouski.uber.taxi.state.TaxiState#howLong()
//     */
//    @Override
//    public int minutesTillEnd() {
//        // TODO Auto-generated method stub
//        return endTime.getMinute() - LocalTime.now().getMinute();
//    }

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
    public boolean isDone() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public String toString() {
        return "Arriving";
    }

}
