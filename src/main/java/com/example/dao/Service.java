package com.example.dao;

import com.example.database.DatabaseProvider;
import com.example.models.Employee;

import java.util.List;

/*
data access object alpha
 */
public class Service {
    DatabaseProvider databaseProvider;

    public Service() {
        databaseProvider = DatabaseProvider.getInstance();
    }

    public List<Employee> getUsers() {
        return databaseProvider.getEmployees();
    }
}
