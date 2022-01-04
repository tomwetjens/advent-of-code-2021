package day16;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day16Part1 {

    interface Packet {
        int version();

        int typeID();
    }

    record LiteralPacket(int version, int typeID, long literalNumber) implements Packet {
    }

    record OperatorPacket(int version, int typeID, List<Packet> subpackets) implements Packet {

        Stream<Packet> allSubpackets() {
            return subpackets.stream()
                    .flatMap(packet -> packet instanceof OperatorPacket
                            ? Stream.concat(Stream.of(packet), ((OperatorPacket) packet).allSubpackets())
                            : Stream.of(packet));
        }
    }

    public static void main(String[] args) throws Exception {
        var input = Files.readString(Paths.get(Day16Part1.class.getResource("input.txt").toURI())).trim();

        var binaryStr = decodeHexToBinary(input);
        var reader = new PacketReader(binaryStr);

        var packet = (OperatorPacket) reader.readPacket();
        var answer = packet.version() + packet.allSubpackets()
                .mapToInt(Packet::version)
                .sum();

        System.out.println(answer);
    }

    private static String decodeHexToBinary(String hexStr) {
        return hexStr.chars()
                .map(c -> Integer.parseInt(String.valueOf((char) c), 16))
                .mapToObj(Integer::toBinaryString)
                .map(binaryStr -> ("0000" + binaryStr).substring(binaryStr.length(), binaryStr.length() + 4))
                .reduce(String::concat)
                .orElse("");
    }

    static class PacketReader {

        final String binaryStr;
        int offset;

        PacketReader(String binaryStr) {
            this.binaryStr = binaryStr;
        }

        Packet readPacket() {
            var version = Integer.parseInt(binaryStr.substring(offset, offset + 3), 2);
            offset += 3;

            var typeID = Integer.parseInt(binaryStr.substring(offset, offset + 3), 2);
            offset += 3;

            return switch (typeID) {
                case 4 -> readLiteralPacket(version, typeID);
                default -> readOperatorPacket(version, typeID);
            };
        }

        private OperatorPacket readOperatorPacket(int version, int typeID) {
            var startOffset = offset;

            var lengthTypeID = binaryStr.charAt(offset);
            offset += 1;

            var subpackets = new ArrayList<Packet>();

            if (lengthTypeID == '0') {
                var totalLengthBinaryStr = binaryStr.substring(offset, offset + 15);
                var totalLength = Integer.valueOf(totalLengthBinaryStr, 2);
                offset += 15;

                var endOffset = startOffset + 16 + totalLength;

                while (offset != endOffset) {
                    subpackets.add(readPacket());
                }
            } else if (lengthTypeID == '1') {
                var numberOfSubpackets = Integer.valueOf(binaryStr.substring(offset, offset + 11), 2);
                offset += 11;

                while (numberOfSubpackets-- > 0) {
                    subpackets.add(readPacket());
                }
            }

            return new OperatorPacket(version, typeID, subpackets);
        }

        private LiteralPacket readLiteralPacket(int version, int typeID) {
            var literalBinaryStr = "";

            String group;
            do {
                group = binaryStr.substring(offset, offset + 5);
                offset += 5;

                literalBinaryStr += group.substring(1);
            } while (group.startsWith("1"));

            var literalNumber = Long.parseLong(literalBinaryStr, 2);

            return new LiteralPacket(version, typeID, literalNumber);
        }
    }
}
