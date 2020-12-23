package com.allbooks.books_shop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Book {
    private String id;
    private String name;
    private String author;
    private String genre;
    private int book_id;
    private int year;
    private String publisher;
    private String info;
    private String hall;
    private int rack;
    private boolean available;
    private boolean sold;
    private String date_sale;
    private int cost;
    //getters
    public Book(){
        this.id="book";
        this.name = "";
        this.author = "";
        this.genre = "";
        this.book_id = 0;
        this.year = 0;
        this.publisher = "";
        this.info = "";
        this.hall = "";
        this.rack = 0;
        this.available = true;
        this.sold = false;
        this.cost = 0;
        this.date_sale="";
    }
    public Book(String id,String name, String author, String genre, int book_id,
                int year, String publisher, String info, String hall, int rack, int cost) {
        this.id=id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.book_id = book_id;
        this.year = year;
        this.publisher = publisher;
        this.info = info;
        this.hall = hall;
        this.rack = rack;
        this.available = true;
        this.sold = false;
        this.cost = cost;
        this.date_sale="" ;
    }

    public String getid() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public String getGenre() {
        return genre;
    }
    public int getBook_id() {
        return book_id;
    }
    public int getYear() {
        return year;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getInfo() {
        return info;
    }
    public String getHall() {
        return hall;
    }
    public int getRack() {
        return rack;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(){
        this.available=false;
    }
    public boolean isSold() {
        return sold;
    }
    public String getDate_sale() {
        return date_sale;
    }
    public int getCost() {
        return cost;
    }
    //end of getters

}
