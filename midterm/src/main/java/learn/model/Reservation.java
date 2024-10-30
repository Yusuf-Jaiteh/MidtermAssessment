package learn.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal total;
    private int userId;
    private int locationId;


    public Reservation(){
    }

    public Reservation(int id, LocalDate startDate, LocalDate endDate, BigDecimal total, int userId, int locationId){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total = total;
        this.userId = userId;
        this.locationId = locationId;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }

    public LocalDate getEndDate(){
        return endDate;
    }

    public void setTotal(BigDecimal total){
        this.total = total;
    }

    public BigDecimal getTotal(){
        return total;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
    }

    public void setLocationId(int locationId){
        this.locationId = locationId;
    }

    public int getLocationId(){
        return locationId;
    }


}
