package com.example.myapplication;

import java.util.Scanner;

public class Exercise5 {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String sentence = scan.nextLine();

        char x[] = sentence.toCharArray();
        int N = x.length;

        String s = "";
        for (int i = N - 1; i >= 0; i--) {
            s += x[i];
        }
        System.out.println(s);
    }
}

