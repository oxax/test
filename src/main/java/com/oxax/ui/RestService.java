/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oxax.ui;

import com.oxax.persistence.entity.OnlineStore;
import com.oxax.persistence.service.PersistenceService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Morph
 */
@Path("shop")
@RequestScoped
public class RestService implements Serializable {
    
    @EJB
    private PersistenceService service;

    @GET
    @Path("/stores")
    @Produces({"application/json"})
    public Response getAllOnlineStores() {
        List<OnlineStore> stores = new ArrayList<>();
        stores = service.getAllOnlineStore();
        return Response.ok(stores).build();
    }
    
    
    @GET
    @Path("/stores/{id}")
    @Produces({"application/json"})
    public Response getOnlineStore(@PathParam("id") Long id) {
        OnlineStore store = service.find(OnlineStore.class, id);
        return Response.ok(store).build();
    }
}
