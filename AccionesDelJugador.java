import java.util.Scanner;
public class AccionesDelJugador {

    public static void jugadorTieneBlackJack(int a) {       

        if(a == 21){

            System.out.println("BlackJack! Usted ha ganado.");

        }
    }
        
        public static String jugadorNoTieneBlackJack(int a) {
            
            if(a < 21){
                
                System.out.println("Que quiere hacer?");
                System.out.println("Pedir carta (P)");
                System.out.println("Plantarse (S)");
                System.out.println("Doblar(D)");
                System.out.println("Salir del Juego(E)a");
            }
                Scanner opcion = new Scanner (System.in);
                String decision = opcion.nextLine();

                return decision;               
                
            }
            
            public static void jugadorTieneMasDe21(int a) {
                
                if(a > 21){
                System.out.println("Usted ha perdido esta mano");
                }
            }
        }
        
   
