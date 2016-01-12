package com.demo;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * 串口链接类
 *
 */
public class Conn implements Runnable {
	/**
	 * 串口
	 */
	private static SerialPort port;
	/**
	 * 串口名称
	 */
	private static String portName;
	/**
	 * 输出流
	 */
	private static OutputStream outputStream;
	/**
	 * 输入流
	 */
	private static InputStream inputStream;
	/**
	 * 温度文本框
	 */
	private JTextField tv_tmp;
	/**
	 * 气体浓度文本框
	 */
	private JTextField tv_gas;
	/**
	 * 湿度文本框
	 */
	private JTextField tv_hmp;
	/**
	 * 窗体
	 */
	private JFrame frame;
	private BufferedWriter bw;

	private boolean isRunning = false;

	/**
	 * 构造函数
	 * 
	 * @param frame
	 * 
	 * @param tv_tmp
	 * @param tv_hmp
	 * @param tv_gas
	 */
	Conn(JFrame frame, JTextField tv_tmp, JTextField tv_hmp, JTextField tv_gas) {
		this.frame = frame;
		this.tv_tmp = tv_tmp;
		this.tv_hmp = tv_hmp;
		this.tv_gas = tv_gas;
	}

	/**
	 * 运行串口链接
	 * 
	 * @throws InterruptedException
	 */
	private void runConn() throws InterruptedException {
		listPortChoices();

		try {
			// 获取串口标志实例
			CommPortIdentifier portIdentifier = CommPortIdentifier
					.getPortIdentifier(portName);
			// 打开串口
			port = (SerialPort) portIdentifier.open(portName, 1000);
			// 设置串口
			setPort();
			// 输出流
			outputStream = new BufferedOutputStream(port.getOutputStream());
			// 输入流
			inputStream = new BufferedInputStream(port.getInputStream());
			// 消息
			byte[] msg = new byte[] { 0x01 };
			// 写入命令
			outputStream.write(msg);
			// 立即刷新命令
			outputStream.flush();
			// 改变运行标志
			isRunning = true;

			bw = new BufferedWriter(new FileWriter(new File("D:/data.txt")));

			// 缓冲区数组
			byte[] re = new byte[8];
			// 缓冲长度
			int len = 0;
			int gas = 0, tmp = 0, hmp = 0;
			ArrayBlockingQueue<Byte> queue = new ArrayBlockingQueue<Byte>(8);
			while (isRunning) {

				while (isRunning && ((len = inputStream.read(re)) != -1)) {
					for (int j = 0; j < len; j++) {
						re[j]--;
						queue.put(Byte.valueOf(re[j]));
						if (queue.size() == 8) {
							int a = queue.poll().byteValue() * 1000;
							int b = queue.poll().byteValue() * 100;
							int c = queue.poll().byteValue() * 10;
							int d = queue.poll().byteValue() * 1;
							if (a + b + c + d - 700 > 0)
								gas = a + b + c + d - 700;
							int e = queue.poll().byteValue() * 10;
							int f = queue.poll().byteValue() * 1;
							if (e + f > 0)
								tmp = e + f;
							int g = queue.poll().byteValue() * 10;
							int h = queue.poll().byteValue() * 1;
							if (g + h > 0)
								hmp = g + h;

							System.out.println("气体：" + gas + "; 温度：" + tmp
									+ "; 湿度：" + hmp);
							bw.write("气体：" + gas + "; 温度：" + tmp + "; 湿度："
									+ hmp);
							bw.newLine();
							bw.flush();

							// 设置文本框数据
							tv_gas.setText(gas + "");
							tv_tmp.setText(tmp + "");
							tv_hmp.setText(hmp + "");

							// 报警
							if (gas >= Constant.MAX_GAS) {
								tv_gas.setForeground(Color.RED);
								UI.tf_gas.setForeground(Color.RED);
								UI.tf_gas_unit.setForeground(Color.RED);
								Toolkit toolkit = frame.getToolkit();
								toolkit.beep();
							} else {
								tv_gas.setForeground(Color.DARK_GRAY);
								UI.tf_gas.setForeground(Color.DARK_GRAY);
								UI.tf_gas_unit.setForeground(Color.DARK_GRAY);
							}
							if (tmp >= Constant.MAX_TMP) {
								UI.tf_tmp.setForeground(Color.RED);
								UI.tf_tmp_unit.setForeground(Color.RED);
								tv_tmp.setForeground(Color.RED);
								Toolkit toolkit = frame.getToolkit();
								toolkit.beep();
							} else {
								tv_tmp.setForeground(Color.DARK_GRAY);
								UI.tf_tmp.setForeground(Color.DARK_GRAY);
								UI.tf_tmp_unit.setForeground(Color.DARK_GRAY);
							}
							if (hmp >= Constant.MAX_HMP) {
								UI.tf_hmp.setForeground(Color.RED);
								UI.tf_hmp_unit.setForeground(Color.RED);
								tv_hmp.setForeground(Color.RED);
								Toolkit toolkit = frame.getToolkit();
								toolkit.beep();
							} else {
								tv_hmp.setForeground(Color.DARK_GRAY);
								UI.tf_hmp.setForeground(Color.DARK_GRAY);
								UI.tf_hmp_unit.setForeground(Color.DARK_GRAY);
							}
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流 关闭串口
			try {
				outputStream.close();
				inputStream.close();
				port.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			port.close();
			try {
				bw.close();
			} catch (IOException e) {
				bw = null;
			}
		}
	}

	/**
	 * 自动设置为可用串口
	 */
	private static void setPort() {
		try {
			// 设置串口参数
			port.setSerialPortParams(9600, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			port.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN
					| SerialPort.FLOWCONTROL_XONXOFF_OUT);
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 展示所有串口
	 */
	private static void listPortChoices() {
		// 获取所有可用端口
		Enumeration<CommPortIdentifier> enumeration = CommPortIdentifier
				.getPortIdentifiers();
		while (enumeration.hasMoreElements()) {
			CommPortIdentifier cp = enumeration.nextElement();
			// 判断是否为活动串口
			if (cp.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				portName = cp.getName();
				System.out.println("port: " + portName);
			}
		}
	}

	@Override
	public void run() {
		try {
			runConn();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
		isRunning = false;
	}

}