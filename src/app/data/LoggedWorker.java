package app.data;


import java.sql.Date;

public class LoggedWorker {
    private final int id_prac;
    private final String name;
    private final String surname;
    private final Date date;
    private final boolean if_waiter;
    private final boolean if_cooker;
    private final boolean if_manager;
    private static LoggedWorker loggedWorker;

    public LoggedWorker(int id_prac, String name, String surname, Date date, String if_waiter, String if_cooker, String if_manager){
        this.id_prac = id_prac;
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.if_waiter = if_waiter.equals("T");
        this.if_cooker = if_cooker.equals("T");
        this.if_manager = if_manager.equals("T");
        loggedWorker = this;
    }

    public static LoggedWorker getInstance(){
        return loggedWorker;
    }
}
