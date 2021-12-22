package day9;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Day9Part2 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day9Part2.class.getResource("/day9-input").toURI()))
                .map(line -> line.chars().map(i -> Character.digit(i, 10)).toArray())
                .toArray(int[][]::new);

        var largest = 0;
        var secondLargest = 0;
        var thirdLargest = 0;
        for (var y = 0; y < input.length; y++) {
            for (var x = 0; x < input[y].length; x++) {
                if (input[y][x] != 9) {
                    var size = floodfill(input, x, y);
                    System.out.println("basin: " + size);
                    if (size > largest) {
                        thirdLargest = secondLargest;
                        secondLargest = largest;
                        largest = size;
                    } else if (size > secondLargest) {
                        thirdLargest = secondLargest;
                        secondLargest = size;
                    } else if (size > thirdLargest) {
                        thirdLargest = size;
                    }
                }
            }
        }

        System.out.println("largest: " + largest);
        System.out.println("second largest: " + secondLargest);
        System.out.println("third largest: " + thirdLargest);

        System.out.println("answer: " + largest * secondLargest * thirdLargest);
    }

    record Point(int x, int y) {
    }

    static int floodfill(int[][] input, int startX, int startY) {
        var queue = new LinkedList<Point>();
        queue.add(new Point(startX, startY));

        var size = 0;
        while (!queue.isEmpty()) {
            var point = queue.poll();

            if (input[point.y][point.x] != 9) {
                input[point.y][point.x] = 9;
                size++;
            }

            if (point.y > 0 && input[point.y - 1][point.x] != 9)
                queue.add(new Point(point.x, point.y - 1));
            if (point.x > 0 && input[point.y][point.x - 1] != 9)
                queue.add(new Point(point.x - 1, point.y));
            if (point.y < input.length - 1 && input[point.y + 1][point.x] != 9)
                queue.add(new Point(point.x, point.y + 1));
            if (point.x < input[point.y].length - 1 && input[point.y][point.x + 1] != 9)
                queue.add(new Point(point.x + 1, point.y));
        }

        return size;
    }

}
