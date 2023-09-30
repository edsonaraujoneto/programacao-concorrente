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

public class TremAzul extends Thread {
    
  private ImageView tremAzul;
  
  private final Slider aceleradorAzul;
  
  private boolean movimentar = true;
  
  private PlataformaController controller;
  
  // construtor da classe
  public TremAzul (PlataformaController controller) {
    this.controller = controller;
    aceleradorAzul = controller.getAceleradorAzul();
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
      tremAzul = controller.getTremAzul();
    }
    else if (direcao.equals("Baixo")) {
      tremAzul = controller.getTremAzulLadoOposto();
    }

    tremAzul.setVisible(true);
  }
  
  /*********************************************************************
  * Metodo: resetar
  * Funcao: resetar o movimento do trem
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void resetar() {
    if (tremAzul != null)
      tremAzul.setVisible(false);
    aceleradorAzul.setValue(0);
  }
  public void girarTrem (int posicao,String lado) throws InterruptedException {
    
    double x = tremAzul.getX();
    double y = tremAzul.getY();
    if (lado.equals("Direita")) {
      for (int c = 0; c < posicao; c++) {
        x++;
        if(!controller.tremSubindo())
          y++;
        else
          y--;
        final double finalX = x;
        final double finalY = y;

        // Usar Platform.runLater() para atualizar a interface do usuário
        Platform.runLater(() -> {
          tremAzul.setX(finalX);
          tremAzul.setY(finalY);
        });

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      }
    } else if (lado.equals("Esquerda")){
        for (int c = 0; c < posicao; c++) {
        x--;
        if(!controller.tremSubindo())
          y++;
        else
          y--;
        final double finalX = x;
        final double finalY = y;

        // Usar Platform.runLater() para atualizar a interface do usuário
        Platform.runLater(() -> {
          tremAzul.setX(finalX);
          tremAzul.setY(finalY);
        });

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      }    
    }
  }
  
  public void andarTrem (int posicaoY, String direcao) throws InterruptedException {
    
    if (direcao.equals("Subir")) {
      double y = tremAzul.getY();
      for (int c = 0; c < posicaoY; c++) {
        y--;
        final double finalY = y;
        Platform.runLater(() -> {
          tremAzul.setY(finalY);
        });

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      }
    }
    else if (direcao.equals("Descer")){
       double y = tremAzul.getY();
      for (int c = 0; c < posicaoY; c++) {
        y++;
        final double finalY = y;
        Platform.runLater(() -> {
          tremAzul.setY(finalY);
        });

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      }     
    }

  }
  
  @Override
  public void run () {
    System.out.println("Executando ThreadTremAzul");
      while (true) {
        try {
          if (controller.tremSubindo()) {
            this.andarTrem( 100,"Subir");
            // inicio regiao critica embaixo
            this.girarTrem( 33,"Esquerda");
            this.andarTrem( 55,"Subir");
            this.girarTrem(33,"Direita");
            // fim regiao critica embaixo
            this.andarTrem( 100,"Subir");
            //inicio regiao critica cima
            this.girarTrem( 33, "Esquerda");
            this.andarTrem(45,"Subir");
            this.girarTrem(33,"Direita");
            // fim regiao critica cima
            this.andarTrem(100,"Subir");
            tremAzul.setX(0.0);
            tremAzul.setY(0.0);
          } else {
            this.andarTrem( 100,"Descer");
            // inicio regiao critica embaixo
            this.girarTrem( 33,"Esquerda");
            this.andarTrem( 55,"Descer");
            this.girarTrem(33,"Direita");
            // fim regiao critica embaixo
            this.andarTrem( 100,"Descer");
            //inicio regiao critica cima
            this.girarTrem( 33, "Esquerda");
            this.andarTrem(45,"Descer");
            this.girarTrem(33,"Direita");
            // fim regiao critica cima
            this.andarTrem(100,"Descer");
            tremAzul.setX(0.0);
            tremAzul.setY(0.0);
          }
        } catch (InterruptedException ex) {
          Logger.getLogger(TremAzul.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
  } // fim do metodo run
  
} // fim da classe
