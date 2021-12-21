package day8;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day8Part1 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day8Part1.class.getResource("/day8-input").toURI()))
                .flatMap(line -> Arrays.stream(line.split(" \\| ")[1].split(" ")))
                .collect(Collectors.toList());

        // len 0 = nothing
        // len 1 = nothing
        // len 2 = 1
        // len 3 = 7
        // len 4 = 4
        // len 5 = 2 or 3 or 5
        // len 6 = 6 or 9
        // len 7 = 8

        System.out.println(input.stream()
                .filter(digit -> digit.length() == 2 || digit.length() == 3 || digit.length() == 4 || digit.length() == 7)
                .count());
    }

}
