package model;
/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/08/2023
* Ultima alteracao.: 25/09/2023
* Nome.............: Thread trem Verde
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
 
public class TremVerde extends Thread {
  
  private final ImageView tremVerde;
  
  private final ImageView tremVerdeLadoOposto;
  
  private final Slider aceleradorVerde;
  
  private final PathTransition pathTransitionVerde;
  
  private final Path pathVerde;
  
  private final Object lock;
  
  // construtor da classe
  public TremVerde (ImageView tremVerde,ImageView tremVerdeLadoOposto, Slider aceleradorVerde, Object lock) {
    this.tremVerde = tremVerde;
    this.tremVerdeLadoOposto = tremVerdeLadoOposto;
    this.aceleradorVerde = aceleradorVerde;
    this.lock = lock;
    pathVerde = new Path();
    pathTransitionVerde = new PathTransition();
  }
  
  /*********************************************************************
  * Metodo: ajustarVelocidade
  * Funcao: mudar a velocidade do trem de acordo com o valor do slider
  * Parametro: double velocidade
  * Retorno: void
  ******************************************************************* */
  public void ajustarVelocidade (Double velocidade) {
    if (velocidade == 0) {
      pathTransitionVerde.stop();
    }
    else {
      pathTransitionVerde.pause();
      pathTransitionVerde.setRate(velocidade); // taxa de reproducao
      pathTransitionVerde.play();
    }
  }
  
  /*********************************************************************
  * Metodo: setDirecao
  * Funcao: configurar a direcao do trem
  * Parametro: String direcao
  * Retorno: void
  ******************************************************************* */
  public void setDirecao (String direcao) {
    pathVerde.getElements().clear();
    aceleradorVerde.setValue(0);
    
    tremVerde.setTranslateX(0); 
    tremVerde.setTranslateY(0);
    tremVerdeLadoOposto.setTranslateX(0);
    tremVerdeLadoOposto.setTranslateY(0);
    
    if (direcao.equals("Cima")) {
      tremVerdeLadoOposto.setVisible(false);
      tremVerde.setVisible(true);
      pathVerde.getElements().add(new MoveTo(300, 300)); // ponto de partida
      pathVerde.getElements().add(new LineTo(300, 220)); // vai pra frente
      // inicio regiao critica 1
      synchronized (lock) {
        pathVerde.getElements().add(new LineTo(330, 190)); // vai pra direita
        pathVerde.getElements().add(new LineTo(330, 120)); // vai pra frente
        pathVerde.getElements().add(new LineTo(300, 90)); // vai pra esquerda
      }
      // fim regiao critica 1
      pathVerde.getElements().add(new LineTo(300, 5)); // vai pra frente
      // inicio regiao critica 2
      synchronized (lock) {
        pathVerde.getElements().add(new LineTo(330, -50)); // vai pra direita
        pathVerde.getElements().add(new LineTo(330, -100)); // vai pra frente
        pathVerde.getElements().add(new LineTo(300, -140)); // vai pra esquerda
      }
      // fim regiao critica 2
      pathVerde.getElements().add(new LineTo(300, -220)); // vai pra frente
      
      pathTransitionVerde.setNode(tremVerde);
    }
    else if (direcao.equals("Baixo")) {
      tremVerdeLadoOposto.setVisible(true);
      tremVerde.setVisible(false);
      pathVerde.getElements().add(new MoveTo(300, 300)); // ponto de partida
      pathVerde.getElements().add(new LineTo(300, 380)); // vai pra frente
      // inicio regiao critica 2
      synchronized (lock) {
        pathVerde.getElements().add(new LineTo(330, 400)); // vai pra direita
        pathVerde.getElements().add(new LineTo(330, 470)); // vai pra frente
        pathVerde.getElements().add(new LineTo(300, 500)); // vai pra esquerda
      }
      // fim regiao critica 2
      pathVerde.getElements().add(new LineTo(300, 585)); // vai pra frente
      // inicio regiao critica 1
      synchronized (lock) {
        pathVerde.getElements().add(new LineTo(330, 635)); // vai pra direita
        pathVerde.getElements().add(new LineTo(330, 685)); // vai pra frente
        pathVerde.getElements().add(new LineTo(300, 725)); // vai pra esquerda
      }
      // fim regiao critica 1
      pathVerde.getElements().add(new LineTo(300, 825)); // vai pra frente
      
      pathTransitionVerde.setNode(tremVerdeLadoOposto);
    }
    pathTransitionVerde.setPath(pathVerde);
    pathTransitionVerde.setCycleCount(PathTransition.INDEFINITE);
    pathTransitionVerde.setInterpolator(Interpolator.LINEAR);
    pathTransitionVerde.setDuration(Duration.seconds(5));
  }
  
  /*********************************************************************
  * Metodo: parar
  * Funcao: parar o movimento do trem
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void parar() {
    if (pathTransitionVerde != null) {
      pathTransitionVerde.stop();
      tremVerdeLadoOposto.setVisible(false);
      tremVerde.setVisible(false);
      aceleradorVerde.setValue(0);
    }
  }
  
  @Override
  public void run () {
    // Listener que chama o metodo ajustarVelocidade toda vez que o slider é alterado
    aceleradorVerde.valueProperty().addListener((observable, oldValue, newValue) -> {
      ajustarVelocidade(newValue.doubleValue());
    });
  } // fim do metodo run
  
} // fim da classe
