/**
 * File Name    : RadixBoy.java
 * Decription   :
 *
 * Revision History
 * ----------------
 *  Date            Author           Version
 *  Mar 18, 2012      manu          First Version
 *
 * Copyright Pat8 Inc.
 **/

package com.pat8.algo.util;

public class RadixBoy {
    static char[] symbols = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
        'I', 'J' };

    public static String toRadix(int num, final int radix) {
        final StringBuffer buf = new StringBuffer();
        for (; num > 0; num /= radix)
            buf.append(symbols[num % radix]);
        return ReverseAString.reverse(buf.toString());
    }

    public static Long fromRadix(final String num, final int radix) {
        final String symb = new String(symbols);
        long val = 0;
        for (int i = 0; i < num.length(); i++)
            val = val * radix + symb.indexOf(num.charAt(i));
        return val;
    }

    public static String toBinary(final int x) {
        return toRadix(x, 2);
    }
}

class RadixMan {
    public String toRadix(final int num, final int radix) {
        return null;
    }
}