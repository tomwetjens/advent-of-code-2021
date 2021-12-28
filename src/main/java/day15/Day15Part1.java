package day15;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Day15Part1 {

    record Point(int x, int y) {
    }

    public static void main(String[] args) throws Exception {
        var grid = Files.lines(Paths.get(Day15Part1.class.getResource("input.txt").toURI()))
                .map(line -> line.chars().map(c -> Character.digit(c, 10)).toArray())
                .toArray(int[][]::new);

        var source = new Point(0, 0);
        var target = new Point(grid[grid.length - 1].length - 1, grid.length - 1);

        var path = astar(grid, source, target);

        print(grid, path);

        System.out.println(cost(path, grid));
    }

    static int h(Point point, Point target) {
        return Math.abs(point.x - target.x) + Math.abs(point.y - target.y);
    }

    static int cost(List<Point> path, int[][] grid) {
        return path.stream()
                .mapToInt(point -> grid[point.y][point.x])
                .sum();
    }

    static List<Point> astar(int[][] grid, Point source, Point target) {
        var dist = new HashMap<Point, Integer>();
        dist.put(source, 0);

        var prev = new HashMap<Point, Point>();

        var fScore = new HashMap<Point, Integer>();
        fScore.put(source, h(source, target));

        var q = new PriorityQueue<Point>(Comparator.comparingInt(p -> fScore.getOrDefault(p, Integer.MAX_VALUE)));
        q.add(source);

        while (!q.isEmpty()) {
            var current = q.poll();
            if (current.equals(target)) {
                // found
                return backtrack(source, current, prev);
            }

            q.remove(current);

            var neighbors = Stream.of(
                    new Point(current.x + 1, current.y),
                    new Point(current.x - 1, current.y),
                    new Point(current.x, current.y + 1),
                    new Point(current.x, current.y - 1)
            )
                    .filter(point -> point.y >= 0 && point.y < grid.length
                            && point.x >= 0 && point.x < grid[point.y].length);

            neighbors.forEach(neighbor -> {
                var cost = grid[neighbor.y][neighbor.x];

                var alt = dist.getOrDefault(current, Integer.MAX_VALUE) + cost;

                if (alt < dist.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    prev.put(neighbor, current);
                    dist.put(neighbor, alt);

                    fScore.put(neighbor, alt);
                    q.add(neighbor);
                }
            });
        }

        throw new IllegalStateException("no path from " + source + " to " + target);
    }

    static LinkedList<Point> backtrack(Point source, Point target, Map<Point, Point> prev) {
        var path = new LinkedList<Point>();

        var current = target;
        while (!source.equals(current)) {
            var edge = prev.get(current);
            path.addFirst(current);
            current = edge;
        }

        return path;
    }

    static void print(int[][] grid, List<Point> path) {
        for (var y = 0; y < grid.length; y++) {
            for (var x = 0; x < grid[y].length; x++) {
                if (path.contains(new Point(x, y))) {
                    System.out.print("(" + grid[y][x] + ")");
                } else {
                    System.out.print(" " + grid[y][x] + " ");
                }
            }
            System.out.println();
        }
    }
}
