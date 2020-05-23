package juegopreguntasjmgestal;

public class PreguntasMetodos {
    
    // Metodo que genera la pregunta 1
    public static void generarPregunta1(){
        System.out.println("¿Quien descubrio América?");
    }
    
    // Metodo que genera las respuestas de la pregunta 1
    public static void generarRespuestas1(){
        System.out.println("\ta. Cristobal Colón");
        System.out.println("\tb. Napoleón Bonaparte");
        System.out.println("\tc. Nicolás Copernico");
    }
    
    // Metodo que genera la pregunta 2
    public static void generarPregunta2(){
        System.out.println("¿Cuantos años tiene un siglo?");
    }
    
    // Metodo que genera las respuestas de la pregunta 2
    public static void generarRespuestas2(){
        System.out.println("\ta. 10");
        System.out.println("\tb. 100");
        System.out.println("\tc. 1000");
    }
    
    // Metodo que genera la pregunta 3
    public static void generarPregunta3(){
        System.out.println("¿Cuál es la capital de Rusia?");
    }
    
    // Metodo que genera las respuestas de la pregunta 3
    public static void generarRespuestas3(){
        System.out.println("\ta. Moscú");
        System.out.println("\tb. Volgogrado");
        System.out.println("\tc. San Petersburgo");
    }
    
    // Metodo que comprueba si las respuestas son correctas
    public static boolean comprobarRespuestas(String respuesta, String correcta){
        
        boolean solucion = false;
        
        if(respuesta.equals(correcta)){
            solucion = true;
            return solucion;
        } else{
            return solucion;
        }
    }
    
    // Metodo que genera un mensaje, dependiendo si la respuesta es correcta o incorrecta
    public static void generarMensaje(boolean solucion){
        
        if(solucion){
            System.out.println("Correcto!!");
        }else{
            System.out.println("Incorrecto...");
        }
    }
    
    // Metodo que introduce en un array las respuestas correctas para compararlas con las respuestas del usuario
    public static void introducirRespuestas(String soluciones[]){
        soluciones[0] = "a";
        soluciones[1] = "b";
        soluciones[2] = "a";
    }
}
