package rest;

import com.google.gson.Gson;
import entity.User;
import entity.history.History;
import facade.HistoryDB;
//import entity.Wish;
import fetch.ParallelPinger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.PuSelector;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {

    HistoryDB hdb = new HistoryDB();
    Gson gson = new Gson();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("/availablecars/{week}/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCarsAtAddressAndWeek(@PathParam("week") String week, @PathParam("address") String address) throws Exception {
        System.out.println("week:" + week + "  address:" + address);
        hdb.addSearchHistory(new History("week=" + week + ",addr=" + address));
        String json = ParallelPinger.getJsonFromAllServers("week=" + week + "&addr=" + address);
        return json;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("history")
    public String getAllHistories() {
        return gson.toJson(hdb.getAllHistories());
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {
        EntityManager em = PuSelector.getEntityManagerFactory("pu").createEntityManager();
        try {
            List<User> users = em.createQuery("select user from User user").getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() throws Exception {
        String thisUser = securityContext.getUserPrincipal().getName();
        return "{\"name\":\"" + thisUser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisUser = securityContext.getUserPrincipal().getName();

        return "{\"name\":\"" + thisUser + "\"}";
    }
}
