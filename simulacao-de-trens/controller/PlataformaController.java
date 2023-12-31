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
  @FXML
  private ImageView luzVerdeBaixo;
  @FXML
  private ImageView luzVerdeCima;
  @FXML
  private ImageView luzVermelhaBaixo;
  @FXML
  private ImageView luzVermelhaCima;

  private TremAzul tremAzulThread;
  private TremVerde tremVerdeThread;
  private ToggleGroup grupoRadiosButtonsDirecao;
  private boolean start = false;
  
  /********************************************************/
  // Variavel de Travamento
  private static int variavelDeTravamentoDeBaixo = 0;
  private static int variavelDeTravamentoDeCima = 0;
  /*********************************************************/
  // Alternancia Explicita
  private static int vezDeCima = 0;   // 1 trem Azul - 0 tremVerde
  private static int vezDeBaixo = 0;   // 1 trem Azul - 0 tremVerde
  /************************************************************************************************/
  // Solucao de Peterson
  private boolean interesseDeCima [] = new boolean [2]; // 1 trem Azul - 0 trem Verde 
  private boolean interesseDeBaixo [] = new boolean [2]; // 1 trem Azul - 0 trem Verde 
  private static int vezDeCimaSP;
  private static int vezDeBaixoSP;
  /**************************************************************************************************/
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Configuracoes basicas dos sliders de velocidade
    getAceleradorAzul().setMin(0); // Minimo de velocidade
    getAceleradorAzul().setMax(5); // Maximo de velocidade
    getAceleradorAzul().setValue(0); // Velocidade de inicio
    getAceleradorAzul().setShowTickMarks(true); // Marcar as velocidades
    getAceleradorAzul().setMajorTickUnit(1); // Intervalo entre as velocidades
    getAceleradorAzul().setShowTickLabels(true); // Pontuar as velocidades
    getAceleradorVerde().setMin(0);
    getAceleradorVerde().setMax(5);
    getAceleradorVerde().setValue(0);
    getAceleradorVerde().setShowTickMarks(true);
    getAceleradorVerde().setMajorTickUnit(1);
    getAceleradorVerde().setShowTickLabels(true);
    
    // criando as threads e passando como parametro o mesmo controller 
    tremAzulThread = new TremAzul(this);
    tremVerdeThread = new TremVerde(this);
    
    // criando um grupo onde sera possivel selecionar apenas um radio button de direcao
    grupoRadiosButtonsDirecao = new ToggleGroup();
    radioBaixoBaixo.setToggleGroup(getGrupoRadiosButtonsDirecao());
    radioBaixoCima.setToggleGroup(getGrupoRadiosButtonsDirecao());
    radioCimaCima.setToggleGroup(getGrupoRadiosButtonsDirecao());
    radioCimaBaixo.setToggleGroup(getGrupoRadiosButtonsDirecao());
    
    // Opções padrões de inicio
    radioCimaCima.setSelected(true);
    radioSolucaoPeterson.setSelected(true);
    
    // criando um grupo onde sera possivel selecionar apenas um radio button de tratamento de colisão
    ToggleGroup grupoRadiosButtonsTratamento = new ToggleGroup();
    radioVariavelDeTravamento.setToggleGroup(grupoRadiosButtonsTratamento);
    radioSolucaoPeterson.setToggleGroup(grupoRadiosButtonsTratamento);
    radioEstritaAlternancia.setToggleGroup(grupoRadiosButtonsTratamento);
    
    // Inicialmente a passagem esta livre.
    luzVerdeCima.setVisible(true);
    luzVerdeBaixo.setVisible(true);

    // Listener para mudar a velocidade quando o valor for alterado do slider.
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
    if (selecionouPosicao() && selecionouTratamentoDeColisao()) { // Verifica se os itens minimos foram atendidos
      grupoMenu.setVisible(false); // Esconde o menu (todo o grupo)
      ajustarPosicao(); // Ajusta a posicao dos trens
      // Reseta os interesses de todos como false.
      interesseDeBaixo[0] = false;
      interesseDeBaixo[1] = false;
      interesseDeCima [0] = false;
      interesseDeCima [1] = false;
      
      // a vez inicial é do trem verde
      vezDeCima = 0;
      vezDeBaixo = 0;
      
      // Necessario colocar as vT em 0 para caso esteja em 1 quando foi reiniciado.
      setVariavelDeTravamentoDeBaixo(0); 
      setVariavelDeTravamentoDeCima(0);
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
    
    // Pausa ambas threads quando o botão de reiniciar é clicado
    iniciarThreadTremAzul(0);
    iniciarThreadTremVerde(0);
    start = false; // false não permite que o trem se movimente.
    

    // Caso tenha sido reiniciado com a cor vermelha ligada, ela é apagada.
    apagarLuzVermelhaBaixo();
    apagarLuzVermelhaCima();
  
    grupoMenu.setVisible(true); // Torna o menu visivel
    
    if (tremAzulThread.isAlive()) { // Se a thread está viva
      tremAzulThread.interrupt(); 
      tremAzulThread = new TremAzul(this); // Instancia uma nova após interromper a anterior
    }
    
    if (tremVerdeThread.isAlive()) { 
      tremVerdeThread.interrupt();
      tremVerdeThread = new TremVerde(this);
    }
  } // Fim do metodo clicouReiniciar
  
  /*********************************************************************
  * Metodo: iniciarThreadTremAzul
  * Funcao: Inicia a thread se ela não está viva somente se a velocidade foi alterada, pausa se a velocidade é zero, e aumenta a velocidade caso necessario.
  * Parametros: double velocidade
  * Retorno: void
  ******************************************************************* */
  public void iniciarThreadTremAzul (double velocidade) {

    if (velocidade != 0 ) {
      if (!tremAzulThread.isAlive()) { // caso a thread não esteja viva, é iniciada.
        start = true;
        tremAzulThread.start();
      } 
      tremAzulThread.setVelocidadeTrem( 20/velocidade); // Maximo de 4 e mínimo de 20 aproximadamente
      if (tremAzulThread.getPausarThread()) { // caso a Thread esteja pausada, volta a movimentar
        synchronized (tremAzulThread) {
          tremAzulThread.setPausarThread(false);
          tremAzulThread.notify();
        }
      }
    } // fim if acelerador diferente de 0
    
    else if(velocidade == 0 && tremAzulThread.isAlive()) { // verifica se a velocidade é zero e o trem esta em movimento
      synchronized (tremAzulThread) {
        tremAzulThread.setPausarThread(true); // pausa a thread
      }
    } // fim if acelerador igual a 0
  } // fim iniciarThreadTremAzul
  
  
  /*********************************************************************
  * Metodo: iniciarThreadTremVerde
  * Funcao: Inicia a thread se ela não está viva somente se a velocidade foi alterada, pausa se a velocidade é zero, e aumenta a velocidade caso necessario.
  * Parametros: double velocidade
  * Retorno: void
  ******************************************************************* */
  public void iniciarThreadTremVerde (double velocidade) {
    
    if (velocidade != 0 ) {
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
    else if(velocidade == 0 && tremVerdeThread.isAlive() ) {
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
  
  /*********************************************************************
  * Metodo: selecionouVariavelDeTravamento
  * Funcao: Verifica se o o button de Variavel de Travamento foi selecionado
  * Parametros: void
  * Retorno: boolean
  ******************************************************************* */
  public boolean selecionouVariavelDeTravamento () {
    if (radioVariavelDeTravamento.isSelected()) 
      return true;
    return false;
  }
  
  /*********************************************************************
  * Metodo: selecionouAlternanciaExplicita
  * Funcao: Verifica se o o button de Alternancia Explicita foi selecionado
  * Parametros: void
  * Retorno: boolean
  ******************************************************************* */
  public boolean selecionouAlternanciaExplicita() {
    if (radioEstritaAlternancia.isSelected()) 
      return true;
    return false;
  }
  
  /*********************************************************************
  * Metodo: selecionouSolucaoDePeterson
  * Funcao: Verifica se o o button de solucao de peterson foi selecionado
  * Parametros: void
  * Retorno: boolean
  ******************************************************************* */
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
  
  /*********************************************************************
  * Metodo: entrouNaRegiaoCriticaDeBaixo
  * Funcao: setar o interesse como verdadeiro para a thread que chamou o metodo e verificar se pode ou não acessar essa região critica.
  * Parametros: int id
  * Retorno: void
  ******************************************************************* */
  public void entrouNaRegiaoCriticaDeBaixo (int id) {
    int outro = 1-id;
    interesseDeBaixo [id] = true;
    vezDeBaixoSP = id;
    while (vezDeBaixoSP == id && interesseDeBaixo[outro] == true) {System.out.println();}
  }
  
  /*********************************************************************
  * Metodo: saiuDaRegiaoCriticaDeBaixo
  * Funcao: setar o interesse como falso para thread que chamou a metodo, significa que saiu dessa regiao critica
  * Parametros: int id
  * Retorno: void
  ******************************************************************* */
  public void saiuDaRegiaoCriticaDeBaixo (int id) {
    interesseDeBaixo [id] = false;
  }
  
  /*********************************************************************
  * Metodo: entrouNaRegiaoCriticaDeCima
  * Funcao: setar o interesse como verdadeiro para a thread que chamou o metodo e verificar se pode ou não acessar essa região critica.
  * Parametros: int id
  * Retorno: void
  ******************************************************************* */
  public void entrouNaRegiaoCriticaDeCima (int id ) {
    int outro = 1-id;
    interesseDeCima [id] = true; 
    vezDeCimaSP = id;
    while (vezDeCimaSP == id && interesseDeCima[outro] == true){System.out.println();}
  }
  
  /*********************************************************************
  * Metodo: saiuDaRegiaoCriticaDeBCima
  * Funcao: setar o interesse como falso para thread que chamou a metodo, significa que saiu dessa regiao critica
  * Parametros: int id
  * Retorno: void
  ******************************************************************* */
  public void saiuDaRegiaoCriticaDeCima (int id) {
    interesseDeCima [id] = false;
  }
  
  /*********************************************************************
  * Metodo: acenderLuzVermelhaCima
  * Funcao: Tornar Visivel a imagem com destaque do vermelho e deixar invisivel o verde.
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void acenderLuzVermelhaCima() {
    luzVermelhaCima.setVisible(true);
    luzVerdeCima.setVisible(false);
  }
  
  /*********************************************************************
  * Metodo: apagarLuzVermelhaCima
  * Funcao: Tornar Visivel a imagem com destaque do verde e deixar invisivel o vermelho
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void apagarLuzVermelhaCima() {
    luzVermelhaCima.setVisible(false);
    luzVerdeCima.setVisible(true);
  }
  
  /*********************************************************************
  * Metodo: acenderLuzVermelhaBaixo
  * Funcao: Tornar Visivel a imagem com destaque do vermelho e deixar invisivel o verde.
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void acenderLuzVermelhaBaixo() {
    luzVermelhaBaixo.setVisible(true);
    luzVerdeBaixo.setVisible(false);
  }
  
  /*********************************************************************
  * Metodo: apagarLuzVermelhaBaixo
  * Funcao: Tornar Visivel a imagem com destaque do verde e deixar invisivel o vermelho
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void apagarLuzVermelhaBaixo() {
    luzVermelhaBaixo.setVisible(false);
    luzVerdeBaixo.setVisible(true);
  }

} // Fim do PlataformaController
