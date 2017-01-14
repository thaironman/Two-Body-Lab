package mainPack;

import javax.swing.*;

import tools.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoisCorpos{
	JFrame frame;
	MyDrawPanel drawPanel;
	JButton playButton, resetButton;
	JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9;
	JCheckBox checkV, checkA;
	JTextField txtM1, txtP1X, txtV1X, txtP1Y, txtV1Y, txtM2, txtP2X, txtV2X, txtP2Y, txtV2Y, txtDT;
	boolean play;
	Corpo c1, c2;
	Ponto p;
	Velocidade v;
	Aceleracao a;
	double forca;
	double x, y, rx, ry, moduloR;
	double m1, m2;
	double deltaT;
	double G = 6.67e-11;
	double raio1 = 30, raio2 = 10;
	int largura = 1200, altura = 900;
	
	public void go(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		drawPanel = new MyDrawPanel();
		playButton = new JButton("Play");
		playButton.addActionListener(new PlayListener());
		resetButton = new JButton("Reiniciar");
		resetButton.addActionListener(new ResetListener());
		
		checkV = new JCheckBox(" Velocidade ");
		checkA = new JCheckBox(" Aceleração ");
		txtM1 = new JTextField("1000000000");
		txtP1X = new JTextField("000600");
		txtP1Y = new JTextField("000350");
		txtV1X = new JTextField("000000");
		txtV1Y = new JTextField("000000");
		txtM2 = new JTextField("0000000001");
		txtP2X = new JTextField("0000440");
		txtP2Y = new JTextField("0000350");
		txtV2X = new JTextField("0000000");
		txtV2Y = new JTextField("-0.02");
		txtDT = new JTextField("0.002");
		
		p = new Ponto(600, 350);
		v = new Velocidade(0, 0);
		a = new Aceleracao(0, 0);
		m1 = 1000000000; 
		c1 = new Corpo(m1, p, v, a);
		p = new Ponto(440, 350);
		v = new Velocidade(0, 2*-10e-3);
		a = new Aceleracao(0, 0);
		m2 = 1; 
		c2 = new Corpo(m2, p, v, a);
		
		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
		panel2.add(new JLabel(" Mostrar Vetores: "));
		panel2.add(checkV);
		panel2.add(checkA);
		panel2.add(playButton);
		panel3 = new JPanel();
		panel3.add(new JLabel(" M1 "));  //Painel lateral Direito
		panel3.add(txtM1);
		panel4 = new JPanel();
		panel4.add(new JLabel(" P0  x "));
		panel4.add(txtP1X);
		panel4.add(new JLabel(" y "));
		panel4.add(txtP1Y);
		panel5 = new JPanel();
		panel5.add(new JLabel(" V0  x "));
		panel5.add(txtV1X);
		panel5.add(new JLabel(" y "));
		panel5.add(txtV1Y);
		panel6 = new JPanel();
		panel6.add(new JLabel(" M2 "));
		panel6.add(txtM2);
		panel7 = new JPanel();
		panel7.add(new JLabel(" P0  x "));
		panel7.add(txtP2X);
		panel7.add(new JLabel(" y "));
		panel7.add(txtP2Y);
		panel8 = new JPanel();
		panel8.add(new JLabel(" V0  x "));
		panel8.add(txtV2X);
		panel8.add(new JLabel(" y "));
		panel8.add(txtV2Y);
		panel9 = new JPanel();
		panel9.add(txtDT);
		
		panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.add(new JLabel(" Corpo 1 "));
		panel1.add(panel3);
		panel1.add(panel4);
		panel1.add(panel5);
		panel1.add(new JLabel(" Corpo 2 "));
		panel1.add(panel6);
		panel1.add(panel7);
		panel1.add(panel8);
		panel1.add(new JLabel(" DeltaT "));
		panel1.add(panel9);
		panel1.add(resetButton);
		
		frame.getContentPane().add(BorderLayout.EAST,panel1);
		
		frame.getContentPane().add(BorderLayout.SOUTH,panel2);
		frame.getContentPane().add(drawPanel);
		frame.setSize( largura, altura);
		frame.setVisible( true );
		
		play = false;
		deltaT = 2*10e-4;
		while (true){
			if(play){
				moduloR = Math.sqrt(Math.pow(c2.p.getX() - c1.p.getX(),2)+Math.pow(c2.p.getY() - c1.p.getY(),2));
				if( moduloR != 0){
					forca = (G*c1.getMassa()*c2.getMassa()/Math.pow(moduloR, 2));
					
					//aceleração
					rx = (c2.p.getX() - c1.p.getX())/moduloR; //vetor direção
					x = forca*rx/c1.getMassa();
					ry = (c2.p.getY() - c1.p.getY())/moduloR;
					y = forca*ry/c1.getMassa();
					c1.a.redefine(x, y);
					
					rx = -rx;
					x = forca*rx/c2.getMassa();
					ry = -ry;
					y = forca*ry/c2.getMassa();
					c2.a.redefine(x, y);
				}
				//velocidade
				x = c1.v.getX() + c1.a.getX()*deltaT;
				y = c1.v.getY() + c1.a.getY()*deltaT;
				c1.v.redefine(x, y);
				
				x = c2.v.getX() + c2.a.getX()*deltaT;
				y = c2.v.getY() + c2.a.getY()*deltaT;
				c2.v.redefine(x, y);
				//posição
				x = c1.p.getX() + c1.v.getX()*deltaT;
				y = c1.p.getY() + c1.v.getY()*deltaT;
				if (moduloR>(raio1+raio2)){
					c1.p.setX(x);
				} else{
					c1.colisao(c2);
				}
				if (moduloR>(raio1+raio2)){
					c1.p.setY(y);
				} else{
					c1.colisao(c2);
				}
				
				x = c2.p.getX() + c2.v.getX()*deltaT;
				y = c2.p.getY() + c2.v.getY()*deltaT;
				if (moduloR>(raio1+raio2)){
					c2.p.setX(x);
				} else{
					c1.colisao(c2);
				}
				if (moduloR>(raio1+raio2)){
					c2.p.setY(y);
				} else{
					c1.colisao(c2);
				}
			}
			drawPanel.repaint();
			
		}
	}
	public class MyDrawPanel extends JPanel{
		public void paintComponent( Graphics g ){
			g.setColor( Color.LIGHT_GRAY );
			g.fillRect( 0, 0, this.getWidth(), this.getHeight());
			g.setColor( Color.blue );
			g.fillOval((int) (c1.p.getX() - raio1), (int) (c1.p.getY()-raio1), (int) (2*raio1), (int) (2*raio1));
			g.setColor( Color.red );
			g.fillOval((int) (c2.p.getX() - raio2), (int) (c2.p.getY()-raio2), (int) (2*raio2), (int) (2*raio2));
			if (checkV.isSelected()){
				g.setColor( Color.black );
				g.drawLine((int) c1.p.getX(), (int) c1.p.getY(), (int) ((raio1*c1.v.getX()/c1.v.getModulo())+c1.p.getX()), (int) ((raio1*c1.v.getY()/c1.v.getModulo())+c1.p.getY()));
				g.drawLine((int) c2.p.getX(), (int) c2.p.getY(), (int) ((2*raio2*c2.v.getX()/c2.v.getModulo())+c2.p.getX()), (int) ((2*raio2*c2.v.getY()/c2.v.getModulo())+c1.p.getY()));
			}
			if (checkA.isSelected()){
				g.setColor( Color.red );
				g.drawLine((int) c1.p.getX(), (int) c1.p.getY(), (int) ((raio1*c1.a.getX()/c1.a.getModulo())+c1.p.getX()), (int) ((raio1*c1.a.getY()/c1.a.getModulo())+c1.p.getY()));
				g.setColor( Color.blue );
				g.drawLine((int) c2.p.getX(), (int) c2.p.getY(), (int) ((2*raio2*c2.a.getX()/c2.a.getModulo())+c2.p.getX()), (int) ((2*raio2*c2.a.getY()/c2.a.getModulo())+c2.p.getY()));
			}
			g.setColor( Color.black );
			g.drawString("Vetor F = G.m1.m2/r²", 30, 30);
		}
	}
	
	public class PlayListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			if (play){
				playButton.setText("Play");
				play = false;
			} else{
				playButton.setText("Stop");
				play = true;
			}
		}
	}
	
	public class ResetListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			c1.setMassa(Double.parseDouble(txtM1.getText()));
			c1.p.redefine(Double.parseDouble(txtP1X.getText()), Double.parseDouble(txtP1Y.getText()));
			c1.v.redefine(Double.parseDouble(txtV1X.getText()), Double.parseDouble(txtV1Y.getText()));
			c1.a.redefine(0, 0);
			c2.setMassa(Double.parseDouble(txtM2.getText()));
			c2.p.redefine(Double.parseDouble(txtP2X.getText()), Double.parseDouble(txtP2Y.getText()));
			c2.v.redefine(Double.parseDouble(txtV2X.getText()), Double.parseDouble(txtV2Y.getText()));
			c2.a.redefine(0, 0);
			deltaT = Double.parseDouble(txtDT.getText());
		}
	}
	
}
