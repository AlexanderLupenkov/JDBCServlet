package com.example.database.models;

public class Employee {

    private int id;
    private int age;
    private String name;
    private int salary;


    public Employee(int id, int age, String name, int salary) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.salary = salary;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Id: " + this.id + "\nAge: " + this.age + "\nName: " + this.name + "\nSalary: " + this.salary;
    }
}
