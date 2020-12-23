package com.allbooks.books_shop;

import java.util.Random;

public class Soldbook {
    private String name;
    private String author;
    private String datesale;
    private int cost;
    private int workerid;
    private String surnameworker;
    private int id_sale;

    public Soldbook(String name, String author, String datesale, int cost, int workerid, String surnameworker) {
        this.name = name;
        this.author = author;
        this.datesale = datesale;
        this.cost = cost;
        this.workerid = workerid;
        this.surnameworker = surnameworker;
        int id_sale=new Random().nextInt(100000);
        this.id_sale = id_sale;
    }
    public Soldbook() {
        this.name ="";
        this.author = "";
        this.datesale = "";
        this.cost = 0;
        this.workerid = 0;
        this.surnameworker = "";
        this.id_sale = 0;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDatesale() {
        return datesale;
    }

    public int getCost() {
        return cost;
    }

    public int getWorkerid() {
        return workerid;
    }

    public String getSurnameworker() {
        return surnameworker;
    }

    public int getId_sale() {
        return id_sale;
    }
}
