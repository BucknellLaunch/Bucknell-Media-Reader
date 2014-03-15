package com.example.bucknellian.data;

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("serial")
public class SortedArrayList<T> extends ArrayList<T> {

    @SuppressWarnings("unchecked")
    public void insertSorted(T value) {
        add(value);
        Comparable<T> cmp = (Comparable<T>) value;
        try{
        for (int i = size()-1; i > 0 && cmp.compareTo(get(i-1)) > 0; i--)
            Collections.swap(this, i, i-1);
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
}
