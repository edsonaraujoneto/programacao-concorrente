package controller;

/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 17/10/2023
* Ultima alteracao.: 28/10/2023
* Nome.............: Controle de interface grafica do usuario
* Funcao...........: Manipular as imagens, e os metodos de acordo com a acao do usuario.
*************************************************************** */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import model.Filosofo;
import model.Mesa;

public class TelaController implements Initializable {
  
  @FXML   // buttoes de pausar individualmente cada filosofo
  private Button buttonPausarFilosofo01;
  @FXML
  private Button buttonPausarFilosofo02;
  @FXML
  private Button buttonPausarFilosofo03;
  @FXML
  private Button buttonPausarFilosofo04;
  @FXML
  private Button buttonPausarFilosofo05;
  
  @FXML  // button de reiniciar todo o codigo
  private Button buttonReiniciar;
  
  @FXML   // garfos na mesa
  private ImageView garfo01;
  @FXML
  private ImageView garfo02;
  @FXML
  private ImageView garfo03;
  @FXML
  private ImageView garfo04;
  @FXML
  private ImageView garfo05;
  
  @FXML   // garfos nas maos dos filosofos
  private ImageView garfo1e2;
  @FXML
  private ImageView garfo1e5;
  @FXML
  private ImageView garfo3e2;
  @FXML
  private ImageView garfo4e3;
  @FXML
  private ImageView garfo5e4;
  
  @FXML // controle de velocidade de comer de cada filosofo
  private Slider sliderComerFilosofo01;
  @FXML
  private Slider sliderComerFilosofo02;
  @FXML
  private Slider sliderComerFilosofo03;
  @FXML
  private Slider sliderComerFilosofo04;
  @FXML
  private Slider sliderComerFilosofo05;
  
  
  @FXML // controle de velocidade de pensar de cada filosofo
  private Slider sliderPensarFilosofo01;
  @FXML
  private Slider sliderPensarFilosofo02;
  @FXML
  private Slider sliderPensarFilosofo03;
  @FXML
  private Slider sliderPensarFilosofo04;
  @FXML
  private Slider sliderPensarFilosofo05;
  
  @FXML // imagens de indicacao
  private ImageView Filosofo01Aguardando;
  @FXML
  private ImageView Filosofo01Comendo;
  @FXML
  private ImageView Filosofo01Pensando;
  @FXML
  private ImageView Filosofo01Pausado;
  @FXML
  private ImageView Filosofo02Aguardando;
  @FXML
  private ImageView Filosofo02Comendo;
  @FXML
  private ImageView Filosofo02Pensando;
  @FXML
  private ImageView Filosofo02Pausado;
  @FXML
  private ImageView Filosofo03Aguardando;
  @FXML
  private ImageView Filosofo03Comendo;
  @FXML
  private ImageView Filosofo03Pensando;
  @FXML
  private ImageView Filosofo03Pausado;
  @FXML
  private ImageView Filosofo04Aguardando;
  @FXML
  private ImageView Filosofo04Comendo;
  @FXML
  private ImageView Filosofo04Pensando;
  @FXML
  private ImageView Filosofo04Pausado;
  @FXML
  private ImageView Filosofo05Aguardando;
  @FXML
  private ImageView Filosofo05Comendo;
  @FXML
  private ImageView Filosofo05Pensando;
  @FXML
  private ImageView Filosofo05Pausado;
  
  // instanciando as threads dos filosofos
  private Filosofo filosofo01;
  private Filosofo filosofo02;
  private Filosofo filosofo03;
  private Filosofo filosofo04;
  private Filosofo filosofo05;
  
  // instanciando a classe mesa
  private Mesa mesa;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // Maximo e minimo permitido de cada slider de pensar. (direita para esquerda)
    sliderPensarFilosofo01.setMax(10);
    sliderPensarFilosofo01.setMin(1);
    sliderPensarFilosofo01.setValue(10);
    sliderPensarFilosofo02.setMax(10);
    sliderPensarFilosofo02.setMin(1);
    sliderPensarFilosofo02.setValue(10);
    sliderPensarFilosofo03.setMax(10);
    sliderPensarFilosofo03.setMin(1);
    sliderPensarFilosofo03.setValue(10);
    sliderPensarFilosofo04.setMax(10);
    sliderPensarFilosofo04.setMin(1);
    sliderPensarFilosofo04.setValue(10);
    sliderPensarFilosofo05.setMax(10);
    sliderPensarFilosofo05.setMin(1);
    sliderPensarFilosofo05.setValue(10);
    
    // Maximo e minimo permitido de cada slider de comer. (direita para esquerda)
    sliderComerFilosofo01.setMax(10);
    sliderComerFilosofo01.setMin(1);
    sliderComerFilosofo01.setValue(10);
    sliderComerFilosofo02.setMax(10);
    sliderComerFilosofo02.setMin(1);
    sliderComerFilosofo02.setValue(10);
    sliderComerFilosofo03.setMax(10);
    sliderComerFilosofo03.setMin(1);
    sliderComerFilosofo03.setValue(10);
    sliderComerFilosofo04.setMax(10);
    sliderComerFilosofo04.setMin(1);
    sliderComerFilosofo04.setValue(10);
    sliderComerFilosofo05.setMax(10);
    sliderComerFilosofo05.setMin(1);
    sliderComerFilosofo05.setValue(10);
    
    mesa = new Mesa(this); // this eh a propria classe telaController no qual todos os filosofos vao ter acesso atraves da mesa

    filosofo01 = new Filosofo(0,mesa);
    filosofo02 = new Filosofo(1,mesa);
    filosofo03 = new Filosofo(2,mesa);
    filosofo04 = new Filosofo(3,mesa);
    filosofo05 = new Filosofo(4,mesa);
    
    filosofo01.start();
    filosofo02.start();
    filosofo03.start();
    filosofo04.start();
    filosofo05.start();
    
    // Listener para atualizar em tempo real a velocidade caso houver mudanca (para todos os filosofos em suas acoes de comer e pensar)
    sliderPensarFilosofo01.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo01.setVelocidadePensar((int) sliderPensarFilosofo01.getValue() * 1000);
      }
    });
    sliderComerFilosofo01.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo01.setVelocidadeComer((int) sliderComerFilosofo01.getValue() * 1000);
      }
    });
    sliderPensarFilosofo02.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo02.setVelocidadePensar((int) sliderPensarFilosofo02.getValue() * 1000);
      }
    });
    sliderComerFilosofo02.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo02.setVelocidadeComer((int) sliderComerFilosofo02.getValue() * 1000);
      }
    });
    sliderPensarFilosofo03.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo03.setVelocidadePensar((int) sliderPensarFilosofo03.getValue() * 1000);
      }
    });
    sliderComerFilosofo03.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo03.setVelocidadeComer((int) sliderComerFilosofo03.getValue() * 1000);
      }
    });
    sliderPensarFilosofo04.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo04.setVelocidadePensar((int) sliderPensarFilosofo04.getValue() * 1000);
      }
    });
    sliderComerFilosofo04.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo04.setVelocidadeComer((int) sliderComerFilosofo04.getValue() * 1000);
      }
    });
    sliderPensarFilosofo05.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo05.setVelocidadePensar((int) sliderPensarFilosofo05.getValue() * 1000);
      }
    });
    sliderComerFilosofo05.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        filosofo05.setVelocidadeComer((int) sliderComerFilosofo05.getValue() * 1000);
      }
    });
    
  } // fim da classe initialize
  
  /**********************************************************************************************
  Metodos: clicouEmPausarFilosofoXX
  Funcao: Os proximos metodos tem como objetivo suspender a execucao da Thread, e retornar caso ja tenha sido pausado
  Parametros: Acao de clique
  Retorno: void
  *********************************************************************************************** */
  @FXML
  public void clicouEmPausarFilosofo01(ActionEvent event) {
    if (buttonPausarFilosofo01.getText().equals("Pausar")) {
      this.alterarEstadoFilosofo(0, 4); // 4 eh o estado de pausado
      filosofo01.suspend();
      buttonPausarFilosofo01.setText("Retomar");
    } else { // ja esta pausado
      filosofo01.resume();
      this.alterarEstadoFilosofo(0, mesa.getEstados(0)); // retornara do estado de onde parou
      buttonPausarFilosofo01.setText("Pausar");
    }
  }// fim clicouEmPausarFilosofo01
  
  @FXML
  public void clicouEmPausarFilosofo02(ActionEvent event) {
    if (buttonPausarFilosofo02.getText().equals("Pausar")) {
      this.alterarEstadoFilosofo(1, 4);
      filosofo02.suspend();
      buttonPausarFilosofo02.setText("Retomar");
    } else {
      filosofo02.resume();
      this.alterarEstadoFilosofo(1, mesa.getEstados(1));
      buttonPausarFilosofo02.setText("Pausar");
    }
  } // fim clicouEmPausarFilosofo02
  
  @FXML
  public void clicouEmPausarFilosofo03(ActionEvent event) {
    if (buttonPausarFilosofo03.getText().equals("Pausar")) {
      this.alterarEstadoFilosofo(2, 4);
      filosofo03.suspend();
      buttonPausarFilosofo03.setText("Retomar");
    } else {
      filosofo03.resume();
      this.alterarEstadoFilosofo(2, mesa.getEstados(2));
      buttonPausarFilosofo03.setText("Pausar");
    }
  } // fim clicouEmPausarFilosofo03
  
  @FXML
  public void clicouEmPausarFilosofo04(ActionEvent event) {
    if (buttonPausarFilosofo04.getText().equals("Pausar")) {
      this.alterarEstadoFilosofo(3, 4);
      filosofo04.suspend();
      buttonPausarFilosofo04.setText("Retomar");
    } else {
      filosofo04.resume();
      this.alterarEstadoFilosofo(3, mesa.getEstados(3));
      buttonPausarFilosofo04.setText("Pausar");
    }
  }// fim clicouEmPausarFilosofo04
  
  @FXML
  public void clicouEmPausarFilosofo05(ActionEvent event) {
    if (buttonPausarFilosofo05.getText().equals("Pausar")) {
      this.alterarEstadoFilosofo(4, 4);
      filosofo05.suspend();
      buttonPausarFilosofo05.setText("Retomar");
    } else {
      filosofo05.resume();
      this.alterarEstadoFilosofo(4, mesa.getEstados(4));
      buttonPausarFilosofo05.setText("Pausar");
    }
  } // fim clicouEmPausarFilosofo05
  
  /**********************************************************************************************
  Metodo: clicouEmReiniciar
  Funcao: Da um stop na execucao das threads, instancia passando como parametro uma nova mesa e dar start na mesma
  Parametros: Acao de clique
  Retorno: void
  *********************************************************************************************** */
  @FXML
  public void clicouEmReiniciar(ActionEvent event) {
    
    mesa = new Mesa (this);
    
    filosofo01.stop();
    filosofo01 = new Filosofo(0,mesa);
    filosofo01.start();
    
    filosofo02.stop();
    filosofo02 = new Filosofo(1,mesa);
    filosofo02.start();
    
    filosofo03.stop();
    filosofo03 = new Filosofo(2,mesa);
    filosofo03.start();
    
    filosofo04.stop();
    filosofo04 = new Filosofo(3,mesa);
    filosofo04.start();
    
    filosofo05.stop();
    filosofo05 = new Filosofo(4,mesa);
    filosofo05.start();
    
    // Resetando os sliders para o valor inicial
    sliderPensarFilosofo01.setValue(10);
    sliderPensarFilosofo02.setValue(10);
    sliderPensarFilosofo03.setValue(10);
    sliderPensarFilosofo04.setValue(10);
    sliderPensarFilosofo05.setValue(10);
    sliderComerFilosofo01.setValue(10);
    sliderComerFilosofo02.setValue(10);
    sliderComerFilosofo03.setValue(10);
    sliderComerFilosofo04.setValue(10);
    sliderComerFilosofo05.setValue(10);
    
    // Resetando os buttoes de cada filosofo
    buttonPausarFilosofo01.setText("Pausar");
    buttonPausarFilosofo02.setText("Pausar");
    buttonPausarFilosofo03.setText("Pausar");
    buttonPausarFilosofo04.setText("Pausar");
    buttonPausarFilosofo05.setText("Pausar");
    
    for (int c = 0; c < 5; c++) { 
      colocarGarfoNaMesa(c); // coloca garfo na mesa caso alguem esteja com o garfo
      alterarEstadoFilosofo(c,0); // todos pensando
    } // fim for
  } // fim clicouEmReiniciar
  
  /**********************************************************************************************
  Metodo: alterarEstadoFilosofo
  Funcao: Muda o texto de indicacao acordo com o filosofo e o estado atual do mesmo
  Parametros: inteiro id, inteiro estado
  Retorno: void
  *********************************************************************************************** */
  public void alterarEstadoFilosofo(int id, int estado) {
    switch (id) {
      case 0: // filosofo 1
        switch (estado){
          case 0: // pensando
            Filosofo01Pensando.setVisible(true);
            Filosofo01Comendo.setVisible(false);
            Filosofo01Aguardando.setVisible(false);
            Filosofo01Pausado.setVisible(false);
            break;
          case 2: // comendo
            Filosofo01Pensando.setVisible(false);
            Filosofo01Comendo.setVisible(true);
            Filosofo01Aguardando.setVisible(false);
            Filosofo01Pausado.setVisible(false);
            break;
          case 3: // aguardando
            Filosofo01Pensando.setVisible(false);
            Filosofo01Comendo.setVisible(false);
            Filosofo01Aguardando.setVisible(true);
            Filosofo01Pausado.setVisible(false);
            break;
          case 4: // pausado
            Filosofo01Pensando.setVisible(false);
            Filosofo01Comendo.setVisible(false);
            Filosofo01Aguardando.setVisible(false);
            Filosofo01Pausado.setVisible(true);
            break;
        } // fim switch filosofo 1
        break;
      case 1: // filosofo 2
        switch (estado){
          case 0: // pensando
            Filosofo02Pensando.setVisible(true);
            Filosofo02Comendo.setVisible(false);
            Filosofo02Aguardando.setVisible(false);
            Filosofo02Pausado.setVisible(false);
            break;
          case 2: // comendo
            Filosofo02Pensando.setVisible(false);
            Filosofo02Comendo.setVisible(true);
            Filosofo02Aguardando.setVisible(false);
            Filosofo02Pausado.setVisible(false);
            break;
          case 3: // aguardando
            Filosofo02Pensando.setVisible(false);
            Filosofo02Comendo.setVisible(false);
            Filosofo02Aguardando.setVisible(true);
            Filosofo02Pausado.setVisible(false);
            break;
          case 4: // pausado
            Filosofo02Pensando.setVisible(false);
            Filosofo02Comendo.setVisible(false);
            Filosofo02Aguardando.setVisible(false);
            Filosofo02Pausado.setVisible(true);
            break;
        } // fim switch filosofo 2
        break;
      case 2: // filosofo 3
        switch (estado){
          case 0: // pensando
            Filosofo03Pensando.setVisible(true);
            Filosofo03Comendo.setVisible(false);
            Filosofo03Aguardando.setVisible(false);
            Filosofo03Pausado.setVisible(false);
            break;
          case 2: // comendo
            Filosofo03Pensando.setVisible(false);
            Filosofo03Comendo.setVisible(true);
            Filosofo03Aguardando.setVisible(false);
            Filosofo03Pausado.setVisible(false);
            break;
          case 3: // aguardando
            Filosofo03Pensando.setVisible(false);
            Filosofo03Comendo.setVisible(false);
            Filosofo03Aguardando.setVisible(true);
            Filosofo03Pausado.setVisible(false);
            break;
          case 4: // pausado
            Filosofo03Pensando.setVisible(false);
            Filosofo03Comendo.setVisible(false);
            Filosofo03Aguardando.setVisible(false);
            Filosofo03Pausado.setVisible(true);
            break;
        } // fim switch filosofo 3
        break;
      case 3: // filosofo 4
        switch (estado){
          case 0: // pensando
            Filosofo04Pensando.setVisible(true);
            Filosofo04Comendo.setVisible(false);
            Filosofo04Aguardando.setVisible(false);
            Filosofo04Pausado.setVisible(false);
            break;
          case 2: // comendo
            Filosofo04Pensando.setVisible(false);
            Filosofo04Comendo.setVisible(true);
            Filosofo04Aguardando.setVisible(false);
            Filosofo04Pausado.setVisible(false);
            break;
          case 3: // aguardando
            Filosofo04Pensando.setVisible(false);
            Filosofo04Comendo.setVisible(false);
            Filosofo04Aguardando.setVisible(true);
            Filosofo04Pausado.setVisible(false);
            break;
          case 4: // pausado
            Filosofo04Pensando.setVisible(false);
            Filosofo04Comendo.setVisible(false);
            Filosofo04Aguardando.setVisible(false);
            Filosofo04Pausado.setVisible(true);
            break;
        } // fim switch filosofo 4
        break;
      case 4: // filosofo 5
        switch (estado){
          case 0: // pensando
            Filosofo05Pensando.setVisible(true);
            Filosofo05Comendo.setVisible(false);
            Filosofo05Aguardando.setVisible(false);
            Filosofo05Pausado.setVisible(false);
            break;
          case 2: // comendo
            Filosofo05Pensando.setVisible(false);
            Filosofo05Comendo.setVisible(true);
            Filosofo05Aguardando.setVisible(false);
            Filosofo05Pausado.setVisible(false);
            break;
          case 3: // aguardando
            Filosofo05Pensando.setVisible(false);
            Filosofo05Comendo.setVisible(false);
            Filosofo05Aguardando.setVisible(true);
            Filosofo05Pausado.setVisible(false);
            break;
          case 4: // pausado
            Filosofo05Pensando.setVisible(false);
            Filosofo05Comendo.setVisible(false);
            Filosofo05Aguardando.setVisible(false);
            Filosofo05Pausado.setVisible(true);
            break;
        } // fim switch filosofo 5
        break;
    } // fim switch principal (filosofos)
  } // fim alterarEstadoFilosofo
  
  /**********************************************************************************************
  Metodo: colocarGarfoNaMesa
  Funcao: Desabilita a imagem no qual o filosofo tem a posse dos dois garfos e coloca na mesa cada um individualmente
  Parametros: inteiro id
  Retorno: void
  *********************************************************************************************** */
  public void colocarGarfoNaMesa(int id) {
    switch (id) {
      case 0: // filsoofo 1
        garfo1e2.setVisible(false);
        garfo01.setVisible(true);
        garfo02.setVisible(true);
        break;
      case 1: // filosofo 2
        garfo3e2.setVisible(false);
        garfo02.setVisible(true);
        garfo03.setVisible(true);
        break;
      case 2: // filosofo 3
        garfo4e3.setVisible(false);
        garfo03.setVisible(true);
        garfo04.setVisible(true);
        break;
      case 3: // filosofo 4
        garfo5e4.setVisible(false);
        garfo04.setVisible(true);
        garfo05.setVisible(true);
        break;
      case 4: // filosofo 5
        garfo1e5.setVisible(false);
        garfo05.setVisible(true);
        garfo01.setVisible(true);
        break;
    } // fim switch
  } // fim colocarGarfoNaMesa
  
  /**********************************************************************************************
  Metodo: pegarGarfoDaMesa
  Funcao: Desabilita a visibilidade dos garfos individuais do lado esquerdo e direito do filosofo e habilita a visibilidade do garfo em suas maos
  Parametros: inteiro id
  Retorno: void
  *********************************************************************************************** */
  public void pegarGarfoDaMesa(int id) {
     switch (id) {
      case 0: // filosofo 1
        garfo1e2.setVisible(true);
        garfo01.setVisible(false);
        garfo02.setVisible(false);
        break;
      case 1: // filosofo 2
        garfo3e2.setVisible(true);
        garfo02.setVisible(false);
        garfo03.setVisible(false);
        break;
      case 2: // filosofo 3
        garfo4e3.setVisible(true);
        garfo03.setVisible(false);
        garfo04.setVisible(false);
        break;
      case 3: // filosofo 4
        garfo5e4.setVisible(true);
        garfo04.setVisible(false);
        garfo05.setVisible(false);
        break;
      case 4: // filosofo 5
        garfo1e5.setVisible(true);
        garfo05.setVisible(false);
        garfo01.setVisible(false);
        break;
    } // fim switch  
  } // fim pegarGarfoDaMesa
  
} // Fim do PlataformaController
