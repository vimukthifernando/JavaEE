/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entities.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.spi.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

/**
 *
 * @author vimukthifernando
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "Shopping4-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    
    
    
    public Customer authenticate(String login_id, String pwd) {
        try{
            Customer user = (Customer) em.createNamedQuery("Customer.login")
            .setParameter("username", login_id) 
            .setParameter("password", pwd)
            .getResultList().get(0);
        System.out.println("user id is :"+user.userid);
        return user;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
       
    
        
    
    }
    public List<Customer> displayCustInfo() {
       
     FacesContext context = FacesContext.getCurrentInstance();
       int userId = (int)context.getExternalContext().getSessionMap().get("userid");
       
      System.out.println("its user:dis"+userId);
     
    Customer c = new Customer();
        
        c.userid = userId;
       try {
          List <Customer> custInfo = (List<Customer>) em.createNamedQuery("Customer.displayUserInfo")
                    .setParameter("userid",userId).getResultList();

            
            return custInfo;
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
           return null;
       }


  }
    public List<Customer> displayCustInfo(String userId) {
       
     
       
      
     
    Customer c = new Customer();
        
        c.userid = Integer.parseInt(userId);
       try {
          List <Customer> custInfo = (List<Customer>) em.createNamedQuery("Customer.displayUserInfo")
                    .setParameter("userid",userId).getResultList();

            
            return custInfo;
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
           return null;
       }


  }

    
}
