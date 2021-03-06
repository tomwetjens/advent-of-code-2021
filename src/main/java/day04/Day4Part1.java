package day04;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day4Part1 {

    private static final int MARKED = -1;

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day4Part1.class.getResource("input.txt").toURI()))
                .collect(Collectors.toList());

        var numbers = parseNumbers(input);
        var bingoCards = parseBingoCards(input);

        int[][] winner = null;
        int drawnNumber = 0;

        while (winner == null) {
            drawnNumber = numbers.poll();

            for (var bingoCard : bingoCards) {
                for (var row : bingoCard) {
                    for (var columnIndex = 0; columnIndex < row.length; columnIndex++) {
                        if (row[columnIndex] == drawnNumber) {
                            row[columnIndex] = MARKED;
                        }
                    }

                    if (isWholeRowMarked(row)) {
                        winner = bingoCard;
                        break;
                    }
                }

                for (var columnIndex = 0; columnIndex < 5; columnIndex++) {
                    if (isWholeColumnMarked(bingoCard, columnIndex)) {
                        winner = bingoCard;
                        break;
                    }
                }
            }
        }

        var score = score(winner, drawnNumber);

        System.out.println(score);
    }

    private static boolean isWholeColumnMarked(int[][] bingoCard, int columnIndex) {
        for (var row : bingoCard) {
            if (row[columnIndex] != MARKED) {
                return false;
            }
        }
        return true;
    }

    private static boolean isWholeRowMarked(int[] row) {
        for (var i = 0; i < row.length; i++) {
            if (row[i] != MARKED) {
                return false;
            }
        }
        return true;
    }

    private static LinkedList<Integer> parseNumbers(List<String> input) {
        return Arrays.stream(input.get(0).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static List<int[][]> parseBingoCards(List<String> input) {
        return IntStream.range(0, (input.size() - 1) / 6)
                .mapToObj(i -> parseBingoCard(input.subList(i * 6 + 2, i * 6 + 7).stream()))
                .collect(Collectors.toList());
    }

    private static int[][] parseBingoCard(Stream<String> lines) {
        return lines
                .limit(5)
                .map(line -> Arrays.stream(line.trim().split(" +"))
                        .mapToInt(Integer::valueOf)
                        .toArray())
                .toArray(int[][]::new);
    }

    private static int score(int[][] bingoCard, int drawnNumber) {
        return Arrays.stream(bingoCard)
                .mapToInt(row -> Arrays.stream(row)
                        .filter(n -> n != MARKED)
                        .sum())
                .sum() * drawnNumber;
    }

}
