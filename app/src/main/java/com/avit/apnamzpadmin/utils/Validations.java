package com.avit.apnamzpadmin.utils;

public class Validations {

    public static boolean isNumber(String s){
        if(s.length() == 0) return false;
        for(int i=0;i<s.length();i++){
            if(!Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
