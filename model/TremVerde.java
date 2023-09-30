package model;
/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/08/2023
* Ultima alteracao.: 25/09/2023
* Nome.............: Thread trem azul
* Funcao...........: Iniciar a thread de acordo com a direcao escolhida e variar a velocidade ao mudar o slider
*************************************************************** */
import controller.PlataformaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class TremVerde extends Thread {
    
  private final ImageView tremVerde;
  
  private final ImageView tremVerdeLadoOposto;
  
  private final Slider aceleradorVerde;
  
  private boolean movimentar = true;
  
  private PlataformaController controller;
  
  // construtor da classe
  public TremVerde (PlataformaController controller) {
    this.controller = controller;
    tremVerde = controller.getTremVerde();
    tremVerdeLadoOposto = controller.getTremVerdeLadoOposto();
    aceleradorVerde = controller.getAceleradorVerde();
  }
    
  /*********************************************************************
  * Metodo: ajustarVelocidade
  * Funcao: mudar a velocidade do trem de acordo com o valor do slider
  * Parametro: double velocidade
  * Retorno: void
  ******************************************************************* */ 
  
  /*********************************************************************
  * Metodo: setDirecao
  * Funcao: configurar a direcao do trem
  * Parametro: String direcao
  * Retorno: void
  ******************************************************************* */
  public void setDirecao (String direcao) {
    resetar();
    
    if (direcao.equals("Cima")) {
      tremVerde.setVisible(true);
    }
    else if (direcao.equals("Baixo")) {
      tremVerdeLadoOposto.setVisible(true);
    }

  }
  
  /*********************************************************************
  * Metodo: resetar
  * Funcao: resetar o movimento do trem
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void resetar() {
    tremVerdeLadoOposto.setVisible(false);
    tremVerde.setVisible(false);
    aceleradorVerde.setValue(0);
  }
  public void girarTrem (int posicao, double rotacao, String lado) throws InterruptedException {

    tremVerde.setRotate(rotacao);
    
    double x = tremVerde.getX();
    double y = tremVerde.getY();
    if (lado.equals("Direita")) {
      for (int c = 0; c < posicao; c++) {
        x++;
        y--;
        final double finalX = x;
        final double finalY = y;

        // Usar Platform.runLater() para atualizar a interface do usuário
        Platform.runLater(() -> {
          tremVerde.setX(finalX);
          tremVerde.setY(finalY);
        });

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      }
    } else {
        for (int c = 0; c < posicao; c++) {
        x--;
        y--;
        final double finalX = x;
        final double finalY = y;

        // Usar Platform.runLater() para atualizar a interface do usuário
        Platform.runLater(() -> {
          tremVerde.setX(finalX);
          tremVerde.setY(finalY);
        });

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      }    
    }
  }
  
  public void subirTrem (int posicaoY) throws InterruptedException {
    tremVerde.setRotate(0.0);
    
    double y = tremVerde.getY();
    for (int c = 0; c < posicaoY; c++) {
      y++;
      final double finalY = y;
      Platform.runLater(() -> {
        tremVerde.setY(finalY);
      });

      try {
          Thread.sleep(10);
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
    }

  }
  
  @Override
  public void run () {
    System.out.println("Executando ThreadTremVerde");
    while (true) {
      try {
        this.subirTrem( 100);
        // inicio regiao critica embaixo
        this.girarTrem( 33,-15,"Esquerda");
        this.subirTrem( 45);
        this.girarTrem(33,15,"Direita");
        // fim regiao critica embaixo
        this.subirTrem( 100);
        //inicio regiao critica cima
        this.girarTrem( 33,-15, "Esquerda");
        this.subirTrem(45);
        this.girarTrem(33,15,"Direita");
        // fim regiao critica cima
        this.subirTrem(100);
        tremVerde.setX(0);
        tremVerde.setY(0);
      } catch (InterruptedException ex) {
        Logger.getLogger(TremVerde.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  } // fim do metodo run
  
} // fim da classe
