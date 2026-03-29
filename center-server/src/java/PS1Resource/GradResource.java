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
@Path("gradovi")
public class GradResource {
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createGrad(@FormParam("naziv") String naziv,
            @FormParam("IDKor") int idKor) {
        
        String zahtev = "CREATE_GRAD;" + naziv + ";" + idKor;
        
        String odgovor1 = JMSHelper.send(zahtev, "podsistem1Queue");
        if (!odgovor1.startsWith("OK;")) {
            return Response.ok(odgovor1).build();
        }
        String[] delovi = odgovor1.split(";");
        int id = Integer.parseInt(delovi[1]);
        String odgovor2 = JMSHelper.send("ADD_GRAD_ID;" + id, "podsistem3Queue");
        if (!odgovor2.startsWith("OK;")) {
            return Response.ok(odgovor2).build();
        }
        return Response.ok(odgovor1).build();
    }
    
    @GET
    @Path("{idKor}")
    public Response getGradovi(@PathParam("idKor") int idKor) {
        String zahtev = "GET_GRADOVI;" + idKor;
        
        String odgovor = JMSHelper.send(zahtev, "podsistem1Queue");
        return Response.ok(odgovor).build();
    }
}
