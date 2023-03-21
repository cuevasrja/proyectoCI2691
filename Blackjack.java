import java.util.Scanner;
import java.io.Console;

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
        int[] resultado;
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
            int[] manoJugador;
            String[] manoJugadorString;
            String[] tipoManoJugador;
            int manoJugadorValor = 0;
            int cartasJugador = 2;
            int[] manoCroupier;
            String[] manoCroupierString;
            String[] tipoManoCroupier;
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
            manoJugadorString = repartirCartas(manoJugador, baraja);
            tipoManoJugador = repartirTipos(manoJugador, tipos);
            System.out.print("Sus cartas son: ");
            System.out.print(manoJugadorString[0] + " de " + tipoManoJugador[0]);
            System.out.println(" y " + manoJugadorString[1] + " de " + tipoManoJugador[1]);
            manoJugadorValor = valorCartas(manoJugador);

            // * Repartir y mostrar cartas del croupier
            // ! Solo se muestra la primera carta del croupier
            manoCroupier = repartirCartasIndice(barajaIndex);
            manoCroupierString = repartirCartas(manoCroupier, baraja);
            tipoManoCroupier = repartirTipos(manoCroupier, tipos);
            System.out.print("La carta del croupier es: ");
            System.out.println(manoCroupierString[0] + " de " + tipoManoCroupier[0]);
            manoCroupierValor = valorCartas(manoCroupier);

            // ? Turno del jugador         
                
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
                            System.out.println("Gracias por jugar!");
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
        int[] mano = new int[2];
        int[] barajaAux = baraja;
        int i = 0;
        //@ maintaining 0 <= i && i <= mano.length;
        //@ decreases mano.length - i;
        while(0 <= i && i < mano.length){
            int carta = (int) (Math.random() * barajaAux.length);
            //@ assume 0 <= carta && carta < barajaAux.length;
            mano[i] = barajaAux[carta];
            i = i+1;
        }
        return mano;
    }
    //@ requires baraja.length > 0 && baraja.length <= 56 && indices.length > 0 && indices.length <= baraja.length;
    //@ ensures (\result.length >= 2) <== (\forall int i; 0 <= i && i < \result.length; \result[i] >= 0 && \result[i] < 56);
    public static /*@ pure @*/ int[] agregarCartaAlMazo(int[] indices, int[] baraja){
        int[] mano = new int[indices.length + 1];
        int[] barajaAux = new int[baraja.length - indices.length];
        int carta = (int) (Math.random() * barajaAux.length);
        int i = 0;
        int j = 0;
        //@ maintaining 0 <= i && i <= baraja.length && 0 <= j && j <= barajaAux.length;
        //@ decreases baraja.length - i;
        while(0 <= i && i < baraja.length && 0 <= j && j < barajaAux.length){
            int k = 0;
            boolean existe = false;
            //@ maintaining 0 <= k && k <= indices.length;
            //@ decreases indices.length - k;
            while(0 <= k && k < indices.length && !existe){
                if(baraja[i] == indices[k]){
                    existe = true;
                }
                k++;
            }
            if(!existe){
                barajaAux[j] = baraja[i];
                j = j+1;
            }
            i = i+1;
        }

        i = 0;
        //@maintaining 0 <= i && i <= mano.length;
        //@decreases mano.length - i;
        while(0 <= i && i < mano.length){
            if(i < indices.length){
                mano[i] = indices[i];
            }
            else{
                //@ assume 0 <= carta && carta < barajaAux.length;
                mano[i] = barajaAux[carta];
            }
            i = i+1;
        }

        return mano;
    }
    //@ requires baraja.length > 0 && baraja.length <= 56 && indices.length < 56 && (\forall int i; 0 <= i && i < indices.length; indices[i] >= 0 && indices[i] < baraja.length);
    //@ ensures (\result.length == 2) <== (\forall int i; 0 <= i && i < \result.length; \result[i] != null);
    public static /*@ pure @*/ String[] repartirCartas(int[] indices, String[] baraja){
        String[] mano = new String[indices.length];
        int i = 0;
        //@ maintaining 0 <= i && i <= mano.length;
        //@ decreases mano.length - i;
        while(0 <= i && i < mano.length){
            mano[i] = baraja[indices[i]];
            i = i+1;
        }
        return mano;
    }
    //@ requires tipos.length == 4 && indices.length == 2 && (\forall int i; 0 <= i && i < indices.length; indices[i] >= 0 && indices[i] < tipos.length);
    //@ ensures (\result.length == 2) <== (\forall int i; 0 <= i && i < \result.length; \result[i] != null);
    public static /*@ pure @*/ String[] repartirTipos(int[] indices, String[] tipos){
        String[] tipo = new String[2];
        int i = 0;
        //@ maintaining 0 <= i && i <= tipo.length;
        //@ decreases tipo.length - i;
        while(0 <= i && i < tipo.length){
            tipo[i] = tipos[indices[i]%4];
            i = i+1;
        }
        return tipo;
    }
    //
    public static /*@ pure @*/ int valorCartas(int[] indices){
        int valor = 0;
        int i = 0;
        //@ maintaining 0 <= i && i <= indices.length;
        //@ decreases indices.length - i;
        while(0 <= i && i < indices.length){
            //@ assume 0 <= indices[i] && indices[i] < 56;
            //@ assume 0 <= valor && valor < 22;
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
        //@ ensures true;
        System.out.println("BlackJack! Usted ha ganado.");
    }

    public static int jugadorNoTieneBlackJack(int a) {
        Console consola = System.console();
        //@ requires consola != null;
        String decision = consola.readLine("Que quiere hacer? \n\n Pedir carta - Escriba '1' \n Plantarse - Escriba '2' \n Doblar - Escriba '3' \n Salir del Juego - Escriba '4'\n");
        //@ requires decision.matches("[1234]");
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
        //@ ensures accion == 0 || accion == 1 || accion == 2 || accion == 3 || accion == 4;
        return accion;
    }

    public static void jugadorTieneMasDe21(int a) {
        //@ ensures true;
        System.out.println("Usted ha perdido esta mano");
    }
    //@ requires manoCuprierValor >= 1 && manoCuprierValor <= 31;
    //@ requires manoCroupier != null && !manoCroupier.isEmpty();
    //@ requires barajaIndex >= 0 && barajaIndex < 52;
    //@ ensures \result >= 1 && \result <= 31;
    public static void accionesDelCuprier(int manoCuprierValor) {
        if(manoCuprierValor < 17) {
            //agregarCartaAlMazo(manoCroupier, barajaIndex); 
            //Actualizar
        }
        else if(manoCuprierValor >= 17 && manoCuprierValor <= 21) {
            // No hace nada
        }
        else if(manoCuprierValor > 21) {
            System.out.println("El cuprier ha perdido!");
            
        }
    }
}

