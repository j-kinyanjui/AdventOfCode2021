import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3 {

    public int findPowerConsumption(Multimap<Integer, Character> input) {

        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();

        IntStream.range(0, input.keys().size())
                .boxed()
                .forEach(index -> {

                    var charMap = input.get(index).stream()
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

                    charMap
                            .entrySet()
                            .stream()
                            .max(Map.Entry.comparingByValue())
                            .ifPresent(key -> gamma.append(key.getKey()));

                    charMap
                            .entrySet()
                            .stream()
                            .min(Map.Entry.comparingByValue())
                            .ifPresent(key -> epsilon.append(key.getKey()));
                });

        return (Integer.parseInt(gamma.toString(), 2)) * (Integer.parseInt(epsilon.toString(), 2));
    }

    public int findRating(List<String> codes, String gasName) {

        String ratingCode = null;
        for (int i = 0; i < codes.get(0).length(); ++i) {

            int ones = 0;
            for (String code : codes) {
                if (code.charAt(i) == '1') {
                    ++ones;
                }
            }

            int zeros = codes.size() - ones;
            List<String> newCodes = new ArrayList<>();
            for (String code : codes) {

                if (ones >= zeros) {
                    if ((gasName.equals("Oxygen") && code.charAt(i) == '1') || (gasName.equals("CO2") && code.charAt(i) == '0')) {
                        newCodes.add(code);
                    }
                } else {
                    if ((gasName.equals("Oxygen") && code.charAt(i) == '0') || (gasName.equals("CO2") && code.charAt(i) == '1')) {
                        newCodes.add(code);
                    }
                }
            }

            codes = newCodes;
            if (codes.size() == 1) {
                ratingCode = codes.get(0);
                break;
            }
        }

        return ratingCode != null ? Integer.parseInt(ratingCode, 2) : 0;
    }

    public int findLifeSupportRating(List<String> codes) {
        var oxygen = findRating(codes, "Oxygen");
        var co2 = findRating(codes, "CO2");
        return oxygen * co2;
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("diagnostic.txt"));
        Multimap<Integer, Character> map = LinkedListMultimap.create();
        List<String> codes = new ArrayList<>();

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            codes.add(line);
            for (int i = 0; i < line.length(); i++) {
                map.put(i, line.charAt(i));
            }
        }
        scanner.close();

        var scratch = new Day3();
        System.out.println(scratch.findPowerConsumption(map));
        System.out.println(scratch.findLifeSupportRating(codes));
    }
}
