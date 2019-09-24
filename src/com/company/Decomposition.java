package com.company;

import java.math.BigInteger;

import static com.company.PrimeNumber.powMod;

public class Decomposition
{
    BigInteger a, b;

    Decomposition(int D, BigInteger p)
    {
        int legSymbol = legendreSymbol(BigInteger.valueOf(D), p);
        if (legSymbol == 1)
        {
            BigInteger x = stepTwo(BigInteger.valueOf(D), p);
            System.out.println(x);
        }
    }

    private BigInteger GCD(BigInteger a, BigInteger b)
    {
        BigInteger g = BigInteger.ONE;
        while (a.mod(BigInteger.TWO).equals(BigInteger.ZERO) &&
                b.mod(BigInteger.TWO).equals(BigInteger.ZERO))
        {
            a = a.divide(BigInteger.TWO);
            b = b.divide(BigInteger.TWO);
            g = g.multiply(BigInteger.TWO);
        }

        BigInteger u = a, v = b;
        while(!u.equals(BigInteger.ZERO))
        {
            while(u.mod(BigInteger.TWO).equals(BigInteger.ZERO))
                u = u.divide(BigInteger.TWO);
            while(v.mod(BigInteger.TWO).equals(BigInteger.ZERO))
                v = v.divide(BigInteger.TWO);
            if (u.compareTo(v) == -1)
                v = v.subtract(u);
            else
                u = u.subtract(v);
        }
        return g.multiply(v);
    }

    public static BigInteger pow(BigInteger a, BigInteger n)
    {
        BigInteger res = BigInteger.ONE;

        while (n.compareTo(BigInteger.ZERO) != 0)
        {
            if (n.mod(BigInteger.TWO).compareTo(BigInteger.ONE) != 0)
            {
                a = a.multiply(a);
                n = n.divide(BigInteger.TWO);
            }
            else
            {
                res = a.multiply(res);
                n = n.subtract(BigInteger.ONE);

            }
        }
        return res;
    }

    private int legendreSymbol(BigInteger a, BigInteger b)
    {
        BigInteger aa = BigInteger.ONE;
        if (a.compareTo(BigInteger.ZERO) == -1)
            aa = a.add(b);

        if (GCD(aa, b).compareTo(BigInteger.ONE) != 0)
            return 0;

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

    private BigInteger stepTwo(BigInteger a, BigInteger p)
    {
        a = a.add(p);
        if (p.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)))
        {
            return powMod(a, (p.add(BigInteger.ONE)).divide(BigInteger.valueOf(4)), p);
        }
        if (p.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(5)))
        {
            if ((powMod(a, (p.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(4)), p)).equals(BigInteger.ONE))
            {
                return powMod(a, (p.add(BigInteger.valueOf(3))).divide(BigInteger.valueOf(8)), p);
            }
            if ((powMod(a, (p.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(4)), p)).equals(p.subtract(BigInteger.ONE)))
            {
                BigInteger aa = a.multiply(BigInteger.TWO);
                a = a.multiply(BigInteger.valueOf(4));
                a = powMod(a, (p.subtract(BigInteger.valueOf(5))).divide(BigInteger.valueOf(8)), p);
                return aa.multiply(a).mod(p);
            }
        }

        BigInteger q = p.subtract(BigInteger.ONE);
        BigInteger n = BigInteger.ZERO;
        while (q.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            q = q.divide(BigInteger.TWO);
            n = n.add(BigInteger.ONE);
        }

        BigInteger r = n, z = BigInteger.ONE;
        for (BigInteger c = BigInteger.TWO; !c.equals(p.subtract(BigInteger.ONE)); c = c.add(BigInteger.ONE))
            if (legendreSymbol(c, p) == -1)
            {
                z = powMod(c, q, p);
                break;
            }

        BigInteger t = powMod(a, (q.subtract(BigInteger.ONE)).divide(BigInteger.TWO), p),
                x = a.multiply(t).mod(p),
                b = x.multiply(t).mod(p);

        while (!x.mod(p).equals(BigInteger.ONE))
        {
            int m = 0;
            while (true)
            {
                if (m == 0)
                    if (b.mod(p).equals(BigInteger.ONE))
                        break;

                else
                    {
                        long deg = 1;
                        for (int i = 0; i < m; i++)
                            deg *= 2;

                        if (powMod(b, BigInteger.valueOf(deg), p).equals(BigInteger.ONE))
                            break;
                    }
                m++;
            }

            t = powMod(z, pow(BigInteger.TWO, r.subtract(BigInteger.valueOf(m - 1))), p);
            z = powMod(t, BigInteger.TWO, p);
            x = x.multiply(t).mod(p);
            b = b.multiply(z).mod(p);
            r = BigInteger.valueOf(m);
        }
        return x;
    }

}
