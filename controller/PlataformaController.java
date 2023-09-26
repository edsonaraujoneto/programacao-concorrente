package controller;

/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/08/2023
* Ultima alteracao.: 25/09/2023
* Nome.............: Controle da Plataforma
* Funcao...........: Controlar todos os eventos que ocorrem no arquivo FXML.
*************************************************************** */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.TremAzul;
import model.TremVerde;

public class PlataformaController implements Initializable {

  @FXML
  private Slider aceleradorAzul;

  @FXML
  private Slider aceleradorVerde;

  @FXML
  private RadioButton radioBaixoBaixo;

  @FXML
  private RadioButton radioBaixoCima;

  @FXML
  private RadioButton radioCimaBaixo;

  @FXML
  private RadioButton radioCimaCima;

  @FXML
  private ImageView tremAzul;

  @FXML
  private ImageView tremVerde;

  @FXML
  private ImageView tremVerdeLadoOposto;

  @FXML
  private ImageView tremAzulLadoOposto;

  private MediaPlayer mediaPlayer;

  private int entrada = 0; // variavel de controle para som do trem
  
  private TremAzul tremAzulThread;
  
  private TremVerde tremVerdeThread;

  private static final Object lock = new Object();
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Configuracoes basicas dos sliders
    aceleradorAzul.setMin(0);
    aceleradorAzul.setMax(5);
    aceleradorAzul.setValue(0);
    aceleradorAzul.setShowTickMarks(true);
    aceleradorAzul.setMajorTickUnit(1);
    aceleradorAzul.setShowTickLabels(true);
    aceleradorVerde.setMin(0);
    aceleradorVerde.setMax(5);
    aceleradorVerde.setValue(0);
    aceleradorVerde.setShowTickMarks(true);
    aceleradorVerde.setMajorTickUnit(1);
    aceleradorVerde.setShowTickLabels(true);
    
    // carregar arquivo de audio
    String audioFile = getClass().getResource("/music/somDeTrem.mp3").toExternalForm();
    Media media = new Media(audioFile);
    mediaPlayer = new MediaPlayer(media);
    
    // criando as threads e passando como parametro as imagens dos dois lados e os sliders.
    tremAzulThread = new TremAzul(tremAzul,tremAzulLadoOposto, aceleradorAzul, lock );
    tremVerdeThread = new TremVerde(tremVerde,tremVerdeLadoOposto, aceleradorVerde, lock);
    tremAzulThread.start();
    tremVerdeThread.start();

  } // fim da classe initialize

  /*********************************************************************
  * Metodo: ajustarPosicao
  * Funcao: configurar o percurso do trem de acordo com o radioButton selecionado
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void ajustarPosicao() {
    if (radioCimaCima.isSelected()) {
      tremAzulThread.setDirecao("Cima");
      tremVerdeThread.setDirecao("Cima");
    }
    else if(radioCimaBaixo.isSelected()) {
      tremAzulThread.setDirecao("Baixo");
      tremVerdeThread.setDirecao("Cima");
    }
    else if(radioBaixoCima.isSelected()) {
      tremAzulThread.setDirecao("Cima");
      tremVerdeThread.setDirecao("Baixo");
    }
    else if(radioBaixoBaixo.isSelected()) {
      tremAzulThread.setDirecao("Baixo");
      tremVerdeThread.setDirecao("Baixo");
    }
  } // fim ajustarPosicao

  /*********************************************************************
  * Metodos: clicouRadioBaixoBaixo | clicouRadioBaixoCima | clicouRadioCimaBaixo | clicouRadioBaixoBaixo
  * Funcao: Evitar que outros radiosButtons sejam selecionados e configurar as posicoes dos trens
  * Parametros: Evento de clique
  * Retorno: void
  ******************************************************************* */
  @FXML
  public void clicouRadioBaixoBaixo(ActionEvent event) {
    radioCimaBaixo.setSelected(false);
    radioCimaCima.setSelected(false);
    radioBaixoCima.setSelected(false);
    ajustarPosicao();
  } // fim do metodo clicouRadioBaixoBaixo

  @FXML
  public void clicouRadioBaixoCima(ActionEvent event) {
    radioCimaBaixo.setSelected(false);
    radioCimaCima.setSelected(false);
    radioBaixoBaixo.setSelected(false);
    ajustarPosicao();
  } // fim clicouRadioBaixoCima

  @FXML
  public void clicouRadioCimaBaixo(ActionEvent event) {
    radioBaixoBaixo.setSelected(false);
    radioCimaCima.setSelected(false);
    radioBaixoCima.setSelected(false);
    ajustarPosicao();
  } // fim do metodo clicouRadioCimaBaixo

  @FXML
  public void clicouRadioCimaCima(ActionEvent event) {
    radioCimaBaixo.setSelected(false);
    radioBaixoBaixo.setSelected(false);
    radioBaixoCima.setSelected(false);
    ajustarPosicao();
  } // fim do metodo clicouRadioCimaCima

  /*********************************************************************
  * Metodo: clicouReiniciar
  * Funcao: Reiniciar toda a simulacao do inicio, escondendo os trens, interrompendo a thread, zerando o velocimetro (os sliders) e desativando o audio
  * Parametros: Evento de clique
  * Retorno: void
  ******************************************************************* */
  @FXML
  public void clicouReiniciar(ActionEvent event) {
    radioCimaCima.setSelected(false);
    radioCimaBaixo.setSelected(false);
    radioBaixoBaixo.setSelected(false);
    radioBaixoCima.setSelected(false);
    
    if (!tremAzulThread.isAlive()) { 
      tremAzulThread.parar();
      tremAzulThread.interrupt();
      mediaPlayer.stop();
    }
    if (!tremVerdeThread.isAlive()) { 
      tremVerdeThread.parar();
      tremVerdeThread.interrupt();
      mediaPlayer.stop();
    }
  } // Fim do metodo clicouReiniciar

  /*********************************************************************
  * Metodo: clicouMute
  * Funcao: Mutar o audio do trem 
  * Parametros: Evento de clique
  * Retorno: void
  ******************************************************************* */
  @FXML
  public void clicouMute(ActionEvent event) {
    if (entrada % 2 == 0) { // entrada eh a variavel inteira inicializada com 1
      mediaPlayer.stop();
      ++entrada;
    } else if (aceleradorAzul.getValue() != 0 || aceleradorVerde.getValue() != 0) { // aceleradorAzul e aceleradorVerde sao os sliders(velocimetro) do trem
      // condicao para dar possibilidade de tocar o audio novamente apos desativa-lo
      mediaPlayer.play();
      ++entrada;
    }
  } // fim do metodo clicouMute

} // Fim do PlataformaController
