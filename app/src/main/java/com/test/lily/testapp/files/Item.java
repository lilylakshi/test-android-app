package com.test.lily.testapp.files;

/**
 * Created by Lily on 30/06/2015.
 */
public class Item {

    private String name;
    private Double price;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getName() + "\n" + "(" + getPrice() + ")";
    }
}
