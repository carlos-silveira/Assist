package sample;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
public static int duracion=10000;
public static int cuanto=1;

    @Override
    public void init() throws Exception {

        for(int i=0;i<duracion;i++){
            double progreso=(100*i)/duracion;
            LauncherImpl.notifyPreloader(this,new Splash.ProgressNotification(progreso));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Assist");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {

        LauncherImpl.launchApplication(Main.class,Splash.class,args);
        //launch(args)feo;
    }
}
