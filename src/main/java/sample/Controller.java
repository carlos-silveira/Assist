package sample;

import Logica.Compilador;
import Logica.Interprete;
import javafx.application.Application;
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

import static Constantes.TiposTokens.PATTERN;
import static Constantes.TiposTokens.arregloToken;
public class Controller extends Application {
    private Stage stage;
   // @FXML TextArea txtCodigo;
    @FXML TextArea txtConsola,txtResultado;
    @FXML TabPane Tabs;
    @FXML StackPane panelCodigo;
    private CodeArea codeArea;

    @Override
        public void start(Stage stage) {
        this.stage=stage;
    }

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
    public void evtClose(){
        System.exit(0);
    }
    public void evtOpen(){
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
    private static String[] expresiones={

            "^translate\\s'.*';$", //.-hacer traduccion a ingles
            "^(run)\\s.*;",// abrir aplicaciones
            "^(convert\\s)[0-1]{8};$",//.-Convertir de binario a decimal
            "^alarm\\s([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9];$", //.-hacer alarma
            "^[0-9]+([+-/%]|\\*)[0-9]+$",//.-resolver matemáticas
            "^(open\\s)[a-zA-Z]:\\\\[\\\\\\S|*\\S]?.*;$",//.-Abrir carpeta o archivo
            "^email\\s(.+)@(.+);$",//.-enviar correo a usuario
            "^(map\\s)([-+]?\\d{1,2}[.]\\d+),\\s*([-+]?\\d{1,3}[.]\\d+);$",//.-Abrir mapa de coordenadas
            "^(call\\s)(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4};$",//.- LLamar por Skype



    };
    public void compilar (){
        String textoEditor=codeArea.getText();
        String[] arreglo=textoEditor.split("\\n");
        int cont=0;
        arregloToken.clear();
        for (int x=0;x<arreglo.length;x++){
            boolean res=false;
            for (int y = 0; y<expresiones.length && !res; y++){
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
            for (String anArreglo : arreglo) {
                //si es una operación
                if (anArreglo.contains("+") || anArreglo.contains("-") || anArreglo.contains("*") || anArreglo.contains("/") || anArreglo.contains("%")) {
                    Interprete in = new Interprete(anArreglo, txtConsola);
                    double res = in.expresion();
                    txtResultado.setText(txtResultado.getText() + "\nResultado: " + res);
                }
                else {
                    //interprete de variables
                    Compilador compilarCodigo = new Compilador(anArreglo);
                    String textoImprimible = compilarCodigo.compilar();



                        txtResultado.setText(txtResultado.getText() + "\n" + textoImprimible );

                }


            }//llave for

            Tabs.getSelectionModel().select(1);
            long tiempoFinal=System.nanoTime();
            txtResultado.setText(txtResultado.getText()+"\nCompilado en "+(tiempoFinal-tiempoInicial)+" nanosegundos");
        }
    }
    private boolean verificar(String r, String ex){
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
    public void saveAs(){
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