

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class settingsFrame extends JFrame {
	settingsFrame sf;
	int extx = 200;
	int exty = 210;
	int curx = 0;
	int cury = 0;
	JButton close;
	JPanel holder;
	JTextField rgbf;
	JTextField rgbs;
	JTextField rgbt;
	JButton rgbconf;
	JLabel signature;
	//C:\Users\myval\.p2\pool\plugins\org.eclipse.equinox.launcher.win32.win32.x86_64_1.2.1100.v20240722-2106
	settingsFrame() {
		sf = this;
		holder = new JPanel();
		close = new JButton();
		rgbf = new JTextField();
		rgbs = new JTextField();
		rgbt = new JTextField();
		rgbconf = new JButton();
		signature = new JLabel();
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 220, 10);
		this.setVisible(false);
		this.setUndecorated(true);
		this.setLayout(new GridLayout());
		this.add(holder);
		holder.setLayout(null);
		holder.setBackground(Color.black);
		holder.add(close);
		holder.add(rgbf);
		holder.add(rgbs);
		holder.add(rgbt);
		holder.add(rgbconf);
		holder.add(signature);
		signature.setSize(200,20);
		signature.setText("Luděk Hanzík");
		signature.setForeground(Color.white);
		signature.setLocation(10, 140);
		rgbf.setSize(50, 25);
		rgbf.setLocation(10, 10);
		rgbf.setForeground(Color.white);
		rgbf.setBackground(Color.black);
		rgbf.setBorder(new LineBorder(Color.red));
		rgbs.setSize(50, 25);
		rgbs.setLocation(10, 40);
		rgbs.setForeground(Color.white);
		rgbs.setBackground(Color.black);
		rgbs.setBorder(new LineBorder(Color.green));
		rgbt.setSize(50, 25);
		rgbt.setLocation(10, 70);
		rgbt.setForeground(Color.white);
		rgbt.setBackground(Color.black);
		rgbt.setBorder(new LineBorder(Color.blue));
		rgbconf.setSize(50, 25);
		rgbconf.setLocation(10, 100);
		rgbconf.setForeground(Color.white);
		rgbconf.setBackground(Color.black);
		rgbconf.setText("✓");
		close.setSize(20, 20);
		close.setBackground(new Color(150,0,0));
		close.setFocusable(false);
		close.setLocation(extx-10, 0);
		close.addActionListener(e -> close());
		
	}
	
	public void open() {
		this.setVisible(true);
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(curx <= extx) {
					curx += 10;
					sf.setSize(curx, sf.getHeight());
				}
				if(cury <= exty) {
					cury += 10;
					sf.setSize(sf.getWidth(), cury);
				}
				if(curx >= extx && cury >= exty) {
					t.cancel();
				}
			}
		}, 0, 20);
	}
	public void close() {
		this.setVisible(true);
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(curx > 0) {
					curx -= 10;
					sf.setSize(curx, sf.getHeight());
				}
				if(cury > 0) {
					cury -= 10;
					sf.setSize(sf.getWidth(), cury);
				}
				if(curx == 0 && cury == 0) {
					t.cancel();
					sf.setVisible(false);
				}
			}
		}, 0, 10);
	}
}
