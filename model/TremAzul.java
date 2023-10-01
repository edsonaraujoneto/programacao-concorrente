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
    
  private final ImageView tremAzul;
  
  private final ImageView tremAzulLadoOposto;
  
  private ImageView tremMovendo;
  
  private final Slider aceleradorAzul;
  
  private PlataformaController controller;
  
  private double velocidadeTrem;
  
  private boolean pausarThread = false;
  
  // construtor da classe
  public TremAzul (PlataformaController controller) {
    this.controller = controller;
    this.aceleradorAzul = controller.getAceleradorAzul();
    this.tremAzul = controller.getTremAzul();
    this.tremAzulLadoOposto = controller.getTremAzulLadoOposto();
  }
  
  /*********************************************************************
  * Metodo: setDirecao
  * Funcao: configurar a direcao do trem
  * Parametro: String direcao
  * Retorno: void
  ******************************************************************* */
  public void setDirecao (String direcao) {
    resetar();
    
    if (direcao.equals("Cima")) {
      tremAzul.setVisible(true);
    }
    else if (direcao.equals("Baixo")) {
      tremAzulLadoOposto.setVisible(true);
    }

  }
  
  /*********************************************************************
  * Metodo: resetar
  * Funcao: resetar o movimento do trem
  * Parametros: void
  * Retorno: void
  ******************************************************************* */
  public void resetar() {
    tremAzul.setVisible(false);
    tremAzulLadoOposto.setVisible(false);
    aceleradorAzul.setValue(0);
  }
  
  public void girarTrem (int posicao,String lado, ImageView trem) throws InterruptedException {
    
    double x = trem.getX();
    double y = trem.getY();
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
          trem.setX(finalX);
          trem.setY(finalY);
        });

        try {
            Thread.sleep((long) velocidadeTrem);
        } catch (InterruptedException e) {
            break;
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
          trem.setX(finalX);
          trem.setY(finalY);
        });

        try {
            Thread.sleep((long) velocidadeTrem);
        } catch (InterruptedException e) {
            break;
        }
      }    
    }
  }
  
  public void andarTrem (int posicaoY, String direcao, ImageView trem) throws InterruptedException {
    
    if (direcao.equals("Subir") ) {
      double y = trem.getY();
      for (int c = 0; c < posicaoY; c++) {
        y--;
        final double finalY = y;
        Platform.runLater(() -> {
          trem.setY(finalY);
        });

        try {
            Thread.sleep((long) velocidadeTrem);
        } catch (InterruptedException e) {
            break;
        }
      }
    }
    else if (direcao.equals("Descer") ){
       double y = trem.getY();
      for (int c = 0; c < posicaoY; c++) {
        y++;
        final double finalY = y;
        Platform.runLater(() -> {
          trem.setY(finalY);
        });

        try {
            Thread.sleep((long) velocidadeTrem);
        } catch (InterruptedException e) {
            break;
        }
      }     
    }
  }
  
  @Override
  public void run () {
    System.out.println("Executando ThreadTremAzul");
      while (true) {
        synchronized (this) {
          while (pausarThread) {
            try {
              wait();
            } catch (InterruptedException ex) {
              Logger.getLogger(TremAzul.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }
        try {
          if (controller.tremSubindo() ) {
            this.andarTrem( 100,"Subir", tremAzul);
            // inicio regiao critica embaixo
            this.girarTrem( 33,"Esquerda", tremAzul);
            this.andarTrem( 55,"Subir",tremAzul);
            this.girarTrem(33,"Direita",tremAzul);
            // fim regiao critica embaixo
            this.andarTrem( 100,"Subir",tremAzul);
            //inicio regiao critica cima
            this.girarTrem( 33, "Esquerda",tremAzul);
            this.andarTrem(45,"Subir",tremAzul);
            this.girarTrem(33,"Direita",tremAzul);
            // fim regiao critica cima
            this.andarTrem(100,"Subir",tremAzul);
            tremAzul.setX(0.0);
            Platform.runLater(() -> tremAzul.setX(0.0));
            tremAzul.setY(0.0);
            Platform.runLater(() -> tremAzul.setY(0.0));
          } else  {
            this.andarTrem( 100,"Descer",tremAzulLadoOposto);
            // inicio regiao critica embaixo
            this.girarTrem( 33,"Esquerda",tremAzulLadoOposto);
            this.andarTrem( 55,"Descer",tremAzulLadoOposto);
            this.girarTrem(33,"Direita",tremAzulLadoOposto);
            // fim regiao critica embaixo
            this.andarTrem( 100,"Descer",tremAzulLadoOposto);
            //inicio regiao critica cima
            this.girarTrem( 33, "Esquerda",tremAzulLadoOposto);
            this.andarTrem(45,"Descer",tremAzulLadoOposto);
            this.girarTrem(33,"Direita",tremAzulLadoOposto);
            // fim regiao critica cima
            this.andarTrem(100,"Descer",tremAzulLadoOposto);
            tremAzulLadoOposto.setX(0.0);
            Platform.runLater(() -> tremAzulLadoOposto.setX(0.0));
            tremAzulLadoOposto.setY(0.0);
            Platform.runLater(() -> tremAzulLadoOposto.setY(0.0));
          }
        } catch (InterruptedException ex) {
          Logger.getLogger(TremAzul.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
  } // fim do metodo run

  public double getVelocidadeTrem() {
    return velocidadeTrem;
  }

  public void setVelocidadeTrem(double velocidadeTrem) {
    this.velocidadeTrem = velocidadeTrem;
  }

  public void setPausarThread(boolean pausarThread) {
    this.pausarThread = pausarThread;
  }
  
  
} // fim da classe
