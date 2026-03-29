/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author ognje
 */
public class Session {
    public static int IDKor = -1;
    public static String username = null;
    public static String role = null;
    
    public static boolean isLoggedIn() {
        return IDKor != -1;
    }
    
    public static void logout() {
        IDKor = -1;
        username = null;
        role = null;
    }
}
