/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import java.util.*;
import javax.ejb.EJB;
import model.CartFacade;
import entities.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.faces.context.FacesContext;
import javax.jms.Connection;
import javax.resource.cci.ResultSet;

/**
 *
 * @author vimukthifernando
 */
@Named(value = "cartController")
@SessionScoped
@ManagedBean
public class cartController implements Serializable {

    @EJB
    private CartFacade cartFacade;

    private Integer cartid;

    private Double itemprice;

    private String itemname;

    private Customer customeruserid;

    public Customer getCustomeruserid() {
        return customeruserid;
    }

    public void setCustomeruserid(Customer customeruserid) {
        this.customeruserid = customeruserid;
    }

    public CartFacade getCartFacade() {
        return cartFacade;
    }

    public void setCartFacade(CartFacade cartFacade) {
        this.cartFacade = cartFacade;
    }

    public Integer getCartid() {
        return cartid;
    }

    public void setCartid(Integer cartid) {
        this.cartid = cartid;
    }

    public Double getItemprice() {
        return itemprice;
    }

    public void setItemprice(Double itemprice) {
        this.itemprice = itemprice;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public cartController() {
    }

    public void addtocart(double price, String itemname) {
        //getting the usewr id of the current user logged in 
        FacesContext context = FacesContext.getCurrentInstance();
        int userId = (int) context.getExternalContext().getSessionMap().get("userid");
        //
        Cart cr = new Cart();
        Customer c = new Customer();
        //getting the customer instant from the database 
        c.userid = userId;

        cr.customeruserid = c;
        cr.itemname = itemname;
        cr.itemprice = price;

        this.cartFacade.create(cr);
    }
    public void addtocart(double price, String itemname,String userId) {
       
        //
        Cart cr = new Cart();
        Customer c = new Customer();
        //getting the customer instant from the database 
        c.userid = Integer.parseInt(userId);

        cr.customeruserid = c;
        cr.itemname = itemname;
        cr.itemprice = price;

        this.cartFacade.create(cr);
    }

    public List<Cart> displaycart() {
//        Cart cart = new Cart();
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.getExternalContext().getSessionMap().put("userid", cart.customeruserid);
//        List<Cart> myList = new ArrayList<Cart>();
//        myList = ;
//        myList.contains(context)
        return this.cartFacade.displayCart1();
    }
    public List<Cart> displaycart(int userid) {

        return this.cartFacade.displayCart1(userid);
    }

    public void dis(Cart id) {

        this.cartFacade.find(id);
    }

    public void removeProductFromCart(Cart c) {
        this.cartFacade.remove(c);
    }
//    
//   
}
