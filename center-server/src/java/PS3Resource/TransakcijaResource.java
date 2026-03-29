/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PS3Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.JMSHelper;

/**
 *
 * @author ognje
 */
@Path("transaction")
public class TransakcijaResource {
    
    @GET
    @Path("all")
    public Response getTransactions() {
        String odgovor = JMSHelper.send("GET_TRANSACTIONS", "podsistem3Queue");
        return Response.ok(odgovor).build();
    }
    
    @POST
    @Path("payment")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response makePayment(@FormParam("IDKor") int IDKor,
            @FormParam("IDGra") int IDGra,
            @FormParam("adresa") String adresa) {
        
        System.out.println("USAO U PAYMENT");
        String zahtev1 = "GET_CART;" + IDKor;
        System.out.println(zahtev1);
        String odgovor1 = JMSHelper.send(zahtev1, "podsistem2Queue");
        System.out.println(odgovor1);
        if (!odgovor1.startsWith("OK;")) return Response.ok(odgovor1).build();
        
        String[] delovi = odgovor1.split(";");

        StringBuilder stavke = new StringBuilder();

        for (int i = 1; i < delovi.length; i++) {

            String[] podaci = delovi[i].split(", ");

            int idArt = Integer.parseInt(podaci[0]);
            int kolicina = Integer.parseInt(podaci[3]);
            double cena = Double.parseDouble(podaci[2]);

            stavke.append(idArt)
                  .append(":")
                  .append(kolicina)
                  .append(":")
                  .append(cena)
                  .append("|");
        }

        if (stavke.length() == 0) {
            return Response.ok("ERROR;Korpa je prazna").build();
        }
        stavke.deleteCharAt(stavke.length() - 1);
        
        String zahtev2 = "PAYMENT;" + IDKor + ";" + IDGra + ";" + adresa + ";" + stavke.toString();
        System.out.println("ZAHTEV2 = " + zahtev2);
        String odgovor2 = JMSHelper.send(zahtev2, "podsistem3Queue");
        
        if (odgovor2.startsWith("OK;")) {
            System.out.println("USAO U GRANU");
            String zahtev3 = "EMPTY_CART;" + IDKor;
            System.out.println(zahtev1);
            String odgovor3 = JMSHelper.send(zahtev3, "podsistem2Queue");
            System.out.println(odgovor3);
        }
        
        return Response.ok(odgovor2).build();
    }
}
