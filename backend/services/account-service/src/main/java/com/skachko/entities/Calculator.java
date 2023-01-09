package com.skachko.entities;

import java.util.Arrays;

public class Calculator {

    public static final String THROW_TEXT = "throw Exception";

    public int sum(int...ints){
        return Arrays.stream(ints).sum();
    }

    public double throwException(){
        throw new IllegalArgumentException(THROW_TEXT);
    }
}
