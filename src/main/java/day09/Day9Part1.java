package day09;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Day9Part1 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day9Part1.class.getResource("input.txt").toURI()))
                .map(line -> line.chars().map(i -> Character.digit(i, 10)).toArray())
                .toArray(int[][]::new);

        var answer = 0;
        for (var y = 0; y < input.length; y++) {
            for (var x = 0; x < input[y].length; x++) {
                if ((y == 0 || input[y - 1][x] > input[y][x])
                        && (y == input.length - 1 || input[y + 1][x] > input[y][x])
                        && (x == 0 || input[y][x - 1] > input[y][x])
                        && (x == input[y].length - 1 || input[y][x + 1] > input[y][x])) {
                    System.out.println("low point at (" + x + "," + y + "): " + input[y][x]);
                    answer += input[y][x] + 1;
                }
            }
        }

        System.out.println(answer);
    }

}
