package com.company;

import java.math.BigInteger;
import java.util.Random;
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

        PrimeNumber prime = null;
        for (int i = 0; i < 50; i++)
        {
            prime = new PrimeNumber(l);
            Decomposition decomposition = new Decomposition(-1, prime.getPrimeNumber());
            if (!check3(prime, decomposition, l))
                continue;
            if (check4(prime, m))
                break;
            if (i == 49)
            {
                System.out.println("Не выполнилось для данных l и m. Введите другие данные");
                return;
            }
        }

        while (true)
        {
            if (!check5(prime))
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

    static boolean check4(PrimeNumber prime, int m)
    {
        BigInteger p = prime.getPrimeNumber();

        if (p.equals(r))
            return false;

        for (int i = 1; i <= m; i++)
        {
            if (PrimeNumber.powMod(p, BigInteger.valueOf(i), r).equals(BigInteger.ONE))
                return false;
        }
        return true;
    }

    static BigInteger x0 = null, y0 = null, A = null;

    static boolean check5(PrimeNumber prim)
    {
        BigInteger p = prim.getPrimeNumber();
        Random rand = new Random();
        x0 = new BigInteger(p.bitLength(), rand);
        x0 = x0.mod(p);

        if (x0.equals(BigInteger.ZERO))
            return false;

        y0 = new BigInteger(p.bitLength(), rand);
        y0 = y0.mod(p);

        if (y0.equals(BigInteger.ZERO))
            return false;

        A = ((Decomposition.pow(y0, BigInteger.TWO).subtract(Decomposition.pow(x0, BigInteger.valueOf(3)))).multiply(x0.modInverse(p))).mod(p);
        BigInteger A_ = p.subtract(A),
                N1 = r.multiply(BigInteger.TWO),
                N2 = r.multiply(BigInteger.valueOf(4));

        if (N1.equals(N) && Decomposition.legendreSymbol(A_, p) == -1)
            return true;
        if (N2.equals(N) && Decomposition.legendreSymbol(A_, p) == 1)
            return true;

        return false;
    }
}
