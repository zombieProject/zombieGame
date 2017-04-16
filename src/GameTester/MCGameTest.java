package GameTester;

import ai.AshAi;
import ai.AshAiMC;

public class MCGameTest extends GameTest {
    @Override
    public AshAi createAI() {
        return new AshAiMC();
    }
}
