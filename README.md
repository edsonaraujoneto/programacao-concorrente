# Sobre 🔍

Aqui estão todos os trabalhos realizados na disciplina de **Programação concorrente** ministrada pelo professor **Marlos Marques** no III semestre do curso de Ciência da Computação na Universidade Estadual do Sudoeste da Bahia (UESB) em Vitória da Conquista, Bahia.

Todos os códigos aqui postados são de autoria de @edsonaraujoneto

---
# Trabalhos 🖥

   ## 1. Simulação de trens com colisão 🚂

   Implementar simulação de dois trens em percurso simples e duplo.
   Cada trem será uma thread.
   Há dois recursos compartilhados no qual é permitido apenas um trem por vez em cada percurso, para dessa forma, evitar batidas.
   Nota: [20/100]
   [Código disponível aqui](https://github.com/edsonaraujoneto/programacao-concorrente/tree/master/simulacao-de-trens)

   ## 2. Jantar dos Filósofos 🍽

   O jantar dos filósofos é um dos problemas clássicos de programação concorrente. Cinco filósofos com suas ações de pensar e comer estão sentados em uma mesa com cinco garfos. Para 
   comer, cada filósofo deve pegar o garfo da esquerda e direita. Porém devemos ter cuidado para que todos possam comer, e evitar starvation.
   Nota: [100/100]
   [Código disponível aqui](https://github.com/edsonaraujoneto/programacao-concorrente/tree/master/jantar-dos-filosofos)

   ## 3. Leitores/Escritores 📚
   Mais um problema clássico, no qual leitores e escritores simula uma situação real de acesso ao banco de dados. Pode ter vários leitores lendo um dado, porém apenas um escrevendo, e       caso há algum escritor escrevendo, nenhum leitor pode ler.
   Nota: [10/100]
   [Código disponível aqui](https://github.com/edsonaraujoneto/programacao-concorrente/tree/master/leitores-escritores)

   ## 4. Trânsito Autômato 🚙
   Trabalho desenvolvido pelo professor Marlos, no qual há oito carros, cada um representado por uma thread e com percursos distintos. Nosso objetivo é evitar batidas utilizando             semáforos, porém o real desafio é evitar deadlocks (quando um processo é evitado de executar pois aguarda uma ação de outro processo que não será executado).
   Nota: [100/100]
   [Código disponível aqui](https://github.com/edsonaraujoneto/programacao-concorrente/tree/master/transito-automato)










   




