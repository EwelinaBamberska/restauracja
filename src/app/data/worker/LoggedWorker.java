package app.data.worker;

import java.sql.Date;

public class LoggedWorker extends Worker {
    private static LoggedWorker instance;

    public LoggedWorker(int id_prac, String name, String surname, Date date, String if_waiter, String if_cooker, String if_manager) {
        super(id_prac, name, surname, date, if_waiter, if_cooker, if_manager);
        instance = this;
    }

    public static LoggedWorker getInstance(){
        return instance;
    }
}
