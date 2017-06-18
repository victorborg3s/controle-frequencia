package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

public class TimerLabelUpdater implements Runnable {

	private JLabel timerLabel;
	private Date currentTime;
	private DateFormat dateFormat;
	private boolean interrupt = false;
	
	@SuppressWarnings("unused")
	private TimerLabelUpdater() {
		
	}

	public TimerLabelUpdater(JLabel label) {
		this.timerLabel = label;
		this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	@Override
	public void run() {
		try {
			while (!interrupt) {
				this.currentTime = new Date();
				this.timerLabel.setText(dateFormat.format(currentTime));
				Thread.sleep(1000);
			}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

	public void setInterrupt(boolean b) {
		this.interrupt = b;
	}
	
	public boolean getInterrupt() {
		return this.interrupt;
	}
	
}
