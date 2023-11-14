/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 06/11/2023
* Ultima alteracao.: 12/11/2023
* Nome.............: TelaController
* Funcao...........: Controlar a interface grafica e tratar as regioes criticas
*************************************************************** */

package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.*;

public class TelaController implements Initializable {

  @FXML
  private Button buttonReiniciar;
  @FXML
  private Button buttonContinuar;
  
  private int controleTutorial; // controlar as imagem dos tutorial
  
  @FXML
  private ImageView escritor01Escrevendo;
  @FXML
  private ImageView escritor01Obtendo;
  @FXML
  private ImageView escritor02Escrevendo;
  @FXML
  private ImageView escritor02Obtendo;
  @FXML
  private ImageView escritor03Escrevendo;
  @FXML
  private ImageView escritor03Obtendo;
  @FXML
  private ImageView escritor04Escrevendo;
  @FXML
  private ImageView escritor04Obtendo;
  @FXML
  private ImageView escritor05Escrevendo;
  @FXML
  private ImageView escritor05Obtendo;
  
  @FXML
  private ImageView leitor01Lendo;
  @FXML
  private ImageView leitor01Utilizando;
  @FXML
  private ImageView leitor02Lendo;
  @FXML
  private ImageView leitor02Utilizando;
  @FXML
  private ImageView leitor03Lendo;
  @FXML
  private ImageView leitor03Utilizando;
  @FXML
  private ImageView leitor04Lendo;
  @FXML
  private ImageView leitor04Utilizando;
  @FXML
  private ImageView leitor05Lendo;
  @FXML
  private ImageView leitor05Utilizando;
  
  @FXML
  private Slider sliderEscritor01Escrevendo;
  @FXML
  private Slider sliderEscritor01Obtendo;
  @FXML
  private Slider sliderEscritor02Escrevendo;
  @FXML
  private Slider sliderEscritor02Obtendo;
  @FXML
  private Slider sliderEscritor03Escrevendo;
  @FXML
  private Slider sliderEscritor03Obtendo;
  @FXML
  private Slider sliderEscritor04Escrevendo;
  @FXML
  private Slider sliderEscritor04Obtendo;
  @FXML
  private Slider sliderEscritor05Escrevendo;
  @FXML
  private Slider sliderEscritor05Obtendo;
  @FXML
  private Slider sliderLeitor01Lendo;
  @FXML
  private Slider sliderLeitor01Utilizando;
  @FXML
  private Slider sliderLeitor02Lendo;
  @FXML
  private Slider sliderLeitor02Utilizando;
  @FXML
  private Slider sliderLeitor03Lendo;
  @FXML
  private Slider sliderLeitor03Utilizando;
  @FXML
  private Slider sliderLeitor04Lendo;
  @FXML
  private Slider sliderLeitor04Utilizando;
  @FXML
  private Slider sliderLeitor05Lendo;
  @FXML
  private Slider sliderLeitor05Utilizando;
  
  @FXML
  private Text textEscritor01;
  @FXML
  private Text textEscritor02;
  @FXML
  private Text textEscritor03;
  @FXML
  private Text textEscritor04;
  @FXML
  private Text textEscritor05;
  @FXML
  private Text textLeitor01;
  @FXML
  private Text textLeitor02;
  @FXML
  private Text textLeitor03;
  @FXML
  private Text textLeitor04;
  @FXML
  private Text textLeitor05;
  
  @FXML
  private Button buttonPausarEscritor01;
  @FXML
  private Button buttonPausarEscritor02;
  @FXML
  private Button buttonPausarEscritor03;
  @FXML
  private Button buttonPausarEscritor04;
  @FXML
  private Button buttonPausarEscritor05;
  @FXML
  private Button buttonPausarLeitor01;
  @FXML
  private Button buttonPausarLeitor02;
  @FXML
  private Button buttonPausarLeitor03;
  @FXML
  private Button buttonPausarLeitor04;
  @FXML
  private Button buttonPausarLeitor05;
  
  @FXML
  private ImageView tutorialEscritor;
  @FXML
  private ImageView tutorialLeitor;
  
  private Semaphore mutex = new Semaphore(1); // controla o acesso a RC
  private Semaphore db = new Semaphore(1); // controla o acesso ao banco de dados
  private int rc = 0; // numero de processos lendo ou querendo ler
  
  private Leitor leitor [] = new Leitor [5]; // 5 leitores do tipo Leitor
  private Escritor escritor[] = new Escritor [5]; // 5 escritor do Tipo Escritor
  private String estadoEscritor[] = new String [5]; // salvar o estado do Escritor quando pausado
  private String estadoLeitor[] = new String [5]; // salvar o estado do Leitor quando pausado
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // tutorial so aparece quando inicia a animacao pela primeira vez
    tutorialEscritor.setVisible(true);
    tutorialLeitor.setVisible(true);
    controleTutorial = 0; // 0 pra aparecer o primeiro tutorial
    
    for (int c = 0; c < 5; c++) { // criação dos leitores e escritores
      leitor[c] = new Leitor(c+1,this); // this eh a classe TelaController
      alterarEstadoLeitor(c+1,1); // todos leitores iniciam com 1 (Lendo)
      leitor[c].start(); // inicia
      
      escritor[c] = new Escritor(c+1,this); // this eh a classe TelaController
      alterarEstadoEscritor(c+1,1); // 1 equivale a Obtendo
      escritor[c].start();
    }
    
    // modificar a velocidade dos escritores e leitores quando o slider for alterado
    sliderEscritor01Escrevendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        escritor[0].setVelocidadeEscrever((int) (sliderEscritor01Escrevendo.getValue()*(-1000)+6000)); // equacao da reta (5 do slider dara 1000 e 1 do slider dara 1000)
      }
    });
    
    sliderEscritor01Obtendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        escritor[0].setVelocidadeObter((int) (sliderEscritor01Obtendo.getValue()*(-1000)+6000));
      }
    });

    sliderEscritor02Escrevendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        escritor[1].setVelocidadeEscrever((int) (sliderEscritor02Escrevendo.getValue()*(-1000)+6000));
      }
    });
    
    sliderEscritor02Obtendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        escritor[1].setVelocidadeObter((int) (sliderEscritor02Obtendo.getValue()*(-1000)+6000));
      }
    });    

    sliderEscritor03Escrevendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        escritor[2].setVelocidadeEscrever((int) (sliderEscritor03Escrevendo.getValue()*(-1000)+6000));
      }
    });
    
    sliderEscritor03Obtendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        escritor[2].setVelocidadeObter((int) (sliderEscritor03Obtendo.getValue()*(-1000)+6000));
      }
    });       
 
    sliderEscritor04Escrevendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        escritor[3].setVelocidadeEscrever((int) (sliderEscritor04Escrevendo.getValue()*(-1000)+6000));
      }
    });
    
    sliderEscritor04Obtendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        escritor[3].setVelocidadeObter((int) (sliderEscritor04Obtendo.getValue()*(-1000)+6000));
      }
    });          

    sliderEscritor05Escrevendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        escritor[4].setVelocidadeEscrever((int) (sliderEscritor05Escrevendo.getValue()*(-1000)+6000));
      }
    });
    
    sliderEscritor05Obtendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        escritor[4].setVelocidadeObter((int) (sliderEscritor05Obtendo.getValue()*(-1000)+6000));
      }
    });       
    
     sliderLeitor01Lendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        leitor[0].setVelocidadeLer((int) (sliderLeitor01Lendo.getValue()*(-1000)+6000));
      }
    });    
     
    sliderLeitor01Utilizando.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        leitor[0].setVelocidadeUtilizar((int) (sliderLeitor01Utilizando.getValue()*(-1000)+6000));
      }
    });  
    
    sliderLeitor02Lendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        leitor[1].setVelocidadeLer((int) (sliderLeitor02Lendo.getValue()*(-1000)+6000));
      }
    });    
     
    sliderLeitor02Utilizando.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        leitor[1].setVelocidadeUtilizar((int) (sliderLeitor02Utilizando.getValue()*(-1000)+6000));
      }
    });     
    
    sliderLeitor03Lendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        leitor[2].setVelocidadeLer((int) (sliderLeitor03Lendo.getValue()*(-1000)+6000));
      }
    });    
     
    sliderLeitor03Utilizando.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        leitor[2].setVelocidadeUtilizar((int) (sliderLeitor03Utilizando.getValue()*(-1000)+6000));
      }
    });     
    
    sliderLeitor04Lendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        leitor[3].setVelocidadeLer((int) (sliderLeitor04Lendo.getValue()*(-1000)+6000));
      }
    });    
     
    sliderLeitor04Utilizando.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        leitor[3].setVelocidadeUtilizar((int) (sliderLeitor04Utilizando.getValue()*(-1000)+6000));
      }
    }); 
    
    sliderLeitor05Lendo.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        leitor[4].setVelocidadeLer((int) (sliderLeitor05Lendo.getValue()*(-1000)+6000));
      }
    });    
     
    sliderLeitor05Utilizando.valueProperty().addListener(new ChangeListener<Number> () {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        leitor[4].setVelocidadeUtilizar((int) (sliderLeitor05Utilizando.getValue()*(-1000)+6000));
      }
    }); 
  } // fim initializable
  
  /**********************************************************************************************
  Metodo: clicouEmReiniciar
  Funcao: Reiniciar todas as threads, e os valores dos sliders
  Parametros: Acao de clique
  Retorno: void
  *********************************************************************************************** */
  @FXML
  public void clicouEmReiniciar(ActionEvent event) {
    mutex = new Semaphore (1); // // instancia novamente reiniciando
    db = new Semaphore (1); // instancia novamente reiniciando
    rc = 0; // numero de processos lendo ou querendo ler
    
    for (int c = 0; c < 5; c++) { // criação dos leitores e escritores
      alterarEstadoLeitor(c+1,1); // 1 equivale ao estado de Lendo
      leitor[c].stop();
      leitor[c] = new Leitor(c+1,this);
      leitor[c].start();
      
      alterarEstadoEscritor(c+1,1); // 1 equivale ao estado de Obtendo
      escritor[c].stop();
      escritor[c] = new Escritor(c+1,this);
      escritor[c].start();
    }
    // volta ao valor inicial dos sliders
    sliderEscritor01Escrevendo.setValue(1);
    sliderEscritor01Obtendo.setValue(1);
    sliderEscritor02Escrevendo.setValue(1);
    sliderEscritor02Obtendo.setValue(1);
    sliderEscritor03Escrevendo.setValue(1);
    sliderEscritor03Obtendo.setValue(1);
    sliderEscritor04Escrevendo.setValue(1);
    sliderEscritor04Obtendo.setValue(1);
    sliderEscritor05Escrevendo.setValue(1);
    sliderEscritor05Obtendo.setValue(1);
   
    sliderLeitor01Lendo.setValue(2);
    sliderLeitor01Utilizando.setValue(2);
    sliderLeitor02Lendo.setValue(2);
    sliderLeitor02Utilizando.setValue(2);
    sliderLeitor03Lendo.setValue(2);
    sliderLeitor03Utilizando.setValue(2);
    sliderLeitor04Lendo.setValue(2);
    sliderLeitor04Utilizando.setValue(2);
    sliderLeitor05Lendo.setValue(2);
    sliderLeitor05Utilizando.setValue(2);
    
    // configura o botao do pausar, para caso algum esteja pausado
    buttonPausarEscritor01.setText("Pausar");
    buttonPausarEscritor02.setText("Pausar");
    buttonPausarEscritor03.setText("Pausar");
    buttonPausarEscritor04.setText("Pausar");
    buttonPausarEscritor05.setText("Pausar");
    buttonPausarEscritor01.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    buttonPausarEscritor02.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    buttonPausarEscritor03.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    buttonPausarEscritor04.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    buttonPausarEscritor05.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    
    buttonPausarLeitor01.setText("Pausar");
    buttonPausarLeitor02.setText("Pausar");
    buttonPausarLeitor03.setText("Pausar");
    buttonPausarLeitor04.setText("Pausar");
    buttonPausarLeitor05.setText("Pausar");
    buttonPausarLeitor01.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px"); 
    buttonPausarLeitor02.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px"); 
    buttonPausarLeitor03.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px"); 
    buttonPausarLeitor04.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px"); 
    buttonPausarLeitor05.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px"); 
    
  } // fim clicouEmReiniciar
  
  /**********************************************************************************************
  Metodos: clicouEmPausarEscritorXX
  Funcao: Os proximos metodos tem como objetivo suspender a execucao da Thread, e retomar caso ja tenha sido pausado
  Parametros: Acao de clique
  Retorno: void
  *********************************************************************************************** */
  @FXML
  void clicouEmPausarEscritor01(ActionEvent event) {
    if (buttonPausarEscritor01.getText().equals("Pausar")) {
      escritor[0].suspend();
      estadoEscritor[0] = textEscritor01.getText(); // guardar o text do estado onde foi pausado
      buttonPausarEscritor01.setText("Retomar");
      textEscritor01.setText("Pausado");
      buttonPausarEscritor01.setStyle("-fx-background-color: #e7db15; -fx-background-radius: 20px"); // configurar o botao
    }
    else {
      escritor[0].resume();
      textEscritor01.setText(estadoEscritor[0]); // escrever o estado de onde foi pausado
      buttonPausarEscritor01.setText("Pausar");
      buttonPausarEscritor01.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px"); // configurar o botao
    }
  }

  @FXML
  void clicouEmPausarEscritor02(ActionEvent event) {
    if (buttonPausarEscritor02.getText().equals("Pausar")) {
      escritor[1].suspend();
      buttonPausarEscritor02.setText("Retomar");
      estadoEscritor[1] = textEscritor02.getText();
      textEscritor02.setText("Pausado");
      buttonPausarEscritor02.setStyle("-fx-background-color: #e7db15; -fx-background-radius: 20px");
    }
    else {
      escritor[1].resume();
      textEscritor02.setText(estadoEscritor[1]);
      buttonPausarEscritor02.setText("Pausar");
      buttonPausarEscritor02.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    }
  }

  @FXML
  void clicouEmPausarEscritor03(ActionEvent event) {
    if (buttonPausarEscritor03.getText().equals("Pausar")) {
      escritor[2].suspend();
      buttonPausarEscritor03.setText("Retomar");
      estadoEscritor[2] = textEscritor03.getText();
      textEscritor03.setText("Pausado");
      buttonPausarEscritor03.setStyle("-fx-background-color: #e7db15; -fx-background-radius: 20px");
    }
    else {
      escritor[2].resume();
      textEscritor03.setText(estadoEscritor[2]);
      buttonPausarEscritor03.setText("Pausar");
      buttonPausarEscritor03.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    }
  }

  @FXML
  void clicouEmPausarEscritor04(ActionEvent event) {
    if (buttonPausarEscritor04.getText().equals("Pausar")) {
      escritor[3].suspend();
      buttonPausarEscritor04.setText("Retomar");
      estadoEscritor[3] = textEscritor04.getText();
      textEscritor04.setText("Pausado");
      buttonPausarEscritor04.setStyle("-fx-background-color: #e7db15; -fx-background-radius: 20px");
    }
    else {
      escritor[3].resume();
      textEscritor04.setText(estadoEscritor[3]);
      buttonPausarEscritor04.setText("Pausar");
      buttonPausarEscritor04.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    }
  }

  @FXML
  void clicouEmPausarEscritor05(ActionEvent event) {
    if (buttonPausarEscritor05.getText().equals("Pausar")) {
      escritor[4].suspend();
      buttonPausarEscritor05.setText("Retomar");
      estadoEscritor[4] = textEscritor05.getText();
      textEscritor05.setText("Pausado");
      buttonPausarEscritor05.setStyle("-fx-background-color: #e7db15; -fx-background-radius: 20px");
    }
    else {
      escritor[4].resume();
      textEscritor05.setText(estadoEscritor[4]);
      buttonPausarEscritor05.setText("Pausar");
      buttonPausarEscritor05.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    }
  }

  /**********************************************************************************************
  Metodos: clicouEmPausarLeitorXX
  Funcao: Os proximos metodos tem como objetivo suspender a execucao da Thread, e retomar caso ja tenha sido pausado
  Parametros: Acao de clique
  Retorno: void
  *********************************************************************************************** */
  @FXML
  void clicouEmPausarLeitor01(ActionEvent event) {
    if (buttonPausarLeitor01.getText().equals("Pausar")) {
      leitor[0].suspend();
      buttonPausarLeitor01.setText("Retomar");
      estadoLeitor[0] = textLeitor01.getText();  // guardar o text do estado onde foi pausado
      textLeitor01.setText("Pausado");
      buttonPausarLeitor01.setStyle("-fx-background-color: #e7db15; -fx-background-radius: 20px"); // configurar o botao
    }
    else {
      leitor[0].resume();
      textLeitor01.setText(estadoLeitor[0]); // escrever o estado de onde foi pausado
      buttonPausarLeitor01.setText("Pausar");
      buttonPausarLeitor01.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px"); // configurar o botao
    }
  }

  @FXML
  void clicouEmPausarLeitor02(ActionEvent event) {
    if (buttonPausarLeitor02.getText().equals("Pausar")) {
      leitor[1].suspend();
      buttonPausarLeitor02.setText("Retomar");
      estadoLeitor[1] = textLeitor02.getText();
      textLeitor02.setText("Pausado");
      buttonPausarLeitor02.setStyle("-fx-background-color: #e7db15; -fx-background-radius: 20px");
    }
    else {
      leitor[1].resume();
      textLeitor02.setText(estadoLeitor[1]);
      buttonPausarLeitor02.setText("Pausar");
      buttonPausarLeitor02.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    }
  }

  @FXML
  void clicouEmPausarLeitor03(ActionEvent event) {
    if (buttonPausarLeitor03.getText().equals("Pausar")) {
      leitor[2].suspend();
      buttonPausarLeitor03.setText("Retomar");
      estadoLeitor[2] = textLeitor03.getText();
      textLeitor03.setText("Pausado");
      buttonPausarLeitor03.setStyle("-fx-background-color: #e7db15; -fx-background-radius: 20px");
    }
    else {
      leitor[2].resume();
      textLeitor03.setText(estadoLeitor[2]);
      buttonPausarLeitor03.setText("Pausar");
      buttonPausarLeitor03.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    }
  }

  @FXML
  void clicouEmPausarLeitor04(ActionEvent event) {
    if (buttonPausarLeitor04.getText().equals("Pausar")) {
      leitor[3].suspend();
      buttonPausarLeitor04.setText("Retomar");
      estadoLeitor[3] = textLeitor04.getText();
      textLeitor04.setText("Pausado");
      buttonPausarLeitor04.setStyle("-fx-background-color: #e7db15; -fx-background-radius: 20px");
    }
    else {
      leitor[3].resume();
      textLeitor04.setText(estadoLeitor[3]);
      buttonPausarLeitor04.setText("Pausar");
      buttonPausarLeitor04.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    }
  }

  @FXML
  void clicouEmPausarLeitor05(ActionEvent event) {
    if (buttonPausarLeitor05.getText().equals("Pausar")) {
      leitor[4].suspend();
      buttonPausarLeitor05.setText("Retomar");
      estadoLeitor[4] = textLeitor05.getText();
      textLeitor05.setText("Pausado");
      buttonPausarLeitor05.setStyle("-fx-background-color: #e7db15; -fx-background-radius: 20px");
    }
    else {
      leitor[4].resume();
      textLeitor05.setText(estadoLeitor[4]);
      buttonPausarLeitor05.setText("Pausar");
      buttonPausarLeitor05.setStyle("-fx-background-color: #FFF; -fx-background-radius: 20px");
    }
  }
  
  /**********************************************************************************************
  Metodo: interesseLerDado
  Funcao: Bloquear possiveis acessos de leitores so mesmo tempo, e bloquear escritores caso haja um leitor lendo
  Parametros: inteiro
  Retorno: void
  *********************************************************************************************** */
  public void interesseLerDado(int numeroLeitor) throws InterruptedException {
    mutex.acquire(); // equivale ao down
    rc += 1; // um leitor a mais
    if (rc == 1) { // se ha leitor
      db.acquire(); // bloqueia o acesso de possiveis escritores ao banco de dados.
    }
    alterarEstadoLeitor(numeroLeitor,1); // atualizar GUI - essa ordem faz DIFERENCA! 1 equivale a lendo
    mutex.release(); // equivale ao up
  }
  
  /**********************************************************************************************
  Metodo: terminouLerDado
  Funcao: Bloquear possiveis acessos de leitores so mesmo tempo, remove um leitor do contador e libera os escritores se nao ha mais leitores
  Parametros: inteiro
  Retorno: void
  *********************************************************************************************** */
  public void terminouLerDado(int numeroLeitor) throws InterruptedException {
    mutex.acquire(); // equivale ao down
    alterarEstadoLeitor(numeroLeitor,2);
    rc -= 1; // menor um leitor
    if (rc == 0) { // se nao ha leitores
      db.release(); // incrementa 1 e verifica se ha escritores bloqueados
    }
    mutex.release(); // equivale ao up
  }
  
  /**********************************************************************************************
  Metodo: interesseEscreverDado
  Funcao: Bloqueia o escritor caso haja leitores, se nao bloqueado, ou desbloqueado altera o estado
  Parametros: inteiro
  Retorno: void
  *********************************************************************************************** */
  public void interesseEscreverDado (int numeroEscritor) throws InterruptedException {
    db.acquire();
    this.alterarEstadoEscritor(numeroEscritor,2); // 2 equivale a escrevendo
  }
  
  /**********************************************************************************************
  Metodo: escreveuDado
  Funcao: Incrementa o semaphoro e verifica se ha escritores bloqueados
  Parametros: inteiro
  Retorno: void
  *********************************************************************************************** */
  public void escreveuDado (int numeroEscritor) {
    db.release();
    this.alterarEstadoEscritor(numeroEscritor,1); // equivale a obtendo
  }
  
 /**********************************************************************************************
  Metodo: alterarEstadoLeitor
  Funcao: Altera o estado do devido leitor escolhido para lendo ou utilizando, isso eh, desabilita ou habilita a visibilidade da imagem
  Parametros: inteiro, inteiro
  Retorno: void
  *********************************************************************************************** */
  public void alterarEstadoLeitor(int id,int estado) {
  switch (id) {
      case 1: // leitor 01
        switch (estado) {
          case 1: // Lendo
            textLeitor01.setText("Lendo");
            leitor01Lendo.setVisible(true);
            leitor01Utilizando.setVisible(false);
            break;
          case 2: // utilizando
            textLeitor01.setText("Utilizando");
            leitor01Lendo.setVisible(false);
            leitor01Utilizando.setVisible(true);
            break;
        }
        break;
      case 2: // leitor 02
        switch (estado) {
          case 1: // lendo
            textLeitor02.setText("Lendo");
            leitor02Lendo.setVisible(true);
            leitor02Utilizando.setVisible(false);
            break;
          case 2: // utilizando
            textLeitor02.setText("Utilizando");
            leitor02Lendo.setVisible(false);
            leitor02Utilizando.setVisible(true);
            break;
        }
        break;
      case 3: // leitor 3
        switch (estado) {
          case 1: // lendo
            textLeitor03.setText("Lendo");
            leitor03Lendo.setVisible(true);
            leitor03Utilizando.setVisible(false);
            break;
          case 2: // utilizando
            textLeitor03.setText("Utilizando");
            leitor03Lendo.setVisible(false);
            leitor03Utilizando.setVisible(true);
            break;
        }
        break;
      case 4: // leitor 4
        switch (estado) {
          case 1: // lendo
            textLeitor04.setText("Lendo");
            leitor04Lendo.setVisible(true);
            leitor04Utilizando.setVisible(false);
            break;
          case 2: // utilizando
            textLeitor04.setText("Utilizando");
            leitor04Lendo.setVisible(false);
            leitor04Utilizando.setVisible(true);
            break;
        }
        break;
      case 5: // leitor 5
        switch (estado) {
          case 1: // lendo
            textLeitor05.setText("Lendo");
            leitor05Lendo.setVisible(true);
            leitor05Utilizando.setVisible(false);
            break;
          case 2: // utilizando
            textLeitor05.setText("Utilizando");
            leitor05Lendo.setVisible(false);
            leitor05Utilizando.setVisible(true);
            break;
        }
        break;    
    }
  } // fim alterarEstadoLeitor
  
  /**********************************************************************************************
  Metodo: alterarEstadoEscritor
  Funcao: Altera o estado do devido escritor escolhido para obtendo ou escrevendo, isso eh, desabilita ou habilita a visibilidade da imagem
  Parametros: inteiro, inteiro
  Retorno: void
  *********************************************************************************************** */
  public void alterarEstadoEscritor (int id, int estado) {
    switch (id) {
      case 1: // escritor 01
        switch (estado) {
          case 1: // obtendo
            textEscritor01.setText("Obtendo");
            escritor01Escrevendo.setVisible(false);
            escritor01Obtendo.setVisible(true);
            break;
          case 2: // escrevendo
            textEscritor01.setText("Escrevendo");
            escritor01Escrevendo.setVisible(true);
            escritor01Obtendo.setVisible(false);
            break;
        }
        break;
      case 2: // escritor 02
        switch (estado) {
          case 1: // obtendo
            textEscritor02.setText("Obtendo");
            escritor02Escrevendo.setVisible(false);
            escritor02Obtendo.setVisible(true);
            break;
          case 2: // escrevendo
            textEscritor02.setText("Escrevendo");
            escritor02Escrevendo.setVisible(true);
            escritor02Obtendo.setVisible(false);
            break;
        }
        break;
      case 3: // escritor 03
        switch (estado) {
          case 1: // obtendo
            textEscritor03.setText("Obtendo");
            escritor03Escrevendo.setVisible(false);
            escritor03Obtendo.setVisible(true);
            break;
          case 2: // escrevendo
            textEscritor03.setText("Escrevendo");
            escritor03Escrevendo.setVisible(true);
            escritor03Obtendo.setVisible(false);
            break;
        }
        break;
      case 4: // escritor 04
        switch (estado) {
          case 1: // obtendo
            textEscritor04.setText("Obtendo");
            escritor04Escrevendo.setVisible(false);
            escritor04Obtendo.setVisible(true);
            break;
          case 2: // escrevendo
            textEscritor04.setText("Escrevendo");
            escritor04Escrevendo.setVisible(true);
            escritor04Obtendo.setVisible(false);
            break;
        }
        break;
      case 5: // escritor 05
        switch (estado) {
          case 1: // obtendo
            textEscritor05.setText("Obtendo");
            escritor05Escrevendo.setVisible(false);
            escritor05Obtendo.setVisible(true);
            break;
          case 2: // escrevendo
            textEscritor05.setText("Escrevendo");
            escritor05Escrevendo.setVisible(true);
            escritor05Obtendo.setVisible(false);
            break;
        }
        break;
    }   
  } // fim alterarEstadoEscritor
  
  /**********************************************************************************************
  Metodo: clicouEmContinuar
  Funcao: Desabilitar a imagem do tutorial
  Parametros: Evento de acao
  Retorno: void
  *********************************************************************************************** */
  @FXML
  public void clicouEmContinuar(ActionEvent event) {
    if (controleTutorial == 0) { // primeiro tutorial
      tutorialEscritor.setVisible(false);
      controleTutorial++;
    }
    else { // segundo tutorial
      tutorialLeitor.setVisible(false);
      buttonContinuar.setVisible(false); // esconde o butao de continuar
    }
  }
} // fim TelaController
