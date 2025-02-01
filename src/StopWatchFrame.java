
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class StopWatchFrame extends JFrame {
	Font base;
	Font Base;
	Color txt1 = new Color(0, 0, 0);
	Color txt2 = new Color(0, 0, 0);
	PTime pt;
	Timer t;
	JButton start;
	JButton restart;
	JButton stop;
	long startTime = 0;
	long displayableTime = 0;
	boolean running = false;
	
	StopWatchFrame() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(10f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
			Base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(18f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
		} catch (Exception e) {e.printStackTrace();}
		start = new JButton();
		stop = new JButton();
		restart = new JButton();
		pt = new PTime();
		t = new Timer();
		this.setSize(100, 100);
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 220, 340);
		this.toBack();
		this.setFocusableWindowState(false);
		this.setUndecorated(true);
		this.setBackground(new Color(0,0,0,180));
		//this.setOpacity(0.8f);
		this.setVisible(true);
		this.toBack();
		this.setLayout(new GridLayout());
		this.add(pt);
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				pt.repaint();
				if(running == true) {
					displayableTime = System.currentTimeMillis() - startTime;
				}
			}
		}, 1000, 100);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
	}
	
	public class PTime extends JPanel {
		PTime() {
			this.setOpaque(false);
			this.setBackground(new Color(0, 0, 0,0));
			this.setSize(100, 100);
			this.setLayout(null);
			this.add(start);
			start.setFocusable(false);
			start.setBackground(new Color(0,0,0,0));
			start.setSize(new Dimension(20, 20));
			start.setLocation(15, 70);
			start.addActionListener(e -> startTimer());
			this.add(stop);
			stop.setFocusable(false);
			stop.setBackground(new Color(0,0,0,0));
			stop.setSize(new Dimension(20, 20));
			stop.setLocation(40, 70);
			stop.addActionListener(e -> stopTimer());
			this.add(restart);
			restart.setFocusable(false);
			restart.setBackground(new Color(0,0,0,0));
			restart.setSize(new Dimension(20, 20));
			restart.setLocation(65, 70);
			restart.addActionListener(e -> resetTimer());
		}

		public void paint(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setFont(Base);
			long time = displayableTime;
			String clock;
			ZonedDateTime zdt = Instant.ofEpochMilli(time).atZone(ZoneOffset.UTC);
			clock = (zdt.format(DateTimeFormatter.ofPattern("HHmmss")));
			String hourf = String.valueOf(clock.charAt(0));
			String hours = String.valueOf(clock.charAt(1));
			String minf = String.valueOf(clock.charAt(2));
			String mins = String.valueOf(clock.charAt(3));
			String secf = String.valueOf(clock.charAt(4));
			String secs = String.valueOf(clock.charAt(5));
			GradientPaint gp = new GradientPaint(0, 0, txt2, 0, 50, txt1);
			g2.setPaint(gp);
			g2.drawString(hourf, 10, 50);
			g2.drawString(hours, 20, 50);
			g2.drawString(":", 30, 50);
			g2.drawString(minf, 40, 50);
			g2.drawString(mins, 50, 50);
			g2.drawString(":", 60, 50);
			g2.drawString(secf, 70, 50);
			g2.drawString(secs, 80, 50);
			g2.setFont(base);
			g2.drawString("▶", 21, 83);
			//g2.drawRect(15,70, 20, 20);
			g2.drawString("▐▐ ", 42, 83);
			//g2.drawRect(40,70,20,20);
			g2.drawString("∆", 72, 84);
			//g2.drawRect(65,70,20,20);
		}
		public void startTimer() {
			startTime = System.currentTimeMillis();
			running = true;
		}
		public void stopTimer() {
			running = false;
		}
		public void resetTimer() {
			running = false;
			displayableTime = 0;
			
		}
	}
	
}
