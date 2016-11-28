/*
 * 
 * 
 * 
 * 
 */
package vn.vanlanguni.ponggame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 
 * @author Invisible Man
 *
 */
public class MainGameUI extends JFrame{
	private static final int _HEIGHT = 550;
	private static final int _WIDTH = 500;
	private PongPanel pongPanel;
	
	JLabel lblPonggame = new JLabel("Pong Game");
	JLabel lblPlayer1 = new JLabel("Player 1");
	JLabel lblPlayer2 = new JLabel("Player 2");
	
	JTextField txtPlayer1 = new JTextField();
	JTextField txtPlayer2 = new JTextField();
	
	JButton btnB = new JButton(" Accept ");
	int x = 10, y = 20, w = 100, h = 30;  
	
	public MainGameUI(){
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
		setLayout(null);
		setTitle("Pong Game - K21T01 Ltd.");
		pongPanel = new PongPanel();
		add(pongPanel);
		pongPanel.setBounds(0, 0, 500, 500);
		pongPanel.setVisible(false);
		SaveNamePlayer();
		Act();
		pack();
	}
	
	public void SaveNamePlayer(){
		add(txtPlayer1);
		add(txtPlayer2);
		
		add(lblPonggame);
		add(lblPlayer1);
		add(lblPlayer2);
		add(btnB);
		
		lblPonggame.setBounds(x+120, y, w+200, h+30);
		lblPonggame.setFont(new Font("Pong Game", Font.BOLD, 36));
		lblPlayer1.setBounds(x, y+110, w-20, h);
		lblPlayer2.setBounds(x, y+170, w-20, h);
		txtPlayer1.setBounds(x+90, y+110, w+140, h);
		txtPlayer2.setBounds(x+90, y+170, w+140, h);
		btnB.setBounds(x+140, y+230, w, h);
	}
	
	public void Act(){
		ActionListener act = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == btnB){
					pongPanel.setVisible(true);
					lblPlayer1.setVisible(false);
					lblPlayer2.setVisible(false);
					txtPlayer1.setVisible(false);
					txtPlayer2.setVisible(false);
					btnB.setVisible(false);
					pongPanel.namePlayer1 = txtPlayer1.getText();
					pongPanel.namePlayer2 = txtPlayer2.getText();
				}
			}
		};
		btnB.addActionListener(act);
	}

    public static void main(String[] args) {
       MainGameUI mainFrame = new MainGameUI();
       mainFrame.setVisible(true);
       mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}