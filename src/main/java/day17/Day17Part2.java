package day17;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day17Part2 {

    public static void main(String[] args) throws Exception {
        var input = Files.readString(Paths.get(Day17Part2.class.getResource("input.txt").toURI())).trim();

        var targetArea = parseTargetArea(input);

        var answer = IntStream.rangeClosed(6, targetArea.bottomRight.x)
                .map(vx0 -> (int) IntStream.rangeClosed(targetArea.bottomRight.y, -targetArea.bottomRight.y - 1)
                        .mapToObj(vy0 -> trajectory(vx0, vy0, targetArea))
                        .filter(traj -> traj.stream().anyMatch(targetArea::contains))
                        .count())
                .sum();

        System.out.println(answer);
    }

    private static List<Point> trajectory(int vx0, int vy0, TargetArea targetArea) {
        var result = new ArrayList<Point>();

        var x = 0;
        var y = 0;
        var vx = vx0;
        var vy = vy0;

        while (x <= targetArea.bottomRight.x && y >= targetArea.bottomRight.y) {
            x += vx;
            y += vy;

            result.add(new Point(x, y));

            vx = vx < 0 ? vx + 1 : vx > 0 ? vx - 1 : 0;
            vy -= 1;
        }

        return result;
    }

    static TargetArea parseTargetArea(String input) {
        var str = input.split("[=,]|\\.\\.");
        return new TargetArea(
                new Point(Integer.parseInt(str[1]), Integer.parseInt(str[5])),
                new Point(Integer.parseInt(str[2]), Integer.parseInt(str[4])));
    }

    record TargetArea(Point topLeft, Point bottomRight) {
        boolean contains(Point point) {
            return point.x >= topLeft.x && point.y <= topLeft.y && point.x <= bottomRight.x && point.y >= bottomRight.y;
        }
    }

    record Point(int x, int y) {
    }

}
