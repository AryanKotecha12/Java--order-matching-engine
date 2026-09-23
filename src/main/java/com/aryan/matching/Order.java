package com.aryan.matching;

public class Order {

    // Private fields, can only be accesed the getters, Encapsulation ensured...
    private final long orderId;
    private final String symbol;
    private final Side side;
    private final long price;       // in cents, so 150.25 is stored as 15025
    private long quantity;          // not final, it goes down on partial fills
    private final long timestamp;

    // Constructor to init..
    public Order(long orderId, String symbol, Side side, long price, long quantity) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = System.nanoTime();
    }
    // Getters and Setters
    public long getOrderId() { return orderId; }
    public String getSymbol() { return symbol; }
    public Side getSide() { return side; }
    public long getPrice() { return price; }
    public long getQuantity() { return quantity; }
    public long getTimestamp() { return timestamp; }

    public void reduceQuantity(long filled) {
        if(filled<=0){
            throw new IllegalArgumentException("Fill quantity should be positive");
        }
        if(filled>quantity){
            throw new IllegalArgumentException("Cannot fill "+filled+", only "+quantity+" remaining");
        }

        this.quantity -= filled;
    }

    public boolean isFilled(){
        return quantity == 0;
    }
    public String toString() {
        return "Order{id=" + orderId + ", " + symbol + ", " + side
                + ", price=" + price + ", qty=" + quantity + "}";
    }
}
