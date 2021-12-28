package day11;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class Day11Part1 {

    record Point(int x, int y) {
    }

    public static void main(String[] args) throws Exception {
        var state = Files.lines(Paths.get(Day11Part1.class.getResource("input.txt").toURI()))
                .map(line -> line.chars()
                        .map(c -> Character.digit(c, 10))
                        .map(n -> 10 - n) // invert so it says in which step it will flash
                        .toArray())
                .toArray(int[][]::new);

        System.out.println("Before any steps:");
        print(state, 0);

        var total = 0;
        for (var step = 1; step <= 100; step++) {
            System.out.println("After step " + step + ":");
            total += step(state, step);
            print(state, step);
        }

        System.out.println("Total flashes: " + total);
    }

    static int step(int[][] state, int step) {
        var flashes = new LinkedList<Point>();
        for (var y = 0; y < state.length; y++) {
            for (var x = 0; x < state[y].length; x++) {
                if (state[y][x] == step) {
                    flashes.add(new Point(x, y));
                }
            }
        }

        var count = 0;
        while (!flashes.isEmpty()) {
            var point = flashes.poll();

            flash(state, step, flashes, point);

            count++;
        }

        return count;
    }

    static void flash(int[][] state, int step, LinkedList<Point> flashes, Point point) {
        state[point.y][point.x] = step + 10;

        dec(state, step, point.x - 1, point.y - 1, flashes);
        dec(state, step, point.x, point.y - 1, flashes);
        dec(state, step, point.x + 1, point.y - 1, flashes);

        dec(state, step, point.x - 1, point.y, flashes);
        dec(state, step, point.x + 1, point.y, flashes);

        dec(state, step, point.x - 1, point.y + 1, flashes);
        dec(state, step, point.x, point.y + 1, flashes);
        dec(state, step, point.x + 1, point.y + 1, flashes);
    }

    static void dec(int[][] state, int step, int x, int y, Queue<Point> flashes) {
        if (x >= 0 && y >= 0 && y < state.length && x < state[y].length) {
            if (state[y][x] != step + 10) { // cannot flash more than once per step
                if (--state[y][x] == step) {
                    flashes.add(new Point(x, y));
                }
            }
        }
    }

    static void print(int[][] state, int step) {
        for (var row : state) {
            for (var n : row) {
                System.out.print(10 - (n - step));
            }
            System.out.println();
        }
        System.out.println();
    }

}
