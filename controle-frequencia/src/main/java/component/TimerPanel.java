package component;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import util.TimerLabelUpdater;

public class TimerPanel extends JPanel {
	
	private static final long serialVersionUID = -7707424230660601763L;
	private JLabel timerLabel;
	private Thread timerUpdaterThread;
	
	public TimerPanel() {
		super();
		this.setBackground(new Color(255, 255, 255));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		Date date = new Date();
		timerLabel = new JLabel(dateFormat.format(date));
		timerLabel.setFont(new Font("Courier", Font.PLAIN, 60));
		this.add(timerLabel);
		timerUpdaterThread = new Thread(new TimerLabelUpdater(timerLabel));
		timerUpdaterThread.start();
	}
	
	public JLabel getTimerLabel() {
		return this.timerLabel;
	}
	
}
