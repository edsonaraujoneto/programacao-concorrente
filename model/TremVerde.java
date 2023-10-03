package model;
/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/08/2023
* Ultima alteracao.: 25/09/2023
* Nome.............: Thread trem Verde
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
  
  private ImageView tremMovendo;
  
  private final Slider aceleradorVerde;
  
  private PlataformaController controller;
  
  private double velocidadeTrem;
  
  private boolean pausarThread = false;
  
  // construtor da classe
  public TremVerde (PlataformaController controller) {
    this.controller = controller;
    this.aceleradorVerde = controller.getAceleradorVerde();
    this.tremVerde = controller.getTremVerde();
    this.tremVerdeLadoOposto = controller.getTremVerdeLadoOposto();
  }
  
  public void verificar() {
    synchronized (this) {
      while (getPausarThread()) {
        try {
          wait();
        } catch (InterruptedException ex) {
          Logger.getLogger(TremVerde.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
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
    tremVerde.setVisible(false);
    tremVerdeLadoOposto.setVisible(false);
    Platform.runLater(() -> tremVerde.setX(0.0));
    Platform.runLater(() -> tremVerde.setY(0.0));
    Platform.runLater(() -> tremVerdeLadoOposto.setX(0.0));
    Platform.runLater(() -> tremVerdeLadoOposto.setY(0.0));
    aceleradorVerde.setValue(0);
  }
  
  public void girarTrem (int posicao,String lado, ImageView trem) throws InterruptedException {
    
    double x = trem.getX();
    double y = trem.getY();
    if (lado.equals("Direita")) {
      for (int c = 0; c < posicao; c++) {
        verificar();
        x++;
        if(!controller.tremVerdeSubindo())
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
        verificar();
        x--;
        if(!controller.tremVerdeSubindo())
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
    
    if (direcao.equals("Subir")) {
      double y = trem.getY();
      for (int c = 0; c < posicaoY; c++) {
        verificar();
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
    else if (direcao.equals("Descer")  ){
       double y = trem.getY();
      for (int c = 0; c < posicaoY; c++) {
        verificar(); // verificar se não foi pausado o trem
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
      while (true) { 
        try {
          if (controller.tremVerdeSubindo()  && controller.isStart() ) {
            tremVerde.setX(0.0);
            Platform.runLater(() -> tremVerde.setX(0.0));
            tremVerde.setY(0.0);
            Platform.runLater(() -> tremVerde.setY(0.0));
            
            this.andarTrem( 90,"Subir", tremVerde);
            // inicio regiao critica embaixo
            if (controller.selecionouVariavelDeTratamento()) {
              while (controller.getVariavelDeTravamentoDeBaixo() == 1) {System.out.println();}
              controller.entrouNaRegiaoCriticaDeBaixo();
              this.girarTrem( 30,"Direita", tremVerde);
              this.andarTrem( 55,"Subir",tremVerde);
              this.girarTrem(30,"Esquerda",tremVerde);
              controller.saiuDaRegiaoCriticaDeBaixo();
              // fim regiao critica embaixo
              this.andarTrem( 95,"Subir",tremVerde);
              //inicio regiao critica cima
              while (controller.getVariavelDeTravamentoDeCima() == 1) {System.out.println();}
              controller.entrouNaRegiaoCriticaDeCima();             
              this.girarTrem( 30, "Direita",tremVerde);
              this.andarTrem(60,"Subir",tremVerde);
              this.girarTrem(30,"Esquerda",tremVerde);
              controller.saiuDaRegiaoCriticaDeCima();
              // fim regiao critica cima
              this.andarTrem(100,"Subir",tremVerde);
            }
            
          } else if(controller.isStart()) {
            if (controller.selecionouVariavelDeTratamento()) {
              this.andarTrem( 80,"Descer",tremVerdeLadoOposto);
              // inicio regiao critica cima
              while (controller.getVariavelDeTravamentoDeCima() == 1) {System.out.println(); }
              controller.entrouNaRegiaoCriticaDeCima();
              this.girarTrem( 30,"Direita",tremVerdeLadoOposto);
              this.andarTrem( 55,"Descer",tremVerdeLadoOposto);
              this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
              controller.saiuDaRegiaoCriticaDeCima();
              // fim regiao critica cima
              this.andarTrem( 85,"Descer",tremVerdeLadoOposto);
              //inicio regiao critica baixo
              while (controller.getVariavelDeTravamentoDeBaixo() == 1) {System.out.println(); }
              controller.entrouNaRegiaoCriticaDeBaixo();
              this.girarTrem( 30, "Direita",tremVerdeLadoOposto);
              this.andarTrem(75,"Descer",tremVerdeLadoOposto);
              this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
              controller.saiuDaRegiaoCriticaDeBaixo();
              // fim regiao critica baixo
              this.andarTrem(120,"Descer",tremVerdeLadoOposto);
            }
            tremVerdeLadoOposto.setX(0.0);
            Platform.runLater(() -> tremVerdeLadoOposto.setX(0.0));
            tremVerdeLadoOposto.setY(0.0);
            Platform.runLater(() -> tremVerdeLadoOposto.setY(0.0));
          }
        } catch (InterruptedException ex) {
          break;
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

  public boolean getPausarThread() {
    return pausarThread;
  }
  
  
} // fim da classe
