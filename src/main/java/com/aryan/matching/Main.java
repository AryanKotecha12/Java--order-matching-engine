package com.aryan.matching;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        OrderBook book = new OrderBook("AAPL");

        System.out.println("--- Two sellers arrive ---");
        print(book.addOrder(new Order(1, "AAPL", Side.SELL, 150, 40)));
        print(book.addOrder(new Order(2, "AAPL", Side.SELL, 152, 50)));
        System.out.println("Best sell: " + book.getBestSell());

        System.out.println("--- Seller 1 cancels ---");
        System.out.println("Cancelled? " + book.cancelOrder(1));
        System.out.println("Best sell: " + book.getBestSell());

        System.out.println("--- Cancel again / cancel unknown ID ---");
        System.out.println("Cancel 1 again? " + book.cancelOrder(1));
        System.out.println("Cancel 99? " + book.cancelOrder(99));

        System.out.println("--- Buyer arrives at 155 for 30 ---");
        print(book.addOrder(new Order(3, "AAPL", Side.BUY, 155, 30)));

        System.out.println("--- Try cancelling the filled buyer ---");
        System.out.println("Cancel 3? " + book.cancelOrder(3));
        System.out.println("Best sell: " + book.getBestSell());
    }

    static void print(List<Trade> trades) {
        if (trades.isEmpty()) {
            System.out.println("No trades");
        } else {
            for (Trade t : trades) {
                System.out.println(t);
            }
        }
    }
}





