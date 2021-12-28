package day07;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7Part1 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day7Part1.class.getResource("input.txt").toURI()))
                .flatMap(line -> Arrays.stream(line.split(","))
                        .map(Integer::parseInt))
                .sorted()
                .collect(Collectors.toList());

        var median = (int)input.get(input.size() / 2);
        var average = (int)Math.ceil(input.stream().mapToInt(i -> i).average().orElseThrow());

        var min = Math.min(median, average);
        var max = Math.max(median, average);

        var answer = IntStream.rangeClosed(min, max)
                .map(dst -> fuel(input, dst))
                .min()
                .orElseThrow();

        System.out.println(answer);
    }

    static int fuel(List<Integer> input, int dst) {
        return input.stream()
                .mapToInt(pos -> Math.abs(dst-pos))
                .sum();
    }

}
