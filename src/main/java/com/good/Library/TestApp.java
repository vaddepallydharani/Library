package com.good.Library;

public class TestApp {

    public static void main(String[] args) {
        String str = "abc\\000u\\";

        System.out.println("final String : "+str.replace("\\000u\\",""));
    }
}
