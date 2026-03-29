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
@Path("cart")
public class KorpaResource {
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {
        return Response.ok("cart radi").build();
    }
    //addArticleToCart(int IDArt, int IDKor, int kolicina)
    @POST
    @Path("cart")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addToCart(@FormParam("IDArt") int IDArt,
            @FormParam("IDKor") int IDKor,
            @FormParam("kolicina") int kolicina) {
        
        String zahtev = "ADD_TO_CART;" + IDArt + ";" + IDKor + ";" + kolicina;
        String odgovor = JMSHelper.send(zahtev, "podsistem2Queue");
        
        return Response.ok(odgovor).build();
    }
    
    //deleteArticleFromCart(int IDArt, int IDKor, int kolicina)
    @POST
    @Path("remove_cart")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeFromCart(@FormParam("IDArt") int IDArt,
            @FormParam("IDKor") int IDKor,
            @FormParam("kolicina") int kolicina) {
        
        String zahtev = "DELETE_FROM_CART;" + IDArt + ";" + IDKor + ";" + kolicina;
        String odgovor = JMSHelper.send(zahtev, "podsistem2Queue");
        
        return Response.ok(odgovor).build();
    }
    
    //addArticleToWishList(int IDArt, int IDKor)
    @POST
    @Path("wishlist")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addToWishlist(@FormParam("IDArt") int IDArt,
            @FormParam("IDKor") int IDKor) {
        String zahtev = "ADD_TO_WISH_LIST;" + IDArt + ";" + IDKor;
        String odgovor = JMSHelper.send(zahtev, "podsistem2Queue");
        
        return Response.ok(odgovor).build();
    }
    
    //deleteArticleFromWishList(int IDArt, int IDKor)
    @POST
    @Path("remove_wishlist")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeFromWishlist(@FormParam("IDArt") int IDArt,
            @FormParam("IDKor") int IDKor) {
        String zahtev = "DELETE_FROM_WISH_LIST;" + IDArt + ";" + IDKor;
        String odgovor = JMSHelper.send(zahtev, "podsistem2Queue");
        
        return Response.ok(odgovor).build();
    }
    
    @GET
    @Path("{IDKor}")
    public Response getCart(@PathParam("IDKor") int IDKor) {
        String zahtev = "GET_CART;" + IDKor;
        String odgovor = JMSHelper.send(zahtev, "podsistem2Queue");
        return Response.ok(odgovor).build();
    }
    
    @GET
    @Path("wishlist/{IDKor}")
    public Response getWishlist(@PathParam("IDKor") int IDKor) {
        String zahtev = "GET_WISH_LIST;" + IDKor;
        String odgovor = JMSHelper.send(zahtev, "podsistem2Queue");
        return Response.ok(odgovor).build();
    }
}
