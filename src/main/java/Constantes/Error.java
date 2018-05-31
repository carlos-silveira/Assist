package Constantes;

public class Error {
    public static void caracterInvalido(){
        throw new ArithmeticException("El caracter no se reconoce");
    }
    public static void tokenMalo(){
        throw new ArithmeticException("No se pudo compilar laa expresión");
    }
}
