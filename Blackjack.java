// * Scanner para lectura de datos
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.Console;

// ! Libreria para interfaz grafica
import java.awt.Font;

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
    CORAZONES_JOKER, PICAS_JOKER, DIAMANTES_JOKER, TREBOL_JOKER
}

public class Blackjack{
    public static void main(String[] args){
        int creditos = 100;
        boolean continuar = true;
        int juegosMax = 5;
        int apuestaMin = 10;
        int juegos = 0;
        int ganadas = 0;
        int perdidas = 0;
        int empates = 0;
        int apuesta = 0;

        // * Bienvenida y pedir nombre
        System.out.println("Bienvenido al juego de Blackjack!");
        System.out.print("Ingrese su nombre: ");
        // * Leer nombre y declarar Scanner
        Scanner lectura = new Scanner (System.in);
        String nombre = lectura.nextLine();
        System.out.println("Bienvenido " + nombre + "!");

        while(continuar && juegos < juegosMax && creditos >= apuestaMin){
            MaquinaDeTrazados mt = new MaquinaDeTrazados(800, 500, "Mesa de Blackjack", Colores.LIGHT_GRAY);
            Carta[] manoJugador = new Carta[21];
            int manoJugadorValor = 0;
            int cartasJugador = 2;
            Carta[] manoCroupier = new Carta[21];
            int manoCroupierValor = 0;
            int cartasCroupier = 2;
            Boolean partidaContinua = true;

            // * Mostrar creditos y pedir apuesta
            System.out.println("Sus creditos son: " + creditos);
            System.out.print("Ingrese su apuesta: ");
            apuesta = pedirApuesta(creditos, lectura.nextInt());
            // * Validar apuesta, si es invalida pedir de nuevo
            while(apuesta == 0){
                System.out.println("Apuesta invalida!");
                System.out.print("Ingrese su apuesta: ");
                apuesta = pedirApuesta(creditos, lectura.nextInt());
            }
            // * Mostrar apuesta y restar creditos disponibles
            System.out.println("Apuesta: " + apuesta);
            // TODO: Los creditos se restan al final de cada juego. Aun asi, se restan aqui para que el usuario no pueda apostar mas de lo que se tiene.
            creditos -= apuesta;
            System.out.println("Sus creditos restantes son: " + creditos);

            // * Repartir y mostrar cartas del jugador
            manoJugador = repartirCartas();
            manoJugadorValor = valorCartas(manoJugador, cartasJugador);
            System.out.print("Sus cartas son: ");
            System.out.println(manoJugador[0] + " y " + manoJugador[1]);


            // * Repartir y mostrar cartas del croupier
            // ! Solo se muestra la primera carta del croupier
            manoCroupier = repartirCartas();
            manoCroupierValor = valorCartas(manoCroupier, cartasCroupier);
            System.out.print("La carta oculta del Croupier es: ");
            System.out.println(manoCroupier[0]);

            // * Mostrar cartas del jugador y primera del croupier
            mostrarCartas(mt, nombre, manoJugador, cartasJugador, 5, 30, manoCroupier);


            // TODO: Agregar metodos para cada caso
            while(partidaContinua){
                if(manoJugadorValor == 21){
                    jugadorTieneBlackJack(manoJugadorValor);
                    creditos = creditos + apuesta + (apuesta * 3) / 2;
                    partidaContinua = false;
                }
                else if(manoJugadorValor < 21){                
                    int noBlackJack = jugadorNoTieneBlackJack(manoJugadorValor);
                    if(noBlackJack == 3){
                        if(manoJugadorValor == 9 || manoJugadorValor == 10 || manoJugadorValor == 11){
                            apuesta = apuesta * 2;
                        }
                        else{
                            System.out.println("No puede doblar!");
                        }                
                    }
                    else if(noBlackJack == 2){
                        // ? Funcion de acabar turno                
                    }
                    else if(noBlackJack == 1){
                        manoJugador = agregarCartaAlMazo(cartasJugador, manoJugador);
                        cartasJugador++;
                        manoJugadorValor = valorCartas(manoJugador, cartasJugador);
                        mostrarCartas(mt, nombre, manoJugador, cartasJugador, 5, 30, manoCroupier);
                    }
                    else if(noBlackJack == 4){                       
                        continuar = false;
                        partidaContinua = false;
                    }
                    else{
                        System.out.println("Opcion Invalida");
                    }
                }
                else{
                    jugadorTieneMasDe21(manoJugadorValor);
                    partidaContinua = false;
                }
            }
                        
            // Turno del croupier

            while(partidaContinua){
                accionesDelCuprier(manoCroupierValor);
                //@ assert manoCroupierValor >= 1 && manoCroupierValor <= 31;
                if(manoCroupierValor > 21) {
                    System.out.println("El cuprier ha perdido!");
                    partidaContinua = false;                    
                }
            }
            juegos++;
        }
        
        // * Cerrar Scanner
        lectura.close();
        // Mostrar resultados y finalizar juego
        // System.out.println("Juegos jugados: " + juegos + " de " + juegosMax);
        // System.out.println("Juegos ganados: " + ganadas);
        // System.out.println("Juegos perdidos: " + perdidas);
        // System.out.println("Juegos empatados: " + empates);
        // System.out.println("Creditos restantes: " + creditos);
        System.out.println("Gracias por jugar!");
    }

    //@ requires creditos >= 10 && ap >= 10;
    //@ ensures \result >= 10 || \result == 0;
    public static /*@ pure @*/ int pedirApuesta(int creditos, int ap){
        int apuestaMin = 10;
        int apuesta = ap;
        if(apuesta < apuestaMin){
            apuesta = 0;
        }
        else if(apuesta > creditos){ 
            apuesta = 0;
        }
        return apuesta;
    }
    //@ requires true;
    //@ ensures (\result.length == 21) <== (\forall int i; 0 <= i && i < 2; \result[i].ordinal() >= 0 && \result[i].ordinal() < 56);
    public static /*@ pure @*/ Carta[] repartirCartas(){
        Carta[] mano = new Carta[21];
        int cantidadCartas = Carta.values().length;
        int cartasInicial = 2;
        int i = 0;
        //@ maintaining 0 <= i && i <= cartasInicial;
        //@ decreases cartasInicial - i;
        while(0 <= i && i < cartasInicial && i < mano.length){
            int carta = (int) (Math.random() * cantidadCartas);
            //@ assume 0 <= carta && carta < cantidadCartas;
            mano[i] = Carta.values()[carta];
            i = i+1;
        }
        return mano;
    }
    //@ requires numeroCartas >= 0 && numeroCartas <= 21 && mano.length == 21;
    //@ ensures (\result.length == mano.length) <== (\forall int i; 0 <= i && i < numeroCartas; \result[i].ordinal() >= 0 && \result[i].ordinal() < 56);
    public static /*@ pure @*/ Carta[] agregarCartaAlMazo(int numeroCartas, Carta[] mano){
        Carta[] manoNueva = new Carta[mano.length];
        int cantidadCartas = Carta.values().length;
        int carta = (int) (Math.random() * cantidadCartas);
        int i = 0;
        //@maintaining 0 <= i && i <= numeroCartas + 1 && i <= manoNueva.length;
        //@decreases numeroCartas + 1 - i;
        while(0 <= i && i < numeroCartas+1 && i < manoNueva.length){
            if(i < numeroCartas){
                int indiceCarta = mano[i].ordinal();
                manoNueva[i] = Carta.values()[indiceCarta];
            }
            else{
                //@ assume 0 <= carta && carta < cantidadCartas;
                manoNueva[i] = Carta.values()[carta];
            }
            i = i+1;
        }

        return manoNueva;
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
    * @requires System.console() != null;
    * @requires decision.matches("[1234]");
    * @ensures accion == 0 || accion == 1 || accion == 2 || accion == 3 || accion == 4;
    * @return  Ensures: \result == 0 || \result == 1 || \result == 2 || \result == 3 || \result == 4
    */
    public static int jugadorNoTieneBlackJack(int a) {
        Console consola = System.console();
        String decision = consola.readLine("Que quiere hacer? \n\n Pedir carta - Escriba '1' \n Plantarse - Escriba '2' \n Doblar - Escriba '3' \n Salir del Juego - Escriba '4'\n");
        int accion = 0;
        if (decision.equals("1")) {
            accion = 1;
        } else if (decision.equals("2")) {
            accion = 2;
        } else if (decision.equals("3")) {
            accion = 3;
        } else if (decision.equals("4")) {
            accion = 4;
        } else {
            System.out.println("Error! debe introducir una de las opciones");
        }
        return accion;
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
    // ! Las siguientes funciones no se toman en cuenta para la verificacion estatica
    //@ requires carta != null && carta.toString() != null;
    //@ ensures \result != null && \result.length() > 0;
    public static /*@ pure @*/ String buscarCarta(Carta carta){
        String cartaInfo = "";
        String[] cartasPosibles = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "JOKER"};
        int indiceCartas = carta.ordinal()/4;
        cartaInfo = cartasPosibles[indiceCartas];
        return cartaInfo;
    }
    //@ requires carta != null;
    //@ ensures (\result.length == 2);
    public static /*@ pure @*/ int[] buscarCartaPosicion(Carta carta){
        int[] posicion = new int[2];
        int indiceCartas = carta.ordinal()/4;
        int indiceTipo = carta.ordinal()%4;
        posicion[1] = indiceCartas;
        posicion[0] = indiceTipo;
        return posicion;
    }
    //@ requires carta != null && numeroCarta >= 0 && mt != null;
    //@ ensures true;
    public static /*@ pure @*/ void dibujarCarta(Carta carta, int numeroCarta, int xCarta, int yCarta, MaquinaDeTrazados mt){
        int cartaA = 100;
        int cartaL = 150;
        int separacionCartas = 25;

        String cartaInfo = buscarCarta(carta);
        int[] cartaIndices = buscarCartaPosicion(carta);
        
        //@ assume numeroCarta*(cartaA + separacionCartas) < Integer.MAX_VALUE && xCarta + 150 < Integer.MAX_VALUE;
        //@ assume xCarta + 150 + numeroCarta*(cartaA + separacionCartas) < Integer.MAX_VALUE;
        //@ assume yCarta + 150 < Integer.MAX_VALUE;
        int x = xCarta + numeroCarta*(cartaA + separacionCartas);

        int ly = yCarta + 20;
        int lx = xCarta + 7 + numeroCarta*(cartaA + separacionCartas);

        int ry = yCarta + 145;
        int rx = xCarta + 75 + numeroCarta*(cartaA + separacionCartas);

        int sy = yCarta + 55;
        int sx = xCarta + 30 + numeroCarta*(cartaA + separacionCartas);

        //@ assume x < Integer.MAX_VALUE && ly < Integer.MAX_VALUE && lx < Integer.MAX_VALUE && ry < Integer.MAX_VALUE && rx < Integer.MAX_VALUE && sy < Integer.MAX_VALUE && sx < Integer.MAX_VALUE; 
        //@ assume cartaIndices[1] >= 0 && cartaIndices[1] < 14;
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
        mt.dibujarRectanguloLleno(x, yCarta, cartaA, cartaL, Colores.WHITE);
        if(cartaIndices[0]%2 == 0) mt.dibujarRectangulo(x, yCarta, cartaA, cartaL, Colores.RED);
        else mt.dibujarRectangulo(x, yCarta, cartaA, cartaL, Colores.BLACK);
        mt.configurarFuente("Serif", Font.BOLD, 40);

        //@ assume sx + 50 < Integer.MAX_VALUE && sy + 50 < Integer.MAX_VALUE;
        //@ assume sx - 50 > Integer.MIN_VALUE && sy - 50 > Integer.MIN_VALUE;
        if(cartaIndices[0] == 0){
            mt.dibujarOvaloLleno(sx-5, sy-5, 30, 30, Colores.RED);
            mt.dibujarOvaloLleno(sx+15, sy-5, 30, 30, Colores.RED);
            mt.dibujarPoligonoLleno(new int[]{sx-1, sx+41, sx+20}, new int[]{sy+20,sy+20,sy+45}, 3, Colores.RED);
        }
        else if(cartaIndices[0] == 1){
            mt.dibujarPoligonoLleno(new int[]{sx + 40, sx + 20, sx}, new int[]{sy+10, sy-10, sy+10}, 3, Colores.BLACK);
            mt.dibujarOvaloLleno(sx - 5, sy + 5, 30, 30, Colores.BLACK);
            mt.dibujarOvaloLleno(sx + 15, sy + 5, 30, 30, Colores.BLACK);
            mt.dibujarPoligonoLleno(new int[]{sx + 10, sx + 20, sx + 30}, new int[]{sy+45, sy+30, sy+45}, 3, Colores.BLACK);
        }
        else if(cartaIndices[0] == 2) mt.dibujarPoligonoLleno(new int[]{sx + 20, sx + 40, sx + 20, sx}, new int[]{sy, sy+20, sy+40, sy+20}, 4, Colores.RED);
        else{
            mt.dibujarOvaloLleno(sx-6, sy+7, 30, 30, Colores.BLACK);
            mt.dibujarOvaloLleno(sx+16, sy+7, 30, 30, Colores.BLACK);
            mt.dibujarOvaloLleno(sx+5, sy-15, 30, 30, Colores.BLACK);
            mt.dibujarPoligonoLleno(new int[]{sx+5, sx+20, sx+35}, new int[]{sy+55,sy+25,sy+55}, 3, Colores.BLACK);
        }

        mt.configurarFuente("Serif", Font.BOLD, 18);
        if(cartaIndices[0]%2 == 0){
            mt.dibujarString(cartaInfo, lx, ly, Colores.RED);
            mt.dibujarString(cartaInfo, rx, ry, Colores.RED);
        }
        else{
            mt.dibujarString(cartaInfo, lx, ly, Colores.BLACK);
            mt.dibujarString(cartaInfo, rx, ry, Colores.BLACK);
        };
        mt.mostrar();
    }
    //@ requires nombre != null && barajaJugador != null && numeroCartasJugador >= 2 && xo >= 0 && yo >= 0 && barajaCroupier != null && barajaCroupier.length >= 2 && mt != null;
    //@ ensures true;
    public static void mostrarCartas(MaquinaDeTrazados mt, String nombre, Carta[] barajaJugador, int numeroCartasJugador, int xo, int yo, Carta[] barajaCroupier){
        
        mt.configurarFuente("Serif", Font.BOLD, 18);
        //@ assume xo + 10 < Integer.MAX_VALUE && yo - 10 > Integer.MIN_VALUE;
        mt.dibujarString(nombre, xo + 2, yo - 10, Colores.CYAN);

        int i = 0;

        //@ maintaining 0 <= i && i <= barajaJugador.length && i <= numeroCartasJugador;
        //@ decreasing barajaJugador.length - i;
        while(0 <= i && i < barajaJugador.length && i < numeroCartasJugador){
            dibujarCarta(barajaJugador[i], i, xo, yo, mt);
            i++;
        }

        //@ assume yo + 190 < Integer.MAX_VALUE;
        int yoCroupier = yo + 190;

        mt.configurarFuente("Serif", Font.BOLD, 18);
        //@ assume yoCroupier - 20 > Integer.MIN_VALUE;
        mt.dibujarString("Croupier", xo + 2, yoCroupier - 10, Colores.YELLOW);

        dibujarCarta(barajaCroupier[0], 0, xo, yoCroupier, mt);

        mt.mostrar();
        pausarEjecucion(15);
        mt.terminar();
    }
}

