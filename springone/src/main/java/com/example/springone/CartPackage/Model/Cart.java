package com.example.springone.CartPackage.Model;

public class Cart {

    private int cartID;
    private int consumerID;

    // Constructors
    public Cart() {}

    public Cart(int cartID, int consumerID) {
        this.cartID = cartID;
        this.consumerID = consumerID;
    }

    // Getters and Setters
    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(int consumerID) {
        this.consumerID = consumerID;
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "cartID=" + cartID +
                ", consumerID=" + consumerID +
                '}';
    }
}
