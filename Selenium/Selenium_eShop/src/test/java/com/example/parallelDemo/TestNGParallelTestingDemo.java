package com.example.parallelDemo;

import org.testng.annotations.Test;

public class TestNGParallelTestingDemo {

    @Test
    public void test1() {
        System.out.println("Test 1 | " + Thread.currentThread().getId());
    }

    @Test
    public void test2() {
        System.out.println("Test 2 | " + Thread.currentThread().getId());
    }

}
