package com.bjjh.MessMan;

import com.bjjh.MessMan.util.PrimaryGenerater;
import org.junit.Test;

public class TestKey {
    @Test
    public void testKeyGen() {

        PrimaryGenerater pg = PrimaryGenerater.getInstance();

        for (int i = 0; i <= 10; i++) {
            System.out.println(pg.generaterNextNumber(String.valueOf(i)));

        }
    }
}
