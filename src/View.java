import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URLDecoder;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class View extends JFrame{

	private static final long serialVersionUID = 6554910010207491079L;

	public View(Main main){
		this.main = main;
		setSize(400, 400);
		setTitle("Snijdende Cirkels");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		ImageIcon img = new ImageIcon("C:\\Users\\Mattias\\Documents\\naamloos.png");
		setIconImage(img.getImage());

		getBtnVoegCirkelsToe().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {		
				try {
					JFileChooser fc = new JFileChooser(new File(URLDecoder.decode(getClass().getResource("").getPath(), "UTF-8")).getParentFile());
					File file = null;

					int returnVal = fc.showOpenDialog(View.this);

					if (returnVal == JFileChooser.APPROVE_OPTION)
						file = fc.getSelectedFile();

					getMain().vervangCirkels(getMain().readCircles(file));

					tekenCirkels();

				} catch (Exception exc){
				}
			}

		});

		getBtnRunAlgoritme().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String a = JOptionPane.showInputDialog(null,"Welk algoritme?");
				if (a != null)
					getMain().setAlgoritmeVersie(Integer.valueOf(a));

				getMain().runAlgoritme();
				repaint();
			}

		});

		getBtnSetMaxStraal().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String a = JOptionPane.showInputDialog(null,"Welke maximale straal (tussen 0 en 1)");
				if (a != null)
					getMain().deler = 1/Double.valueOf(a);
			}

		});
		
		getBtnGenereerCirkel().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String a = JOptionPane.showInputDialog(null,"Hoeveel cirkels?");
				if (a != null)
					getMain().vervangCirkels(Main.generateCircles(Integer.valueOf(a)));
				repaint();
			}

		});

		getBtnWriteSnijpunten().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc;
				try {
					fc = new JFileChooser(new File(URLDecoder.decode(getClass().getResource("").getPath(), "UTF-8")).getParentFile());
					File file = null;

					int returnVal = fc.showOpenDialog(View.this);

					if (returnVal == JFileChooser.APPROVE_OPTION){
						file = fc.getSelectedFile();

						if (file.exists() && !file.delete())
							System.err.println("File couldn't be created");

						if (!file.createNewFile())
							System.err.println("File couldn't be created");
						else
							getMain().writeSnijpunten(file);
					}
				} catch (Exception e) {
				}
			}

		});

		setLayout(new BorderLayout());

		getNorthPanel().setLayout(new FlowLayout(FlowLayout.LEFT));
		getNorthPanel().add(getBtnVoegCirkelsToe());
		getNorthPanel().add(getBtnRunAlgoritme());
		getNorthPanel().add(getBtnGenereerCirkel());
		getNorthPanel().add(getBtnWriteSnijpunten());
		getNorthPanel().add(getBtnSetMaxStraal());

		getTekenPanel().setBackground(Color.WHITE);
		getTekenPanel().setPreferredSize(new Dimension(SCHAAL,SCHAAL));
		getTekenPanel().setMinimumSize(new Dimension(SCHAAL,SCHAAL));

		add(getNorthPanel(),BorderLayout.NORTH);
		add(getTekenPanel(),BorderLayout.CENTER);
		setVisible(true);
		this.pack();
	}

	public JButton getBtnGenereerCirkel() {
		return btnGenereerCirkel;
	}

	public void setBtnGenereerCirkel(JButton btnGenereerCirkel) {
		this.btnGenereerCirkel = btnGenereerCirkel;
	}

	public JButton getBtnVoegCirkelsToe() {
		return btnVoegCirkelsToe;
	}

	public void setBtnVoegCirkelsToe(JButton btnVoegCirkelsToe) {
		this.btnVoegCirkelsToe = btnVoegCirkelsToe;
	}

	public JButton getBtnRunAlgoritme() {
		return btnRunAlgoritme;
	}

	public void setBtnRunAlgoritme(JButton btnRunAlgoritme) {
		this.btnRunAlgoritme = btnRunAlgoritme;
	}


	public JButton getBtnWriteSnijpunten() {
		return btnWriteSnijpunten;
	}

	public void setBtnWriteSnijpunten(JButton btnWriteSnijpunten) {
		this.btnWriteSnijpunten = btnWriteSnijpunten;
	}

	public JButton getBtnSetMaxStraal() {
		return btnSetMaxStraal;
	}
	
	private JButton btnVoegCirkelsToe = new JButton("Lees bestand in"), btnRunAlgoritme = new JButton("Run"),
			btnGenereerCirkel = new JButton("Genereer cirkels"),
			btnWriteSnijpunten = new JButton("Snijpunten opslaan"),
			btnSetMaxStraal = new JButton("Max straal?");

	public JPanel getTekenPanel() {
		return tekenPanel;
	}

	public void setTekenPanel(JPanel tekenPanel) {
		this.tekenPanel = tekenPanel;
	}

	public JPanel getNorthPanel() {
		return northPanel;
	}

	public void setNorthPanel(JPanel northPanel) {
		this.northPanel = northPanel;
	}

	private JPanel northPanel = new JPanel(),tekenPanel = new JPanel(){
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {
			super.paintComponents(g);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());

			for (Cirkel cirkel : getMain().getCirkels()){
				int x = (int) ((cirkel.getMiddelpunt().getX()-cirkel.getStraal())*SCHAAL);
				int y = (int) ((cirkel.getMiddelpunt().getY()-cirkel.getStraal())*SCHAAL);
				g.setColor(Color.BLUE);
				g.drawArc(x, y, (int)(SCHAAL*2*cirkel.getStraal()), (int)(2*SCHAAL*cirkel.getStraal()),0,360);
				g.setColor(Color.BLACK);
				if(getMain().getCirkels().size() < 10)
					drawString(g, "(" + (double)Math.round(cirkel.getMiddelpunt().getX()*1000)/1000 + ", " + (double)Math.round(cirkel.getMiddelpunt().getY()*1000)/1000 + ")\nr: " + (double)Math.round(cirkel.getStraal()*1000)/1000, (int)(cirkel.getMiddelpunt().getX()*SCHAAL), (int)(cirkel.getMiddelpunt().getY()*SCHAAL));
			}

			if(getMain().getSnijpunten() != null && getMain().getSnijpunten().size() < 600000)
				for(Punt snijpunt : getMain().getSnijpunten()){
					g.setColor(Color.RED);
					g.drawString("x", (int)(snijpunt.getX()*SCHAAL), (int)(snijpunt.getY()*SCHAAL));
				}
		};
	};

	private void drawString(Graphics g, String text, int x, int y) {
		y -= g.getFontMetrics().getHeight() * text.split("\n").length / 2;
		for (String line : text.split("\n")){
			int stringLen = (int) g.getFontMetrics().getStringBounds(line, g).getWidth();  
			g.drawString(line, x-stringLen/2, y += g.getFontMetrics().getHeight());
		}
	}


	public Main getMain() {
		return main;
	}

	private final Main main;

	private void tekenCirkels(){
		getTekenPanel().repaint();
	}

	private final int SCHAAL = 600;
}
