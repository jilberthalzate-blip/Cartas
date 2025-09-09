public String getEscaleras() {
    String mensaje = "";
    boolean hayEscaleras = false;

    // 1. Separar cartas por pinta
    int[][] cartasPorPinta = new int[4][10]; // máx 10 cartas por jugador
    int[] cantidadPorPinta = new int[4]; // cuántas cartas tiene cada pinta

    for (int i = 0; i < cartas.length; i++) {
        PintaCarta pinta = cartas[i].getPinta();
        int valor = cartas[i].getNombre().ordinal(); // ACE=1 ... KING=13

        int indicePinta = 0;
        if (pinta == PintaCarta.TREBOL) indicePinta = 0;
        else if (pinta == PintaCarta.PICA) indicePinta = 1;
        else if (pinta == PintaCarta.CORAZON) indicePinta = 2;
        else if (pinta == PintaCarta.DIAMANTE) indicePinta = 3;

        cartasPorPinta[indicePinta][cantidadPorPinta[indicePinta]] = valor;
        cantidadPorPinta[indicePinta]++;
    }

    
    // 2. Ordenar las cartas dentro de cada pinta (burbuja)
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
// 3. Buscar secuencias de 3 o más consecutivos
    for (int p = 0; p < 4; p++) {
        int contador = 1;
        for (int i = 1; i < cantidadPorPinta[p]; i++) {
            if (cartasPorPinta[p][i] == cartasPorPinta[p][i - 1] + 1) {
                contador++;
            } else {
                if (contador >= 3) {
                    mensaje += "Escalera de " + contador + " cartas en " + PintaCarta.values()[p + 1] + "\n";
                    hayEscaleras = true;
                }
                "contador = 1; // reiniciar secuencia"
            }
        }
        if (contador >= 3) {
            mensaje += "Escalera de " + contador + " cartas en " + PintaCarta.values()[p + 1] + "\n";
            hayEscaleras = true;
        }
    }

    if (!hayEscaleras) {
        mensaje = "No hay escaleras";
    }
}
