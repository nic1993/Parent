package com.horstmann.violet.application.gui.util.chengzuo.Verfication;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ClientSocket {

	// 连接服务器端IP以及端口号
	protected String IP;
	protected int PORT;

	// 创建套接字
	private Socket socket = null;

	// 接收数据线程以及线程类
	Thread recThread = null;
	ClientRecThread clientRecThread = null;

	// 文件传输线程以及线程类
	Thread fileThread = null;
	ClientFileThread clientFileThread = null;
	// 文件数据
	File[] file = null;

	// 创建套接字
	public ClientSocket(String ip, int port) {
		this.IP = ip;
		this.PORT = port;
	}

	// 初始化所有线程
	public void initThread() {
		clientRecThread = new ClientRecThread(socket);
		clientRecThread.keepRunning = true;
		recThread = new Thread(clientRecThread);
		recThread.start();

		clientFileThread = new ClientFileThread(socket);
		fileThread = new Thread(clientFileThread);
	}

	/**
	 * 判断连接函数
	 * 
	 * @return
	 */
	public boolean isConnect() {
		if (socket == null || socket.isClosed()) {
			return false;
		}
		return true;
	}

	/**
	 * 创建socket连接
	 */
	public void Connection() {
		// 1.创建 socket
		try {
			socket = new Socket(IP, PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 2.初始化线程
		initThread();
		// 3.弹框提示
		JOptionPane.showMessageDialog(null, "成功连接到服务器", null, JOptionPane.OK_CANCEL_OPTION);
	}

	/**
	 * 关闭socket连接
	 */
	public void ConnectionClose() {
		try {
			// 0.发送关闭信号，关闭服务器端连接
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.write("exit".getBytes());
			out.flush();
			clientRecThread.keepRunning = false;
			// 1. 输入流、输出流的关闭
			out.close();
			clientFileThread.close();
			clientRecThread.close();
			// 2.socket关闭
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件传输函数
	 */
	public void sendFile(File[] files) {
		try {
			if (files.length > 0) {
				clientFileThread.setFiles(files);
				fileThread.join();
				fileThread.start();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
