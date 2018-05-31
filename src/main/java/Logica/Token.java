package Logica;

public class Token {
    private String tipo;
    private String valor;
    private String nombre;

    public Token(String tipo, String valor, String nombre) {
        this.tipo = tipo;
        this.valor = valor;
        this.nombre=nombre;
    }
    public Token (String tipo,String valor){
        this.tipo=tipo;
        this.valor=valor;
    }
    public String toString(){
        return "Token("+this.tipo+","+this.valor+")";
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
