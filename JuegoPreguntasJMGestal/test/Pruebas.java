
import static juegopreguntasjmgestal.PreguntasMetodos.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Pruebas {
    
    private final static String SOLUCIONES[] = new String [3];
    
    @BeforeClass
    public static void crearSoluciones() {
        introducirRespuestas(SOLUCIONES);
    }
    
    @Test (timeout=1000)
    public void probarRespuestas(){
        String respuesta1 = "a";
        String respuesta2 = "f";
        String respuesta3 = "c";
        boolean solucion;
        
        solucion = comprobarRespuestas(respuesta1, SOLUCIONES[0]);
        Assert.assertTrue(solucion);
        
        solucion = comprobarRespuestas(respuesta2, SOLUCIONES[1]);
        Assert.assertFalse(solucion);
        
        solucion = comprobarRespuestas(respuesta3, SOLUCIONES[2]);
        Assert.assertFalse(solucion);
    }
}
