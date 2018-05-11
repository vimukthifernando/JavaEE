/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import model.CustomerFacade;
import java.util.*;
import entities.*;
import javax.annotation.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author vimukthifernando
 */
@Named(value = "customerController")
@SessionScoped
@ManagedBean
public class customerController implements Serializable {

    @EJB
    private CustomerFacade customerFacade;
    private Customer c = new Customer();

    private String username;

    private String password;

    private int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer getC() {
        return c;
    }

    public void setC(Customer c) {
        this.c = c;
    }

    public CustomerFacade getCustomerFacade() {
        return customerFacade;
    }

    public void setCustomerFacade(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    public customerController() {

    }

    public List<Customer> findAll() {
        return this.customerFacade.findAll();
    }

    public String add() {

        this.customerFacade.create(this.c);
        this.c = new Customer();
        return "index";
    }

    public String Login() {      
        Customer cust = this.customerFacade.authenticate(username, password);

        if (cust != null) {
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("userid", cust.userid);
            
            return "customerPage";

        }
        return "errorPage";
    }
    public List<Customer> displayCustomerInfo() {
      
        return this.customerFacade.displayCustInfo();
    }
    public String updateCustomer(Customer c){
        this.c=c;
        return "editCustomerPage";
    }
    
    public String updateCustomer(){
        this.customerFacade.edit(this.c);
        this.c=new Customer();
        return "UpdateCustomerPage";
    }
    
    public String updateCustomer1(Customer c){
        this.customerFacade.edit(c);
        this.c=new Customer();
        return "UpdateCustomerPage";
    }

}
