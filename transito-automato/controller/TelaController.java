/* ***************************************************************
* Autor............: Edson Araujo de Souza Neto
* Matricula........: 202210169
* Inicio...........: 20/11/2023
* Ultima alteracao.: 03/12/2023
* Nome.............: Principal
* Funcao...........: Controllar a GUI e os semaforos da regiao critica de cada carro e seu percurso
*************************************************************** */

package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import model.Carro;

public class TelaController implements Initializable {

    // imagens dos carros
    @FXML
    private ImageView carroP03;
    @FXML
    private ImageView carroP06;
    @FXML
    private ImageView carroP09;
    @FXML
    private ImageView carroP12;
    @FXML
    private ImageView carroP14;
    @FXML
    private ImageView carroP16;
    @FXML
    private ImageView carroP20;
    @FXML
    private ImageView carroP23;
    
    // imagens dos percursos
    @FXML
    private ImageView percurso03;
    @FXML
    private ImageView percurso06;
    @FXML
    private ImageView percurso09;
    @FXML
    private ImageView percurso12;
    @FXML
    private ImageView percurso14;
    @FXML
    private ImageView percurso16;
    @FXML
    private ImageView percurso20;
    @FXML
    private ImageView percurso23;

    // slider para velocidade
    @FXML
    private Slider sliderCarroP03;
    @FXML
    private Slider sliderCarroP06;
    @FXML
    private Slider sliderCarroP09;
    @FXML
    private Slider sliderCarroP12;
    @FXML
    private Slider sliderCarroP14;
    @FXML
    private Slider sliderCarroP16;
    @FXML
    private Slider sliderCarroP20;
    @FXML
    private Slider sliderCarroP23;
    
    // butoes para mostrar percurso
    @FXML
    private Button buttonMostrarP03;
    @FXML
    private Button buttonMostrarP06;
    @FXML
    private Button buttonMostrarP09;
    @FXML
    private Button buttonMostrarP12;
    @FXML
    private Button buttonMostrarP14;
    @FXML
    private Button buttonMostrarP16;
    @FXML
    private Button buttonMostrarP20;
    @FXML
    private Button buttonMostrarP23;
    
    // butoes para pausar cada carro
    @FXML
    private Button buttonPausarP03;
    @FXML
    private Button buttonPausarP06;
    @FXML
    private Button buttonPausarP09;
    @FXML
    private Button buttonPausarP12;
    @FXML
    private Button buttonPausarP14;
    @FXML
    private Button buttonPausarP16;
    @FXML
    private Button buttonPausarP20;
    @FXML
    private Button buttonPausarP23;
    
    // imagem do menu personalizado
    @FXML
    private ImageView menuImagem;
    
    // butao iniciar
    @FXML
    private Button buttonIniciar;

    // grupo dos controles dos carros (slider e butoes de cada um)
    @FXML
    private Group controleDosCarros;
    
    // threads de cada carro
    private Carro threadCarroP03;
    private Carro threadCarroP06;
    private Carro threadCarroP09;
    private Carro threadCarroP12;
    private Carro threadCarroP14;
    private Carro threadCarroP16;
    private Carro threadCarroP20;
    private Carro threadCarroP23;
    
    // semaforo de controle de acesso a regiao critica
    private Semaphore semaforoCarroP03eCarroP06RuaZaRuaC; // carroP03 e carro P06
    private Semaphore semaforoCarroP03eCarroP06Rua26aRuaE; // carroP03 e carro P06
    private Semaphore semaforoCarroP03eCarroP06Rua14aRua13; // carroP03 e carro P06
    private Semaphore semaforoCarroP03eCarroP06Rua18aRuaX; // carroP03 e carro P06
    private Semaphore semaforoCarroP03eCarroP06Rua22; // carroP03 e carro P06
    private Semaphore semaforoCarroP03eCarroP06Rua28aRuaDD; // carroP03 e carro P06

    private Semaphore semaforoCarroP03eCarroP09RuaDRuaC; // carroP03 e carro P09
    private Semaphore semaforoCarroP03eCarroP09Rua13Rua14; // carroP03 e carro P09
    private Semaphore semaforoCarroP03eCarroP09Rua24; // carroP03 e carro P09
    private Semaphore semaforoCarroP03eCarroP09Rua22; // carroP03 e carro P09

    private Semaphore semaforoCarroP03eCarroP12Rua1Rua12; // carroP03 e carro P12
    private Semaphore semaforoCarroP03eCarroP12RuaR; // carroP03 e carro P12
    private Semaphore semaforoCarroP03eCarroP12RuaT; // carroP03 e carro P12
    private Semaphore semaforoCarroP03eCarroP12Rua26; // carroP03 e carro P12

    private Semaphore semaforoCarroP03eCarroP16Rua2aRuaAA; // carroP03 e carro P16
    private Semaphore semaforoCarroP03eCarroP16Rua18; // carroP03 e carro P16

    private Semaphore semaforoCarroP03eCarroP14Rua3eRuaAA; // carroP03 e carro P14
    private Semaphore semaforoCarroP03eCarroP14RuaR; // carroP03 e carro P14
    private Semaphore semaforoCarroP03eCarroP14RuaDDaRuaT; // carroP03 e carro P14

    private Semaphore semaforoCarroP03eCarroP23RuaBaRua1; // carroP03 e carro P23
    private Semaphore semaforoCarroP03eCarroP23Rua13; // carroP03 e carro P23
    private Semaphore semaforoCarroP03eCarroP23RuaDDaRua22; // carroP03 e carro P23

    private Semaphore semaforoCarroP03eCarroP20RuaC; // carroP03 e carro P20
    private Semaphore semaforoCarroP03eCarroP20Rua2; // carroP03 e carro P20
    private Semaphore semaforoCarroP03eCarroP20RuaO; // carroP03 e carro P20
    private Semaphore semaforoCarroP03eCarroP20RuaT; // carroP03 e carro P20
    private Semaphore semaforoCarroP03eCarroP20Rua14; // carroP03 e carro P20
    private Semaphore semaforoCarroP03eCarroP20RuaX; // carroP03 e carro P20

    private Semaphore semaforoCarroP06eCarroP09Rua11aRuaCC; // carroP06 e carro P09
    private Semaphore semaforoCarroP06eCarroP09RuaC; // carroP06 e carro P09
    private Semaphore semaforoCarroP06eCarroP09Rua20aRua23; // carroP06 e carro P09

    private Semaphore semaforoCarroP06eCarroP12Rua1aRua2; // carroP06 e carro P12
    private Semaphore semaforoCarroP06eCarroP12Rua26aRua27; // carroP06 e carro P12
    private Semaphore semaforoCarroP06eCarroP12RuaG; // carroP06 e carro P12

    private Semaphore semaforoCarroP06eCarroP16Rua2aRuaZ; // carroP06 e carro P16
    private Semaphore semaforoCarroP06eCarroP16Rua17aRua18; // carroP06 e carro P16
    private Semaphore semaforoCarroP06eCarroP16RuaBB; // carroP06 e carro P16

    private Semaphore semaforoCarroP06eCarroP14Rua3aRuaZ; // carroP06 e carro P14
    private Semaphore semaforoCarroP06eCarroP14RuaBBaRua28; // carroP06 e carro P14

    private Semaphore semaforoCarroP06eCarroP23RuaCCaRua28; // carroP06 e carro P23
    private Semaphore semaforoCarroP06eCarroP23Rua22; // carroP06 e carro P23
    private Semaphore semaforoCarroP06eCarroP23Rua16; // carroP06 e carro P23
    private Semaphore semaforoCarroP06eCarroP23RuaBaRua1; // carroP06 e carro P23
    private Semaphore semaforoCarroP06eCarroP23Rua7; // carroP06 e carro P23
    private Semaphore semaforoCarroP06eCarroP23Rua13; // carroP06 e carro P23

    private Semaphore semaforoCarroP06eCarroP20RuaXaRua23; // carroP06 e carro P20
    private Semaphore semaforoCarroP06eCarroP20Rua27; // carroP06 e carro P20
    private Semaphore semaforoCarroP06eCarroP20Rua21; // carroP06 e carro P20
    private Semaphore semaforoCarroP06eCarroP20Rua15aRuaC; // carroP06 e carro P20
    private Semaphore semaforoCarroP06eCarroP20RuaGaRua6; // carroP06 e carro P20
    private Semaphore semaforoCarroP06eCarroP20Rua2; // carroP06 e carro P20
    private Semaphore semaforoCarroP06eCarroP20Rua8; // carroP06 e carro P20
    private Semaphore semaforoCarroP06eCarroP20Rua14aRuaBB; // carroP06 e carro P20

    private Semaphore semaforoCarroP09eCarroP16RuaBB; // carroP09 e carro P16
    private Semaphore semaforoCarroP09eCarroP14RuaBBaRuaCC; // carroP09 e carro P14

    private Semaphore semaforoCarroP09eCarroP23Rua22; // carroP09 e carro P23
    private Semaphore semaforoCarroP09eCarroP23Rua10; // carroP09 e carro P23
    private Semaphore semaforoCarroP09eCarroP23Rua13; // carroP09 e carro P23
    private Semaphore semaforoCarroP09eCarroP23RuaCC; // carroP09 e carro P23

    private Semaphore semaforoCarroP09eCarroP20Rua23; // carroP09 e carro P20
    private Semaphore semaforoCarroP09eCarroP20Rua21; // carroP09 e carro P20
    private Semaphore semaforoCarroP09eCarroP20RuaCaRua10; // carroP09 e carro P20
    private Semaphore semaforoCarroP09eCarroP20Rua14aRuaBB; // carroP09 e carro P20

    private Semaphore semaforoCarroP12eCarroP16Rua2; // carroP12 e carro P16

    private Semaphore semaforoCarroP12eCarroP14RuaTaRuaP; // carroP12 e carro P14

    private Semaphore semaforoCarroP12eCarroP23RuaH; // carroP12 e carro P23
    private Semaphore semaforoCarroP12eCarroP23Rua1; // carroP12 e carro P23
    private Semaphore semaforoCarroP12eCarroP23RuaQ; // carroP12 e carro P23
    private Semaphore semaforoCarroP12eCarroP23RuaT; // carroP12 e carro P23

    private Semaphore semaforoCarroP12eCarroP20RuaI; // carroP12 e carro P20
    private Semaphore semaforoCarroP12eCarroP20RuaG; // carroP12 e carro P20
    private Semaphore semaforoCarroP12eCarroP20Rua2aRuaP; // carroP12 e carro P20
    private Semaphore semaforoCarroP12eCarroP20RuaTaRua27; // carroP12 e carro P20


    private Semaphore semaforoCarroP16eCarroP14Rua3aRuaBB; // carroP16 e carro P14

    private Semaphore semaforoCarroP16eCarroP23RuaK; // carroP16 e carro P23
    private Semaphore semaforoCarroP16eCarroP23Rua19; // carroP16 e carro P23

    private Semaphore semaforoCarroP16eCarroP20RuaKaRua2; // carroP16 e carro P20
    private Semaphore semaforoCarroP16eCarroP20RuaBBaRua19; // carroP16 e carro P20

    private Semaphore semaforoCarroP14eCarroP23RuaQ; // carroP14 e carro P23
    private Semaphore semaforoCarroP14eCarroP23RuaCCaRuaT; // carroP14 e carro P23

    private Semaphore semaforoCarroP14eCarroP20RuaT; // carroP14 e carro P20
    private Semaphore semaforoCarroP14eCarroP20RuaP; // carroP14 e carro P20
    private Semaphore semaforoCarroP14eCarroP20RuaBB; // carroP14 e carro P20

    private Semaphore semaforoCarroP23eCarroP20RuaT; // carroP23 e carro P20
    private Semaphore semaforoCarroP23eCarroP20Rua10; // carroP23 e carro P20
    private Semaphore semaforoCarroP23eCarroP20RuaK; // carroP23 e carro P20
    private Semaphore semaforoCarroP23eCarroP20Rua19; // carroP23 e carro P20

    // semaforos para resolver DeadLock
    private Semaphore semaforoCarroP12_CarroP06_CarroP14_RuaZaRua3; // Dealock na rua 2, 3 e P
    private Semaphore semaforoCarroP16_CarroP14_CarroP20_RuaBBaRua3; // Dealock na rua 2, 3 e P
    private Semaphore semaforoCarroP06_CarroP09_CarroP15_Carro914_Rua3aRua28; // Dealock na rua BB e CC
    private Semaphore semaforoCarroP03_CarroP06_CarroP14_RuaZaRuaC; // Dealock na rua 2, 3
    private Semaphore semaforoCarroP16_CarroP20_CarroP03_Rua2aRuaAA; // Dealock na rua BB e AA
    private Semaphore semaforoCarroP06_CarroP23_CarroP20_Rua2aRuaAA; // Dealock na rua BB e CC
    private Semaphore semaforoCarroP03_CarroP06_CarroP12_RuaOaRuaEE; // Dealock na rua 25 e 26
    private Semaphore semaforoCarroP03_CarroP06_CarroP20_RuaBBaRua14; // Dealock na rua BB e 14
    private Semaphore semaforoCarroP06_CarroP23_RuaCCaRua28; // Dealock na rua 28, 27 e T
    private Semaphore semaforoCarroP20_CarroP23_RuaCCaRua28; // Dealock na rua 28, 27 e T
    private Semaphore semaforoCarroP20_CarroP06_RuaEaRua26; // Dealock na rua 26, O e 27
    private Semaphore semaforoCarroP20_CarroP14_RuaBBaRua28; // Dealock na rua 27, T e 28
    private Semaphore semaforoCarroP06_CarroP09_RuaBBaRuaCC; // Dealock na rua 24, CC e DD

    // semaforos para skyna
    private Semaphore skynaWe18_CarroP03P23P06;
    private Semaphore skyna21eJ_CarroP06P12P09;
    private Semaphore skyna15eI_CarroP06eP12;
    private Semaphore skyna17eR_CarroP06P12P14P16; 
    private Semaphore skyna23eS_CarroP06P12P14P09;
    private Semaphore skynaQe13_CarroP06P12P14P09;
    private Semaphore skynaPe07_CarroP06P12P14;
    private Semaphore skyna12eL_CarroP06P16P09;
    private Semaphore skyna6eK_CarroP06eP16;
    private Semaphore skyna11eH_CarroP06eP23;
    private Semaphore skynaGe11_CarroP09P12;
    private Semaphore skynaNeM_CarroP16P23;
    private Semaphore skynaPe7_CarroP23P20;
    private Semaphore skynaVe13_CarroP23P20;
    private Semaphore skyna22eO_CarroP23P20;
    private Semaphore skyna16eI_CarroP23P20;
    
    /**********************************************************************************************
    Metodo: clicouEmIniciar
    Funcao: Instanciar as threads, mostrar os carros e iniciar as threads
    Parametros: Ação de clique
    Retorno: void
    *********************************************************************************************** */
    @FXML
    public void clicouEmIniciar(ActionEvent event) throws InterruptedException {
      if (buttonIniciar.getStyle().equals("-fx-background-color: #56a740;")) { // verifica se o botao esta verde
        threadCarroP03 = new Carro(3,this);
        threadCarroP06 = new Carro(6,this);
        threadCarroP09 = new Carro(9,this);
        threadCarroP12 = new Carro(12,this);
        threadCarroP14 = new Carro(14,this);
        threadCarroP16 = new Carro(16,this);
        threadCarroP20 = new Carro(20,this);
        threadCarroP23 = new Carro(23,this);

        carroP03.setVisible(true);
        carroP06.setVisible(true);
        carroP09.setVisible(true);
        carroP12.setVisible(true);
        carroP14.setVisible(true);
        carroP16.setVisible(true);
        carroP20.setVisible(true);
        carroP23.setVisible(true);
         
        threadCarroP03.start();
        threadCarroP06.start();
        threadCarroP09.start();
        threadCarroP12.start();
        threadCarroP14.start();
        threadCarroP16.start();
        threadCarroP20.start();
        threadCarroP23.start();
        
        buttonIniciar.setStyle("-fx-background-color: #807e7c"); // muda o botao de iniciar para cinza
        menuImagem.setVisible(true); // mostra o menu personalizado
        controleDosCarros.setVisible(true); // mostra o controle dos carros
        
        iniciarSemaforos(); // instanciar os semaforos
        acquireSemaforos(); // da acquire em alguns semaforos

     }   
    }
    
    /**********************************************************************************************
    Metodo: acquireSemaforos
    Funcao: Iniciar semaforo caso aquele carro ja comece na regiao critica
    Parametros: void
    Retorno: void
    *********************************************************************************************** */
    public void acquireSemaforos() throws InterruptedException {
        semaforoCarroP03eCarroP06Rua26aRuaE.acquire(); // o carro azul comeca aqui
        semaforoCarroP03eCarroP06RuaZaRuaC.acquire(); // o carro vermelho comeca aqui
        semaforoCarroP03eCarroP09Rua13Rua14.acquire(); // o carro roxo comeca aqui
        semaforoCarroP03eCarroP12Rua26.acquire(); // o carro ciano comeca aqui
        semaforoCarroP03eCarroP23RuaDDaRua22.acquire(); // o carro amarelo comeca aqui
        semaforoCarroP03eCarroP20RuaX.acquire(); // o carro laranja comeca aqui
        semaforoCarroP06eCarroP09Rua11aRuaCC.acquire(); // o carro roxo comeca aqui
        semaforoCarroP06eCarroP12Rua26aRua27.acquire(); // carro ciano comeca aqui
        semaforoCarroP06eCarroP16Rua2aRuaZ.acquire(); // o carro vermelho comeca aqui
        semaforoCarroP06eCarroP14Rua3aRuaZ.acquire(); // o carro vermelho comeca aqui
        semaforoCarroP06eCarroP23RuaCCaRua28.acquire(); // o carro amarelo comeca aqui
        semaforoCarroP06eCarroP20RuaXaRua23.acquire(); // o carro laranja comeca aqui
        semaforoCarroP09eCarroP16RuaBB.acquire(); // o carro roxo comeca aqui
        semaforoCarroP09eCarroP14RuaBBaRuaCC.acquire(); //o carro roxo comeca aqui
        semaforoCarroP09eCarroP20Rua14aRuaBB.acquire(); //  o carro roxo comeca aqui
        semaforoCarroP12eCarroP14RuaTaRuaP.acquire(); // o carro verde comeca aqui
        semaforoCarroP16eCarroP23RuaK.acquire(); // o carro rosa comeca aqui
        semaforoCarroP16eCarroP20RuaKaRua2.acquire(); // o carro rosa comeca aqui
        semaforoCarroP16eCarroP20RuaBBaRua19.acquire(); // o carro laranja comeca aqui
        semaforoCarroP14eCarroP23RuaCCaRuaT.acquire(); // o carro amarelo comeca aqui
        semaforoCarroP23eCarroP20Rua19.acquire(); // o carro laranja comeca aqui
        semaforoCarroP12_CarroP06_CarroP14_RuaZaRua3.acquire(); // o carro vermelho comeca aqui
        semaforoCarroP03_CarroP06_CarroP14_RuaZaRuaC.acquire(); // o carro vermelho comeca aqui
        semaforoCarroP06_CarroP23_RuaCCaRua28.acquire(); // o carro amarelo comeca aqui
        semaforoCarroP20_CarroP23_RuaCCaRua28.acquire(); // o carro amarelo comeca aqui
        semaforoCarroP20_CarroP14_RuaBBaRua28.acquire(); // o carro laranja comeca aqui
        semaforoCarroP06_CarroP09_RuaBBaRuaCC.acquire(); // o carro roxo comeca aqui
        skyna6eK_CarroP06eP16.acquire();
        skyna17eR_CarroP06P12P14P16.acquire();
        semaforoCarroP14eCarroP23RuaQ.acquire();
        semaforoCarroP03eCarroP14RuaR.acquire();
    } // fim iniciar carro
    
    /**********************************************************************************************
    Metodo: iniciarSemaforos
    Funcao: Instanciar os semaforos novamente 
    Parametros: void
    Retorno: void
    *********************************************************************************************** */
    public void iniciarSemaforos() {
      semaforoCarroP03eCarroP06RuaZaRuaC = new Semaphore(1); // carroP03 e carro P06
      semaforoCarroP03eCarroP06Rua26aRuaE = new Semaphore(1); // carroP03 e carro P06
      semaforoCarroP03eCarroP06Rua14aRua13 = new Semaphore(1); // carroP03 e carro P06
      semaforoCarroP03eCarroP06Rua18aRuaX = new Semaphore(1); // carroP03 e carro P06
      semaforoCarroP03eCarroP06Rua22 = new Semaphore(1); // carroP03 e carro P06
      semaforoCarroP03eCarroP06Rua28aRuaDD = new Semaphore(1); // carroP03 e carro P06
      semaforoCarroP03eCarroP09RuaDRuaC = new Semaphore(1); // carroP03 e carro P09
      semaforoCarroP03eCarroP09Rua13Rua14 = new Semaphore(1); // carroP03 e carro P09
      semaforoCarroP03eCarroP09Rua24 = new Semaphore(1); // carroP03 e carro P09
      semaforoCarroP03eCarroP09Rua22 = new Semaphore(1); // carroP03 e carro P09
      semaforoCarroP03eCarroP12Rua1Rua12 = new Semaphore(1); // carroP03 e carro P12
      semaforoCarroP03eCarroP12RuaR = new Semaphore(1); // carroP03 e carro P12
      semaforoCarroP03eCarroP12RuaT = new Semaphore(1); // carroP03 e carro P12
      semaforoCarroP03eCarroP12Rua26 = new Semaphore(1); // carroP03 e carro P12
      semaforoCarroP03eCarroP16Rua2aRuaAA = new Semaphore(1); // carroP03 e carro P16
      semaforoCarroP03eCarroP16Rua18 = new Semaphore(1); // carroP03 e carro P16
      semaforoCarroP03eCarroP14Rua3eRuaAA = new Semaphore(1); // carroP03 e carro P14
      semaforoCarroP03eCarroP14RuaR= new Semaphore(1); // carroP03 e carro P14
      semaforoCarroP03eCarroP14RuaDDaRuaT= new Semaphore(1); // carroP03 e carro P14
      semaforoCarroP03eCarroP23RuaBaRua1= new Semaphore(1); // carroP03 e carro P23
      semaforoCarroP03eCarroP23Rua13= new Semaphore(1); // carroP03 e carro P23
      semaforoCarroP03eCarroP23RuaDDaRua22= new Semaphore(1); // carroP03 e carro P23
      semaforoCarroP03eCarroP20RuaC= new Semaphore(1); // carroP03 e carro P20
      semaforoCarroP03eCarroP20Rua2= new Semaphore(1); // carroP03 e carro P20
      semaforoCarroP03eCarroP20RuaO= new Semaphore(1); // carroP03 e carro P20
      semaforoCarroP03eCarroP20RuaT= new Semaphore(1); // carroP03 e carro P20
      semaforoCarroP03eCarroP20Rua14= new Semaphore(1); // carroP03 e carro P20
      semaforoCarroP03eCarroP20RuaX= new Semaphore(1); // carroP03 e carro P20
      semaforoCarroP06eCarroP09Rua11aRuaCC= new Semaphore(1); // carroP06 e carro P09
      semaforoCarroP06eCarroP09RuaC= new Semaphore(1); // carroP06 e carro P09
      semaforoCarroP06eCarroP09Rua20aRua23= new Semaphore(1); // carroP06 e carro P09
      semaforoCarroP06eCarroP12Rua1aRua2= new Semaphore(1); // carroP06 e carro P12
      semaforoCarroP06eCarroP12Rua26aRua27= new Semaphore(1); // carroP06 e carro P12
      semaforoCarroP06eCarroP12RuaG= new Semaphore(1); // carroP06 e carro P12
      semaforoCarroP06eCarroP16Rua2aRuaZ= new Semaphore(1); // carroP06 e carro P16
      semaforoCarroP06eCarroP16Rua17aRua18= new Semaphore(1); // carroP06 e carro P16
      semaforoCarroP06eCarroP16RuaBB= new Semaphore(1); // carroP06 e carro P16
      semaforoCarroP06eCarroP14Rua3aRuaZ= new Semaphore(1); // carroP06 e carro P14
      semaforoCarroP06eCarroP14RuaBBaRua28= new Semaphore(1); // carroP06 e carro P14
      semaforoCarroP06eCarroP23RuaCCaRua28= new Semaphore(1); // carroP06 e carro P23
      semaforoCarroP06eCarroP23Rua22= new Semaphore(1); // carroP06 e carro P23
      semaforoCarroP06eCarroP23Rua16= new Semaphore(1); // carroP06 e carro P23
      semaforoCarroP06eCarroP23RuaBaRua1= new Semaphore(1); // carroP06 e carro P23
      semaforoCarroP06eCarroP23Rua7= new Semaphore(1); // carroP06 e carro P23
      semaforoCarroP06eCarroP23Rua13= new Semaphore(1); // carroP06 e carro P23
      semaforoCarroP06eCarroP20RuaXaRua23 = new Semaphore(1); // carroP06 e carro P20
      semaforoCarroP06eCarroP20Rua27 = new Semaphore(1); // carroP06 e carro P20
      semaforoCarroP06eCarroP20Rua21 = new Semaphore(1); // carroP06 e carro P20
      semaforoCarroP06eCarroP20Rua15aRuaC = new Semaphore(1); // carroP06 e carro P20
      semaforoCarroP06eCarroP20RuaGaRua6 = new Semaphore(1); // carroP06 e carro P20
      semaforoCarroP06eCarroP20Rua2 = new Semaphore(1); // carroP06 e carro P20
      semaforoCarroP06eCarroP20Rua8 = new Semaphore(1); // carroP06 e carro P20
      semaforoCarroP06eCarroP20Rua14aRuaBB = new Semaphore(1); // carroP06 e carro P20
      semaforoCarroP09eCarroP16RuaBB = new Semaphore(1); // carroP09 e carro P16
      semaforoCarroP09eCarroP14RuaBBaRuaCC = new Semaphore(1); // carroP09 e carro P14
      semaforoCarroP09eCarroP23Rua22 = new Semaphore(1); // carroP09 e carro P23
      semaforoCarroP09eCarroP23Rua10 = new Semaphore(1); // carroP09 e carro P23
      semaforoCarroP09eCarroP23Rua13 = new Semaphore(1); // carroP09 e carro P23
      semaforoCarroP09eCarroP23RuaCC = new Semaphore(1); // carroP09 e carro P23
      semaforoCarroP09eCarroP20Rua23 = new Semaphore(1); // carroP09 e carro P20
      semaforoCarroP09eCarroP20Rua21 = new Semaphore(1); // carroP09 e carro P20
      semaforoCarroP09eCarroP20RuaCaRua10 = new Semaphore(1); // carroP09 e carro P20
      semaforoCarroP09eCarroP20Rua14aRuaBB = new Semaphore(1); // carroP09 e carro P20
      semaforoCarroP12eCarroP16Rua2 = new Semaphore(1); // carroP12 e carro P16
      semaforoCarroP12eCarroP14RuaTaRuaP = new Semaphore(1); // carroP12 e carro P14
      semaforoCarroP12eCarroP23RuaH = new Semaphore(1); // carroP12 e carro P23
      semaforoCarroP12eCarroP23Rua1 = new Semaphore(1); // carroP12 e carro P23
      semaforoCarroP12eCarroP23RuaQ = new Semaphore(1); // carroP12 e carro P23
      semaforoCarroP12eCarroP23RuaT = new Semaphore(1); // carroP12 e carro P23
      semaforoCarroP12eCarroP20RuaI = new Semaphore(1); // carroP12 e carro P20
      semaforoCarroP12eCarroP20RuaG = new Semaphore(1); // carroP12 e carro P20
      semaforoCarroP12eCarroP20Rua2aRuaP = new Semaphore(1); // carroP12 e carro P20
      semaforoCarroP12eCarroP20RuaTaRua27 = new Semaphore(1); // carroP12 e carro P20
      semaforoCarroP16eCarroP14Rua3aRuaBB = new Semaphore(1); // carroP16 e carro P14
      semaforoCarroP16eCarroP23RuaK = new Semaphore(1); // carroP16 e carro P23
      semaforoCarroP16eCarroP23Rua19 = new Semaphore(1); // carroP16 e carro P23
      semaforoCarroP16eCarroP20RuaKaRua2 = new Semaphore(1); // carroP16 e carro P20
      semaforoCarroP16eCarroP20RuaBBaRua19 = new Semaphore(1); // carroP16 e carro P20
      semaforoCarroP14eCarroP23RuaQ = new Semaphore(1); // carroP14 e carro P23
      semaforoCarroP14eCarroP23RuaCCaRuaT = new Semaphore(1); // carroP14 e carro P23
      semaforoCarroP14eCarroP20RuaT = new Semaphore(1); // carroP14 e carro P20
      semaforoCarroP14eCarroP20RuaP = new Semaphore(1); // carroP14 e carro P20
      semaforoCarroP14eCarroP20RuaBB = new Semaphore(1); // carroP14 e carro P20
      semaforoCarroP23eCarroP20RuaT = new Semaphore(1); // carroP23 e carro P20
      semaforoCarroP23eCarroP20Rua10 = new Semaphore(1); // carroP23 e carro P20
      semaforoCarroP23eCarroP20RuaK = new Semaphore(1); // carroP23 e carro P20
      semaforoCarroP23eCarroP20Rua19 = new Semaphore(1); // carroP23 e carro P20
      
      semaforoCarroP12_CarroP06_CarroP14_RuaZaRua3 = new Semaphore (1);
      semaforoCarroP16_CarroP14_CarroP20_RuaBBaRua3 = new Semaphore (1);
      semaforoCarroP06_CarroP09_CarroP15_Carro914_Rua3aRua28 = new Semaphore (1);
      semaforoCarroP03_CarroP06_CarroP14_RuaZaRuaC = new Semaphore (1);
      semaforoCarroP16_CarroP20_CarroP03_Rua2aRuaAA = new Semaphore (1);
      semaforoCarroP06_CarroP23_CarroP20_Rua2aRuaAA  = new Semaphore (1);
      semaforoCarroP03_CarroP06_CarroP12_RuaOaRuaEE = new Semaphore (1);
      semaforoCarroP03_CarroP06_CarroP20_RuaBBaRua14 = new Semaphore (1);
      semaforoCarroP06_CarroP23_RuaCCaRua28  = new Semaphore (1);
      semaforoCarroP20_CarroP23_RuaCCaRua28 = new Semaphore (1);
      semaforoCarroP20_CarroP06_RuaEaRua26 = new Semaphore (1);
      semaforoCarroP20_CarroP14_RuaBBaRua28 = new Semaphore (1);
      semaforoCarroP06_CarroP09_RuaBBaRuaCC = new Semaphore (1);
      
      skynaWe18_CarroP03P23P06 = new Semaphore (1);
      skyna21eJ_CarroP06P12P09 = new Semaphore (1);
      skyna15eI_CarroP06eP12 = new Semaphore (1);
      skyna17eR_CarroP06P12P14P16 = new Semaphore (1);
      skyna23eS_CarroP06P12P14P09 = new Semaphore (1);
      skynaQe13_CarroP06P12P14P09 = new Semaphore (1);
      skynaPe07_CarroP06P12P14 = new Semaphore (1);
      skyna12eL_CarroP06P16P09 = new Semaphore (1);
      skyna6eK_CarroP06eP16 = new Semaphore (1);
      skyna11eH_CarroP06eP23 = new Semaphore (1);
      skynaGe11_CarroP09P12 = new Semaphore (1);
      skynaNeM_CarroP16P23 = new Semaphore (1);
      skynaPe7_CarroP23P20 = new Semaphore (1);
      skynaVe13_CarroP23P20 = new Semaphore (1);
      skyna22eO_CarroP23P20 = new Semaphore (1);
      skyna16eI_CarroP23P20 = new Semaphore (1);
    }
    
    /**********************************************************************************************
    Metodo: clicouEmReiniciar
    Funcao: Colocar os sliders nos valores iniciais, esconder o menu e o controle dos carros, deixar o botao de iniciar verde...
    Parametros: Acao de clique
    Retorno: void
    *********************************************************************************************** */
    @FXML
    public void clicouEmReiniciar(ActionEvent event) throws InterruptedException {
      if (buttonIniciar.getStyle().equals("-fx-background-color: #807e7c")) { // verifica se o botao iniciar esta cinza
        sliderCarroP03.setValue(3);
        sliderCarroP06.setValue(3);
        sliderCarroP09.setValue(3);
        sliderCarroP12.setValue(3);
        sliderCarroP14.setValue(3);
        sliderCarroP16.setValue(3);
        sliderCarroP20.setValue(3);
        sliderCarroP23.setValue(3);
        
        menuImagem.setVisible(false);
        controleDosCarros.setVisible(false);
        
        // esconde o percurso para caso esteja habilitado
        // coloca a cor do botao em branco
        percurso03.setVisible(false);
        buttonMostrarP03.setStyle("-fx-background-color: #fff;"); 

        percurso06.setVisible(false);
        buttonMostrarP06.setStyle("-fx-background-color: #fff;");
        
        percurso09.setVisible(false);
        buttonMostrarP09.setStyle("-fx-background-color: #fff;");
        
        percurso12.setVisible(false);
        buttonMostrarP12.setStyle("-fx-background-color: #fff;");
        
        percurso14.setVisible(false);
        buttonMostrarP14.setStyle("-fx-background-color: #fff;");
        
        percurso16.setVisible(false);
        buttonMostrarP16.setStyle("-fx-background-color: #fff;");
        
        percurso20.setVisible(false);
        buttonMostrarP20.setStyle("-fx-background-color: #fff;");
        
        percurso23.setVisible(false);
        buttonMostrarP23.setStyle("-fx-background-color: #fff;");
        
        // butao de pausar volta para cor branca
        buttonPausarP03.setStyle("-fx-background-color: #fff;");
        buttonPausarP06.setStyle("-fx-background-color: #fff;");
        buttonPausarP09.setStyle("-fx-background-color: #fff;");
        buttonPausarP12.setStyle("-fx-background-color: #fff;");
        buttonPausarP14.setStyle("-fx-background-color: #fff;");
        buttonPausarP16.setStyle("-fx-background-color: #fff;");
        buttonPausarP20.setStyle("-fx-background-color: #fff;");
        buttonPausarP23.setStyle("-fx-background-color: #fff;");
        
        buttonIniciar.setStyle("-fx-background-color: #56a740;"); // botao iniciar fica verde
        
        // pausa as threads
        threadCarroP03.stop();
        threadCarroP06.stop();
        threadCarroP09.stop();
        threadCarroP12.stop();
        threadCarroP14.stop();
        threadCarroP16.stop();
        threadCarroP20.stop();
        threadCarroP23.stop();
        
        // retorna para seus valores iniciais
        Platform.runLater(() ->  carroP03.setX(0) );
        Platform.runLater(() ->  carroP03.setY(0) );
        Platform.runLater(() ->  carroP03.setRotate(0) );
        
        Platform.runLater(() ->  carroP06.setX(0) );
        Platform.runLater(() ->  carroP06.setY(0) );
        Platform.runLater(() ->  carroP06.setRotate(0) );
        
        Platform.runLater(() ->  carroP09.setX(0) );
        Platform.runLater(() ->  carroP09.setY(0) );
        Platform.runLater(() ->  carroP09.setRotate(0) );
        
        Platform.runLater(() ->  carroP12.setX(0) );
        Platform.runLater(() ->  carroP12.setY(0) );
        Platform.runLater(() ->  carroP12.setRotate(0) );
        
        Platform.runLater(() ->  carroP14.setX(0) );
        Platform.runLater(() ->  carroP14.setY(0) );
        Platform.runLater(() ->  carroP14.setRotate(0) );
        
        Platform.runLater(() ->  carroP16.setX(0) );
        Platform.runLater(() ->  carroP16.setY(0) );
        Platform.runLater(() ->  carroP16.setRotate(0) );
        
        Platform.runLater(() ->  carroP20.setX(0) );
        Platform.runLater(() ->  carroP20.setY(0) );
        Platform.runLater(() ->  carroP20.setRotate(0) );
        
        Platform.runLater(() ->  carroP23.setX(0) );
        Platform.runLater(() ->  carroP23.setY(0) );
        Platform.runLater(() ->  carroP23.setRotate(0) );
        

        iniciarSemaforos(); 
        acquireSemaforos();

      }
    }
    
    // eh iniciado quando a janela é aberta
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
      // listeners para cada slider
      sliderCarroP03.valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
         threadCarroP03.setVelocidadeCarro(10-sliderCarroP03.getValue()); // varia de 3 a 7
        }
      });
      sliderCarroP06.valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
         threadCarroP06.setVelocidadeCarro(10-sliderCarroP06.getValue());
        }
      });
      sliderCarroP09.valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
         threadCarroP09.setVelocidadeCarro(10-sliderCarroP09.getValue());
        }
      });
      sliderCarroP12.valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
         threadCarroP12.setVelocidadeCarro(10-sliderCarroP12.getValue());
        }
      });
      sliderCarroP14.valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
         threadCarroP14.setVelocidadeCarro(10-sliderCarroP14.getValue());
        }
      });
      sliderCarroP16.valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
         threadCarroP16.setVelocidadeCarro(10-sliderCarroP16.getValue());
        }
      });
      sliderCarroP20.valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
         threadCarroP20.setVelocidadeCarro(10-sliderCarroP20.getValue());
        }
      });
      sliderCarroP23.valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
         threadCarroP23.setVelocidadeCarro(10-sliderCarroP23.getValue());
        }
      });
      
    } // fim initializable
    
    /**********************************************************************************************
    Metodo: clicouEmPausarCarroXX
    Funcao: verifica se o botao esta branco para pausar, se branco volta a executar
    Parametros: Acao de clique
    Retorno: void
    *********************************************************************************************** */
    @FXML
    void clicouEmPausarCarroP03(ActionEvent event) {
      if (buttonPausarP03.getStyle().equals("-fx-background-color: #fff;")) { // se cor branca
        buttonPausarP03.setStyle("-fx-background-color: #efd41b;"); // butao fica amarelo
        threadCarroP03.suspend();
      }
      else if (buttonPausarP03.getStyle().equals("-fx-background-color: #efd41b;")) { // se cor amarela
        buttonPausarP03.setStyle("-fx-background-color: #fff;"); // cor fica branca
        threadCarroP03.resume();
      }
    }

    @FXML
    void clicouEmPausarCarroP06(ActionEvent event) {
      if (buttonPausarP06.getStyle().equals("-fx-background-color: #fff;")) {
        buttonPausarP06.setStyle("-fx-background-color: #efd41b;");
        threadCarroP06.suspend();
      }
      else if (buttonPausarP06.getStyle().equals("-fx-background-color: #efd41b;")) {
        buttonPausarP06.setStyle("-fx-background-color: #fff;");
        threadCarroP06.resume();
      }
    }

    @FXML
    void clicouEmPausarCarroP09(ActionEvent event) {
      if (buttonPausarP09.getStyle().equals("-fx-background-color: #fff;")) {
        buttonPausarP09.setStyle("-fx-background-color: #efd41b;");
        threadCarroP09.suspend();
      }
      else if (buttonPausarP09.getStyle().equals("-fx-background-color: #efd41b;")) {
        buttonPausarP09.setStyle("-fx-background-color: #fff;");
        threadCarroP09.resume();
      }
    }

    @FXML
    void clicouEmPausarCarroP12(ActionEvent event) {
      if (buttonPausarP12.getStyle().equals("-fx-background-color: #fff;")) {
        buttonPausarP12.setStyle("-fx-background-color: #efd41b;");
        threadCarroP12.suspend();
      }
      else if (buttonPausarP12.getStyle().equals("-fx-background-color: #efd41b;")) {
        buttonPausarP12.setStyle("-fx-background-color: #fff;");
        threadCarroP12.resume();
      }
    }

    @FXML
    void clicouEmPausarCarroP14(ActionEvent event) {
      if (buttonPausarP14.getStyle().equals("-fx-background-color: #fff;")) {
        buttonPausarP14.setStyle("-fx-background-color: #efd41b;");
        threadCarroP14.suspend();
      }
      else if (buttonPausarP14.getStyle().equals("-fx-background-color: #efd41b;")) {
        buttonPausarP14.setStyle("-fx-background-color: #fff;");
        threadCarroP14.resume();
      }
    }

    @FXML
    void clicouEmPausarCarroP16(ActionEvent event) {
      if (buttonPausarP16.getStyle().equals("-fx-background-color: #fff;")) {
        buttonPausarP16.setStyle("-fx-background-color: #efd41b;");
        threadCarroP16.suspend();
      }
      else if (buttonPausarP16.getStyle().equals("-fx-background-color: #efd41b;")) {
        buttonPausarP16.setStyle("-fx-background-color: #fff;");
        threadCarroP16.resume();
      }
    }

    @FXML
    void clicouEmPausarCarroP20(ActionEvent event) {
      if (buttonPausarP20.getStyle().equals("-fx-background-color: #fff;")) {
        buttonPausarP20.setStyle("-fx-background-color: #efd41b;");
        threadCarroP20.suspend();
      }
      else if (buttonPausarP20.getStyle().equals("-fx-background-color: #efd41b;")) {
        buttonPausarP20.setStyle("-fx-background-color: #fff;");
        threadCarroP20.resume();
      }
    }

    @FXML
    void clicouEmPausarCarroP23(ActionEvent event) {
      if (buttonPausarP23.getStyle().equals("-fx-background-color: #fff;")) {
        buttonPausarP23.setStyle("-fx-background-color: #efd41b;");
        threadCarroP23.suspend();
      }
      else if (buttonPausarP23.getStyle().equals("-fx-background-color: #efd41b;")) {
        buttonPausarP23.setStyle("-fx-background-color: #fff;");
        threadCarroP23.resume();
      }
    }

    /**********************************************************************************************
    Metodo: clicouMostrarPercursoCarroXX
    Funcao: Mostra a imagem do percurso se o botao do percurso é branca, esconde caso amarelo
    Parametros: Acao de clique
    Retorno: void
    *********************************************************************************************** */
    @FXML
    void clicouMostrarPercursoCarroP03(ActionEvent event) {
      if (buttonMostrarP03.getStyle().equals("-fx-background-color: #fff;")) { // se botao eh cor branca
        percurso03.setVisible(true);
        buttonMostrarP03.setStyle("-fx-background-color: #efd41b;"); // botao fica amarelo
      }
      else if (buttonMostrarP03.getStyle().equals("-fx-background-color: #efd41b;")) { // se botao cor amarela
        percurso03.setVisible(false);
        buttonMostrarP03.setStyle("-fx-background-color: #fff;"); // botao fica branco
      }
    }

    @FXML
    void clicouMostrarPercursoCarroP06(ActionEvent event) {
      if (buttonMostrarP06.getStyle().equals("-fx-background-color: #fff;")) {
        percurso06.setVisible(true);
        buttonMostrarP06.setStyle("-fx-background-color: #efd41b;");
      }
      else if (buttonMostrarP06.getStyle().equals("-fx-background-color: #efd41b;")) {
        percurso06.setVisible(false);
        buttonMostrarP06.setStyle("-fx-background-color: #fff;");
      }
    }

    @FXML
    void clicouMostrarPercursoCarroP09(ActionEvent event) {
      if (buttonMostrarP09.getStyle().equals("-fx-background-color: #fff;")) {
        percurso09.setVisible(true);
        buttonMostrarP09.setStyle("-fx-background-color: #efd41b;");
      }
      else if (buttonMostrarP09.getStyle().equals("-fx-background-color: #efd41b;")) {
        percurso09.setVisible(false);
        buttonMostrarP09.setStyle("-fx-background-color: #fff;");
      }
    }

    @FXML
    void clicouMostrarPercursoCarroP12(ActionEvent event) {
      if (buttonMostrarP12.getStyle().equals("-fx-background-color: #fff;")) {
        percurso12.setVisible(true);
        buttonMostrarP12.setStyle("-fx-background-color: #efd41b;");
      }
      else if (buttonMostrarP12.getStyle().equals("-fx-background-color: #efd41b;")) {
        percurso12.setVisible(false);
        buttonMostrarP12.setStyle("-fx-background-color: #fff;");
      }
    }

    @FXML
    void clicouMostrarPercursoCarroP14(ActionEvent event) {
      if (buttonMostrarP14.getStyle().equals("-fx-background-color: #fff;")) {
        percurso14.setVisible(true);
        buttonMostrarP14.setStyle("-fx-background-color: #efd41b;");
      }
      else if (buttonMostrarP14.getStyle().equals("-fx-background-color: #efd41b;")) {
        percurso14.setVisible(false);
        buttonMostrarP14.setStyle("-fx-background-color: #fff;");
      }
    }

    @FXML
    void clicouMostrarPercursoCarroP16(ActionEvent event) {
      if (buttonMostrarP16.getStyle().equals("-fx-background-color: #fff;")) {
        percurso16.setVisible(true);
        buttonMostrarP16.setStyle("-fx-background-color: #efd41b;");
      }
      else if (buttonMostrarP16.getStyle().equals("-fx-background-color: #efd41b;")) {
        percurso16.setVisible(false);
        buttonMostrarP16.setStyle("-fx-background-color: #fff;");
      }
    }

    @FXML
    void clicouMostrarPercursoCarroP20(ActionEvent event) {
      if (buttonMostrarP20.getStyle().equals("-fx-background-color: #fff;")) {
        percurso20.setVisible(true);
        buttonMostrarP20.setStyle("-fx-background-color: #efd41b;");
      }
      else if (buttonMostrarP20.getStyle().equals("-fx-background-color: #efd41b;")) {
        percurso20.setVisible(false);
        buttonMostrarP20.setStyle("-fx-background-color: #fff;");
      }
    }

    @FXML
    void clicouMostrarPercursoCarroP23(ActionEvent event) {
      if (buttonMostrarP23.getStyle().equals("-fx-background-color: #fff;")) {
        percurso23.setVisible(true);
        buttonMostrarP23.setStyle("-fx-background-color: #efd41b;");
      }
      else if (buttonMostrarP23.getStyle().equals("-fx-background-color: #efd41b;")) {
        percurso23.setVisible(false);
        buttonMostrarP23.setStyle("-fx-background-color: #fff;");
      }
    }
    
    /**********************************************************************************************
    Metodo: carroAndar
    Funcao: Movimenta o carro, alem de verificar se ele pode movimentar na rua atraves de semaforos
    Parametros: int
    Retorno: void
    *********************************************************************************************** */
    public void carroAndar(int percursoDoCarro) throws InterruptedException {
      switch (percursoDoCarro) {
        case 3: { // carroP03 (cor azul)
          threadCarroP03.andarCarroVertical(-45, "Cima", carroP03); // RUA E
          semaforoCarroP03eCarroP09RuaDRuaC.acquire();
          threadCarroP03.andarCarroVertical(-145, "Cima", carroP03); // RUA D
          semaforoCarroP03eCarroP06Rua26aRuaE.release();
          semaforoCarroP03_CarroP06_CarroP14_RuaZaRuaC.acquire(); // resolver deadlock
          semaforoCarroP03eCarroP06RuaZaRuaC.acquire();
          semaforoCarroP03eCarroP20RuaC.acquire();
          threadCarroP03.andarCarroVertical(-245, "Cima", carroP03); // RUA C
          semaforoCarroP03eCarroP23RuaBaRua1.acquire();
          threadCarroP03.andarCarroVertical(-345, "Cima", carroP03); // RUA B
          semaforoCarroP03eCarroP09RuaDRuaC.release();
          semaforoCarroP03eCarroP20RuaC.release();
          threadCarroP03.andarCarroVertical(-440, "Cima", carroP03); // RUA A
          threadCarroP03.andarCarroVertical(-490, "Cima", carroP03); // RUA A
          threadCarroP03.girarImagemCarro(90, carroP03); // virar pra direita
          threadCarroP03.andarCarroHorizontal(90, "Direita", carroP03); // RUA 0
          semaforoCarroP03eCarroP12Rua1Rua12.acquire();
          threadCarroP03.andarCarroHorizontal(230, "Direita", carroP03); // RUA 1
          semaforoCarroP16_CarroP20_CarroP03_Rua2aRuaAA.acquire(); // resolver deadlock
          semaforoCarroP03eCarroP16Rua2aRuaAA.acquire();
          semaforoCarroP03eCarroP20Rua2.acquire();
          threadCarroP03.andarCarroHorizontal(373, "Direita", carroP03); // RUA 2
          semaforoCarroP03eCarroP23RuaBaRua1.release();
          semaforoCarroP03eCarroP14Rua3eRuaAA.acquire();
          threadCarroP03.andarCarroHorizontal(540, "Direita", carroP03); // RUA 3
          semaforoCarroP03eCarroP12Rua1Rua12.release();
          semaforoCarroP03eCarroP20Rua2.release();
          threadCarroP03.andarCarroHorizontal(660, "Direita", carroP03); // RUA 4
          threadCarroP03.andarCarroHorizontal(700, "Direita", carroP03); // RUA 4
          threadCarroP03.girarImagemCarro(180, carroP03);
          threadCarroP03.andarCarroVertical(-443, "Baixo", carroP03); // RUA Z
          threadCarroP03.andarCarroVertical(-347, "Baixo", carroP03); // RUA AA
          semaforoCarroP03_CarroP06_CarroP14_RuaZaRuaC.release(); // resolver deadlock
          semaforoCarroP03eCarroP06RuaZaRuaC.release();
          semaforoCarroP03eCarroP20Rua14.acquire();
          semaforoCarroP03eCarroP06Rua14aRua13.acquire();
          semaforoCarroP03eCarroP09Rua13Rua14.acquire();
          threadCarroP03.andarCarroVertical(-290, "Baixo", carroP03); // RUA AA
          threadCarroP03.girarImagemCarro(-90, carroP03);
          threadCarroP03.andarCarroHorizontal(605, "Esquerda", carroP03); // RUA 14
          semaforoCarroP03eCarroP16Rua2aRuaAA.release();
          semaforoCarroP03eCarroP14Rua3eRuaAA.release();
          semaforoCarroP03eCarroP23Rua13.acquire();
          threadCarroP03.andarCarroHorizontal(470, "Esquerda", carroP03); // RUA 13
          semaforoCarroP16_CarroP20_CarroP03_Rua2aRuaAA.release();
          semaforoCarroP03eCarroP20Rua14.release();
          semaforoCarroP03eCarroP12RuaR.acquire();
          semaforoCarroP03eCarroP14RuaR.acquire();
          threadCarroP03.andarCarroHorizontal(420, "Esquerda", carroP03); // RUA 13
          threadCarroP03.girarImagemCarro(-180, carroP03);
          threadCarroP03.andarCarroVertical(-233, "Baixo", carroP03); // RUA R
          semaforoCarroP03eCarroP23Rua13.release();
          semaforoCarroP03eCarroP06Rua14aRua13.release();
          semaforoCarroP03eCarroP09Rua13Rua14.release();
          semaforoCarroP03eCarroP06Rua18aRuaX.acquire();
          semaforoCarroP03eCarroP16Rua18.acquire();
          threadCarroP03.andarCarroVertical(-190, "Baixo", carroP03); // RUA R
          threadCarroP03.girarImagemCarro(90, carroP03);
          threadCarroP03.andarCarroHorizontal(520, "Direita", carroP03); // RUA 18
          semaforoCarroP03eCarroP12RuaR.release();
          semaforoCarroP03eCarroP14RuaR.release();
          semaforoCarroP03eCarroP20RuaX.acquire();
          skynaWe18_CarroP03P23P06.acquire();
          threadCarroP03.andarCarroHorizontal(560, "Direita", carroP03); // RUA 18
          threadCarroP03.girarImagemCarro(-180, carroP03);
          threadCarroP03.andarCarroVertical(-142, "Baixo", carroP03); // RUA X
          skynaWe18_CarroP03P23P06.release();
          semaforoCarroP03eCarroP16Rua18.release();
          semaforoCarroP03eCarroP09Rua24.acquire();
          threadCarroP03.andarCarroVertical(-90, "Baixo", carroP03); // RUA X
          threadCarroP03.girarImagemCarro(90, carroP03);
          threadCarroP03.andarCarroHorizontal(660, "Direita", carroP03); // RUA 24
          semaforoCarroP03eCarroP20RuaX.release();
          semaforoCarroP03eCarroP06Rua18aRuaX.release();
          semaforoCarroP03eCarroP06Rua28aRuaDD.acquire();
          semaforoCarroP03eCarroP14RuaDDaRuaT.acquire();
          semaforoCarroP03eCarroP23RuaDDaRua22.acquire();
          threadCarroP03.andarCarroHorizontal(700, "Direita", carroP03); // RUA 24
          threadCarroP03.girarImagemCarro(-180, carroP03);
          threadCarroP03.andarCarroVertical(-30, "Baixo", carroP03); // RUA DD
          semaforoCarroP03eCarroP09Rua24.release();
          threadCarroP03.andarCarroVertical(10, "Baixo", carroP03); // RUA DD
          threadCarroP03.girarImagemCarro(-90, carroP03);
          threadCarroP03.andarCarroHorizontal(603, "Esquerda", carroP03); // RUA 29
          threadCarroP03.andarCarroHorizontal(470, "Esquerda", carroP03); // RUA 28
          semaforoCarroP03eCarroP12RuaT.acquire();
          threadCarroP03.andarCarroHorizontal(420, "Esquerda", carroP03); // RUA 28
          semaforoCarroP03eCarroP20RuaT.acquire();
          threadCarroP03.girarImagemCarro(-360, carroP03);
          threadCarroP03.andarCarroVertical(-43, "Cima", carroP03); // RUA T
          semaforoCarroP03eCarroP06Rua28aRuaDD.release();
          semaforoCarroP03eCarroP06Rua22.acquire();
          semaforoCarroP03eCarroP09Rua22.acquire();
          threadCarroP03.andarCarroVertical(-90, "Cima", carroP03); // RUA T
          threadCarroP03.girarImagemCarro(-90, carroP03);
          threadCarroP03.andarCarroHorizontal(323, "Esquerda", carroP03); // RUA 22
          semaforoCarroP03eCarroP20RuaT.release();
          semaforoCarroP03eCarroP12RuaT.release();
          semaforoCarroP03eCarroP14RuaDDaRuaT.release();
          semaforoCarroP03eCarroP20RuaO.acquire();
          threadCarroP03.andarCarroHorizontal(280, "Esquerda", carroP03); // RUA 22
          threadCarroP03.girarImagemCarro(-180, carroP03);
          threadCarroP03.andarCarroVertical(-40, "Baixo", carroP03); // RUA O
          semaforoCarroP03eCarroP23RuaDDaRua22.release();
          semaforoCarroP03eCarroP09Rua22.release();
          semaforoCarroP03eCarroP06Rua22.release();
          semaforoCarroP03_CarroP06_CarroP12_RuaOaRuaEE.acquire(); //resolver deadlock
          semaforoCarroP03eCarroP06Rua26aRuaE.acquire();
          semaforoCarroP03eCarroP12Rua26.acquire();
          threadCarroP03.andarCarroVertical(10, "Baixo", carroP03); // RUA O
          threadCarroP03.girarImagemCarro(-90, carroP03); 
          threadCarroP03.andarCarroHorizontal(180, "Esquerda", carroP03); // RUA 26
          semaforoCarroP03eCarroP20RuaO.release();
          threadCarroP03.andarCarroHorizontal(40, "Esquerda", carroP03); // RUA 25
          semaforoCarroP03eCarroP12Rua26.release();
          semaforoCarroP03_CarroP06_CarroP12_RuaOaRuaEE.release();
          threadCarroP03.andarCarroHorizontal(0, "Esquerda", carroP03); // RUA 25
          threadCarroP03.reiniciarCoordenadas(carroP03);
          break;
        }
        case 6: { // carroP06 (vermelho)
          threadCarroP06.andarCarroVertical(-60, "Cima", carroP06); // rua Z
          threadCarroP06.andarCarroVertical(-103, "Cima", carroP06); // rua Z
          threadCarroP06.girarImagemCarro(-90, carroP06); 
          threadCarroP06.andarCarroHorizontal(-100, "Esquerda", carroP06); // rua 4
          threadCarroP06.andarCarroHorizontal(-240, "Esquerda", carroP06); // rua 3
          semaforoCarroP06eCarroP12Rua1aRua2.acquire();
          semaforoCarroP06eCarroP20Rua2.acquire();
          threadCarroP06.andarCarroHorizontal(-380, "Esquerda", carroP06); // rua 2
          semaforoCarroP06eCarroP14Rua3aRuaZ.release();
          semaforoCarroP06eCarroP23RuaBaRua1.acquire();
          threadCarroP06.andarCarroHorizontal(-520, "Esquerda", carroP06); // rua 1
          semaforoCarroP06eCarroP20Rua2.release();
          semaforoCarroP06eCarroP16Rua2aRuaZ.release();
          threadCarroP06.andarCarroHorizontal(-660, "Esquerda", carroP06); // rua 0
          semaforoCarroP06eCarroP12Rua1aRua2.release();
          semaforoCarroP12_CarroP06_CarroP14_RuaZaRua3.release();
          threadCarroP06.andarCarroHorizontal(-698, "Esquerda", carroP06); // rua 0
          threadCarroP06.girarImagemCarro(-180, carroP06); 
          threadCarroP06.andarCarroVertical(-55, "Baixo", carroP06); // rua a
          threadCarroP06.andarCarroVertical(50, "Baixo", carroP06); // rua b
          semaforoCarroP06eCarroP09RuaC.acquire();
          semaforoCarroP06eCarroP20Rua15aRuaC.acquire();
          threadCarroP06.andarCarroVertical(150, "Baixo", carroP06); // rua c
          semaforoCarroP06eCarroP23RuaBaRua1.release();
          threadCarroP06.andarCarroVertical(195, "Baixo", carroP06); // rua c
          threadCarroP06.girarImagemCarro(-270, carroP06); 
          semaforoCarroP03eCarroP06RuaZaRuaC.release();
          threadCarroP06.andarCarroHorizontal(-605, "Direita", carroP06); // rua 15
          semaforoCarroP03_CarroP06_CarroP14_RuaZaRuaC.release();
          semaforoCarroP06eCarroP09RuaC.release();
          semaforoCarroP06eCarroP23Rua16.acquire();
          skyna15eI_CarroP06eP12.acquire();
          threadCarroP06.andarCarroHorizontal(-470, "Direita", carroP06); // rua 16
          skyna15eI_CarroP06eP12.release();
          semaforoCarroP06eCarroP20Rua15aRuaC.release();
          semaforoCarroP06eCarroP16Rua17aRua18.acquire();
          threadCarroP06.andarCarroHorizontal(-325, "Direita", carroP06); // rua 17
          semaforoCarroP06eCarroP23Rua16.release();
          semaforoCarroP03eCarroP06Rua18aRuaX.acquire();
          skyna17eR_CarroP06P12P14P16.acquire();
          threadCarroP06.andarCarroHorizontal(-180, "Direita", carroP06); // rua 18
          skyna17eR_CarroP06P12P14P16.release();
          semaforoCarroP06eCarroP20RuaXaRua23.acquire();
          skynaWe18_CarroP03P23P06.acquire();
          threadCarroP06.andarCarroHorizontal(-140, "Direita", carroP06); // rua 18
          threadCarroP06.girarImagemCarro(-180, carroP06); 
          threadCarroP06.andarCarroVertical(245, "Baixo", carroP06); // rua x
          skynaWe18_CarroP03P23P06.release();
          semaforoCarroP06eCarroP16Rua17aRua18.release();
          semaforoCarroP06eCarroP09Rua20aRua23.acquire();
          threadCarroP06.andarCarroVertical(293, "Baixo", carroP06); // rua x
          threadCarroP06.girarImagemCarro(-90, carroP06); 
          threadCarroP06.andarCarroHorizontal(-240, "Esquerda", carroP06); // rua 23
          semaforoCarroP03eCarroP06Rua18aRuaX.release();
          semaforoCarroP06eCarroP23Rua22.acquire();
          semaforoCarroP03eCarroP06Rua22.acquire();
          skyna23eS_CarroP06P12P14P09.acquire();
          threadCarroP06.andarCarroHorizontal(-380, "Esquerda", carroP06); // rua 22
          skyna23eS_CarroP06P12P14P09.release();
          semaforoCarroP06eCarroP20RuaXaRua23.release();
          semaforoCarroP06eCarroP20Rua21.acquire();
          threadCarroP06.andarCarroHorizontal(-520, "Esquerda", carroP06); // rua 21
          semaforoCarroP06eCarroP23Rua22.release();
          semaforoCarroP03eCarroP06Rua22.release();
          skyna21eJ_CarroP06P12P09.acquire();
          threadCarroP06.andarCarroHorizontal(-660, "Esquerda", carroP06); // rua 20
          skyna21eJ_CarroP06P12P09.release();
          semaforoCarroP06eCarroP20Rua21.release();
          semaforoCarroP03_CarroP06_CarroP12_RuaOaRuaEE.acquire(); // resolver deadlock
          semaforoCarroP20_CarroP06_RuaEaRua26.acquire(); // resolver deadlock
          semaforoCarroP03eCarroP06Rua26aRuaE.acquire();
          semaforoCarroP06eCarroP20Rua27.acquire();
          threadCarroP06.andarCarroHorizontal(-700, "Esquerda", carroP06); // rua 20
          threadCarroP06.girarImagemCarro(-180, carroP06); 
          threadCarroP06.andarCarroVertical(355, "Baixo", carroP06); // rua E
          semaforoCarroP06eCarroP09Rua20aRua23.release();
          threadCarroP06.andarCarroVertical(395, "Baixo", carroP06); // rua E
          threadCarroP06.girarImagemCarro(-270, carroP06); 
          threadCarroP06.andarCarroHorizontal(-610, "Direita", carroP06); // rua 25
          semaforoCarroP06_CarroP09_CarroP15_Carro914_Rua3aRua28.acquire();// resolver deadlock
          semaforoCarroP06_CarroP23_CarroP20_Rua2aRuaAA.acquire();
          semaforoCarroP06eCarroP12Rua26aRua27.acquire();
          threadCarroP06.andarCarroHorizontal(-470, "Direita", carroP06); // rua 26
          threadCarroP06.andarCarroHorizontal(-330, "Direita", carroP06); // rua 27
          semaforoCarroP03_CarroP06_CarroP12_RuaOaRuaEE.release();
          semaforoCarroP03eCarroP06Rua26aRuaE.release();
          semaforoCarroP20_CarroP06_RuaEaRua26.release(); // resolver deadlock
          semaforoCarroP06_CarroP09_RuaBBaRuaCC.acquire();
          semaforoCarroP06_CarroP23_RuaCCaRua28.acquire(); // resolver deadlock
          semaforoCarroP03eCarroP06Rua28aRuaDD.acquire();
          semaforoCarroP06eCarroP23RuaCCaRua28.acquire();
          semaforoCarroP06eCarroP14RuaBBaRua28.acquire();
          threadCarroP06.andarCarroHorizontal(-190, "Direita", carroP06); // rua 28
          semaforoCarroP06eCarroP20Rua27.release();
          semaforoCarroP06eCarroP12Rua26aRua27.release();
          threadCarroP06.andarCarroHorizontal(-40, "Direita", carroP06); // rua 29
          threadCarroP06.andarCarroHorizontal(0, "Direita", carroP06); // rua 29
          threadCarroP06.girarImagemCarro(-360, carroP06); 
          threadCarroP06.andarCarroVertical(335, "Cima", carroP06); // rua DD
          semaforoCarroP06eCarroP09Rua11aRuaCC.acquire();
          threadCarroP06.andarCarroVertical(235, "Cima", carroP06); // rua CC
          semaforoCarroP03eCarroP06Rua28aRuaDD.release();
          semaforoCarroP03_CarroP06_CarroP20_RuaBBaRua14.acquire(); // resolver deadlock
          semaforoCarroP06eCarroP16RuaBB.acquire();
          semaforoCarroP06eCarroP20Rua14aRuaBB.acquire();
          threadCarroP06.andarCarroVertical(135, "Cima", carroP06); // rua BB
          semaforoCarroP06_CarroP23_RuaCCaRua28.release(); // resolver deadlock
          semaforoCarroP06eCarroP23RuaCCaRua28.release();
          semaforoCarroP03eCarroP06Rua14aRua13.acquire();
          threadCarroP06.andarCarroVertical(95, "Cima", carroP06); // rua BB
          threadCarroP06.girarImagemCarro(-90, carroP06); 
          threadCarroP06.andarCarroHorizontal(-100, "Esquerda", carroP06); // rua 14
          semaforoCarroP06_CarroP09_CarroP15_Carro914_Rua3aRua28.release(); // resolver deadlock
          semaforoCarroP06eCarroP14RuaBBaRua28.release();
          semaforoCarroP06eCarroP16RuaBB.release();
          semaforoCarroP06eCarroP23Rua13.acquire();
          threadCarroP06.andarCarroHorizontal(-240, "Esquerda", carroP06); // rua 13
          semaforoCarroP03_CarroP06_CarroP20_RuaBBaRua14.release(); // resolver deadlock
          semaforoCarroP06_CarroP23_CarroP20_Rua2aRuaAA.release();
          semaforoCarroP06eCarroP20Rua14aRuaBB.release();
          skynaQe13_CarroP06P12P14P09.acquire();
          threadCarroP06.andarCarroHorizontal(-380, "Esquerda", carroP06); // rua 12
          skynaQe13_CarroP06P12P14P09.release();
          semaforoCarroP06eCarroP23Rua13.release();
          semaforoCarroP03eCarroP06Rua14aRua13.release();
          skyna12eL_CarroP06P16P09.acquire();
          threadCarroP06.andarCarroHorizontal(-520, "Esquerda", carroP06); // rua 11
          skyna12eL_CarroP06P16P09.release();
          semaforoCarroP06eCarroP12RuaG.acquire();
          semaforoCarroP06eCarroP20RuaGaRua6.acquire();
          skyna11eH_CarroP06eP23.acquire();
          threadCarroP06.andarCarroHorizontal(-560, "Esquerda", carroP06); // rua 11
          threadCarroP06.girarImagemCarro(-360, carroP06); 
          threadCarroP06.andarCarroVertical(40, "Cima", carroP06); // rua G
          semaforoCarroP06_CarroP09_RuaBBaRuaCC.release();
          skyna11eH_CarroP06eP23.release();
          semaforoCarroP06eCarroP09Rua11aRuaCC.release();
          threadCarroP06.andarCarroVertical(-7, "Cima", carroP06); // rua G
          threadCarroP06.girarImagemCarro(-270, carroP06); 
          threadCarroP06.andarCarroHorizontal(-470, "Direita", carroP06); // rua 6
          semaforoCarroP06eCarroP12RuaG.release();
          semaforoCarroP06eCarroP23Rua7.acquire();
          skyna6eK_CarroP06eP16.acquire();
          threadCarroP06.andarCarroHorizontal(-330, "Direita", carroP06); // rua 7
          skyna6eK_CarroP06eP16.release();
          semaforoCarroP06eCarroP20RuaGaRua6.release();
          semaforoCarroP06eCarroP20Rua8.acquire();
          skynaPe07_CarroP06P12P14.acquire();
          threadCarroP06.andarCarroHorizontal(-190, "Direita", carroP06); // rua 8
          skynaPe07_CarroP06P12P14.release();
          semaforoCarroP06eCarroP23Rua7.release();
          threadCarroP06.andarCarroHorizontal(-50, "Direita", carroP06); // rua 9
          semaforoCarroP06eCarroP20Rua8.release();
          semaforoCarroP03_CarroP06_CarroP14_RuaZaRuaC.acquire(); // resolver deadlock
          semaforoCarroP12_CarroP06_CarroP14_RuaZaRua3.acquire(); // RESOLVER DEADLOCK
          semaforoCarroP06eCarroP16Rua2aRuaZ.acquire();
          semaforoCarroP06eCarroP14Rua3aRuaZ.acquire();
          semaforoCarroP03eCarroP06RuaZaRuaC.acquire();
          threadCarroP06.andarCarroHorizontal(-10, "Direita", carroP06); // rua 9
          threadCarroP06.reiniciarCoordenadas(carroP06);
          break;
        }
        case 9: { // carroP09 (roxo)
          threadCarroP09.andarCarroVertical(50, "Baixo", carroP09); // rua BB
          semaforoCarroP03eCarroP09Rua13Rua14.release();
          semaforoCarroP09eCarroP23RuaCC.acquire();
          threadCarroP09.andarCarroVertical(150, "Baixo", carroP09); // rua CC
          semaforoCarroP09eCarroP20Rua14aRuaBB.release();
          semaforoCarroP09eCarroP16RuaBB.release();
          semaforoCarroP03eCarroP09Rua24.acquire();
          threadCarroP09.andarCarroVertical(197, "Baixo", carroP09); // rua CC
          threadCarroP09.girarImagemCarro(90, carroP09);
          threadCarroP09.andarCarroHorizontal(-100,"Esquerda", carroP09); // rua 24
          semaforoCarroP06_CarroP09_RuaBBaRuaCC.release();
          semaforoCarroP09eCarroP23RuaCC.release();
          semaforoCarroP09eCarroP14RuaBBaRuaCC.release();
          semaforoCarroP06eCarroP09Rua11aRuaCC.release();
          semaforoCarroP06eCarroP09Rua20aRua23.acquire();
          semaforoCarroP09eCarroP20Rua23.acquire();
          threadCarroP09.andarCarroHorizontal(-240,"Esquerda", carroP09); // rua 23
          semaforoCarroP03eCarroP09Rua24.release();
          semaforoCarroP03eCarroP09Rua22.acquire();
          semaforoCarroP09eCarroP23Rua22.acquire();
          skyna23eS_CarroP06P12P14P09.acquire();
          threadCarroP09.andarCarroHorizontal(-380,"Esquerda", carroP09); // rua 22
          skyna23eS_CarroP06P12P14P09.release();
          semaforoCarroP09eCarroP20Rua23.release();
          semaforoCarroP09eCarroP20Rua21.acquire();
          threadCarroP09.andarCarroHorizontal(-520,"Esquerda", carroP09); // rua 21
          semaforoCarroP03eCarroP09Rua22.release();
          semaforoCarroP09eCarroP23Rua22.release();
          skyna21eJ_CarroP06P12P09.acquire();
          threadCarroP09.andarCarroHorizontal(-660,"Esquerda", carroP09); // rua 20
          skyna21eJ_CarroP06P12P09.release();
          semaforoCarroP09eCarroP20Rua21.release();
          semaforoCarroP03eCarroP09RuaDRuaC.acquire();
          threadCarroP09.andarCarroHorizontal(-700,"Esquerda", carroP09); // rua 20
          threadCarroP09.girarImagemCarro(180, carroP09);
          semaforoCarroP06eCarroP09Rua20aRua23.release();
          threadCarroP09.andarCarroVertical(140, "Cima", carroP09); // rua D
          semaforoCarroP06eCarroP09RuaC.acquire();
          semaforoCarroP09eCarroP20RuaCaRua10.acquire();
          semaforoCarroP09eCarroP23Rua10.acquire();
          threadCarroP09.andarCarroVertical(40, "Cima", carroP09); // rua C
          threadCarroP09.andarCarroVertical(-5, "Cima", carroP09); // rua C
          threadCarroP09.girarImagemCarro(270, carroP09);
          threadCarroP09.andarCarroHorizontal(-600,"Direita", carroP09); // rua 10
          semaforoCarroP06eCarroP09RuaC.release();
          semaforoCarroP03eCarroP09RuaDRuaC.release();
          semaforoCarroP06_CarroP09_RuaBBaRuaCC.acquire();
          semaforoCarroP06eCarroP09Rua11aRuaCC.acquire();
          skynaGe11_CarroP09P12.acquire();
          threadCarroP09.andarCarroHorizontal(-460,"Direita", carroP09); // rua 11
          skynaGe11_CarroP09P12.release();
          semaforoCarroP09eCarroP23Rua10.release();
          semaforoCarroP09eCarroP20RuaCaRua10.release();
          skyna12eL_CarroP06P16P09.acquire();
          threadCarroP09.andarCarroHorizontal(-320,"Direita", carroP09); // rua 12
          skyna12eL_CarroP06P16P09.release();
          semaforoCarroP03eCarroP09Rua13Rua14.acquire();
          semaforoCarroP09eCarroP23Rua13.acquire();
          skynaQe13_CarroP06P12P14P09.acquire();
          threadCarroP09.andarCarroHorizontal(-180,"Direita", carroP09); // rua 13
          skynaQe13_CarroP06P12P14P09.release();
          semaforoCarroP09eCarroP20Rua14aRuaBB.acquire();
          threadCarroP09.andarCarroHorizontal(-40,"Direita", carroP09); // rua 14
          semaforoCarroP09eCarroP23Rua13.release();
          semaforoCarroP09eCarroP16RuaBB.acquire();
          semaforoCarroP09eCarroP14RuaBBaRuaCC.acquire();
          threadCarroP09.andarCarroHorizontal(0,"Direita", carroP09); // rua 14
          threadCarroP09.reiniciarCoordenadas(carroP09);
          break;
        }
        case 12: { // carroP12 (ciano)
          threadCarroP12.andarCarroVertical(-40, "Cima", carroP12); // rua J
          semaforoCarroP03eCarroP12Rua26.release();
          semaforoCarroP06eCarroP12Rua26aRua27.release();
          semaforoCarroP12eCarroP20RuaI.acquire();
          skyna21eJ_CarroP06P12P09.acquire();
          threadCarroP12.andarCarroVertical(-140, "Cima", carroP12); // rua I
          skyna21eJ_CarroP06P12P09.release();
          semaforoCarroP12eCarroP23RuaH.acquire();
          skyna15eI_CarroP06eP12.release();
          threadCarroP12.andarCarroVertical(-240, "Cima", carroP12); // rua H
          skyna15eI_CarroP06eP12.release();
          semaforoCarroP12eCarroP20RuaI.release();
          semaforoCarroP06eCarroP12RuaG.acquire();
          semaforoCarroP12eCarroP20RuaG.acquire();
          skynaGe11_CarroP09P12.acquire();
          threadCarroP12.andarCarroVertical(-340, "Cima", carroP12); // rua G
          skynaGe11_CarroP09P12.release();
          semaforoCarroP12eCarroP23RuaH.release();
          threadCarroP12.andarCarroVertical(-440, "Cima", carroP12); // rua F
          semaforoCarroP06eCarroP12RuaG.release();
          semaforoCarroP12eCarroP20RuaG.release();
          semaforoCarroP12_CarroP06_CarroP14_RuaZaRua3.acquire(); // RESOLVER DEALOCK
          semaforoCarroP03eCarroP12Rua1Rua12.acquire();
          semaforoCarroP06eCarroP12Rua1aRua2.acquire();
          semaforoCarroP12eCarroP23Rua1.acquire();
          threadCarroP12.andarCarroVertical(-490, "Cima", carroP12); // rua F
          threadCarroP12.girarImagemCarro(90, carroP12);
          threadCarroP12.andarCarroHorizontal(100, "Direita", carroP12); // rua 1
          semaforoCarroP12eCarroP20Rua2aRuaP.acquire();
          semaforoCarroP12eCarroP14RuaTaRuaP.acquire();
          semaforoCarroP12eCarroP16Rua2.acquire();
          threadCarroP12.andarCarroHorizontal(240, "Direita", carroP12); // rua 2
          semaforoCarroP12eCarroP23Rua1.release();
          threadCarroP12.andarCarroHorizontal(280, "Direita", carroP12); // rua 2
          threadCarroP12.girarImagemCarro(180, carroP12);
          threadCarroP12.andarCarroVertical(-440, "Baixo", carroP12); // rua P
          semaforoCarroP12_CarroP06_CarroP14_RuaZaRua3.release(); // RESOLVER DEADLOCK
          semaforoCarroP12eCarroP16Rua2.release();
          semaforoCarroP06eCarroP12Rua1aRua2.release();
          semaforoCarroP03eCarroP12Rua1Rua12.release();
          semaforoCarroP12eCarroP23RuaQ.acquire();
          skynaPe07_CarroP06P12P14.acquire();
          threadCarroP12.andarCarroVertical(-340, "Baixo", carroP12); // rua Q
          skynaPe07_CarroP06P12P14.release();
          semaforoCarroP12eCarroP20Rua2aRuaP.release();
          semaforoCarroP03eCarroP12RuaR.acquire();
          skynaQe13_CarroP06P12P14P09.acquire();
          threadCarroP12.andarCarroVertical(-240, "Baixo", carroP12); // rua R
          skynaQe13_CarroP06P12P14P09.release();
          semaforoCarroP12eCarroP23RuaQ.release();
          skyna17eR_CarroP06P12P14P16.acquire();
          threadCarroP12.andarCarroVertical(-140, "Baixo", carroP12); // rua S
          skyna17eR_CarroP06P12P14P16.release();
          semaforoCarroP03eCarroP12RuaR.release();
          semaforoCarroP12eCarroP20RuaTaRua27.acquire();
          semaforoCarroP12eCarroP23RuaT.acquire();
          semaforoCarroP03eCarroP12RuaT.acquire();
           skyna23eS_CarroP06P12P14P09.acquire();
          threadCarroP12.andarCarroVertical(-40, "Baixo", carroP12); // rua T
           skyna23eS_CarroP06P12P14P09.release();
          semaforoCarroP06eCarroP12Rua26aRua27.acquire();
          threadCarroP12.andarCarroVertical(10, "Baixo", carroP12); // rua T
          threadCarroP12.girarImagemCarro(270, carroP12);
          threadCarroP12.andarCarroHorizontal(190, "Esquerda", carroP12); // rua 27
          semaforoCarroP12eCarroP23RuaT.release();
          semaforoCarroP12eCarroP14RuaTaRuaP.release();
          semaforoCarroP03eCarroP12RuaT.release();
          semaforoCarroP03eCarroP12Rua26.acquire();
          threadCarroP12.andarCarroHorizontal(50, "Esquerda", carroP12); // rua 26
          semaforoCarroP12eCarroP20RuaTaRua27.release();
          threadCarroP12.andarCarroHorizontal(0, "Esquerda", carroP12); // rua 26
          threadCarroP12.reiniciarCoordenadas(carroP12);
          break;
        }
        case 16: { // carroP16 (rosa)
          threadCarroP16.andarCarroVertical(110, "Baixo", carroP16); // rua L
          skyna6eK_CarroP06eP16.release();
          semaforoCarroP16eCarroP23RuaK.release();
          semaforoCarroP16eCarroP20RuaKaRua2.release();
          skyna12eL_CarroP06P16P09.acquire();
          threadCarroP16.andarCarroVertical(210, "Baixo", carroP16); // rua M
          skyna12eL_CarroP06P16P09.release();
          semaforoCarroP06eCarroP16Rua17aRua18.acquire();
          skynaNeM_CarroP16P23.acquire();
          threadCarroP16.andarCarroVertical(250, "Baixo", carroP16); // rua M
          threadCarroP16.girarImagemCarro(-90, carroP16);
          threadCarroP16.andarCarroHorizontal(100,"Direita", carroP16); // rua 17
          skynaNeM_CarroP16P23.release();
          semaforoCarroP03eCarroP16Rua18.acquire();
          skyna17eR_CarroP06P12P14P16.acquire();
          threadCarroP16.andarCarroHorizontal(240,"Direita", carroP16); // rua 18
          skyna17eR_CarroP06P12P14P16.release();
          semaforoCarroP16eCarroP20RuaBBaRua19.acquire();
          semaforoCarroP16eCarroP23Rua19.acquire();
          threadCarroP16.andarCarroHorizontal(380,"Direita", carroP16); // rua 19
          semaforoCarroP06eCarroP16Rua17aRua18.release();
          semaforoCarroP03eCarroP16Rua18.release();
          semaforoCarroP16_CarroP14_CarroP20_RuaBBaRua3.acquire(); // RESOLVER DEADLOCK
          semaforoCarroP06eCarroP16RuaBB.acquire();
          semaforoCarroP16eCarroP14Rua3aRuaBB.acquire(); 
          semaforoCarroP03eCarroP16Rua2aRuaAA.acquire();
          semaforoCarroP09eCarroP16RuaBB.acquire();
          threadCarroP16.andarCarroHorizontal(420,"Direita", carroP16); // rua 19
          threadCarroP16.girarImagemCarro(-180, carroP16);
          threadCarroP16.andarCarroVertical(200, "Cima", carroP16); // rua BB
          semaforoCarroP16eCarroP23Rua19.release();
          threadCarroP16.andarCarroVertical(100, "Cima", carroP16); // rua AA
          semaforoCarroP16eCarroP20RuaBBaRua19.release();
          semaforoCarroP09eCarroP16RuaBB.release();
          semaforoCarroP06eCarroP16RuaBB.release();
           semaforoCarroP06eCarroP16Rua2aRuaZ.acquire();
          threadCarroP16.andarCarroVertical(0, "Cima", carroP16); // rua Z
          threadCarroP16.andarCarroVertical(-50, "Cima", carroP16); // rua Z
          threadCarroP16.girarImagemCarro(-270, carroP16);
          threadCarroP16.andarCarroHorizontal(320,"Esquerda", carroP16); // rua 4
          threadCarroP16.andarCarroHorizontal(180,"Esquerda", carroP16); // rua 3
          semaforoCarroP16eCarroP20RuaKaRua2.acquire();
          semaforoCarroP12eCarroP16Rua2.acquire();
          threadCarroP16.andarCarroHorizontal(40,"Esquerda", carroP16); // rua 2
          semaforoCarroP16_CarroP14_CarroP20_RuaBBaRua3.release();
          semaforoCarroP16eCarroP14Rua3aRuaBB.release();
          semaforoCarroP16eCarroP23RuaK.acquire();
          threadCarroP16.andarCarroHorizontal(0,"Esquerda", carroP16); // rua 2
          threadCarroP16.girarImagemCarro(-360, carroP16);
          threadCarroP16.andarCarroVertical(0, "Baixo", carroP16); // rua K
          semaforoCarroP12eCarroP16Rua2.release();
          semaforoCarroP06eCarroP16Rua2aRuaZ.release();
          semaforoCarroP03eCarroP16Rua2aRuaAA.release();
          threadCarroP16.reiniciarCoordenadas(carroP16);
          skyna6eK_CarroP06eP16.acquire();
          break;
        }
        case 14: { // carroP14 (verde)
          threadCarroP14.andarCarroVertical(-110,"Cima", carroP14); // rua R
          skyna17eR_CarroP06P12P14P16.release();
          skynaQe13_CarroP06P12P14P09.acquire();
          threadCarroP14.andarCarroVertical(-210,"Cima", carroP14); // rua Q
          skynaQe13_CarroP06P12P14P09.release();
          semaforoCarroP03eCarroP14RuaR.release();
          semaforoCarroP20_CarroP14_RuaBBaRua28.acquire(); // resolver deadlock
          semaforoCarroP14eCarroP20RuaP.acquire();
          skynaPe07_CarroP06P12P14.acquire();
          threadCarroP14.andarCarroVertical(-310,"Cima", carroP14); // rua P
          skynaPe07_CarroP06P12P14.release();
          semaforoCarroP14eCarroP23RuaQ.release();
          semaforoCarroP06_CarroP09_CarroP15_Carro914_Rua3aRua28.acquire(); // resolver deadlock
          semaforoCarroP03eCarroP14Rua3eRuaAA.acquire();
          semaforoCarroP06eCarroP14Rua3aRuaZ.acquire();
          semaforoCarroP16eCarroP14Rua3aRuaBB.acquire(); 
          threadCarroP14.andarCarroVertical(-355,"Cima", carroP14); // rua P
          threadCarroP14.girarImagemCarro(90, carroP14);
          threadCarroP14.andarCarroHorizontal(100,"Direita", carroP14); // rua 3
          semaforoCarroP12eCarroP14RuaTaRuaP.release();
          semaforoCarroP14eCarroP20RuaP.release();
          threadCarroP14.andarCarroHorizontal(240,"Direita", carroP14); // rua 4
          threadCarroP14.andarCarroHorizontal(280,"Direita", carroP14); // rua 4
          threadCarroP14.girarImagemCarro(180, carroP14);
          threadCarroP14.andarCarroVertical(-300,"Baixo", carroP14); // rua Z
          threadCarroP14.andarCarroVertical(-200,"Baixo", carroP14); // rua AA
          semaforoCarroP06eCarroP14Rua3aRuaZ.release();
          semaforoCarroP09eCarroP14RuaBBaRuaCC.acquire();
          semaforoCarroP06eCarroP14RuaBBaRua28.acquire();
          semaforoCarroP14eCarroP20RuaBB.acquire();
          threadCarroP14.andarCarroVertical(-100,"Baixo", carroP14); // rua BB
          semaforoCarroP03eCarroP14Rua3eRuaAA.release();
          semaforoCarroP14eCarroP23RuaCCaRuaT.acquire();
          threadCarroP14.andarCarroVertical(0,"Baixo", carroP14); // rua CC
          semaforoCarroP16eCarroP14Rua3aRuaBB.release();
          semaforoCarroP14eCarroP20RuaBB.release();
          semaforoCarroP03eCarroP14RuaDDaRuaT.acquire();
          threadCarroP14.andarCarroVertical(100,"Baixo", carroP14); // rua DD
          semaforoCarroP09eCarroP14RuaBBaRuaCC.release();
          threadCarroP14.andarCarroVertical(145,"Baixo", carroP14); // rua DD
          threadCarroP14.girarImagemCarro(270, carroP14);
          threadCarroP14.andarCarroHorizontal(180,"Esquerda", carroP14); // rua 29
          threadCarroP14.andarCarroHorizontal(40,"Esquerda", carroP14); // rua 28
          semaforoCarroP12eCarroP14RuaTaRuaP.acquire();
          semaforoCarroP14eCarroP20RuaT.acquire();
          threadCarroP14.andarCarroHorizontal(0,"Esquerda", carroP14); // rua 28
          threadCarroP14.girarImagemCarro(360, carroP14);
          threadCarroP14.andarCarroVertical(100,"Cima", carroP14); // rua T
          semaforoCarroP20_CarroP14_RuaBBaRua28.release();
          semaforoCarroP06eCarroP14RuaBBaRua28.release();
          semaforoCarroP06_CarroP09_CarroP15_Carro914_Rua3aRua28.release();
          skyna23eS_CarroP06P12P14P09.acquire();
          threadCarroP14.andarCarroVertical(0,"Cima", carroP14); // rua S
          skyna23eS_CarroP06P12P14P09.release();
          semaforoCarroP14eCarroP23RuaCCaRuaT.release();
          semaforoCarroP03eCarroP14RuaDDaRuaT.release();
          semaforoCarroP14eCarroP20RuaT.release();
          threadCarroP14.reiniciarCoordenadas(carroP14);
          semaforoCarroP14eCarroP23RuaQ.acquire();
          semaforoCarroP03eCarroP14RuaR.acquire();
          skyna17eR_CarroP06P12P14P16.acquire();
          break;
        }
        case 23: { // carroP23 (amarelo)
          threadCarroP23.andarCarroHorizontal(-90,"Esquerda", carroP23); // rua 29
          threadCarroP23.andarCarroHorizontal(-230,"Esquerda", carroP23); // rua 28
          semaforoCarroP12eCarroP23RuaT.acquire();
          semaforoCarroP23eCarroP20RuaT.acquire();
          threadCarroP23.andarCarroHorizontal(-273,"Esquerda", carroP23); // rua 28
          threadCarroP23.girarImagemCarro(90, carroP23);
          threadCarroP23.andarCarroVertical(-53,"Cima", carroP23); // rua T
          semaforoCarroP06_CarroP23_RuaCCaRua28.release();
          semaforoCarroP06eCarroP23RuaCCaRua28.release();
          semaforoCarroP06eCarroP23Rua22.acquire();
          semaforoCarroP09eCarroP23Rua22.acquire();
          threadCarroP23.andarCarroVertical(-97,"Cima", carroP23); // rua T
          threadCarroP23.girarImagemCarro(360, carroP23);
          threadCarroP23.andarCarroHorizontal(-373,"Esquerda", carroP23); // rua 22
          semaforoCarroP20_CarroP23_RuaCCaRua28.release(); // resolver deadlock
          semaforoCarroP12eCarroP23RuaT.release();
          semaforoCarroP14eCarroP23RuaCCaRuaT.release();
          semaforoCarroP23eCarroP20RuaT.release();
          skyna22eO_CarroP23P20.acquire();
          threadCarroP23.andarCarroHorizontal(-413,"Esquerda", carroP23); // rua 22
          threadCarroP23.girarImagemCarro(90, carroP23);
          threadCarroP23.andarCarroVertical(-147,"Cima", carroP23); // rua N
          skyna22eO_CarroP23P20.release();
          semaforoCarroP03eCarroP23RuaDDaRua22.release();
          semaforoCarroP09eCarroP23Rua22.release();
          semaforoCarroP06eCarroP23Rua22.release();
          semaforoCarroP06eCarroP23Rua16.acquire();
          skynaNeM_CarroP16P23.acquire();
          threadCarroP23.andarCarroVertical(-197,"Cima", carroP23); // rua N
          threadCarroP23.girarImagemCarro(360, carroP23);
          threadCarroP23.andarCarroHorizontal(-513,"Esquerda", carroP23); // rua 16
          skynaNeM_CarroP16P23.release();
          semaforoCarroP12eCarroP23RuaH.acquire();
          skyna16eI_CarroP23P20.acquire();
          threadCarroP23.andarCarroHorizontal(-553,"Esquerda", carroP23); // rua 16
          threadCarroP23.girarImagemCarro(90, carroP23);
          threadCarroP23.andarCarroVertical(-257,"Cima", carroP23); // rua H
          skyna16eI_CarroP23P20.release();
          semaforoCarroP06eCarroP23Rua16.release();
          semaforoCarroP09eCarroP23Rua10.acquire();
          semaforoCarroP06eCarroP23RuaBaRua1.acquire();
          semaforoCarroP23eCarroP20Rua10.acquire();
          skyna11eH_CarroP06eP23.acquire();
          threadCarroP23.andarCarroVertical(-297,"Cima", carroP23); // rua H
          threadCarroP23.girarImagemCarro(360, carroP23);
          threadCarroP23.andarCarroHorizontal(-653,"Esquerda", carroP23); // rua 10
          skyna11eH_CarroP06eP23.release();
          semaforoCarroP12eCarroP23RuaH.release();
          semaforoCarroP03eCarroP23RuaBaRua1.acquire();
          threadCarroP23.andarCarroHorizontal(-693,"Esquerda", carroP23); // rua 10
          threadCarroP23.girarImagemCarro(90, carroP23);
          threadCarroP23.andarCarroVertical(-357,"Cima", carroP23); // rua B
          semaforoCarroP09eCarroP23Rua10.release();
          semaforoCarroP23eCarroP20Rua10.release();
          threadCarroP23.andarCarroVertical(-457,"Cima", carroP23); // rua A
          threadCarroP23.andarCarroVertical(-497,"Cima", carroP23); // rua A
          threadCarroP23.girarImagemCarro(180, carroP23);
          threadCarroP23.andarCarroHorizontal(-593,"Direita", carroP23); // rua 0
          semaforoCarroP12eCarroP23Rua1.acquire();
          threadCarroP23.andarCarroHorizontal(-453,"Direita", carroP23); // rua 1
          semaforoCarroP16eCarroP23RuaK.acquire();
          semaforoCarroP23eCarroP20RuaK.acquire();
          threadCarroP23.andarCarroHorizontal(-413,"Direita", carroP23); // rua 1
          threadCarroP23.girarImagemCarro(270, carroP23);
          threadCarroP23.andarCarroVertical(-437,"Baixo", carroP23); // rua K
          semaforoCarroP12eCarroP23Rua1.release();
          semaforoCarroP03eCarroP23RuaBaRua1.release();
          semaforoCarroP06eCarroP23RuaBaRua1.release();
          semaforoCarroP06eCarroP23Rua7.acquire();
          threadCarroP23.andarCarroVertical(-397,"Baixo", carroP23); // rua K
          threadCarroP23.girarImagemCarro(180, carroP23);
          threadCarroP23.andarCarroHorizontal(-313,"Direita", carroP23); // rua 7
          semaforoCarroP16eCarroP23RuaK.release();
          semaforoCarroP23eCarroP20RuaK.release();
          semaforoCarroP12eCarroP23RuaQ.acquire();
          semaforoCarroP14eCarroP23RuaQ.acquire();
          skynaPe7_CarroP23P20.acquire();
          threadCarroP23.andarCarroHorizontal(-273,"Direita", carroP23); // rua 7
          threadCarroP23.girarImagemCarro(270, carroP23);
          threadCarroP23.andarCarroVertical(-337,"Baixo", carroP23); // rua Q
          skynaPe7_CarroP23P20.release();
          semaforoCarroP06eCarroP23Rua7.release();
          semaforoCarroP03eCarroP23Rua13.acquire();
          semaforoCarroP06eCarroP23Rua13.acquire();
          semaforoCarroP09eCarroP23Rua13.acquire();
          threadCarroP23.andarCarroVertical(-297,"Baixo", carroP23); // rua Q
          threadCarroP23.girarImagemCarro(180, carroP23);
          threadCarroP23.andarCarroHorizontal(-173,"Direita", carroP23); // rua 13
          semaforoCarroP12eCarroP23RuaQ.release();
          semaforoCarroP14eCarroP23RuaQ.release();
          skynaVe13_CarroP23P20.acquire();
          threadCarroP23.andarCarroHorizontal(-133,"Direita", carroP23); // rua 13
          threadCarroP23.girarImagemCarro(270, carroP23);
          threadCarroP23.andarCarroVertical(-237,"Baixo", carroP23); // rua W
          skynaVe13_CarroP23P20.release();
          semaforoCarroP03eCarroP23Rua13.release();
          semaforoCarroP06eCarroP23Rua13.release();
          semaforoCarroP09eCarroP23Rua13.release();
          semaforoCarroP16eCarroP23Rua19.acquire();
          semaforoCarroP23eCarroP20Rua19.acquire();
          skynaWe18_CarroP03P23P06.acquire();
          threadCarroP23.andarCarroVertical(-197,"Baixo", carroP23); // rua W
          threadCarroP23.girarImagemCarro(180, carroP23);
          threadCarroP23.andarCarroHorizontal(-33,"Direita", carroP23); // rua 19
          skynaWe18_CarroP03P23P06.release();
          semaforoCarroP06_CarroP23_RuaCCaRua28.acquire(); // resolver deadlock
          semaforoCarroP20_CarroP23_RuaCCaRua28.acquire(); // resolver deadlock
          semaforoCarroP06eCarroP23RuaCCaRua28.acquire();
          semaforoCarroP09eCarroP23RuaCC.acquire();
          semaforoCarroP14eCarroP23RuaCCaRuaT.acquire();
          threadCarroP23.andarCarroHorizontal(7,"Direita", carroP23); // rua 19
          threadCarroP23.girarImagemCarro(270, carroP23);
          threadCarroP23.andarCarroVertical(-147,"Baixo", carroP23); // rua CC
          semaforoCarroP16eCarroP23Rua19.release();
          semaforoCarroP23eCarroP20Rua19.release();
          semaforoCarroP03eCarroP23RuaDDaRua22.acquire();
          threadCarroP23.andarCarroVertical(-47,"Baixo", carroP23); // rua DD
          semaforoCarroP09eCarroP23RuaCC.release();
          threadCarroP23.andarCarroVertical(0,"Baixo", carroP23); // rua DD
          threadCarroP23.reiniciarCoordenadas(carroP23);
          break;
        }
        case 20: { // carroP20 (laranja)
          threadCarroP20.andarCarroVertical(40, "Baixo", carroP20); // rua X
          semaforoCarroP16eCarroP20RuaBBaRua19.release();
          semaforoCarroP23eCarroP20Rua19.release();
          semaforoCarroP09eCarroP20Rua23.acquire();
          threadCarroP20.andarCarroVertical(90, "Baixo", carroP20); // rua X
          threadCarroP20.girarImagemCarro(90, carroP20);
          threadCarroP20.andarCarroHorizontal(-100, "Esquerda", carroP20); // rua 23
          semaforoCarroP03eCarroP20RuaX.release();
          semaforoCarroP20_CarroP23_RuaCCaRua28.acquire(); // resolver deadlock
          semaforoCarroP23eCarroP20RuaT.acquire();
          semaforoCarroP12eCarroP20RuaTaRua27.acquire();
          semaforoCarroP14eCarroP20RuaT.acquire();
          semaforoCarroP06eCarroP20Rua27.acquire(); 
          semaforoCarroP03eCarroP20RuaT.acquire();
          threadCarroP20.andarCarroHorizontal(-140, "Esquerda", carroP20); // rua 23
          threadCarroP20.girarImagemCarro(360, carroP20);
          threadCarroP20.andarCarroVertical(140, "Baixo", carroP20); // rua T
          semaforoCarroP09eCarroP20Rua23.release();
          semaforoCarroP06eCarroP20RuaXaRua23.release();
          threadCarroP20.andarCarroVertical(190, "Baixo", carroP20); // rua T
          threadCarroP20.girarImagemCarro(90, carroP20);
          threadCarroP20.andarCarroHorizontal(-240, "Esquerda", carroP20); // rua 27
          semaforoCarroP20_CarroP23_RuaCCaRua28.release(); // resolver deadlock
          semaforoCarroP03eCarroP20RuaT.release();
          semaforoCarroP14eCarroP20RuaT.release();
          semaforoCarroP23eCarroP20RuaT.release();
          semaforoCarroP03eCarroP20RuaO.acquire();
          threadCarroP20.andarCarroHorizontal(-280, "Esquerda", carroP20); // rua 27
          threadCarroP20.girarImagemCarro(180, carroP20); 
          threadCarroP20.andarCarroVertical(140, "Cima", carroP20); // rua O
          semaforoCarroP20_CarroP06_RuaEaRua26.release(); // resolver deadlock
          semaforoCarroP20_CarroP14_RuaBBaRua28.release();
          semaforoCarroP06eCarroP20Rua27.release();
          semaforoCarroP12eCarroP20RuaTaRua27.release();
          semaforoCarroP06eCarroP20Rua21.acquire();
          semaforoCarroP09eCarroP20Rua21.acquire();
          skyna22eO_CarroP23P20.acquire();
          threadCarroP20.andarCarroVertical(90, "Cima", carroP20); // rua O
          threadCarroP20.girarImagemCarro(90, carroP20);
          threadCarroP20.andarCarroHorizontal(-375, "Esquerda", carroP20); // rua 21
          skyna22eO_CarroP23P20.release();
          semaforoCarroP03eCarroP20RuaO.release();
          semaforoCarroP12eCarroP20RuaI.acquire();
          threadCarroP20.andarCarroHorizontal(-420, "Esquerda", carroP20); // rua 21
          threadCarroP20.girarImagemCarro(180, carroP20); 
          threadCarroP20.andarCarroVertical(30, "Cima", carroP20); // rua I
          semaforoCarroP06eCarroP20Rua21.release();
          semaforoCarroP09eCarroP20Rua21.release();
          semaforoCarroP06eCarroP20Rua15aRuaC.acquire();
          skyna16eI_CarroP23P20.acquire();
          threadCarroP20.andarCarroVertical(-10, "Cima", carroP20); // rua I
          threadCarroP20.girarImagemCarro(90, carroP20);
          threadCarroP20.andarCarroHorizontal(-520, "Esquerda", carroP20); // rua 15
          skyna16eI_CarroP23P20.release();
          semaforoCarroP12eCarroP20RuaI.release();
          semaforoCarroP03eCarroP20RuaC.acquire();
          semaforoCarroP09eCarroP20RuaCaRua10.acquire();
          threadCarroP20.andarCarroHorizontal(-560, "Esquerda", carroP20); // rua 15
          threadCarroP20.girarImagemCarro(180, carroP20); 
          threadCarroP20.andarCarroVertical(-70, "Cima", carroP20); // rua C
          semaforoCarroP23eCarroP20Rua10.acquire();
          threadCarroP20.andarCarroVertical(-110, "Cima", carroP20); // rua C
          threadCarroP20.girarImagemCarro(270, carroP20); 
          threadCarroP20.andarCarroHorizontal(-460, "Direita", carroP20); // rua 10
          semaforoCarroP03eCarroP20RuaC.release();
          semaforoCarroP06eCarroP20Rua15aRuaC.release();
          semaforoCarroP12eCarroP20RuaG.acquire();
          semaforoCarroP06eCarroP20RuaGaRua6.acquire();
          threadCarroP20.andarCarroHorizontal(-420, "Direita", carroP20); // rua 10
          threadCarroP20.girarImagemCarro(180, carroP20); 
          threadCarroP20.andarCarroVertical(-160, "Cima", carroP20); // rua G
          semaforoCarroP09eCarroP20RuaCaRua10.release();
          semaforoCarroP23eCarroP20Rua10.release();
          threadCarroP20.andarCarroVertical(-210, "Cima", carroP20); // rua G
          threadCarroP20.girarImagemCarro(270, carroP20); 
          threadCarroP20.andarCarroHorizontal(-320, "Direita", carroP20); // rua 6
          semaforoCarroP12eCarroP20RuaG.release();
          semaforoCarroP16_CarroP14_CarroP20_RuaBBaRua3.acquire();
          semaforoCarroP16eCarroP20RuaKaRua2.acquire();
          semaforoCarroP23eCarroP20RuaK.acquire();
          threadCarroP20.andarCarroHorizontal(-280, "Direita", carroP20); // rua 6
          threadCarroP20.girarImagemCarro(180, carroP20); 
          threadCarroP20.andarCarroVertical(-260, "Cima", carroP20); // rua K
          semaforoCarroP06eCarroP20RuaGaRua6.release();
          semaforoCarroP03eCarroP20Rua2.acquire();
          semaforoCarroP06eCarroP20Rua2.acquire();
          semaforoCarroP12eCarroP20Rua2aRuaP.acquire();
          threadCarroP20.andarCarroVertical(-310, "Cima", carroP20); // rua K
          threadCarroP20.girarImagemCarro(270, carroP20); 
          threadCarroP20.andarCarroHorizontal(-180, "Direita", carroP20); // rua 2
          semaforoCarroP23eCarroP20RuaK.release();
          semaforoCarroP14eCarroP20RuaP.acquire();
          threadCarroP20.andarCarroHorizontal(-140, "Direita", carroP20); // rua 2
          threadCarroP20.girarImagemCarro(360, carroP20); 
          threadCarroP20.andarCarroVertical(-260, "Baixo", carroP20); // rua P
          semaforoCarroP16_CarroP14_CarroP20_RuaBBaRua3.release(); // RESOLVER DEADLOCK
          semaforoCarroP16eCarroP20RuaKaRua2.release();
          semaforoCarroP03eCarroP20Rua2.release();
          semaforoCarroP06eCarroP20Rua2.release();
          semaforoCarroP06eCarroP20Rua8.acquire();
          skynaPe7_CarroP23P20.acquire();
          threadCarroP20.andarCarroVertical(-210, "Baixo", carroP20); // rua P
          threadCarroP20.girarImagemCarro(270, carroP20); 
          threadCarroP20.andarCarroHorizontal(-40, "Direita", carroP20); // rua 8
          skynaPe7_CarroP23P20.release();
          semaforoCarroP12eCarroP20Rua2aRuaP.release();
          semaforoCarroP14eCarroP20RuaP.release();
          threadCarroP20.andarCarroHorizontal(0, "Direita", carroP20); // rua 8
          threadCarroP20.girarImagemCarro(360, carroP20); 
          threadCarroP20.andarCarroVertical(-160, "Baixo", carroP20); // rua V
          semaforoCarroP06eCarroP20Rua8.release();
          semaforoCarroP20_CarroP06_RuaEaRua26.acquire(); // resolver deadlock 
          semaforoCarroP16_CarroP20_CarroP03_Rua2aRuaAA.acquire(); // resolver deadlock
          semaforoCarroP06_CarroP23_CarroP20_Rua2aRuaAA.acquire(); // resolver deadlock
          semaforoCarroP03_CarroP06_CarroP20_RuaBBaRua14.acquire(); // resolver deadlock
          semaforoCarroP20_CarroP14_RuaBBaRua28.acquire(); // resolver deadlock
          semaforoCarroP03eCarroP20Rua14.acquire();
          semaforoCarroP06eCarroP20Rua14aRuaBB.acquire();
          semaforoCarroP09eCarroP20Rua14aRuaBB.acquire();
          skynaVe13_CarroP23P20.acquire();
          threadCarroP20.andarCarroVertical(-110, "Baixo", carroP20); // rua V
          threadCarroP20.girarImagemCarro(270, carroP20); 
          threadCarroP20.andarCarroHorizontal(100, "Direita", carroP20); // rua 14
          skynaVe13_CarroP23P20.release();
          semaforoCarroP16eCarroP20RuaBBaRua19.acquire();
          semaforoCarroP14eCarroP20RuaBB.acquire();
          threadCarroP20.andarCarroHorizontal(140, "Direita", carroP20); // rua 14
          threadCarroP20.girarImagemCarro(360, carroP20); 
          threadCarroP20.andarCarroVertical(-60, "Baixo", carroP20); // rua BB
          semaforoCarroP16_CarroP20_CarroP03_Rua2aRuaAA.release();
          semaforoCarroP03eCarroP20Rua14.release();
          semaforoCarroP23eCarroP20Rua19.acquire();
          threadCarroP20.andarCarroVertical(-10, "Baixo", carroP20); // rua BB
          threadCarroP20.girarImagemCarro(90, carroP20); 
          threadCarroP20.andarCarroHorizontal(40, "Esquerda", carroP20); // rua 19
          semaforoCarroP03_CarroP06_CarroP20_RuaBBaRua14.release();
          semaforoCarroP06_CarroP23_CarroP20_Rua2aRuaAA.release();
          semaforoCarroP14eCarroP20RuaBB.release();
          semaforoCarroP06eCarroP20Rua14aRuaBB.release();
          semaforoCarroP09eCarroP20Rua14aRuaBB.release();
          semaforoCarroP06eCarroP20RuaXaRua23.acquire();
          semaforoCarroP03eCarroP20RuaX.acquire();
          threadCarroP20.andarCarroHorizontal(0, "Esquerda", carroP20); // rua 19
          threadCarroP20.reiniciarCoordenadas(carroP20);
          break;
        }
      } // fim do switch
    } // fim do metodo carroAndar

} // fim da classe
