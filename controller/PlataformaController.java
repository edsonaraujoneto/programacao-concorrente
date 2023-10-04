package controller;

/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/08/2023
* Ultima alteracao.: 07/10/2023
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
  
  private boolean start = false;
  /********************************************************/
  
  private static int variavelDeTravamentoDeBaixo = 0;
  
  private static int variavelDeTravamentoDeCima = 0;
  
  /*********************************************************/
  
  private static int vezDeCima = 0;   // 1 trem Azul - 0 tremVerde
  
  private static int vezDeBaixo = 0;   // 1 trem Azul - 0 tremVerde
  
  /************************************************************************************************/
  // interesse do recurso compartilhado de cima
  private boolean interesseDeCima [] = new boolean [2]; // 1 trem Azul - 0 trem Verde 
  
  // interesse do recurso compartilhado de baixo
  private boolean interesseDeBaixo [] = new boolean [2]; // 1 trem Azul - 0 trem Verde 
  
  private static int vezDeCimaSP;
  
  private static int vezDeBaixoSP;
  /**************************************************************************************************/
  
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
    
    // criando as threads e passando como parametro o controller
    tremAzulThread = new TremAzul(this);
    tremVerdeThread = new TremVerde(this);
    
    // criando um grupo onde sera possivel selecionar apenas um radio button de direcao
    grupoRadiosButtonsDirecao = new ToggleGroup();
    radioBaixoBaixo.setToggleGroup(getGrupoRadiosButtonsDirecao());
    radioBaixoCima.setToggleGroup(getGrupoRadiosButtonsDirecao());
    radioCimaCima.setToggleGroup(getGrupoRadiosButtonsDirecao());
    radioCimaBaixo.setToggleGroup(getGrupoRadiosButtonsDirecao());
    
    // criando um grupo onde sera possivel selecionar apenas um radio button de tratamento de colisão
    ToggleGroup grupoRadiosButtonsTratamento = new ToggleGroup();
    radioVariavelDeTravamento.setToggleGroup(grupoRadiosButtonsTratamento);
    radioSolucaoPeterson.setToggleGroup(grupoRadiosButtonsTratamento);
    radioEstritaAlternancia.setToggleGroup(grupoRadiosButtonsTratamento);

    aceleradorAzul.valueProperty().addListener((observable, oldValue, newValue) -> {
      iniciarThreadTremAzul(newValue.doubleValue());
    });
    
    aceleradorVerde.valueProperty().addListener((observable,oldValue,newValue) -> {
      iniciarThreadTremVerde(newValue.doubleValue());
    });

  } // fim da classe initialize
  
  
  /*********************************************************************
  * Metodo: clicouIniciar
  * Funcao: Desabilita a visibilidade da tela de menu e ajusta a posicao de acordo com o radioButton de direcao escolhido
  * Parametros: Evento de clique
  * Retorno: void
  ******************************************************************* */
  @FXML
  public void clicouIniciar(ActionEvent event) {
    if (selecionouPosicao() && selecionouTratamentoDeColisao()) {
      grupoMenu.setVisible(false);
      ajustarPosicao();
    }
  }
  
  /*********************************************************************
  * Metodo: clicouReiniciar
  * Funcao: Habilita a visibilidade do menu e interrompe as threads caso estejam vivas para instanciar uma nova
  * Parametros: Evento de clique
  * Retorno: void
  ******************************************************************* */
  @FXML
  public void clicouReiniciar(ActionEvent event) {
    // Necessario colocar as vT em 0 para caso esteja em 1 quando foi reiniciado.
    setVariavelDeTravamentoDeBaixo(0); 
    setVariavelDeTravamentoDeCima(0);
    
    // Pausa ambas threads quando o botão de reiniciar é clicado
    iniciarThreadTremAzul(0);
    iniciarThreadTremVerde(0);
    
    grupoMenu.setVisible(true);
    start = false;
    
    if (tremAzulThread.isAlive()) {
      tremAzulThread.interrupt(); 
      tremAzulThread = new TremAzul(this);
      mediaPlayer.stop();
    }
    
    if (tremVerdeThread.isAlive()) { 
      tremVerdeThread.interrupt();
      tremVerdeThread = new TremVerde(this);
      mediaPlayer.stop();
    }
  } // Fim do metodo clicouReiniciar
  
  /*********************************************************************
  * Metodo: iniciarThreadTremAzul
  * Funcao: Inicia a thread somente se a velocidade foi alterada
  * Parametros: double velocidade
  * Retorno: void
  ******************************************************************* */
  public void iniciarThreadTremAzul (double velocidade) {

    if (aceleradorAzul.getValue() != 0 ) {
      if (!tremAzulThread.isAlive()) { // caso a thread não esteja viva, é iniciada.
        start = true;
        tremAzulThread.start();
      } 
      
      tremAzulThread.setVelocidadeTrem( 20/velocidade); //Maximo de 4 e mínimo de 20 aproximadamente
      
      if (tremAzulThread.getPausarThread()) { // caso a Thread esteja pausada, volta a movimentar
        synchronized (tremAzulThread) {
          tremAzulThread.setPausarThread(false);
          tremAzulThread.notify();
        }
      }
    } // fim if acelerador diferente de 0
    else if(aceleradorAzul.getValue() == 0 && tremAzulThread.isAlive()) {
      synchronized (tremAzulThread) {
        tremAzulThread.setPausarThread(true); // pausa a thread
      }
    } // fim if acelerador igual a 0
  } // fim iniciarThreadTremAzul
  
  
  /*********************************************************************
  * Metodo: iniciarThreadTremVerde
  * Funcao: Inicia a thread somente se a velocidade foi alterada
  * Parametros: double velocidade
  * Retorno: void
  ******************************************************************* */
  public void iniciarThreadTremVerde (double velocidade) {
    
    if (aceleradorVerde.getValue() != 0 ) {
      
      if (!tremVerdeThread.isAlive()) { // caso a thread não esteja viva, é iniciada.
        tremVerdeThread.start();
        start = true;
      } 
      tremVerdeThread.setVelocidadeTrem( 20/velocidade); //Maximo de 4 e mínimo de 20 aproximadamente
      
      if (tremVerdeThread.getPausarThread()) { // caso a Thread esteja pausada, volta a movimentar
        synchronized (tremVerdeThread) {
          tremVerdeThread.setPausarThread(false);
          tremVerdeThread.notify();
        }
      }
    } // fim if acelerador diferente de 0 
    else if(aceleradorVerde.getValue() == 0 ) {
      synchronized (tremVerdeThread) {
        tremVerdeThread.setPausarThread(true); // pausa a thread
      }
    } // fim if acelerador igual a 0
  } // fim iniciarThreadTremVerde
  
  
  /*********************************************************************
  * Metodo: selecionouPosicao
  * Funcao: Verifica se o radioButton de posicao foi selecionado
  * Parametros: void
  * Retorno: boolean
  ******************************************************************* */
  //Verificar se o usuario selecionou a posicao do trem
  public boolean selecionouPosicao() {
    if (getRadioCimaCima().isSelected() || getRadioBaixoBaixo().isSelected() || getRadioBaixoCima().isSelected() || getRadioCimaBaixo().isSelected()) 
      return true;
    return false;
  }

  /*********************************************************************
  * Metodo: ajustarPosicao
  * Funcao: configurar a direcao do trem de acordo com o radioButton selecionado
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void ajustarPosicao() {
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
  
  
  /*********************************************************************
  * Metodo: selecionouTratamentoDeColisao
  * Funcao: Verifica se o radioButton de tratamentoDeColisao foi selecionado
  * Parametros: void
  * Retorno: boolean
  ******************************************************************* */
  public boolean selecionouTratamentoDeColisao() {
    if (radioVariavelDeTravamento.isSelected() || radioSolucaoPeterson.isSelected() || radioEstritaAlternancia.isSelected())
      return true;
    return false;
  }

  /*********************************************************************
  * Metodo: tremAzulSubindo
  * Funcao: Verifica se o radiobutton no qual o tremAzul sobe esta selecionado
  * Parametros: void
  * Retorno: boolean
  ******************************************************************* */
  public boolean tremAzulSubindo () {
    if (radioCimaCima.isSelected() || radioBaixoCima.isSelected())
      return true;
    return false;
  }
  
  /*********************************************************************
  * Metodo: tremVerdeSubindo
  * Funcao: Verifica se o radiobutton no qual o tremVerde sobe esta selecionado
  * Parametros: void
  * Retorno: boolean
  ******************************************************************* */
  public boolean tremVerdeSubindo () {
    if (radioCimaCima.isSelected() || radioCimaBaixo.isSelected())
      return true;
    return false;
  }
  
  public boolean isStart() {
    return start;
  }
  
  public boolean selecionouVariavelDeTravamento () {
    if (radioVariavelDeTravamento.isSelected()) 
      return true;
    return false;
  }
  
  public boolean selecionouAlternanciaExplicita() {
    if (radioEstritaAlternancia.isSelected()) 
      return true;
    return false;
  }
  
  public boolean selecionouSolucaoDePeterson() {
    if (radioSolucaoPeterson.isSelected())
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

  public static int getVariavelDeTravamentoDeBaixo() {
    return variavelDeTravamentoDeBaixo;
  }

  public static int getVariavelDeTravamentoDeCima() {
    return variavelDeTravamentoDeCima;
  }

  public static void setVariavelDeTravamentoDeBaixo(int aVariavelDeTravamentoDeBaixo) {
    variavelDeTravamentoDeBaixo = aVariavelDeTravamentoDeBaixo;
  }

  public static void setVariavelDeTravamentoDeCima(int aVariavelDeTravamentoDeCima) {
    variavelDeTravamentoDeCima = aVariavelDeTravamentoDeCima;
  }
  
  public static int getVezDeBaixo() {
    return vezDeBaixo;
  }
  
  public static int getVezDeCima() {
    return vezDeBaixo;
  }
  
  public static void setVezDeCima(int aVezDeCima) {
    vezDeCima = aVezDeCima;
  }

  public static void setVezDeBaixo(int aVezDeBaixo) {
    vezDeBaixo = aVezDeBaixo;
  }
  
  public void entrouNaRegiaoCriticaDeBaixo (int id) {
    System.out.println("Entrou na regiao Critica de Baixo");
    System.out.println("ID:" +id);
    int outro = 1-id;
    System.out.println("OUTRO:" + outro);
    System.out.println("Interesse do Outro: " + interesseDeBaixo[outro]);
    interesseDeBaixo [id] = true;
    vezDeBaixoSP = id;
    while (vezDeBaixoSP == id && interesseDeBaixo[outro] == true) {System.out.println();}
  }
  
  public void saiuDaRegiaoCriticaDeBaixo (int id) {
    interesseDeBaixo [id] = false;
  }
  
  public void entrouNaRegiaoCriticaDeCima (int id ) {
    int outro = 1-id;
    interesseDeCima [id] = true; // ok
    vezDeCimaSP = id;
    while (vezDeCimaSP == id && interesseDeCima[outro] == true){System.out.println();}
  }
  
  public void saiuDaRegiaoCriticaDeCima (int id) {
    interesseDeCima [id] = false;
  }

} // Fim do PlataformaController
