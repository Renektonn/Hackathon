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
	JLabel image;
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
	Scores [] scores = new Scores[4];
	Problem [] problems = new Problem[30];
	private int whichProblem;

	private JTextArea centralT;
	private JTextArea leftT;
	private JTextArea rightT;

	private JLabel [] gameOver=new JLabel[4];
	private JLabel win;
	
	
	private JButton startB;
	private JButton leftB;
        private JButton rightB;
	private JButton restartB;

	public Board(){

		initBoard();
	}

	private void initBoard(){

		initGame();
	}

	private void initGame(){
		
		

		setLayout(null);
		setPreferredSize(new Dimension(500, 700));       
		initScores();
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
		restartB.addActionListener(this);
		
		
		initGameComponent();	
		

	}


	private void loadImage() {
		ImageIcon ii;
		for(int i=0 ; i<4 ; i++){
       			ii= new ImageIcon("image/"+i+".png");
			scores[i].image=new JLabel(ii);
		}
		for(int i=0 ; i<4 ; i++){
			ii=new ImageIcon("image/gameOver"+i+".png");
			gameOver[i]=new JLabel(ii);
		}
		ii=new ImageIcon("image/win.gif") ;
		win=new JLabel(ii);

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

	private void initScores(){
		
		for(int i=0 ; i<4 ; i++){
			scores[i]=new Scores();
		}

	}

	private void initValue(){
		for(int i=0 ; i<4 ; i++){
			scores[i].value=100;
		}
		whichProblem=0;
		
	}

	private void initButton(){
		startB=new JButton("開始遊戲");
		leftB  =new JButton("<--");
		rightB  =new JButton("-->");
		restartB=new JButton("重新開始");

		startB.setBounds(150,300,200,100);
		leftB.setBounds( 75,550,100, 50);	
		rightB.setBounds(325,550,100, 50);
		restartB.setBounds(200,550,100,50);

		add(startB);
		add(leftB);
		add(rightB);
		add(restartB);


	}

	private void initLabel(){

		for(int i=0 ; i<4 ; i++){
			scores[i].text=new JLabel();
			scores[i].text.setBounds(75+125*i,0,100,50);
			add(scores[i].text);
		}


		win.setBounds(75,100,350,400);	
		add(win);
		for(int i=0 ; i<4 ; i++){
			gameOver[i].setBounds(75,100,350,400);
			add(gameOver[i]);
		}

		for(int i=0 ; i<4 ; i++){
			scores[i].image.setBounds(125*i,0,50,50);
			add(scores[i].image);
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

	}

	private void showText(){

		centralT.setText("");
		leftT.setText("");
		rightT.setText("");
		s = new ShowText(centralT,leftT,rightT,problems[whichProblem].mainText,problems[whichProblem].left,problems[whichProblem].right);
		s.start();	

	

	}




	@Override
 	public void actionPerformed(ActionEvent e){

		if(e.getSource() == leftB){
			for(int i=0 ; i<4 ; i++){
				scores[i].value+=problems[whichProblem].dVL[i];
			}	
			s.isContinue=false;		
			nextProblem();
		}
		if(e.getSource() == rightB){
			for(int i=0 ; i<4 ; i++){
				scores[i].value+=problems[whichProblem].dVR[i];
			}
			s.isContinue=false;		
			nextProblem();
		}	
		if(e.getSource() == startB){
			inGameComponent();
		}
		if(e.getSource() == restartB){
			clear();
			initValue();
			inGameComponent();
		}

		showValue();
		if(scores[0].value<0 || scores[1].value<0 || scores[2].value<0 || scores[3].value<0){
			s.isContinue=false;		
			gameOverComponent();
		}


	}	

	private void nextProblem(){
		whichProblem++;	
		if(whichProblem>23){
			s.isContinue=false;		
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

	private void initGameComponent(){
		clear();
		startB.setVisible(true);
	}

	private void inGameComponent(){
		
		clear();
		for(int i=0 ; i<4 ; i++){
			scores[i].text.setVisible(true);
			scores[i].image.setVisible(true);
		}
		leftB.setVisible(true);
		rightB.setVisible(true);
		centralT.setVisible(true);
		leftT.setVisible(true);
		rightT.setVisible(true);
		showValue();	
		showText();

	}

	private void gameOverComponent(){	
		clear();	
		for(int i=0 ; i<4 ; i++){
			scores[i].text.setVisible(true);
			scores[i].image.setVisible(true);
		}
		
		for(int i=0 ; i<4 ; i++){
			
			if(scores[i].value<0){
				gameOver[i].setVisible(true);	
				break;
			}		
		}

		if(whichProblem>23){
			win.setVisible(true);
		}

		restartB.setVisible(true);
	}


	
	private void clear(){
		for(int i=0 ; i<4 ; i++){
			scores[i].text.setVisible(false);
			scores[i].image.setVisible(false);
		}
		for(int i=0 ; i<4 ; i++){
			gameOver[i].setVisible(false);
		}
		startB.setVisible(false);
		leftB.setVisible(false);
		rightB.setVisible(false);
		restartB.setVisible(false);
		centralT.setVisible(false);
		leftT.setVisible(false);
		rightT.setVisible(false);
		win.setVisible(false);
	}

		

}
