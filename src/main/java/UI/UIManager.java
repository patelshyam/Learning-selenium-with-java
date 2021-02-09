package UI;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import logger.LogManager;
import scraper.Scraper;

import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;

public class UIManager {

	private JFrame frmWhatsappAutomation;
	public static JTextField excelPath;
	public static JTextField picturesPath;
	public static JTextField videoPath;
	public static JTextPane logManager;
	public static JTextPane msgField;
	public static JButton startBtn;
	public static JButton stopBtn;
	private static AtomicBoolean scraperThreadStatus = new AtomicBoolean(false);
	public static JTextField textMobile;
	Scraper s; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager window = new UIManager();
					window.frmWhatsappAutomation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UIManager() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWhatsappAutomation = new JFrame();
		frmWhatsappAutomation.setTitle("Whatsapp Automation");
		frmWhatsappAutomation.setBounds(100, 100, 452, 442);
		frmWhatsappAutomation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWhatsappAutomation.getContentPane().setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(3, 5, 205, 200);
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel.add(new JSeparator(SwingConstants.VERTICAL));
		panel.setLayout(null);
		frmWhatsappAutomation.getContentPane().add(panel);
		
		JLabel linkLabel = new JLabel("Add Whatsapp Message");
		linkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		linkLabel.setVerticalAlignment(SwingConstants.CENTER);
		linkLabel.setBounds(10, 10, 190, 25);
		panel.add(linkLabel);
		
		startBtn = new JButton("START");
		startBtn.setBounds(10, 166, 89, 23);
		startBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(UIManager.textMobile.getText().length()>=12)
				{
					File f = new File(UIManager.excelPath.getText());
					if(f.exists())
					{
						startScraper();
					}
					else
					{
						JOptionPane.showMessageDialog(frmWhatsappAutomation,"Excel path is not valid");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(frmWhatsappAutomation,"Mobile Number is not valid");
				}
				
			}
		});
		panel.add(startBtn);
		
		stopBtn = new JButton("STOP");
		stopBtn.setBounds(109, 166, 89, 23);
		stopBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				stopScraper();
			}
		});
		panel.add(stopBtn);
		
		msgField = new JTextPane();
		JScrollPane sp = new JScrollPane(msgField);
		sp.setBounds(10, 34, 185, 121);
		panel.add(sp);
		
		JPanel dataPnl = new JPanel();
		dataPnl.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		dataPnl.setBounds(218, 5, 215, 200);
		frmWhatsappAutomation.getContentPane().add(dataPnl);
		dataPnl.setLayout(null);
		
		JLabel lblExcel = new JLabel("ExcelPath");
		lblExcel.setBounds(10, 25, 64, 14);
		dataPnl.add(lblExcel);
		
		excelPath = new JTextField();
		excelPath.setBounds(71, 22, 132, 20);
		dataPnl.add(excelPath);
		excelPath.setColumns(10);
		
		JLabel lblPictures = new JLabel("Pictures");
		lblPictures.setBounds(10, 58, 64, 14);
		dataPnl.add(lblPictures);
		
		picturesPath = new JTextField();
		picturesPath.setToolTipText("CommaSeprated values formore than one");
		picturesPath.setBounds(71, 55, 132, 20);
		dataPnl.add(picturesPath);
		picturesPath.setColumns(10);
		
		JLabel lblVideo = new JLabel("Video");
		lblVideo.setBounds(10, 95, 64, 14);
		dataPnl.add(lblVideo);
		
		videoPath = new JTextField();
		videoPath.setToolTipText("Comma seprated values for more than one");
		videoPath.setBounds(71, 92, 132, 20);
		dataPnl.add(videoPath);
		videoPath.setColumns(10);
		
		JLabel lblMobile = new JLabel("Mobile");
		lblMobile.setBounds(10, 133, 46, 14);
		dataPnl.add(lblMobile);
		
		textMobile = new JTextField();
		textMobile.setBounds(71, 130, 132, 20);
		dataPnl.add(textMobile);
		textMobile.setColumns(10);
		
		logManager = new JTextPane();
		logManager.setBounds(3, 216, 430, 176);
		frmWhatsappAutomation.getContentPane().add(logManager);
		
		JScrollPane scrollPane = new JScrollPane(logManager,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(3, 216, 430, 176);
		frmWhatsappAutomation.getContentPane().add(scrollPane);
		
		
		LogManager.logInfo("Start User Interface..........................................[Success]");
	}
	
	public static void appendToPane(String msg, Color c) {

		Document doc = logManager.getDocument();

		// Define style
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, c);

		try {
			doc.insertString(doc.getLength(), msg, keyWord);

		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public boolean startScraper()
	{
		UIManager.startBtn.setEnabled(false);
		UIManager.stopBtn.setEnabled(true);
		Thread scraperThread = new Thread() {
			public void run() {
				scraperThreadStatus.set(true);
				while (scraperThreadStatus.get()) {
					s = new Scraper();
					try {
						s.startScraper();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (scraperThreadStatus.get() == false && stopBtn.isEnabled()) {
					// below statements will execute when thread status is set to false
					LogManager.logAlert("-------------------- Scraper Stopped. ------------------\n\n");
					UIManager.startBtn.setEnabled(true);
					UIManager.stopBtn.setEnabled(false);
				}

			}
		};
		scraperThread.start();
		return true;
	}
	
	public static boolean getScraperThreadStatus() {
		return scraperThreadStatus.get();
	}

	public static void setScraperThreadStatus(boolean status) {
		scraperThreadStatus.set(status);
	}

	
	public void stopScraper()
	{
		scraperThreadStatus.set(false);
		s.stopScrapper();
	}
}
