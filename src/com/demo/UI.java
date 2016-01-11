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
	private JTextField textField_2;

	private Thread main;
	private JButton button_1;
	private JTextField textField_3;
	private JTextField textField_4;

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
		frame.setBounds(100, 100, 768, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// 按钮
		JButton btn_run = new JButton("\u542F\u52A8");
		btn_run.setBackground(SystemColor.activeCaption);
		btn_run.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				main = new Thread(new Conn(frame, tv_tmp, tv_hum, tv_gas));
				main.start();
			}
		});
		btn_run.setBounds(154, 291, 93, 23);
		frame.getContentPane().add(btn_run);

		// 温度文本框
		tv_tmp = new JTextField();
		tv_tmp.setBorder(null);
		tv_tmp.setFocusable(false);
		tv_tmp.setFocusTraversalKeysEnabled(false);
		tv_tmp.setBackground(SystemColor.activeCaption);
		tv_tmp.setForeground(Color.DARK_GRAY);
		tv_tmp.setFont(new Font("微软雅黑", Font.PLAIN, 50));
		tv_tmp.setHorizontalAlignment(SwingConstants.CENTER);
		tv_tmp.setEditable(false);
		tv_tmp.setText("00");
		tv_tmp.setBounds(97, 115, 75, 77);
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
		tv_hum.setFont(new Font("微软雅黑", Font.PLAIN, 50));
		tv_hum.setEditable(false);
		tv_hum.setBackground(Color.WHITE);
		tv_hum.setBounds(309, 115, 83, 73);
		frame.getContentPane().add(tv_hum);

		// 气体浓度文本框
		tv_gas = new JTextField();
		tv_gas.setFocusTraversalKeysEnabled(false);
		tv_gas.setFocusable(false);
		tv_gas.setBorder(null);
		tv_gas.setText("0000");
		tv_gas.setHorizontalAlignment(SwingConstants.CENTER);
		tv_gas.setForeground(Color.DARK_GRAY);
		tv_gas.setFont(new Font("微软雅黑", Font.PLAIN, 50));
		tv_gas.setEditable(false);
		tv_gas.setBackground(SystemColor.activeCaption);
		tv_gas.setBounds(535, 126, 143, 55);
		frame.getContentPane().add(tv_gas);

		txtR = new JTextField();
		txtR.setBackground(SystemColor.activeCaption);
		txtR.setBorder(null);
		txtR.setForeground(Color.DARK_GRAY);
		txtR.setFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 36));
		txtR.setHorizontalAlignment(SwingConstants.CENTER);
		txtR.setText("RH");
		txtR.setBounds(378, 141, 61, 31);
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
		txtPpm.setBounds(665, 126, 86, 62);
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
		textField.setText("\u6E29 \u5EA6");
		textField.setBounds(10, 115, 98, 77);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setText("\u6C14 \u4F53");
		textField_1.setSelectionColor(SystemColor.activeCaption);
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setFont(new Font("微软雅黑", Font.BOLD, 24));
		textField_1.setFocusable(false);
		textField_1.setFocusTraversalKeysEnabled(false);
		textField_1.setColumns(10);
		textField_1.setBorder(null);
		textField_1.setBackground(SystemColor.activeCaption);
		textField_1.setBounds(449, 115, 105, 77);
		frame.getContentPane().add(textField_1);

		textField_2 = new JTextField();
		textField_2.setBorder(null);
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setFont(new Font("微软雅黑", Font.BOLD, 30));
		textField_2.setBackground(SystemColor.activeCaption);
		textField_2.setEditable(false);
		textField_2.setText("\u667A\u6167\u5BB6\u5EAD\u7BA1\u7406\u7CFB\u7EDF");
		textField_2.setBounds(222, 0, 318, 50);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		JButton button = new JButton("\u505C\u6B62");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		button.setBackground(SystemColor.activeCaption);
		button.setBounds(504, 291, 93, 23);
		frame.getContentPane().add(button);
		
		button_1 = new JButton("\u5207\u6362\u8282\u70B9");
		button_1.setBackground(SystemColor.activeCaption);
		button_1.setBounds(15, 82, 93, 23);
		frame.getContentPane().add(button_1);
		
		textField_3 = new JTextField();
		textField_3.setText("\u6E7F \u5EA6");
		textField_3.setSelectionColor(SystemColor.activeCaption);
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setFont(new Font("微软雅黑", Font.BOLD, 24));
		textField_3.setFocusable(false);
		textField_3.setFocusTraversalKeysEnabled(false);
		textField_3.setColumns(10);
		textField_3.setBorder(null);
		textField_3.setBackground(SystemColor.activeCaption);
		textField_3.setBounds(225, 115, 98, 77);
		frame.getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setText("\u2103");
		textField_4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4.setForeground(Color.DARK_GRAY);
		textField_4.setFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 36));
		textField_4.setColumns(10);
		textField_4.setBorder(null);
		textField_4.setBackground(SystemColor.activeCaption);
		textField_4.setBounds(165, 143, 50, 31);
		frame.getContentPane().add(textField_4);
		
		JButton button_2 = new JButton("\u901A\u8BAF\u65B9\u5F0F");
		button_2.setBackground(SystemColor.activeCaption);
		button_2.setBounds(122, 82, 93, 23);
		frame.getContentPane().add(button_2);
	}

	protected void close() {
		// TODO Auto-generated method stub
		main.interrupt();
	}
}
