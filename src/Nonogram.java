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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Nonogram definition.
 *
 * Class defining a Nonogram and providing methods to work with.
 *
 * Created by dennis on 11.11.15.
 */
public class Nonogram {

    /**
     * Nonogram's definition as nested List object.
     */
    private List<List<List<Integer>>> nonogram;

    /**
     * Nonogram's representation as array.
     */
    private Integer[][][] nonoArray = null;

    /**
     * Gets an array of a single Nonogram's dimension.
     * @param list List containing Nonogram's dimension.
     * @param type Type of list elements.
     * @param <T> Return type
     * @return Array
     */
    private static <T> T[][] nonoDimensionToArray(List<List<T>> list, Class type) {
        if (list == null) {
            return null;
        }

        List<T[]> temp = new ArrayList<>();
        @SuppressWarnings("unchecked")
        T[] inner = (T[]) Array.newInstance(type, 0);
        for (List<T> sublist : list) {
            temp.add(sublist == null ? null : sublist.toArray(inner));
        }

        @SuppressWarnings("unchecked")
        T[][] outer = (T[][]) Array.newInstance(inner.getClass(), temp.size());
        return temp.toArray(outer);
    }

    /**
     * Creates a new Nonogram instance.
     * @param p String describing the problem.
     */
    public Nonogram(String p) {
        this.nonogram = new ArrayList<>();

        for (String l : p.split("\n")) {
            List<List<Integer>> line = new ArrayList<>();
            for (String w : l.split(" ")) {
                List<Integer> word = new ArrayList<>();
                for (char c : w.toCharArray()) {
                    word.add(c - 'A' + 1);
                }
                line.add(word);
            }
            this.nonogram.add(line);
        }
    }

    /**
     * Converts the Nonogram into an array.
     * @return Array
     */
    public Integer[][][] toArray() {
        if (this.nonoArray == null) {

            List<Integer[][]> temp = new ArrayList<>();

            temp.add(Nonogram.nonoDimensionToArray(this.getList(0), Integer.class));
            temp.add(Nonogram.nonoDimensionToArray(this.getList(1), Integer.class));

            Integer[][][] output = (Integer[][][]) Array.newInstance(temp.get(0).getClass(), 2);

            this.nonoArray = temp.toArray(output);
        }

        return this.nonoArray;
    }

    /**
     * Gets an array, containing a single Nonogram's dimension.
     * @param dimension Integer with dimension's index.
     * @return Array
     */
    public Integer[][] get(int dimension) {
        return Nonogram.nonoDimensionToArray(this.getList(dimension), Integer.class);
    }

    /**
     * Returns a string representation of a Nonogram's dimension.
     * @param dimension Integer with dimension's index.
     * @return String
     */
    public String toString(int dimension) {
        return Arrays.deepToString(this.toArray()[dimension]);
    }

    /**
     * Gets a nested list, containing a single Nonogram's dimension.
     * @param dimension Integer with dimension's index.
     * @return List
     */
    private List<List<Integer>> getList(int dimension) {
        return this.nonogram.get(dimension);
    }

}
