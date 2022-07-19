package org.in5bm.michaelsalvatierra.bicksongarcia.reports;

/**
 * @date Jun 24, 2022
 * @time 3:36:37 PM
 * @author Michael Steven Salvatierra Ramirez
 * Carne: 2021150
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.in5bm.michaelsalvatierra.bicksongarcia.db.Conexion;
public class GenerarReporte {
    private static GenerarReporte instance;
    private JasperViewer jasperViewer;
    private final String PAQUETE_IMAGES = "org/in5bm/michaelsalvatierra/bicksongarcia/resources/images/";
    
    private GenerarReporte() {
        Locale locale = new Locale("es","GT");
        Locale.setDefault(locale);
    }
    
    public static GenerarReporte getInstance(){
        if(instance == null ){
            instance = new GenerarReporte();
        }
        return instance;
    }
    
    public void mostrarReporte(String nombreReporte, Map <String, Object> parametros, String titulo){
        parametros.put("IMAGE_LOGO",PAQUETE_IMAGES+"LOGO_INVERTIDO.png");
        parametros.put("IMAGE_FOOTER",PAQUETE_IMAGES+"logo-colegio.png");
        try{
            URL urlFile = new URL(getClass().getResource(nombreReporte).toString());
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(urlFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, Conexion.getInstance().getConnection());
            jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setTitle(titulo);
            jasperViewer.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
