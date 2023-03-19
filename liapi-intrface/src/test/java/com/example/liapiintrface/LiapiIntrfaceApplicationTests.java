package com.example.liapiintrface;

import com.example.client.NameClient;
import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.Scanner;

@SpringBootTest
class LiapiIntrfaceApplicationTests {
    @Resource
    private NameClient nameClient;
//    @Test
//    void contextLoads() {
//        String result1 = nameClient.getNameByGet("lisi");
//        User user = new User();
//         user.setUsername("wangwu");
//        String result2 = nameClient.getNameByPost(user);
//        System.out.println(result1);
//        System.out.println(result2);
//    }


//    @Test
//    void test(){
//        Scanner in = new Scanner(System.in);
//        String firstRow = in.nextLine();//三个整数N A B
//        String[] str = firstRow.split(" ");
//        int N = Integer.parseInt(str[0]);
//        //接下来N行
//        for(int i =0;i < N;i++){
//            String secondRow = in.nextLine();//每行两个数字 x y
//        }
//        //使用二维数组做一个标记
//        //遍历二维数组
//        //输出
//        System.out.println();
//
//
//    }
    @Test
    void test1(){
        //1.判断是不是回文字符串
         Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String result = isValid(s);
        System.out.println(result);
    }
    //是否是回文字符串
    public String isValid(String str){
        int left = 0;
        int len = str.length();
        int right = len-1;
        StringBuffer sb = new StringBuffer();
        for(int i =0;i < len;i++){
            sb.append(str.charAt(i));
        }
        while(left < right){
            int num1  =sb.charAt(left)-'0';
            int num2 = sb.charAt(right)-'0';
            //如果不是回文字串
            if(num1 < num2){
                sb.setCharAt(right,sb.charAt(left));
            }
            else if(num1 > num2){
                sb.setCharAt(left,sb.charAt(right));
            }
            left++;
            right--;
        }
        return sb.toString();

    }





}
