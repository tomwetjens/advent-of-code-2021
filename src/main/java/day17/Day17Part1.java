package day17;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day17Part1 {

    public static void main(String[] args) throws Exception {
        var input = Files.readString(Paths.get(Day17Part1.class.getResource("input.txt").toURI())).trim();

        var targetArea = parseTargetArea(input);

        var trajectory = trajectory(-targetArea.bottomRight.y - 1, targetArea);

        trajectory.stream().max(Integer::compare)
                .ifPresent(System.out::println);
    }

    private static List<Integer> trajectory(int vy0, TargetArea targetArea) {
        var result = new ArrayList<Integer>();

        var y = 0;
        var vy = vy0;

        while (y >= targetArea.bottomRight.y) {
            y += vy;

            result.add(y);

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
    }

    record Point(int x, int y) {
    }

}
