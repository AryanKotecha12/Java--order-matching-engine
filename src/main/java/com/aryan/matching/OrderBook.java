package com.aryan.matching;
//import java.util.PriorityQueue;
import java.util.*;
public class OrderBook {
    private final String symbol;
    // Buyers: highest price first, then earliest time

    private final PriorityQueue<Order> buyOrders=new PriorityQueue<>((a,b) ->{
        if(a.getPrice()!=b.getPrice()){

            return Long.compare(b.getPrice(),a.getPrice()); // higher price first
        }
        return Long.compare(a.getTimestamp(),b.getTimestamp()); // earlier time first
    });

    // Sellers: lower price first, then earliest time
    private final PriorityQueue<Order> sellOrders=new PriorityQueue<>((a,b) ->{
        if(a.getPrice()!=b.getPrice()){

            return Long.compare(a.getPrice(),b.getPrice());   // lower price first
        }
        return Long.compare(a.getTimestamp(),b.getTimestamp()); // earlier time first
    });

    public OrderBook(String symbol) {
        this.symbol=symbol;
    }
    public List<Trade> addOrder (Order order){
        if(order.getSide()==Side.BUY){
            buyOrders.add(order);
        }
        else{
            sellOrders.add(order);
        }
        ordersById.put(order.getOrderId(), order);
        return match();
    }
    public List<Trade> match(){
        List<Trade> trades=new ArrayList<>();
        while(!buyOrders.isEmpty() && !sellOrders.isEmpty()){
            Order bestBuy=buyOrders.peek();
            Order bestSell=sellOrders.peek();

            // Do the top values cross
            if(bestBuy.getPrice()<bestSell.getPrice()){
                break;
            }
            // The first order placed sets the price
            long tradePrice=(bestBuy.getTimestamp()<bestSell.getTimestamp())
                    ? bestBuy.getPrice()
                    : bestSell.getPrice();

            long tradeQty=Math.min(bestSell.getQuantity(),bestBuy.getQuantity());

            // Reduce/update the quantity
            bestSell.reduceQuantity(tradeQty);
            bestBuy.reduceQuantity(tradeQty);

            trades.add(new Trade(bestBuy.getOrderId(),bestSell.getOrderId(),symbol,tradePrice,tradeQty));

            //remove filled orders
            if (bestBuy.isFilled()) {
                buyOrders.poll();
                ordersById.remove(bestBuy.getOrderId());
            }
            if (bestSell.isFilled()) {
                sellOrders.poll();
                ordersById.remove(bestSell.getOrderId());
            }

        }
        return trades;
    }
    private final Map<Long,Order> ordersById=new HashMap<>();
    // To handle order cancellation
    public boolean cancelOrder (long orderId){
        Order order =ordersById.remove(orderId);
        if(order==null){
            return false;
        }
        if(order.getSide()==Side.BUY){
            buyOrders.remove(order);
        }
        else {
            sellOrders.remove(order);
        }
        return true;
    }




    public Order getBestBuy() {
        return buyOrders.peek();
    }

    public Order getBestSell() {
        return sellOrders.peek();
    }



}
