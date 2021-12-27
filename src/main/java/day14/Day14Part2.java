package day14;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The idea is: I don't need to know the order of the characters, I just need to:
 * 1. how many times a character is in the polymer and;
 * 2. how many times a rule must be applied in the next step.
 * <p>
 * Since each rule inserts a new character between the matching pair of characters, we know that:
 * 1. The total length increases by 1, increment the counter of the character that was added;
 * 2. It creates two new pairs of characters which we should apply matching rules to in the next step.
 */
public class Day14Part2 {

    record Rule(String matchPair, String newPair1, String newPair2, char inserChar) {
        static Rule parse(String str) {
            var insertChar = str.charAt(6);
            return new Rule(str.substring(0, 2),
                    str.charAt(0) + "" + insertChar,
                    insertChar + "" + str.charAt(1),
                    insertChar);
        }
    }

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day14Part2.class.getResource("input.txt").toURI()))
                .collect(Collectors.toList());

        var template = input.get(0);
        System.out.println("Template: " + template);

        var rules = input.stream().skip(2)
                .map(Rule::parse)
                .collect(Collectors.toMap(Rule::matchPair, Function.identity()));

        var counts = template.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        var queue = new HashMap<String, Long>();

        for (var i = 0; i < template.length() - 1; i++) {
            var pair = template.substring(i, i + 2);

            var rule = rules.get(pair);
            if (rule != null) {
                queue.compute(pair, (key, value) -> value != null ? value + 1 : 1);
            }
        }

        for (var step = 1; step <= 40; step++) {
            var snapshot = new HashMap<>(queue);

            for (var rule : rules.values()) {
                var n = snapshot.get(rule.matchPair);
                if (n != null && n > 0) {
                    // insertion
                    counts.compute(rule.inserChar, (k, v) -> v != null ? v + n : n);

                    // add two new pairs to the queue
                    queue.compute(rule.newPair1, (k, v) -> v != null ? v + n : n);
                    queue.compute(rule.newPair2, (k, v) -> v != null ? v + n : n);

                    // rule is now applied n times
                    queue.compute(rule.matchPair, (k, v) -> v - n);
                }
            }
        }

        var min = counts.values().stream().min(Long::compare).orElseThrow();
        var max = counts.values().stream().max(Long::compare).orElseThrow();
        System.out.println(max - min);
    }

}
