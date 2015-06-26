/**
 * 
 */
package com.github.sx.hcm;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

/**
 * @author stevenxu
 *
 */
public class SSH_BJ_R {

	public static void main(String[] arg) {

		try {
			JSch jsch = new JSch();

			// String host = null;
			// if (arg.length > 0) {
			// host = arg[0];
			// } else {
			// host = JOptionPane.showInputDialog("Enter username@hostname",
			// System.getProperty("user.name") + "@localhost");
			// }
			// String user = host.substring(0, host.indexOf('@'));
			// host = host.substring(host.indexOf('@') + 1);

			// Session session = jsch.getSession(user, host, 22);

			// Session session = jsch.getSession("uar3", "221.179.140.186",
			// 33033);
			Session session = jsch.getSession("root", "10.4.66.83", 22);

			// username and password will be given via UserInfo interface.
			// UserInfo ui = new MyUserInfo();
			// ui.promptPassword("abc");
			// session.setUserInfo(ui);
			session.setUserInfo(new UserInfo() {

				@Override
				public String getPassphrase() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getPassword() {
					// TODO Auto-generated method stub
					return "kvmroot";
				}

				@Override
				public boolean promptPassphrase(String arg0) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public boolean promptPassword(String arg0) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public boolean promptYesNo(String arg0) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public void showMessage(String arg0) {
					// TODO Auto-generated method stub

				}
			});

			session.connect();

			// Channel channel=session.openChannel("shell");
			// channel.connect();
			// setPortForwardingR(host, session);

			doExec("whoami", session);
			doExec("pwd", session);
			doExec("su - hdfs -c \"whoami\"", session);
			doExec("su - hdfs -c \"hdfs dfsadmin -report > /home/hdfs/report.log\"", session);
			doExec("whoami", session);
			
			
			session.disconnect();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	private static void doExec(String command, Session session)
			throws JSchException, IOException {

		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setCommand(command);
		System.out.println("[EXEC CMD]" + command);
		System.out.print("[EXEC RET]");
		// X Forwarding
		// channel.setXForwarding(true);

		// channel.setInputStream(System.in);
		channel.setInputStream(null);

		// channel.setOutputStream(System.out);

		// FileOutputStream fos=new FileOutputStream("/tmp/stderr");
		// ((ChannelExec)channel).setErrStream(fos);
		((ChannelExec) channel).setErrStream(System.err);

		InputStream in = channel.getInputStream();

		channel.connect();

		byte[] tmp = new byte[1024];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0)
					break;
				System.out.print(new String(tmp, 0, i));
			}
			if (channel.isClosed()) {
				if (in.available() > 0)
					continue;
				System.out.println("exit-status: " + channel.getExitStatus());
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (Exception ee) {
			}
		}
		channel.disconnect();
	}

	private static void setPortForwardingR(String host, Session session)
			throws JSchException {
		int rport;
		String lhost;
		int lport;

		String foo = JOptionPane.showInputDialog("Enter -R port:host:hostport",
				"port:host:hostport");
		rport = Integer.parseInt(foo.substring(0, foo.indexOf(':')));
		foo = foo.substring(foo.indexOf(':') + 1);
		lhost = foo.substring(0, foo.indexOf(':'));
		lport = Integer.parseInt(foo.substring(foo.indexOf(':') + 1));
		session.setPortForwardingR(rport, lhost, lport);

		System.out.println(host + ":" + rport + " -> " + lhost + ":" + lport);
	}

	public static class MyUserInfo implements UserInfo, UIKeyboardInteractive {
		public String getPassword() {
			return passwd;
		}

		public boolean promptYesNo(String str) {
			Object[] options = { "yes", "no" };
			int foo = JOptionPane.showOptionDialog(null, str, "Warning",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
			return foo == 0;
		}

		String passwd;
		JTextField passwordField = (JTextField) new JPasswordField(20);

		public String getPassphrase() {
			return null;
		}

		public boolean promptPassphrase(String message) {
			return true;
		}

		public boolean promptPassword(String message) {
			Object[] ob = { passwordField };
			int result = JOptionPane.showConfirmDialog(null, ob, message,
					JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				passwd = passwordField.getText();
				System.out.println("PASSWD:" + passwd);
				return true;
			} else {
				return false;
			}
		}

		public void showMessage(String message) {
			JOptionPane.showMessageDialog(null, message);
		}

		final GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0);
		private Container panel;

		public String[] promptKeyboardInteractive(String destination,
				String name, String instruction, String[] prompt, boolean[] echo) {
			panel = new JPanel();
			panel.setLayout(new GridBagLayout());

			gbc.weightx = 1.0;
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.gridx = 0;
			panel.add(new JLabel(instruction), gbc);
			gbc.gridy++;

			gbc.gridwidth = GridBagConstraints.RELATIVE;

			JTextField[] texts = new JTextField[prompt.length];
			for (int i = 0; i < prompt.length; i++) {
				gbc.fill = GridBagConstraints.NONE;
				gbc.gridx = 0;
				gbc.weightx = 1;
				panel.add(new JLabel(prompt[i]), gbc);

				gbc.gridx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weighty = 1;
				if (echo[i]) {
					texts[i] = new JTextField(20);
				} else {
					texts[i] = new JPasswordField(20);
				}
				panel.add(texts[i], gbc);
				gbc.gridy++;
			}

			if (JOptionPane.showConfirmDialog(null, panel, destination + ": "
					+ name, JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
				String[] response = new String[prompt.length];
				for (int i = 0; i < prompt.length; i++) {
					response[i] = texts[i].getText();
				}
				return response;
			} else {
				return null; // cancel
			}
		}
	}

}
