package com.example.coroutine.impl;

import com.example.coroutine.ParallelProblems;
import com.example.coroutine.util.TwoDimensionArraySearchUtilsJava;
import org.jetbrains.annotations.NotNull;

public class ParallelProblemsJavaSerialImpl implements ParallelProblems {

    public int findLargestNumberInAnArray(@NotNull final int[][] array) {
        return TwoDimensionArraySearchUtilsJava.findLargestNumberInArrayRange(0, array.length, array);
        //return TwoDimensionArraySearchUtilsKotlin.Companion.findLargestNumberInArrayRange(0, array.length, array); //You can call the kotlin implementation directly from Java
    }
}