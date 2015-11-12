/*
 * Copyright (c) 2015 HiDensity. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Nonogram solver.
 *
 * Main class of the Nonogram solver.
 *
 * Created by dennis on 11.11.15.
 */
public class Solver {

    /**
     * Calculates the sum over an array of Integers.
     * @param a Array of Integers
     * @return Integer
     */
    private static int sum(Integer[] a) {
        int retVal = 0;

        for (Integer i : a) {
            retVal += i;
        }

        return retVal;
    }

    /**
     * Creates an array of Integers of a given size, with an initialized content.
     * @param size Array's size.
     * @param content Value to assign to each element.
     * @return Array
     */
    private static Integer[] makeArray(int size, int content) {
        Integer[] a = (Integer[]) Array.newInstance(Integer.class, size);
        for (int i = 0; i < size; i++) {
            a[i] = content;
        }
        return a;
    }

    /**
     * Creates all patterns of a row or column, that matches given runs.
     * @param w Integer with size of row or column.
     * @param s Array of integers to create patterns for.
     * @return Array
     */
    private static Integer[][] genRow(int w, Integer[] s) {
        List<Integer[]> list = new ArrayList<>();
        for (int len : s) {
            list.add(Solver.makeArray(len, 1));
        }
        Integer[][] retVal = (Integer[][]) Array.newInstance(Integer[].class, list.size());
        return list.toArray(retVal);
    }

    private static void deduce(Integer[][] hr, Integer[][] vr) {
        int w = hr.length;
        int h = vr.length;
        List<Integer[][]> rows = new ArrayList<>();
        List<Integer[][]> cols = new ArrayList<>();

        for (Integer[] x : hr) {
            rows.add(genRow(w, x));
        }
        return;
    }

    private static void solve(String p, boolean showRuns) {
        Nonogram s = new Nonogram(p);
        if (showRuns) {
            System.out.println("Horizontal runs: " + s.toString(0));
            System.out.println("Vertical runs  : " + s.toString(1));
        }
        deduce(s.get(0), s.get(1));
    }

    private static void solve(String p) {
        solve(p, true);
    }

    public static void main(String[] args) throws Exception {
        // Read problems from file.
        String fn = "assets/nonogram-simple.txt";
        String fc = new String(Files.readAllBytes(Paths.get(fn)));
        for (String p : fc.split("\n\n")) {
            solve(p);
        }
    }
}
