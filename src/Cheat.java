public class Cheat implements Strategy {
    @Override
    public String getMove(String playermove){
        // if the player Move choice equals to the  string then it will return it
        switch(playermove){
            case "R": return "R";
            case "P": return "P";
            case "S": return "S";
            default: return "X";
        }
    }
}
