package com.demo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.DropMode;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JPanel;

public class UI {

	/**
	 * 窗体
	 */
	private JFrame frame;
	/**
	 * 温度文本框
	 */
	private JTextField tv_tmp;
	/**
	 * 湿度文本框
	 */
	private JTextField tv_hum;
	/**
	 * 气体浓度文本框
	 */
	private JTextField tv_gas;
	
	private JTextField txtR;
	private JTextField txtPpm;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * 程序启动类
	 */
	public static void main(String[] args) {
		// 子线程事件队列
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 构造函数
	 */
	public UI() {
		initialize();
	}

	/**
	 * 初始化界面
	 */
	private void initialize() {
		// 设置窗体
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(SystemColor.activeCaption);
		frame.setBounds(100, 100, 561, 348);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// 按钮
		JButton btn_run = new JButton("\u5F00\u59CB");
		btn_run.setBackground(SystemColor.activeCaption);
		btn_run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Conn(frame, tv_tmp, tv_hum, tv_gas)).start();

			}
		});
		btn_run.setBounds(393, 28, 93, 23);
		frame.getContentPane().add(btn_run);

		// 温度文本框
		tv_tmp = new JTextField();
		tv_tmp.setFocusable(false);
		tv_tmp.setFocusTraversalKeysEnabled(false);
		tv_tmp.setBackground(SystemColor.activeCaption);
		tv_tmp.setForeground(Color.DARK_GRAY);
		tv_tmp.setFont(new Font("微软雅黑", Font.PLAIN, 88));
		tv_tmp.setHorizontalAlignment(SwingConstants.CENTER);
		tv_tmp.setEditable(false);
		tv_tmp.setText("0\u2103");
		tv_tmp.setBounds(82, 10, 278, 181);
		frame.getContentPane().add(tv_tmp);

		// 湿度文本框
		tv_hum = new JTextField();
		tv_hum.setBorder(null);
		tv_hum.setPreferredSize(new Dimension(0, 21));
		tv_hum.setVerifyInputWhenFocusTarget(false);
		tv_hum.setSelectionColor(Color.WHITE);
		tv_hum.setOpaque(false);
		tv_hum.setRequestFocusEnabled(false);
		tv_hum.setFocusable(false);
		tv_hum.setFocusTraversalKeysEnabled(false);
		tv_hum.setText("00");
		tv_hum.setHorizontalAlignment(SwingConstants.CENTER);
		tv_hum.setForeground(Color.DARK_GRAY);
		tv_hum.setFont(new Font("微软雅黑", Font.PLAIN, 88));
		tv_hum.setEditable(false);
		tv_hum.setBackground(Color.WHITE);
		tv_hum.setBounds(370, 81, 134, 110);
		frame.getContentPane().add(tv_hum);

		// 气体浓度文本框
		tv_gas = new JTextField();
		tv_gas.setFocusTraversalKeysEnabled(false);
		tv_gas.setFocusable(false);
		tv_gas.setBorder(null);
		tv_gas.setText("0000");
		tv_gas.setHorizontalAlignment(SwingConstants.CENTER);
		tv_gas.setForeground(Color.DARK_GRAY);
		tv_gas.setFont(new Font("微软雅黑", Font.PLAIN, 88));
		tv_gas.setEditable(false);
		tv_gas.setBackground(SystemColor.activeCaption);
		tv_gas.setBounds(82, 201, 318, 110);
		frame.getContentPane().add(tv_gas);

		txtR = new JTextField();
		txtR.setBackground(SystemColor.activeCaption);
		txtR.setBorder(null);
		txtR.setForeground(Color.DARK_GRAY);
		txtR.setFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 28));
		txtR.setHorizontalAlignment(SwingConstants.CENTER);
		txtR.setText("r");
		txtR.setBounds(497, 135, 26, 53);
		frame.getContentPane().add(txtR);
		txtR.setColumns(10);

		txtPpm = new JTextField();
		txtPpm.setFocusable(false);
		txtPpm.setFocusTraversalKeysEnabled(false);
		txtPpm.setBackground(SystemColor.activeCaption);
		txtPpm.setBorder(null);
		txtPpm.setSelectionColor(Color.WHITE);
		txtPpm.setHorizontalAlignment(SwingConstants.CENTER);
		txtPpm.setFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 36));
		txtPpm.setText("ppm");
		txtPpm.setBounds(370, 249, 120, 62);
		frame.getContentPane().add(txtPpm);
		txtPpm.setColumns(10);

		textField = new JTextField();
		textField.setFocusTraversalKeysEnabled(false);
		textField.setFocusable(false);
		textField.setSelectionColor(SystemColor.activeCaption);
		textField.setBorder(null);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("微软雅黑", Font.BOLD, 24));
		textField.setBackground(SystemColor.activeCaption);
		textField.setText("\u6E29\u6E7F\u5EA6");
		textField.setBounds(0, 28, 86, 61);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setText("\u6C14\u4F53");
		textField_1.setSelectionColor(SystemColor.activeCaption);
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setFont(new Font("微软雅黑", Font.BOLD, 24));
		textField_1.setFocusable(false);
		textField_1.setFocusTraversalKeysEnabled(false);
		textField_1.setColumns(10);
		textField_1.setBorder(null);
		textField_1.setBackground(SystemColor.activeCaption);
		textField_1.setBounds(0, 201, 86, 61);
		frame.getContentPane().add(textField_1);
	}
}
