package Logica;

import javafx.scene.control.TextArea;
import static Constantes.TiposTokens.*;

public class Interprete {
    private  String texto;//el texto es la expresión ejemplo: 2+2
    private  int pos;//guarda la posición del token
    private Token token_actual;
    private char c_actual;
    private TextArea console;
    public Interprete(String texto, TextArea console){
        this.texto=texto;
        this.pos=0;
        this.token_actual=null;
        //obtenemos el carácter actual
        this.c_actual=this.texto.charAt(this.pos);
        this.console=console;
    }
    private void avanzar(){
         /*Verificamos que la posición no pase del fin
    *de la expresión por lo tanto es el fin*/
         pos+=1;
        if(this.pos>this.texto.length()-1){
          this.c_actual='#';
        }else{
            this.c_actual=texto.charAt(pos);
        }
    }
    private void ignoraBlancos(){
        while(this.c_actual!='#' && Character.isSpaceChar(this.c_actual)){
            avanzar();
        }
    }
 private String getInteger(){
        StringBuilder r= new StringBuilder();
        while(this.c_actual!='#' && Character.isDigit(this.c_actual)){
            r.append(this.c_actual);
            avanzar();
        }
        return r.toString();
 }
    private void error(){
// Error.tokenMalo();
        console.setText(console.getText()+"\n Error de sintaxis: caracter no valido");
    }

    private Token getSiguiente(){
    /*Scanner Tokenizer este método se encarga de
    *convertir la expresion en partes de tokens*/

    while(this.c_actual!='#'){
        if(Character.isSpaceChar(c_actual)){
            ignoraBlancos();
            continue;
        }
        //Si el caracter que tenemos es un digito
        //lo guardamos en la categoria de Integers
        if (Character.isDigit(c_actual)){
            return new Token(INTEGER,getInteger()+"");
        }
        //para cuando sea un más
        if (c_actual=='+'){
           avanzar();
            return new Token(PLUS,c_actual+"");
        }
        //cuando sea menos
        if (c_actual=='-'){
            avanzar();
            return new Token(MINUS,c_actual+"");
        }
        if(c_actual=='*'){
            avanzar();
            return new Token(TIMES,c_actual+"");
        }
        if(c_actual=='/'){
            avanzar();
            return new Token(DIV,c_actual+"");
        }
        if(c_actual=='%'){
            avanzar();
            return new Token(MOD,c_actual+"");
        }
        this.error();
    }

        return null;
    }
    private void  comer(String tipo){
        //cambia entre token
        if (this.token_actual.getTipo().equals(tipo)){
        this.token_actual=this.getSiguiente();
        }else{
            this.error();
        }
    }
    //Compila la expresión
    public double expresion(){
        //Nuestra expresion es integer plus integer
        this.token_actual=this.getSiguiente();

        Token izquierdo=this.token_actual;
        this.comer(INTEGER);

        Token op=this.token_actual;
        switch (op.getTipo()) {
            case "PLUS":
                this.comer(PLUS);
                break;
            case "MINUS":
                this.comer(MINUS);
                break;
            case "TIMES":
                this.comer(TIMES);
                break;
            case "DIV":
                this.comer(DIV);
                break;
            case "MOD":
                this.comer(MOD);
                break;
        }

        Token derecho=this.token_actual;
        this.comer(INTEGER);
        //Ya hemos generado la expresión solo falta hacer la operación
        double res=0;
        switch (op.getTipo()) {
            case "PLUS":
                assert izquierdo != null;
                res = Double.parseDouble(izquierdo.getValor()) + Double.parseDouble((derecho.getValor()));
                break;
            case "MINUS":
                assert izquierdo != null;
                res = Double.parseDouble(izquierdo.getValor()) - Double.parseDouble((derecho.getValor()));
                break;
            case "TIMES":
                assert izquierdo != null;
                res = Double.parseDouble(izquierdo.getValor()) * Double.parseDouble((derecho.getValor()));
                break;
            case "DIV":
                assert izquierdo != null;
                res = Double.parseDouble(izquierdo.getValor()) / Double.parseDouble((derecho.getValor()));
                break;
            case "MOD":
                assert izquierdo != null;
                res = Double.parseDouble(izquierdo.getValor()) % Double.parseDouble((derecho.getValor()));
                break;
        }

        return res;
    }
}
