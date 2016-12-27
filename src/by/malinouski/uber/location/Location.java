package by.malinouski.uber.location;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Location {
    
    private static final int DECIMAL_PLACES = 3;
    private double latitude;
    private double longitude;

    public Location() {
        // TODO Auto-generated constructor stub
    }
    
    public Location(double latitude, double longitude) {
//        BigDecimal bigDec = new BigDecimal(longitude);
        this.longitude = longitude; //bigDec.setScale(DECIMAL_PLACES, RoundingMode.HALF_UP).doubleValue();
        
//        BigDecimal bigDec2 = new BigDecimal(latitude);
        this.latitude = latitude; //bigDec2.setScale(DECIMAL_PLACES, RoundingMode.HALF_UP).doubleValue();
    }

    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } 
        
        if (!(obj.getClass().equals(this.getClass()))) {
            return false;
        }
        
        Location otherLocation = (Location) obj;
        BigDecimal bigDecLat = new BigDecimal(latitude);
        BigDecimal bigDecLon = new BigDecimal(longitude);
        BigDecimal bigDecOtherLat = new BigDecimal(otherLocation.getLatitude());
        BigDecimal bigDecOtherLon = new BigDecimal(otherLocation.getLongitude());

        return bigDecLat.setScale(DECIMAL_PLACES, RoundingMode.HALF_UP).doubleValue() == 
               bigDecOtherLat.setScale(DECIMAL_PLACES, RoundingMode.HALF_UP).doubleValue() 
               && 
               bigDecLon.setScale(DECIMAL_PLACES, RoundingMode.HALF_UP).doubleValue() ==
               bigDecOtherLon.setScale(DECIMAL_PLACES, RoundingMode.HALF_UP).doubleValue();
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("Location(%s, %s)", longitude, latitude);
    }

}
