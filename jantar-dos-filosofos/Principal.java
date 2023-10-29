/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/10/2023
* Ultima alteracao.: 28/10/2023
* Nome.............: Principal
* Funcao...........: Executar a animacao
*************************************************************** */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;

public class Principal extends Application {

  private static Scene scene;

  public static void main(String[] args) {
    launch(args);
  } // fim da classe Main

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Jantar da discórdia");// Nome da janela
    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Tela.fxml"));
    scene = new Scene(root);
    
    Image icon = new Image("/images/icon.png");  // Carregando o ícone da janela

    primaryStage.setResizable(false); // evitar Maximizar
    primaryStage.setScene(scene);
    primaryStage.getIcons().add(icon);
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });
    primaryStage.show();
  } // fim do metodo start
}// fim da classe Principal
