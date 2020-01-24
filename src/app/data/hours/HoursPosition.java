package app.data.hours;

public class HoursPosition {
    private int ID;
    private String date;
    private Double wage;
    private int hours;

    public HoursPosition(int ID, String date, Double wage, int hours) {
        this.ID = ID;
        this.date = date;
        this.wage = wage;
        this.hours = hours;
    }

    public int getID() {
        return ID;
    }

    public String getDate() {
        return date;
    }

    public Double getWage() {
        return wage;
    }

    public int getHours() {
        return hours;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}