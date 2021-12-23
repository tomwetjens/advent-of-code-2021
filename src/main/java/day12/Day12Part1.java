package day12;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12Part1 {

    public static void main(String[] args) throws Exception {
        var edges = Files.lines(Paths.get(Day12Part1.class.getResource("/day12-input").toURI()))
                .map(line -> line.split("-", 2))
                .flatMap(r -> Stream.of(r, new String[]{r[1], r[0]})) // add inverted edges as well
                .collect(Collectors.groupingBy(r -> r[0], Collectors.mapping(r -> r[1], Collectors.toList())));

        var result = paths(edges, "start", "end", Collections.emptyMap())
                .collect(Collectors.toList());

        result.forEach(System.out::println);
        System.out.println(result.size() + " paths");
    }

    static Stream<List<String>> paths(Map<String, List<String>> edges, String from, String end, Map<String, Integer> visits) {
        if (from.equals(end)) {
            return Stream.of(Collections.singletonList(end));
        }
        return edges.get(from).stream()
                .filter(to -> !to.equals("start"))
                .filter(to -> to.equals("end") || canStillVisit(to, visits.getOrDefault(to, 0)))
                .flatMap(to -> paths(edges, to, end, incVisits(visits, to))
                        .map(subpath -> prependToPath(from, subpath)));
    }

    static List<String> prependToPath(String a, List<String> b) {
        var result = new LinkedList<>(b);
        result.push(a);
        return result;
    }

    static Map<String, Integer> incVisits(Map<String, Integer> visits, String node) {
        var result = new HashMap<>(visits);
        result.compute(node, (key, value) -> value != null ? value + 1 : 1);
        return result;
    }

    static boolean canStillVisit(String node, int visits) {
        return Character.isUpperCase(node.charAt(0)) || visits < 1;
    }

}
