

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class NotesFrame extends JFrame {
	Font base;
	Font Base;
	Font used;
	Color txt1 = new Color(0, 0, 0);
	Color txt2 = new Color(0, 0, 0);
	PTime pt;
	Timer t;
	JTextArea Atxt;
	String savedText = "";
	NotesFrame nf;

	NotesFrame() throws IOException {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(10f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
			Base = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")).deriveFont(18f);
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/SFMono-Heavy.otf")));
		} catch (Exception e) {e.printStackTrace();}
		nf = this;
		Atxt = new JTextArea();
		pt = new PTime();
		t = new Timer();
		this.setSize(100, 210);
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 110, 230);
		this.toBack();
		this.setFocusableWindowState(false);
		this.setUndecorated(true);
		this.setBackground(new Color(0,0,0,180));
		//this.setOpacity(0.9f);
		this.setVisible(true);
		this.toBack();
		this.setLayout(new GridLayout());
		this.add(pt);
		this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setFocusableWindowState(true); // Make the window focusable on hover
                //requestFocus();
                //Atxt.requestFocusInWindow();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setFocusableWindowState(false); // Revert to unfocusable when mouse exits
            }
        });
		Atxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setFocusableWindowState(true); // Make the window focusable on hover
                //requestFocus();
                //Atxt.requestFocusInWindow();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setFocusableWindowState(false); // Revert to unfocusable when mouse exits
            }
        });
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Atxt.setForeground(txt1);
				pt.repaint();
				if(nf.getFocusableWindowState() == true) {
					//System.out.println(Atxt.getText());
					try {
						saveText();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, 0, 200);
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
			this.setSize(100, 210);
			this.setLayout(null);
			this.add(Atxt);
			Atxt.setFont(base);
			Atxt.setColumns(1);
			Atxt.setLocation(5, 5);
			Atxt.setSize(100,200);
			Atxt.setBackground(new Color(0,0,0,0));
			Atxt.setForeground(txt1);
			String homeFolder = System.getProperty("user.home");
        	String fileName = homeFolder + File.separator + "WWpoznamky.txt";
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                Atxt.setText(content.toString());
            } catch (IOException ex) {
                System.err.println("load ERROR");
            }
		}
	}
	public void saveText() throws FileNotFoundException, UnsupportedEncodingException {
		 String content = Atxt.getText();
         String homeFolder = System.getProperty("user.home");
        	String fileName = homeFolder + File.separator + "WWpoznamky.txt";
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
             writer.write(content);
             //JOptionPane.showMessageDialog(frame, "File saved successfully to " + fileName);
         } catch (IOException ex) {
             //JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());
        	 System.err.println("ERROR");
         }
	}
}
