package day03;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Day3Part1 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day3Part1.class.getResource("input.txt").toURI()))
                .collect(Collectors.toList());

        var count = input.size();
        var ones = new int[12];

        for (var line : input) {
            var chars = line.toCharArray();
            for (var i = 0; i < 12; i++) {
                if (chars[i] == '1') {
                    ones[i]++;
                }
            }
        }

        var gammaBits = new char[12];
        var epsilonBits = new char[12];
        for (var i = 0; i < ones.length; i++) {
            gammaBits[i] = ones[i] > count - ones[i] ? '1' : '0';
            epsilonBits[i] = ones[i] < count - ones[i] ? '1' : '0';
        }

        var gamma = Integer.valueOf(new String(gammaBits), 2);
        var epsilon = Integer.valueOf(new String(epsilonBits), 2);

        System.out.println("gamma: " + gamma);
        System.out.println("epsilon: " + epsilon);

        System.out.println("Answer: " + (gamma*epsilon));
    }

}
