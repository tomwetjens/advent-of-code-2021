package day05;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5Part2 {

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

        Stream<Point> points() {
            return isHorizontal() ? pointsHorizontal() : isVertical() ? pointsVertical() : pointsDiagonal();
        }

        private Stream<Point> pointsHorizontal() {
            return IntStream.rangeClosed(Math.min(a.x, b.x), Math.max(a.x, b.x)).mapToObj(x -> new Point(x, a.y));
        }

        private Stream<Point> pointsVertical() {
            return IntStream.rangeClosed(Math.min(a.y, b.y), Math.max(a.y, b.y)).mapToObj(y -> new Point(a.x, y));
        }

        private Stream<Point> pointsDiagonal() {
            var dx = Integer.compare(b.x, a.x);
            var dy = Integer.compare(b.y, a.y);
            var points = Stream.iterate(a, p -> p.x != b.x + dx && p.y != b.y + dy,
                    p -> new Point(p.x + dx, p.y + dy)).collect(Collectors.toList());
            return points.stream();
        }
    }

    public static void main(String[] args) throws Exception {
        var start = Instant.now();

        var input = Files.lines(Paths.get(Day5Part2.class.getResource("input.txt").toURI()))
                .map(Day5Part2::parseLine)
                .collect(Collectors.toList());

        var points = input.stream()
                .flatMap(Line::points)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        printPoints(points, 10, 10);

        var answer = points.values().stream()
                .mapToLong(n -> n)
                .filter(count -> count > 1)
                .count();

        System.out.println(answer);

        var end = Instant.now();
        System.out.println(Duration.between(start, end));
    }

    private static void printPoints(Map<Point, Long> points, int maxX, int maxY) {
        IntStream.rangeClosed(0, maxX)
                .forEach(y -> {
                    IntStream.rangeClosed(0, maxY)
                            .mapToObj(x -> new Point(x, y))
                            .forEach(point -> {
                                var n = points.getOrDefault(point, 0L);
                                System.out.print(n == 0 ? "." : n);
                            });
                    System.out.println();
                });
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
