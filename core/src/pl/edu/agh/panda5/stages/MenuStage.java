package pl.edu.agh.panda5.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.edu.agh.panda5.Panda5;
import pl.edu.agh.panda5.utils.ScoreSerializer;

import java.util.List;
import java.util.stream.Collectors;

public class MenuStage extends Stage {

    private Panda5 game;
    private ScoreSerializer serializer;

    public MenuStage(Panda5 game){
        this.game = game;
        serializer = game.getSerializer();

        setUpInputProcessor();
        //setUpMenuButtons(textures.get("StartButton"),textures.get("ScoreButton"),textures.get("ExitButton"));
    }

    public List<String> showTopTen(){
        return serializer
                .getTopScores(8)
                .stream()
                .map(s-> s.replace(";","   Scores-> "))
                .collect(Collectors.toList());
    }

    private void setUpInputProcessor() {
        Gdx.input.setInputProcessor(this);
    }

}
