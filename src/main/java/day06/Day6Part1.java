package day06;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day6Part1 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day6Part1.class.getResource("input.txt").toURI()))
                .flatMap(line -> Arrays.stream(line.split(","))
                        .map(Integer::parseInt))
                .collect(Collectors.toList());

        var state = input.stream()
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));

        System.out.println("Initial state: " + state);

        for (var day = 0; day < 80; day++) {
            multiply(state, day);

            System.out.println("After " + (day + 1) + " days: " + state);
        }

        var total = state.values().stream().mapToLong(n -> n).sum();
        System.out.println(total);
    }

    static void multiply(Map<Integer, Long> state, int day) {
        var count = state.remove(day);
        if (count != null) {
            state.compute(day + 7, (key, value) -> (value != null ? value : 0) + count);
            state.compute(day + 9, (key, value) -> (value != null ? value : 0) + count);
        }
    }

}
