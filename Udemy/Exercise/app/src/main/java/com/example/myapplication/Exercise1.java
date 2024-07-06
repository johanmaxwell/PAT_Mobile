package com.example.myapplication;

import java.util.Scanner;

public class Exercise1 {
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        System.out.println("Insert first integer :");
        int input1 = scan.nextInt();

        System.out.println("Insert second integer :");
        int input2 = scan.nextInt();

        System.out.println("The division result is =" + division(input1, input2));
        System.out.println("The remainder is =" + mod(input1, input2));
    }

    public static int division(int a, int b){
        return a/b;
    }

    public static int mod(int a, int b){
        return a%b;
    }

}
