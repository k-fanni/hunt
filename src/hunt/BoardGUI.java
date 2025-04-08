/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hunt;


import hunt.board.Board;
import hunt.board.Field;
import hunt.player.Hunter;
import hunt.player.Target;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Fanni
 */
public class BoardGUI {
    
    private Board board;
    private JButton[][] buttons;
    private JPanel boardPanel;
    private JLabel timeLabel;
    private JLabel stepsLeftLabel;
    private JLabel playerLabel;
    private JLabel stepInfoLabel;
    private JPanel infoPanel;
    private long startTime;
    private Timer timer;
    private final ImageIcon targetIcon;
    private final ImageIcon hunterIcon;
    
    
    public BoardGUI(int n) {
        board = new Board(n);
        boardPanel = new JPanel();
        infoPanel = new JPanel();
        buttons = new JButton[n][n];
        
        boardPanel.setLayout(new GridLayout(n, n));
        infoPanel.setLayout(new GridLayout(4, 1));
        
        timeLabel = new JLabel(" ");
        stepsLeftLabel = new JLabel(" ");
        playerLabel = new JLabel(" ");
        stepInfoLabel = new JLabel(" ");
        infoPanel.add(timeLabel);
        infoPanel.add(stepsLeftLabel);
        infoPanel.add(playerLabel);
        infoPanel.add(stepInfoLabel);
        
        targetIcon = new ImageIcon(getClass().getResource("target.png"));
        hunterIcon = new ImageIcon(getClass().getResource("hunter.png"));
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(90, 90));
                button.setMinimumSize(new Dimension(60, 60));
                buttons[i][j] = button;
                button.setBorder(BorderFactory
                        .createLineBorder(Color.LIGHT_GRAY, 1));
                Point position = new Point(i, j);
                Redraw(position);
                button.setFocusable(false);
                button.addActionListener(new ClickListener(position));
                boardPanel.add(button);
            }
        }
        
        RedrawActiveField();
        
        StyleInfoPanel();
        
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText("Time: " + ElapsedTime() + " seconds");
            }
        });
        startTime = System.currentTimeMillis();
        timer.start();

    }
    
    private void StyleInfoPanel() {
        infoPanel.setBackground(Color.BLACK);
        stepsLeftLabel.setFont(new Font("Courier", Font.BOLD, 14));
        timeLabel.setFont(new Font("Courier", Font.BOLD, 14));
        playerLabel.setFont(new Font("Courier", Font.BOLD, 14));
        stepInfoLabel.setFont(new Font("Courier", Font.BOLD, 14));
        stepsLeftLabel.setForeground(Color.WHITE);
        timeLabel.setForeground(Color.WHITE);
        playerLabel.setForeground(Color.WHITE);
        stepInfoLabel.setForeground(Color.WHITE);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        stepsLeftLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        playerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        stepInfoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        playerLabel.setOpaque(true);
        stepInfoLabel.setOpaque(true);
        UpdateInfoLabel();
    }
    
    private void Redraw(Point p) {
        JButton button = buttons[p.x][p.y];
        Field field = board.get(p);
        button.setBackground(Color.WHITE);
        
        if (field.getPlayer() instanceof Target) {
            button.setIcon(MakeIcon(targetIcon));
        }
        else if (field.getPlayer() instanceof Hunter) {
            button.setIcon(MakeIcon(hunterIcon));
        }
        else {
            button.setIcon(null);
        }
    }
    
    private ImageIcon MakeIcon(ImageIcon icon) {
        return new ImageIcon(icon.getImage()
                .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    }
    
    private long ElapsedTime() {
        return (long) (System.currentTimeMillis() - startTime) / 1000;
    }
    
    private void UpdateInfoLabel() {
        stepsLeftLabel.setText("Hunter has " + board.getStepsLeft() + " steps left.");
        playerLabel.setText("Current player: " + board.PlayerName());
        String text = "Move with: ";
        if (board.CurrentPlayer() instanceof Target) {
            playerLabel.setBackground(Color.decode("#e61b14"));
            stepInfoLabel.setBackground(Color.decode("#e61b14"));
            text += "arrow keys";
        }
        else {
            playerLabel.setBackground(Color.decode("#165caf"));
            stepInfoLabel.setBackground(Color.decode("#165caf"));
            text += "WASD keys. Click piece.";
        }
        stepInfoLabel.setText(text);
    }
    
    private void RedrawActiveField() {
        if (board.CurrentPlayer() == null) return;
        Point p = board.PlayerPosition();
        JButton button = buttons[p.x][p.y];
        button.setBackground(Color.decode("#addaa9"));
    }
    
    private void MovePlayer(int x, int y) {
        Point position = new Point(x, y);
        if (board.CanMove(position)) {
            Point oldPosition = board.PlayerPosition();
            board.MovePlayer(position);
            Redraw(position);
            Redraw(oldPosition);
            if (board.CurrentPlayer() instanceof Target) {
                RedrawActiveField();
            }
            UpdateInfoLabel();
        }
        
        if (board.GameOver()) {
            EndGame();
        }
    }
    
    private void EndGame() {
        timer.stop();
        Object[] buttons = {"New game", "Exit"};
        int submit = JOptionPane.showOptionDialog(boardPanel, 
            board.getWinner() + " won in " + ElapsedTime() + " seconds.", "Congrats!", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
            buttons, buttons[0]);
        if (submit == 0) {
            int size = (int)Math.sqrt(boardPanel.getComponents().length);
            board = new Board(size);
            UpdateInfoLabel();
            startTime = System.currentTimeMillis();
            timer.start();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Redraw(new Point(i, j));
                    RedrawActiveField();
                }
            }
        }
        if (submit == 1) {
            System.exit(0);
        }
    }
    
    /**
     * Handles the event of the user pressing a key.
     * If the user pressed one of the allowed keys for the current player, 
     * the MovePlayer method is called for the point on the board in the 
     * given direction.
     * @param key the key the user pressed on the keyboard
     */
    public void PlayerKeyPressed(int key) {
        if (board.CurrentPlayer() == null) return;
        Point p = board.PlayerPosition();
        if (board.CurrentPlayer() instanceof Target) {
            switch (key) {
                case KeyEvent.VK_RIGHT:
                    MovePlayer(p.x, p.y + 1);
                    break;
                case KeyEvent.VK_LEFT:
                    MovePlayer(p.x, p.y - 1);
                    break;
                case KeyEvent.VK_DOWN:
                    MovePlayer(p.x + 1, p.y);
                    break;
                case KeyEvent.VK_UP:
                    MovePlayer(p.x - 1, p.y);
                    break;
                default:
                    break;
            }
        }
        else {
            switch (key) {
                case KeyEvent.VK_D:
                    MovePlayer(p.x, p.y + 1);
                    break;
                case KeyEvent.VK_A:
                    MovePlayer(p.x, p.y - 1);
                    break;
                case KeyEvent.VK_S:
                    MovePlayer(p.x + 1, p.y);
                    break;
                case KeyEvent.VK_W:
                    MovePlayer(p.x - 1, p.y);
                    break;
                default:
                    break;
            }
        }
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public JPanel getInfoPanel() {
        return infoPanel;
    }
    
    
    class ClickListener implements ActionListener {

        private final Point position;

        public ClickListener(Point position) {
            this.position = position;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (board.get(position).isFree()) return;
            if (board.CurrentPlayer() != null 
                && board.CurrentPlayer() instanceof Target) return;
            if (board.CurrentPlayer() != null) {
                Redraw(board.PlayerPosition());
            }
            board.SetActiveHunter(position);
            RedrawActiveField();
            
        }
    }
    
}
