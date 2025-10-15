package game;

import game.backend.Direction;
import game.backend.World;
import game.backend.WorldData;
import game.backend.WorldSnapshot;

public class ConsoleDemo {
    public static void main(String[] args) {
        World w = new World(new WorldData(5, 3));
        WorldSnapshot s = w.snapshot();
        System.out.printf("tick=%d head=%s food=%s score=%d los=%d%n",
                -1, s.snake().get(0), s.food(), s.score(), s.lineOfSight().size());
        for (int i = 0; i < 10 && !w.isGameOver(); i++) {
            if (i == 3) w.setDirection(Direction.POS_Z);
            if (i == 6) w.setDirection(Direction.POS_Y);
            w.step();
            s = w.snapshot();
            System.out.printf("tick=%d head=%s food=%s score=%d los=%d%n",
                    i, s.snake().get(0), s.food(), s.score(), s.lineOfSight().size());
        }
    }
}