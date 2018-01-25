package dades;

import java.io.*;

public class Save {
    private File fitxer;

    public Save(){
        fitxer = new File("mastermind.json");
    }

    public boolean savedGame(){
        return fitxer.exists();
    }

    public void saveSet(String stringJSON){
        try{
            fitxer.createNewFile();
            FileWriter fw = new FileWriter(fitxer);
            fw.write(stringJSON);
            fw.flush();
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String loadSet(){

        if(fitxer.exists()){
            String stringJSON = "";
            try{
                FileReader fr = new FileReader(fitxer);
                BufferedReader br = new BufferedReader(fr);
                stringJSON = br.readLine();
                fr.close();
            }catch(IOException e){
                e.printStackTrace();
            }
            fitxer.delete();
            return stringJSON;
        }
        else System.out.println("No existeix l'arxiu.");
        return "";
    }
}
