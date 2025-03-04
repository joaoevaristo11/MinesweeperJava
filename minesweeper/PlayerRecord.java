package trabs.trab3.anexos.minesweeper;

public class PlayerRecord implements Comparable<PlayerRecord> {
    public String name; // Nome do jogador
    public int time;    // Tempo em segundos

    // Construtor para inicializar os valores
    public PlayerRecord(String name, int time) {
        this.name = name;
        this.time = time;
    }

    // Compara os tempos para ordenar os recordes
    @Override
    public int compareTo(PlayerRecord other) {
        return Integer.compare(this.time, other.time);
    }

    // Formata a saída do recorde como "nome - tempo (minutos:segundos)"
    @Override
    public String toString() {
        return name + " - " + formatTime(time);
    }

    // Converte tempo de segundos para o formato "minutos:segundos"
    public static String formatTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return minutes + ":" + String.format("%02d", seconds);
    }
}


