/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.NotesDBException;
import dataaccess.UserDB;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;
import servlets.ResetPasswordServet;
import sun.util.logging.PlatformLogger;

/**
 *
 * @author awarsyle
 */
public class AccountService {

    public User login(String username, String password, String path) {
        try {
            UserDB userDB = new UserDB();
            User user = userDB.getUser(username);

            if (user.getPassword().equals(password)) {
                // successful login
                Logger.getLogger(AccountService.class.getName())
                        .log(Level.INFO, "User {0} logged in.", user.getUsername());
                
                return user;
            }
        } catch (NotesDBException e) {

        }

        return null;
    }
    public String resetPassword(String url)
    {
        String uuid = UUID.randomUUID().toString();
        String link = url + "&uuid=" + uuid;
        return link;
    }
    public boolean changePassword(String uuid, String password) {
        UserService us = new UserService();
        UserDB udb = new UserDB();
        try {
            User user = udb.getByUUID(uuid);
            if(user == null)
            {
               return false; 
            }
            else
            {   
            user.setPassword(password);
            user.setResetPasswordUUID(null);
            
            udb.update(user);
            return true;
            }
        } catch (NotesDBException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
