package com.ww.javase.MuitiThread;

import javax.swing.*;

/**
 * Created by weiwei on 2018/4/1.
 */
public class GenerateNumber {

    public static void main(String[] args) {
        Number number = new Number();
        Thread oddThread = new Thread(new PrintOdd(number));
        Thread evenThread = new Thread(new PrintEven(number));
        oddThread.start();
        evenThread.start();

    }
}

class Number {
     int i = 1;
     // 两个线程看， 交替执行的一个标志
}

// 奇数
class PrintOdd implements Runnable {

    public Number number = null;

    public PrintOdd(Number number) {
        this.number = number;
    }

    @Override
    public void run() {
        while (number.i < 100) {
            synchronized (number) {
                if(number.i % 2 == 0) {
                    try {
                        number.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("打印奇数： " + number.i);
                    number.i++;

                    number.notify();
                }
            }
        }
    }
}

// 偶数
class PrintEven implements Runnable {

    public Number number = null;

    public PrintEven(Number number) {
        this.number = number;
    }

    @Override
    public void run() {
        while (number.i < 100) {
            synchronized (number) {
                if(number.i % 2 == 0) {
                    System.out.println("打印偶数： " + number.i);
                    number.i++;

                    number.notify();
                } else {
                    try {
                        number.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
