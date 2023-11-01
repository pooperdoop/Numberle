import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.random.*;

public class RNG implements ActionListener{

	static JFrame frame = new JFrame("Guess The Number");
	static JLabel title = new JLabel("GUESS THE NUMBER");
	static JButton[] titleButtons = new JButton[2];
	static JPanel filler = new JPanel();
	static int yVal = 450; 
	
	public static void main(String[] args) {
		
		frame.setTitle("GTN");
		frame.setLayout(new FlowLayout(FlowLayout.CENTER,5000,50));
		frame.setExtendedState(frame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		
		filler.setBackground(Color.LIGHT_GRAY);
		filler.setPreferredSize(new Dimension(1000,1));
		frame.add(filler,BorderLayout.NORTH);
		
		title.setFont(new Font("GUMDROP", Font.BOLD,150));
		title.setForeground(Color.BLACK);
		frame.add(title);
		
		for(int i = 0; i<2; i++) {
			titleButtons[i] = new JButton();
			titleButtons[i].addActionListener(new RNG());
			titleButtons[i].setBackground(Color.gray);
			titleButtons[i].setFont(new Font("GUMDROP",Font.BOLD,100));
			titleButtons[i].setFocusable(false);
			titleButtons[i].setForeground(Color.black);
			titleButtons[i].setPreferredSize(new Dimension(600,300));
			frame.add(titleButtons[i]);
		}
		titleButtons[0].setText("Start");
		titleButtons[1].setText("Quit");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == titleButtons[1]) {
			System.exit(0);
		}
		
		if(e.getSource() == titleButtons[0]) {
			gameTab newTab = new gameTab();
			frame.dispose();
		}
	}
	
	public class gameTab extends JFrame implements ActionListener, KeyListener, MouseListener{
		
		boolean pauseToggle;
		boolean checkDigit;
		boolean hasListener = false;
		boolean gameTimeOn = false;
		boolean gameRunning = false;
		JLabel howToPlay = new JLabel("");
		JLabel cont = new JLabel("");
		JLabel finalLbl = new JLabel("");
		JPanel pausePanel = new JPanel();
		JPanel finalPanel = new JPanel();
		JButton uiButtons[] = new JButton[4];
		JButton exit = new JButton("EXIT");
		JLabel pauseTitle = new JLabel("GAME PAUSED");
		JLabel GTN = new JLabel("GUESS THE NUMBER");
		JLayeredPane mainPanels = new JLayeredPane();
		JPanel h2pPanel = new JPanel();
		JPanel gamePanel = new JPanel();
		JTextField[] guessFields = new JTextField[6];
		JLabel[] guessedFields = new JLabel[6]; 
		Character[] ans = new Character[5];
		Character[] ind = new Character[5];
		int first = 0;
		int sec;
		int min;
		int times = 1000;
		int fieldAt = 0;
		Integer pickedNum = 0;
		String text;
		String[] colors = new String[5];
		Timer time = new Timer(times, new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					TimeStart();
				}
						});
		Integer x;
		gameTab(){
			this.setTitle("GTN");
			this.setLayout(new BorderLayout());
			this.setExtendedState(this.MAXIMIZED_BOTH);
			this.setUndecorated(true);
			this.setResizable(false);
			this.setVisible(true);
			this.getContentPane().setBackground(Color.gray);
			this.add(mainPanels, BorderLayout.CENTER);
			mainPanels.setPreferredSize(new Dimension(1,1));
			mainPanels.setLayout(new OverlayLayout(mainPanels));
			mainPanels.setVisible(true);
			GTN.setFont(new Font("GUMDROP",Font.BOLD,150));
			
			finalPanel.setPreferredSize(new Dimension(1,1));
			finalPanel.setBackground(new Color(90,90,90));
			finalPanel.setLayout(new FlowLayout(FlowLayout.CENTER,1000,50));
			finalPanel.add(finalLbl);
			finalLbl.setFont(new Font("GUMDROP",Font.BOLD,150));
			finalPanel.setVisible(false);
			
			GridBagConstraints cons = new GridBagConstraints();
			h2pPanel.setPreferredSize(new Dimension(1,1));
			h2pPanel.setLayout(new GridBagLayout());
			cons.gridy = 1;
			cons.gridheight = 1;
			h2pPanel.add(cont,cons);
			h2pPanel.add(howToPlay);
			howToPlay.setFont(new Font("Comic Sans MS",Font.BOLD,45));
			cont.setFont(new Font("Comic Sans MS",Font.BOLD,45));
			h2pPanel.setBackground(Color.LIGHT_GRAY);
			
			gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER,1000,20));
			gamePanel.setPreferredSize(new Dimension(1,1));
			gamePanel.setBackground(Color.LIGHT_GRAY);
			gamePanel.setVisible(false);
			gamePanel.add(GTN);
			
			pausePanel.setPreferredSize(new Dimension(1,1));
			pausePanel.setBackground(new Color(255, 255, 0,100));
			pausePanel.addKeyListener(this);
			pauseTitle.setForeground(Color.red);
			pausePanel.setLayout(new FlowLayout(FlowLayout.CENTER,5000,50));
			mainPanels.add(finalPanel);
			mainPanels.add(pausePanel);
			mainPanels.add(h2pPanel);
			mainPanels.add(gamePanel);
			pausePanel.setVisible(false);		
			pauseTitle.setFont(new Font("GUMDROP",Font.ITALIC,150));
			pausePanel.add(pauseTitle);
			
			for(int j = 0; j<6;j++) {
				guessFields[j] = new JTextField();
				guessedFields[j] = new JLabel();
				guessFields[j].setPreferredSize(new Dimension(400,120));
//				guessFields[j].setBorder(javax.swing.BorderFactory.createEmptyBorder());
				guessFields[j].addKeyListener(this);
				guessFields[j].setBackground(Color.gray);
				guessFields[j].setFont(new Font("Comic Sans MS",Font.BOLD,120));
				guessFields[j].setCaretColor(new Color(1,1,1,1));
				guessedFields[j].setForeground(Color.black);
				guessedFields[j].setPreferredSize(new Dimension(400,120));
				guessedFields[j].setFont(new Font("Comic Sans MS",Font.BOLD,120));
				guessedFields[j].setVisible(false);
				gamePanel.add(guessedFields[j]);
				gamePanel.add(guessFields[j]);

			}
			
			for(int i = 0; i<2; i++) {
				uiButtons[i] = new JButton();
				uiButtons[i].addActionListener(this);
				uiButtons[i].setBackground(new Color(196, 180, 84));
				uiButtons[i].setFont(new Font("GUMDROP",Font.BOLD,100));
				uiButtons[i].setFocusable(false);
				uiButtons[i].setForeground(Color.RED);
				uiButtons[i].setPreferredSize(new Dimension(600,300));
				pausePanel.add(uiButtons[i]);
				finalPanel.add(uiButtons[i]);
			}
			uiButtons[0].setText("Resume");
			uiButtons[1].setText("Exit");
			time.start();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == uiButtons[0]) {
				if(gameRunning == true) {
				pausePanel.setVisible(false);
				for(int i = 0;i<6;i++) {
					guessFields[i].setEditable(true);
					guessFields[i].setEnabled(true);
					}
				}
				else {
					reset();
					}
			}
			if(e.getSource() == uiButtons[1]) {
				System.exit(0);
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			for(int i = 0; i<6;i++) {
				if(guessFields[i].getText().length() >= 5) {
					e.consume();
				}
				for(int j = 0;j < guessFields[i].getText().length(); j++) {
					boolean check = Character.isDigit(guessFields[i].getText().charAt(j));
					if(check == false) {
						e.consume();
					}
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_ESCAPE) {
				PauseMenu();
			}
			else if(key == KeyEvent.VK_ENTER) {
				checkNumCount();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(hasListener == false) {
		this.addKeyListener(this);
		h2pPanel.setVisible(false);
		gamePanel.setVisible(true);
		hasListener =  true;
		gameTimeOn = true;
		gameRunning = true;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(hasListener == false) {
		this.addKeyListener(this);
		h2pPanel.setVisible(false);
		gamePanel.setVisible(true);
		hasListener =  true;
		gameRunning = true;
		}
	
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
		}
	
	public void addingListener() {
		this.addMouseListener(this);
		
	}
	
	public void createNum() {
		Random r = new Random();
		int[] test = {1,2,3,4,5,6,7,8,9,0};
		
		for(int i = 0;i<5;i++) {
			int index = r.nextInt(10-i);	
			int digit = test[index];		
			test[index] = test[9-i];
			pickedNum = pickedNum * 10+digit;
				
		}
		if(String.valueOf(pickedNum).length() != 5) {
			pickedNum = pickedNum*10;
		}
		System.out.println(pickedNum);
		x = pickedNum;
		}
	
	public void PauseMenu() {
		
		if(pausePanel.isVisible()) {
			pausePanel.setVisible(false);
			for(int i = 0;i<6;i++) {
				guessFields[i].setEditable(true);
				guessFields[i].setEnabled(true);
			}
		}
			else {
				for(int i = 0; i<2; i++) {
					pausePanel.add(uiButtons[i]);
				}
			pausePanel.setVisible(true);
			this.requestFocus();
			for(int i = 0;i<6;i++) {
				guessFields[i].setEditable(false);
				guessFields[i].setEnabled(false);
			}
			System.out.println("HELLO");
				
			}
		}
	
	public void TimeStart() {
		
		first += 1000;
		sec = (first/1000)%60;
		min = (first/60000)%60;
		if(sec == 1 && gameTimeOn == false) {
			howToPlay.setText("<html>The game generates a five digit number which you have to guess.<br/>You have five chances to guess and if you guess<br/>"
		+ "any of the numbers you are<b/> given hints whether what the number is.<br/>If you fail all five chances, you lose.Good luck have fun.<html>");
		}
		if(sec == 4 && gameTimeOn == false) {
			addingListener();
			createNum();
			cont.setText("Press Anywhere on the Screen to Continue");		
			time.stop();
			}			
		}
	public void checkNumCount() {
			if(guessFields[fieldAt].getText().length() != 5 ) {
				JOptionPane.showMessageDialog(null, "Kulang");
			}
			else {
				for(int i =0;i<5;i++) {
					boolean digits = Character.isDigit(guessFields[fieldAt].getText().charAt(i));
					if(digits != true) {
						JOptionPane.showMessageDialog(null, "the last character is not a digit");
						checkDigit = false;
					}
					else {
						checkDigit = true;
					}
				}
				if(checkDigit == true) {
				checkCorrectPos();
				checkCorrectAns();
				fieldAt++;
				if(fieldAt == 6) {
					this.requestFocus();
					}
				}
			}
		}
	
	public void checkCorrectPos() {
		text = guessFields[fieldAt].getText();
		for(int i = 0;i<5;i++) {
			colors[i] = "gray";
		}
		for(int i = 0; i<ind.length;i++) {
			ind[i] = text.charAt(i);
			ans[i] = pickedNum.toString().charAt(i);
		}
				for(int k =0; k<5;k++) {
					for(int j = 0; j<5;j++) {
						if(ind[k].equals(ans[j])) {
							colors[k] = "orange";
					}
				}
			}
				for(int i = 0;i<5;i++) {
				if(ind[i].equals(ans[i])) {
					colors[i] = "green";
					for(int j = 0; j<5;j++) {
						if(ind[i].equals(ind[j]) && !colors[j].equals("green")) {
							colors[j] = "gray";
						}
					}
				}
			}
			fieldToLabel();
		}
	
	public void checkCorrectAns() {
		gameRunning = false;
			if(text.equals(x.toString()) && fieldAt <= 5) {	
//				JOptionPane.showMessageDialog(null, "u win");
				for(int i = 0; i<2; i++) {
					finalPanel.add(uiButtons[i]);
					uiButtons[i].setBackground(Color.gray);
					uiButtons[i].setForeground(Color.black);
				}
				finalPanel.setVisible(true);
				finalPanel.setBackground(new Color(255,255,255,100));
				finalLbl.setText("YOU WIN");
				finalLbl.setForeground(Color.black);;
				pausePanel.setEnabled(false);
				uiButtons[0].setText("Play Again");
				for(int i = 0;i<6;i++) {
					guessFields[i].setEditable(false);
					guessFields[i].setEnabled(false);
				}
//				System.exit(0);
			}
			else if (fieldAt > 4 &&! text.equals(x.toString())) {
				for(int i = 0; i<2; i++) {
					finalPanel.add(uiButtons[i]);
					uiButtons[i].setBackground(Color.gray);
					uiButtons[i].setForeground(Color.black);
				}
				finalPanel.setVisible(true);
				finalLbl.setText("YOU LOSE");
				finalLbl.setForeground(Color.white);;
				finalPanel.setBackground(new Color(0,0,0,100));;
				pausePanel.setEnabled(false);
				uiButtons[0].setText("Play Again");
				for(int i = 0;i<6;i++) {
					guessFields[i].setEditable(false);
					guessFields[i].setEnabled(false);
			}
		
		}	
	}
	public void fieldToLabel() {
		guessedFields[fieldAt].setText("<html><font color = "+colors[0]+">"+ind[0]+"</font><font color ="+colors[1]+">"+ind[1]+""
				+ "</font><font color ="+colors[2]+">"+ind[2]+"</font><font color ="+colors[3]+">"+ind[3]+"</font><font color ="+colors[4]+">"+ind[4]+"</font></html>");
		guessedFields[fieldAt].setVisible(true);
		guessFields[fieldAt].setVisible(false);
		guessFields[fieldAt].setText("");
		}
	public void reset() {
		pickedNum =0;
		createNum();
		for(int i = 0;i<6;i++) {
			guessedFields[i].setText("");
			guessedFields[i].setVisible(false);
			guessFields[i].setVisible(true);
			guessFields[i].setEditable(true);
			guessFields[i].setEnabled(true);
			pausePanel.setEnabled(true);
		}
		for(int j = 0; j<2; j++) {
			uiButtons[j].setBackground(new Color(196, 180, 84));
			uiButtons[j].setForeground(Color.RED);
		}
		uiButtons[0].setText("Resume");
		fieldAt = 0;
		finalPanel.setVisible(false);
		gameRunning = true;
		}
	}
}
