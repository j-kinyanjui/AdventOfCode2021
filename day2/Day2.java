import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day2 {

    public int partOne(Multimap<String, Integer> steps) {
        var forward = steps.get("forward").stream().mapToInt(Integer::intValue).sum();
        var down = steps.get("down").stream().mapToInt(Integer::intValue).sum();
        var up = steps.get("up").stream().mapToInt(Integer::intValue).sum() * -1;
        return forward * (up + down);
    }

    public int partTwo(Multimap<String, Integer> steps) {
        AtomicInteger aim = new AtomicInteger();
        AtomicInteger depth = new AtomicInteger();
        AtomicInteger horizontal = new AtomicInteger();

        steps.forEach((direction, step) -> {
            switch (direction) {
                case "down": aim.addAndGet(step);
                    break;
                case "up" : aim.addAndGet((-step));
                    break;
                case "forward": {
                    horizontal.addAndGet(step);
                    depth.addAndGet((aim.get() * step));
                    break;
                }
            }
        });

        return depth.get() * horizontal.get();
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("dive.txt"));
        Multimap<String, Integer> map = LinkedListMultimap.create();

        while (scanner.hasNextLine()) {
            String[] move = scanner.nextLine().split(" ");
            map.put(move[0], Integer.valueOf(move[1]));
        }
        scanner.close();

        var scratch = new Day2();
        System.out.println(scratch.partOne(map));
        System.out.println(scratch.partTwo(map));
    }
}
