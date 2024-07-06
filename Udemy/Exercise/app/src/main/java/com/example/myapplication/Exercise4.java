package com.example.myapplication;

import java.util.Scanner;

public class Exercise4 {
    public static void main(String[] args){
        int cLetter = 0, cSpaces = 0, cNumbers = 0, cChar = 0;

        Scanner scan = new Scanner(System.in);
        String sentence = scan.nextLine();

        for(char c : sentence.toCharArray()){
            if(Character.isSpaceChar(c)) {
                cSpaces++;
            }else if (Character.isLetter(c) ) {
                cLetter++;
            } else if (Character.isDigit(c)) {
                cNumbers++;
            }else {
                cChar++;
            }
        }

        System.out.println("Result = " + cLetter +" "+ cSpaces +" "+ cNumbers + " "+ cChar);
    }
}
