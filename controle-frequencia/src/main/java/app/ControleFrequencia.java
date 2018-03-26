package app;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.digitalpersona.onetouch.DPFPCaptureFeedback;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;

import component.MainWindowFrame;

public class ControleFrequencia {

	private MainWindowFrame janelaPrincipal;
	private static DPFPCapture capturer = DPFPGlobal.getCaptureFactory().createCapture();
	private static DPFPEnrollment enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
	private static JLabel jLabelToDraw, jLabelStatus;

	public static void main(String[] args) {
		init();
		new ControleFrequencia().apresentaTelaPrincipal();
	}

	public ControleFrequencia() {
		try {
			this.janelaPrincipal = new MainWindowFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void apresentaTelaPrincipal() {
		janelaPrincipal.setVisible(true);
	}

	public static DPFPCapture getCapturer() {
		return ControleFrequencia.capturer;
	}
	
	public static DPFPEnrollment getEnroller() {
		return ControleFrequencia.enroller;
	}
	
	public static void setJLabelToDraw(JLabel jLabel) {
		ControleFrequencia.jLabelToDraw = jLabel;
	}
	
	public static JLabel getJLabelToDraw() {
		return ControleFrequencia.jLabelToDraw;
	}
	
	public static void setJLabelStatus(JLabel status) {
		ControleFrequencia.jLabelStatus = status;
	}

	protected static void init()
	{
		capturer.addDataListener(new DPFPDataAdapter() {
			@Override public void dataAcquired(final DPFPDataEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					System.out.println("The fingerprint sample was captured.");
					System.out.println("Scan the same fingerprint again.");
					processEnrollment(e.getSample());
				}});
			}
		});
		capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
			@Override public void readerConnected(final DPFPReaderStatusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					System.out.println("The fingerprint reader was connected.");
				}});
			}
			@Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					System.out.println("The fingerprint reader was disconnected.");
				}});
			}
		});
		capturer.addSensorListener(new DPFPSensorAdapter() {
			@Override public void fingerTouched(final DPFPSensorEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					System.out.println("The fingerprint reader was touched.");
				}});
			}
			@Override public void fingerGone(final DPFPSensorEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					System.out.println("The finger was removed from the fingerprint reader.");
				}});
			}
		});
		capturer.addImageQualityListener(new DPFPImageQualityAdapter() {
			@Override public void onImageQuality(final DPFPImageQualityEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	public void run() {
					if (e.getFeedback().equals(DPFPCaptureFeedback.CAPTURE_FEEDBACK_GOOD))
						System.out.println("The quality of the fingerprint sample is good.");
					else
						System.out.println("The quality of the fingerprint sample is poor.");
				}});
			}
		});
	}
	
	protected static Image convertSampleToBitmap(DPFPSample sample) {
		return DPFPGlobal.getSampleConversionFactory().createImage(sample);
	}
	
	public static void drawPicture(Image image) {
		jLabelToDraw.setIcon(new ImageIcon(
			image.getScaledInstance(100, 120, Image.SCALE_DEFAULT)));
	}
	
	protected static void process(DPFPSample sample)
	{
		// Draw fingerprint sample image.
		drawPicture(convertSampleToBitmap(sample));
	}
	
	protected static DPFPFeatureSet extractFeatures(DPFPSample sample, DPFPDataPurpose purpose)
	{
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}
	
	private static void updateStatus()
	{
		// Show number of samples needed.
		jLabelStatus.setText(String.format("Repita leitura %1$s vezes.", enroller.getFeaturesNeeded()));
	}
	
	public static void stop()
	{
		capturer.stopCapture();
	}
	
	public static void start()
	{
		capturer.startCapture();
		jLabelStatus.setText("Posicione a digital no leitor.");
	}
	
	protected static void processEnrollment(DPFPSample sample) {
		process(sample);
		DPFPFeatureSet features = extractFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

		// Check quality of the sample and add to enroller if it's good
		if (features != null) try
		{
			System.out.println("The fingerprint feature set was created.");
			enroller.addFeatures(features);		// Add feature set to template.
		}
		catch (DPFPImageQualityException ex) { }
		finally {
			updateStatus();

			// Check if template has been created.
			switch(enroller.getTemplateStatus())
			{
				case TEMPLATE_STATUS_READY:	// report success and stop capturing
					stop();
					//((MainForm)getOwner()).setTemplate(enroller.getTemplate());
					jLabelStatus.setText("Captura ok.");
					break;

				case TEMPLATE_STATUS_FAILED:	// report failure and restart capturing
					enroller.clear();
					stop();
					updateStatus();
					//((MainForm)getOwner()).setTemplate(null);
					jLabelStatus.setText("Erro: repita captura.");
					start();
					break;
			default:
				break;
			}
		}
	}

}
