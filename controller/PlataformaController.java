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
import javafx.scene.Group;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
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
  private Group grupoMenu;

  @FXML
  private RadioButton radioBaixoCima;

  @FXML
  private RadioButton radioCimaBaixo;

  @FXML
  private RadioButton radioCimaCima;

  @FXML
  private RadioButton radioEstritaAlternancia;

  @FXML
  private RadioButton radioSolucaoPeterson;

  @FXML
  private RadioButton radioVariavelDeTravamento;

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
  
  private ToggleGroup grupoRadiosButtonsDirecao;

  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Configuracoes basicas dos sliders
    getAceleradorAzul().setMin(0);
    getAceleradorAzul().setMax(5);
    getAceleradorAzul().setValue(0);
    getAceleradorAzul().setShowTickMarks(true);
    getAceleradorAzul().setMajorTickUnit(1);
    getAceleradorAzul().setShowTickLabels(true);
    getAceleradorVerde().setMin(0);
    getAceleradorVerde().setMax(5);
    getAceleradorVerde().setValue(0);
    getAceleradorVerde().setShowTickMarks(true);
    getAceleradorVerde().setMajorTickUnit(1);
    getAceleradorVerde().setShowTickLabels(true);
    
    // carregar arquivo de audio
    String audioFile = getClass().getResource("/music/somDeTrem.mp3").toExternalForm();
    Media media = new Media(audioFile);
    mediaPlayer = new MediaPlayer(media);
    
    // criando as threads e passando como parametro as imagens dos dois lados e os sliders.
    tremAzulThread = new TremAzul(this);
    tremVerdeThread = new TremVerde(this);
    
    grupoRadiosButtonsDirecao = new ToggleGroup();
    radioBaixoBaixo.setToggleGroup(getGrupoRadiosButtonsDirecao());
    radioBaixoCima.setToggleGroup(getGrupoRadiosButtonsDirecao());
    radioCimaCima.setToggleGroup(getGrupoRadiosButtonsDirecao());
    radioCimaBaixo.setToggleGroup(getGrupoRadiosButtonsDirecao());
    
    ToggleGroup grupoRadiosButtonsTratamento = new ToggleGroup();
    radioVariavelDeTravamento.setToggleGroup(grupoRadiosButtonsTratamento);
    radioSolucaoPeterson.setToggleGroup(grupoRadiosButtonsTratamento);
    radioEstritaAlternancia.setToggleGroup(grupoRadiosButtonsTratamento);
    
    grupoRadiosButtonsTratamento.selectedToggleProperty().addListener((observable,oldValue,newValue) -> {
    });

    aceleradorAzul.valueProperty().addListener((observable, oldValue, newValue) -> {
      iniciarThreadTremAzul(newValue.doubleValue());
    });
    
    aceleradorVerde.valueProperty().addListener((observable,oldValue,newValue) -> {
      iniciarThreadTremVerde(newValue.doubleValue());
    });

  } // fim da classe initialize
  
  @FXML
  void clicouIniciar(ActionEvent event) {
    ajustarPosicao();
    if (selecionouPosicao() && selecionouTratamentoDeColisao())
      grupoMenu.setVisible(false);
  }
  // iniciar a thread apenas se a velocidade foi alterada.
  public void iniciarThreadTremAzul (double velocidade) {
    
    if (aceleradorAzul.getValue() != 0 ) {
      if (!tremAzulThread.isAlive()) {
        tremAzulThread.start();
      } 
      tremAzulThread.setVelocidadeTrem( 20/velocidade);
      synchronized (tremAzulThread) {
        tremAzulThread.setPausarThread(false);
        tremAzulThread.notify();
      }
    } 
    else if(aceleradorAzul.getValue() == 0 ) {
      synchronized (tremAzulThread) {
        tremAzulThread.setPausarThread(true);
      }
    }
  } // fim iniciarThread
  
    public void iniciarThreadTremVerde (double velocidade) {
    
    if (aceleradorVerde.getValue() != 0 && selecionouPosicao() && selecionouTratamentoDeColisao()) {
      if (!tremVerdeThread.isAlive()) {
        tremVerdeThread.start();
      } 
      tremVerdeThread.setVelocidadeTrem( 20/velocidade);
      synchronized (tremVerdeThread) {
        tremVerdeThread.setPausarThread(false);
        tremVerdeThread.notify();
      }
    } 
    else if(aceleradorVerde.getValue() == 0 && selecionouPosicao() && selecionouTratamentoDeColisao()) {
      synchronized (tremVerdeThread) {
        tremVerdeThread.setPausarThread(true);
      }
    }
  } // fim iniciarThread
  
  
  //Verificar se o usuario selecionou a posicao do trem
  public boolean selecionouPosicao() {
    if (getRadioCimaCima().isSelected() || getRadioBaixoBaixo().isSelected() || getRadioBaixoCima().isSelected() || getRadioCimaBaixo().isSelected()) 
      return true;
    return false;
  }

  /*********************************************************************
  * Metodo: ajustarPosicao
  * Funcao: configurar o percurso do trem de acordo com o radioButton selecionado
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void ajustarPosicao() {
    if (tremAzulThread.isAlive()) {
      tremAzulThread.interrupt();
    }
    if (tremVerdeThread.isAlive()) {
      tremVerdeThread.interrupt();
    }
    if (getRadioCimaCima().isSelected()) {
      tremAzulThread.setDirecao("Cima");
      tremVerdeThread.setDirecao("Cima");
    }
    else if(getRadioCimaBaixo().isSelected()) {
      tremAzulThread.setDirecao("Baixo");
      tremVerdeThread.setDirecao("Cima");
    }
    else if(getRadioBaixoCima().isSelected()) {
      tremAzulThread.setDirecao("Cima");
      tremVerdeThread.setDirecao("Baixo");
    }
    else if(getRadioBaixoBaixo().isSelected()) {
      tremAzulThread.setDirecao("Baixo");
      tremVerdeThread.setDirecao("Baixo");
    }
  } // fim ajustarPosicao

  /*********************************************************************
  * Metodo: clicouReiniciar
  * Funcao: Reiniciar toda a simulacao do inicio, escondendo os trens, interrompendo a thread, zerando o velocimetro (os sliders) e desativando o audio
  * Parametros: Evento de clique
  * Retorno: void
  ******************************************************************* */
  @FXML
  public void clicouReiniciar(ActionEvent event) {
    grupoMenu.setVisible(true);
    
    if (!tremAzulThread.isAlive()) { 
      tremAzulThread.resetar();
      tremAzulThread.interrupt();
      mediaPlayer.stop();
    }
    if (!tremVerdeThread.isAlive()) { 
      tremVerdeThread.resetar();
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
    } else if (getAceleradorAzul().getValue() != 0 || getAceleradorVerde().getValue() != 0) { // aceleradorAzul e aceleradorVerde sao os sliders(velocimetro) do trem
      // condicao para dar possibilidade de tocar o audio novamente apos desativa-lo
      mediaPlayer.play();
      ++entrada;
    }
  } // fim do metodo clicouMute
  
  // verificar se selecionou variavel de tratamento
  public boolean selecionouTratamentoDeColisao() {
    if (radioVariavelDeTravamento.isSelected() || radioSolucaoPeterson.isSelected() || radioEstritaAlternancia.isSelected())
      return true;
    return false;
  }

  public ImageView getTremAzul() {
    return tremAzul;
  }

  public ImageView getTremVerde() {
    return tremVerde;
  }

  public ImageView getTremVerdeLadoOposto() {
    return tremVerdeLadoOposto;
  }

  public ImageView getTremAzulLadoOposto() {
    return tremAzulLadoOposto;
  }

  public Slider getAceleradorAzul() {
    return aceleradorAzul;
  }

  public Slider getAceleradorVerde() {
    return aceleradorVerde;
  }
  
  public boolean tremSubindo () {
    if (radioCimaCima.isSelected() || radioBaixoCima.isSelected())
      return true;
    return false;
  }

  public RadioButton getRadioBaixoBaixo() {
    return radioBaixoBaixo;
  }

  public RadioButton getRadioBaixoCima() {
    return radioBaixoCima;
  }

  public RadioButton getRadioCimaBaixo() {
    return radioCimaBaixo;
  }

  public RadioButton getRadioCimaCima() {
    return radioCimaCima;
  }

  public ToggleGroup getGrupoRadiosButtonsDirecao() {
    return grupoRadiosButtonsDirecao;
  }

  
  
} // Fim do PlataformaController
