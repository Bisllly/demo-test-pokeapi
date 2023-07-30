package api.features;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SerenityRunner.class)
public class SamplePokeApiStory {
    private static final String END_POINT = "https://beta.pokeapi.co/graphql/v1beta";
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/queries/samplePokeAPIQuery";
    Actor bisllly = Actor.named("bisllly");

    @Before
    public void tearUp () { bisllly.whoCan(CallAnApi.at(END_POINT)); }

    @Test
    public void samplePokeApi() {
        bisllly.attemptsTo(
                postRequestFrom(FILE_PATH)
        );
    }

    private Performable postRequestFrom(String filePath) {
        return Post.to("/")
                .with(requestSpecification ->
                        requestSpecification.header("Content-Type", "application/json")
                                .body(getFileContent(filePath))
                );
    }

    private String getFileContent(String path) {
        byte[] fileContent = new byte[0];
        try {
            fileContent = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(fileContent);
    }
}
