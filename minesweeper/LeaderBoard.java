package trabs.trab3.anexos.minesweeper;

import java.io.*;
import java.util.*;

public class LeaderBoard {
    // Caminho para o ficheiro que guarda os recordes
    private static final String FILE_NAME = "C:\\ISEL\\3ºsemestre\\PG 3\\Trabalho\\PG3\\src\\trabs\\trab3\\anexos\\minesweeper\\LeaderBoard.txt";
    // Número máximo de recordes permitidos por nível de dificuldade
    private static final int MAX_RECORDS = 10;
    // Estrutura que associa os níveis de dificuldade a listas de recordes
    private static final Map<String, List<PlayerRecord>> records = new HashMap<>();

    // Carrega os recordes assim que a classe é usada
    static {
        loadRecords();
    }

    // Carrega os recordes do ficheiro para a estrutura "records"
    private static void loadRecords() {
        File file = new File(FILE_NAME);
        // Se o ficheiro não existir, termina o carregamento
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            // Processa cada linha do ficheiro
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length == 3) {
                    String playerName = parts[0].trim(); // Nome do jogador
                    String level = parts[1].trim();     // Nível de dificuldade
                    String[] timeParts = parts[2].trim().split(":"); // Tempo
                    if (timeParts.length == 2) {
                        try {
                            int minutes = Integer.parseInt(timeParts[0].trim());
                            int seconds = Integer.parseInt(timeParts[1].trim());
                            int time = minutes * 60 + seconds;

                            // Adiciona o recorde ao nível correspondente
                            records.putIfAbsent(level, new ArrayList<>());
                            records.get(level).add(new PlayerRecord(playerName, time));
                        } catch (NumberFormatException e) {
                            // Ignora as linhas mal formatadas
                        }
                    }
                }
            }
            // Ordena e limita os recordes
            sortAndTrimRecords();
        } catch (IOException e) {
            // Trata erros de leitura do ficheiro
        }
    }

    // Salva os recordes no ficheiro
    private static void saveRecords() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, List<PlayerRecord>> entry : records.entrySet()) {
                String level = entry.getKey();
                for (PlayerRecord record : entry.getValue()) {
                    // Escreve os recordes no formato "nome - nível - tempo"
                    writer.write(record.name + " - " + level + " - " + PlayerRecord.formatTime(record.time) + "\n");
                }
            }
        } catch (IOException e) {
            // Trata erros de gravação no ficheiro
        }
    }

    // Atualiza ou adiciona um recorde para um jogador em um determinado nível
    public static void updateRecord(String level, String playerName, int time) {
        records.putIfAbsent(level, new ArrayList<>());
        List<PlayerRecord> levelRecords = records.get(level);

        // Verifica se o jogador já tem um recorde neste nível de dificuldade
        PlayerRecord existingRecord = null;
        for (PlayerRecord record : levelRecords) {
            if (record.name.equals(playerName)) {
                existingRecord = record;
                break;
            }
        }

        if (existingRecord != null) {
            // Atualiza o tempo apenas se o novo tempo for melhor
            if (time < existingRecord.time) {
                existingRecord.time = time;
            }
        } else {
            // Adiciona um novo recorde
            levelRecords.add(new PlayerRecord(playerName, time));
        }

        // Ordena e limita os recordes
        sortAndTrimRecords();
        // Salva os recordes atualizados no ficheiro
        saveRecords();
    }

    // Ordena os recordes por tempo e mantém apenas os 10 melhores
    private static void sortAndTrimRecords() {
        for (Map.Entry<String, List<PlayerRecord>> entry : records.entrySet()) {
            List<PlayerRecord> levelRecords = entry.getValue();
            Collections.sort(levelRecords); // Ordena por tempo (menor primeiro)
            // Remove os recordes além do limite
            if (levelRecords.size() > MAX_RECORDS) {
                levelRecords.subList(MAX_RECORDS, levelRecords.size()).clear();
            }
        }
    }

    // Devolve os 10 melhores recordes de um nível específico como uma string
    public static String getTopRecords(String level) {
        List<PlayerRecord> levelRecords = records.getOrDefault(level, new ArrayList<>());
        StringBuilder builder = new StringBuilder("Top 10 - " + level + "\n");

        for (int i = 0; i < levelRecords.size(); i++) {
            builder.append((i + 1)).append(". ").append(levelRecords.get(i)).append("\n");
        }

        return builder.toString();
    }

    // Devolve o melhor tempo de um jogador num nível específico
    public static String getPlayerRecord(String level, String playerName) {
        List<PlayerRecord> levelRecords = records.getOrDefault(level, new ArrayList<>());

        for (PlayerRecord record : levelRecords) {
            if (record.name.equals(playerName)) {
                return playerName + " - Best time on " + level + " level: " + record;
            }
        }
        return playerName + " has no records in " + level + " level.";
    }
}
