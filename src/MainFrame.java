

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;

public class MainFrame {
	TimeFrame tf;
	DateFrame df;
	WeatherFrame wf;
	StopWatchFrame sw;
	TimerFrame tif;
	NotesFrame nf;
	settingsFrame sf;
	
	public static void main(String[] args) throws Exception {
		MainFrame mf = new MainFrame();
		mf.sf = new settingsFrame();
		mf.tf = new TimeFrame();
		mf.df = new DateFrame();
		mf.wf = new WeatherFrame();
		mf.sw = new StopWatchFrame();
		mf.tif = new TimerFrame();
		mf.nf = new NotesFrame();
		mf.tf.remove.addActionListener(e -> System.exit(0));
		mf.tf.settings.addActionListener(e -> mf.openSettings());
		mf.sf.rgbconf.addActionListener(e -> mf.saveColor(new Color(Integer.valueOf(mf.sf.rgbf.getText()),Integer.valueOf(mf.sf.rgbs.getText()),Integer.valueOf(mf.sf.rgbt.getText()))));
		mf.changeColor(new Color(20,200,153));
        mf.loadColor();
	}
	public void openSettings() {
		sf.open();
	}
    public void loadColor() {
        String homeFolder = System.getProperty("user.home");
        String fileName = homeFolder + File.separator + "WWbrgbConfig.txt";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            
            if (line != null) {
                // Try splitting by comma and optional spaces
                String[] parts = line.split(",\\s*");
                
                // Ensure the parts contain exactly three numbers
                if (parts.length == 3) {
                    int red = Integer.parseInt(parts[0]);
                    int green = Integer.parseInt(parts[1]);
                    int blue = Integer.parseInt(parts[2]);
                    
                    // Ensure values are valid RGB (0-255)
                    if (isValidColorValue(red) && isValidColorValue(green) && isValidColorValue(blue)) {
                        changeColor(new Color(red, green, blue));
                    } else {
                        System.err.println("Error: Invalid color values. RGB values must be between 0 and 255.");
                    }
                } else {
                    System.err.println("Error: Invalid color format. Expected format: 'Red, Green, Blue'.");
                }
            } else {
                System.err.println("Error: File is empty or missing.");
            }
        } catch (IOException ex) {
            System.err.println("Error reading the color file: " + ex.getMessage());
        }
    }
    private boolean isValidColorValue(int value) {
        return value >= 0 && value <= 255;
    }

    public void saveColor(Color c) {
        String content = String.format("%d, %d, %d", c.getRed(), c.getGreen(), c.getBlue());
        String homeFolder = System.getProperty("user.home");
        String fileName = homeFolder + File.separator + "WWbrgbConfig.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("Color saved to file: " + fileName);
        } catch (IOException ex) {
            System.err.println("Error saving color to file: " + ex.getMessage());
        }
        
        loadColor(); // Reload the color after saving
    }
	
	public void changeColor(Color c) {
		tf.txt1 = c;
		df.txt1 = c;
		wf.txt1 = c;
		sw.txt1 = c;
		tif.txt1 = c;
		nf.txt1 = c;
		tf.repaint();
		df.repaint();
		wf.repaint();
		sw.repaint();
		tif.repaint();
		nf.repaint();
	}
	
}
