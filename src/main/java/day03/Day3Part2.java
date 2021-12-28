package day03;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class Day3Part2 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day3Part2.class.getResource("input.txt").toURI()))
                .map(String::toCharArray)
                .collect(Collectors.toList());

        var oxygenGeneratorRating =  rating(input, (ones, zeroes) -> ones >= zeroes);
        System.out.println("oxygenGeneratorRating: " + oxygenGeneratorRating);

        var co2ScrubberRating = rating(input, (ones, zeroes) -> ones < zeroes);
        System.out.println("co2ScrubberRating: " + co2ScrubberRating);

        System.out.println("Answer: " + (oxygenGeneratorRating * co2ScrubberRating));
    }

    private static int rating(List<char[]> input, BiPredicate<Integer, Integer> predicate) {
        var pos = 0;
        var remaining = new ArrayList<>(input);
        while (remaining.size() > 1 && pos < 12) {
            var ones = 0;
            for (var line : remaining) {
                if (line[pos] == '1') {
                    ones++;
                }
            }
            var zeroes = remaining.size() - ones;

            var bit = predicate.test(ones, zeroes) ? '1' : '0';

            var i = pos;
            remaining.removeIf(line -> line[i] != bit);

            pos++;
        }
        return Integer.valueOf(new String(remaining.get(0)), 2);
    }

}
