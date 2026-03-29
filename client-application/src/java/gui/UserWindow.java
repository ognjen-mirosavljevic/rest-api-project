/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author ognje
 */
import service.RestClient;
import service.Session;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class UserWindow extends JFrame {

    private final JLabel lblStatus = new JLabel();

    private final JButton btnLogout = new JButton("Logout");
    private final JButton btnGradovi = new JButton("Gradovi");
    private final JButton btnKorisnici = new JButton("Korisnici");
    private final JButton btnKategorije = new JButton("Kategorije");
    private final JButton btnArtikli = new JButton("Moji artikli");
    private final JButton btnKorpa = new JButton("Korpa");
    private final JButton btnWishlist = new JButton("Wishlist");
    private final JButton btnMyOrders = new JButton("Moje narudžbine");
    private final JButton btnAllOrders = new JButton("Sve narudžbine");
    private final JButton btnTransactions = new JButton("Transakcije");
    private final JButton btnCreateGrad = new JButton("Kreiraj grad");
    private final JButton btnAddMoney = new JButton("Dodaj novac");
    private final JButton btnCreateCategory = new JButton("Kreiraj kategoriju");
    private final JButton btnCreateArticle = new JButton("Kreiraj artikal");
    private final JButton btnAddToCart = new JButton("Dodaj u korpu");
    private final JButton btnCreateTransaction = new JButton("Placanje");
    private final JButton btnCreateUser = new JButton("Kreiraj korisnika");
    private final JButton btnChangePrice = new JButton("Promeni cenu");
    private final JButton btnChangeDiscount = new JButton("Postavi popust");
    private final JButton btnRemoveFromCart = new JButton("Izbaci iz korpe");
    private final JButton btnRemoveFromWishlist = new JButton("Izbaci iz liste želja");
    private final JButton btnAddToWishlist = new JButton("Ubaci u listu želja");

    public UserWindow() {
        setTitle("Klijentska aplikacija");
        setSize(700, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        lblStatus.setText("Ulogovan: " + Session.username + " | IDKor = " + Session.IDKor);

        JPanel topPanel = new JPanel();
        topPanel.add(lblStatus);
        topPanel.add(btnLogout);

        JPanel centerPanel = new JPanel(new GridLayout(0, 3, 8, 8));
        centerPanel.add(btnGradovi);
        centerPanel.add(btnKorisnici);
        centerPanel.add(btnKategorije);
        centerPanel.add(btnArtikli);
        centerPanel.add(btnKorpa);
        centerPanel.add(btnWishlist);
        centerPanel.add(btnMyOrders);
        centerPanel.add(btnAllOrders);
        centerPanel.add(btnTransactions);
        centerPanel.add(btnCreateGrad);
        centerPanel.add(btnCreateGrad);
        centerPanel.add(btnAddMoney);
        centerPanel.add(btnCreateCategory);
        centerPanel.add(btnCreateArticle);
        centerPanel.add(btnAddToCart);
        centerPanel.add(btnCreateTransaction);
        centerPanel.add(btnCreateUser);
        centerPanel.add(btnChangePrice);
        centerPanel.add(btnChangeDiscount);
        centerPanel.add(btnRemoveFromCart);
        centerPanel.add(btnRemoveFromWishlist);
        centerPanel.add(btnAddToWishlist);
        

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        btnLogout.addActionListener(e -> logoutAction());

        btnGradovi.addActionListener(e -> 
            showTable("Gradovi", RestClient.getGradovi(),
                new String[]{"ID grada", "Naziv"})
        );

        btnKorisnici.addActionListener(e -> 
            showTable("Korisnici", RestClient.getUsers(),
                new String[]{"ID", "Username", "Ime", "Prezime", "Adresa", "Grad", "Uloga", "Stanje"})
        );

        btnKategorije.addActionListener(e -> 
            showTable("Kategorije", RestClient.getCategories(),
                new String[]{"ID kategorije", "Naziv", "ID nadkategorije"})
        );

        btnArtikli.addActionListener(e -> 
            showTable("Artikli", RestClient.getArticles(),
                new String[]{"ID artikla", "Naziv", "Opis", "Cena", "Popust", "Kategorija", "Vlasnik"})
        );

        btnKorpa.addActionListener(e -> 
            showTable("Korpa", RestClient.getCart(),
                new String[]{"ID artikla", "Naziv", "Jedinicna Cena", "Količina"})
        );

        btnWishlist.addActionListener(e -> 
            showTable("Wishlist", RestClient.getWishlist(),
                new String[]{"ID artikla", "Naziv", "Cena", "Vreme dodavanja"})
        );

        btnMyOrders.addActionListener(e -> 
            showTable("Moje narudžbine", RestClient.getMyOrders(),
                new String[]{"ID narudžbine", "Cena", "Datum", "Adresa", "Grad"})
        );

        btnAllOrders.addActionListener(e -> 
            showTable("Sve narudžbine", RestClient.getAllOrders(),
                new String[]{"ID narudžbine", "Cena", "Datum", "Adresa", "Grad"})
        );

        btnTransactions.addActionListener(e -> 
            showTable("Transakcije", RestClient.getTransactions(),
                new String[]{"ID transakcije", "Datum", "Iznos", "Narudžbina"})
        );
        btnCreateGrad.addActionListener(e -> createGradAction());
        btnAddMoney.addActionListener(e -> addMoneyAction());
        btnCreateCategory.addActionListener(e -> createCategoryAction());
        btnCreateArticle.addActionListener(e -> createArticleAction());
        btnAddToCart.addActionListener(e -> addToCartAction());
        btnCreateTransaction.addActionListener(e -> paymentAction());
        btnCreateUser.addActionListener(e -> createUserAction());
        btnChangePrice.addActionListener(e -> changePriceAction());
        btnChangeDiscount.addActionListener(e -> changeDiscountAction());
        btnRemoveFromCart.addActionListener(e -> removeFromCartAction());
        btnAddToWishlist.addActionListener(e -> addToWishlistAction());
        btnRemoveFromWishlist.addActionListener(e -> removeFromWishlistAction());
    }

    private void showResponse(String title, String response) {
        JOptionPane.showMessageDialog(this, response, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void logoutAction() {
        Session.logout();
        JOptionPane.showMessageDialog(this, "Uspešno odjavljivanje.");

        new LoginWindow().setVisible(true);
        dispose();
    }
    
    private String[] showInputDialog(String title, String... labels) {
        JPanel panel = new JPanel(new java.awt.GridLayout(labels.length, 2, 5, 5));
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            panel.add(new JLabel(labels[i] + ":"));
            fields[i] = new JTextField(15);
            panel.add(fields[i]);
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        String[] values = new String[labels.length];
        for (int i = 0; i < labels.length; i++) {
            values[i] = fields[i].getText().trim();
            if (values[i].isEmpty()) {
                JOptionPane.showMessageDialog(this, "Sva polja moraju biti popunjena.");
                return null;
            }
        }

        return values;
    }
    
    private void createGradAction() {
        String[] input = showInputDialog("Kreiranje grada", "Naziv grada");
        if (input == null) return;

        String response = RestClient.createGrad(input[0]);
        showResponse("Kreiraj grad", response);
    }
    
    private void addMoneyAction() {
        String[] input = showInputDialog("Dodavanje novca", "Iznos", "ID korisnika");
        if (input == null) return;

        try {
            int amount = Integer.parseInt(input[0]);
            int idKor = Integer.parseInt(input[1]);

            String response = RestClient.addMoney(amount, idKor);
            showResponse("Dodaj novac", response);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Iznos i ID korisnika moraju biti brojevi.");
        }
    }
    
    private void createCategoryAction() {
        String[] input = showInputDialog("Kreiranje kategorije", "Naziv", "ID nadkategorije");
        if (input == null) return;

        try {
            String naziv = input[0];
            int idNat = Integer.parseInt(input[1]);

            String response = RestClient.createCategory(naziv, idNat);
            showResponse("Kreiraj kategoriju", response);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID nadkategorije mora biti broj.");
        }
    }

    private void createArticleAction() {
        String[] input = showInputDialog("Kreiranje artikla",
                "Naziv", "Opis", "Cena", "ID kategorije");
        if (input == null) return;

        try {
            String naziv = input[0];
            String opis = input[1];
            double cena = Double.parseDouble(input[2]);
            int idKat = Integer.parseInt(input[3]);

            String response = RestClient.createArticle(naziv, opis, cena, idKat);
            showResponse("Kreiraj artikal", response);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cena i ID kategorije moraju biti brojevi.");
        }
    }
    
    private void addToCartAction() {
        String[] input = showInputDialog("Dodavanje u korpu", "ID artikla", "Količina");
        if (input == null) return;

        try {
            int idArt = Integer.parseInt(input[0]);
            int kolicina = Integer.parseInt(input[1]);

            String response = RestClient.addToCart(idArt, kolicina);
            showResponse("Dodaj u korpu", response);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID artikla i količina moraju biti brojevi.");
        }
    }
    
    private void paymentAction() {
        String[] input = showInputDialog("Plaćanje", "ID grada", "Adresa");
        if (input == null) return;

        try {
            int idGra = Integer.parseInt(input[0]);
            String adresa = input[1];

            String response = RestClient.makePayment(idGra, adresa);
            showResponse("Plaćanje", response);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID grada mora biti broj.");
        }
    }
    
    private void createUserAction() {
        String[] input = showInputDialog(
                "Kreiranje korisnika",
                "Username", "Password", "Ime", "Prezime", "Adresa", "ID grada", "ID uloge"
        );
        if (input == null) return;

            try {
                String username = input[0];
                String password = input[1];
                String ime = input[2];
                String prezime = input[3];
                String adresa = input[4];
                int idGra = Integer.parseInt(input[5]);
                int idUlo = Integer.parseInt(input[6]);

                String response = RestClient.createUser(username, password, ime, prezime, adresa, idGra, idUlo);
                showResponse("Kreiraj korisnika", response);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID grada i ID uloge moraju biti brojevi.");
            }
    }
    
    private void changePriceAction() {
        String[] input = showInputDialog("Promena cene", "ID artikla", "Nova cena");
        if (input == null) return;

        try {
            int idArt = Integer.parseInt(input[0]);
            int cena = Integer.parseInt(input[1]);

            String response = RestClient.changePrice(idArt, cena);
            showResponse("Promeni cenu", response);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID artikla i cena moraju biti brojevi.");
        }
    }
    
    private void changeDiscountAction() {
        String[] input = showInputDialog("Postavljanje popusta", "ID artikla", "Popust");
        if (input == null) return;

        try {
            int idArt = Integer.parseInt(input[0]);
            int popust = Integer.parseInt(input[1]);

            String response = RestClient.changeDiscount(idArt, popust);
            showResponse("Postavi popust", response);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID artikla i popust moraju biti brojevi.");
        }
    }
    
    private void removeFromCartAction() {
        String[] input = showInputDialog("Izbacivanje iz korpe", "ID artikla", "Količina");
        if (input == null) return;

        try {
            int idArt = Integer.parseInt(input[0]);
            int kolicina = Integer.parseInt(input[1]);

            String response = RestClient.removeFromCart(idArt, kolicina);
            showResponse("Izbaci iz korpe", response);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID artikla i količina moraju biti brojevi.");
        }
    }
    
    private void removeFromWishlistAction() {
        String[] input = showInputDialog("Izbaci iz liste želja", "ID artikla");
        if (input == null) return;

        try {
            int idArt = Integer.parseInt(input[0]);

            String response = RestClient.removeFromWishlist(idArt);
            showResponse("Izbaci iz liste želja", response);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID artikla mora biti broj.");
        }
    }
    
    private void addToWishlistAction() {
        String[] input = showInputDialog("Ubaci u listu želja", "ID artikla");
        if (input == null) return;

        try {
            int idArt = Integer.parseInt(input[0]);

            String response = RestClient.addToWishlist(idArt);
            showResponse("Ubaci u listu želja", response);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID artikla mora biti broj.");
        }
    }
    
    private void showTable(String title, String response, String[] columns) {
        if (!response.startsWith("OK")) {
            JOptionPane.showMessageDialog(this, response);
            return;
        }

        String[] rows = response.split(";");
        Object[][] data = new Object[Math.max(0, rows.length - 1)][columns.length];

        for (int i = 1; i < rows.length; i++) {
            String[] parts = rows[i].split(",");

            for (int j = 0; j < columns.length; j++) {
                data[i - 1][j] = (j < parts.length) ? parts[j].trim() : "";
            }
        }

        JTable table = new JTable(data, columns);
        JScrollPane scroll = new JScrollPane(table);

        JFrame frame = new JFrame(title);
        frame.add(scroll);
        frame.setSize(800, 300);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }
}