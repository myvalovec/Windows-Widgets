

import java.awt.Button;
import java.awt.Color;
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
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TimeFrame extends JFrame {
	Timer t;
	TimeFrame tfrm;
	PTime pt;
	Font base;
	Font Base;
	Font used;
	JButton remove;
	JButton settings;
	// Color txt1 = new Color(255,230,0);
	Color txt1 = new Color(0, 0, 0);
	Color txt2 = new Color(0, 0, 0);

	TimeFrame() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(60f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
			Base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(64f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
			used = base;
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t = new Timer();
		tfrm = this;
		pt = new PTime();
		remove = new JButton();
		remove.setFont(new Font("Roboto", 0, 4));
		remove.setLocation(190, 0);
		remove.setSize(20, 20);
		remove.setBackground(Color.black);
		remove.setFocusable(false);
		settings = new JButton();
		settings.setFont(new Font("Roboto", 0, 4));
		settings.setLocation(0, 0);
		settings.setSize(20, 20);
		settings.setBackground(Color.black);
		settings.setFocusable(false);
		this.setSize(210, 100);
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 220, 10);
		this.toBack();
		this.setFocusableWindowState(false);
		this.setUndecorated(true);
		this.setBackground(new Color(0,0,0,180));
		//GradientPaint gp = new GradientPaint(0, 0, txt2, 0, 100, txt1);
		// this.getContentPane().setBackground(txt2);
		this.setVisible(true);
		this.toBack();
		this.setLayout(new GridLayout());
		this.add(pt);
		//pt.setBackground(new Color(0, 0, 0));
		pt.add(remove);
		pt.add(settings);
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				pt.repaint();
			}
		}, 1000, 100);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
	}

	// public static void main(String[] args) {
	// TimeFrame frm = new TimeFrame(0);
	// }
	public class PTime extends JPanel {
		PTime() {
			this.setOpaque(false);
			this.setBackground(new Color(0, 0, 0, 0));
			this.setSize(210, 100);
		}

		public void paint(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			// g2.setColor(new Color(30,30,30));
			// RoundRectangle2D roundedRect = new RoundRectangle2D.Double(15, 5, 70, 90, 40,
			// 40);
			// g2.fill(roundedRect);
			long time = System.currentTimeMillis();
			String clock;
			clock = (new SimpleDateFormat("HH:mm")).format(new Date(time));
			String hourf = String.valueOf(clock.charAt(0));
			String hours = String.valueOf(clock.charAt(1));
			String minf = String.valueOf(clock.charAt(3));
			String mins = String.valueOf(clock.charAt(4));
			GradientPaint gp = new GradientPaint(0, 0, txt2, 0, 50, txt1);
			g2.setPaint(gp);
			// g2.setColor(new Color(250,250,250));
			g2.setFont(used);
			// g2.drawString(clock, 12, 68);
			g2.drawString(hourf, 10, 70);
			g2.drawString(hours, 50, 70);
			g2.drawString(":", 85, 65);
			g2.drawString(minf, 120, 70);
			g2.drawString(mins, 160, 70);
			g2.setFont(new Font("Roboto", 1, 7));
			g2.setColor(new Color(50, 50, 50));
			g2.drawString("X", 195, 12);
			g2.drawString("O", 10, 12);
		}
	}
}
