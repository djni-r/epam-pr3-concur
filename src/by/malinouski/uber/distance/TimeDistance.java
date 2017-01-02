/**
 * 
 */
package by.malinouski.uber.distance;

/**
 * @author makarymalinouski
 *
 */
public class TimeDistance implements Comparable<TimeDistance> {

    private int minutes;
    private int meters;
    
    public TimeDistance() {
    }
    
    /**
     * Assumes meters and minutes are positive values
     * ATTENTION: there are two similar non-default constructors:
     *      - one with int, int parameters - DISTANCE and TIME 
     *      - one with int, double - DISTANCE and SPEED
     */
    public TimeDistance(int meters, int minutes) {
        this.minutes = minutes;
        this.meters = meters;
    }
    
    public TimeDistance(int meters, double speed) {
        this.meters = meters;
        minutes = (int) (meters/speed);
    }
    
    public int getMinutes() {
        return minutes;
    }
    
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    
    public int getMeters() {
        return meters;
    }
    
    public void setMeters(int meters) {
        this.meters = meters;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * NEED TO IMPLEMENT!!!
     */
    @Override
    public int compareTo(TimeDistance o) {
        // TODO Auto-generated method stub
//        double a = minutes + minutes * meters / 100.0;
        return Integer.valueOf(minutes).compareTo(
               Integer.valueOf(o.getMinutes()));
    }
    
}
