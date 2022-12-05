package day18;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Part1Test {

    @Test
    void parse() {
        SnailfishNumber.parse("[[[6,[8,3]],[2,0]],[[[9,5],[9,1]],3]]");
    }

    @Test
    void addAndReduce() {
        assertThat(SnailfishNumber.parse("1").addAndReduce(SnailfishNumber.parse("2"))).isEqualTo(SnailfishNumber.parse("3"));
        assertThat(SnailfishNumber.parse("[1,2]").addAndReduce(SnailfishNumber.parse("3"))).isEqualTo(SnailfishNumber.parse("[[1,2],3]"));
        assertThat(SnailfishNumber.parse("1").addAndReduce(SnailfishNumber.parse("[2,3]"))).isEqualTo(SnailfishNumber.parse("[1,[2,3]]"));
        assertThat(SnailfishNumber.parse("[1,2]").addAndReduce(SnailfishNumber.parse("[[3,4],5]"))).isEqualTo(SnailfishNumber.parse("[[1,2],[[3,4],5]]"));

        assertThat(SnailfishNumber.parse("[[[[4,3],4],4],[7,[[8,4],9]]]").addAndReduce(SnailfishNumber.parse("[1,1]")))
                .isEqualTo(SnailfishNumber.parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"));

        assertThat(SnailfishNumber.parse("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]").addAndReduce(SnailfishNumber.parse("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]")))
                .isEqualTo(SnailfishNumber.parse("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]"));
        assertThat(SnailfishNumber.parse("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]").addAndReduce(SnailfishNumber.parse("[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]")))
                .isEqualTo(SnailfishNumber.parse("[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]"));
        assertThat(SnailfishNumber.parse("[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]").addAndReduce(SnailfishNumber.parse("[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]")))
                .isEqualTo(SnailfishNumber.parse("[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]"));
        assertThat(SnailfishNumber.parse("[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]").addAndReduce(SnailfishNumber.parse("[7,[5,[[3,8],[1,4]]]]")))
                .isEqualTo(SnailfishNumber.parse("[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]"));
        assertThat(SnailfishNumber.parse("[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]").addAndReduce(SnailfishNumber.parse("[[2,[2,2]],[8,[8,1]]]")))
                .isEqualTo(SnailfishNumber.parse("[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]"));
        assertThat(SnailfishNumber.parse("[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]").addAndReduce(SnailfishNumber.parse("[2,9]")))
                .isEqualTo(SnailfishNumber.parse("[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]"));
        assertThat(SnailfishNumber.parse("[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]").addAndReduce(SnailfishNumber.parse("[1,[[[9,3],9],[[9,0],[0,7]]]]")))
                .isEqualTo(SnailfishNumber.parse("[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]"));
        assertThat(SnailfishNumber.parse("[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]").addAndReduce(SnailfishNumber.parse("[[[5,[7,4]],7],1]")))
                .isEqualTo(SnailfishNumber.parse("[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]"));
        assertThat(SnailfishNumber.parse("[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]").addAndReduce(SnailfishNumber.parse("[[[[4,2],2],6],[8,7]]")))
                .isEqualTo(SnailfishNumber.parse("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"));

        assertThat(SnailfishNumber.parse("[[[6,[8,3]],[2,0]],[[[9,5],[9,1]],3]]").addAndReduce(SnailfishNumber.parse("[[[9,[2,2]],[5,4]],[[[2,2],[9,6]],[7,7]]]")))
                .isEqualTo(SnailfishNumber.parse("[[[[6,0],[7,7]],[[8,7],9]],[[[6,6],[6,0]],[[6,6],[6,7]]]]"));
        assertThat(SnailfishNumber.parse("[[[9,[2,2]],[5,4]],[[[2,2],[9,6]],[7,7]]]").addAndReduce(SnailfishNumber.parse("[[[6,[8,3]],[2,0]],[[[9,5],[9,1]],3]]")))
                .isEqualTo(SnailfishNumber.parse("[[[[6,6],[6,6]],[[6,6],[6,7]]],[[[7,0],[7,8]],[[8,7],[5,5]]]]"));
    }

    @Test
    void process() {
        assertThat(Day18Part1.sum(Stream.of(
                "[1,1]",
                "[2,2]",
                "[3,3]",
                "[4,4]"))).get().asString().isEqualTo("[[[[1,1],[2,2]],[3,3]],[4,4]]");

        assertThat(Day18Part1.sum(Stream.of(
                "[1,1]",
                "[2,2]",
                "[3,3]",
                "[4,4]",
                "[5,5]"))).get().asString().isEqualTo("[[[[3,0],[5,3]],[4,4]],[5,5]]");

        assertThat(Day18Part1.sum(Stream.of(
                "[1,1]",
                "[2,2]",
                "[3,3]",
                "[4,4]",
                "[5,5]",
                "[6,6]"))).get().asString().isEqualTo("[[[[5,0],[7,4]],[5,5]],[6,6]]");

        assertThat(Day18Part1.sum(Stream.of(
                "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
                "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                "[7,[5,[[3,8],[1,4]]]]",
                "[[2,[2,2]],[8,[8,1]]]",
                "[2,9]",
                "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                "[[[5,[7,4]],7],1]",
                "[[[[4,2],2],6],[8,7]]"))).get().asString().isEqualTo("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
    }

    @Test
    void magnitude() {
        assertThat(SnailfishNumber.parse("[[1,2],[[3,4],5]]").magnitude()).isEqualTo(143);
        assertThat(SnailfishNumber.parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").magnitude()).isEqualTo(1384);
        assertThat(SnailfishNumber.parse("[[[[1,1],[2,2]],[3,3]],[4,4]]").magnitude()).isEqualTo(445);
        assertThat(SnailfishNumber.parse("[[[[3,0],[5,3]],[4,4]],[5,5]]").magnitude()).isEqualTo(791);
        assertThat(SnailfishNumber.parse("[[[[5,0],[7,4]],[5,5]],[6,6]]").magnitude()).isEqualTo(1137);
        assertThat(SnailfishNumber.parse("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").magnitude()).isEqualTo(3488);
    }
}