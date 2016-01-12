package com.demo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.ImageObserver;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.TextArea;

import javax.swing.DropMode;

import java.awt.Dimension;

import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import java.awt.SystemColor;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.awt.Canvas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.AttributedCharacterIterator;

import javax.swing.JTextArea;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JScrollPane;

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
	private JTextField tv_hmp;
	/**
	 * 气体浓度文本框
	 */
	private JTextField tv_gas;

	public static JTextField tf_hmp_unit;
	public static JTextField tf_gas_unit;
	public static JTextField tf_tmp_unit;
	public static JTextField tf_tmp;
	public static JTextField tf_hmp;
	public static JTextField tf_gas;

	private JTextField textField_2;
	private Thread main;
	private JComboBox<String> comboBox_1;
	private TextArea tv_log;

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
		frame.setBounds(100, 100, 778, 373);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// 按钮
		JButton btn_run = new JButton("\u542F\u52A8");
		btn_run.setBackground(SystemColor.activeCaption);
		btn_run.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Conn conn = new Conn(frame, tv_tmp, tv_hmp, tv_gas);
				main = new Thread(conn) {

					@Override
					public void interrupt() {
						super.interrupt();
						conn.dispose();
					}

				};
				main.start();
			}
		});
		btn_run.setBounds(542, 60, 93, 23);
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
		tv_hmp = new JTextField();
		tv_hmp.setBorder(null);
		tv_hmp.setPreferredSize(new Dimension(0, 21));
		tv_hmp.setVerifyInputWhenFocusTarget(false);
		tv_hmp.setSelectionColor(Color.WHITE);
		tv_hmp.setOpaque(false);
		tv_hmp.setRequestFocusEnabled(false);
		tv_hmp.setFocusable(false);
		tv_hmp.setFocusTraversalKeysEnabled(false);
		tv_hmp.setText("00");
		tv_hmp.setHorizontalAlignment(SwingConstants.CENTER);
		tv_hmp.setForeground(Color.DARK_GRAY);
		tv_hmp.setFont(new Font("微软雅黑", Font.PLAIN, 50));
		tv_hmp.setEditable(false);
		tv_hmp.setBackground(Color.WHITE);
		tv_hmp.setBounds(309, 115, 83, 73);
		frame.getContentPane().add(tv_hmp);

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

		tf_hmp_unit = new JTextField();
		tf_hmp_unit.setBackground(SystemColor.activeCaption);
		tf_hmp_unit.setBorder(null);
		tf_hmp_unit.setForeground(Color.DARK_GRAY);
		tf_hmp_unit.setFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 36));
		tf_hmp_unit.setHorizontalAlignment(SwingConstants.CENTER);
		tf_hmp_unit.setText("RH");
		tf_hmp_unit.setBounds(378, 141, 61, 31);
		frame.getContentPane().add(tf_hmp_unit);
		tf_hmp_unit.setColumns(10);

		tf_gas_unit = new JTextField();
		tf_gas_unit.setFocusable(false);
		tf_gas_unit.setFocusTraversalKeysEnabled(false);
		tf_gas_unit.setBackground(SystemColor.activeCaption);
		tf_gas_unit.setBorder(null);
		tf_gas_unit.setSelectionColor(Color.WHITE);
		tf_gas_unit.setHorizontalAlignment(SwingConstants.CENTER);
		tf_gas_unit.setFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 36));
		tf_gas_unit.setText("ppm");
		tf_gas_unit.setBounds(665, 126, 86, 62);
		frame.getContentPane().add(tf_gas_unit);
		tf_gas_unit.setColumns(10);

		tf_tmp = new JTextField();
		tf_tmp.setFocusTraversalKeysEnabled(false);
		tf_tmp.setFocusable(false);
		tf_tmp.setSelectionColor(SystemColor.activeCaption);
		tf_tmp.setBorder(null);
		tf_tmp.setHorizontalAlignment(SwingConstants.CENTER);
		tf_tmp.setFont(new Font("微软雅黑", Font.BOLD, 24));
		tf_tmp.setBackground(SystemColor.activeCaption);
		tf_tmp.setText("\u6E29 \u5EA6");
		tf_tmp.setBounds(10, 115, 98, 77);
		frame.getContentPane().add(tf_tmp);
		tf_tmp.setColumns(10);

		tf_gas = new JTextField();
		tf_gas.setText("\u6C14 \u4F53");
		tf_gas.setSelectionColor(SystemColor.activeCaption);
		tf_gas.setHorizontalAlignment(SwingConstants.CENTER);
		tf_gas.setFont(new Font("微软雅黑", Font.BOLD, 24));
		tf_gas.setFocusable(false);
		tf_gas.setFocusTraversalKeysEnabled(false);
		tf_gas.setColumns(10);
		tf_gas.setBorder(null);
		tf_gas.setBackground(SystemColor.activeCaption);
		tf_gas.setBounds(449, 115, 105, 77);
		frame.getContentPane().add(tf_gas);

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
		button.setBounds(645, 60, 93, 23);
		frame.getContentPane().add(button);

		tf_hmp = new JTextField();
		tf_hmp.setText("\u6E7F \u5EA6");
		tf_hmp.setSelectionColor(SystemColor.activeCaption);
		tf_hmp.setHorizontalAlignment(SwingConstants.CENTER);
		tf_hmp.setFont(new Font("微软雅黑", Font.BOLD, 24));
		tf_hmp.setFocusable(false);
		tf_hmp.setFocusTraversalKeysEnabled(false);
		tf_hmp.setColumns(10);
		tf_hmp.setBorder(null);
		tf_hmp.setBackground(SystemColor.activeCaption);
		tf_hmp.setBounds(225, 115, 98, 77);
		frame.getContentPane().add(tf_hmp);

		tf_tmp_unit = new JTextField();
		tf_tmp_unit.setText("\u2103");
		tf_tmp_unit.setHorizontalAlignment(SwingConstants.CENTER);
		tf_tmp_unit.setForeground(Color.DARK_GRAY);
		tf_tmp_unit.setFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 36));
		tf_tmp_unit.setColumns(10);
		tf_tmp_unit.setBorder(null);
		tf_tmp_unit.setBackground(SystemColor.activeCaption);
		tf_tmp_unit.setBounds(165, 143, 50, 31);
		frame.getContentPane().add(tf_tmp_unit);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("1号节点");
		comboBox.addItem("2号节点");
		comboBox.addItem("3号节点");
		comboBox.setBounds(25, 61, 83, 21);
		frame.getContentPane().add(comboBox);

		comboBox_1 = new JComboBox<String>();
		comboBox_1.addItem("433MHz");
		comboBox_1.addItem("蓝牙");
		comboBox_1.addItem("Wifi");
		comboBox_1.setBounds(128, 61, 83, 21);
		frame.getContentPane().add(comboBox_1);

		tv_log = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		tv_log.setFocusTraversalKeysEnabled(false);
		tv_log.setFocusable(false);
		tv_log.setFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 12));

		tv_log.setEditable(false);
		tv_log.setBounds(20, 202, 732, 133);
		frame.getContentPane().add(tv_log);

		JButton button_1 = new JButton("\u5386\u53F2\u8BB0\u5F55");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File(Constant.FILE_PATH);
				if (!file.exists()) {
					JOptionPane.showMessageDialog(frame, "无任何历史记录！", "提示",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line = null;
					int i = 0;
					tv_log.setText("");
					while ((line = br.readLine()) != null) {
						i++;
						if (i % 3 == 0) {
							line += "\n";
						} else {
							line += "\t\t";
						}
						tv_log.append(line);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		button_1.setBackground(SystemColor.activeCaption);
		button_1.setBounds(439, 60, 93, 23);
		frame.getContentPane().add(button_1);

	}

	protected void close() {
		// TODO Auto-generated method stub
		tv_gas.setText("0000");
		tv_hmp.setText("00");
		tv_tmp.setText("00");
		tv_log.setText("");
		tv_gas.setForeground(Color.DARK_GRAY);
		tv_tmp.setForeground(Color.DARK_GRAY);
		tv_hmp.setForeground(Color.DARK_GRAY);
		tf_gas_unit.setForeground(Color.DARK_GRAY);
		tf_gas.setForeground(Color.DARK_GRAY);
		tf_tmp_unit.setForeground(Color.DARK_GRAY);
		tf_tmp.setForeground(Color.DARK_GRAY);
		tf_hmp_unit.setForeground(Color.DARK_GRAY);
		tf_hmp.setForeground(Color.DARK_GRAY);
		main.interrupt();
	}
}
