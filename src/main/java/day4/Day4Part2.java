package day4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day4Part2 {

    private static final int MARKED = -1;

    public static void main(String[] args) throws Exception {
        var input = Files.lines(Paths.get(Day4Part2.class.getResource("/day4-input").toURI()))
                .collect(Collectors.toList());

        var numbers = parseNumbers(input);
        var bingoCards = parseBingoCards(input);

        var winners = new ArrayList<int[][]>();
        var winningScores = new ArrayList<Integer>();
        int drawnNumber = 0;

        while (!numbers.isEmpty()) {
            drawnNumber = numbers.poll();

            for (var bingoCard : bingoCards) {
                for (var row : bingoCard) {
                    for (var columnIndex = 0; columnIndex < row.length; columnIndex++) {
                        if (row[columnIndex] == drawnNumber) {
                            row[columnIndex] = MARKED;
                        }
                    }

                    if (isWholeRowMarked(row)) {
                        winners.add(bingoCard);
                        winningScores.add(score(bingoCard, drawnNumber));
                        break;
                    }
                }

                for (var columnIndex = 0; columnIndex < 5; columnIndex++) {
                    if (isWholeColumnMarked(bingoCard, columnIndex)) {
                        winners.add(bingoCard);
                        winningScores.add(score(bingoCard, drawnNumber));
                        break;
                    }
                }
            }

            bingoCards.removeAll(winners);
        }

        var lastWinningScore = winningScores.get(winningScores.size() - 1);

        System.out.println(lastWinningScore);
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
