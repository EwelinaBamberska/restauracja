package app.data.worker;


import java.sql.Date;

public class Worker {
    private final int id_prac;
    private final String name;
    private final String surname;
    private final Date date;
    private final boolean if_waiter;
    private final boolean if_cooker;
    private final boolean if_manager;

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
}
