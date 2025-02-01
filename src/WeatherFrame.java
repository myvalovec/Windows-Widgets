

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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WeatherFrame extends JFrame {
	Color txt1 = new Color(0, 0, 0);
	Color txt2 = new Color(0, 0, 0);
	Color wtxt;
	PTime pt;
	Font base;
	Font Base;
	Font used;
	WeatherApi wapi;
	Timer t;
	String lokace = "Pilsen";
	WeatherFrame() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(10f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
			Base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(18f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
		} catch (Exception e) {e.printStackTrace();}
		wapi = new WeatherApi();
		pt = new PTime();
		t = new Timer();
		this.setSize(100, 100);
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 110, 120);
		this.toBack();
		this.setFocusableWindowState(false);
		this.setUndecorated(true);
		this.setBackground(new Color(0,0,0,180));
		//this.setOpacity(0.9f);
		this.setVisible(true);
		this.toBack();
		this.setLayout(new GridLayout());
		this.add(pt);
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				pt.repaint();
			}
		}, 1000, 5000);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
	}
	

	public class PTime extends JPanel {
		PTime() {
			this.setOpaque(false);
			this.setBackground(new Color(0, 0, 0));
			this.setSize(100, 100);
		}

		public void paint(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			String Wdesc = wapi.getWeatherDescription(lokace);
			Wdesc = Wdesc.toLowerCase();
			if (Wdesc.contains("clear sky")) {
				wtxt = new Color(0, 100, 255);
				Wdesc = "jasno";
				
			} else if (Wdesc.contains("cloud")) {
				Wdesc = "zataženo";
				wtxt = new Color(200, 200, 200);
			} else if (Wdesc.contains("rain") || Wdesc.contains("drizzle")) {
				wtxt = new Color(0, 0, 255);
			} else if (Wdesc.contains("thunderstorm")) {
				wtxt = new Color(128, 0, 128);
				Wdesc = "bouře";
			} else if (Wdesc.contains("snow")) {
				wtxt = new Color(255, 255, 255);
			} else if (Wdesc.contains("mist") || Wdesc.contains("fog") || Wdesc.contains("haze")
					|| Wdesc.contains("smoke") || Wdesc.contains("dust") || Wdesc.contains("sand")
					|| Wdesc.contains("ash")) {
				wtxt = new Color(169, 169, 169);
				Wdesc = "mlha";
			} else if (Wdesc.contains("tornado")) {
				wtxt = new Color(255, 0, 0);
				Wdesc = "tornádo";
			} else if (Wdesc.contains("squalls")) {
				wtxt = new Color(255, 69, 0);
				Wdesc = "bouře";
			} else if (Wdesc.contains("hail")) {
				wtxt = new Color(255, 223, 0);
				Wdesc = "kroupy";
			} else if (Wdesc.contains("volcanic ash")) {
				wtxt = new Color(128, 128, 128);
				Wdesc = "popel";
			} else if (Wdesc.contains("extreme")) {
				wtxt = new Color(255, 0, 0);
				Wdesc = "extrémní";
			} else {
				wtxt = txt1;
				Wdesc = "zataženo";
				
			}
			GradientPaint gpw = new GradientPaint(0, 0, txt2, 0, 50, wtxt);
			g2.setPaint(gpw);
			g2.setFont(Base);
			// System.out.println(wapi.getTemperature("Pilsen")+" uuuuuuu");
			g2.drawString(Wdesc, 5, 40);
			GradientPaint gp = new GradientPaint(0, 0, txt2, 0, 50, txt1);
			g2.setPaint(gp);
			g2.drawString(wapi.getTemperature(lokace)+"°C", 5, 60);
			g2.setFont(base);
			g2.drawString(wapi.getFeelsLikeTemperature(lokace)+"°C", 5, 75);
			g2.drawString(lokace, 45-(lokace.length())*3, 20);
		}
	}
}
