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
import java.util.Arrays;
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
        Integer[][] elements = (Integer[][]) Array.newInstance(Integer[].class, list.size());
        elements = list.toArray(elements);

        // Generate the possible patterns.
        Integer[][] patternTemp = genSeg(elements, w + 1 - Solver.sum(s));
        // The first character of each generated pattern needs to be discarded.
        List<Integer[]> patternList = new ArrayList<>();
        for (Integer[] pattern : patternTemp) {
            patternList.add(Arrays.copyOfRange(pattern, 1, pattern.length));
        }

        return patternList.toArray(elements);
    }

    /**
     * Creates all patterns for a single segment.
     * @param o Original definition of segment.
     * @param sp Integer containing pattern's size.
     * @return Array
     */
    private static Integer[][] genSeg(Integer[][] o, int sp) {
        if (o == null || o.length == 0) {
            try {
                Integer[][] retVal = (Integer[][]) Array.newInstance(Integer[].class, sp + 1);
                //if (sp > 0) {
                    retVal[0] = makeArray(sp, 2);
                //}
                return retVal;
            } catch (Exception e) {
                System.out.println(sp);
                return null;
            }
        } else {
            List<List<Integer>> retVal = new ArrayList<>();
            for (int x = 1; x < sp - o.length + 2; x++) {
                //noinspection ConstantConditions
                for (Integer[] tail : genSeg(Arrays.copyOfRange(o, 1, o.length), sp - x)) {
                    List<Integer> result = new ArrayList<>();
                    result.addAll(Arrays.asList(makeArray(x, 2)));
                    result.addAll(Arrays.asList(o[0]));
                    if (tail != null) {
                        result.addAll(Arrays.asList(tail));
                        retVal.add(result);
                    }

                }
            }

            return Nonogram.nestedListToArray(retVal, Integer.class);
        }
    }

    private static void deduce(Integer[][] hr, Integer[][] vr) {
        int w = hr.length;
        int h = vr.length;
        List<Integer[][]> rows = new ArrayList<>();
        List<Integer[][]> cols = new ArrayList<>();

        for (Integer[] x : hr) {
            rows.add(genRow(w, x));
        }
        for (Integer[] x : vr) {
            cols.add(genRow(h, x));
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
        String fn = "assets/nonogram-simple2.txt";
        String fc = new String(Files.readAllBytes(Paths.get(fn)));
        for (String p : fc.split("\n\n")) {
            solve(p);
        }
    }
}
