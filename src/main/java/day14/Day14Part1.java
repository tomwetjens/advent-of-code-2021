package day14;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day14Part1 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day14Part1.class.getResource("input.txt").toURI()))
                .collect(Collectors.toList());

        var template = input.get(0).chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.print("Template: ");
        print(template);

        var insertions = input.stream().skip(2)
                .map(line -> line.split(" -> ", 2))
                .collect(Collectors.toList());

        List<Character> polymer = template;
        for (var n = 1; n <= 10; n++) {
            polymer = step(polymer, insertions);

            System.out.print("After step " + n + ": ");
            print(polymer);
        }

        var freq = polymer.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        var min = freq.values().stream().min(Long::compare).orElseThrow();
        var max = freq.values().stream().max(Long::compare).orElseThrow();

        System.out.println(max - min);
    }

    static List<Character> step(List<Character> input, List<String[]> insertions) {
        var result = new LinkedList<>(input);

        var prev = (char)0;
        var i = 0;
        for (var cur : input) {
            if (prev != 0) {
                for (var rule : insertions) {
                    if (rule[0].charAt(0) == prev & rule[0].charAt(1) == cur) {
                        result.add(i, rule[1].charAt(0));
                        i++;
                        break;
                    }
                }
            }
            i++;
            prev = cur;
        }

        return result;
    }

    static void print(List<Character> cs) {
        for (var c : cs) {
            System.out.print(c);
        }
        System.out.println();
    }

}
