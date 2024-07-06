package com.example.myapplication;

import java.util.Scanner;

public class Exercise2 {

    public static void main (String[] args){

        Scanner scan = new Scanner(System.in);
        System.out.println("Insert Radius =");
        double radius = scan.nextFloat();

        double area = 3.1457 * radius * radius;
        double perimeter = 2*3.1457*radius;

        System.out.println("Area =" + area + "\nPerimeter = "+ perimeter);

    }

}
