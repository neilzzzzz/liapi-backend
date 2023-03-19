package com.example.liapiclintsdk;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;


class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if(n == 0){
            return;
        }
        int sum = 0;
        for(int i =0;i < n;i++){
            int num = in.nextInt();
            sum += num;

        }
        System.out.println(sum);

    }

}
