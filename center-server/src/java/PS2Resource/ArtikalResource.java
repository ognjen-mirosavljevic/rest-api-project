/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PS2Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.JMSHelper;

/**
 *
 * @author ognje
 */
@Path("artikli")
public class ArtikalResource {
    
    @POST
    @Path("category")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createCategory(@FormParam("naziv") String naziv,
            @FormParam("IDNat") int IDNat) {
        
        String zahtev = "CREATE_CATEGORY;" + naziv + ";" + IDNat;
        String odgovor = JMSHelper.send(zahtev, "podsistem2Queue");
        
        return Response.ok(odgovor).build();
    }
    
    //createArticle(String naziv, String opis, double cena, int IDKat, int IDKor)
    @POST
    @Path("create_artikli")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createArticle(@FormParam("naziv") String naziv,
            @FormParam("opis") String opis,
            @FormParam("cena") Double cena,
            @FormParam("IDKat") int IDKat,
            @FormParam("IDKor") int IDKor) {
        
        String zahtev = "CREATE_ARTICLE;" + naziv + ";" + opis + ";" + cena + ";" + IDKat + ";" + IDKor;
        String odgovor1 = JMSHelper.send(zahtev, "podsistem2Queue");
        if (!odgovor1.startsWith("OK;")) {
            return Response.ok(odgovor1).build();
        }
        
        String[] delovi = odgovor1.split(";");
        int id = Integer.parseInt(delovi[1]);
        
        String odgovor2 = JMSHelper.send("ADD_ARTICLE_ID;" + id, "podsistem3Queue");
        if (!odgovor2.startsWith("OK;")) return Response.ok(odgovor2).build();
        
        return Response.ok(odgovor1).build();
    }
    
    //changePrice(int IDArt, int cena)
    @POST
    @Path("price")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response changePrice(@FormParam("IDArt") int IDArt,
            @FormParam("cena") int cena,
            @FormParam("IDKor") int IDKor) {
        
        String zahtev = "CHANGE_PRICE;" + IDArt + ";" + cena + ";" + IDKor;
        String odgovor = JMSHelper.send(zahtev, "podsistem2Queue");
        
        return Response.ok(odgovor).build();
    }
    
    //setPopust(int IDArt, int popust)
    @POST
    @Path("discount")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response changeDiscount(@FormParam("IDArt") int IDArt,
            @FormParam("popust") int popust,
            @FormParam("IDKor") int IDKor) {
        
        String zahtev = "SET_POPUST;" + IDArt + ";" + popust + ";" + IDKor;
        String odgovor = JMSHelper.send(zahtev, "podsistem2Queue");
        
        return Response.ok(odgovor).build();
    }
    
    @GET
    @Path("categories")
    public Response getCategories() {
        String odgovor = JMSHelper.send("GET_CATEGORIES", "podsistem2Queue");
        return Response.ok(odgovor).build();
    }
    
    @GET
    @Path("{IDKor}")
    public Response getArticles(@PathParam("IDKor") int IDKor) {
        String zahtev = "GET_ARTICLES;" + IDKor;
        String odgovor = JMSHelper.send(zahtev, "podsistem2Queue");
        return Response.ok(odgovor).build();
    }
    
}
