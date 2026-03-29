/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import entities.Artikal;
import entities.Grad;
import entities.Korisnik;
import entities.Narudzbina;
import entities.StavkaNarudzbina;
import entities.StavkaNarudzbinaPK;
import entities.Transakcija;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;



/**
 *
 * @author ognje
 */
public class ObradaZahteva {
    public static String obradi(String zahtev) {
            String [] delovi = zahtev.split(";");
            
            String metoda = delovi[0];
            
            switch (metoda) {
                case "PAYMENT":
                    int idKor = Integer.parseInt(delovi[1]);
                    int idGra = Integer.parseInt(delovi[2]);
                    String adresa = delovi[3];
                    String[] stavkeString = delovi[4].split("\\|");
                    List<Object[]> stavke = new ArrayList<>();
                    
                    for (String s : stavkeString) {
                        String[] podaci = s.split(":");
                        int idArt = Integer.parseInt(podaci[0]);
                        int kolicina = Integer.parseInt(podaci[1]);
                        double cena = Double.parseDouble(podaci[2]);
                        stavke.add(new Object[]{idArt, kolicina, cena});
                    }
                    
                   return payment(idKor, idGra, adresa, stavke);
                   
                   
                    
                case "GET_ORDERS_FROM_USER":
                    return getOrdersFromUser(Integer.parseInt(delovi[1]));
                    
                case "GET_ORDERS":
                    return getOrders();
                    
                case "GET_TRANSACTIONS":
                    return getTransactions();
                    
                case "ADD_USER_ID":
                    return addUserID(Integer.parseInt(delovi[1]));
                    
                case "ADD_GRAD_ID":
                    return addGradID(Integer.parseInt(delovi[1]));
                    
                case "ADD_ARTICLE_ID":
                    return addArticleID(Integer.parseInt(delovi[1]));
    
                default:
                    return "ERROR;Nepostojeca komanda";
                    
            }
    }
    
    private static String payment(int IDKor, int IDGra, String address, List<Object[]> stavke) {
        EntityManager em = DB.createEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Korisnik korisnik = em.find(Korisnik.class, IDKor);
            if (korisnik == null) {
                em.getTransaction().rollback();
                return "ERROR;Korisnik ne postoji";
            }
            
            Grad grad = em.find(Grad.class, IDGra);
            if (grad == null) {
                em.getTransaction().rollback();
                return "ERROR;Grad ne postoji";
            }
            
            if (stavke == null || stavke.isEmpty()) {
                em.getTransaction().rollback();
                return "ERROR;Korpa je prazna";
            }
            
            double ukupnaCena = 0.0;
            for (Object[] s : stavke) {
                int idArt =  (Integer)s[0];
                int kolicina = (Integer)s[1];
                double cena = (Double)s[2];
                if (kolicina <= 0 || cena < 0) {
                    em.getTransaction().rollback();
                    return "ERROR;Neispravna stavka";
                }
                ukupnaCena += kolicina * cena;
            }
            
            Narudzbina n = new Narudzbina();
            n.setIDGra(grad);
            n.setIDKor(korisnik);
            n.setAdresa(address);
            n.setUkupnaCena(ukupnaCena);
            n.setVreme(new Date());
            
            em.persist(n);
            em.flush();
            
            for (Object[] s : stavke) {
                int idArt =  (Integer)s[0];
                int kolicina = (Integer)s[1];
                double cena = (Double)s[2];
                Artikal art = em.find(Artikal.class, idArt);
                if (art == null) {
                    em.getTransaction().rollback();
                    return "ERROR;Artikal ne postoji";
                }
                
                StavkaNarudzbina sn = new StavkaNarudzbina();
                StavkaNarudzbinaPK pk = new StavkaNarudzbinaPK();
                pk.setIDArt(idArt);
                pk.setIDNar(n.getIDNar());
                
                sn.setStavkaNarudzbinaPK(pk);
                sn.setArtikal(art);
                sn.setNarudzbina(n);
                sn.setKolicina(kolicina);
                sn.setJedinicnaCena(cena);
                
                em.persist(sn);
            }
            
            Transakcija t = new Transakcija();
            t.setIDNar(n);
            t.setSuma(ukupnaCena);
            t.setVreme(new Date());
            
            em.persist(t);
            em.getTransaction().commit();
            
            return "OK;Transakcija uspesno zavrsena";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String getOrdersFromUser(int IDKor) {
        EntityManager em = DB.createEntityManager();
        
        try {
            Korisnik k = em.find(Korisnik.class, IDKor);
            if (k == null) {
                return "ERROR;Korisnik ne postoji";
            }
            
            List<Narudzbina> lista = em.createQuery(
                    "SELECT n FROM Narudzbina n WHERE n.iDKor.iDKor = :idKor",
                    Narudzbina.class)
                    .setParameter("idKor", IDKor)
                    .getResultList();
            
            StringBuilder sb = new StringBuilder("OK");
            
            for (Narudzbina n : lista) {
                sb.append(";")
                        .append(n.getIDNar()).append(", ")
                        .append(n.getUkupnaCena()).append(", ")
                        .append(n.getVreme()).append(", ")
                        .append(n.getAdresa()).append(", ")
                        .append(n.getIDGra());
            }
            
            return sb.toString();
        } catch (Exception e) {
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String getOrders() {
        EntityManager em = DB.createEntityManager();
        
        try {
            List<Narudzbina> lista = em.createNamedQuery(
                    "Narudzbina.findAll",
                    Narudzbina.class)
                    .getResultList();
            
            StringBuilder sb = new StringBuilder("OK");
            
            for (Narudzbina n : lista) {
                sb.append(";")
                        .append(n.getIDNar()).append(", ")
                        .append(n.getUkupnaCena()).append(", ")
                        .append(n.getVreme()).append(", ")
                        .append(n.getAdresa()).append(", ")
                        .append(n.getIDGra()).append(", ")
                        .append(n.getIDKor().getIDKor());
            }
            
            return sb.toString();
        } catch (Exception e) {
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String getTransactions() {
        EntityManager em = DB.createEntityManager();
        
        try {
            List<Transakcija> lista = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
            
            StringBuilder sb = new StringBuilder("OK");
            
            for (Transakcija t : lista) {
                sb.append(";")
                        .append(t.getIDTra()).append(", ")
                        .append(t.getSuma()).append(", ")
                        .append(t.getVreme()).append(", ")
                        .append(t.getIDNar().getIDNar());
            }
            
            return sb.toString();
        } catch (Exception e) {
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    
    private static String addUserID(int idKor) {
        EntityManager em = DB.createEntityManager();
        try {
            em.getTransaction().begin();
            System.out.println("usao u podsistem3");

            Korisnik k = new Korisnik();
            k.setIDKor(idKor);

            em.persist(k);
            em.getTransaction().commit();
            System.out.println("Uspesan commit");
            return "OK;Dodat korisnik";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String addGradID(int idGra) {
        EntityManager em = DB.createEntityManager();
        try {
            em.getTransaction().begin();

            Grad g = new Grad();
            g.setIDGra(idGra);

            em.persist(g);
            em.getTransaction().commit();

            return "OK;Dodat grad";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String addArticleID(int idArt) {
        EntityManager em = DB.createEntityManager();
        
        try {
            em.getTransaction().begin();
            Artikal art = new Artikal();
            art.setIDArt(idArt);
            em.persist(art);
            em.getTransaction().commit();
            
            return "OK;Dodat artikal";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        }
        finally {
            em.close();
        }
    }
}
