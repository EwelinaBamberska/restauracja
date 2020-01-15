package app.data.worker;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class WorkerItemProperty {
    private final SimpleStringProperty id_prac;
    private final SimpleStringProperty name;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty position;

    public WorkerItemProperty(int id_prac, String name, String surname, boolean manager, boolean cook, boolean waiter){
        this.id_prac = new SimpleStringProperty(String.valueOf(id_prac));
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        if(manager)
            this.position = new SimpleStringProperty("Menadżer");
        else if(waiter)
            this.position = new SimpleStringProperty("Kelner");
        else
            this.position = new SimpleStringProperty("Kucharz");
    }

    public String getId_prac() {
        return id_prac.get();
    }

    public SimpleStringProperty id_pracProperty() {
        return id_prac;
    }

    public void setId_prac(String id_prac) {
        this.id_prac.set(id_prac);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }
}
