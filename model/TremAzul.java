package model;
/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/08/2023
* Ultima alteracao.: 07/10/2023
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
  
  private final Slider aceleradorAzul;
  
  private final PlataformaController controller;
  
  private double velocidadeTrem;
  
  private boolean pausarThread = false;
  
  // construtor da classe
  public TremAzul (PlataformaController controller) {
    this.controller = controller;
    this.aceleradorAzul = controller.getAceleradorAzul();
    this.tremAzul = controller.getTremAzul();
    this.tremAzulLadoOposto = controller.getTremAzulLadoOposto();

  }
  
  public void verificar() {
    synchronized (this) {
      while (getPausarThread()) {
        try {
          wait();
        } catch (InterruptedException ex) {
          Logger.getLogger(TremAzul.class.getName()).log(Level.SEVERE, null, ex);
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
    Platform.runLater(() -> tremAzul.setX(0.0));
    Platform.runLater(() -> tremAzul.setY(0.0));
    Platform.runLater(() -> tremAzulLadoOposto.setX(0.0));
    Platform.runLater(() -> tremAzulLadoOposto.setY(0.0));
    aceleradorAzul.setValue(0);
  }
  
  public void girarTrem (int posicao,String lado, ImageView trem) throws InterruptedException {
    double x = trem.getX();
    double y = trem.getY();
    
    if (lado.equals("Direita")) {
      for (int c = 0; c < posicao; c++) {
        verificar();
        x++;
        if(!controller.tremAzulSubindo())
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
        if(!controller.tremAzulSubindo())
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
        if (controller.tremAzulSubindo() && controller.isStart() ) { 

          if (controller.selecionouVariavelDeTravamento()) {
            this.andarTrem( 90,"Subir", tremAzul);
            while (controller.getVariavelDeTravamentoDeBaixo() == 1) {System.out.println();}
            controller.setVariavelDeTravamentoDeBaixo(1);
            // inicio regiao critica embaixo
            this.girarTrem( 33,"Esquerda", tremAzul);
            this.andarTrem( 55,"Subir",tremAzul);
            this.girarTrem(30,"Direita",tremAzul);
            // fim regiao critica embaixo
            controller.setVariavelDeTravamentoDeBaixo(0);
            this.andarTrem( 95,"Subir",tremAzul);
            while (controller.getVariavelDeTravamentoDeCima() == 1) {System.out.println();}
            controller.setVariavelDeTravamentoDeCima(1);
            //inicio regiao critica cima
            this.girarTrem( 30, "Esquerda",tremAzul);
            this.andarTrem(60,"Subir",tremAzul);
            this.girarTrem(33,"Direita",tremAzul);
            // fim regiao critica cima
            controller.setVariavelDeTravamentoDeCima(0);
            this.andarTrem(100,"Subir",tremAzul);
          } // fim selecionou variavel de tratamento
          
          else if(controller.selecionouAlternanciaExplicita()) {
            
            this.andarTrem( 90,"Subir", tremAzul);
            while (controller.getVezDeBaixo() == 0) {System.out.println();}
            // inicio regiao critica embaixo
            this.girarTrem( 33,"Esquerda", tremAzul);
            this.andarTrem( 55,"Subir",tremAzul);
            this.girarTrem(30,"Direita",tremAzul);
            // fim regiao critica embaixo
            controller.setVezDeBaixo(0);
            this.andarTrem( 95,"Subir",tremAzul);
            
            while (controller.getVezDeCima() == 0) {System.out.println();}
            //inicio regiao critica cima
            this.girarTrem( 30, "Esquerda",tremAzul);
            this.andarTrem(60,"Subir",tremAzul);
            this.girarTrem(33,"Direita",tremAzul);
            // fim regiao critica cima
            controller.setVezDeCima(0);
            this.andarTrem(100,"Subir",tremAzul);
            
          } // fim alternancia explicita selecionado
          
          else if (controller.selecionouSolucaoDePeterson()) {
            System.out.println("Entrou aqui na solucao de Peterson Azul");
            this.andarTrem( 90,"Subir", tremAzul);
            controller.entrouNaRegiaoCriticaDeBaixo(1);
            // inicio regiao critica embaixo
            this.girarTrem( 33,"Esquerda", tremAzul);
            this.andarTrem( 55,"Subir",tremAzul);
            this.girarTrem(30,"Direita",tremAzul);
            // fim regiao critica embaixo
            controller.saiuDaRegiaoCriticaDeBaixo(1);
            
            this.andarTrem( 95,"Subir",tremAzul);
            
            controller.entrouNaRegiaoCriticaDeCima(1);
            //inicio regiao critica cima
            this.girarTrem( 30, "Esquerda",tremAzul);
            this.andarTrem(60,"Subir",tremAzul);
            this.girarTrem(33,"Direita",tremAzul);
            // fim regiao critica cima
            controller.saiuDaRegiaoCriticaDeCima(1);
            this.andarTrem(100,"Subir",tremAzul);
            
          } // fim selecionou SolucaoDePeterson


          tremAzul.setX(0.0);
          Platform.runLater(() -> tremAzul.setX(0.0));
          tremAzul.setY(0.0);
          Platform.runLater(() -> tremAzul.setY(0.0));

        } else if(controller.isStart()) { // o trem esta descendo

          if (controller.selecionouVariavelDeTravamento()) {
            this.andarTrem( 80,"Descer",tremAzulLadoOposto);
            while (controller.getVariavelDeTravamentoDeCima() == 1) {System.out.println();}
            controller.setVariavelDeTravamentoDeCima(1);
            // inicio regiao critica cima
            this.girarTrem( 30,"Esquerda",tremAzulLadoOposto);
            this.andarTrem( 55,"Descer",tremAzulLadoOposto);
            this.girarTrem(30,"Direita",tremAzulLadoOposto);
            // fim regiao critica cima
            controller.setVariavelDeTravamentoDeCima(0);
            this.andarTrem( 85,"Descer",tremAzulLadoOposto);
            while (controller.getVariavelDeTravamentoDeBaixo() == 1) {System.out.println();}
            controller.setVariavelDeTravamentoDeBaixo(1);
            //inicio regiao critica baixo
            this.girarTrem( 30, "Esquerda",tremAzulLadoOposto);
            this.andarTrem(75,"Descer",tremAzulLadoOposto);
            this.girarTrem(30,"Direita",tremAzulLadoOposto);
            // fim regiao critica baixo
            controller.setVariavelDeTravamentoDeBaixo(0);
            this.andarTrem(120,"Descer",tremAzulLadoOposto);
          } // fim selecionou VariavelDeTravamento
          
          else if(controller.selecionouAlternanciaExplicita()) {
            this.andarTrem( 80,"Descer",tremAzulLadoOposto);
            while (controller.getVezDeCima() == 0) {System.out.println();}
            // inicio regiao critica cima
            this.girarTrem( 30,"Esquerda",tremAzulLadoOposto);
            this.andarTrem( 55,"Descer",tremAzulLadoOposto);
            this.girarTrem(30,"Direita",tremAzulLadoOposto);
            // fim regiao critica cima
            controller.setVezDeCima(0);
            this.andarTrem( 85,"Descer",tremAzulLadoOposto);
            while (controller.getVezDeBaixo() == 0) {System.out.println();}
            //inicio regiao critica baixo
            this.girarTrem( 30, "Esquerda",tremAzulLadoOposto);
            this.andarTrem(75,"Descer",tremAzulLadoOposto);
            this.girarTrem(30,"Direita",tremAzulLadoOposto);
            // fim regiao critica baixo
            controller.setVezDeBaixo(0);
            this.andarTrem(120,"Descer",tremAzulLadoOposto);
          } // fim selecinou alternancia explicita
          
          else if (controller.selecionouSolucaoDePeterson()) {
            this.andarTrem( 80,"Descer",tremAzulLadoOposto);
            controller.entrouNaRegiaoCriticaDeCima(1);
            // inicio regiao critica cima
            this.girarTrem( 30,"Esquerda",tremAzulLadoOposto);
            this.andarTrem( 55,"Descer",tremAzulLadoOposto);
            this.girarTrem(30,"Direita",tremAzulLadoOposto);
            // fim regiao critica cima
            controller.saiuDaRegiaoCriticaDeCima(1);
            this.andarTrem( 85,"Descer",tremAzulLadoOposto);
            controller.entrouNaRegiaoCriticaDeBaixo(1);
            //inicio regiao critica baixo
            this.girarTrem( 30, "Esquerda",tremAzulLadoOposto);
            this.andarTrem(75,"Descer",tremAzulLadoOposto);
            this.girarTrem(30,"Direita",tremAzulLadoOposto);
            // fim regiao critica baixo
            controller.saiuDaRegiaoCriticaDeBaixo(1);
            this.andarTrem(120,"Descer",tremAzulLadoOposto);
          } // fim selecionou solucao de Peterson

          tremAzulLadoOposto.setX(0.0);
          Platform.runLater(() -> tremAzulLadoOposto.setX(0.0));
          tremAzulLadoOposto.setY(0.0);
          Platform.runLater(() -> tremAzulLadoOposto.setY(0.0));

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
