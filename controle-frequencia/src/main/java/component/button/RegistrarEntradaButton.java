package component.button;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import component.MainWindowFrame;
import component.registro.RegistroPonto;
import entity.RegistroTipo;

public class RegistrarEntradaButton extends JButton {

	private static final long serialVersionUID = -4450693869981094587L;
	private RegistrarEntradaButton self;
	
	public RegistrarEntradaButton() throws Exception {
		super("Entrada");
		self = this;
		this.setPreferredSize(new Dimension(330, 130));
		this.setFont(new Font("Arial", Font.PLAIN, 40));
		
		BufferedImage originalImage;
		BufferedImage resizedImage;
		try {
			originalImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("entrada.png"));
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			resizedImage = new BufferedImage(64, 64, type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, 64, 64, null);
			g.dispose();
		} catch (IOException e) {
			throw new Exception("Erro ao tentar ler arquivo 'resources/entrada.jpg'.", e);
		}

		this.setIcon(new ImageIcon(resizedImage));
	    this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegistroPonto telaRegistro = new RegistroPonto((MainWindowFrame)self.getParent().getParent().getParent().getParent(), RegistroTipo.ENTRADA);
				telaRegistro.setVisible(true);
			}
		});
	    
	}

}
