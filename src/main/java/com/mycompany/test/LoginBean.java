/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mycompany.test;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * Name:            LoginBean
 * Aufgabe:         Repräsentierung Backend für Login
 * Version:         1.0
 * Letzte Änderung: 01.05.2022
 * Realisierung     Sascha Nickel
 */

@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable{
    private boolean loginOk;
    private boolean isAdmin;
    private String pwd;
    private String loginName;
    private FacesContext context;
    private boolean ok;
    private static int id=0;
    private List<User> userList;
    private HttpSession session;
    
    
    
    @Inject
    private PasswordHashConverter phc;
    @Inject
    private DataBean dBean;
    
    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        
        session = (HttpSession) context.getExternalContext().getSession(false);
        userList = dBean.getUserObjectList();
    }
    
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    /**
     * Prüft die Anmeldedaten und loggt User ggf. ein
     *
     * 
     */
    public String login(){

        FacesMessage fm = null;
        context = FacesContext.getCurrentInstance();
        ok=false;
        String nextPage = "index";
        for (User u : userList) {
            if (u.getLoginName().equals(loginName) && u.getPassword().equals(pwd)) {
                ok = true;
                isAdmin = u.isAdmin();
                break;
            }
        }

            if (ok){
                this.loginOk = true;
                fm = new FacesMessage("Ok.");
                getSession().setAttribute("loginName", loginName);
                context.addMessage("loginForm:username", fm);
                context.addMessage("loginForm:password", fm);
            } else {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Benutzername oder Passwort falsch","detail");
                context.addMessage("loginForm:username2", fm);
                nextPage = "login";
            }
            return nextPage;
            
        }
 
    
     /**
     * Get the value of isAdmin
     *
     * @return the value of isAdmin
     */
    public boolean isIsAdmin() {
        return isAdmin;
    }
    /**
     * Set the value of isAdmin
     *
     * @param isAdmin new value of admin
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
        
    
    /**
     * Logt User aus
     */
     public void logout() {
        context = FacesContext.getCurrentInstance();

        context.getExternalContext().invalidateSession();
        context.getExternalContext().getSession(true);
        
        //https://www.java-forum.org/thema/aktuelle-seite-neu-laden.131093/
        //return seite würde es hier auch tun!
        try {
            context.getExternalContext().redirect("");
        } catch (IOException ex) {
            //LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the value of loginOk
     *
     * @return the value of loginOk
     */
    public boolean isLoginOk() {
        return loginOk;
    }

    /**
     * Set the value of loginOk
     *
     * @param loginOk new value of loginOk
     */
    public void setLoginOk(boolean loginOk) {
        this.loginOk = loginOk;
    }


    /**
     * Get the value of lgn
     *
     * @return the value of lgn
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Set the value of lgn
     *
     * @param loginName new value of lgn
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public static int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public static void setId(int id) {
        LoginBean.id = id;
    }

        /**
     * Get the value of ok
     *
     * @return the value of ok
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * Set the value of ok
     *
     * @param ok new value of ok
     */
    public void setOk(boolean ok) {
        this.ok = ok;
    }


    /**
     * Get the value of pwd
     *
     * @return the value of pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Set the value of pwd
     *
     * @param pwd new value of pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }



    /**
     * @return the session
     */
    public HttpSession getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(HttpSession session) {
        this.session = session;
    }
    
    /**
     * Der Listener wird erst aufgerufen, wenn die Eingabe nicht leer ist und
     * die Validierung erfolgreich war!
     * WIRD NICHT BENÖTIGT - LÖSCHEN
     *
     * @param ev
     
    public void inputAjaxListener(AjaxBehaviorEvent ev) {
        FacesMessage fm;
        context = FacesContext.getCurrentInstance();
        //String s = phc.getPwdHash(pwd);
        //s = s.substring(0,15)+"...";
        if (!context.isValidationFailed()) {
            //fm = new FacesMessage("Passwort: " + s);
            fm = new FacesMessage("Passwort: ");
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
            fm.setDetail(": Passwort(Länge ok).");
            context.addMessage(null, fm);
        }
    }
    * */
}
