package com.example.utils;

import com.example.models.Employee;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class EmployeePuller {

    private final static String NAME_PARAMETER = "name";
    private final static String AGE_PARAMETER = "age";
    private final static String SALARY_PARAMETER = "salary";

    public static Employee PullEntity(HttpServletRequest req) {
        Employee employee = null;
        if (req != null
                || !Objects.equals(req.getParameter(NAME_PARAMETER), "")
                || !Objects.equals(req.getParameter(AGE_PARAMETER), "")
                || !Objects.equals(req.getParameter(SALARY_PARAMETER), "")) {
            String newEmployeeName = req.getParameter(NAME_PARAMETER);
            try {
                int newEmployeeAge = Integer.parseInt(req.getParameter(AGE_PARAMETER));
                int newEmployeeSalary = Integer.parseInt(req.getParameter(SALARY_PARAMETER));

                employee = new Employee(newEmployeeName, newEmployeeAge, newEmployeeSalary);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return employee;
    }
}