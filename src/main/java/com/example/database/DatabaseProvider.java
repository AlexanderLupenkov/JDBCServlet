package com.example.database;

import com.example.models.User;
import com.example.models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

public class DatabaseProvider {

    private static final Logger LOG = Logger.getLogger(Executor.class.getName());
    private static volatile DatabaseProvider provider;

    private final String COLUMN_USERNAME = "userName";
    private final String COLUMN_USER_PASSWORD = "userPassword";
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_AGE = "age";
    private final String COLUMN_SALARY = "salary";

    private Connection connection = null;
    private Statement statement = null;

    private DatabaseProvider(String DATABASE_URL, String USER_NAME, String PASSWORD, String JDBC_Driver) {
        try {
            Class.forName(JDBC_Driver);
            try {
                this.connection = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASSWORD);
                this.statement = connection.createStatement();
            } catch (SQLException e) {
                LOG.info(e.toString());
            }
        } catch (ClassNotFoundException e) {
            LOG.info(e.toString());
        }
    }

    public static DatabaseProvider getInstance() {
        DatabaseProvider localProvider = provider;
        if (provider == null) {
            synchronized (DatabaseProvider.class) {
                localProvider = provider;
                if (localProvider == null) {
                    String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
                    String DATABASE_URL = "jdbc:mysql://localhost:3306/albase?useSSL=false";
                    String USER = "root";
                    String PASSWORD = "lolkek123";
                    provider = localProvider = new DatabaseProvider(DATABASE_URL, USER, PASSWORD, JDBC_Driver);
                }
            }
        }
        return localProvider;
    }

    public User getUserByName(String name) throws NullPointerException {
        User user = null;
        try {

            String sqlRequest = "SELECT * FROM user WHERE userName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String userName = resultSet.getString(COLUMN_USERNAME);
                String userPassword = resultSet.getString(COLUMN_USER_PASSWORD);

                user = new User(userName, userPassword);
            }
        } catch (SQLException ex) {
            LOG.info(ex.toString());
        }
        return user;
    }

    public List<Employee> getEmployees() {
        List<Employee> employeeList = null;
        try {
            ResultSet resultSet = this.statement.executeQuery("SELECT * from employee");

            employeeList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                int age = resultSet.getInt(COLUMN_AGE);
                int salary = resultSet.getInt(COLUMN_SALARY);

                employeeList.add(new Employee(id, name, age, salary));
            }
        } catch (SQLException ex) {
            LOG.info(ex.toString());
        }
        return employeeList;
    }

    public void pushData(Employee employee) {
        try {
            String sqlRequest = "INSERT employee (age, name, salary) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);

            preparedStatement.setInt(1, employee.getAge());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setInt(3, employee.getSalary());

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException | InputMismatchException e) {
            LOG.warning(e.getMessage());
        }
    }

    public boolean userExistence(String userName) {
        boolean existence = false;
        try {
            ResultSet resultSet = this.statement.executeQuery("SELECT * from user");
            while (resultSet.next()) {
                String sqlName = resultSet.getString(COLUMN_USERNAME);
                if (sqlName.equals(userName)) {
                    existence = true;
                    break;
                }
            }
        } catch (SQLException e) {
            LOG.info(e.toString());
        }
        return existence;
    }
}