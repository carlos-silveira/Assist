package sample;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class Splash extends Preloader {
    @FXML
    private javafx.scene.control.Label lblProgreso;
    @FXML
    private ProgressBar barraProgreso;
    private Stage escenario;
    private Scene escena;
    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        super.handleStateChangeNotification(info);
        StateChangeNotification.Type tipo=info.getType();
        switch (tipo){
            case BEFORE_START:
                escenario.hide();
                break;
        }
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if(info instanceof  ProgressNotification){
        lblProgreso.setText(((ProgressNotification)info).getProgress()+"%");
        barraProgreso.setProgress( ((ProgressNotification) info).getProgress()/100 );
    }}

    @Override
    public void init() throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Parent root=null;
                try {
                    Parent root2= FXMLLoader.load(getClass().getResource("Splash.fxml"));
                    escena=new Scene(root2,600,400);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.escenario=primaryStage;
        this.escenario.initStyle(StageStyle.UNDECORATED);
        this.escenario.setScene(escena);
        this.escenario.show();
        lblProgreso=(javafx.scene.control.Label)escena.lookup("#lblProgreso");
        barraProgreso=(ProgressBar) escena.lookup("#barraProgreso");

    }
}
