package model;

public class AlternanciaExplicita extends Comunicacao {

  private int vez = 0;
  
  @Override
  public void entrouRegiaCritica(int id) {
    while (vez != id) {}
    vez = id;
  }

  @Override
  public void saiuRegiaoCritica(int id) {
    vez++;
  }

}
