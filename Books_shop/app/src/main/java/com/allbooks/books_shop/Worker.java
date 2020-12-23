package com.allbooks.books_shop;

import java.util.Date;

public class Worker {
    private String name;
    private String login;
    private String password;
    private String surname;
    private int worker_id;
    private int day_salary;
    private int salary;
    private int sales;
    private String email;
    private String id;
    public Worker(){
        this.id="";
        this.name = "";
        this.surname = "";
        this.worker_id = 0;
        this.day_salary = 0;
        this.salary = 0;
        this.email = "";
        this.login="";
        this.password="";
        this.sales=0;
    }

    public Worker(String id,String name, String surname,
                  String login, String password,
                  int worker_id, int day_salary, int salary,int sales,
                  String email) {
        this.id=id;
        this.name = name;
        this.surname = surname;
        this.worker_id = worker_id;
        this.day_salary = day_salary;
        this.salary = salary;
        this.email = email;
        this.login=login;
        this.sales=sales;
        this.password=password;
    }
    public int getSales(){return sales;}
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public int getWorker_id() {
        return worker_id;
    }
    public int getDay_salary() {
        return day_salary;
    }
    public int getSalary() {
        return salary;
    }
    public String getEmail() {
        return email;
    }

    public void setnewsale(int sale){this.sales=sale;}
    public void addsale(){this.sales++;}
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }
    public void setDay_salary(int day_salary) {
        this.day_salary = day_salary;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
