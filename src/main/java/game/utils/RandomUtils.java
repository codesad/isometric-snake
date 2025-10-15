package game.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {
    public static <T> T getRandomFromList(Random rng, List<T> list) {
        return list.get(rng.nextInt(list.size()));
    }

    public static double randomFrom(Random rng, double a, double b) {
        return a + rng.nextDouble() * (b - a);
    }
}
