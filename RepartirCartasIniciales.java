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
    public static /*@ pure @*/ int[] repartirCartasIndice(int[] baraja){
        int[] mano = new int[2];
        int[] barajaAux = baraja;
        int carta1 = (int) (Math.random() * barajaAux.length);
        mano[0] = barajaAux[carta1];
        barajaAux = eliminarCartaIndice(barajaAux, carta1);
        int carta2 = (int) (Math.random() * barajaAux.length);
        //@ assume 0 <= carta2 && carta2 < barajaAux.length;
        mano[1] = barajaAux[carta2];
        barajaAux = eliminarCartaIndice(barajaAux, carta2);
        return mano;
    }
    //@ requires baraja.length > 0 && baraja.length <= 56 &&carta >= 0 && carta < baraja.length;
    //@ ensures \result.length < baraja.length;
    public static /*@ pure @*/ int[] eliminarCartaIndice(int[] baraja, int carta){
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
    //@ requires baraja.length > 0 && baraja.length <= 56 && indices.length == 2 && (\forall int i; 0 <= i && i < indices.length; indices[i] >= 0 && indices[i] < baraja.length);
    //@ ensures (\result.length == 2) <== (\forall int i; 0 <= i && i < \result.length; \result[i] != null);
    public static /*@ pure @*/ String[] repartirCartas(int[] indices, String[] baraja){
        String[] mano = new String[2];
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
}