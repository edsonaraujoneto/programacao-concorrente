package model;
/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/08/2023
* Ultima alteracao.: 25/09/2023
* Nome.............: Thread trem azul
* Funcao...........: Iniciar a thread de acordo com a direcao escolhida e variar a velocidade ao mudar o slider
*************************************************************** */
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
 
public class TremAzul extends Thread {
  
  
  private final ImageView tremAzul;
  
  private final ImageView tremAzulLadoOposto;
  
  private final Slider aceleradorAzul;
  
  private final PathTransition pathTransitionAzul;
  
  private final Path pathAzul;
  
  private final Object lock;
  
  // construtor da classe
  public TremAzul (ImageView tremAzul,ImageView tremAzulLadoOposto, Slider aceleradorAzul,Object lock) {
    this.tremAzul = tremAzul;
    this.tremAzulLadoOposto = tremAzulLadoOposto;
    this.aceleradorAzul = aceleradorAzul;
    this.lock = lock;
    pathAzul = new Path();
    pathTransitionAzul = new PathTransition();
  }
    
  
  /*********************************************************************
  * Metodo: ajustarVelocidade
  * Funcao: mudar a velocidade do trem de acordo com o valor do slider
  * Parametro: double velocidade
  * Retorno: void
  ******************************************************************* */
  public void ajustarVelocidade (Double velocidade) {
    if (velocidade == 0) {
      pathTransitionAzul.stop();
    }
    else {
      pathTransitionAzul.pause();
      pathTransitionAzul.setRate(velocidade); // taxa de reproducao
      pathTransitionAzul.play();
    }
  }
  
  /*********************************************************************
  * Metodo: setDirecao
  * Funcao: configurar a direcao do trem
  * Parametro: String direcao
  * Retorno: void
  ******************************************************************* */
  public void setDirecao (String direcao) {
    pathAzul.getElements().clear();
    aceleradorAzul.setValue(0);
    
    tremAzul.setTranslateX(0); 
    tremAzul.setTranslateY(0);
    tremAzulLadoOposto.setTranslateX(0);
    tremAzulLadoOposto.setTranslateY(0);
    
    if (direcao.equals("Cima")) {
      tremAzulLadoOposto.setVisible(false);
      tremAzul.setVisible(true);
      pathAzul.getElements().add(new MoveTo(300, 300)); // ponto de partida
      pathAzul.getElements().add(new LineTo(300, 220)); // vai pra frente
      // inicio regiao critica 1
        pathAzul.getElements().add(new LineTo(266, 190)); // vai pra esquerda
        pathAzul.getElements().add(new LineTo(266, 120)); // vai pra frente
        pathAzul.getElements().add(new LineTo(300, 90)); // vai pra direita
      // fim regiao critica 1
      pathAzul.getElements().add(new LineTo(300, 5)); // vai pra frente
      // inicio regiao critica 2

        pathAzul.getElements().add(new LineTo(266, -50)); // vai pra esquerda
        pathAzul.getElements().add(new LineTo(266, -100)); // vai pra frente
        pathAzul.getElements().add(new LineTo(300, -140)); // vai pra direita
      // fim regiao critica 2
      pathAzul.getElements().add(new LineTo(300, -220)); // vai pra frente
      
      pathTransitionAzul.setNode(tremAzul);
    }
    else if (direcao.equals("Baixo")) {
      tremAzulLadoOposto.setVisible(true);
      tremAzul.setVisible(false);
      pathAzul.getElements().add(new MoveTo(300, 300)); // ponto de partida
      pathAzul.getElements().add(new LineTo(300, 380)); // vai pra frente
      // inicio regiao critica 2
        pathAzul.getElements().add(new LineTo(268, 400)); // vai pra esquerda
        pathAzul.getElements().add(new LineTo(268, 470)); // vai pra frente
        pathAzul.getElements().add(new LineTo(300, 500)); // vai pra direita
      // fim regiao critica 2
      pathAzul.getElements().add(new LineTo(300, 585)); // vai pra frente
      // inicio regiao critica 1
        pathAzul.getElements().add(new LineTo(268, 635)); // vai pra esquerda
        pathAzul.getElements().add(new LineTo(268, 685)); // vai pra frente
        pathAzul.getElements().add(new LineTo(300, 725)); // vai pra direita
      // fim regiao critica 1
      pathAzul.getElements().add(new LineTo(300, 825)); // vai pra frente
      
      pathTransitionAzul.setNode(tremAzulLadoOposto);
    }
    pathTransitionAzul.setPath(pathAzul);
    pathTransitionAzul.setCycleCount(PathTransition.INDEFINITE);
    pathTransitionAzul.setInterpolator(Interpolator.LINEAR);
    pathTransitionAzul.setDuration(Duration.seconds(5));
  }
  
  /*********************************************************************
  * Metodo: parar
  * Funcao: parar o movimento do trem
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void parar() {
    if (pathTransitionAzul != null) {
      pathTransitionAzul.stop();
      tremAzulLadoOposto.setVisible(false);
      tremAzul.setVisible(false);
      aceleradorAzul.setValue(0);
    }
  }
  
  @Override
  public void run () {
    // Listener que chama o metodo ajustarVelocidade toda vez que o slider é alterado
    aceleradorAzul.valueProperty().addListener((observable, oldValue, newValue) -> {
      synchronized (lock) {
        ajustarVelocidade(newValue.doubleValue());
      }
    });
  } // fim do metodo run
  
} // fim da classe
