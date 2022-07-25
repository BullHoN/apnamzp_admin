package com.avit.apnamzpadmin.utils;

public class PrettyStrings {

    public static String getPriceInRupees(String value){
        return "₹" + value;
    }

    public static String getPriceInRupees(int value){
        return "₹" + value;
    }

}
