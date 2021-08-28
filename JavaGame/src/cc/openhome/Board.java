package cc.openhome;

import java.io.*;
import java.util.*;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

class Scores{
	int value;
	JLabel text;
	Image image;
}	

class Problem {
	String mainText;
	String left;
	int [] dVL = new int [4];
	String right;
	int [] dVR = new int [4];
}

class ShowText extends Thread{
	JTextArea t0;
	JTextArea t1;
	JTextArea t2;
	String s0;
	String s1;
	String s2;
	public Boolean isContinue=true;
	public ShowText(JTextArea t0 , JTextArea t1 , JTextArea t2 , String s0 , String s1 , String s2){
		this.t0=t0;
		this.t1=t1;
		this.t2=t2;
		this.s0=s0;	
		this.s1=s1;	
		this.s2=s2;	
	}

	@Override
	public void run(){
		for(int i=0 ; i<s0.length() ; i++){
			if(!isContinue) break;
			t0.append(""+s0.charAt(i));
			try {
				sleep(150);
			}
			catch(InterruptedException e) {   
				System.out.println("sleep Exception !");
			}
		}

		for(int i=0 ; i<s1.length() ; i++){
			if(!isContinue) break;
			t1.append(""+s1.charAt(i));
			try {
				sleep(150);
			}
			catch(InterruptedException e) {   
				System.out.println("sleep Exception !");
			}
		}

		
		for(int i=0 ; i<s2.length() ; i++){
			if(!isContinue) break;
			t2.append(""+s2.charAt(i));
			try {
				sleep(150);
			}
			catch(InterruptedException e) {   
				System.out.println("sleep Exception !");
			}
		}
	}
}

public class Board extends JPanel implements ActionListener{

	ShowText s;
	Scores [] scores;
	Problem [] problems = new Problem[30];
	private int whichEvent=0;

	private JTextArea centralT;
	private JTextArea leftT;
	private JTextArea rightT;

	private Image [] gameOver=new Image[4];
	private Image win;
	
	
	private JButton startB;
	private JButton leftB;
        private JButton rightB;
	private int isGame=0;

	public Board(){

		initBoard();
	}

	private void initBoard(){

		initGame();
	}

	private void initGame(){
		scores = new Scores[4];
		for(int i=0 ; i<4 ; i++){
			scores[i]=new Scores();
		}

		setLayout(null);
		setPreferredSize(new Dimension(500, 700));       
		loadImage();
		
		try{
			loadEvent();
		}
		catch(IOException ex){
			ex.printStackTrace();
			System.out.println("IOException");
		}

		initValue();
		initLabel();
		initButton();
		initTextArea();
		startB.addActionListener(this);
		leftB.addActionListener(this);
		rightB.addActionListener(this);

		
		
//		repaint();

	}


	private void loadImage() {
		ImageIcon ii;
		for(int i=0 ; i<4 ; i++){
       			ii= new ImageIcon("image/"+i+".png");
			scores[i].image=ii.getImage();
		}
		for(int i=0 ; i<4 ; i++){
			ii=new ImageIcon("image/gameOver"+i+".png");
			gameOver[i]=ii.getImage();
		}
		ii=new ImageIcon("image/win.git");
		win=ii.getImage();

    	}

	private void loadEvent() throws IOException {

			FileReader fr = new FileReader("jj.txt");
			BufferedReader br = new BufferedReader(fr);
			int i=0;
			String [] sp;
			while (br.ready()) {
				problems[i]=new Problem();
				String tem;
				tem=br.readLine();
				problems[i].mainText=tem;
				//	System.out.println(p[i].mainText);

				tem=br.readLine();
				sp=tem.split("\\s+");
				problems[i].left=sp[0];
				//	System.out.println(p[i].left);

				for(int j=0 ; j<4 ; j++){
					problems[i].dVL[j]=Integer.valueOf(sp[j+1]);
					//				System.out.println(problems[i].dVL[j]);
				}

				tem=br.readLine();
				sp=tem.split("\\s+");
				problems[i].right=sp[0];
				//			System.out.println(p[i].right);
				for(int j=0 ; j<4 ; j++){
					problems[i].dVR[j]=Integer.valueOf(sp[j+1]);
					//				System.out.println(p[i].dVR[j]);
				}	
				i++;	
			}
			fr.close();
			br.close();
		

	}

	private void initValue(){
		for(int i=0 ; i<4 ; i++){
			scores[i].value=100;
		}
		
	}

	private void initButton(){
		startB=new JButton("開始遊戲");
		leftB  =new JButton("<--");
		rightB  =new JButton("-->");

		startB.setBounds(150,300,200,100);
		leftB.setBounds( 75,550,100, 50);	
		rightB.setBounds(325,550,100, 50);

		add(startB);
		add(leftB);
		add(rightB);


	}

	private void initLabel(){
		for(int i=0 ; i<4 ; i++){
			scores[i].text=new JLabel(Integer.toString(scores[i].value));
		}

		for(int i=0 ; i<4 ; i++){
			scores[i].text.setBounds(75+125*i,0,100,50);
		}	
		
		for(int i=0 ; i<4 ; i++){
			add(scores[i].text);
		}

	}

	private void initTextArea(){

		centralT = new JTextArea();
		centralT.setBounds(0,100,500,300);		
		centralT.setEditable(false);
		centralT.setLineWrap(true);//自動換行
		centralT.setFont(centralT.getFont().deriveFont(16f));//設定文字大小
				
		
		leftT = new JTextArea();
		leftT.setBounds(0,450,250,100);			
		leftT.setEditable(false);
		leftT.setLineWrap(true);
		leftT.setFont(leftT.getFont().deriveFont(16f));

		
		rightT = new JTextArea();
		rightT.setBounds(250,450,250,100);		
		rightT.setEditable(false);
		rightT.setLineWrap(true);
		rightT.setFont(rightT.getFont().deriveFont(16f));

		
		add(centralT);
		add(leftT);
		add(rightT);
	//	try{
			showText();
	//	}
	//	catch(InterruptedException e) {   
	//		System.out.println("sleep Exception !");
	//	}

	}

	private void showText()/* throws InterruptedException*/{
		centralT.setText("");
		leftT.setText("");
		rightT.setText("");
		s = new ShowText(centralT,leftT,rightT,problems[whichEvent].mainText,problems[whichEvent].left,problems[whichEvent].right);
		s.start();	

	

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		doDrawing(g);
	}

	

	private void doDrawing(Graphics g) {
		drawImage(g);	
	
	}	

	private void drawImage(Graphics g){
		if(isGame==1){
			paintInGame(g);
		}
		else
			if(isGame==-1){
				paintGameOver(g);	
			}
			else
				if(isGame==0){
					paintInit(g);	
				}
	}


	private void paintInGame(Graphics g){
		showValue();
	
		for(int i=0 ; i<4 ; i++){
			g.drawImage(scores[i].image , 125*i , 0 , null);
		}
	}


	private void paintGameOver(Graphics g){
		for(int i=0 ; i<4 ; i++){
			if(scores[i].value<0){
				g.drawImage(gameOver[i],0,0,null);	
				break;
			}		
		}
		if(whichEvent>23){
			g.drawImage(win,0,0,null);
		}
	}

	private void paintInit(Graphics g){
		clear();
		startB.setVisible(true);
	}


	@Override
 	public void actionPerformed(ActionEvent e){

		if(e.getSource() == leftB){
			for(int i=0 ; i<4 ; i++){
				scores[i].value+=problems[whichEvent].dVL[i];
			}	
			s.isContinue=false;		
			nextProblem();
		}
		if(e.getSource() == rightB){
			for(int i=0 ; i<4 ; i++){
				scores[i].value+=problems[whichEvent].dVR[i];
			}
			s.isContinue=false;		
			nextProblem();
		}	
		if(e.getSource() == startB){
			isGame=1;
			inGameComponent();
		}

		showValue();
		if(scores[0].value<0 || scores[1].value<0 || scores[2].value<0 || scores[3].value<0){
			s.isContinue=false;		
			isGame=-1;
			gameOverComponent();
		}
		repaint();

	}	

	private void nextProblem(){
		whichEvent++;	
		if(whichEvent>23){
			s.isContinue=false;		
			isGame=-1;
			gameOverComponent();
		}
		else{
			showText();
		}
	}


	private void showValue(){

		for(int i=0 ; i<4 ; i++){
			scores[i].text.setText( Integer.toString(scores[i].value));
		}

	}

	private void inGameComponent(){
		
		clear();
		for(int i=0 ; i<4 ; i++){
			scores[i].text.setVisible(true);
		}
		leftB.setVisible(true);
		rightB.setVisible(true);
		centralT.setVisible(true);
		leftT.setVisible(true);
		rightT.setVisible(true);

	}

	private void gameOverComponent(){	
		clear();	
	}


	
	private void clear(){
		for(int i=0 ; i<4 ; i++){
			scores[i].text.setVisible(false);
		}
		startB.setVisible(false);
		leftB.setVisible(false);
		rightB.setVisible(false);
		centralT.setVisible(false);
		leftT.setVisible(false);
		rightT.setVisible(false);
	}

		

}
