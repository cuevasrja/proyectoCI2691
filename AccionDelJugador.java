import java.util.Scanner;
public class AccionDelJugador {
    public static void jugadorTieneBlackJack(int a) { 
        System.out.println("BlackJack! Usted ha ganado.");        
    } 

    public static int jugadorNoTieneBlackJack(int a) {       
        System.out.println("Que quiere hacer? \n\n Pedir carta - Escriba '1' \n Plantarse - Escriba '2' \n Doblar - Escriba '3' \n Salir del Juego - Escriba '4'\n");                   
        Scanner opcion = new Scanner(System.in);
        int decision = Integer.parseInt(opcion.nextLine());

        int accion = 0;            
        if (decision == 1){                     
            accion = 1;                
        }else if(decision == 2){                    
            accion = 2;                
        }else if(decision == 3){                    
            accion = 3;                
        }else if(decision == 4){                    
            accion = 4;                
        }else{
            System.out.println("Error! debe introducir una de las opciones");
        };            
        opcion.close();
        return accion;       
        }   

    public static void jugadorTieneMasDe21(int a) {                
        System.out.println("Usted ha perdido esta mano");
        
    }
}

    

        
   
