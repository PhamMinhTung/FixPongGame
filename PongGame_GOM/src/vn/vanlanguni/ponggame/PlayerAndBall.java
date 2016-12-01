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
			"Selected ball in game"), lblinball = new JLabel();
	JTextField txtPlayer1 = new JTextField();
	JTextField txtPlayer2 = new JTextField();
	private JRadioButton optball1, optball2, optball3, optback1,optback2,optback3;
	private ImageIcon imgball1;
	private ImageIcon imgball2;
	private ImageIcon imgball3;
	private ImageIcon imgback1;
	private ImageIcon imgback2;
	private ImageIcon imgback3;
	private ButtonGroup btgSelect1,btgSelect2;
	private JPanel panbox1,panbox2;
	JButton btnB = new JButton(" Accept ");
	int x = 10, y = 20, w = 100, h = 30;

	public PlayerAndBall() {
		this.setTitle("Name and Ball in Game");
		this.setSize(400, 530);
		this.setLayout(null);
		setLocationRelativeTo(null);
		// add
		panbox1 = new JPanel();
		panbox2 = new JPanel();
		add(lblinball);
		lblinball.setSize(90, 90);
		lblinball.setLocation(160, 270);
		panbox1.setLayout(new BorderLayout(14, 10));
		panbox2.setLayout(new BorderLayout(14, 10));
		optball1 = new JRadioButton("Ball 1");
		optball2 = new JRadioButton("Ball 2");
		optball3 = new JRadioButton("Ball 3");
		optback1 = new JRadioButton("BackG 1");
		optback2 = new JRadioButton("BackG 2");
		optback3 = new JRadioButton("BackG 3");
		add(lblBall);
		lblBall.setForeground(Color.BLACK);
		lblBall.setFont(new Font("Ball", Font.BOLD, 16));
		lblBall.setBounds(x + 20, y + 170, w + 80, h);
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
//		imgback1 = new ImageIcon("Image/ball_45.png");
//		imgback2 = new ImageIcon("Image/ball_45.png");
//		imgback3 = new ImageIcon("Image/ball_45.png");
		txtPlayer1.setText(PongPanel.namePlayer1);
		txtPlayer2.setText(PongPanel.namePlayer2);
		// ..
		SaveNamePlayer();
		Act();
		lblinball.setIcon(imgball1);
		ActionListener action = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
		this.add(panbox1);
		// Location selected ball
		panbox1.add(optball1, BorderLayout.WEST);
		panbox1.add(optball2, BorderLayout.CENTER);
		panbox1.add(optball3, BorderLayout.EAST);
		panbox1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		panbox1.setSize(210, 30);
		panbox1.setLocation(110, 230);
		btnB.setBounds(x + 140, y + 400, w, h);
		
		this.add(panbox2);
		// Location selected ball
		panbox2.add(optback1, BorderLayout.WEST);
		panbox2.add(optback2, BorderLayout.CENTER);
		panbox2.add(optback3, BorderLayout.EAST);
		panbox2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		panbox2.setSize(250, 30);
		panbox2.setLocation(90, 370);
	}

	public void Act() {
		btnB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// if (e.getSource() == btnB) {
				// pongPanel.setVisible(true);
				if (optball1.isSelected() == true) {
					PongPanel.NumTypeBall = 0;
				} else if (optball2.isSelected() == true) {
					PongPanel.NumTypeBall = 1;
				} else if (optball3.isSelected() == true) {
					PongPanel.NumTypeBall = 2;
				}
				panbox1.setVisible(false);
				lblPlayer1.setVisible(false);
				lblPlayer2.setVisible(false);
				txtPlayer1.setVisible(false);
				txtPlayer2.setVisible(false);
				btnB.setVisible(false);
				PongPanel.namePlayer1 = txtPlayer1.getText();
				PongPanel.namePlayer2 = txtPlayer2.getText();
				dispose();
				// }
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
