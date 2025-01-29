import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tic_Tac_Toe implements ActionListener{
    JFrame frame = new JFrame("Tic-Tac-Toe");
    JPanel  textPanel = new JPanel();
    JLabel textLabel = new JLabel();
    JPanel boardPanel = new JPanel();
    JButton[][] board = new JButton[3][3];
    String playerX = "x";
    String playerO = "O";
    String currentPlayer = playerX;
    boolean isSinglePlayer = false;

    boolean gameOver = false;
    int turns = 0;

    JButton resetButton = new JButton("Reset");


    Tic_Tac_Toe()
    {
        showGameModeSelection();
    }

    void setupGameWindow()
    {
        frame.setSize(650, 650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.gray);

        frame.add(boardPanel);

        for(int r = 0; r<3; r++)
        {
            for(int c = 0; c<3; c++)
            {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial",Font.BOLD, 120));
                tile.setFocusable(false);
//                tile.setText(currentPlayer);
                tile.addActionListener(this);

                frame.setVisible(true);
            }
        }

        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.addActionListener(e -> resetGame());
        textPanel.add(resetButton, BorderLayout.SOUTH);
    }


    public void actionPerformed(ActionEvent ae)
    {
        if(gameOver) return;
        JButton tile = (JButton)ae.getSource();
        if(tile.getText() == "")
        {
            tile.setText(currentPlayer);
            turns++;
            setTie();
            checkWinner();
            if(!gameOver)
            {
                currentPlayer = currentPlayer == playerX ? playerO : playerX;
                textLabel.setText(currentPlayer + "'s turn");

                if (isSinglePlayer && currentPlayer.equals(playerO)) {
                    makeAIMove();
                }
            }
        }
    }

    void showGameModeSelection() {
        String[] options = {"2 Player Game", "1 Player Game (vs AI)"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Select Game Mode",
                "Tic-Tac-Toe",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]
        );

        if (choice != JOptionPane.CLOSED_OPTION) {
            isSinglePlayer = (choice == 1);
            setupGameWindow();
        }
    }


    void checkWinner()
    {
        //horizontal
        for(int r = 0; r<3; r++)
        {
            if(board[r][0].getText() == "") continue;
            if(board[r][0].getText() == board[r][1].getText() &&
                board[r][1].getText() == board[r][2].getText())
            {
                for(int i = 0; i<3; i++)
                {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        //vertical
        for(int c = 0; c<3; c++)
        {
            if(board[0][c].getText() == "") continue;
            if(board[0][c].getText() == board[1][c].getText() &&
                    board[1][c].getText() == board[2][c].getText())
            {
                for(int i = 0; i<3; i++)
                {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        //diagonal
        if(board[0][0].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][2].getText() &&
            board[0][0].getText() != "")
        {
            for(int i = 0; i<3; i++)
            {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        //Anti-diagonal
        if(board[0][2].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][0].getText() &&
                board[0][2].getText() != "")
        {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);

            gameOver = true;
            return;
        }

    }

    void setWinner(JButton tile)
    {
        tile.setBackground(Color.gray);
        tile.setForeground(Color.green);
        textLabel.setText(currentPlayer + " is winner");
    }

    void setTie()
    {
        if(turns == 9 && !gameOver)
        {
            for(int r = 0; r<3; r++)
            {
                for(int c = 0; c<3; c++)
                {
                    board[r][c].setBackground(Color.gray);
                    board[r][c].setForeground(Color.orange);
                }
            }
            textLabel.setText("Tie!!!");
            gameOver = true;
        }
    }

    void makeAIMove() {
        if (gameOver) return;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText() == "") {
                    board[r][c].setText(playerO); // AI plays 'O'
                    turns++;
                    setTie();
                    checkWinner();
                    if (!gameOver) {
                        currentPlayer = playerX;
                        textLabel.setText(currentPlayer + "'s turn");
                    }
                    return;
                }
            }
        }
    }

    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.darkGray);
                board[r][c].setForeground(Color.white);
            }
        }
        currentPlayer = playerX;
        textLabel.setText("Tic-Tac-Toe");
        gameOver = false;
        turns = 0;
    }


    public static void main(String[] args) {
        System.out.println("Game is starting.................");
        new Tic_Tac_Toe();
    }
}


