package org.in5bm.michaelsalvatierra.bicksongarcia.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
/**
 *
 * @date May 10, 2022
 * @time 3:36:37 PM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */

public class Conexion {
    private static Connection connection;
    private final String USER = "kinal";
    private final String PASSWORD = "admin";
    private final String IP_SERVER = "127.0.0.1";
    private final String PORT = "3306";
    private final String DB = "db_control_academico_in5bm";
    
    private final String URL = "jdbc:mysql://"+IP_SERVER+":"+PORT+"/"+DB+"?serverTimezone=UTC"; 
    private String driver = "com.mysql.cj.jdbc.Driver";
    
    private static Conexion instancia;
    private Conexion() {
        try{
           Class.forName(driver);
           connection = DriverManager.getConnection(URL, USER, PASSWORD);
           
           if(connection != null){
               System.out.println("Conexion realizada con exito");
               DatabaseMetaData dba = connection.getMetaData();
               System.out.println("\nConnected to: "+dba.getURL());
               System.out.println("Driver: "+dba.getDriverName());
               System.out.println("Version: "+dba.getDriverVersion()+"\n");
           }
        }catch(ClassNotFoundException e){
            System.err.println("No se encuentra ninguna definicion parala clase");
            e.printStackTrace();
            /*
        } catch (InstantionException e) {
            System.err.println("No se puede crear una instancia del objeto");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("No se tiene los permisos para acceder al paquete");
            e.printStackTrace();
            */
        } catch(CommunicationsException e){
            System.err.println("\nNo se puede establecer comunicacioncon MySQL");
            System.err.println("Verifique si el servicio MySQL este levantado "+"\n verifique la IP_SERVER y el puerto este correcto");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error SQL");
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("ErrorCode: "+e.getErrorCode());
            System.out.println("Message: e.get: "+e.getMessage());
            System.out.println("\n");
        } catch (Exception e) {
            System.err.println("Ocurrio un error en la conexion " + e);
            System.err.println("Track del error ");
            e.printStackTrace();
        }
    }
    
    public static Conexion getInstance(){
        if(instancia == null){
            instancia = new Conexion();
        }
        System.out.println("Conexion con SIngleton realizada con exito");
        return instancia ;
    }

    public static Connection getConnection() {
        return connection;
    }
    
    
}
