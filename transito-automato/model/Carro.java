/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 20/11/2023
* Ultima alteracao.: 03/12/2023
* Nome.............: Carro
* Funcao...........: Fazer o carro andar na direcao X ou Y, mudar a velocidade do carro.
*************************************************************** */

package model;

import controller.TelaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class Carro extends Thread {
  private final int percursoDoCarro;
  private double velocidadeCarro = 7; // 3 max | 7 min 
  private final TelaController telaUsuario;
  
  // construtor padrao
  public Carro (int percursoDoCarro, TelaController telaUsuario) {
    this.percursoDoCarro = percursoDoCarro; // percurso do carro é a quadra onde ele anda
    this.telaUsuario = telaUsuario;
  }
  
  public void setVelocidadeCarro (double velocidadeCarro) {
    this.velocidadeCarro = velocidadeCarro;
  }
  
  /**********************************************************************************************
  Metodo: andarCarroVertical
  Funcao:Fazer o carro andar pra cima ou pra baixo de acordo com a quantidade desejada
  Parametros: int, String, ImageView
  Retorno: void
  *********************************************************************************************** */
  public void andarCarroVertical (int quantidadeY, String direcao, ImageView carro) {
    double y = carro.getY(); // pega a posicao atual do carro
    if (direcao.equals("Cima")) {
      while (y != quantidadeY) {
        y--;
        final double finalY = y;
        //atualizar a interface do usuario
        Platform.runLater(() -> {
          carro.setY(finalY);
        });

        try {
          Thread.sleep((int) velocidadeCarro); // mudar a velocidade do carro
        } catch (InterruptedException e) {
          break;      
        }
      }
    }
    else if (direcao.equals("Baixo")) {
      while (y != quantidadeY) {
        y++;
        final double finalY = y;
        //atualizar a interface do usuario
        Platform.runLater(() -> {
          carro.setY(finalY);
        });

        try {
          Thread.sleep((int) velocidadeCarro); // mudar a velocidade do carro
        } catch (InterruptedException e) {
          break;      
        }
      }
    }
  }
  /**********************************************************************************************
  Metodo: girarImagemCarro
  Funcao: Girar a imagem do carro escolhido de acordo com o valor da rotacao
  Parametros: int, ImageView
  Retorno: void
  *********************************************************************************************** */
  public void girarImagemCarro (int rotacao, ImageView carro) {
    Platform.runLater(() -> {
      carro.setRotate(rotacao);
    });
  }
  
  /**********************************************************************************************
  Metodo: andarCarroHorizontal
  Funcao: Fazer o carro andar pra direita ou pra esquerda de acordo com a quantidade desejada
  Parametros: int, String, ImageView
  Retorno: void
  *********************************************************************************************** */
  public void andarCarroHorizontal (int quantidadeX, String direcao, ImageView carro) {
    double x = carro.getX();
    if (direcao.equals("Direita")) {
      while (x != quantidadeX) {
        x++;
        final double finalX = x;
        //atualizar a interface do usuario
        Platform.runLater(() -> {
          carro.setX(finalX);
        });

        try {
          Thread.sleep((int) velocidadeCarro); // mudar a velocidade do trem
        } catch (InterruptedException e) {
          break;      
        }
      }
    }

    else if (direcao.equals("Esquerda")) {
      while (x != quantidadeX) {
        x--;
        final double finalX = x;
        //atualizar a interface do usuario
        Platform.runLater(() -> {
          carro.setX(finalX);
        });

        try {
          Thread.sleep((int) velocidadeCarro); // mudar a velocidade do trem
        } catch (InterruptedException e) {
          break;      
        }
      }
    }
    
  }
  
  /**********************************************************************************************
  Metodo: reiniciarCoordenadas
  Funcao: Atualizar na GUI a posicao inicial do carro.
  Parametros: ImageView
  Retorno: void
  *********************************************************************************************** */
  public void reiniciarCoordenadas (ImageView carro) {
    Platform.runLater(() -> {
      carro.setY(0);
      carro.setX(0);
      carro.setRotate(0);
  });
  }
  
  @Override
  public void run() {
    while (true) {
      try {
        telaUsuario.carroAndar(percursoDoCarro);
      } catch (InterruptedException ex) {
        Logger.getLogger(Carro.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
