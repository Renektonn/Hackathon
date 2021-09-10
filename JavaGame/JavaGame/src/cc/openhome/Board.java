package cc.openhome;

import javax.imageio.ImageIO;
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
	public boolean isContinue=true;
	public boolean skip=false;
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
			if(skip){
				t0.append(s0.substring(i,s0.length() ) );
				break;
			}
			t0.append(""+s0.charAt(i));
			try {
				sleep(100);
			}
			catch(InterruptedException e) {   
				System.out.println("sleep Exception !");
			}
		}

		for(int i=0 ; i<s1.length() ; i++){
			if(!isContinue) break;
			if(skip){
				t1.append(s1.substring(i,s1.length() ) );
				break;
			}
			t1.append(""+s1.charAt(i));
			try {
				sleep(100);
			}
			catch(InterruptedException e) {   
				System.out.println("sleep Exception !");
			}
		}

		
		for(int i=0 ; i<s2.length() ; i++){
			if(!isContinue) break;
			if(skip){
				t2.append(s2.substring(i,s2.length() ) );
				break;
			}
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

public class Board extends JPanel implements ActionListener,MouseListener{

	ShowText s;
	Scores [] scores = new Scores[4];
	Problem [] problems = new Problem[30];

	private String path=System.getProperty("user.dir");	

	private int whichProblem;
	private int allProblem;

	private int isGame;

	private JTextArea centralT;
	private JTextArea leftT;
	private JTextArea rightT;

	private ImageIcon [] button = new ImageIcon[4];
	private JLabel cover;
	private JLabel [] gameOver=new JLabel[11];
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

		try{
			loadImage();
		}

		catch(IOException ex){
			ex.printStackTrace();
			System.out.println("IOException");
		}

		try{
			loadProblems();
		}
		catch(IOException ex){
			ex.printStackTrace();
			System.out.println("IOException");
		}
		problemsBecomeRandom();

		initValue();
		initLabel();
		initButton();
		initTextArea();
		addMouseListener(this);
		startB.addActionListener(this);
		leftB.addActionListener(this);
		rightB.addActionListener(this);
		restartB.addActionListener(this);

		
		initGameComponent();	
		

	}


	private void loadImage() throws IOException{
		ImageIcon ii;
		InputStream is;
		for(int i=0 ; i<4 ; i++){	
			is = this.getClass().getResourceAsStream(i+".png");
			ii=new ImageIcon(ImageIO.read(is) ) ;

			scores[i].image=new JLabel(ii);
		}
		for(int i=0 ; i<11 ; i++){
			is = this.getClass().getResourceAsStream("gameOver"+i+".png");
			ii=new ImageIcon(ImageIO.read(is) ) ;

			gameOver[i]=new JLabel(ii);
		}
		for(int i=0 ; i<4 ; i++){
			is = this.getClass().getResourceAsStream("button"+i+".png");
			ii=new ImageIcon(ImageIO.read(is) ) ;

			button[i]=ii;
		}

		is = this.getClass().getResourceAsStream("cover.png");
		ii=new ImageIcon(ImageIO.read(is) ) ;
		cover=new JLabel(ii);

		is = this.getClass().getResourceAsStream("win.gif");
		ii=new ImageIcon(ImageIO.read(is) ) ;
		win=new JLabel(ii);

    	}

	private void loadProblems() throws IOException {
		System.setProperty("file.encoding", "UTF-8");

		InputStream is = this.getClass().getResourceAsStream("jj.txt");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
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
//								System.out.println(problems[i].dVL[j]);
			}

			tem=br.readLine();
			sp=tem.split("\\s+");
			problems[i].right=sp[0];
			//			System.out.println(p[i].right);
			for(int j=0 ; j<4 ; j++){
				problems[i].dVR[j]=Integer.valueOf(sp[j+1]);
//								System.out.println(problems[i].dVR[j]);
			}	
			i++;	
		}
		allProblem=i;

		is.close();
		isr.close();
		br.close();


	}

	private void problemsBecomeRandom(){
		int [] random=new int [allProblem];
		for(int i=allProblem-1 ; i>=0 ; i--){
			int rand=(int) (Math.random()*(i+1));//[0,i+1)==[0,i]
			Problem tem = problems[i];
			problems[i]=problems[rand];
			problems[rand]=tem;

		}
	}

//	private void swapP(Problem a , Problem b){
//		
//	}

	private void initScores(){
		
		for(int i=0 ; i<4 ; i++){
			scores[i]=new Scores();
		}

	}

	private void initValue(){
		for(int i=0 ; i<4 ; i++){
			scores[i].value=50;
		}
		whichProblem=0;
		
	}

	private void initButton(){
		startB=new JButton();
		leftB  =new JButton();
		rightB  =new JButton();
		restartB=new JButton();

		startB.setIcon(button[0]);
		leftB.setIcon(button[1]);
		rightB.setIcon(button[2]);
		restartB.setIcon(button[3]);

		startB.setBounds(150,400,200,100);
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


		cover.setBounds(0,0,500,700);
		add(cover);

		win.setBounds(75,100,350,400);	
		add(win);
		for(int i=0 ; i<11 ; i++){
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
		centralT.setFont(centralT.getFont().deriveFont(23f));//設定文字大小
				
		
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
	public void mouseClicked(MouseEvent e) {
		if(isGame==1){
			s.skip=true;
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
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
			problemsBecomeRandom();
			inGameComponent();
		}

		showValue();
		if(scores[0].value<=0 || scores[1].value<=0 || scores[2].value<=0 || scores[3].value<=0){
			s.isContinue=false;		
			gameOverComponent();
		}


	}	

	private void nextProblem(){
		whichProblem++;	
		if(!(whichProblem<allProblem)){
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
		isGame=0;
		clear();
		cover.setVisible(true);
		startB.setVisible(true);
	}

	private void inGameComponent(){
		isGame=1;	
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
		isGame=-1;
		clear();	
		for(int i=0 ; i<4 ; i++){
			scores[i].text.setVisible(true);
			scores[i].image.setVisible(true);
		}
		
		boolean [] judge = new boolean [4];
		
		for(int i=0 ; i<4 ; i++){	
			if(scores[i].value<=0){
				judge[i]=true;
			}		
		}

		if(judge[0] && judge[1] && judge[3]){
			gameOver[10].setVisible(true);
		}
		else{
			for(int i=0,count=4 ; i<3 ; i++){
				for(int j=i+1 ; j<4 ; j++){
					if(judge[i] && judge[j]){

						gameOver[count].setVisible(true);	
						restartB.setVisible(true);
						return ;
					}
					count++;
				}
			}
			for(int i=0 ; i<4 ; i++){	
				if(judge[i]){
					gameOver[i].setVisible(true);
				}
			}
		}

		if(!(whichProblem<allProblem)){
			win.setVisible(true);
		}

		restartB.setVisible(true);
	}


	
	private void clear(){
		for(int i=0 ; i<4 ; i++){
			scores[i].text.setVisible(false);
			scores[i].image.setVisible(false);
		}
		for(int i=0 ; i<11 ; i++){
			gameOver[i].setVisible(false);
		}
		startB.setVisible(false);
		leftB.setVisible(false);
		rightB.setVisible(false);
		restartB.setVisible(false);
		centralT.setVisible(false);
		leftT.setVisible(false);
		rightT.setVisible(false);
		cover.setVisible(false);
		win.setVisible(false);
	}

		

}
