import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int SEPARACION = 40;
    private final int MARGEN = 10;
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posicion = MARGEN;
        JLabel[] lblCartas = new JLabel[TOTAL_CARTAS];
        int z = 0;
        for (Carta carta : cartas) {
            lblCartas[z] = carta.mostrar(pnl, posicion, MARGEN);
            posicion += SEPARACION;
            z++;
        }

        z = lblCartas.length - 1;
        for (JLabel lbl : lblCartas) {
            pnl.setComponentZOrder(lbl, z);
            z--;
        }

        pnl.repaint();
    }

    public String getGrupos() {
        String resultado = "No se encontraron grupos";

        // calcular los contadores por nombre de carta
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        // verificar si hubo grupos
        boolean hayGrupos = false;
        for (int contador : contadores) {
            if (contador >= 2) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            resultado = "Se hallaron los siguientes grupos:\n";
            for (int p = 0; p < contadores.length; p++) {
                int contador = contadores[p];
                if (contador >= 2) {
                    resultado += Grupo.values()[contador] + " de " + NombreCarta.values()[p] + "\n";
                }
            }
        }
        return resultado;
    }

    public String getEscaleras() {
        String mensaje = "";
        boolean hayEscaleras = false;

        // 1. Separar cartas por pinta
        int[][] cartasPorPinta = new int[4][10];
        int[] cantidadPorPinta = new int[4];

        for (int i = 0; i < cartas.length; i++) {
            Pinta pinta = cartas[i].getPinta();
            int valor = cartas[i].getNombre().ordinal();

            int indicePinta = 0;
            if (pinta == Pinta.TREBOL)
                indicePinta = 0;
            else if (pinta == Pinta.PICA)
                indicePinta = 1;
            else if (pinta == Pinta.CORAZON)
                indicePinta = 2;
            else if (pinta == Pinta.DIAMANTE)
                indicePinta = 3;

            cartasPorPinta[indicePinta][cantidadPorPinta[indicePinta]] = valor;
            cantidadPorPinta[indicePinta]++;
        }

        // 2. Ordenar cada pinta
        for (int p = 0; p < 4; p++) {
            for (int i = 0; i < cantidadPorPinta[p] - 1; i++) {
                for (int j = 0; j < cantidadPorPinta[p] - 1 - i; j++) {
                    if (cartasPorPinta[p][j] > cartasPorPinta[p][j + 1]) {
                        int temp = cartasPorPinta[p][j];
                        cartasPorPinta[p][j] = cartasPorPinta[p][j + 1];
                        cartasPorPinta[p][j + 1] = temp;
                    }
                }
            }
        }

        // 3. Buscar TODAS las escaleras dentro de cada pinta
        for (int p = 0; p < 4; p++) {
            int contador = 1;
            int inicio = 0;

            for (int i = 1; i < cantidadPorPinta[p]; i++) {
                if (cartasPorPinta[p][i] == cartasPorPinta[p][i - 1] + 1) {
                    contador++;
                } else {
                    if (contador >= 3) {
                        mensaje += Escalera.values()[contador - 3] + " en " + Pinta.values()[p]
                                + " de " + NombreCarta.values()[cartasPorPinta[p][inicio]]
                                + " hasta " + NombreCarta.values()[cartasPorPinta[p][i - 1]] + "\n";
                        hayEscaleras = true;
                    }
                    contador = 1;
                    inicio = i;
                }
            }

            // revisar si al final quedó una escalera pendiente
            if (contador >= 3) {
                mensaje += Escalera.values()[contador - 3] + " en " + Pinta.values()[p]
                        + " de " + NombreCarta.values()[cartasPorPinta[p][inicio]]
                        + " hasta " + NombreCarta.values()[cartasPorPinta[p][cantidadPorPinta[p] - 1]] + "\n";
                hayEscaleras = true;
            }
        }

        if (!hayEscaleras) {
            mensaje = "No hay escaleras";
        }
        return mensaje;
    }


    public int getPuntajeCartasSueltas() {
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        int puntaje = 0;
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] == 1) {
                NombreCarta nombre = NombreCarta.values()[i];
                switch (nombre) {
                    case AS:
                    case JACK:
                    case QUEEN:
                    case KING:
                        puntaje += 10;
                    
                        break;
                    default:
                        puntaje += i + 1;
                        break;
                }
            }

        }
        return puntaje;
    }
}