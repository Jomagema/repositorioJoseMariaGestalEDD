/**
 * Aplicación de un Juego de Preguntas
 * 
 * Jose María Gestal Maya
 */

package juegopreguntasjmgestal;

import java.util.Scanner;
import static juegopreguntasjmgestal.PreguntasMetodos.*;

public class JuegoPreguntasJMGestal {

    public static void main(String[] args) {
        
        //Declaración de variables
        Scanner leer = new Scanner (System.in);
        String respuesta;
        boolean solucion;
        String soluciones[] = new String [3];
        
        // Rellenamos el array de las soluciones
        introducirRespuestas(soluciones);
        
        
        System.out.println("*****Bienvenido al Juego de las Preguntas*****");
        System.out.println("Primera pregunta:");
        
        // Generación del texto de las preguntas y las respuestas
        generarPregunta1();
        generarRespuestas1();
        
        
        System.out.println("Responder con a, b o c;");
//        do{
        respuesta = leer.nextLine();
//        }while(respuesta.equals("a") || !respuesta.equals("b") || !respuesta.equals("c") ||respuesta.isEmpty());

        // Comprobación de las respuestas
        solucion = comprobarRespuestas(respuesta, soluciones[0]);
        // Genera un mensaje de si es correcto o incorrecto
        generarMensaje(solucion);
       
        System.out.println("Segunda pregunta:");
        
        // Generación del texto de las preguntas y las respuestas
        generarPregunta2();
        generarRespuestas2();
        
        
        System.out.println("Responder con a, b o c;");
//        do{
        respuesta = leer.nextLine();
//        }while(!respuesta.equals("a") || !respuesta.equals("b") || !respuesta.equals("c") ||respuesta.isEmpty());
        // Comprobación de las respuestas
        solucion = comprobarRespuestas(respuesta, soluciones[1]);
        // Genera un mensaje de si es correcto o incorrecto
        generarMensaje(solucion);
        
        System.out.println("Tercera pregunta:");
        
        // Generación del texto de las preguntas y las respuestas 
        generarPregunta3();
        generarRespuestas3();
        
        
        System.out.println("Responder con a, b o c;");
//       do{
        respuesta = leer.nextLine();
//        }while(!respuesta.equals("a") || !respuesta.equals("b") || !respuesta.equals("c") || respuesta.isEmpty());
        // Comprobación de las respuestas
        solucion = comprobarRespuestas(respuesta, soluciones[2]);
        // Genera un mensaje de si es correcto o incorrecto
        generarMensaje(solucion);
        
    }
}

