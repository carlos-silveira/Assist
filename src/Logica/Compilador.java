package Logica;

import static Constantes.TiposTokens.*;

public class Compilador {

    private String texto;
    private String[] dividirCodigo;
    private String var,val,op;
    private boolean resultado;
    private static String textoImprimible="";
    private static boolean impreso=false;
    public static boolean estaImpreso(){
        return impreso;
    }
    public static String obtenerTextoImprimible(){
        return textoImprimible;
    }
    public Compilador (String c){
        this.texto=c;
    }
    public boolean compilar() {
    if(this.texto.contains("show")){
        resultado=false;
        impreso=true;
        if(this.texto.contains("\'")){
            int inicio=texto.indexOf("(")+2;
            int fin=texto.indexOf(")")-1;
            textoImprimible=texto.substring(inicio,fin);
            resultado=true;
        }
        else{
            resultado = checarValor();
            if(resultado){
                int inicio=texto.indexOf("(")+1;
                int fin=texto.indexOf(")");
                for (Token arregloTokenfor : arregloToken){
                    if(arregloTokenfor.getNombre().equals(texto.substring(inicio,fin))){
                        textoImprimible=arregloTokenfor.getValor();
                    }
                }
            }
        }
        return resultado;
    }
    else if(this.texto.contains("int") ){
        impreso=false;
        if(this.texto.contains("=")){
            dividirCodigo=this.texto.split("=");
            var= dividirCodigo[0].replaceAll("int","").trim();
            val= dividirCodigo[1].replace(';',' ').trim();
            textoImprimible=val;
            if(checarExistencia(var)){
                arregloToken.add(new Token(INTEGER,var,val));
                resultado=true;
            }
            else{
                resultado=false;
            }
            return resultado;
        }
        else {
            var = texto.replaceAll("int","").trim();
            var = var.replace(';',' ').trim();
            if(checarExistencia(var)){
                arregloToken.add(new Token(INTEGER,var));
                resultado=true;
            }
            else {
                resultado=false;
            }
        }
    }

    else if(this.texto.contains("string") ){
        impreso=false;
        if(this.texto.contains("=")){
            dividirCodigo=this.texto.split("=");
            var= dividirCodigo[0].replaceAll("string","").trim();
            val= dividirCodigo[1].replace(';',' ').trim();
            val= val.replaceAll("[\"]","");
            if(checarExistencia(var)){
                System.out.print("La variable String es:"+var);
                arregloToken.add(new Token(STRING,var,val));
                resultado=true;
            }
            else{
                resultado=false;
            }
            return resultado;
        }
        else {
            var = texto.replaceAll("string","").trim();
            var = var.replace(';',' ').trim();
            if(checarExistencia(var)){
                System.out.print("La variable String es:"+var);
                arregloToken.add(new Token(STRING,var));
                resultado=true;
            }
            else {
                resultado=false;
            }
        }
    }

    else if(this.texto.contains("char") ){
        impreso=false;
        if(this.texto.contains("=")){
            dividirCodigo=this.texto.split("=");
            var= dividirCodigo[0].replaceAll("char","").trim();
            val= dividirCodigo[1].replace(';',' ').trim();
            val= val.replaceAll("[\']","");
            if(checarExistencia(var)){
                System.out.print("El char de la variable es:"+var);
                arregloToken.add(new Token(CHAR,var,val));
                resultado=true;
            }
            else{
                resultado=false;
            }
            return resultado;
        }
        else {
            var = texto.replaceAll("char","").trim();
            var = var.replace(';',' ').trim();
            if(checarExistencia(var)){
                System.out.print("El char de la variable es:"+var);
                arregloToken.add(new Token(CHAR,var));
                resultado=true;
            }
            else {
                resultado=false;
            }
        }
    }

    else if(this.texto.contains("boolean") ){
        impreso=false;
        if(this.texto.contains("=")){
            dividirCodigo=this.texto.split("=");
            var= dividirCodigo[0].replaceAll("boolean","").trim();
            val= dividirCodigo[1].replace(';',' ').trim();
            if(checarExistencia(var)){
                System.out.print("La bandera es:"+var);
                arregloToken.add(new Token(BOOLEAN,var,val));
                resultado=true;
            }
            else{
                resultado=false;
            }
            return resultado;
        }
        else {
            var = texto.replaceAll("boolean","").trim();
            var = var.replace(';',' ').trim();
            if(checarExistencia(var)){
                System.out.print("La bandera es:"+var);
                arregloToken.add(new Token(BOOLEAN,var));
                resultado=true;
            }
            else {
                resultado=false;
            }
        }
    }
    else if(this.texto.contains("double") ){
        impreso=false;
        if(this.texto.contains("=")){
            dividirCodigo=this.texto.split("=");
            var= dividirCodigo[0].replaceAll("double","").trim();
            val= dividirCodigo[1].replace(';',' ').trim();

            if(checarExistencia(var)){
                System.out.print("El double es:"+var);
                arregloToken.add(new Token(DOUBLE,var,val));
                resultado=true;
            }
            else{
                resultado=false;
            }
            return resultado;
        }
        else {
            var = texto.replaceAll("double","").trim();
            var = var.replace(';',' ').trim();
            if(checarExistencia(var)){
                System.out.print("El double es:"+var);
                arregloToken.add(new Token(DOUBLE,var));
                resultado=true;
            }
            else {
                resultado=false;
            }
        }
    }

    else if(this.texto.contains("note") ){
        impreso=false;
        if(this.texto.contains("=")){
            dividirCodigo=this.texto.split("=");
            var= dividirCodigo[0].replaceAll("string","").trim();
            val= dividirCodigo[1].replace(';',' ').trim();
            val= val.replaceAll("[\"]","");
            if(checarExistencia(var)){
                System.out.print("La variable String es:"+var);
                arregloToken.add(new Token(STRING,var,val));
                resultado=true;
            }
            else{
                resultado=false;
            }
            return resultado;
        }
        else {
            var = texto.replaceAll("string","").trim();
            var = var.replace(';',' ').trim();
            if(checarExistencia(var)){
                System.out.print("La variable String es:"+var);
                arregloToken.add(new Token(STRING,var));
                resultado=true;
            }
            else {
                resultado=false;
            }
        }
    }
    else if(this.texto.contains("translate") || this.texto.contains("email")
            || this.texto.contains("map") || this.texto.contains("convert")
            || this.texto.contains("open") || this.texto.contains("buy")
            || this.texto.contains("call")){
        impreso=true;
        resultado=true;
    }else{
         resultado=false;
    }
    return resultado;
    }
    private  boolean checarValor() {
        boolean r = false;
        int inicio = texto.indexOf("(");
        int fin = texto.indexOf(")");
        String textoImprimible = texto.substring(inicio, fin);
        for (Token arregloTokenfor : arregloToken) {
            if (arregloTokenfor.getNombre().equals(textoImprimible)) {
                try {
                    if (!arregloTokenfor.getValor().equals(null)) {
                        r = true;
                    }
                } catch (NullPointerException e) {
                    r = false;
                }
            }
        }
        return r;
    }
    private boolean checarExistencia(String c){
        boolean r=true;
        for(Token arregloTokenfor: arregloToken){
            if(arregloTokenfor.getNombre().equals(c)){
                r=false;
            }
        }
        return r;
    }
}
