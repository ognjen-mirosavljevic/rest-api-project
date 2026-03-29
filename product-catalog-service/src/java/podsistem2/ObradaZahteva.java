/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entities.Artikal;
import entities.Kategorija;
import entities.Korisnik;
import entities.Korpa;
import entities.ListaZelja;
import entities.StavkaKorpa;
import entities.StavkaKorpaPK;
import entities.StavkaListeZelja;
import entities.StavkaListeZeljaPK;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.jboss.weld.bootstrap.spi.EEModuleDescriptor;

/**
 *
 * @author ognje
 */
public class ObradaZahteva {
    public static String obradi(String zahtev) {
        String [] delovi = zahtev.split(";");
        
        String metoda = delovi[0];
        
        switch(metoda) {
            case "CREATE_CATEGORY":
                Integer idKatNat;
                if (delovi.length > 2 && delovi[2] != null && !delovi[2].isEmpty()) {
                    idKatNat = Integer.parseInt(delovi[2]);
                } else {
                    idKatNat = 0;
                }
                return createCategory(delovi[1], idKatNat);
                
            case "CREATE_ARTICLE":
                return createArticle(delovi[1], delovi[2], Double.parseDouble(delovi[3]), Integer.parseInt(delovi[4]), Integer.parseInt(delovi[5]));
                
            case "CHANGE_PRICE":
                return changePrice(Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2]), Integer.parseInt(delovi[3]));
                
            case "SET_POPUST":
                return setPopust(Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2]), Integer.parseInt(delovi[3]));
                
            case "ADD_TO_CART":
                return addArticleToCart(Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2]), Integer.parseInt(delovi[3]));
                
            case "DELETE_FROM_CART":
                return deleteArticleFromCart(Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2]), Integer.parseInt(delovi[3]));
                
            case "ADD_TO_WISH_LIST":
                return addArticleToWishList(Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2]));
                
            case "DELETE_FROM_WISH_LIST":
                return deleteArticleFromWishList(Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2]));
                
            case "GET_CATEGORIES":
                return getCategories();
                
            case "GET_ARTICLES":
                return getArticles(Integer.parseInt(delovi[1]));
                
            case "GET_CART":
                return getCart(Integer.parseInt(delovi[1]));
                
            case "GET_WISH_LIST":
                return getWishList(Integer.parseInt(delovi[1]));
                
            case "EMPTY_CART":
                return emptyCart(Integer.parseInt(delovi[1]));
                
            case "ADD_USER_ID":
                return addUserID(Integer.parseInt(delovi[1]));
            default:
                return "ERROR;Nepostojeca komanda";
        }
    }
    
    
    private static String createCategory(String naziv, int IDNat) {
        EntityManager em = DB.createEntityManager();

        try {
            em.getTransaction().begin();

            List<Kategorija> lista = em.createQuery(
                    "SELECT k FROM Kategorija k WHERE k.naziv = :naziv",
                    Kategorija.class)
                    .setParameter("naziv", naziv)
                    .getResultList();

            if (!lista.isEmpty()) {
                em.getTransaction().rollback();
                return "ERROR;Kategorija vec postoji";
            }

            Kategorija k = new Kategorija();
            k.setNaziv(naziv);

            if (IDNat != 0) {
                Kategorija parent = em.find(Kategorija.class, IDNat);
                if (parent == null) {
                    em.getTransaction().rollback();
                    return "ERROR;Nepostojeca nadkategorija";
                }
                k.setIDKatNat(parent);
            } else {
                k.setIDKatNat(null);
            }

            em.persist(k);
            em.getTransaction().commit();
            return "OK;Uspesno kreiranje kategorije";

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String createArticle(String naziv, String opis, double cena, int IDKat, int IDKor) {
        EntityManager em = DB.createEntityManager();
        
        try {
            em.getTransaction().begin();
            
            if (cena <= 0) {
                em.getTransaction().rollback();
                return "ERROR;Neadekvatna cena";
            }
            Korisnik prodavac = em.find(Korisnik.class, IDKor);
            if (prodavac == null) {
                em.getTransaction().rollback();
                return "ERROR;Korisnik ne postoji";
            }
            Kategorija kat = em.find(Kategorija.class, IDKat);
            if (kat == null) {
                em.getTransaction().rollback();
                return "ERROR;Kategorija ne postoji";
            }
            
            Artikal a = new Artikal();
            a.setNaziv(naziv);
            a.setOpis(opis);
            a.setCena(cena);
            a.setIDKat(kat);
            a.setIDKor(prodavac);
            em.persist(a);
            em.getTransaction().commit();
            return "OK;" + a.getIDArt();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String changePrice(int IDArt, int cena, int IDKor) {
        EntityManager em = DB.createEntityManager();

        try {
            em.getTransaction().begin();

            if (cena < 0) {
                em.getTransaction().rollback();
                return "ERROR;Nevalidna cena";
            }

            Artikal art = em.find(Artikal.class, IDArt);
            if (art == null) {
                em.getTransaction().rollback();
                return "ERROR;Artikal ne postoji";
            }

            if (art.getIDKor() == null || !art.getIDKor().getIDKor().equals(IDKor)) {
                em.getTransaction().rollback();
                return "ERROR;Korisnik nije vlasnik artikla";
            }

            art.setCena(cena);
            em.getTransaction().commit();
            return "OK;Uspesna promena cene";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String setPopust(int IDArt, int popust, int IDKor) {
        EntityManager em = DB.createEntityManager();

        try {
            em.getTransaction().begin();

            if (popust < 0 || popust > 100) {
                em.getTransaction().rollback();
                return "ERROR;Nevalidan popust";
            }

            Artikal art = em.find(Artikal.class, IDArt);
            if (art == null) {
                em.getTransaction().rollback();
                return "ERROR;Artikal ne postoji";
            }

            if (art.getIDKor() == null || !art.getIDKor().getIDKor().equals(IDKor)) {
                em.getTransaction().rollback();
                return "ERROR;Korisnik nije vlasnik artikla";
            }

            art.setPopust(popust);
            em.getTransaction().commit();
            return "OK;Uspesna postavka popusta";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String addArticleToCart(int IDArt, int IDKor, int kolicina) {
        EntityManager em = DB.createEntityManager();
        
        try {
            em.getTransaction().begin();
            if (kolicina <= 0) {
                em.getTransaction().rollback();
                return "ERROR;Nevalidna kolicina";
            }
            
            Korpa korpa = em.createQuery(
                    "SELECT k FROM Korpa k WHERE k.iDKor = :idKor",
                    Korpa.class).setParameter("idKor", IDKor)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            
            if (korpa == null) {
                Korisnik korisnik = em.find(Korisnik.class, IDKor);
                if (korisnik == null) {
                    em.getTransaction().rollback();
                    return "ERROR;Korisnik ne postoji";
                }

                korpa = new Korpa();
                korpa.setIDKor(IDKor);
                korpa.setKorisnik(korisnik);
                korpa.setCena(0);
                em.persist(korpa);
            }
            
            Artikal art = em.find(Artikal.class, IDArt);
            if (art == null) {
                em.getTransaction().rollback();
                return "ERROR;Artiakl ne postoji";
            }
            
            StavkaKorpaPK pk = new StavkaKorpaPK();
            pk.setIDArt(IDArt);
            pk.setIDKor(IDKor);
            
            StavkaKorpa stavka = em.find(StavkaKorpa.class, pk);
            
            if (stavka != null) {
                stavka.setKolicina(stavka.getKolicina() + kolicina);
            } else {
                stavka = new StavkaKorpa();
                stavka.setStavkaKorpaPK(pk);
                stavka.setArtikal(art);
                stavka.setKorpa(korpa);
                stavka.setKolicina(kolicina);
                
                em.persist(stavka);
            }
            double efektivnaCena = art.getCena() * (100 - art.getPopust()) / 100.0;
            korpa.setCena(korpa.getCena() + efektivnaCena * kolicina);
            
            em.getTransaction().commit();
            return "OK;Artikal dodat u korpu";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String deleteArticleFromCart(int IDArt, int IDKor, int kolicina) {
        EntityManager em = DB.createEntityManager();
        
        try {
            em.getTransaction().begin();
            
            if (kolicina <= 0) {
                em.getTransaction().rollback();
                return "ERROR;Nevalidna kolicina";
            }
            
            StavkaKorpaPK pk = new StavkaKorpaPK();
            pk.setIDArt(IDArt);
            pk.setIDKor(IDKor);
            
            StavkaKorpa stavka = em.find(StavkaKorpa.class, pk);
            
            if (stavka == null) {
                em.getTransaction().rollback();
                return "ERROR;Artikal se ne nalazi u korpi";
            }
            
            if (kolicina >= stavka.getKolicina()) {
                em.remove(stavka);
            } else {
                stavka.setKolicina(stavka.getKolicina() - kolicina);
            }
            
            Artikal art = stavka.getArtikal();
            double efektivnaCena = art.getCena() * (100 - art.getPopust()) / 100.0;
            
            Korpa korpa = stavka.getKorpa();
            korpa.setCena(korpa.getCena() - efektivnaCena * Math.min(kolicina, stavka.getKolicina()));
            if (korpa.getCena() < 0) {
                korpa.setCena(0);
            }
            
            em.getTransaction().commit();
            return "OK;Artikal uklonjen iz korpe";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String addArticleToWishList(int IDArt, int IDKor) {
        EntityManager em = DB.createEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Korisnik korisnik = em.find(Korisnik.class, IDKor);
            if (korisnik == null) {
                em.getTransaction().rollback();
                return "ERROR;Korisnik ne postoji";
            }
            
            ListaZelja wishlist = em.find(ListaZelja.class, IDKor);
            
            if (wishlist == null) {
                wishlist = new ListaZelja();
                wishlist.setIDKor(korisnik.getIDKor());
                wishlist.setDatum(new Date());
                em.persist(wishlist);
            }
            
            Artikal art = em.find(Artikal.class, IDArt);
            if (art == null) {
                em.getTransaction().rollback();
                return "ERROR;Artikal ne postoji";
            }
            
            StavkaListeZeljaPK pk = new StavkaListeZeljaPK();
            pk.setIDArt(IDArt);
            pk.setIDKor(IDKor);
            
            StavkaListeZelja stavka = em.find(StavkaListeZelja.class, pk);
            if (stavka != null) {
                em.getTransaction().rollback();
                return "ERROR;Artikal je vec u listi zelja";
            }
            
            stavka = new StavkaListeZelja();
            stavka.setStavkaListeZeljaPK(pk);
            stavka.setArtikal(art);
            stavka.setListaZelja(wishlist);
            stavka.setVremeDodavanja(new Date());
            
            em.persist(stavka);
            em.getTransaction().commit();
            return "OK;Artikal dodat u listu zelja";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String deleteArticleFromWishList(int IDArt, int IDKor) {
        EntityManager em = DB.createEntityManager();
        
        try {
            em.getTransaction().begin();
            
            StavkaListeZeljaPK pk = new StavkaListeZeljaPK();
            pk.setIDArt(IDArt);
            pk.setIDKor(IDKor);
            
            StavkaListeZelja stavka = em.find(StavkaListeZelja.class, pk);
            
            if (stavka == null) {
                em.getTransaction().rollback();
                return "ERROR;Artikal nije u listi zelja";
            }
            em.remove(stavka);
            em.getTransaction().commit();
            return "OK;Artikal uklonjen iz liste zelja";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String getCategories() {
        EntityManager em = DB.createEntityManager();
        
        try {
            List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
            
            StringBuilder sb = new StringBuilder("OK");
            for (Kategorija k : kategorije) {
                sb.append(";").append(k.getIDKat()).append(", ")
                        .append(k.getNaziv()).append(", ");
                
                if (k.getIDKatNat() != null) {
                    sb.append(k.getIDKatNat().getIDKat());
                } else {
                    sb.append("null");
                }
            }
            
            return sb.toString();
        } catch (Exception e) {
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    
    private static String getArticles(int IDKor) {
        EntityManager em = DB.createEntityManager();
        
        try {
            Korisnik korisnik = em.find(Korisnik.class, IDKor);
            
            if (korisnik == null) {
                return "ERROR;Korisnik ne postoji";
            }
            
            List<Artikal> artikli = em.createQuery(
                    "SELECT a FROM Artikal a WHERE a.iDKor.iDKor =  :idKor",
                    Artikal.class)
                    .setParameter("idKor", IDKor)
                    .getResultList();
            
            StringBuilder sb = new StringBuilder("OK");
            for (Artikal a : artikli) {
                sb.append(";")
                        .append(a.getIDArt()).append(", ")
                        .append(a.getNaziv()).append(", ")
                        .append(a.getCena()).append(", ")
                        .append(a.getPopust()).append(", ")
                        .append(a.getIDKat().getNaziv());
            }
            
            return sb.toString();
        } catch (Exception e) {
          return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String getCart(int IDKor) {
        EntityManager em = DB.createEntityManager();

        try {
            Korisnik korisnik = em.find(Korisnik.class, IDKor);
            if (korisnik == null) {
                return "ERROR;Korisnik ne postoji";
            }

            List<StavkaKorpa> stavke = em.createQuery(
                    "SELECT s FROM StavkaKorpa s WHERE s.stavkaKorpaPK.iDKor = :idKor",
                    StavkaKorpa.class)
                    .setParameter("idKor", IDKor)
                    .getResultList();

            StringBuilder sb = new StringBuilder("OK");

            for (StavkaKorpa s : stavke) {
                Artikal a = s.getArtikal();
                double efektivnaCena = a.getCena() * (100 - a.getPopust()) / 100.0;

                sb.append(";")
                  .append(a.getIDArt()).append(", ")
                  .append(a.getNaziv()).append(", ")
                  .append(efektivnaCena).append(", ")
                  .append(s.getKolicina());
            }

            return sb.toString();
        } catch (Exception e) {
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String getWishList(int IDKor) {
        EntityManager em = DB.createEntityManager();
        
        try {
            Korisnik korisnik = em.find(Korisnik.class, IDKor);
            if (korisnik == null) {
                return "ERROR;Korisnik ne postoji";
            }
            
            List<StavkaListeZelja> stavke = em.createQuery(
                    "SELECT s FROM StavkaListeZelja s WHERE s.stavkaListeZeljaPK.iDKor = :idKor",
                    StavkaListeZelja.class)
                    .setParameter("idKor", IDKor)
                    .getResultList();
            
            StringBuilder sb = new StringBuilder("OK");
            
            for (StavkaListeZelja s : stavke) {
                Artikal a = s.getArtikal();
                
                sb.append(";")
                    .append(a.getIDArt()).append(",")
                    .append(a.getNaziv()).append(",")
                    .append(a.getCena()).append(",")
                    .append(s.getVremeDodavanja());
            }
            
            return sb.toString();
        } catch (Exception e) {
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String emptyCart(int IDKor) {
        EntityManager em = DB.createEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Korisnik korisnik = em.find(Korisnik.class, IDKor);
            if (korisnik == null) {
                em.getTransaction().rollback();
                return "ERROR;Korisnik ne postoji";
            }
            
            Korpa korpa = em.find(Korpa.class, IDKor);
            if (korpa != null) {
                korpa.setCena(0);
            }
            
            em.createQuery(
                "DELETE FROM StavkaKorpa s WHERE s.stavkaKorpaPK.iDKor = :idKor")
                .setParameter("idKor", IDKor)
                .executeUpdate();
            em.getTransaction().commit();
            return "OK;Korpa ispraznjena";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    private static String addUserID(int idKor) {
        EntityManager em = DB.createEntityManager();
        try {
            em.getTransaction().begin();

            Korisnik k = new Korisnik();
            k.setIDKor(idKor);

            em.persist(k);
            em.getTransaction().commit();
            return "OK;Dodat korisnik";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return "ERROR;" + e.getMessage();
        } finally {
            em.close();
        }
    }
}
