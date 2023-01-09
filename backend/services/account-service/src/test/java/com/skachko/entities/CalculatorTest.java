package com.skachko.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculatorTest {

    private Calculator calculator;

    @BeforeAll
    public void init(){
        calculator = new Calculator();
    }

    @BeforeEach
    public void beforeTest(){
        assert calculator != null;
        System.out.println(calculator);
    }


    @Test
    public void sumTest(){
        assertNotNull(calculator);
        assertEquals(6, calculator.sum(1,2,3));
        assertThrows(IllegalArgumentException.class, () -> calculator.throwException(), Calculator.THROW_TEXT);
    }
}