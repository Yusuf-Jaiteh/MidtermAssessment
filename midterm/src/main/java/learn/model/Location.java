package learn.model;

import java.math.BigDecimal;

public class Location {
    private int id;
    private String address;
    private String city;
    private String postalCode;
    private BigDecimal standardRate;
    private BigDecimal weekendRate;
    private int stateId;
    private int userId;

    public Location(){
    }

    public Location(int id, String address, String city, String postalCode,BigDecimal standardRate, BigDecimal weekendRate, int stateId, int userId){
        this.stateId = stateId;
        this.userId = userId;
        this.id = id;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.standardRate = standardRate;
        this.weekendRate = weekendRate;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }


    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public void setStandardRate(BigDecimal standardRate){
        this.standardRate = standardRate;
    }

    public BigDecimal getStandardRate(){
        return standardRate;
    }

    public void setWeekendRate(BigDecimal weekendRate){
        this.weekendRate = weekendRate;
    }

    public BigDecimal getWeekendRate(){
        return weekendRate;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
    }

    public void setStateId(int stateId){
        this.stateId = stateId;
    }

    public int getStateId(){
        return stateId;
    }
}
