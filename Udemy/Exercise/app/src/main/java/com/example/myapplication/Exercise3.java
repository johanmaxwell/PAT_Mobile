package com.example.myapplication;

import java.util.Scanner;

public class Exercise3 {

    public static void main(String[] args){
        int dec, i=0;
        int bin_num[] = new int[100];

        Scanner scan = new Scanner(System.in);

        System.out.println("Insert a decimal number =");
        dec = scan.nextInt();

        while(dec > 0){
            bin_num[i++] = dec % 2;
            dec /= 2;
        }

        for (int j=i-1; j>=0; j--){
            System.out.print(bin_num[j]);
        }

    }

}
