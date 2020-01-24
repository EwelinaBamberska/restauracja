package app.data.hours;

import javafx.beans.property.SimpleStringProperty;

public class HoursItemProperty {
    private SimpleStringProperty ID;
    private SimpleStringProperty date;
    private SimpleStringProperty wage;
    private SimpleStringProperty hours;

    public HoursItemProperty(Integer ID, String date, Double wage, int hours) {
        this.ID = new SimpleStringProperty(Integer.toString(ID));
        this.date = new SimpleStringProperty(date);
        this.wage = new SimpleStringProperty(Double.toString(wage));
        this.hours = new SimpleStringProperty(Integer.toString(hours));
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setWage(String wage) {
        this.wage.set(wage);
    }

    public void setHours(String hours) {
        this.hours.set(hours);
    }

    public String getID() {
        return ID.get();
    }

    public SimpleStringProperty IDProperty() {
        return ID;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getWage() {
        return wage.get();
    }

    public SimpleStringProperty wageProperty() {
        return wage;
    }

    public String getHours() {
        return hours.get();
    }

    public SimpleStringProperty hoursProperty() {
        return hours;
    }


}
