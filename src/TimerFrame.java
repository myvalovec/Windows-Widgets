

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TimerFrame extends JFrame {
	Font base;
	Font Base;
	Color txt1 = new Color(0, 0, 0);
	Color txt2 = new Color(0, 0, 0);
	PTime pt;
	Timer t;
	long startTime = 0;
	long displayTime = 0;
	long setTime = 0;
	JButton plh;
	JButton mih;
	JButton plm;
	JButton mim;
	JButton pls;
	JButton mis;
	JButton start;
	JButton stop;
	JButton reset;
	boolean running = false;
	boolean paused = false;
	boolean stopped = false;
	long stopStartTime = 0;
	String st = "";

	TimerFrame() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(10f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
			Base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(18f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
		} catch (Exception e) {e.printStackTrace();}
		plh = new JButton();
		mih = new JButton();
		plm = new JButton();
		mim = new JButton();
		pls = new JButton();
		mis = new JButton();

		start = new JButton();
		stop = new JButton();
		reset = new JButton();

		pt = new PTime();
		t = new Timer();

		this.setSize(100, 100);
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 220, 230);
		this.toBack();
		this.setFocusableWindowState(false);
		this.setUndecorated(true);
		this.setBackground(new Color(0, 0, 0, 180));
		// this.setOpacity(0.8f);
		this.setVisible(true);
		this.toBack();
		this.setLayout(new GridLayout());
		this.add(pt);
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				pt.repaint();

				if (running == true) {
					displayTime = (startTime + setTime) - System.currentTimeMillis();
					if ((startTime + setTime) - System.currentTimeMillis() <= 1) {
						reset();
						if (SystemTray.isSupported()) {
							SystemTray tray = SystemTray.getSystemTray();
							Image image = Toolkit.getDefaultToolkit().createImage("icon.png"); // Optional icon

							TrayIcon trayIcon = new TrayIcon(image, "Java Notification");
							trayIcon.setImageAutoSize(true);
							trayIcon.setToolTip("Notification Demo");
							try {
								tray.add(trayIcon);
							} catch (AWTException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							trayIcon.displayMessage("Časovač", "Časovač skončil.", MessageType.INFO);
						} else {
							System.out.println("System tray not supported!");
						}
					}
				} else if (running == false && stopped == false) {
					ZonedDateTime zdt = Instant.ofEpochMilli(setTime).atZone(ZoneOffset.UTC);
					st = (zdt.format(DateTimeFormatter.ofPattern("HHmmss")));
					LocalTime time = LocalTime.parse(st, DateTimeFormatter.ofPattern("HHmmss"));
					setTime = time.toSecondOfDay() * 1000L;
					displayTime = setTime;
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
			this.setBackground(new Color(0, 0, 0, 0));
			this.setSize(100, 100);
			this.setLayout(null);
			this.add(start);
			start.setFocusable(false);
			start.setBackground(new Color(0, 0, 0, 0));
			start.setSize(new Dimension(20, 20));
			start.setLocation(15, 70);
			start.addActionListener(e -> start());
			this.add(stop);
			stop.setFocusable(false);
			stop.setBackground(new Color(0, 0, 0, 0));
			stop.setSize(new Dimension(20, 20));
			stop.setLocation(40, 70);
			stop.addActionListener(e -> stop());
			this.add(reset);
			reset.setFocusable(false);
			reset.setBackground(new Color(0, 0, 0, 0));
			reset.setSize(new Dimension(20, 20));
			reset.setLocation(65, 70);
			reset.addActionListener(e -> reset());

			this.add(plh);
			plh.setFocusable(false);
			plh.setBackground(new Color(0, 0, 0, 0));
			plh.setSize(new Dimension(20, 15));
			plh.setLocation(10, 20);
			plh.addActionListener(e -> buttonsAction(1));
			this.add(mih);
			mih.setFocusable(false);
			mih.setBackground(new Color(0, 0, 0, 0));
			mih.setSize(new Dimension(20, 15));
			mih.setLocation(10, 55);
			mih.addActionListener(e -> buttonsAction(2));
			this.add(plm);
			plm.setFocusable(false);
			plm.setBackground(new Color(0, 0, 0, 0));
			plm.setSize(new Dimension(20, 15));
			plm.setLocation(40, 20);
			plm.addActionListener(e -> buttonsAction(3));
			this.add(mim);
			mim.setFocusable(false);
			mim.setBackground(new Color(0, 0, 0, 0));
			mim.setSize(new Dimension(20, 15));
			mim.setLocation(40, 55);
			mim.addActionListener(e -> buttonsAction(4));
			this.add(pls);
			pls.setFocusable(false);
			pls.setBackground(new Color(0, 0, 0, 0));
			pls.setSize(new Dimension(20, 15));
			pls.setLocation(70, 20);
			pls.addActionListener(e -> buttonsAction(5));
			this.add(mis);
			mis.setFocusable(false);
			mis.setBackground(new Color(0, 0, 0, 0));
			mis.setSize(new Dimension(20, 15));
			mis.setLocation(70, 55);
			mis.addActionListener(e -> buttonsAction(6));

		}

		public void paint(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			long time = displayTime;
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
			g2.setFont(Base);
			g2.drawString(hourf, 10, 50);
			g2.drawString(hours, 20, 50);
			g2.drawString(":", 30, 50);
			g2.drawString(minf, 40, 50);
			g2.drawString(mins, 50, 50);
			g2.drawString(":", 60, 50);
			g2.drawString(secf, 70, 50);
			g2.drawString(secs, 80, 50);
			g2.setFont(base);
			if (running == false && stopped == false) {
				g2.drawString("▲", 17, 32);
				g2.drawString("▲", 47, 32);
				g2.drawString("▲", 77, 32);
				g2.drawString("▼", 17, 62);
				g2.drawString("▼", 47, 62);
				g2.drawString("▼", 77, 62);
			}
			g2.drawString("▶", 21, 83);
			// g2.drawRect(15, 70, 20, 20);
			g2.drawString("▐▐ ", 42, 83);
			// g2.drawRect(40,70,20,20);
			g2.drawString("∆", 72, 84);
			// g2.drawRect(65,70,20,20);
		}

	}

	public void start() {
		if (stopped == true) {
			startTime += System.currentTimeMillis() - stopStartTime;
			System.out.println(stopStartTime - System.currentTimeMillis());
			stopped = false;
			running = true;
		} else {
			startTime = System.currentTimeMillis();
			running = true;
			stopped = false;
		}
	}

	public void stop() {
		running = false;
		stopped = true;
		stopStartTime = System.currentTimeMillis();

	}

	public void reset() {
		setTime = 0;
		running = false;
		stopped = false;
		stopStartTime = 0;
	}

	public void buttonsAction(int type) {
		if (running == false) {
			if (type == 1) {
				setTime += 60 * 60 * 1000;
			} else if (type == 2) {
				setTime -= 60 * 60 * 1000;
			} else if (type == 3) {
				setTime += 60 * 1000;
			} else if (type == 4) {
				setTime -= 60 * 1000;
			} else if (type == 5) {
				setTime += 1000;
			} else if (type == 6) {
				setTime -= 1000;
			}
		}

	}
}
