package com.SpringBootJdk22.SpringBootJdk22.model;

public class CartItem {
    private Product product;
    private int quantity;
    private String name;
    private String address;
    private String phone;

    // Constructors
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(Product product, int quantity, String name, String address, String phone) {
        this.product = product;
        this.quantity = quantity;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Getters and Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
