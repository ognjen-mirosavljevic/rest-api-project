/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PS3Resource;

import java.util.ArrayList;
import java.util.List;
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
@Path("orders")
public class NarudzbinaResource {
    
    @GET
    @Path("all")
    public Response getOrders() {
        String odgovor = JMSHelper.send("GET_ORDERS;", "podsistem3Queue");
        return Response.ok(odgovor).build();
    }
    
    @GET
    @Path("{IDKor}")
    public Response getOrdersFromUser(@PathParam("IDKor") int IDKor) {
        String zahtev = "GET_ORDERS_FROM_USER;" + IDKor;
        String odgovor = JMSHelper.send(zahtev, "podsistem3Queue");
        return Response.ok(odgovor).build();
    }
    
}
