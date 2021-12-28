package day13;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Day13Part1 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day13Part1.class.getResource("input.txt").toURI()))
                .iterator();

        var width = 0;
        var height = 0;
        var paper = new boolean[10000][10000];

        while (input.hasNext()) {
            var line = input.next();

            if (line.isBlank()) {
                break;
            }

            var p = line.split(",", 2);
            var x = Integer.parseInt(p[0]);
            var y = Integer.parseInt(p[1]);

            paper[x][y] = true;
            width = Math.max(width, x + 1);
            height = Math.max(height, y + 1);
        }

//        print(paper, width, height);

//        while (input.hasNext()) {
        var line = input.next();

        System.out.println(line);

        var instruction = line.split("=", 2);
        if (instruction[0].endsWith("y")) {
            var Y = Integer.parseInt(instruction[1]);

            for (var x = 0; x < width; x++) {
                for (var y = Y + 1; y < height; y++) {
                    paper[x][height - y - 1] |= paper[x][y];
                }
            }

            height /= 2;
        } else {
            var X = Integer.parseInt(instruction[1]);

            for (var x = X + 1; x < width; x++) {
                for (var y = 0; y < height; y++) {
                    paper[width - x - 1][y] |= paper[x][y];
                }
            }

            width /= 2;
        }

//            print(paper, width, height);
//        }

        System.out.println(count(paper, width, height));
    }

    static int count(boolean[][] paper, int width, int height) {
        var result = 0;
        for (var x = 0; x < width; x++) {
            for (var y = 0; y < height; y++) {
                if (paper[x][y])
                    result++;
            }
        }
        return result;
    }

    static void print(boolean[][] paper, int width, int height) {
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                System.out.print(paper[x][y] ? '#' : '.');
            }
            System.out.println();
        }
        System.out.println();
    }

}
