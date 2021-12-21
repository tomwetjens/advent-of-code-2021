package day8;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Day8Part2 {

    public static void main(String[] args) throws Exception {
        var answer = Files.lines(Paths.get(Day8Part2.class.getResource("/day8-input").toURI()))
                .map(line -> line.split(" \\| "))
                .mapToInt(parts -> decode(parts[1], deduce(parts[0])))
                .sum();

        System.out.println(answer);
    }

    private static int decode(String str, Map<String, Integer> mapping) {
        return Integer.parseInt(Arrays.stream(str.split(" "))
                .map(Day8Part2::normalize)
                .map(signal -> mapping.get(signal).toString())
                .reduce("", String::concat));
    }

    private static String normalize(String str) {
        var letters = str.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }

    static Map<String, Integer> deduce(String str) {
        var mapping = new HashMap<String, Integer>();

        var signals = Arrays.stream(str.split(" "))
                .map(Day8Part2::normalize)
                .collect(Collectors.groupingBy(String::length));

        // len 0 = nothing
        // len 1 = nothing
        // len 2 = 1
        // len 3 = 7
        // len 4 = 4
        // len 7 = 8
        var one = signals.get(2).get(0);
        var four = signals.get(4).get(0);
        var seven = signals.get(3).get(0);
        mapping.put(one, 1);
        mapping.put(seven, 7);
        mapping.put(four, 4);
        mapping.put(signals.get(7).get(0), 8);

        // len 6 = 0 or 6 or 9
        //       if not contains signal for "1" -> 6
        //       else if contains signal for "4" -> 9
        //       else -> 0
        signals.get(6).forEach(signal -> {
            if (!containsAll(signal, one.toCharArray())) {
                mapping.put(signal, 6);
            } else if (containsAll(signal, four.toCharArray())) {
                mapping.put(signal, 9);
            } else {
                mapping.put(signal, 0);
            }
        });

        // len 5 = 2 or 3 or 5
        //       if contains signal for "1" -> 3
        //       else if all 5 letters are also in the signal for "6" -> 5
        //       else -> 2
        var six = mapping.entrySet().stream().filter(entry -> entry.getValue() == 6).findFirst().orElseThrow().getKey();
        signals.get(5).forEach(signal -> {
            if (containsAll(six, signal.toCharArray())) {
                mapping.put(signal, 5);
            } else if (containsAll(signal, one.toCharArray())) {
                mapping.put(signal, 3);
            } else {
                mapping.put(signal, 2);
            }
        });

        return mapping;
    }

    private static boolean containsAll(String str, char[] chars) {
        for (var c : chars) {
            if (!str.contains("" + c)) {
                return false;
            }
        }
        return true;
    }

}
