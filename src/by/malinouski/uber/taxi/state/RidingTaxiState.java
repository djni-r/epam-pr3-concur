/**
 * 
 */
package by.malinouski.uber.taxi.state;


/**
 * @author makarymalinouski
 *
 */
public class RidingTaxiState implements TaxiState {

//    private static final int MINUTES_IN_HOUR = 60;
//    private LocalTime endTime; 
//    /* (non-Javadoc)
//     * @see by.malinouski.uber.taxi.state.TaxiState#howLong()
//     */
//    public BusyTaxiState(int minutesTillEnd) {
//        endTime = LocalTime.now().plusMinutes(minutesTillEnd);
//    }
//    
//    @Override
//    public int minutesTillEnd() {
//        LocalTime now = LocalTime.now();
//        return (endTime.getHour() - now.getHour()) * MINUTES_IN_HOUR + 
//                endTime.getMinute() - now.getMinute();
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
        return "Riding";
    }

}
