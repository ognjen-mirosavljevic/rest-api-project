/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PS1Resource;

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
@Path("korisnici")
public class KorisnikResource {
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(@FormParam("username") String username,
            @FormParam("password") String password) {
        
        
        String zahtev = "CHECK_USER;" + username + ";" + password;
        String odgovor = JMSHelper.send(zahtev, "podsistem1Queue");
        
        return Response.ok(odgovor).build();
    }
    
    //private static String createUser(String username, String password, String ime, String prezime, String adresa, int IDGra, int IDUlo, int IDKor)
    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createUser(@FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("ime") String ime,
            @FormParam("prezime") String prezime,
            @FormParam("adresa") String adresa,
            @FormParam("IDGra") int IDGra,
            @FormParam("IDUlo") int IDUlo,
            @FormParam("IDKor") int IDKor) {
        
        String zahtev = "CREATE_USER;" + username + ";" + password + ";" + ime + ";" + prezime + ";" + adresa + ";" + IDGra + ";" + IDUlo + ";" + IDKor;
        String odgovor1 = JMSHelper.send(zahtev, "podsistem1Queue");
        if (!odgovor1.startsWith("OK;")) {
            return Response.ok(odgovor1).build();
        }

        String[] delovi = odgovor1.split(";");
        int idKor = Integer.parseInt(delovi[1]);
        
        String odgovor2 = JMSHelper.send("ADD_USER_ID;" + idKor, "podsistem2Queue");
        String odgovor3 = JMSHelper.send("ADD_USER_ID;" + idKor, "podsistem3Queue");
        
        if (!odgovor2.startsWith("OK;")) return Response.ok(odgovor2).build();
        if (!odgovor3.startsWith("OK;")) return Response.ok(odgovor3).build();

        return Response.ok(odgovor1).build();
    }

    //addMoney(int amount, int IDKor, int IDKorAdmin)
    @POST
    @Path("addMoney")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addMoney(@FormParam("amount") int amount,
            @FormParam("IDKor") int IDKor,
            @FormParam("IDKorAdmin") int IDKorAdmin) {
        
        String zahtev = "ADD_MONEY;" + amount + ";" + IDKor + ";" + IDKorAdmin;
        String odgovor = JMSHelper.send(zahtev, "podsistem1Queue");
        
        return Response.ok(odgovor).build();
    }
    
    //changeAddress(int IDKorAdmin, int IDKor, String newAddress, int IDGra)
    @POST
    @Path("address")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response changeAddress(@FormParam("IDKorAdmin") int IDKorAdmin,
            @FormParam("IDKor") int IDKor,
            @FormParam("newAddress") String newAddress,
            @FormParam("IDGra") int IDGra) {
        
        String zahtev = "CHANGE_ADDRESS_AND_CITY;" + IDKorAdmin + ";" + IDKor + ";" + newAddress + ";" + IDGra;
        String odgovor = JMSHelper.send(zahtev, "podsistem1Queue");
        
        return Response.ok(odgovor).build();
    }
    
    @GET
    @Path("{idKor}")
    public Response getUsers(@PathParam("idKor") int idKor) {
        String zahtev = "GET_USERS;" + idKor;
        
        String odgovor = JMSHelper.send(zahtev, "podsistem1Queue");
        return Response.ok(odgovor).build();
    }
}
