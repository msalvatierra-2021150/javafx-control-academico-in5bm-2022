package org.in5bm.michaelsalvatierra.bicksongarcia.models;

/**
 *
 * @author W10
 * @date Jul 19, 2022
 * @time 9:40:54 AM
 */
public class Usuario {
    private static String user;
    private static String password;
    private static String tipo;

    public Usuario(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public Usuario() {
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" + "user=" + user + ", password=" + password + '}';
    }
    
    
}
