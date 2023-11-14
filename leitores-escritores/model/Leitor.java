/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 06/11/2023
* Ultima alteracao.: 12/11/2023
* Nome.............: Leitor
* Funcao...........: Ler o dado, usar o dado, e alterar a velocidade de ambas acoes
*************************************************************** */

package model;
import controller.TelaController;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Leitor extends Thread {
  private int numeroLeitor;
  private double velocidadeLer = 4000; // comeca com 4s
  private double velocidadeUtilizar = 4000; // comeca com 4s
  private TelaController telaUsuario;
  
  // construtor padrao
  public Leitor (int numero, TelaController telaUsuario) {
    this.numeroLeitor = numero;
    this.telaUsuario = telaUsuario;
  }
  
  /**********************************************************************************************
  Metodo: lerDado
  Funcao: Colocar a thread pra dormir ate o contador ser maior ou igual a velocidadeLer
  Parametros: void
  Retorno: void
  *********************************************************************************************** */
  public void lerDado() {
    int contador = 0;
    try {
      while (contador < velocidadeLer) { // velocidadeLer varia de 1000 a 5000 (1s a 5s)
        Thread.sleep(1000); // 1000 equivale a 1s
        contador += 1000;
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  /**********************************************************************************************
  Metodo: utilizarDado
  Funcao: Colocar a thread pra dormir ate o contador ser maior ou igual a velocidadeUtilizar
  Parametros: void
  Retorno: void
  *********************************************************************************************** */
  public void utilizarDado() {
    int contador = 0;
    try {
      while (contador < velocidadeUtilizar) { // velocidadeUtilizar varia de 1000 a 5000 (1s a 5s)
        Thread.sleep(1000); // 1000 equivale a 1s
        contador += 1000;
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
  }
  
  public void setVelocidadeLer (int velocidade) {
    this.velocidadeLer = velocidade;
  }
  
  public void setVelocidadeUtilizar (int velocidade) {
    this.velocidadeUtilizar = velocidade;
  }
  
  @Override
  public void run ()  {
    while (true) { // repetir infinitamente
      try {
        telaUsuario.interesseLerDado(numeroLeitor); // sinaliza o interesse de ler dado a TelaController
        lerDado(); // regiao critica
        telaUsuario.terminouLerDado(numeroLeitor); // sinaliza que ja leu o dado a TelaController
        utilizarDado(); // regiao nao critica
      }
      catch (InterruptedException ex) {
        Logger.getLogger(Leitor.class.getName()).log(Level.SEVERE, null, ex);
      }
    } // fim while
  } // fim run
} // fim leitor
