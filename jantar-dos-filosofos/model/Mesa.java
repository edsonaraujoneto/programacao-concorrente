package model;
/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/10/2023
* Ultima alteracao.: 28/10/2023
* Nome.............: Mesa
* Funcao...........: Tratar as possiveis regioes criticas e atualizar na GUI
*************************************************************** */

import controller.TelaController;
import java.util.concurrent.Semaphore;

public class Mesa {
  private final static int PENSANDO = 0;
  private final static int FOME = 1;
  private final static int COMENDO = 2;
  
  private Semaphore mutex = new Semaphore(1);
  private Semaphore garfos[] = new Semaphore[5];
  private int estados[] = new int [5];
  
  private TelaController telaUsuario;

  // construtor padrao
  public Mesa (TelaController telaUsuario) {
    this.telaUsuario = telaUsuario;
    
    for (int c = 0; c < 5; c++) {
      telaUsuario.alterarEstadoFilosofo(c, PENSANDO); // atualiza na GUI que todos estao pensando
      estados[c] = PENSANDO; // estados de todos pensando inicialmente
      garfos[c] = new Semaphore(0);
    }
  }
  
  /**********************************************************************************************
  Metodos: pegarGarfo
  Funcao: Verifica atraves do semaforo mutex se alguem esta tentando pegar algum garfo, se nao, muda seu estado para fome e tenta pegar o garfo atraves do submetodo
  Parametros: inteiro numeroFilosofo
  Retorno: void
  *********************************************************************************************** */
  public void pegarGarfo(int numeroFilosofo) throws InterruptedException {
    mutex.acquire();
    estados[numeroFilosofo] = FOME;
    testarGarfo(numeroFilosofo); // verifica se eh possivel pegar o garfo
    mutex.release();
    garfos[numeroFilosofo].acquire(); // trava se nao for possivel pegar o garfo (se for igual a 0)
  }
  
  /**********************************************************************************************
  Metodos: testarGarfo
  Funcao: Pega o garfo se nenhum dos filosofos ao lado estiver comendo e aguarda caso esteja com fome e os filosos ao lado estao comendo
  Parametros: inteiro numeroFilosofo
  Retorno: void
  *********************************************************************************************** */
  public void testarGarfo(int numeroFilosofo) throws InterruptedException {
    if (estados[numeroFilosofo] == FOME && estados[(numeroFilosofo - 1 + 5) % 5] != COMENDO && estados[(numeroFilosofo + 1) % 5 ] != COMENDO) {
      estados[numeroFilosofo] = COMENDO;
      telaUsuario.pegarGarfoDaMesa(numeroFilosofo); // atualiza na GUI
      telaUsuario.alterarEstadoFilosofo(numeroFilosofo, COMENDO); // atualiza a indicacao
      garfos[numeroFilosofo].release(); // incremente 1
    } else {
      if(estados[numeroFilosofo] == FOME) // se estiver com fome
        telaUsuario.alterarEstadoFilosofo(numeroFilosofo, 3); // indica que ele esta aguardando
    }
  }
  
  /**********************************************************************************************
  Metodos: devolverGarfo
  Funcao: Devolve o garfo na mesa e testa se alguem esta com fome e deseja comer
  Parametros: int numeroFilosofo
  Retorno: void
  *********************************************************************************************** */
  public void devolverGarfo(int numeroFilosofo) throws InterruptedException {
    mutex.acquire();
    estados[numeroFilosofo] = PENSANDO;
    telaUsuario.colocarGarfoNaMesa(numeroFilosofo); // atualiza na GUI
    testarGarfo((numeroFilosofo - 1 + 5) % 5); // verifica o interesse do filosofo da esquerda
    testarGarfo((numeroFilosofo + 1) % 5); // verifica o interesse do filosofo da direita
    telaUsuario.alterarEstadoFilosofo(numeroFilosofo, PENSANDO);
    mutex.release();

  }

  // retorna o estado de acordo com o filosofo escolhido (id)
  public int getEstados(int id) {
    return estados[id];
  }
 
}