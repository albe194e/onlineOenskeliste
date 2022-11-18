package com.example.oenskeliste.Model;

public class Wish {

    private String name, price, link, note;

    public Wish(String name, String price, String link, String note){
        this.name = name;
        this.price = price;
        this.link = link;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }

    public String getNote() {
        return note;
    }
}
