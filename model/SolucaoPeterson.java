package model;

public class SolucaoPeterson extends Comunicacao {
  private int vez;
  private boolean interesse[];
  
  public SolucaoPeterson (int processos) {
    interesse = new boolean [processos];
  }

  @Override
  public void entrouRegiaCritica(int id) {
    int outro = 1-id;
    interesse [id] = true;
    vez = id;
    while (vez != id && interesse[outro] == true);
  }

  @Override
  public void saiuRegiaoCritica(int id) {
    interesse[id] = false;
  }
}
