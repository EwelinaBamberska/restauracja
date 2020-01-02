package app.data;


import java.sql.Date;

public class Worker {
    private final int id_prac;
    private final String name;
    private final String surname;

    public Worker(int id_prac, String name, String surname, Date date, String if_waiter, String if_cooker, String if_manager){
        this.id_prac = id_prac;
        this.name = name;
        this.surname = surname;
    }
}
