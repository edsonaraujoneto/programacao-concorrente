package model;
/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/08/2023
* Ultima alteracao.: 07/10/2023
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
  
  private final Slider aceleradorVerde;
  
  private final PlataformaController controller;
  
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
    if (direcao.equals("Subir") ) {
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
    else if (direcao.equals("Descer") ){
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
        if (controller.tremVerdeSubindo() && controller.isStart() ) { 

          if (controller.selecionouVariavelDeTravamento()) {
            this.andarTrem( 90,"Subir", tremVerde);
            while (controller.getVariavelDeTravamentoDeBaixo() == 1) {System.out.println();}
            controller.setVariavelDeTravamentoDeBaixo(1);
            // inicio regiao critica embaixo
            this.girarTrem( 30,"Direita", tremVerde);
            this.andarTrem( 55,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            // fim regiao critica embaixo
            controller.setVariavelDeTravamentoDeBaixo(0);
            this.andarTrem( 95,"Subir",tremVerde);
            while (controller.getVariavelDeTravamentoDeCima() == 1) {System.out.println();}
            controller.setVariavelDeTravamentoDeCima(1);
            //inicio regiao critica cima
            this.girarTrem( 30, "Direita",tremVerde);
            this.andarTrem(60,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            // fim regiao critica cima
            controller.setVariavelDeTravamentoDeCima(0);
            this.andarTrem(100,"Subir",tremVerde);
          } // fim selecionou variavel de tratamento
          
          else if(controller.selecionouAlternanciaExplicita()) {
            
            this.andarTrem( 90,"Subir", tremVerde);
            while (controller.getVezDeBaixo() == 1) {System.out.println();}
            // inicio regiao critica embaixo
            this.girarTrem( 30,"Direita", tremVerde);
            this.andarTrem( 55,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            // fim regiao critica embaixo
            controller.setVezDeBaixo(1);
            this.andarTrem( 95,"Subir",tremVerde);
            
            while (controller.getVezDeCima() == 1) {System.out.println();}
            //inicio regiao critica cima
            this.girarTrem( 30, "Direita",tremVerde);
            this.andarTrem(60,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            // fim regiao critica cima
            controller.setVezDeCima(1);
            this.andarTrem(100,"Subir",tremVerde);
            
          } // fim alternancia explicita selecionado
          else if(controller.selecionouSolucaoDePeterson()) {
            this.andarTrem( 90,"Subir", tremVerde);
            controller.entrouNaRegiaoCriticaDeBaixo(0);
            // inicio regiao critica embaixo
            this.girarTrem( 30,"Direita", tremVerde);
            this.andarTrem( 55,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            // fim regiao critica embaixo
            controller.entrouNaRegiaoCriticaDeBaixo(0);
            this.andarTrem( 95,"Subir",tremVerde);
            
            controller.entrouNaRegiaoCriticaDeCima(0);
            //inicio regiao critica cima
            this.girarTrem( 30, "Direita",tremVerde);
            this.andarTrem(60,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            // fim regiao critica cima
            controller.saiuDaRegiaoCriticaDeCima(0);
            this.andarTrem(100,"Subir",tremVerde);
          } // fim solucao de peterson

          tremVerde.setX(0.0);
          Platform.runLater(() -> tremVerde.setX(0.0));
          tremVerde.setY(0.0);
          Platform.runLater(() -> tremVerde.setY(0.0));

        } else if(controller.isStart()) { // o trem esta descendo

          if (controller.selecionouVariavelDeTravamento()) {
            this.andarTrem( 80,"Descer",tremVerdeLadoOposto);
            while (controller.getVariavelDeTravamentoDeCima() == 1) {System.out.println();}
            controller.setVariavelDeTravamentoDeCima(1);
            // inicio regiao critica cima
            this.girarTrem( 30,"Direita",tremVerdeLadoOposto);
            this.andarTrem( 55,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            // fim regiao critica cima
            controller.setVariavelDeTravamentoDeCima(0);
            this.andarTrem( 85,"Descer",tremVerdeLadoOposto);
            while (controller.getVariavelDeTravamentoDeBaixo() == 1) {System.out.println();}
            controller.setVariavelDeTravamentoDeBaixo(1);
            //inicio regiao critica baixo
            this.girarTrem( 30, "Direita",tremVerdeLadoOposto);
            this.andarTrem(75,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            // fim regiao critica baixo
            controller.setVariavelDeTravamentoDeBaixo(0);
            this.andarTrem(120,"Descer",tremVerdeLadoOposto);
          } // fim selecionou VariavelDeTravamento
          
          else if(controller.selecionouAlternanciaExplicita()) {
            this.andarTrem( 80,"Descer",tremVerdeLadoOposto);
            while (controller.getVezDeCima() == 1) {System.out.println();}
            // inicio regiao critica cima
            this.girarTrem( 30,"Direita",tremVerdeLadoOposto);
            this.andarTrem( 55,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            // fim regiao critica cima
            controller.setVezDeCima(1);
            this.andarTrem( 85,"Descer",tremVerdeLadoOposto);
            while (controller.getVezDeBaixo() == 1) {System.out.println();}
            //inicio regiao critica baixo
            this.girarTrem( 30, "Direita",tremVerdeLadoOposto);
            this.andarTrem(75,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            // fim regiao critica baixo
            controller.setVezDeBaixo(1);
            this.andarTrem(120,"Descer",tremVerdeLadoOposto);
          } // fim selecinou alternancia explicita
          else if (controller.selecionouSolucaoDePeterson()) {
            this.andarTrem( 80,"Descer",tremVerdeLadoOposto);
            controller.entrouNaRegiaoCriticaDeCima(0);
            // inicio regiao critica cima
            this.girarTrem( 30,"Direita",tremVerdeLadoOposto);
            this.andarTrem( 55,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            // fim regiao critica cima
            controller.saiuDaRegiaoCriticaDeCima(0);
            this.andarTrem( 85,"Descer",tremVerdeLadoOposto);
            controller.entrouNaRegiaoCriticaDeBaixo(0);
            //inicio regiao critica baixo
            this.girarTrem( 30, "Direita",tremVerdeLadoOposto);
            this.andarTrem(75,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            // fim regiao critica baixo
            controller.saiuDaRegiaoCriticaDeBaixo(0);
            this.andarTrem(120,"Descer",tremVerdeLadoOposto);
          }

          tremVerdeLadoOposto.setX(0.0);
          Platform.runLater(() -> tremVerdeLadoOposto.setX(0.0));
          tremVerdeLadoOposto.setY(0.0);
          Platform.runLater(() -> tremVerdeLadoOposto.setY(0.0));

        } // fim do else trem esta descendo
      } catch (InterruptedException ex) {
        break;
      }
    } // fim while
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
