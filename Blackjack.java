// * Scanner para lectura de datos
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.Console;

// ! Libreria para interfaz grafica
import java.awt.Font;

public class Blackjack{
    public static void main(String[] args){
        int creditos = 100;
        boolean continuar = true;
        int juegosMax = 5;
        int apuestaMin = 10;
        String[] tipos = {"♥", "♠", "♦", "♣"};
        // String[] tipos = {"Corazones", "Picas", "Diamantes", "Treboles"};
        int[] barajaIndex = {
            0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,
            28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,
            53,54,55
        };
        // El valor de cada carta es: En el caso de las Ases es 1, 
        // para J, Q y K es 10, el joker es 11 y el resto de cartas es su valor numerico
        String[] baraja = {"A", "A", "A", "A", "2", "2", "2", "2", "3", "3", "3", "3",
            "4", "4", "4", "4", "5", "5", "5", "5", "6", "6", "6", "6", "7", "7", "7",
            "7", "8", "8", "8", "8", "9", "9", "9", "9", "10", "10", "10", "10", "J",
            "J", "J", "J", "Q", "Q", "Q", "Q", "K", "K", "K", "K", "★", "★", "★", "★"
        };
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
            int[] manoJugador = new int[21];
            String[] manoJugadorString = new String[21];
            String[] tipoManoJugador = new String[21];
            int manoJugadorValor = 0;
            int cartasJugador = 2;
            int[] manoCroupier = new int[21];
            String[] manoCroupierString = new String[21];
            String[] tipoManoCroupier = new String[21];
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
            manoJugador = repartirCartasIndice(barajaIndex);
            manoJugadorString = repartirCartas(manoJugador, baraja, cartasJugador);
            tipoManoJugador = repartirTipos(manoJugador, tipos, cartasJugador);
            System.out.print("Sus cartas son: ");
            System.out.print(manoJugadorString[0] + " de " + tipoManoJugador[0]);
            System.out.println(" y " + manoJugadorString[1] + " de " + tipoManoJugador[1]);
            manoJugadorValor = valorCartas(manoJugador, cartasJugador);

            // * Repartir y mostrar cartas del croupier
            // ! Solo se muestra la primera carta del croupier
            manoCroupier = repartirCartasIndice(barajaIndex);
            manoCroupierString = repartirCartas(manoCroupier, baraja, cartasCroupier);
            tipoManoCroupier = repartirTipos(manoCroupier, tipos, cartasCroupier);
            System.out.print("La carta del croupier es: ");
            System.out.println(manoCroupierString[0] + " de " + tipoManoCroupier[0]);
            manoCroupierValor = valorCartas(manoCroupier, cartasCroupier);

            // ? Mostrar cartas del jugador y primera del croupier
            mostrarCartas(nombre, manoJugadorString, tipoManoJugador, cartasJugador, 5, 30, manoCroupierString, tipoManoCroupier);


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
                        //agregarCartaAlMazo(manoJugador, barajaIndex);               
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
    //@ requires baraja.length > 0 && baraja.length <= 56;
    //@ ensures (\result.length >= 2) <== (\forall int i; 0 <= i && i < \result.length; \result[i] >= 0 && \result[i] < 56);
    public static /*@ pure @*/ int[] repartirCartasIndice(int[] baraja){
        int[] mano = new int[21];
        int cartasInicial = 2;
        int i = 0;
        //@ maintaining 0 <= i && i <= cartasInicial;
        //@ decreases cartasInicial - i;
        while(0 <= i && i < cartasInicial){
            int carta = (int) (Math.random() * baraja.length);
            //@ assume 0 <= carta && carta < baraja.length;
            mano[i] = baraja[carta];
            i = i+1;
        }
        return mano;
    }
    //@ requires baraja.length > 0 && baraja.length <= 56 && indices.length > 0 && indices.length <= baraja.length && 2 <= numeroCartas && numeroCartas + 1 <= indices.length;
    //@ ensures (\result.length >= 2) <== (\forall int i; 0 <= i && i < \result.length; \result[i] >= 0 && \result[i] < 56);
    public static /*@ pure @*/ int[] agregarCartaAlMazo(int[] indices, int[] baraja, int numeroCartas){
        int[] mano = new int[21];
        int carta = (int) (Math.random() * baraja.length);
        int i = 0;
        //@maintaining 0 <= i && i <= numeroCartas + 1 && i <= mano.length;
        //@decreases numeroCartas + 1 - i;
        while(0 <= i && i < numeroCartas+1 && i < mano.length){
            if(i < numeroCartas){
                mano[i] = indices[i];
            }
            else{
                //@ assume 0 <= carta && carta < baraja.length;
                mano[i] = baraja[carta];
            }
            i = i+1;
        }

        return mano;
    }
    //@ requires baraja.length > 0 && baraja.length <= 56 && indices.length <= 56 && 0 <= numeroCartas && numeroCartas <= indices.length && (\forall int i; 0 <= i && i < numeroCartas; indices[i] >= 0 && indices[i] < baraja.length);
    //@ ensures (\result.length >= 2) <== (\forall int i; 0 <= i && i < \result.length; \result[i] != null);
    public static /*@ pure @*/ String[] repartirCartas(int[] indices, String[] baraja, int numeroCartas){
        String[] mano = new String[21];
        int i = 0;
        //@ maintaining 0 <= i && i <= numeroCartas && i <= mano.length;
        //@ decreases numeroCartas - i;
        while(0 <= i && i < numeroCartas && i < mano.length){
            mano[i] = baraja[indices[i]];
            i = i+1;
        }
        return mano;
    }
    //@ requires tipos.length == 4 && indices.length >= 2 && indices.length <= 56 && 0 <= numeroCartas && numeroCartas <= indices.length && (\forall int i; 0 <= i && i < numeroCartas; indices[i] >= 0 && indices[i] < tipos.length);
    //@ ensures (\result.length >= 2) <== (\forall int i; 0 <= i && i < numeroCartas; \result[i] != null);
    public static /*@ pure @*/ String[] repartirTipos(int[] indices, String[] tipos, int numeroCartas){
        String[] mano = new String[21];
        int i = 0;
        //@ maintaining 0 <= i && i <= numeroCartas && i <= mano.length;
        //@ decreases numeroCartas - i;
        while(0 <= i && i < numeroCartas && i < mano.length){
            mano[i] = tipos[indices[i]%4];
            i = i+1;
        }
        return mano;
    }
    //@ requires indices.length >= 2 && indices.length <= 56 && numeroCartas >= 2 && numeroCartas <= indices.length && (\forall int i; 0 <= i && i < numeroCartas; indices[i] >= 0 && indices[i] < 56);
    //@ ensures \result >= 0 <== (\forall int i; 0 <= i && i < numeroCartas; indices[i] >= 0 && indices[i] < 56);
    public static /*@ pure @*/ int valorCartas(int[] indices, int numeroCartas){
        int valor = 0;
        int i = 0;
        //@ maintaining 0 <= i && i <= numeroCartas && i <= indices.length;
        //@ decreases numeroCartas - i;
        while(0 <= i && i < numeroCartas && i < indices.length){
            //@ assume 0 <= indices[i] && indices[i] < 56;
            //@ assume 0 <= valor && valor + 11 < Integer.MAX_VALUE;
            if(indices[i] <= 35){
                valor = valor + indices[i]/4 + 1;
            }
            else if(indices[i] <= 51){
                valor = valor + 10;
            }
            else{
                valor = valor + 11;
            }
            i = i+1;
        }
        return valor;
    }
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
    // ! Las siguientes funciones no se toman en cuenta para la verificacion estatica
    public static void pausarEjecucion(int segundos) {
        try {
            TimeUnit.SECONDS.sleep(segundos);
        } catch(Exception e) {
            System.out.println("Error pausando el programa.");
        };
    }
    public static void mostrarCartas(String nombre, String[] barajaJugador, String[] tipoJugador, int numeroCartasJugador, int xo, int yo, String[] barajaCroupier, String[] tipoCroupier){
        int cartaA = 100;
        int cartaL = 150;
        int separacionCartas = 25;

        int ly = yo + 20;
        int ry = yo + 145;
        int sy = yo + 90;

        MaquinaDeTrazados mt = new MaquinaDeTrazados(800, 500, "Mesa de Blackjack", Colores.LIGHT_GRAY);
        mt.configurarFuente("Serif", Font.BOLD, 18);
        mt.dibujarString(nombre, xo + 2, yo - 10, Colores.CYAN);

        for(int i = 0; i < barajaJugador.length && i < numeroCartasJugador; i++){
            int x = xo + i*(cartaA + separacionCartas);
            int lx = xo + 7 + i*(cartaA + separacionCartas);
            int rx = xo + 75 + i*(cartaA + separacionCartas);
            int sx = xo + 35 + i*(cartaA + separacionCartas);
            
            mt.dibujarRectanguloLleno(x, yo, cartaA, cartaL, Colores.WHITE);
            if(tipoJugador[i] == "♥" || tipoJugador[i] == "♦") mt.dibujarRectangulo(x, yo, cartaA, cartaL, Colores.RED);
            else mt.dibujarRectangulo(x, yo, cartaA, cartaL, Colores.BLACK);
            mt.configurarFuente("Serif", Font.BOLD, 40);
            if(tipoJugador[i] == "♥" || tipoJugador[i] == "♦") mt.dibujarString(tipoJugador[i], sx, sy, Colores.RED);
            else mt.dibujarString(tipoJugador[i], sx, sy, Colores.BLACK);
            mt.configurarFuente("Serif", Font.BOLD, 18);
            if(tipoJugador[i] == "♥" || tipoJugador[i] == "♦"){
                mt.dibujarString(barajaJugador[i], lx, ly, Colores.RED);
                mt.dibujarString(barajaJugador[i], rx, ry, Colores.RED);
            }
            else{
                mt.dibujarString(barajaJugador[i], lx, ly, Colores.BLACK);
                mt.dibujarString(barajaJugador[i], rx, ry, Colores.BLACK);
            };
        }

        int yoCroupier = yo + 190;
        int lx = xo + 7;
        ly = yoCroupier + 20;

        int rx = xo + 75;
        ry = yoCroupier + 145;

        int sx = xo + 35;
        sy = yoCroupier + 90;
        mt.configurarFuente("Serif", Font.BOLD, 18);
        mt.dibujarString("Croupier", xo + 2, yoCroupier - 10, Colores.YELLOW);

        mt.dibujarRectanguloLleno(xo, yoCroupier, cartaA, cartaL, Colores.WHITE);
            if(tipoCroupier[0] == "♥" || tipoCroupier[0] == "♦") mt.dibujarRectangulo(xo, yoCroupier, cartaA, cartaL, Colores.RED);
            else mt.dibujarRectangulo(xo, yoCroupier, cartaA, cartaL, Colores.BLACK);
            mt.configurarFuente("Serif", Font.BOLD, 40);
            if(tipoCroupier[0] == "♥" || tipoCroupier[0] == "♦") mt.dibujarString(tipoCroupier[0], sx, sy, Colores.RED);
            else mt.dibujarString(tipoCroupier[0], sx, sy, Colores.BLACK);
            mt.configurarFuente("Serif", Font.BOLD, 18);
            if(tipoCroupier[0] == "♥" || tipoCroupier[0] == "♦"){
                mt.dibujarString(barajaCroupier[0], lx, ly, Colores.RED);
                mt.dibujarString(barajaCroupier[0], rx, ry, Colores.RED);
            }
            else{
                mt.dibujarString(barajaCroupier[0], lx, ly, Colores.BLACK);
                mt.dibujarString(barajaCroupier[0], rx, ry, Colores.BLACK);
            };

        mt.mostrar();
        pausarEjecucion(15);
        mt.terminar();
    }
}

