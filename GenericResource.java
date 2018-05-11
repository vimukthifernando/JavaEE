/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.sun.javafx.image.impl.ByteIndexed;
import dto.TestDTO;
import dto.cartDTO;
import dto.cutomerDTO;
import dto.itemsDTO;
import entities.Cart;
import entities.Customer;
import entities.Item;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.CartFacade;
import model.CustomerFacade;
import model.ItemFacade;

/**
 * REST Web Service
 *
 * @author vimukthifernando
 */
@Path("ws")
public class GenericResource {

    public EntityManager em;
    @Context
    private UriInfo context;

    @EJB
    private CustomerFacade customerFacade;
    private Customer c = new Customer();

    @EJB
    private ItemFacade itemFacade;
    @EJB
    private CartFacade cartFacade;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer login(@QueryParam("username") String username, @QueryParam("password") String password) {

        //Customer c = customerFacade.authenticate(c1.getUsername(), c1.getPassword());
        System.out.println(username + password);
        Customer cust = customerFacade.authenticate(username, password);

        if (cust != null) {

            return cust;

        }
        return cust;

    }

    @GET
    @Path("/addItem")
    @Produces(MediaType.APPLICATION_JSON)
    public String addToCart(@QueryParam("itemname") String itemname, @QueryParam("price") String price, @QueryParam("userid") String userid) {

        //-----
        Cart cr = new Cart();
        Customer c = new Customer();
        //getting the customer instant from the database 
        c.userid = Integer.parseInt(userid);

        cr.setCustomeruserid(c);
        cr.setItemname(itemname);
        cr.setItemprice(Double.parseDouble(price));
        cartFacade.create(cr);
        return userid;
    }

    @PUT
    @Path("/createCustomer")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createCustomer(cutomerDTO newCustomer) {

        Customer c = new Customer();
        c.setUsername(newCustomer.getUsername());
        c.setPassword(newCustomer.getPassword());
        c.setDob(newCustomer.getDob());
        c.setFname(newCustomer.getFname());
        c.setLname(newCustomer.getLname());

        System.out.print(c);

        customerFacade.create(c);

        return "Customer created";

    }

    @POST
    @Path("/addToCart")
    public String addItem(@QueryParam("itemname") String itemname, @QueryParam("userid") String userid, @QueryParam("itemprice") String price) {
        Cart cr = new Cart();
        Customer c = new Customer();
        //getting the customer instant from the database 
        c.userid = Integer.parseInt(userid);

        cr.customeruserid = c;
        cr.itemname = itemname;
        cr.itemprice = Double.parseDouble(price);

        this.cartFacade.create(cr);
        return "Item added";
    }

    /**
     * Retrieves representation of an instance of ws.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public TestDTO getJson() {
        //TODO return proper representation object
        TestDTO test = new TestDTO();
        test.setId("23");
        test.setName("Vimukthi");
        return test;
    }

    //display items 
    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItemsAvalable() {
//        itemController ic = new itemController();
        return itemFacade.findAll();

    }

    @GET
    @Path("/cart")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cart> getItemsCart(@QueryParam("userid") String userid) {
//        itemController ic = new itemController();
        List<Cart> cartlist = this.cartFacade.displayCart1(Integer.parseInt(userid));

        return cartlist;
    }

    @GET
    @Path("/getCustomerInfo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomerInfo(@QueryParam("userid") String userId) {
        Customer c1 = new Customer();

        c1.userid = Integer.parseInt(userId);
        try {
            List<Customer> custInfo = (List<Customer>) em.createNamedQuery("Customer.displayUserInfo")
                    .setParameter("userid", userId).getResultList();

            return custInfo;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String putJson(TestDTO content) {
        return "putJson() received: " + content.getId() + " and " + content.getName();
    }
}
