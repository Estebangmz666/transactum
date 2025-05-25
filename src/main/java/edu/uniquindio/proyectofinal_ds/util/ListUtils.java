package edu.uniquindio.proyectofinal_ds.util;

import java.util.ArrayList;

public class ListUtils {
    public static <T> java.util.List<T> toJavaList(edu.uniquindio.proyectofinal_ds.datastructures.List<T> customList) {
        java.util.List<T> javaList = new ArrayList<>();
        for (T element : customList) {
            javaList.add(element);
        }
        return javaList;
    }
}