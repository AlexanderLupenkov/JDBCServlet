package com.example.database;

import com.example.database.jdbc.JDBC;
import com.example.database.jdbc.interfaces.IConstants;
import com.example.database.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class DatabaseProvider implements IConstants {
    private static DatabaseProvider provider = null;
    private static JDBC jdbc;

    private DatabaseProvider() {
        jdbc = new JDBC(DATABASE_URL, USER, PASSWORD, JDBC_Driver);
    }

    public static DatabaseProvider getInstance() {
        if (provider == null) {
            provider = new DatabaseProvider();
        }
        return provider;
    }

    public List<Employee> getData() {
        return jdbc.getData();
    }

    public void pushData(Employee employee) {
        jdbc.push(employee);
    }

    public boolean checkId(int id) {
        return jdbc.idExists(id);
    }

    public void popeById(int id) {
        jdbc.deleteTableFieldByID(id);
    }
}
