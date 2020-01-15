package app.data.worker;


import java.sql.Date;

public class Worker {
    private int id_prac;
    private String name;
    private String surname;
    private Date date;
    private boolean if_waiter;
    private boolean if_cooker;
    private boolean if_manager;

    public Worker(int id_prac, String name, String surname, Date date, String if_waiter, String if_cooker, String if_manager){
        this.id_prac = id_prac;
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.if_waiter = if_waiter.equals("T");
        this.if_cooker = if_cooker.equals("T");
        this.if_manager = if_manager.equals("T");
    }

    public int getId_prac() {
        return id_prac;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDate() {
        return date;
    }

    public boolean isIf_waiter() {
        return if_waiter;
    }

    public boolean isIf_cooker() {
        return if_cooker;
    }

    public boolean isIf_manager() {
        return if_manager;
    }

    @Override
    public String toString(){
        return this.name + " " + this.surname;
    }

    public void setId_prac(int id_prac) {
        this.id_prac = id_prac;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setIf_waiter(boolean if_waiter) {
        this.if_waiter = if_waiter;
    }

    public void setIf_cooker(boolean if_cooker) {
        this.if_cooker = if_cooker;
    }

    public void setIf_manager(boolean if_manager) {
        this.if_manager = if_manager;
    }

    public void setManager() {
        this.if_cooker = false;
        this.if_manager = true;
        this.if_waiter = false;
    }

    public void setCook() {
        this.if_waiter = false;
        this.if_manager = false;
        this.if_cooker = true;
    }

    public void setWaiter() {
        this.if_waiter = true;
        this.if_manager = false;
        this.if_cooker = false;
    }
}
