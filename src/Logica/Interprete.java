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
        //obtenemos el caracter actual
        this.c_actual=this.texto.charAt(this.pos);
        this.console=console;
    }
    public void avanzar(){
         /*Verificamos que la posición no pase del fin
    *de la expresión por lo tanto es el fin*/
         pos+=1;
        if(this.pos>this.texto.length()-1){
          this.c_actual='#';
        }else{
            this.c_actual=texto.charAt(pos);
        }
    }
    public void ignoraBlancos(){
        while(this.c_actual!='#' && Character.isSpaceChar(this.c_actual)){
            avanzar();
        }
    }
 public String getInteger(){
        String r="";
        while(this.c_actual!='#' && Character.isDigit(this.c_actual)){
            r+=this.c_actual;
            avanzar();
        }
        return  r;
 }
    public void error(int i){
if(i==0){
   // Error.caracterInvalido();
    console.setText(console.getText()+"\n Error desconocido");
}
if(i==1){
   // Error.tokenMalo();
    console.setText(console.getText()+"\n Error de sintaxis: caracter no valido");
}
    }

    public Token getSiguiente(){
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
        this.error(1);
    }

        return null;
    }
    public void  comer(String tipo){
        //cambia entre token
        if (this.token_actual.getTipo().equals(tipo)){
        this.token_actual=this.getSiguiente();
        }else{
            this.error(1);
        }
    }
    //Compila la expresión
    public double expresion(){
        //Nuestra expresion es integer plus integer
        this.token_actual=this.getSiguiente();

        Token izquierdo=this.token_actual;
        this.comer(INTEGER);

        Token op=this.token_actual;
        if(op.getTipo().equals("PLUS")){
            this.comer(PLUS);
        }else if (op.getTipo().equals("MINUS")){
            this.comer(MINUS);
        }else if(op.getTipo().equals("TIMES")){
            this.comer(TIMES);
        }else if(op.getTipo().equals("DIV")){
            this.comer(DIV);
        }else if(op.getTipo().equals("MOD")) {
            this.comer(MOD);
        }

        Token derecho=this.token_actual;
        this.comer(INTEGER);
        //Ya hemos generado la expresión solo falta hacer la operación
        double res=0;
        if(op.getTipo().equals("PLUS")){
            res=Double.parseDouble(izquierdo.getValor())+Double.parseDouble((derecho.getValor()));
        }else if (op.getTipo().equals("MINUS")){
            res=Double.parseDouble(izquierdo.getValor())-Double.parseDouble((derecho.getValor()));
        }else if(op.getTipo().equals("TIMES")){
            res=Double.parseDouble(izquierdo.getValor())*Double.parseDouble((derecho.getValor()));
        }else if(op.getTipo().equals("DIV")){
            res=Double.parseDouble(izquierdo.getValor())/Double.parseDouble((derecho.getValor()));
        }else if(op.getTipo().equals("MOD")){
            res=Double.parseDouble(izquierdo.getValor())%Double.parseDouble((derecho.getValor()));
        }

        return res;
    }
}
