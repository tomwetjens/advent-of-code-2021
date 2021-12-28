package day10;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10Part1 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day10Part1.class.getResource("input.txt").toURI()))
                .collect(Collectors.toList());

        var answer = input.stream()
                .mapToInt(line -> switch (parse(line)) {
                    case ')' -> 3;
                    case ']' -> 57;
                    case '}' -> 1197;
                    case '>' -> 25137;
                    default -> 0;
                })
                .sum();

        System.out.println(answer);
    }

    static char parse(String line) {
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
                        return c;
                    }
                }
            }
        }

        return 0;
    }

}
