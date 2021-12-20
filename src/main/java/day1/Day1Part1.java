package day1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day1Part1 {

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day1Part1.class.getResource("/day1-input").toURI()))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        var count = 0;
        Integer prev = null;
        for (var cur : input) {
            if (prev != null && cur > prev){
                count++;
            }
            prev= cur;
        }

        System.out.println(count);
    }

}
