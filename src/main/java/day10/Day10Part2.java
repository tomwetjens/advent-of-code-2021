package day10;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10Part2 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day10Part2.class.getResource("/day10-input").toURI()))
                .collect(Collectors.toList());

        var scores = input.stream()
                .mapToLong(Day10Part2::autocomplete)
                .filter(score -> score != -1) // ignore corrupted
                .sorted()
                .toArray();

        var answer = scores[scores.length / 2];

        System.out.println(answer);
    }

    static long autocomplete(String line) {
        var stack = new Stack<Character>();

        for (var c : line.toCharArray()) {
            switch (c) {
                case '(' -> stack.push(')');
                case '[' -> stack.push(']');
                case '{' -> stack.push('}');
                case '<' -> stack.push('>');
                default -> {
                    var expected = stack.pop();
                    if (c != expected) {
                        // ignore corrupted lines
                        return -1;
                    }
                }
            }
        }

        var score = 0L;

        if (!stack.isEmpty()) {
            while (!stack.isEmpty()) {
                var c = stack.pop();

                System.out.print(c);

                score = (score * 5L) + switch (c) {
                    case ')' -> 1L;
                    case ']' -> 2L;
                    case '}' -> 3L;
                    case '>' -> 4L;
                    default -> 0L;
                };
            }
            System.out.println(" - " + score + " total points");
        }

        return score;
    }

}
