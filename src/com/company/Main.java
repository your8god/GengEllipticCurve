package com.company;

import java.math.BigInteger;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        System.out.print("Введите длину характеристики поля l: ");
        int l = in.nextInt();
        System.out.print("Введите параметр безопасности m: ");
        int m = in.nextInt();

        PrimeNumber prime = new PrimeNumber(l);

        System.out.println("Простое число, которое удовлетворяет условию - " + prime.toString(prime));

        Decomposition decomposition = new Decomposition(-1, prime.getPrimeNumber());

    }
}
