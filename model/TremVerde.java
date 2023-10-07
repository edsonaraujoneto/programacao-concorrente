package model;
/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/08/2023
* Ultima alteracao.: 07/10/2023
* Nome.............: Thread trem Verde
* Funcao...........: Iniciar a thread de acordo com a direcao escolhida, variar a velocidade ao mudar o slider e verificar o tratamento escolhido de colisão
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
  } // fim do construtor da classe
  
  /*********************************************************************
  * Metodo: verificar
  * Funcao: verifica se a velocidade é igual a zero para colocar thread no estado de espera
  * Parametro: void
  * Retorno: void
  ******************************************************************* */
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
  } // fim verificar
  
  /*********************************************************************
  * Metodo: setDirecao
  * Funcao: chama a funcao resetar e configurar a direcao do trem
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
  * Funcao: esconde todos os trens, e coloca eles na posicao inicial
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
  } // fim resetar
  
  /*********************************************************************
  * Metodo: girarTrem
  * Funcao: movimentar a posicao x e y do trem para entrar no trilho unico
  * Parametro: int posicao, string lado, ImageView trem
  * Retorno: void
  ******************************************************************* */
  public void girarTrem (int posicao,String lado, ImageView trem) throws InterruptedException {
    double x = trem.getX();
    double y = trem.getY();
    
    if (lado.equals("Direita")) { //ir para direita
      for (int c = 0; c < posicao; c++) {
        verificar(); // verifica se foi pausado
        x++;
        if(!controller.tremVerdeSubindo())
          y++; // trem esta descendo
        else
          y--; // trem esta subindo
        final double finalX = x;
        final double finalY = y;

        //atualizar a interface do usuario
        Platform.runLater(() -> {
          trem.setX(finalX);
          trem.setY(finalY);
        });

        try {
          Thread.sleep((long) velocidadeTrem); // mudar a velocidade do trem
        } catch (InterruptedException e) {
          break;
        }
      }
    }// fim if ir para Direita
    
    else if (lado.equals("Esquerda")){ // ir para esquerda
        for (int c = 0; c < posicao; c++) {
        verificar(); // verifica se foi pausado
        x--;
        if(!controller.tremVerdeSubindo())
          y++; // trem esta descendo
        else
          y--; // trem esta subindo
        final double finalX = x;
        final double finalY = y;

        // atualizar a interface do usuario
        Platform.runLater(() -> {
          trem.setX(finalX);
          trem.setY(finalY);
        });

        try {
          Thread.sleep((long) velocidadeTrem); // mudar a velocidade do trem
        } catch (InterruptedException e) {
          break;
        }
      }    
    } // fim if ir para Esquerda
  } // fim girarTrem
  
  /*********************************************************************
  * Metodo: andarTrem
  * Funcao: movimentar a posicao y do trem 
  * Parametro: int posicao, string direcao, ImageView trem
  * Retorno: void
  ******************************************************************* */
  public void andarTrem (int posicaoY, String direcao, ImageView trem) throws InterruptedException {
    if (direcao.equals("Subir") ) {
      double y = trem.getY();
      for (int c = 0; c < posicaoY; c++) {
        verificar(); // verifica se foi pausado
        y--;
        final double finalY = y;
        
        // atualizar a interface do usuario
        Platform.runLater(() -> {
          trem.setY(finalY);
        });

        try {
          Thread.sleep((long) velocidadeTrem); // mudar a velocidade o trem
        } catch (InterruptedException e) {
          break;
        }
      }
    } // fim if subir
    
    else if (direcao.equals("Descer") ){
       double y = trem.getY();
      for (int c = 0; c < posicaoY; c++) {
        verificar(); // verificar se não foi pausado o trem
        y++;
        final double finalY = y;
        
        // atualizar a interface do usuario
        Platform.runLater(() -> {
          trem.setY(finalY);
        });

        try {
          Thread.sleep((long) velocidadeTrem); // mudar a velocidade do trem
        } catch (InterruptedException e) {
          break;
        }
      }// fim for     
    } // fim if descer
  }// fim classe subir
  
  /*********************************************************************
  * Metodo: run
  * Funcao: chama as funcoes que movimenta o trem, alem de verificar o tipo de tratamento após ser chamado
  * Parametro: int posicao, string lado, ImageView trem
  * Retorno: void
  ******************************************************************* */
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
            controller.acenderLuzVermelhaBaixo(); // sinalizar na interface gráfica
            this.girarTrem( 30,"Direita", tremVerde);
            this.andarTrem( 55,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            controller.apagarLuzVermelhaBaixo(); // sinalizar na interface gráfica
            // fim regiao critica embaixo
            controller.setVariavelDeTravamentoDeBaixo(0);
            this.andarTrem( 95,"Subir",tremVerde);
            while (controller.getVariavelDeTravamentoDeCima() == 1) {System.out.println();}
            controller.setVariavelDeTravamentoDeCima(1);
            //inicio regiao critica cima
            controller.acenderLuzVermelhaCima(); // sinalizar na interface gráfica
            this.girarTrem( 30, "Direita",tremVerde);
            this.andarTrem(60,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            controller.apagarLuzVermelhaCima(); // sinalizar na interface gráfica
            // fim regiao critica cima
            controller.setVariavelDeTravamentoDeCima(0);
            this.andarTrem(100,"Subir",tremVerde);
          } // fim selecionou variavel de tratamento
          
          else if(controller.selecionouAlternanciaExplicita()) {
            
            this.andarTrem( 90,"Subir", tremVerde);
            while (controller.getVezDeBaixo() == 1) {System.out.println();}
            // inicio regiao critica embaixo
            controller.acenderLuzVermelhaBaixo(); // sinalizar na interface gráfica
            this.girarTrem( 30,"Direita", tremVerde);
            this.andarTrem( 55,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            controller.apagarLuzVermelhaBaixo(); // sinalizar na interface gráfica
            // fim regiao critica embaixo
            controller.setVezDeBaixo(1);
            this.andarTrem( 95,"Subir",tremVerde);
            
            while (controller.getVezDeCima() == 1) {System.out.println();}
            //inicio regiao critica cima
            controller.acenderLuzVermelhaCima(); // sinalizar na interface gráfica
            this.girarTrem( 30, "Direita",tremVerde);
            this.andarTrem(60,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            controller.apagarLuzVermelhaCima(); // sinalizar na interface gráfica
            // fim regiao critica cima
            controller.setVezDeCima(1);
            this.andarTrem(100,"Subir",tremVerde);
            
          } // fim alternancia explicita selecionado
          else if(controller.selecionouSolucaoDePeterson()) {
            this.andarTrem( 90,"Subir", tremVerde);
            controller.entrouNaRegiaoCriticaDeBaixo(0);
            // inicio regiao critica embaixo
            controller.acenderLuzVermelhaBaixo(); // sinalizar na interface gráfica
            this.girarTrem( 30,"Direita", tremVerde);
            this.andarTrem( 55,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            controller.apagarLuzVermelhaBaixo(); // sinalizar na interface gráfica
            // fim regiao critica embaixo
            controller.saiuDaRegiaoCriticaDeBaixo(0);
            this.andarTrem( 95,"Subir",tremVerde);
            
            controller.entrouNaRegiaoCriticaDeCima(0);
            //inicio regiao critica cima
            controller.acenderLuzVermelhaCima(); // sinalizar na interface gráfica
            this.girarTrem( 30, "Direita",tremVerde);
            this.andarTrem(60,"Subir",tremVerde);
            this.girarTrem(30,"Esquerda",tremVerde);
            controller.apagarLuzVermelhaCima(); // sinalizar na interface gráfica
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
            controller.acenderLuzVermelhaCima(); // sinalizar na interface gráfica
            this.girarTrem( 30,"Direita",tremVerdeLadoOposto);
            this.andarTrem( 55,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            controller.apagarLuzVermelhaCima(); // sinalizar na interface gráfica
            // fim regiao critica cima
            controller.setVariavelDeTravamentoDeCima(0);
            this.andarTrem( 85,"Descer",tremVerdeLadoOposto);
            while (controller.getVariavelDeTravamentoDeBaixo() == 1) {System.out.println();}
            controller.setVariavelDeTravamentoDeBaixo(1);
            //inicio regiao critica baixo
            controller.acenderLuzVermelhaBaixo(); // sinalizar na interface gráfica
            this.girarTrem( 30, "Direita",tremVerdeLadoOposto);
            this.andarTrem(75,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            controller.apagarLuzVermelhaBaixo(); // sinalizar na interface gráfica
            // fim regiao critica baixo
            controller.setVariavelDeTravamentoDeBaixo(0);
            this.andarTrem(120,"Descer",tremVerdeLadoOposto);
          } // fim selecionou VariavelDeTravamento
          
          else if(controller.selecionouAlternanciaExplicita()) {
            this.andarTrem( 80,"Descer",tremVerdeLadoOposto);
            while (controller.getVezDeCima() == 1) {System.out.println();}
            // inicio regiao critica cima
            controller.acenderLuzVermelhaCima(); // sinalizar na interface gráfica
            this.girarTrem( 30,"Direita",tremVerdeLadoOposto);
            this.andarTrem( 55,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            controller.apagarLuzVermelhaCima(); // sinalizar na interface gráfica
            // fim regiao critica cima
            controller.setVezDeCima(1);
            this.andarTrem( 85,"Descer",tremVerdeLadoOposto);
            while (controller.getVezDeBaixo() == 1) {System.out.println();}
            //inicio regiao critica baixo
            controller.acenderLuzVermelhaBaixo(); // sinalizar na interface gráfica
            this.girarTrem( 30, "Direita",tremVerdeLadoOposto);
            this.andarTrem(75,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            controller.apagarLuzVermelhaBaixo(); // sinalizar na interface gráfica
            // fim regiao critica baixo
            controller.setVezDeBaixo(1);
            this.andarTrem(120,"Descer",tremVerdeLadoOposto);
          } // fim selecinou alternancia explicita
          else if (controller.selecionouSolucaoDePeterson()) {
            this.andarTrem( 80,"Descer",tremVerdeLadoOposto);
            controller.entrouNaRegiaoCriticaDeCima(0);
            // inicio regiao critica cima
            controller.acenderLuzVermelhaCima(); // sinalizar na interface gráfica
            this.girarTrem( 30,"Direita",tremVerdeLadoOposto);
            this.andarTrem( 55,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            controller.apagarLuzVermelhaCima(); // sinalizar na interface gráfica
            // fim regiao critica cima
            controller.saiuDaRegiaoCriticaDeCima(0);
            this.andarTrem( 85,"Descer",tremVerdeLadoOposto);
            controller.entrouNaRegiaoCriticaDeBaixo(0);
            //inicio regiao critica baixo
            controller.acenderLuzVermelhaBaixo(); // sinalizar na interface gráfica
            this.girarTrem( 30, "Direita",tremVerdeLadoOposto);
            this.andarTrem(75,"Descer",tremVerdeLadoOposto);
            this.girarTrem(30,"Esquerda",tremVerdeLadoOposto);
            controller.apagarLuzVermelhaBaixo(); // sinalizar na interface gráfica
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
