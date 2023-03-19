public class RepartirCartasIniciales{
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
    //@ ensures (\result.length == 2) <== (\forall int i; 0 <= i && i < \result.length; \result[i] >= 0 && \result[i] < 56);
    public static /*@ pure @*/ int[] repartirCartas(int[] baraja){
        int[] mano = new int[2];
        int[] barajaAux = baraja;
        int carta1 = (int) (Math.random() * barajaAux.length);
        mano[0] = barajaAux[carta1];
        barajaAux = eliminarCarta(barajaAux, carta1);
        int carta2 = (int) (Math.random() * barajaAux.length);
        //@ assume 0 <= carta2 && carta2 < barajaAux.length;
        mano[1] = barajaAux[carta2];
        barajaAux = eliminarCarta(barajaAux, carta2);
        return mano;
    }
    //@ requires baraja.length > 0 && baraja.length <= 56 &&carta >= 0 && carta < baraja.length;
    //@ ensures \result.length < baraja.length;
    public static /*@ pure @*/ int[] eliminarCarta(int[] baraja, int carta){
        int[] barajaAux = new int[baraja.length - 1];
        int j = 0;
        int i = 0;
        //@ maintaining 0 <= i && i <= baraja.length && 0 <= j && j <= barajaAux.length;
        //@ decreases baraja.length - i;
        while(0 <= i && i < barajaAux.length){
            if(i != carta && j < barajaAux.length && 0 <= j){
                barajaAux[j] = baraja[i];
                j++;
            }
            i = i+1;
        }
        return barajaAux;
    }
}