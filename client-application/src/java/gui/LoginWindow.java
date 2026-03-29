/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import service.RestClient;
import service.Session;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends JFrame {

    private final JButton btnCheckUser = new JButton("Check user");
    private final JButton btnLogin = new JButton("Login");
    private final JLabel lblStatus = new JLabel("Nije ulogovan korisnik.");

    public LoginWindow() {
        setTitle("Prijava korisnika");
        setSize(400, 160);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JPanel centerPanel = new JPanel(new FlowLayout());

        topPanel.add(lblStatus);
        centerPanel.add(btnCheckUser);
        centerPanel.add(btnLogin);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        btnCheckUser.addActionListener(e -> checkUserAction());
        btnLogin.addActionListener(e -> loginAction());
    }

    private String[] showCredentialsDialog() {
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Unos kredencijala",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) return null;

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username i password moraju biti uneti.");
            return null;
        }

        return new String[]{username, password};
    }

    private void checkUserAction() {
        String[] credentials = showCredentialsDialog();
        if (credentials == null) return;

        String response = RestClient.checkUser(credentials[0], credentials[1]);
        JOptionPane.showMessageDialog(this, response, "Check user", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loginAction() {
        String[] credentials = showCredentialsDialog();
        if (credentials == null) return;

        String response = RestClient.checkUser(credentials[0], credentials[1]);

        if (response.startsWith("OK;")) {
            String[] parts = response.split(";");

            if (parts.length < 2) {
                JOptionPane.showMessageDialog(this, "Neispravan odgovor servera: " + response);
                return;
            }

            Session.IDKor = Integer.parseInt(parts[1]);
            Session.username = credentials[0];
            Session.role = null;

            JOptionPane.showMessageDialog(this, "Uspešan login.");

            new UserWindow().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, response, "Login neuspešan", JOptionPane.WARNING_MESSAGE);
        }
    }
}
