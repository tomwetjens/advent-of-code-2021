package day02;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Day2Part1 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day2Part1.class.getResource("input.txt").toURI()))
                .collect(Collectors.toList());

        int depth = 0;
        int horizontal = 0;

        for (var line : input) {
            var parts = line.split(" ", 2);

            if (parts[0].equals("forward")) {
                horizontal += Integer.parseInt(parts[1]);
            } else if (parts[0].equals("up")) {
                depth -= Integer.parseInt(parts[1]);
            } else if (parts[0].equals("down")) {
                depth += Integer.parseInt(parts[1]);
            }
        }

        System.out.println(depth * horizontal);
    }

}
