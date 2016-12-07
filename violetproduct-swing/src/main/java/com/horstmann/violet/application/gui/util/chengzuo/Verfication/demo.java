package com.horstmann.violet.application.gui.util.chengzuo.Verfication;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class demo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ClientSocket clientSocket;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					demo frame = new demo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public demo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
//		clientSocket = new ClientSocket("192.168.149.115", 5555);
		clientSocket = new ClientSocket("192.168.150.142", 5555);
		
		JButton btnNewButton_2 = new JButton("导入测试用例");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setMultiSelectionEnabled(true);
				jfc.showDialog(new JLabel(), "选择测试用例"); 
				File[] files = jfc.getSelectedFiles();
				clientSocket.sendFile(files);
			}
		});
		btnNewButton_2.setBounds(260, 192, 130, 23);
		contentPane.add(btnNewButton_2);
		
		final JButton button = new JButton("开始");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//如果socket为关闭状态，则开启，否则关闭
				if(!clientSocket.isConnect()){
					clientSocket.Connection();
					if(clientSocket.isConnect()){
						button.setText("关闭");
					}
				}else{
					clientSocket.ConnectionClose();
					if(!clientSocket.isConnect()){
						button.setText("连接");
					}
				}
			}
		});
		button.setBounds(63, 192, 130, 23);
		contentPane.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 143, 126, -125);
		contentPane.add(scrollPane);
	}
}
