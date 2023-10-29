package model;
/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/10/2023
* Ultima alteracao.: 28/10/2023
* Nome.............: Filosofo
* Funcao...........: Fazer as acoes de pensar e comer de acordo com a velocidade
*************************************************************** */

import java.util.logging.Level;
import java.util.logging.Logger;

public class Filosofo extends Thread {
  private int numeroFilosofo;
  private Mesa mesa;
  private double velocidadeComer = 10000; // velocidade padrao de 10s
  private double velocidadePensar = 10000; // velocidade padrao de 10s
  
  // construtor padrao no qual recebe o identificador do filosofo e a mesa(classe em comum)
  public Filosofo (int numero, Mesa mesa) {
    this.numeroFilosofo = numero;
    this.mesa = mesa;
  }
  
  /**********************************************************************************************
  Metodos: comer
  Funcao: Filosofo come atraves da thread em seu estado de sono. Minimo de 1000 (1s) maximo de 10000(10s)
  Parametros: void
  Retorno: void
  *********************************************************************************************** */
  public void comer() {
    int contador = 0;
    try { 
      while (contador < velocidadeComer) {
        Thread.sleep(1000);
        contador += 1000;
      }
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  /**********************************************************************************************
  Metodos: pensar
  Funcao: Filosofo pensa atraves da thread em seu estado de sono. Minimo de 1000 (1s) maximo de 10000(10s)
  Parametros: void
  Retorno: void
  *********************************************************************************************** */
  public void pensar() {
    int contador = 0;
    try {
      while (contador < velocidadePensar) {
        Thread.sleep(1000);
        contador += 1000;
      }
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  public void setVelocidadeComer(double velocidade) {
    this.velocidadeComer = velocidade;
  }
  
  public void setVelocidadePensar(double velocidade) {
    this.velocidadePensar = velocidade;
  }
  
  @Override
  public void run() {
    while (true) {
      try {
        pensar ();
        mesa.pegarGarfo(numeroFilosofo); // verifica se eh possivel pegarGarfo, se sim ele avanca
        comer();
        mesa.devolverGarfo(numeroFilosofo); 
      } catch (InterruptedException ex) {
        Logger.getLogger(Filosofo.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
