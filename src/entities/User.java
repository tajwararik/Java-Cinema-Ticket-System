package entities;

import java.util.ArrayList;

public class User {

    private String userName;
    private int userAge;
    private double balance;
    private ArrayList<Ticket> orderHistory;

    public User(String userName, int userAge, double balance) {
        this.userName = userName;
        this.userAge = userAge;
        this.balance = balance;
        this.orderHistory = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public double getBalance() {
        return balance;
    }

    public void addToOrderHistory(Ticket ticket) {
        orderHistory.add(ticket);
    }

    public ArrayList<Ticket> getOrderHistory() {
        return orderHistory;
    }
}