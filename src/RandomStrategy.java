import java.util.Random;
public class RandomStrategy implements Strategy{
    private Random rnd = new Random();
    @Override
    public String getMove(String playerMove) {
        String[] moves = {"R", "P", "S"};
       // returns the moves[0] etc and the matching value
        return moves[rnd.nextInt(3)];


    }
}
