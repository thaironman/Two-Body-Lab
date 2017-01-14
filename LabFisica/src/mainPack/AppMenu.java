package mainPack;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AppMenu {
	JFrame frame;
	DoisCorpos doisC;
	JButton graviButton;
	
	public void go(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		doisC = new DoisCorpos();
		graviButton = new JButton("Gravitação");
		graviButton.addActionListener(new graviListener());
		frame.getContentPane().add(BorderLayout.NORTH, new JLabel("Laboratóro de Física"));
		frame.getContentPane().add(graviButton);
		frame.setSize( 200, 300);
		frame.setVisible( true );
	}
	
	public class graviListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			doisC.go();
		}
	}
}
