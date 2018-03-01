package sample;

import Logica.Compilador;
import Logica.Interprete;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Constantes.TiposTokens.*;
public class Controller extends Application {
    private Stage stage;
   // @FXML TextArea txtCodigo;
    @FXML TextArea txtConsola,txtResultado;
    @FXML TabPane Tabs;
    @FXML StackPane panelCodigo;
    CodeArea codeArea;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
    }
    @FXML
    public void initialize(){
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    if(!codeArea.getText().equals("")) {
                        codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
                    }
                    });
       // codeArea.replaceText(0, 0, sampleCode);
        codeArea.getStyleClass().add("fondoEditor");
        //codeArea.setId("codeArea");
        panelCodigo.getChildren().add(new StackPane(new VirtualizedScrollPane<>(codeArea)));

    }
    public void evtClose(ActionEvent evt){
        System.exit(0);
    }
    public void evtOpen(ActionEvent evt){
        FileChooser oFile = new FileChooser();
        oFile.setTitle("Open File");
        FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("ELB Files(.elb)","*.elb");
        oFile.getExtensionFilters().add(filter);
        File file=oFile.showOpenDialog(stage);
        if(file!=null){
            //leer archivo
            try {
                BufferedReader leer=new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()),"UTF-8"));
                String linea;
                while((linea=leer.readLine())!=null){
                    codeArea.replaceText(codeArea.getText()+linea+"\n");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");


            alert.setContentText("Archivo no valido");
            alert.show();
        }
    }
    static String[] expresiones={

            "^translate\\s'.*';$", //2.-hacer traduccion a ingles
            "^alarm\\s\\(([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]\\);$", //3.-hacer alarma
            //"^(GO TO https?://|GO TO https?://www.)[-a-zA-Z0-9]+[.](com)$", //5.-ir a pagina
            "^[0-9]+([+-/%]|\\*)[0-9]+$",//6.-resolver matematicas
            "^email\\s(.+)@(.+);$",//7.-enviar correo a usuario
            "^(map\\s)([-+]?\\d{1,2}[.]\\d+),\\s*([-+]?\\d{1,3}[.]\\d+);$",//8.-Abrir mapa de coordenada
            "^(convert\\s)[0-1]{8};$",//9.-Convertir de binario a decimal
            "^(open\\s)[a-zA-Z]:\\\\[\\\\\\S|*\\S]?.*;$",//10.-Abrir carpeta o archivo
            "^(buy\\s) (\\d){4}\\s(\\d){4}\\s(\\d){4}\\s(\\d){4};$",//11.- Comprar con una tarjeta de credito
            "^(call\\s)(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4};$",//12.- LLamar por Skype
            "^show\\(\\'?[a-zA-Z0-9\\s]*\\'?\\);$",
            "^(int)\\s+[-\\d\\w]+\\s*;$",
            "^(int)\\s+[-\\d\\w]+\\s*[=]\\s*[0-9]{1,9}[0-7]?\\s*;$",
            "^(string)\\s+[-\\d\\w]+\\s*;$",
            "^(string)\\s+[-\\d\\w]+\\s*[=]\\s*(\")[-\\w\\d]+(\");$",
            "^(char)\\s+[-\\d\\w]+\\s*;$",
            "^(char)\\s+[-\\d\\w]+\\s*[=]\\s*['][\\w][']\\s*;$",
            "^(boolean)\\s+[-\\d\\w]+\\s*;$",
            "^(boolean)\\s+[-\\d\\w]+\\s*[=]\\s*(true|false)\\s*;$",
            "^(double)\\s+[-\\d\\w]+\\s*;$",
            "^(double)\\s+[-\\d\\w]+\\s*[=]\\s*((.[0-9])|([0-9]+)|([0-9]+[.][0-9]{0,2}))\\s*;$",

    };
    public void compilar (){
        String textoEditor=codeArea.getText();
        String[] arreglo=textoEditor.split("\\n");
        int cont=0;
        arregloToken.clear();
        for (int x=0;x<arreglo.length;x++){
            boolean res=false;
            for (int y=0;y<expresiones.length && res==false;y++){
                res=verificar(arreglo[x],expresiones[y]);
            }
            if (!res){
                txtConsola.setText(txtConsola.getText()+"\n Error de sintaxis en la linea: "+x);
               // Error.tokenMalo();
                cont++;
            }


        }
        if(cont==0){
            long tiempoInicial=System.nanoTime();
            for(int x=0;x<arreglo.length;x++){
                //si es una operacion
                if(arreglo[x].contains("+")|| arreglo[x].contains("-") || arreglo[x].contains("*") || arreglo[x].contains("/") || arreglo[x].contains("%")){
                    Interprete in=new Interprete(arreglo[x], txtConsola);
                    double res=in.expresion();
                    txtResultado.setText(txtResultado.getText()+"\nResultado: "+res);
                }
                else{
                    //interprete de variables
                    Compilador compilarCodigo= new Compilador(arreglo[x]);
                    boolean textoImprimible=compilarCodigo.compilar();
                  if(!Compilador.estaImpreso()){
                      txtResultado.setText(txtResultado.getText()+"\n"+textoImprimible);
                  }
                  else{
                      txtResultado.setText(txtResultado.getText()+"\n"+textoImprimible +" imprimir: "+Compilador.obtenerTextoImprimible());
                  }
                }


            }//llave for
            Tabs.getSelectionModel().select(1);
            long tiempoFinal=System.nanoTime();
            txtResultado.setText(txtResultado.getText()+"\nCompilado en "+(tiempoFinal-tiempoInicial)+" nanosegundos");
        }//llave if
        //new Alert(Alert.AlertType.INFORMATION,res+"").show();
    }
    public boolean verificar(String r, String ex){
        Pattern pa=Pattern.compile(ex);
        Matcher ma=pa.matcher(r);
        return  ma.matches();
    }
    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
    public void saveAs(ActionEvent e){
        FileChooser g=new FileChooser();

       FileChooser.ExtensionFilter ex= new FileChooser.ExtensionFilter("ELB files(.elb)","*.elb");
        g.getExtensionFilters().add(ex);
        File archivo=g.showSaveDialog(stage);
        if(archivo!=null){
            try{
                FileWriter write=new FileWriter(archivo);
                write.write(codeArea.getText());
                write.close();
            }catch(Exception error){
                System.out.println(error.getMessage());}}}}