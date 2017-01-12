/**
 * File Name    : ReverseAString.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Feb 19, 2012      manu          First Version
 *
 * Copyright Aryaka Networks Inc.
 **/

package com.pat8.algo.util;

public class ReverseAString {
    public static void main(final String[] args) {
        final String string = "meepdarrttii";
        final char str[] = string.toCharArray();
        final int cnt = str.length%2 == 0 ? str.length/2: (str.length-1)/2;
        for(int i=0; i< cnt; i++){
            final char t = str[i];
            str[i] = str[str.length-1-i];
            str[str.length-1-i] = t;
        }
        System.out.println(String.valueOf(str)+" and "+reverse(string));
    }
    public static String reverse(final String str){
        if(str.length()<=2){
            return str;
        }
        return str.substring(str.length()-1)+reverse(str.substring(1,str.length()-1))+str.charAt(0);
    }
}
