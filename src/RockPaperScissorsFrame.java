import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Contains all the fields for the GUI Rock paper Scissors game
 * It picks the move from the five strategies chosen at random
 */
public class RockPaperScissorsFrame extends JFrame {
    private JButton rockBtn, scissorsBtn,paperBtn, quitBtn;
    private JTextField playerWinsTF,computerWinsTF, tiesTF;
    private int playerWins = 0, computerWins =0, ties = 0;
    private JTextArea resultsTA;
    private int rockCount = 0, paperCount = 0, scissorsCount = 0, quitCount = 0;
    private String lastPlayerMove = null;


    private Strategy cheat = new Cheat();
    private Strategy random = new RandomStrategy();
    private Strategy leastUsed = new LeastUsed();
    private Strategy mostUsed = new MostUsed();
    private Strategy lastUsed = new LastUsed();
    private Random rnd = new Random();

    /**
     * This is where the buttons are created and builds the window and
     * all its panel. At the bottom, it connects all the bottom
     */
    public RockPaperScissorsFrame() {
        setTitle("Rock Paper Scissors");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLayout( new BorderLayout());

        add(createButtonPanel(), BorderLayout.NORTH);
        add(createStatsPanel(), BorderLayout.CENTER);
        add(createResultsPanel(), BorderLayout.SOUTH);

        ActionListener moveListener = ae -> playRound(ae);
        rockBtn.addActionListener(moveListener);
        paperBtn.addActionListener(moveListener);
        scissorsBtn.addActionListener(moveListener);
        setVisible(true);
    }

    /**
     *  Creates the Rock paper scissors
     * @return the button panel
     */
    // the loadIcon is the method to resize the images
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4));
        panel.setBorder(BorderFactory.createTitledBorder("Choose Rock Paper Scissors"));
        rockBtn = new JButton("Rock ", loadIcon("/rock.jpg"));
        paperBtn = new JButton("Paper",  loadIcon("/paper.png"));
        scissorsBtn = new JButton("Scissors", loadIcon("/scissors.jpg"));
        quitBtn = new JButton("Quit", loadIcon("/quit.jpg"));

        quitBtn.addActionListener(e -> System.exit(0));
        panel.add(rockBtn);
        panel.add(paperBtn);
        panel.add(scissorsBtn);
        panel.add(quitBtn);
        return panel;

    }

    /**
     * Creates the stats panel with read-only. Player wins , computer wins or tie boxes
     *  @return
     */
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2,5,5));
        panel.setBorder(BorderFactory.createTitledBorder("Stats"));
        playerWinsTF = new JTextField("0");
        computerWinsTF = new JTextField("0");
        tiesTF = new JTextField("0");
        playerWinsTF.setEditable(false);
        tiesTF.setEditable(false);

        panel.add(new JLabel("Player Wins:"));
        panel.add(playerWinsTF);
        panel.add(new JLabel("Computer Wins:"));
        panel.add(computerWinsTF);
        panel.add(new JLabel("Ties:"));
        panel.add(tiesTF);
        return panel;
    }

    /**
     * Creates the scrolling panel
     * @return the results panel
     */
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Results"));

        resultsTA = new JTextArea(10, 40);
        resultsTA.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsTA);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;

    }

    /**
     *
     * @param fileName are the image file icon
     * @return the resized icon
     */
   // Added this method because I was having trouble with sizing the icons images at the top.
    // Essentially, this creates an instance of the original images and scale to 64 width and 64 height
    private ImageIcon loadIcon(String fileName) {
        ImageIcon original = new ImageIcon(getClass().getResource(fileName));
        Image scaled = original.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
    // method for what moves beats what moves
    private String beat(String move){
        if (move.equals("R"))
            return "P";
        if (move.equals("P"))
            return "S";
        return "R";
        }
    // Turns "R"/"P"/"S" into "Rock"/"Paper"/"Scissors"
    private String name(String move) {
        if (move.equals("R")) return "Rock";
        if (move.equals("P")) return "Paper";
        return "Scissors";
    }
    private String action(String winner) {
        if (winner.equals("R")) return "Rock breaks Scissors";
        if (winner.equals("P")) return "Paper covers Rock";
        return "Scissors cuts Paper";
    }

    class LeastUsed implements Strategy{
        @Override
        public String getMove(String playerMove) {
            String least = "R";
            int min = rockCount;
            if (paperCount < min) {
                least = "P";
                min = paperCount;
            }
            if (scissorsCount < min) {
                least = "S";
            }
            return beat(least);

        }
    }
    class MostUsed implements Strategy{
        @Override
        public String getMove(String playerMove) {
            String most = "R";
            int max = rockCount;
            if (paperCount > max) {
                most = "P";
                max = paperCount;
            }
            if (scissorsCount > max) {
                most = "S";
            }
            return beat(most);
        }
    }
    class LastUsed implements Strategy{
        @Override
        public String getMove(String playerMove) {
            if (lastPlayerMove == null) {
                return new RandomStrategy().getMove(playerMove);
            }
            return lastPlayerMove;
      }
    }

    // Action listener method

    private void playRound(ActionEvent ae) {
        // 1. Which button? That's the player's move.
        String playerMove;
        if (ae.getSource() == rockBtn)
            playerMove = "R";
        else if (ae.getSource() == paperBtn)
            playerMove = "P";
        else
            playerMove = "S";

        // 2. Roll 1-100 to pick the strategy (the table in the assignment).
        int roll = rnd.nextInt(100) + 1;
        Strategy strategy;
        String strategyName;
        if (roll <= 10)
        { strategy = cheat;
            strategyName = "Cheat"; }
        else if (roll <= 30)
        { strategy = leastUsed;
            strategyName = "Least Used"; }
        else if (roll <= 50)
        { strategy = mostUsed;
            strategyName = "Most Used"; }
        else if (roll <= 70)
        { strategy = lastUsed;
            strategyName = "Last Used"; }
        else
        { strategy = random;
            strategyName = "Random"; }

        // 3. Ask that strategy for the computer's move.
        String computerMove = strategy.getMove(playerMove);

    // to determine who won
        String result;
        if (playerMove.equals(computerMove)) {
            ties++;
            result = name(playerMove) + " vs " + name(computerMove)
                    + " (Tie! Computer: " + strategyName + ")";
        } else if (beat(computerMove).equals(playerMove)) {
            playerWins++;
            result = action(playerMove) + " (Player wins! Computer: " + strategyName + ")";
        } else {
            computerWins++;
            result = action(computerMove) + " (Computer wins! Computer: " + strategyName + ")";
        }

        // 5. Show the result and the running totals.
        resultsTA.append(result + "\n");
        playerWinsTF.setText("" + playerWins);
        computerWinsTF.setText("" + computerWins);
        tiesTF.setText("" + ties);

        // 6. Remember the player's move for LeastUsed / MostUsed / LastUsed.
        if (playerMove.equals("R"))      rockCount++;
        else if (playerMove.equals("P")) paperCount++;
        else                             scissorsCount++;
        lastPlayerMove = playerMove;
    }

}

