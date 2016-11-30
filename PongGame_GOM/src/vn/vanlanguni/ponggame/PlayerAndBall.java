/**
 * 
 */
package vn.vanlanguni.ponggame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * @author Shu
 *
 */
public class PlayerAndBall extends JDialog {
	JLabel lblPonggame = new JLabel("Pong Game");
	JLabel lblPlayer1 = new JLabel("Player 1");
	JLabel lblPlayer2 = new JLabel("Player 2"), lblBall = new JLabel(
			"Selected ball in game");
	JTextField txtPlayer1 = new JTextField();
	JTextField txtPlayer2 = new JTextField();
	private JRadioButton optball1, optball2, optball3;
	private ImageIcon imgball1;
	private ImageIcon imgball2;
	private ImageIcon imgball3;
	private ButtonGroup btgSelect;
	private JPanel panbox;
	JButton btnB = new JButton(" Accept ");
	int x = 10, y = 20, w = 100, h = 30;

	public PlayerAndBall() {
		this.setTitle("Name and Ball in Game");
		this.setSize(400, 500);
		this.setLayout(null);
		setLocationRelativeTo(null);
		// add
		panbox = new JPanel();
		panbox.setLayout(new BorderLayout(14, 10));
		optball1 = new JRadioButton("Ball 1");
		optball2 = new JRadioButton("Ball 2");
		optball3 = new JRadioButton("Ball 3");
		add(lblBall);
		lblBall.setForeground(Color.BLACK);
		lblBall.setFont(new Font("Ball", Font.BOLD, 16));
		lblBall.setBounds(x + 20, y + 170, w + 80, h);
		btgSelect = new ButtonGroup();
		btgSelect.add(optball1);
		btgSelect.add(optball2);
		btgSelect.add(optball3);
		optball1.setSelected(true);
		imgball1 = new ImageIcon("Image/ball_45.png");
		imgball2 = new ImageIcon("Image/ball_5.png");
		imgball3 = new ImageIcon("Image/ball_123.png");
		txtPlayer1.setText(PongPanel.namePlayer1);
		txtPlayer2.setText(PongPanel.namePlayer2);
		// ..
		SaveNamePlayer();
		Act();
	}

	public void SaveNamePlayer() {
		add(txtPlayer1);
		add(txtPlayer2);
		add(lblPonggame);
		add(lblPlayer1);
		add(lblPlayer2);
		add(btnB);
		setModal(true);
		lblPonggame.setBounds(x + 100, y - 10, w + 200, h + 30);
		lblPonggame.setFont(new Font("Pong Game", Font.BOLD, 36));
		lblPlayer1.setBounds(x + 20, y + 80, w - 20, h);
		lblPlayer2.setBounds(x + 20, y + 130, w - 20, h);
		txtPlayer1.setBounds(x + 90, y + 80, w + 140, h);
		txtPlayer2.setBounds(x + 90, y + 130, w + 140, h);
		this.add(panbox);
		// Location selected ball
		panbox.add(optball1, BorderLayout.WEST);
		panbox.add(optball2, BorderLayout.CENTER);
		panbox.add(optball3, BorderLayout.EAST);
		panbox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		panbox.setSize(210, 30);
		panbox.setLocation(110, 230);
		btnB.setBounds(x + 140, y + 350, w, h);
	}

	public void Act() {
		ActionListener act = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == btnB) {
					// pongPanel.setVisible(true);
					lblPlayer1.setVisible(false);
					lblPlayer2.setVisible(false);
					txtPlayer1.setVisible(false);
					txtPlayer2.setVisible(false);
					btnB.setVisible(false);
					PongPanel.namePlayer1 = txtPlayer1.getText();
					PongPanel.namePlayer2 = txtPlayer2.getText();
					dispose();
				}
			}
		};
		btnB.addActionListener(act);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerAndBall f = new PlayerAndBall();
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
