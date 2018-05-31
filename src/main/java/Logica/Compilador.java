package Logica;


import static Constantes.TiposTokens.*;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.IOException;

public class Compilador {

    private String texto;
    private static String textoImprimible;



    public Compilador (String c){
        this.texto=c;
    }
    public String compilar() {
        String var;
        boolean resultado;

        if (this.texto.contains("translate")) {
            var = texto.replaceAll("translate", "").trim();
            var = var.replace(';', ' ').trim();
            var = var.replace('\'', ' ').trim();
            Translate translate = TranslateOptions.getDefaultInstance().getService();
            Translation translation =
                    translate.translate(
                            var,
                            TranslateOption.sourceLanguage("en"),
                            TranslateOption.targetLanguage("es"));
            textoImprimible = translation.getTranslatedText();

        } else if (this.texto.contains("convert")) {
            var = texto.replaceAll("convert", "").trim();
            var = var.replace(';', ' ').trim();
            textoImprimible = binaryToDecimal(Integer.parseInt(var)) + "";
        } else if (this.texto.contains("run")) {
            var = texto.replaceAll("run", "").trim();
            var = var.replace(';', ' ').trim();
            try {

                Runtime runTime = Runtime.getRuntime();
                Process process = runTime.exec(var);



                textoImprimible="Se abrió correctamente "+var;
            } catch (IOException e) {
                e.printStackTrace();
            }


    }
    else if (this.texto.contains("email")){
        var = texto.replaceAll("email","").trim();
        var = var.replace(';',' ').trim();
        textoImprimible=var;
    }
    else if (this.texto.contains("map")){
        var = texto.replaceAll("map","").trim();
        var = var.replace(';',' ').trim();
        textoImprimible=var;
    }
    else if (this.texto.contains("open")){
            var = texto.replaceAll("open", "").trim();
            var = var.replace(';', ' ').trim();
            try {

                Runtime runTime = Runtime.getRuntime();
                Process process = runTime.exec("explorer "+var);



                textoImprimible="Se abrió correctamente "+var;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    else if (this.texto.contains("alarm")){
        var = texto.replaceAll("alarm","").trim();
        var = var.replace(';',' ').trim();
        textoImprimible="Alarma asignada a: "+var;
    }
    return textoImprimible;
    }

    private static int binaryToDecimal(int number) {
        int decimal = 0;
        int binary = number;
        int power = 0;
        while (binary != 0) {
            int lastDigit = binary % 10;
            decimal = (int) (decimal + lastDigit * Math.pow(2, power));
            power++;
            binary = binary / 10;
        }
        return decimal;
    }

}
