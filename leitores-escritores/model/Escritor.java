/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 06/11/2023
* Ultima alteracao.: 12/11/2023
* Nome.............: Escritor
* Funcao...........: Escrever o dado, obter o dado, e alterar a velocidade de ambas acoes
*************************************************************** */

package model;
import controller.TelaController;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Escritor extends Thread {
  private int numeroEscritor;
  private double velocidadeObter = 5000; // comeca com 5s
  private double velocidadeEscrever = 5000; // comeca com 5s
  private TelaController telaUsuario;
  
  // construtor padrao
  public Escritor (int numero, TelaController telaUsuario) {
    this.numeroEscritor = numero;
    this.telaUsuario = telaUsuario;
  }
  
  /**********************************************************************************************
  Metodo: obterDado
  Funcao: Colocar a thread pra dormir ate o contador ser maior ou igual a velocidadeObter
  Parametros: void
  Retorno: void
  *********************************************************************************************** */
  public void obterDado() {
    int contador = 0;
    try {
      while (contador < velocidadeObter) { // velocidadeObter varia de 1000 a 5000 (1s a 5s)
        Thread.sleep(1000); // 1000 equivale a 1s
        contador += 1000;
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  /**********************************************************************************************
  Metodo: escreverDado
  Funcao: Colocar a thread pra dormir ate o contador ser maior ou igual a velocidadeEscrever
  Parametros: void
  Retorno: void
  *********************************************************************************************** */
  public void escreverDado() {
    int contador = 0;
    try {
      while (contador < velocidadeEscrever) { // velocidadeObter varia de 1000 a 5000 (1s a 5s)
        Thread.sleep(1000); // 1000 equivale a 1s
        contador += 1000;
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
  }
  
  public void setVelocidadeObter (int velocidade) {
    this.velocidadeObter = velocidade;
  }
  
  public void setVelocidadeEscrever (int velocidade) {
    this.velocidadeEscrever = velocidade;
  }
  
  @Override
  public void run () {
    while (true) { // repetir infinitamente
      try {
        obterDado(); // regiao nao critica
        telaUsuario.interesseEscreverDado(numeroEscritor);  // sinaliza o interesse de escrever dado a TelaController
        escreverDado(); // regiao critica
        telaUsuario.escreveuDado(numeroEscritor); // sinaliza que ja escreveu o dado a TelaController
      } catch (InterruptedException ex) {
        Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
      }
    } // fim while
  } // fim run  
} // fim Escritor
