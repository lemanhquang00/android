package com.example.btl.Class;

public class Order {
    int id;
    private String name;
    private String price;
    int img;




    public Order(String name, String price, int img) {
        this.name = name;
        this.price = price;
        this.img = img;
    }

    public  String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImg() {
        return img;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
