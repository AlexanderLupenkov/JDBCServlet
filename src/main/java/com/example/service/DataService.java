package com.example.service;

import com.example.database.DatabaseProvider;
import com.example.database.models.Employee;
import com.example.exception.WrongIDException;
import com.example.exception.WrongInputParametersException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

public class DataService {

    private final DatabaseProvider databaseProvider;
    private final Gson gson;

    public DataService() {
        databaseProvider = DatabaseProvider.getInstance();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public String getEmployees() throws IOException {

        List<Employee> employeeList = databaseProvider.getData();

        return gson.toJson(employeeList);
    }

    public void addEmployee(String JSONBody) throws WrongInputParametersException {
        Employee employee = gson.fromJson(JSONBody, Employee.class);
        databaseProvider.pushData(employee);
    }

    public void deleteEmployee(String id) throws WrongIDException {

        int intId = Integer.parseInt(id);

        boolean idExists = databaseProvider.checkId(intId);

        if (idExists) {
            databaseProvider.popeById(intId);
        } else {
            throw new WrongIDException();
        }
    }
}
