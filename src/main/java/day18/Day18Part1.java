package day18;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class Day18Part1 {

    public static void main(String[] args) throws Exception {
        try (var lines = Files.lines(Paths.get(Day18Part1.class.getResource("input.txt").toURI()))) {
            System.out.println(sum(lines)
                    .map(SnailfishNumber::magnitude)
                    .get());
        }
    }

    static Optional<SnailfishNumber> sum(Stream<String> lines) {
        return lines.map(SnailfishNumber::parse)
                .reduce(SnailfishNumber::addAndReduce);
    }

}
