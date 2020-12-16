package utils;

import base.Population;
import problem.TSPProblem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {

    private final static Random random = new Random();

    /**
     * @param n produce random integers in range [0, n)
     * @return two random integers, in ascending order
     */
    public static int[] getRandomPair(int n) {
        int x = random.nextInt(n);
        int y;
        do {
            y = random.nextInt(n);
        } while (x == y);
        if (x > y) {
            int temp = y;
            y = x;
            x = temp;
        }
        return new int[]{x, y};
    }

}
