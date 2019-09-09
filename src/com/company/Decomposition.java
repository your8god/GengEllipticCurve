package com.company;

import javafx.util.Pair;

import java.math.BigInteger;

public class Decomposition
{
    Pair<BigInteger, BigInteger> ab;

    Decomposition(int D, BigInteger p)
    {
        int legSynbol = legendreSymbol(BigInteger.valueOf(D), p);
        if (legSynbol!= -1)
        {

        }
    }

//    private BigInteger NOD(BigInteger a, BigInteger b)0
//    {
//        if (a.compareTo(b) == 0)
//            return a;
//        if (a.compareTo(b) == 1)
//        {
//            BigInteger t = a;
//            a = b;
//            b = t;
//        }
//        return NOD(a, b.subtract(a));
//    }

    private int legendreSymbol(BigInteger a, BigInteger b)
    {
//      if (NOD(a, b).compareTo(BigInteger.ONE) != 0)
//            return 0;

        BigInteger r = BigInteger.ONE;
        if (a.compareTo(BigInteger.ZERO) == -1)
        {
            a = a.multiply(BigInteger.valueOf(-1));
            if (b.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0)
                r = r.multiply(BigInteger.valueOf(-1));
        }

        while(a.compareTo(BigInteger.ZERO) != 0)
        {
            BigInteger t = BigInteger.ZERO;
            while(a.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0)
            {
                t = t.add(BigInteger.ONE);
                a = a.divide(BigInteger.TWO);
            }

            if (t.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) != 0)
                if ((b.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(3)) == 0) ||
                b.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(5)) == 0)
                {
                    r = r.multiply(BigInteger.valueOf(-1));
                }

            if (a.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0 &&
            b.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0)
            {
                r = r.multiply(BigInteger.valueOf(-1));
            }

            BigInteger c = a;
            a = b.mod(c);
            b = c;

            if (a.compareTo(BigInteger.ZERO) == 0)
                return r.intValue();
        }

        return r.intValue();
    }
}
