package com.allbooks.books_shop;

public class Salinf {
    private String date;
    private int idw;
    private String surname;
    private int salary;
    Salinf(){
        date="";
        idw=0;
        surname="";
        salary=0;
    }
    Salinf(String date, int idw, String surname, int salary){
        this.date=date;
        this.idw=idw;
        this.surname=surname;
        this.salary=salary;
    }

    public String getDate() {
        return date;
    }

    public int getIdw() {
        return idw;
    }

    public String getSurname() {
        return surname;
    }

    public int getSalary() {
        return salary;
    }
}
