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
 * @author GoM
 *
 */
public class PlayerAndBall extends JDialog {
	private JLabel lblPonggame = new JLabel("Pong Game");
	private JLabel lblPlayer1 = new JLabel("Player 1");
	private JLabel lblPlayer2 = new JLabel("Player 2"), lblBall = new JLabel("1/ Selected ball in game"),
			lblBack = new JLabel("2/ Selected background in game"), lblinball = new JLabel(),
			lblinball01 = new JLabel();
	private JTextField txtPlayer1 = new JTextField("Nhap de Homie");
	private JTextField txtPlayer2 = new JTextField("Nhap de Homie");
	private JRadioButton optball1, optball2, optball3, optback1, optback2, optback3;
	private ImageIcon imgball1;
	private ImageIcon imgball2;
	private ImageIcon imgball3;
	private ImageIcon imgback1;
	private ImageIcon imgback2;
	private ImageIcon imgback3;
	private ButtonGroup btgSelect1, btgSelect2;
	private JPanel panbox1, panbox2;
	private JButton btnB = new JButton(" Accept ");
	int x = 10, y = 20, w = 100, h = 30;

	public PlayerAndBall() {
		this.setTitle("Name and Ball in Game");
		this.setSize(750, 490);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		// addBall
		panbox1 = new JPanel();
		panbox2 = new JPanel();
		this.add(lblinball);
		lblinball.setSize(90, 90);
		lblinball.setLocation(160, 280);
		// addBG
		this.add(lblinball01);
		lblinball01.setSize(300, 200);
		lblinball01.setLocation(400, 170);
		// Khung
		panbox1.setLayout(new BorderLayout(14, 10));
		panbox2.setLayout(new BorderLayout(14, 10));
		optball1 = new JRadioButton("Ball 1");
		optball2 = new JRadioButton("Ball 2");
		optball3 = new JRadioButton("Ball 3");
		optback1 = new JRadioButton("BackG 1");
		optback2 = new JRadioButton("BackG 2");
		optback3 = new JRadioButton("BackG 3");
		// Ball
		this.add(lblBall);
		lblBall.setForeground(Color.BLUE);
		lblBall.setFont(new Font("Ball", Font.BOLD, 16));
		lblBall.setBounds(x + 20, y + 180, w + 80, h);
		// BG
		this.add(lblBack);
		lblBack.setForeground(Color.BLUE);
		lblBack.setFont(new Font("Background", Font.BOLD, 16));
		lblBack.setBounds(x + 390, y + 70, w + 200, h);
		// BTG
		btgSelect1 = new ButtonGroup();
		btgSelect2 = new ButtonGroup();
		btgSelect1.add(optball1);
		btgSelect1.add(optball2);
		btgSelect1.add(optball3);
		btgSelect2.add(optback1);
		btgSelect2.add(optback2);
		btgSelect2.add(optback3);
		optball1.setSelected(true);
		optback1.setSelected(true);
		imgball1 = new ImageIcon("Image/ball_45.png");
		imgball2 = new ImageIcon("Image/gai.png");
		imgball3 = new ImageIcon("Image/kirby.png");
		imgback1 = new ImageIcon("Image/backG1.gif");
		imgback2 = new ImageIcon("Image/backG2.jpg");
		imgback3 = new ImageIcon("Image/bk2.png");
		txtPlayer1.setText(PongPanel.namePlayer1);
		txtPlayer2.setText(PongPanel.namePlayer2);
		// ..
		SaveNamePlayer();
		Act();
		lblinball.setIcon(imgball1);
		lblinball01.setIcon(imgback1);
		ActionListener action = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Sound.play("Sound/Accept.wav");
				if (optball1.isSelected()) {
					lblinball.setIcon(imgball1);
				} else if (optball2.isSelected()) {
					lblinball.setIcon(imgball2);
				} else if (optball3.isSelected()) {
					lblinball.setIcon(imgball3);
				}
			}
		};
		optball1.addActionListener(action);
		optball2.addActionListener(action);
		optball3.addActionListener(action);

		ActionListener action01 = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Sound.play("Sound/Accept.wav");
				if (optback1.isSelected()) {
					lblinball01.setIcon(imgback1);
				} else if (optback2.isSelected()) {
					lblinball01.setIcon(imgback2);
				} else if (optback3.isSelected()) {
					lblinball01.setIcon(imgback3);
				}
			}
		};
		optback1.addActionListener(action01);
		optback2.addActionListener(action01);
		optback3.addActionListener(action01);
	}

	public void SaveNamePlayer() {
		this.add(txtPlayer1);
		this.add(txtPlayer2);
		this.add(lblPonggame);
		this.add(lblPlayer1);
		this.add(lblPlayer2);
		this.add(btnB);
		this.setModal(true);
		lblPonggame.setBounds(x + 250, y - 10, w + 200, h + 30);
		lblPonggame.setFont(new Font("Pong Game", Font.BOLD, 36));
		lblPlayer1.setBounds(x + 20, y + 80, w - 20, h);
		lblPlayer2.setBounds(x + 20, y + 130, w - 20, h);
		txtPlayer1.setBounds(x + 90, y + 80, w + 140, h);
		txtPlayer2.setBounds(x + 90, y + 130, w + 140, h);
		this.add(panbox1);
		// Location selected ball
		panbox1.add(optball1, BorderLayout.WEST);
		panbox1.add(optball2, BorderLayout.CENTER);
		panbox1.add(optball3, BorderLayout.EAST);
		panbox1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		panbox1.setSize(210, 30);
		panbox1.setLocation(100, 240);
		btnB.setBounds(x + 300, y + 375, w, h);

		this.add(panbox2);
		// Location selected ball
		panbox2.add(optback1, BorderLayout.WEST);
		panbox2.add(optback2, BorderLayout.CENTER);
		panbox2.add(optback3, BorderLayout.EAST);
		panbox2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		panbox2.setSize(250, 30);
		panbox2.setLocation(420, 125);
	}

	public void Act() {
		btnB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (optball1.isSelected() == true) {
					PongPanel.NumTypeBall = 0;
				} else if (optball2.isSelected() == true) {
					PongPanel.NumTypeBall = 1;
				} else if (optball3.isSelected() == true) {
					PongPanel.NumTypeBall = 2;
				}
				if (optback1.isSelected() == true) {
					PongPanel.NumTypeBall01 = 0;
				} else if (optback2.isSelected() == true) {
					PongPanel.NumTypeBall01 = 1;
				} else if (optback3.isSelected() == true) {
					PongPanel.NumTypeBall01 = 2;
				}
				PongPanel.namePlayer1 = txtPlayer1.getText();
				PongPanel.namePlayer2 = txtPlayer2.getText();
				dispose();
				Sound.play("Sound/click.wav");

			}
		});
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
