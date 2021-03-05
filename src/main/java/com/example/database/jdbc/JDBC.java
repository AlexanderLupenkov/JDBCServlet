package com.example.database.jdbc;

import com.example.database.models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.logging.Logger;


public class JDBC {

    private static final Logger LOG = Logger.getLogger(Executor.class.getName());
    public static final String ID = "id";

    private Connection connection = null;
    private Statement statement = null;

    private String USER_NAME;
    private Scanner scan = new Scanner(System.in);

    public JDBC(String DATABASE_URL, String USER_NAME, String PASSWORD, String JDBC_Driver) {
        try {
            connectToDB(DATABASE_URL, USER_NAME, PASSWORD, JDBC_Driver);

            this.USER_NAME = USER_NAME;

        } catch (NullPointerException ex) {
            LOG.info(ex.toString());
        }
    }


    private void connectToDB(String DATABASE_URL, String USER_NAME, String PASSWORD, String JDBC_Driver) throws NullPointerException {
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

    public boolean idExists(int id) {
        boolean existence = false;
        try {
            ResultSet resultSet = this.statement.executeQuery("SELECT * from employee");
            while (resultSet.next()) {
                int sqlID = resultSet.getInt(ID);
                if (sqlID == id) {
                    existence = true;
                    break;
                }
            }
        } catch (SQLException e) {
            LOG.info(e.toString());
        }
        return existence;
    }


    public void createDataBase(String DBName) {
        String request = "CREATE DATABASE " + DBName + ";";
        try {
            this.statement.execute(request);
        } catch (SQLException e) {
            LOG.info(e.toString());
        }
    }


    public void createTable(String tableName, String requirement) {
        String request = "CREATE TABLE " + tableName + " (" + requirement + ");";
        try {
            this.statement.execute(request);
        } catch (SQLException e) {
            LOG.info(e.toString());
        }
    }


    /*
     * This method execute getting data from my table 'Employee'
     *
     * Table: employee
                Columns:
                        id int AI PK
                        age int
                        name varchar(30)
                        salary int
     */

    public List<Employee> getData() {

        List<Employee> employeeList = null;
        try {
            ResultSet resultSet = this.statement.executeQuery("SELECT * from employee");

            employeeList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String name = resultSet.getString("name");
                int salary = resultSet.getInt("salary");
                int age = resultSet.getInt("age");

                employeeList.add(new Employee(id, age, name, salary));
            }
        } catch (SQLException ex) {
            LOG.info(ex.toString());
        }
        return employeeList;
    }

    /*
     * This method performs an update in my table 'Employee'
     * Table: employee
                Columns:
                        id int AI PK
                        age int
                        name varchar(30)
                        salary int
     */

    public void insertField() {
        try {
            String sqlRequest = "INSERT employee (age, name, salary) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);

            LOG.info("Enter employee's age:");
            int age = scan.nextInt();
            LOG.info("Enter employee's name:");
            String name = scan.next();
            LOG.info("Enter employee's salary:");
            int salary = scan.nextInt();

            preparedStatement.setInt(1, age);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, salary);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException | InputMismatchException ex) {
            LOG.info(ex.toString());
        }
    }


    public void push(Employee employee) {
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


    /*
     * This method performs an update in my table 'Employee'
     * Table: employee
                Columns:
                        id int AI PK
                        age int
                        name varchar(30)
                        salary int
     */
    public void update(int key) {
        String sqlRequest;

        LOG.info("Enter employee's ID whose information you want to change.");
        int id = scan.nextInt();

        try {
            if (!idExists(id))
                throw new InputMismatchException("Incorrect ID");

            else {
                switch (key) {
                    case 1: {
                        sqlRequest = "UPDATE employee SET age=? WHERE id=?";

                        LOG.info("Enter a new age:");
                        int age = scan.nextInt();
                        try {
                            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);

                            preparedStatement.setInt(1, age);
                            preparedStatement.setInt(2, id);

                            preparedStatement.executeUpdate();

                            preparedStatement.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    }
                    case 2: {
                        sqlRequest = "UPDATE employee SET name=? WHERE id=?";

                        LOG.info("Enter a new name:");
                        String name = scan.next();
                        try {
                            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);

                            preparedStatement.setString(1, name);
                            preparedStatement.setInt(2, id);

                            preparedStatement.executeUpdate();

                            preparedStatement.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    }
                    case 3:
                        sqlRequest = "UPDATE employee SET salary=? WHERE id=?";

                        LOG.info("Enter a new salary:");
                        int salary = scan.nextInt();
                        try {
                            PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);

                            preparedStatement.setInt(1, salary);
                            preparedStatement.setInt(2, id);
                            preparedStatement.executeUpdate();

                            preparedStatement.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        break;

                    default:
                        LOG.info("Incorrect input value");
                        break;
                }
            }
        } catch (InputMismatchException ex) {
            LOG.info(ex.toString());
        }
    }

    public void dropDataBase(String DBName) {
        String request = "DROP DATABASE " + DBName + ";";
        try {
            this.statement.execute(request);
        } catch (SQLException e) {
            LOG.info(e.toString());
        }
    }

    public void dropTable(String DBName, String tableName) {
        String request = "DROP TABLE " + DBName + "." + tableName + ";";
        try {
            this.statement.execute(request);
        } catch (SQLException e) {
            LOG.info(e.toString());
        }
    }


    /*
     * This method delete a field from my table 'Employee'
     * Table: employee
                Columns:
                        id int AI PK
                        age int
                        name varchar(30)
                        salary int
     */
    public void deleteTableFieldByID(int id) {
        try {
            if (!idExists(id))
                throw new SQLException("Incorrect ID");

            else {
                String sqlRequest = "DELETE FROM employee WHERE id=?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);

                    preparedStatement.setInt(1, id);

                    preparedStatement.executeUpdate();

                    preparedStatement.close();

                } catch (SQLException ex) {
                    LOG.info(ex.toString());
                }
            }

        } catch (SQLException e) {
            LOG.info(e.toString());
        }

    }

    public void close() {
        if (this.connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                LOG.info(ex.toString());
            }
        }

        if (this.statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                LOG.info(ex.toString());
            }
        }
        scan.close();
    }
}