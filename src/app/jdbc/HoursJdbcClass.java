package app.jdbc;

import app.data.hours.HoursList;
import app.data.hours.HoursPosition;
import app.data.menu.MenuList;
import app.data.menu.MenuPosition;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HoursJdbcClass {
    private static HoursJdbcClass hoursJdbcClass = new HoursJdbcClass();

    public static HoursJdbcClass getInstance() {
        return hoursJdbcClass;
    }


    public void getHoursFromDatabase(String Id) {
        Statement stmt = null;
        String query = "select * from pracownik_na_zmianie where pracownik_id_prac = " + Id;
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                HoursPosition position = new HoursPosition(rs.getInt(1), rs.getString(2), rs.getDouble(4), rs.getInt(3));
                HoursList.getInstance().addHoursPosition(position);
            }
            HoursList.getInstance().setDownloadedData(true);
        } catch (SQLException e) {
            throw new Error("Problem", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
