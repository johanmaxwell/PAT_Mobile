package com.example.myapplication;

public class Exercise7 {
    public static void main(String[] args){
        int a[] = {1,2,3,5,7};
        int even=0, odd=0;

        for(int i=0; i<a.length;i++){
            if(a[i] % 2 == 0){
                even++;
            }else{
                odd++;
            }
        }
        System.out.println("Even = "+even+"\nOdd = "+ odd);
    }
}
