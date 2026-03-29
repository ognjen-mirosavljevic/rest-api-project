/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import entities.Grad;
import entities.Korisnik;
import entities.Uloga;
import java.util.List;
import javax.persistence.EntityManager;


/**
 *
 * @author ognje
 */
public class ObradaZahteva {
    public static String obradi(String zahtev) {
        String [] delovi = zahtev.split(";");
        
        String metoda = delovi[0];
        
        switch(metoda) {
            case "CHECK_USER":
                return checkLogin(delovi[1], delovi[2]);
            case "GET_GRADOVI":
                return getGradovi(Integer.parseInt(delovi[1]));
                
            case "CREATE_GRAD":
                return createGrad(delovi[1], Integer.parseInt(delovi[2]));
                
            case "CREATE_USER":
                return createUser(delovi[1], delovi[2], delovi[3], delovi[4], delovi[5], Integer.parseInt(delovi[6]), Integer.parseInt(delovi[7]), Integer.parseInt(delovi[8]));
                
            case "ADD_MONEY":
                return addMoney(Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2]), Integer.parseInt(delovi[3]));
                
            case "GET_USERS":
                return getUsers(Integer.parseInt(delovi[1]));
            
            case "CHANGE_ADDRESS_AND_CITY":
                return changeAddress(Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2]), delovi[3], Integer.parseInt(delovi[4]));
            default:
                return "ERROR;Nepoznat zahtev";
        }
    }
    
    private static String checkLogin(String username, String password) {
        EntityManager em = DB.createEntityManager();
        try {
            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findAll", Korisnik.class)
                    .getResultList();

            for (Korisnik k : korisnici) {
                if (k.getUsername().equals(username) && k.getPassword().equals(password)) {
                    return "OK;" + k.getIDKor();
                }
            }
            return "ERROR;Nepostojeci korisnik";
        } finally {
            em.close();
        }
    }
    
    private static String getGradovi(int IDKor) {
        EntityManager em = DB.createEntityManager();
        try {
            Korisnik k = em.find(Korisnik.class, IDKor);
            if (k == null) {
                return "ERROR;Korisnik ne postoji";
            }
            Uloga u = k.getIDUlo();
            if (!"administrator".equals(u.getNaziv())) {
                return "ERROR;Korisnik nema pristup";
            }
            List<Grad> gradovi = em.createNamedQuery("Grad.findAll", Grad.class).getResultList();
            
            StringBuilder sb = new StringBuilder("OK");
            
            for (Grad g : gradovi) {
                sb.append(";").append(g.getIDGra()).append(",").append(g.getNaziv());
            }
            return sb.toString();
        } finally{
            em.close();
        }
    }
    
    private static String createGrad(String naziv, int IDKor) {
        EntityManager em = DB.createEntityManager();
        try {
            em.getTransaction().begin();
            Korisnik k = em.find(Korisnik.class, IDKor);
            if (k == null) {
                em.getTransaction().rollback();
                return "ERROR; Korisnik ne postoji";
            }
            
            Uloga u = k.getIDUlo();
            if (!u.getNaziv().equals("administrator")) {
                em.getTransaction().rollback();
                return "ERROR; Korisnik nema pristup";
            }
            
            List<Grad> postoji = em.createQuery(
                    "SELECT g FROM Grad g WHERE g.naziv = :n", Grad.class)
                    .setParameter("n", naziv)
                    .getResultList();

            if (!postoji.isEmpty()) {
                em.getTransaction().rollback();
                return "ERROR;Grad vec postoji";
            }
            
            Grad g = new Grad();
            g.setNaziv(naziv);
            
            em.persist(g);
            em.getTransaction().commit();
            return "OK;" + g.getIDGra();
        } catch(Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String createUser(String username, String password, String ime, String prezime, String adresa, int IDGra, int IDUlo, int IDKor) {
        EntityManager em = DB.createEntityManager();
        try {
            em.getTransaction().begin();
            Korisnik k1 = em.find(Korisnik.class, IDKor);
            if (k1 == null) {
                em.getTransaction().rollback();
                return "ERROR;Korisnik ne postoji";
            }
            Uloga u1 = k1.getIDUlo();
            if (!u1.getNaziv().equals("administrator")) {
                em.getTransaction().rollback();
                return "ERROR;Korisnik nema pristup";
            }
            
            Grad g = em.find(Grad.class, IDGra);
            if (g == null) {
                em.getTransaction().rollback();
                return "ERROR;Grad ne postoji";
            }
            
            Uloga u = em.find(Uloga.class, IDUlo);
            if (u == null) {
                em.getTransaction().rollback();
                return "ERROR;Uloga ne postoji";
            }
            
            List<Korisnik> postoji = em.createQuery(
                    "SELECT k FROM Korisnik k WHERE k.username = :u", Korisnik.class)
                    .setParameter("u", username)
                    .getResultList();

            if (!postoji.isEmpty()) {
                em.getTransaction().rollback();
                return "ERROR;Username vec postoji";
            }
            
            Korisnik k = new Korisnik();
            k.setUsername(username);
            k.setPassword(password);
            k.setIDGra(g);
            k.setIme(ime);
            k.setPrezime(prezime);
            k.setAdresa(adresa);
            k.setStanjeNovca(0);
            k.setIDUlo(u);
            em.persist(k);
            em.getTransaction().commit();
            return "OK;" + k.getIDKor();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String addMoney(int amount, int IDKor, int IDKorAdmin) {
        EntityManager em = DB.createEntityManager();
        try {
            em.getTransaction().begin();
            if (amount <= 0) {
                em.getTransaction().rollback();
                return "ERROR;Negativni iznos nije dozvoljen";
            }
            
            Korisnik k1 = em.find(Korisnik.class, IDKorAdmin);
            if (k1 == null) {
                em.getTransaction().rollback();
                return "ERROR;ID admina ne postoji";
            }
            Uloga u = k1.getIDUlo();
            if (!u.getNaziv().equals("administrator")) {
                em.getTransaction().rollback();
                return "ERROR;Korisnik nema pristup";
            }
            
            Korisnik k = em.find(Korisnik.class, IDKor);
            if (k == null) {
                em.getTransaction().rollback();
                return "ERROR;Korisnik ne postoji";
            }
            
            k.setStanjeNovca(amount + k.getStanjeNovca());
            em.getTransaction().commit();
            return "OK;Korisniku dodat iznos novca: " + k.getStanjeNovca();
        } catch(Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String getUsers(int IDKor) {
        EntityManager em = DB.createEntityManager();
        try {
            
            Korisnik k1 = em.find(Korisnik.class, IDKor);
            if (k1 == null) {
                return "ERROR;Korisnik ne postoji";
            }
            Uloga u = k1.getIDUlo();
            if (!u.getNaziv().equals("administrator")) {
                return "ERROR;Korisnik nema pristup";
            }
            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findAll", Korisnik.class).getResultList();
            StringBuilder sb = new StringBuilder("OK");
            
            for (Korisnik k : korisnici) {
                sb.append(";").append(k.getIDKor()).append(", ").append(k.getUsername()).append(", ").
                        append(k.getIme()).append(", ").append(k.getPrezime()).append(", ").append(k.getAdresa()).append(", ").append(k.getStanjeNovca()).append(", ").
                        append(k.getIDGra().getNaziv()).append(", ").append(k.getIDUlo().getNaziv());
            }
            return sb.toString();
        } finally {
            em.close();
        }
    }
    
    private static String changeAddress(int IDKorAdmin, int IDKor, String newAddress, int IDGra) {
        EntityManager em = DB.createEntityManager();
        try {
            em.getTransaction().begin();
            Korisnik admin = em.find(Korisnik.class, IDKorAdmin);
            if (admin == null) {
                em.getTransaction().rollback();
                return "ERROR;ID admina ne postoji";
            }
            Uloga u = admin.getIDUlo();
            if (!u.getNaziv().equals("administrator")) {
                em.getTransaction().rollback();
                return "ERROR;Nije administrator";
            }
            
            Korisnik k = em.find(Korisnik.class, IDKor);
            if (k == null) {
                em.getTransaction().rollback();
                return "ERROR;Korisnik ne postoji";
            }
            Grad g = em.find(Grad.class, IDGra);
            if (g == null) {
                em.getTransaction().rollback();
                return "ERROR;Grad ne postoji";
            }
            k.setAdresa(newAddress);
            k.setIDGra(g);
            em.getTransaction().commit();
            return "OK;Adresa i grad promenjeni";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally{
            em.close();
        }
    }
}
