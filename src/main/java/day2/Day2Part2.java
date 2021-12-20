package day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Day2Part2 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day2Part2.class.getResource("/day2-input").toURI()))
                .collect(Collectors.toList());

        int depth = 0;
        int horizontal = 0;
        int aim = 0;

        for (var line : input) {
            var parts = line.split(" ", 2);

            var x = Integer.parseInt(parts[1]);

            if (parts[0].equals("forward")) {
                horizontal += x;
                depth += aim * x;
            } else if (parts[0].equals("up")) {
                aim -= x;
            } else if (parts[0].equals("down")) {
                aim += x;
            }
        }

        System.out.println(depth * horizontal);
    }

}
