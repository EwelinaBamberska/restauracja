package app.data.worker;

import java.util.ArrayList;
import java.util.List;

public class WorkerList {
    private List<Worker> workers = new ArrayList<>();
    private boolean ifDataDownloaded = false;
    private static WorkerList instance;

    public WorkerList(){
        instance = this;
    }

    public static WorkerList getInstance(){
        return instance;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public boolean isIfDataDownloaded() {
        return ifDataDownloaded;
    }

    public void setIfDataDownloaded(boolean ifDataDownloaded) {
        this.ifDataDownloaded = ifDataDownloaded;
    }

    public Worker getWorkerById(int id) {
        for (Worker w:
             workers) {
            if(w.getId_prac() == id)
                return w;
        }
        return null;
    }

    public List<Worker> getWorkerRegex(String regexToFind) {
        List<Worker> regexWorkers = new ArrayList<>();
        for (Worker w:
             workers) {
            if(w.toString().contains(regexToFind))
                regexWorkers.add(w);
        }
        return regexWorkers;
    }

    public void deleteWorker(Worker workerToShow) {
        workers.remove(workerToShow);
    }
}
