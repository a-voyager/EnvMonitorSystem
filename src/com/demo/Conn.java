package com.demo;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * 串口链接类
 *
 */
public class Conn {
	private static SerialPort port;
	private static String portName;
	private static OutputStream outputStream;
	private static InputStream inputStream;

	public static void main(String[] args) {
		listPortChoices();

		try {
			CommPortIdentifier portIdentifier = CommPortIdentifier
					.getPortIdentifier(portName);
			try {
				port = (SerialPort) portIdentifier.open(portName, 1000);
				setPort();
				try {
					outputStream = new BufferedOutputStream(
							port.getOutputStream());
					byte[] msg = new byte[] { 0x01 };

					outputStream.write(msg);
					outputStream.flush();

					byte[] re = new byte[8];
					int len = 0;
					while (true) {
						inputStream = new BufferedInputStream(
								port.getInputStream());
						while ((len = inputStream.read(re)) > 0) {
							System.out.println("结果: "
									+ bytesToHexString(subBytes(re, 0, 4)));
						}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						outputStream.close();
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (PortInUseException e) {
				e.printStackTrace();
			}
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		} finally {
			port.close();
		}

	}

	private static void setPort() {
		try {
			port.setSerialPortParams(9600, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			port.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN
					| SerialPort.FLOWCONTROL_XONXOFF_OUT);
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}
	}

	public static void listPortChoices() {
		Enumeration enumeration = CommPortIdentifier.getPortIdentifiers();
		while (enumeration.hasMoreElements()) {
			CommPortIdentifier cp = (CommPortIdentifier) enumeration
					.nextElement();
			if (cp.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				portName = cp.getName();
				System.out.println("port: " + portName);
			}
		}
	}

	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		int i = 0;
		for (; i < bArray.length - 1; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
			sb.append(' ');
		}
		sTemp = Integer.toHexString(0xFF & bArray[i]);
		if (sTemp.length() < 2)
			sb.append(0);
		sb.append(sTemp.toUpperCase());
		return sb.toString();
	}

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static int toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	public static byte[] arrCat(byte[] first, byte[] second) {
		byte[] result = new byte[first.length + second.length];
		System.arraycopy(first, 0, result, 0, first.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public static byte[] subBytes(byte[] src, int begin, int count) {
		byte[] bs = new byte[count];
		for (int i = begin; i < begin + count; i++)
			bs[i - begin] = src[i];
		return bs;
	}

}