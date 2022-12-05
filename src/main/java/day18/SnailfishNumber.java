package day18;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.stream.Stream;

final class SnailfishNumber {

    static SnailfishNumber parse(String str) {
        return parse(new StringTokenizer(str, "[],", true));
    }

    private static SnailfishNumber parse(StringTokenizer tokenizer) {
        var token = tokenizer.nextToken();

        if ("[".equals(token)) {
            var left = parse(tokenizer);

            if (!",".equals(tokenizer.nextToken()))
                throw new IllegalArgumentException("expected , after: " + left);

            var right = parse(tokenizer);

            if (!"]".equals(tokenizer.nextToken()))
                throw new IllegalArgumentException("expected ] after: " + right);

            return new SnailfishNumber(left, right);
        } else {
            return new SnailfishNumber(Integer.parseInt(token));
        }
    }


    private Integer number;
    private SnailfishNumber left;
    private SnailfishNumber right;

    private SnailfishNumber(int number) {
        this.number = number;
    }

    private SnailfishNumber(SnailfishNumber left, SnailfishNumber right) {
        this.left = left;
        this.right = right;
    }

    private SnailfishNumber(SnailfishNumber copy) {
        this.number = copy.number;
        this.left = copy.left;
        this.right = copy.right;
    }

    public SnailfishNumber addAndReduce(SnailfishNumber other) {
        return add(other).reduce();
    }

    public int magnitude() {
        return isRegular() ? number : left.magnitude() * 3 + right.magnitude() * 2;
    }

    private SnailfishNumber add(SnailfishNumber other) {
        if (number != null && other.number != null) {
            number += other.number;
        } else {
            left = new SnailfishNumber(this);
            right = other;
            number = null;
        }

        return this;
    }

    private SnailfishNumber reduce() {
        boolean applied;
        do {
            applied = explode(new LinkedList<>()) || split();
        } while (applied);

        return this;
    }

    private boolean isPair() {
        return left != null && right != null;
    }

    private boolean isRegular() {
        return number != null;
    }

    private boolean explode(Deque<SnailfishNumber> parents) {
        if (isPair()) {
            if (mustExplode(parents.size())) {
                parents.stream()
                        .filter(parent -> parent.left != this && !parents.contains(parent.left))
                        .map(parent -> parent.left)
                        .flatMap(SnailfishNumber::traverseRightToLeft)
                        .filter(SnailfishNumber::isRegular)
                        .findFirst()
                        .ifPresent(firstRegularToTheLeft -> firstRegularToTheLeft.add(left));

                parents.stream()
                        .filter(parent -> parent.right != this && !parents.contains(parent.right))
                        .map(parent -> parent.right)
                        .flatMap(SnailfishNumber::traverseLeftToRight)
                        .filter(SnailfishNumber::isRegular)
                        .findFirst()
                        .ifPresent(firstRegularToTheRight -> firstRegularToTheRight.add(right));

                number = 0;
                left = null;
                right = null;
                return true;
            }

            parents.push(this);
            var exploded = left.explode(parents) || right.explode(parents);
            parents.pop();

            return exploded;
        }

        return false;
    }

    private boolean mustExplode(int depth) {
        return depth == 4;
    }

    private Stream<SnailfishNumber> traverseLeftToRight() {
        if (isPair()) {
            return Stream.concat(left.traverseLeftToRight(), right.traverseLeftToRight());
        } else {
            return Stream.of(this);
        }
    }

    private Stream<SnailfishNumber> traverseRightToLeft() {
        if (isPair()) {
            return Stream.concat(right.traverseRightToLeft(), left.traverseRightToLeft());
        } else {
            return Stream.of(this);
        }
    }

    private boolean split() {
        if (isPair()) {
            return left.split() || right.split();
        }

        if (mustSplit()) {
            left = new SnailfishNumber(number / 2);
            right = new SnailfishNumber(Math.ceilDiv(number, 2));
            number = null;
            return true;
        }
        return false;
    }

    private boolean mustSplit() {
        return number > 9;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnailfishNumber that = (SnailfishNumber) o;
        return Objects.equals(number, that.number) && Objects.equals(left, that.left) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, left, right);
    }

    @Override
    public String toString() {
        return number == null ? "[" + left + "," + right + "]" : number.toString();
    }

}
