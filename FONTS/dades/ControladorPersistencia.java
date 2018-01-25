package dades;

public class ControladorPersistencia {
    private Ranking ranking;
    private Save save;


    public ControladorPersistencia() {
        ranking = new Ranking();
        save = new Save();
    }

    public String getRankingRow(int i) {
        return ranking.getRankingRow(i);
    }

    public void addScore(String name, int minScore, int difficulty){
        ranking.addScore(name, minScore, difficulty);
    }

    public void saveSet(String stringJSON){
        save.saveSet(stringJSON);
    }

    public String loadSet(){
        return save.loadSet();
    }

    public boolean savedGame(){return save.savedGame();}
}