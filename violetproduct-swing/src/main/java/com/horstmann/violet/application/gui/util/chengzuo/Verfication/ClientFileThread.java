package com.horstmann.violet.application.gui.util.chengzuo.Verfication;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientFileThread implements Runnable {
	// 该线程所处理的socket所对应的输入流
	private DataOutputStream dos = null;
	private File[] files = null;

	public ClientFileThread(Socket socket) {
		try {
			this.dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * 关闭 输入流
	 */
	public void close(){
		try {
			this.dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	@Override
	public void run() {
		System.out.println("文件传输进程运行");
		// 选择进行传输的文件
		DataInputStream fis = null;
		try {
			int bufferSize = 1024;
			byte[] buf = new byte[bufferSize * bufferSize];

			for (File fi : files) {
				String filePath = fi.getAbsolutePath();
				fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
				String type;

				// 将文件名
				type = "fileName:" + fi.getName();
				dos.write(type.getBytes());
				dos.flush();

				// 文件长度
				type = "fileSize:" + Long.toString(fi.length());
				dos.write(type.getBytes());
				dos.flush();

				// 清空缓冲区
				Arrays.fill(buf, (byte) 0);
				int read = 0;
				while ((read = fis.read(buf)) != -1) {
					dos.write(buf, 0, read);
				}
				dos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				System.out.println("文件传输完成\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}