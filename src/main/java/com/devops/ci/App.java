package com.devops.ci;

public class App {
    public static void main(String[] args) throws Exception {

        System.out.println("CI2 Application Running...");

        while(true) {
            System.out.println("App Alive...");
            Thread.sleep(5000);
        }

    }
}