package com.example.btl.Class;

public class Bill {
    int id;
    String name;
    String price;
    String total;
    byte[] img;

    public Bill(int id, String name, String price, String total, byte[] img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.total = total;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
