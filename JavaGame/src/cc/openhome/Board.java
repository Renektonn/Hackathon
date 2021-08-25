package cc.openhome;

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

public class Board extends JPanel implements ActionListener{

	Scores wealth;
	Scores status;
	Scores opinion;
	Scores mood;

	private Image gameOver;
	
	private JButton start;
	private JButton innocent;
	private JButton misdemeanor;
        private JButton felony;
	private int isGame=0;

	public Board(){

		initBoard();
	}

	private void initBoard(){

		initGame();
	}

	private void initGame(){
		wealth = new Scores();
		status = new Scores();
		opinion = new Scores();
		mood = new Scores();

		setLayout(null);
		setPreferredSize(new Dimension(500, 700));       
		loadImage();
		
		
		initValue();
		initLabel();
		initButton();
		start.addActionListener(this);
		innocent.addActionListener(this);
		misdemeanor.addActionListener(this);
		felony.addActionListener(this);

		
		
//		repaint();

	}

	private void loadImage() {

       		ImageIcon ii= new ImageIcon("image/money.png");
	        wealth.image = ii.getImage();
       		ii= new ImageIcon("image/status.png");
	        status.image = ii.getImage();
       		ii= new ImageIcon("image/opinion.png");
	        opinion.image = ii.getImage();
       		ii= new ImageIcon("image/mood.png");
	        mood.image = ii.getImage();
		ii=new ImageIcon("image/gameOver.png");
		gameOver=ii.getImage();

    	}

	private void initValue(){
		wealth.value=0;
		opinion.value=0;
		status.value=0;
                mood.value=0;
		
	}

	private void initButton(){
		start=new JButton("開始遊戲");
		innocent  =new JButton("無罪");
		misdemeanor  =new JButton("輕判");
		felony  =new JButton("重判");

		start.setBounds(150,300,200,100);
		innocent.setBounds( 75,550,100, 50);	
		misdemeanor.setBounds(200,550,100, 50);
		felony.setBounds(325,550,100, 50);

		add(start);
		add(innocent);
		add(misdemeanor);
		add(felony);


	}

	private void initLabel(){
		wealth.text =new JLabel(Integer.toString(wealth.value    )  );
		status.text =new JLabel(Integer.toString(status.value    )  );
		opinion.text=new JLabel(Integer.toString(opinion.value   )  );
		mood.text   =new JLabel(Integer.toString(mood.value      )  );

		wealth .text.setBounds( 75,0,100,50);
		status .text.setBounds(200,0,100,50);
		opinion.text.setBounds(325,0,100,50);
		mood   .text.setBounds(450,0,100,50);
		
		add(wealth .text);	
		add(status .text);	
		add(opinion.text);	
		add(mood   .text);

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
	
		g.drawImage(wealth .image ,   0 , 0 , null);
		g.drawImage(status .image , 125 , 0 , null);
		g.drawImage(opinion.image , 250 , 0 , null);
		g.drawImage(mood   .image , 375 , 0 , null);
	}


	private void paintGameOver(Graphics g){
		g.drawImage(gameOver,0,0,null);	
	}

	private void paintInit(Graphics g){
		clear();
		start.setVisible(true);
	}


	@Override
 	public void actionPerformed(ActionEvent e){

		if(e.getSource() == misdemeanor){
			wealth.value=0;

		}
		if(e.getSource() == innocent){
			wealth.value=777;
		}
		if(e.getSource() == felony){

		}	
		if(e.getSource() == start){
			isGame=1;
			inGameComponent();
		}

		showValue();
		if(wealth.value==777){
			isGame=-1;
			gameOverComponent();
		}
		repaint();

	}	


	private void showValue(){

		wealth .text.setText( Integer.toString(wealth .value   ));	
		opinion.text.setText( Integer.toString(opinion.value   ));
		status .text.setText( Integer.toString(status .value   ));
		mood   .text.setText( Integer.toString(mood   .value   ));

	}

	private void inGameComponent(){
		
		clear();
		wealth .text.setVisible(true);
		status .text.setVisible(true);
		opinion.text.setVisible(true);
		mood   .text.setVisible(true);
		innocent.setVisible(true);
		misdemeanor.setVisible(true);
		felony.setVisible(true);

	}

	private void gameOverComponent(){	
		clear();	
	}


	
	private void clear(){
		wealth .text.setVisible(false);
		status .text.setVisible(false);
		opinion.text.setVisible(false);
		mood   .text.setVisible(false);
		start.setVisible(false);
		innocent.setVisible(false);
		misdemeanor.setVisible(false);
		felony.setVisible(false);
	}

		

}
