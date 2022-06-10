package vttp2022.project01;

import java.util.*;

public class ShoppingCart {
    
    // members
    //private String cartName; // name of the cart
    private List<String> cartList = new LinkedList<String>(); // established list of items
    private boolean exist = false; // so that i can check if item exist in cartlist

    // constructors
    // use default constructor 


    // methods
    public void list () {
        if (cartList.size() == 0) {
            System.out.println("The cart is empty");
        } 
        else {
            for (int i = 0; i < cartList.size(); i++) {
                System.out.printf("%d. %s\n" , i+1, cartList.get(i));
            }
        }
    }

    public void addItem (String[] strArray) {
        for (int j = 1; j < strArray.length; j++) {
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i).equals(strArray[j])) {
                    System.out.printf("%s already exist in cart\n", cartList.get(i));
                    exist = true;
                } 
            }

            if (!exist) {
                cartList.add(strArray[j]);
                System.out.printf("%s added to the cart\n", strArray[j]);
            }

            exist = false;
        }
    }

    public void deleteItem (String[] strArray) {
        int del = Integer.parseInt(strArray[1]);
        if (del-1 < cartList.size()){
            String removedItem = cartList.get(del-1);
            cartList.remove(del-1);
            System.out.printf("%s removed from cart\n" , removedItem);
        } 
        else {
            System.out.println("Please enter a valid index");
        }
    }

    public void end () {
        System.out.println();
        System.out.println(">>> Thank you for shopping with us <<<");
        System.out.println("The remaining item in the cart: ");
        for (int i = 0; i < cartList.size(); i++) {
            System.out.printf("%d. %s\n" , i+1, cartList.get(i));
        }
    }

    public String writeToFile () {
        String write = "";
        for (int i = 0; i < cartList.size(); i++) {
            write += i+1 + "." + cartList.get(i);
            write += "\n";
        }
        return write;
    }

    public void readFromFile (String readLine) {
        cartList.add(readLine.substring(2));
    }
}