package com.example.myapplication;

public class Exercise6 {
    public static void main(String[] args) {
        int[] a = {1,2,3,4};
        int[] b = {4,3,2,1};
        int c[] = new int[4];

        for(int i=0; i<a.length; i++){
            c[i] = a[i] * b[i];
        }
        for(int i=0; i<c.length; i++){
            System.out.print(c[i] + "");
        }

    }
}
