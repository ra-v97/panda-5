package pl.edu.agh.panda5.utils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreSerializer {

    private List<Record> topScores = new ArrayList<>();
    private List<Record> newScores = new LinkedList<>();

    private final String databasePath;
    private  BufferedWriter writer;
    private final String pattern = "dd-MM-yyyy HH:mm:ss";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public ScoreSerializer(String databasePath) {
        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            try{
                serializeScores();
            }catch (IOException e){e.printStackTrace();}
        }));

        this.databasePath = databasePath;
        try{
            if(!isFileExists()){
                writer = new BufferedWriter(new FileWriter(databasePath));
            }
            else{
                loadScores();
                writer = new BufferedWriter(new FileWriter(databasePath,true));
            }
        }catch (IOException e){e.printStackTrace();}
    }

    public List<String> getTopScores(int number){
        return topScores.stream()
                .sorted((r1,r2)-> r2.getScore() - r1.getScore())
                .limit(number)
                .map(Record::toString)
                .collect(Collectors.toList());
    }

    public void addNewScore(int score){
        Record r = new Record(new Date(),score);
        newScores.add(r);
        topScores.add(r);
    }

    private void serializeScores() throws IOException{
        for(Record record : newScores){
            writer.write(record.toString()+"\n");
        }
        writer.close();
    }

    private boolean isFileExists(){
        File f = new File(databasePath);
        return f.exists() && !f.isDirectory();
    }

    private void loadScores() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(databasePath));
        String line;
        while ((line = reader.readLine()) != null){
            String[] output = line.split(";");
            try{
                topScores.add(new Record(simpleDateFormat.parse(output[0]),Integer.parseInt(output[1])));
            }catch (ParseException e){
                System.out.println("Cannot parse date;");
            }
        }
}

    private class Record{
        private final Date gameDate;
        private final int result;

        private Record(Date date, int result){
            this.gameDate=date;
            this.result = result;
        }

        private int getScore(){
            return this.result;
        }

        @Override
        public String toString(){
            return simpleDateFormat.format(gameDate) + ";" + result;
        }
    }
}
