

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class DateFrame extends JFrame {
	Font base;
	Font Base;
	Font used;
	Color txt1 = new Color(0, 0, 0);
	Color txt2 = new Color(0, 0, 0);
	PTime pt;
	Timer t;
	
	DateFrame() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(10f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
			Base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(58f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
		} catch (Exception e) {e.printStackTrace();}
		pt = new PTime();
		t = new Timer();
		this.setSize(100, 100);
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 220, 120);
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
			}
		}, 1000, 3000);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
	}

	public class PTime extends JPanel {
		PTime() {
			this.setOpaque(false);
			this.setBackground(new Color(0, 0, 0,0));
			this.setSize(100, 100);

		}

		public void paint(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			long time = System.currentTimeMillis();
			String date1;
			date1 = (new SimpleDateFormat("DD")).format(new Date(time));
			String date2;
			date2 = (new SimpleDateFormat("MM/YY")).format(new Date(time));
			GradientPaint gp = new GradientPaint(0, 0, txt2, 0, 50, txt1);
			g2.setFont(Base);
			g2.setPaint(gp);
			g2.drawString(date1, 13, 68);
			g2.setFont(base);
			g2.drawString(date2, 35, 88);
			
		}
	}
}
