package com.company;

import java.math.BigInteger;
import java.util.Scanner;

public class Main
{
    static BigInteger N = null,
    r = null;


    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        System.out.print("Введите длину характеристики поля l: ");
        int l = in.nextInt();
        System.out.print("Введите параметр безопасности m: ");
        int m = in.nextInt();

        for (int i = 0; i < 50; i++)
        {
            PrimeNumber prime = new PrimeNumber(l);
            Decomposition decomposition = new Decomposition(-1, prime.getPrimeNumber());
            if (!check3(prime, decomposition, l))
                continue;

        }
    }

    static boolean check3(PrimeNumber prime, Decomposition dec, int l)
    {
        BigInteger a = dec.getA(), b = dec.getB(),
                p = prime.getPrimeNumber();

        BigInteger N1 = p.add(BigInteger.ONE).add(a.multiply(BigInteger.valueOf(-2))),
                N2 = p.add(BigInteger.ONE).add(b.multiply(BigInteger.valueOf(-2))),
                N3 = p.add(BigInteger.ONE).add(a.multiply(BigInteger.valueOf(2))),
                N4 = p.add(BigInteger.ONE).add(b.multiply(BigInteger.valueOf(2)));

        if (checkNR(N1, l, prime))
            return true;
        if (checkNR(N2, l, prime))
            return true;
        if (checkNR(N3, l, prime))
            return true;
        if (checkNR(N4, l, prime))
            return true;

        return false;
    }

    static boolean checkNR(BigInteger Ni, int l, PrimeNumber prime)
    {
        if ((Ni.mod(BigInteger.TWO)).equals(BigInteger.ZERO) && prime.checkPrime(Ni.divide(BigInteger.TWO), l))
        {
            N = Ni;
            r = Ni.divide(BigInteger.TWO);
            return true;
        }
        if ((Ni.mod(BigInteger.valueOf(4))).equals(BigInteger.ZERO) && prime.checkPrime(Ni.divide(BigInteger.valueOf(4)), l))
        {
            N = Ni;
            r = Ni.divide(BigInteger.valueOf(4));
            return true;
        }
        return false;
    }
}
