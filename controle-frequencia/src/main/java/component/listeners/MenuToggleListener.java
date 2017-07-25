package component.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import component.MainWindowFrame;

public class MenuToggleListener implements KeyListener {
	
	private MainWindowFrame mainFrame;
	
	public MenuToggleListener(MainWindowFrame frame) {
		this.mainFrame = frame;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_M) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            if (mainFrame.getJMenuBar().isVisible()) {
            	mainFrame.getJMenuBar().setVisible(false);
            } else {
            	mainFrame.getJMenuBar().setVisible(true);
            }
        }
	}

}
