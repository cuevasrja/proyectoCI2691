import java.util.Scanner;

public class Blackjack{
    public static void main(String[] args){
        int creditos = 100;
        boolean continuar = true;
        int juegosMax = 5;
        int apuestaMin = 10;
        String[] tipos = {"Corazones", "Picas", "Diamantes", "Treboles"};
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
            "J", "J", "J", "Q", "Q", "Q", "Q", "K", "K", "K", "K", "Comodin", "Comodin", 
            "Comodin", "Comodin"
        };
        int[] manoJugador;
        String[] manoJugadorString;
        String[] tipoManoJugador;
        int manoJugadorValor = 0;
        int[] manoCroupier;
        String[] manoCroupierString;
        String[] tipoManoCroupier;
        int manoCroupierValor = 0;
        int[] resultado;
        int juegos = 0;
        int ganadas = 0;
        int perdidas = 0;
        int empates = 0;

        // Bienvenida y pedir nombre
        System.out.println("Bienvenido al juego de Blackjack!");
        System.out.print("Ingrese su nombre: ");
        // Leer nombre y declarar Scanner
        Scanner lectura = new Scanner (System.in);
        String nombre = lectura.nextLine();
        System.out.println("Bienvenido " + nombre + "!");

        while(continuar && juegos < juegosMax && creditos >= apuestaMin){
            // Mostrar creditos y pedir apuesta
            System.out.println("Sus creditos son: " + creditos);
            System.out.print("Ingrese su apuesta: ");
            int apuesta = RepartirCartasIniciales.pedirApuesta(creditos, lectura.nextInt());
            // Validar apuesta, si es invalida pedir de nuevo
            while(apuesta == 0){
                System.out.println("Apuesta invalida!");
                System.out.print("Ingrese su apuesta: ");
                apuesta = RepartirCartasIniciales.pedirApuesta(creditos, lectura.nextInt());
            }
            // Mostrar apuesta y restar creditos disponibles
            System.out.println("Apuesta: " + apuesta);
            // TODO: Los creditos se restan al final de cada juego. Aun asi, se restan aqui para que el usuario no pueda apostar mas de lo que se tiene.
            creditos -= apuesta;
            System.out.println("Sus creditos restantes son: " + creditos);

            // * Repartir y mostrar cartas del jugador
            manoJugador = RepartirCartasIniciales.repartirCartasIndice(barajaIndex);
            manoJugadorString = RepartirCartasIniciales.repartirCartas(manoJugador, baraja);
            tipoManoJugador = RepartirCartasIniciales.repartirTipos(manoJugador, tipos);
            System.out.print("Sus cartas son: ");
            System.out.print(manoJugadorString[0] + " de " + tipoManoJugador[0]);
            System.out.println(" y " + manoJugadorString[1] + " de " + tipoManoJugador[1]);
            manoJugadorValor = RepartirCartasIniciales.valorCartas(manoJugador);

            // * Repartir y mostrar cartas del croupier
            // ! Solo se muestra la primera carta del croupier
            manoCroupier = RepartirCartasIniciales.repartirCartasIndice(barajaIndex);
            manoCroupierString = RepartirCartasIniciales.repartirCartas(manoCroupier, baraja);
            tipoManoCroupier = RepartirCartasIniciales.repartirTipos(manoCroupier, tipos);
            System.out.print("La carta del croupier es: ");
            System.out.println(manoCroupierString[0] + " de " + tipoManoCroupier[0]);
            manoCroupierValor = RepartirCartasIniciales.valorCartas(manoCroupier);

            
            juegos++;
        }
        // Cerrar Scanner
        lectura.close();
        // Mostrar resultados y finalizar juego
        // System.out.println("Juegos jugados: " + juegos + " de " + juegosMax);
        // System.out.println("Juegos ganados: " + ganadas);
        // System.out.println("Juegos perdidos: " + perdidas);
        // System.out.println("Juegos empatados: " + empates);
        // System.out.println("Creditos restantes: " + creditos);
        System.out.println("Gracias por jugar!");
    }
}