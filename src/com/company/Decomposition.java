package com.company;

import java.math.BigInteger;

import static com.company.PrimeNumber.powMod;

public class Decomposition
{
    BigInteger a, b;
    BigInteger ui = null;

    Decomposition(int D, BigInteger p)
    {
        int legSymbol = legendreSymbol(BigInteger.valueOf(D), p);
        if (legSymbol == 1)
        {
            BigInteger x1 = stepTwo(BigInteger.valueOf(D), p),
                    x2 = p.subtract(x1);
            System.out.println(x1 + "  " + x2);

           /* System.out.println(x1 + "\n" + x2);
            boolean f = check(x1, p);
            boolean f1 = check(x2, p);*/

        }
    }

    private boolean check(BigInteger u, BigInteger p)
    {
        BigInteger ui = u, mi = p;
        for (int i = 0; i < 500; i++)
        {
            mi = (pow(ui, BigInteger.TWO).add(BigInteger.ONE)).divide(mi);
            if (ui.mod(mi).compareTo(mi.subtract(ui.mod(mi))) == -1)
                ui = ui.mod(mi);
            else
                ui = mi.subtract(ui.mod(mi));

            if (mi.equals(BigInteger.ONE)) {

                this.ui = ui;
                return true;
            }
        }
        return false;
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
        BigInteger s = BigInteger.ZERO;
        while (q.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            q = q.divide(BigInteger.TWO);
            s = s.add(BigInteger.ONE);
        }

        BigInteger c = BigInteger.ONE;
        for (BigInteger z = BigInteger.TWO; !z.equals(p.subtract(BigInteger.ONE)); z = z.add(BigInteger.ONE))
            if (legendreSymbol(z, p) == -1)
            {
                c = powMod(z, q, p);
                break;
            }

        BigInteger r = powMod(a, (q.add(BigInteger.ONE)).divide(BigInteger.TWO), p),
                t = powMod(a, q, p), M = s;

        while(!t.mod(p).equals(BigInteger.ONE))
        {
            BigInteger S_ = BigInteger.ONE;
            while(!powMod(t, pow(BigInteger.TWO, S_), p).equals(BigInteger.ONE))
                S_ = S_.add(BigInteger.ONE);
            BigInteger w = powMod(c, pow(BigInteger.TWO, s.subtract(S_).subtract(BigInteger.ONE)), p);
            r = r.multiply(w).mod(p);
            t = t.multiply(pow(w, BigInteger.TWO)).mod(p);
            s = S_;
        }

        return r;
    }
}
