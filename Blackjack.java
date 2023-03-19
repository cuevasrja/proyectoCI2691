import java.util.Scanner;

public class Blackjack{
    public static void main(String[] args){
        int creditos = 100;
        boolean continuar = true;
        int juegosMax = 5;
        int apuestaMin = 10;
        int[] baraja = {
            0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,
            28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,
            53,54,55
        };
        int[] manoJugador;
        int[] manoCroupier;
        int[] resultado;
        int juegos = 0;
        int ganadas = 0;
        int perdidas = 0;
        int empates = 0;

        System.out.print("Ingrese su nombre: ");
        Scanner lectura = new Scanner (System.in);
        String nombre = lectura.nextLine();
        System.out.println("Bienvenido " + nombre + "!");

        while(continuar && juegos < juegosMax && creditos >= apuestaMin){
            System.out.println("Sus creditos son: " + creditos);
            System.out.print("Ingrese su apuesta: ");
            int apuesta = RepartirCartasIniciales.pedirApuesta(creditos, lectura.nextInt());
            while(apuesta == 0){
                System.out.println("Apuesta invalida!");
                System.out.print("Ingrese su apuesta: ");
                apuesta = RepartirCartasIniciales.pedirApuesta(creditos, lectura.nextInt());
            }
            System.out.println("Apuesta: " + apuesta);
            creditos -= apuesta;
            System.out.println("Sus creditos restantes son: " + creditos);
            manoJugador = RepartirCartasIniciales.repartirCartas(baraja);
            System.out.println("Sus cartas son: " + manoJugador[0] + " y " + manoJugador[1]);
            manoCroupier = RepartirCartasIniciales.repartirCartas(baraja);
            System.out.println("Las cartas del croupier son: " + manoCroupier[0] + " y " + manoCroupier[1]);
            juegos++;
        }
        lectura.close();
        System.out.println("Juegos jugados: " + juegos + " de " + juegosMax);
        System.out.println("Juegos ganados: " + ganadas);
        System.out.println("Juegos perdidos: " + perdidas);
        System.out.println("Juegos empatados: " + empates);
        System.out.println("Creditos restantes: " + creditos);
        System.out.println("Gracias por jugar!");
    }
}