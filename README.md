# proyectoCI2691
Proyecto para la asignatura de CI2691 - Laboratorio de Algoritmos y Estructuras 1.

Juego de Blackjack en Java

**Hecho por:**
*Juan Cuevas - Miguel Salomon*

## Informacion sobre el proyecto
Se verifico con prueba estatica usando el comando:

`openjml --esc --exclude 'main,jugadorNoTieneBlackJack' -cp ./lib/maquinaTrazados-v0.1.jar Blackjack.java`

Despues se realizo la prueba dinamica con el comando:

`openjml --rac Blackjack.java`

## Probar con la libreria grafica
Para compilar el proyecto se uso el comando
`openjml --compile -cp ./lib/maquinaTrazados-v0.1.jar Blackjack.java`

Despues se ejecuto con el comando
`openjml-java -cp ./lib/maquinaTrazados-v0.1.jar:. Blackjack`