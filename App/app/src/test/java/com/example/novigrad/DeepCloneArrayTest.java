package com.example.novigrad;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class DeepCloneArrayTest {

    @Test
    public void ShallowCloneTest() {
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = arr1;
        arr1.add(6);
        assertEquals(arr1.get(0), arr2.get(0));
    }

    @Test
    public void DeepCloneTest() {
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = arr1;
        arr1.add(6);
        arr1 = new ArrayList<>();
        arr1.add(7);
        assertNotEquals(arr1.get(0), arr2.get(0));
    }
}
