
public class CheatStrategyDemo {
    public static void main(String[] args)
    {
       // Inner class to define the Cheat strategy just for a test here.
       // Not necesary since it doesn't require access to additonal data
       // In the assigned lab implement this as an EXTERNAL class instead along with Random
       // The LeastUsed. LastUsed and MostUsed strategies will require
       // additional data so they must be implemented as inner classes so they can access it
       class Cheat implements Strategy
       {

               public String getMove(String playerMove)
               {
                   String computerMove = "";
                   switch (playerMove)
                   {
                       case "R":
                           computerMove = "P";
                           break;
                       case "P":
                           computerMove = "S";
                           break;
                       case "S":
                           computerMove = "R";
                           break;
                       default:
                            computerMove = "X";
                            break;
                    }
                     return computerMove;
               }

       }
       // Create an instance of the Cheat class.
       Strategy cheat = new Cheat();

         // Test the getMove method.
       System.out.println(cheat.getMove("R"));  // should print "P"
       System.out.println(cheat.getMove("P"));  // should print "S"
       System.out.println(cheat.getMove("S"));  // should print"R"
    }
}