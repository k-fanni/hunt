/*
 * Click nbfs:nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs:nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hunt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 *
 * @author Fanni
 */
public class HuntGUI {
    
    private JFrame frame;
    private BoardGUI boardGUI;
    private final int INIT_SIZE = 5;
    
    
    public HuntGUI() {
        frame = new JFrame("Hunt Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyPressListener());
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        
        MakeBoard(INIT_SIZE);
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu menu = new JMenu("Game");
        JMenu newGame = new JMenu("New");
        menuBar.add(menu);
        menu.add(newGame);
        int[] boardSize = new int[]{3, 5, 7};
        for (int size : boardSize) {
            JMenuItem sizeOption = new JMenuItem(size + "×" + size);
            newGame.add(sizeOption);
            sizeOption.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(boardGUI.getBoardPanel());
                    frame.getContentPane().remove(boardGUI.getInfoPanel());
                    MakeBoard(size);
                    frame.pack();
                }
            });
        }
        JMenuItem exitMenu = new JMenuItem("Exit");
        menu.add(exitMenu);
        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    private void MakeBoard(int size) {
        boardGUI = new BoardGUI(size);
        frame.getContentPane().add(boardGUI.getBoardPanel(), BorderLayout.CENTER);
        frame.getContentPane().add(boardGUI.getInfoPanel(), BorderLayout.NORTH);
    }
    
    class KeyPressListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            boardGUI.PlayerKeyPressed(e.getKeyCode());
        }
        
    }

}
