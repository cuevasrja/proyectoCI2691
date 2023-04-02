// * Scanner para lectura de datos
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.Console;

// ! Libreria para interfaz grafica
import java.awt.Font;

public class Blackjack{
    enum Carta {
        CORAZONES_A, PICAS_A, DIAMANTES_A, TREBOL_A, 
        CORAZONES_2, PICAS_2, DIAMANTES_2, TREBOL_2,
        CORAZONES_3, PICAS_3, DIAMANTES_3, TREBOL_3,
        CORAZONES_4, PICAS_4, DIAMANTES_4, TREBOL_4,
        CORAZONES_5, PICAS_5, DIAMANTES_5, TREBOL_5,
        CORAZONES_6, PICAS_6, DIAMANTES_6, TREBOL_6,
        CORAZONES_7, PICAS_7, DIAMANTES_7, TREBOL_7,
        CORAZONES_8, PICAS_8, DIAMANTES_8, TREBOL_8,
        CORAZONES_9, PICAS_9, DIAMANTES_9, TREBOL_9,
        CORAZONES_10, PICAS_10, DIAMANTES_10, TREBOL_10,
        CORAZONES_J, PICAS_J, DIAMANTES_J, TREBOL_J,
        CORAZONES_Q, PICAS_Q, DIAMANTES_Q, TREBOL_Q,
        CORAZONES_K, PICAS_K, DIAMANTES_K, TREBOL_K,
        CORAZONES_JOKER, PICAS_JOKER, DIAMANTES_JOKER, TREBOL_JOKER;
    }
    public static void main(String[] args){
        Carta[] mazo = {
            Carta.CORAZONES_A, Carta.PICAS_A, Carta.DIAMANTES_A, Carta.TREBOL_A, 
            Carta.CORAZONES_2, Carta.PICAS_2, Carta.DIAMANTES_2, Carta.TREBOL_2,
            Carta.CORAZONES_3, Carta.PICAS_3, Carta.DIAMANTES_3, Carta.TREBOL_3,
            Carta.CORAZONES_4, Carta.PICAS_4, Carta.DIAMANTES_4, Carta.TREBOL_4,
            Carta.CORAZONES_5, Carta.PICAS_5, Carta.DIAMANTES_5, Carta.TREBOL_5,
            Carta.CORAZONES_6, Carta.PICAS_6, Carta.DIAMANTES_6, Carta.TREBOL_6,
            Carta.CORAZONES_7, Carta.PICAS_7, Carta.DIAMANTES_7, Carta.TREBOL_7,
            Carta.CORAZONES_8, Carta.PICAS_8, Carta.DIAMANTES_8, Carta.TREBOL_8,
            Carta.CORAZONES_9, Carta.PICAS_9, Carta.DIAMANTES_9, Carta.TREBOL_9,
            Carta.CORAZONES_10, Carta.PICAS_10, Carta.DIAMANTES_10, Carta.TREBOL_10,
            Carta.CORAZONES_J, Carta.PICAS_J, Carta.DIAMANTES_J, Carta.TREBOL_J,
            Carta.CORAZONES_Q, Carta.PICAS_Q, Carta.DIAMANTES_Q, Carta.TREBOL_Q,
            Carta.CORAZONES_K, Carta.PICAS_K, Carta.DIAMANTES_K, Carta.TREBOL_K,
            Carta.CORAZONES_JOKER, Carta.PICAS_JOKER, Carta.DIAMANTES_JOKER, Carta.TREBOL_JOKER
        };
        int creditos = 100;
        boolean continuar = true;
        int juegosMax = 5;
        int apuestaMin = 10;
        int juegos = 0;
        int ganadas = 0;
        int perdidas = 0;
        int empates = 0;

        // * Bienvenida y pedir nombre
        System.out.println("Bienvenido al juego de Blackjack!");
        System.out.print("Ingrese su nombre: ");
        // * Leer nombre y declarar Scanner
        Scanner lectura = new Scanner (System.in);
        String nombre = lectura.nextLine();
        System.out.println("Bienvenido " + nombre + "!");

        while(continuar && juegos < juegosMax && creditos >= apuestaMin){
            MaquinaDeTrazados mt = new MaquinaDeTrazados(1200, 650, "Mesa de Blackjack", Colores.LIGHT_GRAY);
            Carta[] manoJugador = new Carta[21];
            int manoJugadorValor = 0;
            int cartasJugador = 2;
            Carta[] manoCroupier = new Carta[17];
            int manoCroupierValor = 0;
            int cartasCroupier = 2;
            Boolean partidaContinua = true;
            int apuesta = 0;

            // * Mostrar creditos y pedir apuesta
            System.out.println("Sus creditos son: " + creditos);
            System.out.print("Por favor, ingrese su apuesta. Esta debe ser mayor o igual a " + apuestaMin + ": ");
            // * Leer apuesta, si es invalida retornar 0
            apuesta = pedirApuesta(creditos, lectura.nextInt());
            // * Validar apuesta, si es invalida pedir de nuevo
            while(apuesta == 0){
                System.out.println("Apuesta invalida!");
                System.out.print("Por favor, ingrese su apuesta. Esta debe ser mayor o igual a " + apuestaMin + ": ");
                apuesta = pedirApuesta(creditos, lectura.nextInt());
            }
            // * Mostrar apuesta y restar creditos disponibles
            System.out.println("Apuesta: " + apuesta);
            // TODO: Los creditos se restan al final de cada juego. Aun asi, se restan aqui con fines de prueba.
            creditos -= apuesta;
            System.out.println("Sus creditos restantes son: " + creditos);

            // * Repartir y mostrar cartas del jugador
            repartirCartas(mazo, manoJugador);
            manoJugadorValor = valorCartas(manoJugador, cartasJugador);

            // * Repartir y mostrar cartas del croupier
            // ! Solo se muestra la primera carta del croupier
            repartirCartas(mazo, manoCroupier);
            manoCroupierValor = valorCartas(manoCroupier, cartasCroupier);

            // * Mostrar cartas del jugador y primera del croupier
            System.out.println("Preparando mesa...");
            System.out.println("Por favor, espere a que la mesa se cierre para continuar...");
            mostrarCartas(mt, nombre, juegos, apuesta, creditos, manoJugador, cartasJugador, 60, manoCroupier, cartasCroupier);


            // TODO: Agregar metodos para cada caso
            while(partidaContinua){
                if(manoJugadorValor == 21){
                    jugadorTieneBlackJack(manoJugadorValor);
                    creditos = creditos + apuesta + (apuesta * 3) / 2;
                    ganadas++;
                    partidaContinua = false;
                }
                else if(manoJugadorValor < 21){ 
                    Console consola = System.console();               
                    String decision = consola.readLine("Que quiere hacer? \n\n Pedir carta - Escriba '1' \n Plantarse - Escriba '2' \n Doblar - Escriba '3' \n Salir del Juego - Escriba '4'\n");
                    int accion = Integer.parseInt(decision);
                    while(accion < 1 || accion > 4){
                        System.out.println("Opcion Invalida");
                        decision = consola.readLine("Que quiere hacer? \n\n Pedir carta - Escriba '1' \n Plantarse - Escriba '2' \n Doblar - Escriba '3' \n Salir del Juego - Escriba '4'\n");
                        accion = Integer.parseInt(decision);
                    }
                    if(accion == 3){
                        if(manoJugadorValor == 9 || manoJugadorValor == 10 || manoJugadorValor == 11){
                            apuesta = apuesta * 2;
                            agregarCartaAlMazo(cartasJugador, manoJugador, mazo);
                            cartasJugador++;
                            manoJugadorValor = valorCartas(manoJugador, cartasJugador);
                        }
                        else{
                            System.out.println("No puede doblar!");
                        }                
                    }
                    else if(accion == 2){
                        // TODO: Funcion de acabar turno                
                    }
                    else if(accion == 1){                        
                        agregarCartaAlMazo(cartasJugador, manoJugador, mazo);
                        cartasJugador++;
                        manoJugadorValor = valorCartas(manoJugador, cartasJugador);
                    }
                    else if(accion == 4){                       
                        continuar = false;
                        partidaContinua = false;
                    }
                }
                else{
                    jugadorTieneMasDe21(manoJugadorValor);
                    perdidas++;
                    partidaContinua = false;
                }  
                // Turno del croupier
                if(partidaContinua){
                    accionesDelCuprier(manoCroupierValor);
                    if(manoCroupierValor < 17){
                        agregarCartaAlMazo(cartasCroupier, manoCroupier, mazo);
                        cartasCroupier++;
                        manoCroupierValor = valorCartas(manoCroupier, cartasCroupier);
                    }
                    else if(manoCroupierValor > 21) {
                        System.out.println("El cuprier ha perdido!");
                        ganadas++;
                        partidaContinua = false;                    
                    }
                    if(manoCroupierValor == 21){
                        System.out.println("El cuprier ha ganado!");
                        perdidas++;
                        partidaContinua = false;
                    }
                    else
                        mostrarCartas(mt, nombre, juegos, apuesta, creditos, manoJugador, cartasJugador, 60, manoCroupier, cartasCroupier);
                }
                if(manoCroupierValor == 21){
                    System.out.println("Empate!");
                    empates++;
                }
            }
            juegos++;
        }
        if(juegos == juegosMax){
            System.out.println("Se ha alcanzado el numero maximo de juegos!");
        }
        else if(creditos < apuestaMin){
            System.out.println("No tiene suficientes creditos para continuar!");
        }
        
        // * Cerrar Scanner
        lectura.close();
        // * Mostrar resultados y finalizar juego
        System.out.println("Juegos totales: " + juegos + " de " + juegosMax);
        System.out.println("Juegos ganados: " + ganadas);
        System.out.println("Juegos perdidos: " + perdidas);
        System.out.println("Creditos restantes: " + creditos);
        System.out.println("Gracias por jugar!");
    }

    //@ requires creditos >= 10 && ap >= 10;
    //@ ensures \result >= 10 || \result == 0;
    public static /*@ pure @*/ int pedirApuesta(int creditos, int ap){
        int apuestaMin = 10;
        int apuesta = ap;
        if(apuesta < apuestaMin){ // * Si la apuesta es menor a 10
            apuesta = 0;
        }
        else if(apuesta > creditos){ // * Si la apuesta es mayor a los creditos
            apuesta = 0;
        }
        return apuesta;
    }
    //@ requires mazo.length == 56 && (mano.length == 21 || mano.length == 17);
    //@ ensures (\forall int a; 0 <= a && a < mano.length; mano[a] != null <== (\exists int b; 0 <= b && b < mazo.length; mazo[b] == mano[a]));
    public static void repartirCartas(Carta[] mazo, Carta[] mano){
        int cantidadCartas = mazo.length;
        int cartasInicial = 2;
        int i = 0;
        //@ maintaining 0 <= i && i <= cartasInicial && i <= mano.length && i <= mazo.length;
        //@ decreases cartasInicial - i;
        while(0 <= i && i < cartasInicial && i < mano.length && i < mazo.length){
            // * Seleccionar carta aleatoria y agregarla a la mano
            int carta = (int) (Math.random() * cantidadCartas);
            //@ assume 0 <= carta && carta < cantidadCartas && 0 <= i && i < mano.length;
            mano[i] = mazo[carta];
            i = i+1;
        }
    }
    //@ requires numeroCartas >= 0 && numeroCartas < 21 && (mano.length == 21 || mano.length == 17) && mazo.length == 56;
    //@ ensures (\forall int i; 0 <= i && i < numeroCartas+1; mano[i].ordinal() >= 0 && mano[i].ordinal() < 56) ==> mano[numeroCartas].ordinal() >= 0 && mano[numeroCartas].ordinal() < 56;
    public static void agregarCartaAlMazo(int numeroCartas, Carta[] mano, Carta[] mazo){
        int cantidadCartas = mazo.length;
        int carta = (int) (Math.random() * cantidadCartas);
        //@ assume 0 <= carta && carta < cantidadCartas && 0 <= numeroCartas && numeroCartas < mano.length;
        mano[numeroCartas] = mazo[carta];
    }
    //@ requires numeroCartas >= 0 && numeroCartas <= 21 && mano.length == 21;
    //@ ensures( \result >= 0 && \result < Integer.MAX_VALUE) <== numeroCartas > 0;
    public static /*@ pure @*/ int valorCartas(Carta[] mano, int numeroCartas){
        int valor = 0;
        int i = 0;
        //@ maintaining 0 <= i && i <= numeroCartas && i <= mano.length;
        //@ decreases numeroCartas - i;
        while(0 <= i && i < numeroCartas && i < mano.length){
            int indiceCarta = mano[i].ordinal();
            //@ assume 0 <= indiceCarta && indiceCarta < 56;
            //@ assume 0 <= valor && valor + 11 < Integer.MAX_VALUE;
            if(indiceCarta <= 35){
                valor = valor + indiceCarta/4 + 1;
            }
            else if(indiceCarta <= 51){
                valor = valor + 10;
            }
            else{
                valor = valor + 11;
            }
            i = i+1;
        }
        return valor;
    }
    //@ requires true;
    //@ ensures true;
    public static void jugadorTieneBlackJack(int a) {
        System.out.println("BlackJack! Usted ha ganado.");
    }
    /**
    * @param a Requires: a >= 0
    * @ensures true;
    */
    public static void jugadorTieneMasDe21(int a) {
        System.out.println("Usted ha perdido esta mano");
    }
    
    /**
    * @param manoCroupierValor Requires: manoCroupierValor >= 1 && manoCroupierValor <= 31
    * @ensures true;
    */
    public static void accionesDelCuprier(int manoCroupierValor) {
        if(manoCroupierValor < 17) {
            //agregarCartaAlMazo(manoCroupier, barajaIndex); 
            //Actualizar
        }
        else if(manoCroupierValor >= 17 && manoCroupierValor <= 21) {
            // No hace nada
        }
        else if(manoCroupierValor > 21) {
            System.out.println("El cuprier ha perdido!");
        }
    }
    
    //@ requires segundos >= 0;
    //@ ensures segundos >= 0;
    public static void pausarEjecucion(int segundos) {
        try {
            TimeUnit.SECONDS.sleep(segundos);
        } catch(Exception e) {
            System.out.println("Error pausando el programa.");
        };
    }
    //@ requires carta != null && carta.toString() != null;
    //@ ensures \result != null && \result.length() > 0;
    public static /*@ pure @*/ String buscarCarta(Carta carta){
        String cartaInfo = "";
        // * Se divide entre 4 para obtener un numero entre 0 y 13, que son los indices de las cartas
        String[] cartasPosibles = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "JOKER"};
        int indiceCartas = carta.ordinal()/4;
        cartaInfo = cartasPosibles[indiceCartas];
        return cartaInfo;
    }
    //@ requires carta != null;
    //@ ensures (\result.length == 2);
    public static /*@ pure @*/ int[] buscarCartaPosicion(Carta carta){
        int[] posicion = new int[2];
        // * Se divide entre 4 para obtener el indice de la carta, y el resto para obtener el tipo de carta
        int indiceCartas = carta.ordinal()/4;
        int indiceTipo = carta.ordinal()%4;
        posicion[1] = indiceCartas;
        posicion[0] = indiceTipo;
        return posicion;
    }
    //@ requires carta != null && numeroCarta >= 0 && mt != null;
    //@ ensures mt != null;
    public static /*@ pure @*/ void dibujarCarta(Carta carta, int numeroCarta, int xCarta, int yCarta, MaquinaDeTrazados mt){
        // * Ancho y alto de la carta
        int cartaA = 100;
        int cartaL = 150;
        // * Separacion entre cartas
        int separacionCartas = 25;

        String cartaInfo = buscarCarta(carta);
        int[] cartaIndices = buscarCartaPosicion(carta);
        
        //@ assume numeroCarta*(cartaA + separacionCartas) < Integer.MAX_VALUE && xCarta + 150 < Integer.MAX_VALUE;
        //@ assume xCarta + 150 + numeroCarta*(cartaA + separacionCartas) < Integer.MAX_VALUE;
        //@ assume yCarta + 150 < Integer.MAX_VALUE;
        int x = xCarta + numeroCarta*(cartaA + separacionCartas);

        // * Posiciones (x,y) de las esquinas de la carta
        int ly = yCarta + 20;
        int lx = xCarta + 7 + numeroCarta*(cartaA + separacionCartas);
        int ry = yCarta + 145;
        int rx = xCarta + 75 + numeroCarta*(cartaA + separacionCartas);

        // * Posiciones (x,y) del simbolo de la carta
        int sy = yCarta + 55;
        int sx = xCarta + 30 + numeroCarta*(cartaA + separacionCartas);

        //@ assume x < Integer.MAX_VALUE && ly < Integer.MAX_VALUE && lx < Integer.MAX_VALUE && ry < Integer.MAX_VALUE && rx < Integer.MAX_VALUE && sy < Integer.MAX_VALUE && sx < Integer.MAX_VALUE; 
        //@ assume cartaIndices[1] >= 0 && cartaIndices[1] < 14;
        // * Casos particulares de las cartas
        if(cartaIndices[1] == 13){
            lx = lx + 8;
            rx = lx;
        }
        else if(cartaIndices[1] == 9)rx = rx - 8;
        else if(cartaIndices[1] == 10){
            rx = rx + 8;
            ry = ry - 5;
        }
        //@ assume cartaIndices[0] >= 0 && cartaIndices[0] < 4;
        // * Dibujar fondo y marco de la carta
        mt.dibujarRectanguloLleno(x, yCarta, cartaA, cartaL, Colores.WHITE);
        if(cartaIndices[0]%2 == 0) mt.dibujarRectangulo(x, yCarta, cartaA, cartaL, Colores.RED);
        else mt.dibujarRectangulo(x, yCarta, cartaA, cartaL, Colores.BLACK);
        mt.configurarFuente("Serif", Font.BOLD, 40);

        //@ assume sx + 50 < Integer.MAX_VALUE && sy + 50 < Integer.MAX_VALUE;
        //@ assume sx - 50 > Integer.MIN_VALUE && sy - 50 > Integer.MIN_VALUE;
        if(cartaIndices[0] == 0){ // * Dibuja el corazon en la carta
            mt.dibujarOvaloLleno(sx-5, sy-5, 30, 30, Colores.RED);
            mt.dibujarOvaloLleno(sx+15, sy-5, 30, 30, Colores.RED);
            mt.dibujarPoligonoLleno(new int[]{sx-1, sx+41, sx+20}, new int[]{sy+20,sy+20,sy+45}, 3, Colores.RED);
        }
        else if(cartaIndices[0] == 1){ // * Dibuja el picas en la carta
            mt.dibujarPoligonoLleno(new int[]{sx + 40, sx + 20, sx}, new int[]{sy+10, sy-10, sy+10}, 3, Colores.BLACK);
            mt.dibujarOvaloLleno(sx - 5, sy + 5, 30, 30, Colores.BLACK);
            mt.dibujarOvaloLleno(sx + 15, sy + 5, 30, 30, Colores.BLACK);
            mt.dibujarPoligonoLleno(new int[]{sx + 10, sx + 20, sx + 30}, new int[]{sy+45, sy+30, sy+45}, 3, Colores.BLACK);
        }
        else if(cartaIndices[0] == 2) // * Dibuja el diamante en la carta
            mt.dibujarPoligonoLleno(new int[]{sx + 20, sx + 40, sx + 20, sx}, new int[]{sy, sy+20, sy+40, sy+20}, 4, Colores.RED);
        else{ // * Dibuja el trebol en la carta
            mt.dibujarOvaloLleno(sx-6, sy+7, 30, 30, Colores.BLACK);
            mt.dibujarOvaloLleno(sx+16, sy+7, 30, 30, Colores.BLACK);
            mt.dibujarOvaloLleno(sx+5, sy-15, 30, 30, Colores.BLACK);
            mt.dibujarPoligonoLleno(new int[]{sx+5, sx+20, sx+35}, new int[]{sy+55,sy+25,sy+55}, 3, Colores.BLACK);
        }

        mt.configurarFuente("Serif", Font.BOLD, 18);
        if(cartaIndices[0]%2 == 0){ // * Color rojo si es diamantes o corazones
            mt.dibujarString(cartaInfo, lx, ly, Colores.RED);
            mt.dibujarString(cartaInfo, rx, ry, Colores.RED);
        }
        else{ // * Color negro si es picas o trebol
            mt.dibujarString(cartaInfo, lx, ly, Colores.BLACK);
            mt.dibujarString(cartaInfo, rx, ry, Colores.BLACK);
        };
        mt.mostrar();
    }
    //@ requires carta != null && numeroCarta >= 0 && mt != null;
    //@ ensures mt != null;
    public static void dibujarCartaVolteada(Carta carta, int numeroCarta, int xCarta, int yCarta, MaquinaDeTrazados mt){
        // * Ancho y alto de la carta
        int cartaA = 100;
        int cartaL = 150;
        // * Separacion entre cartas
        int separacionCartas = 25;
        
        //@ assume numeroCarta*(cartaA + separacionCartas) < Integer.MAX_VALUE && xCarta + 150 < Integer.MAX_VALUE;
        //@ assume xCarta + 150 + numeroCarta*(cartaA + separacionCartas) < Integer.MAX_VALUE;
        //@ assume yCarta + 150 < Integer.MAX_VALUE;
        int x = xCarta + numeroCarta*(cartaA + separacionCartas);

        //@ assume x + 20 < Integer.MAX_VALUE && yCarta + 20 < Integer.MAX_VALUE;
        int xMarco = x + 20;
        int yMarco = yCarta + 20;
        //@ assume cartaA - 40 > 0 && cartaL - 40 > 0;
        int marcoA = cartaA - 40;
        int marcoL = cartaL - 40;
        mt.dibujarRectanguloLleno(x, yCarta, cartaA, cartaL, Colores.BLACK);
        mt.dibujarRectangulo(xMarco, yMarco, marcoA, marcoL, Colores.CYAN);
        //@ assume xMarco + 40 < Integer.MAX_VALUE && yMarco + 40 < Integer.MAX_VALUE;
        //@ assume marcoA - 80 > 0 && marcoL - 80 > 0;
        mt.dibujarRectanguloLleno(xMarco + 5, yMarco + 5, marcoA - 10, marcoL - 10, Colores.BLACK);
        mt.dibujarRectangulo(xMarco + 10, yMarco + 10, marcoA - 20, marcoL - 20, Colores.CYAN);
        mt.dibujarRectanguloLleno(xMarco + 15, yMarco + 15, marcoA - 30, marcoL - 30, Colores.BLACK);
        mt.dibujarRectangulo(xMarco + 20, yMarco + 20, marcoA - 40, marcoL - 40, Colores.CYAN);
        mt.dibujarRectanguloLleno(xMarco + 25, yMarco + 25, marcoA - 50, marcoL - 50, Colores.BLACK);
        mt.dibujarRectangulo(xMarco + 30, yMarco + 30, marcoA - 60, marcoL - 60, Colores.CYAN);
        mt.dibujarRectanguloLleno(xMarco + 35, yMarco + 35, marcoA - 70, marcoL - 70, Colores.BLACK);
        mt.dibujarRectangulo(xMarco + 40, yMarco + 40, marcoA - 80, marcoL - 80, Colores.CYAN);        
    }
    //@ requires mt != null && numeroCartas > 0;
    //@ ensures \result > Integer.MIN_VALUE && \result < Integer.MAX_VALUE;
    public static /*@ pure @*/ int alinearCartas(MaquinaDeTrazados mt, int numeroCartas){
        //@ assume numeroCartas*50 < Integer.MAX_VALUE;
        //@ assume mt.XMAX/2 - numeroCartas*50 > Integer.MIN_VALUE && mt.XMAX/2 - numeroCartas*50 < Integer.MAX_VALUE;
        int xCarta = mt.XMAX/2 - numeroCartas*50;
        return xCarta;
    }

    //@ requires nombre != null && apuesta >= 10 && creditos > 0 && barajaJugador != null && numeroCartasJugador >= 2 && yo >= 0 && barajaCroupier != null && barajaCroupier.length >= 2 && mt != null && numeroCartasCroupier >= 2;
    //@ ensures mt != null;
    public static void mostrarCartas(MaquinaDeTrazados mt, String nombre, int juegos, int apuesta, int creditos, Carta[] barajaJugador, int numeroCartasJugador, int yo, Carta[] barajaCroupier, int numeroCartasCroupier){
        // * Limpiar la pantalla
        mt.limpiar();

        // * Dibujar mesa
        int anchoPantalla = mt.XMAX;
        int altoPantalla = mt.YMAX;
        //@ assume -1*altoPantalla > Integer.MIN_VALUE && -1*altoPantalla < Integer.MAX_VALUE;
        //@ assume 2*altoPantalla < Integer.MAX_VALUE && 2*altoPantalla > Integer.MIN_VALUE;
        //@ assume anchoPantalla - 30 > 0;
        int yMesa = -1*altoPantalla;
        int anchoMesa = anchoPantalla - 30;
        int altoMesa = 2*altoPantalla;
        mt.dibujarOvaloLleno(0, yMesa, anchoMesa, altoMesa, Colores.GREEN);
        mt.dibujarOvalo(0, yMesa, anchoMesa, altoMesa, Colores.BLACK);
        
        mt.configurarFuente("Serif", Font.BOLD, 18);
        // * Mostrar las cartas del jugador
        int xo = alinearCartas(mt, numeroCartasJugador);
        //@ assume xo + 2 < Integer.MAX_VALUE && yo - 10 > Integer.MIN_VALUE;
        mt.dibujarString(nombre, xo + 2, yo - 10, Colores.BLUE);

        int i = 0;

        //@ maintaining 0 <= i && i <= barajaJugador.length && i <= numeroCartasJugador;
        //@ decreasing barajaJugador.length - i;
        while(0 <= i && i < barajaJugador.length && i < numeroCartasJugador){
            dibujarCarta(barajaJugador[i], i, xo, yo, mt);
            i++;
        }

        // * Mostrar las cartas del croupier
        //@ assume yo + 190 < Integer.MAX_VALUE;
        xo = alinearCartas(mt, numeroCartasCroupier);
        int yoCroupier = yo + 190;

        mt.configurarFuente("Serif", Font.BOLD, 18);
        //@ assume yoCroupier - 20 > Integer.MIN_VALUE && xo + 2 < Integer.MAX_VALUE;
        mt.dibujarString("Croupier", xo + 2, yoCroupier - 10, Colores.RED);

        int j = 0;
        //@ maintaining 0 <= j && j <= barajaCroupier.length && j <= numeroCartasCroupier;
        //@ decreasing barajaCroupier.length - j;
        while(0 <= j && j < barajaCroupier.length && j < numeroCartasCroupier){
            if(j == 0)
                dibujarCarta(barajaCroupier[j], j, xo, yoCroupier, mt);
            else
                dibujarCartaVolteada(barajaCroupier[j], j, xo, yoCroupier, mt);
            j++;
        }

        //@ assume yoCroupier + 190 < Integer.MAX_VALUE && mt.XMAX/2 - 50 < Integer.MAX_VALUE && mt.XMAX/2 - 50 > 0;
        int yApuesta = yoCroupier + 190;
        int xApuesta = mt.XMAX/2 - 50;
        mt.dibujarString("Apuesta: " + apuesta, xApuesta, yApuesta, Colores.WHITE);

        //@ assume yApuesta + 20 < Integer.MAX_VALUE && mt.XMAX/2 - 50 < Integer.MAX_VALUE && mt.XMAX/2 - 50 > 0;
        int yCreditos = yApuesta + 20;
        int xCreditos = mt.XMAX/2 - 50;
        mt.dibujarString("Creditos: " + creditos, xCreditos, yCreditos, Colores.WHITE);

        //@ assume yCreditos + 20 < Integer.MAX_VALUE && mt.XMAX/2 - 50 < Integer.MAX_VALUE && mt.XMAX/2 - 50 > 0;
        int yJuegos = yCreditos + 20;
        int xJuegos = mt.XMAX/2 - 50;
        //@ assume juegos >= 0 && juegos + 1 < Integer.MAX_VALUE;
        mt.dibujarString("Juegos: " + (juegos+1), xJuegos, yJuegos, Colores.WHITE);

        mt.mostrar();
        // * Pausar la ejecucion por 10 segundos
        pausarEjecucion(10);
        // * Cerrar la ventana
        mt.terminar();
    }
}

