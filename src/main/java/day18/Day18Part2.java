package day18;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day18Part2 {

    public static void main(String[] args) throws Exception {
        try (var lines = Files.lines(Paths.get(Day18Part2.class.getResource("input.txt").toURI()))) {
            System.out.println(max(lines));
        }
    }

    static int max(Stream<String> lines) {
        var snailfishNumbers = lines.map(SnailfishNumber::parse).toList();

        return snailfishNumbers.stream()
                .flatMapToInt(a -> snailfishNumbers.stream()
                        .filter(b -> !b.equals(a))
                        .flatMapToInt(b -> IntStream.of(
                                a.deepCopy().addAndReduce(b.deepCopy()).magnitude(),
                                b.deepCopy().addAndReduce(a.deepCopy()).magnitude())))
                .max().getAsInt();
    }

}
