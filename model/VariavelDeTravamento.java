package model;

public class VariavelDeTravamento extends Comunicacao{
  private int vT = 0;
  
  @Override
  public void entrouRegiaCritica(int id) {
    while (vT == 1) {}
    vT = 1;
  }

  @Override
  public void saiuRegiaoCritica(int id) {
    vT = 0;
  }


}
