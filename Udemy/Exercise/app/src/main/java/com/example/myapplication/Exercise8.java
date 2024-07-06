package com.example.myapplication;

import java.util.Scanner;

public class Exercise8 {
    public static void main(String[] args){
        System.out.println("Number of rows =");

        Scanner s = new Scanner(System.in);
        int n = s.nextInt();

        for(int i=1; i<=n; i++){
            for(int j=n-i; j>=0; j--){
                System.out.print(" ");
            }
            for (int k=0; k<i; k++){
                System.out.print(i+" ");
            }
            System.out.print("\n");
        }
    }
}
