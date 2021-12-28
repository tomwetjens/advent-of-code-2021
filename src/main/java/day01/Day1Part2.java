package day01;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Day1Part2 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day1Part2.class.getResource("input.txt").toURI()))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        var count = 0;
        Integer prev = null;
        for (var i = 0; i < input.size() - 2; i++) {
            var cur = input.get(i) + input.get(i + 1) + input.get(i + 2);

            if (prev != null && cur > prev) {
                count++;
            }

            prev = cur;
        }

        System.out.println(count);
    }

}
