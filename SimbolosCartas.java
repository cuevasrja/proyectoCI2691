public class SimbolosCartas {
    
    public static void main(String[] args) {
        MaquinaDeTrazados mt = new MaquinaDeTrazados(600, 400, "Símbolos de Cartas", Colores.WHITE);
        dibujarPica(mt, 100, 100, 50);
        dibujarTrebol(mt, 250, 100, 65, 65, 55);
        dibujarDiamante(mt, 400, 100, 50);
        dibujarCorazon(mt, 100, 100, 65, 65, 55);
        mt.mostrar();
    }
    
    public static void dibujarPica(MaquinaDeTrazados mt, int x, int y, int radius) {
        
    }
    
    public static void dibujarTrebol(MaquinaDeTrazados mt, int x, int y, int largo, int ancho, int radius) {
        int[] xPoints = {x - radius+20, x, x + radius -20, x};
        int[] yPoints = {y+80, y - radius+102, y+80, y + radius};
        int nPoints = 3;
        mt.dibujarOvaloLleno(x-30, y-45, largo, ancho, Colores.BLACK);
        mt.dibujarOvaloLleno(x-60, y, largo, ancho, Colores.BLACK);
        mt.dibujarOvaloLleno(x-5, y, largo, ancho, Colores.BLACK);
        mt.dibujarPoligonoLleno(xPoints, yPoints, nPoints, Colores.BLACK);
    }
    
    public static void dibujarDiamante(MaquinaDeTrazados mt, int x, int y, int radius) {
        int[] xPoints = {x - radius, x, x + radius, x};
        int[] yPoints = {y, y - radius, y, y + radius};
        int nPoints = 4;
        mt.dibujarPoligonoLleno(xPoints, yPoints, nPoints, Colores.RED);
    }
    
    public static void dibujarCorazon(MaquinaDeTrazados mt, int x, int y, int largo, int ancho, int radius) {
        int[] xPoints = {x - radius, x, x + radius, x};
        int[] yPoints = {y, y + radius, y, y - radius};
        int nPoints = 3;
        mt.dibujarOvaloLleno(x-60, y-50, largo, ancho, Colores.RED);
        mt.dibujarOvaloLleno(x-5, y-50, largo, ancho, Colores.RED);
        mt.dibujarPoligonoLleno(xPoints, yPoints, nPoints, Colores.RED);
    }
    }
    

