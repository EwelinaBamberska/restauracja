package app.data.hours;

import java.util.ArrayList;
import java.util.List;

public class HoursList {
    private static HoursList hoursList = new HoursList();
    private List<HoursPosition> hoursPositionList = new ArrayList<>();
    private boolean downloadedData = false;

    public static HoursList getInstance(){
        return hoursList;
    }

    public void addHoursPosition(HoursPosition position){
        this.hoursPositionList.add(position);
    }
    public void setDownloadedData(boolean downloadedData) {
        this.downloadedData = downloadedData;
    }
    public boolean isDownloadedData() {
        return downloadedData;
    }
    public List<HoursPosition> getMenuPositionList() {
        return hoursPositionList;
    }



}
