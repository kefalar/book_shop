package com.allbooks.books_shop;

import java.util.Date;

public class Request {
    private String name;
    private String autor;
    private String publisher;
    private String date;
    private int worker_id;
    private String info;

    public Request(String name, String autor, String publisher, String date, int worker_id, String info) {
        this.name = name;
        this.autor = autor;
        this.publisher = publisher;
        this.date = date;
        this.worker_id = worker_id;
        this.info=info;
    }
    public Request(){
        this.name = "";
        this.autor = "";
        this.publisher = "";
        this.date ="" ;
        this.worker_id =0;
        this.info="";
    }
    public String getName() {
        return name;
    }
    public String getAutor() {
        return autor;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getDate() {
        return date;
    }
    public int getWorker_id() {
        return worker_id;
    }
    public String getInfo(){return info;}
}
