package day05;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5Part1 {

    static final Pattern LINE_PATTERN = Pattern.compile("^(\\d+),(\\d+) -> (\\d+),(\\d+)$");

    record Point(int x, int y) {
    }

    record Line(Point a, Point b) {
        boolean isHorizontal() {
            return a.y == b.y;
        }

        boolean isVertical() {
            return a.x == b.x;
        }
    }

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day5Part1.class.getResource("input.txt").toURI()))
                .map(Day5Part1::parseLine)
                .collect(Collectors.toList());

        var points = input.stream()
                .filter(line -> line.isHorizontal() || line.isVertical())
                .flatMap(line -> line.isHorizontal()
                        ? IntStream.rangeClosed(Math.min(line.a.x, line.b.x), Math.max(line.a.x, line.b.x)).mapToObj(x -> new Point(x, line.a.y))
                        : IntStream.rangeClosed(Math.min(line.a.y, line.b.y), Math.max(line.a.y, line.b.y)).mapToObj(y -> new Point(line.a.x, y)))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        var answer = points.values().stream()
                .mapToLong(n -> n)
                .filter(count -> count > 1)
                .count();

        System.out.println(answer);
    }

    private static Line parseLine(String s) {
        var matcher = LINE_PATTERN.matcher(s);
        if (matcher.find()) {
            return new Line(
                    new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
                    new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)))
            );
        }
        return null;
    }

}
