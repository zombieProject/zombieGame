package GameTester;

import ai.AshAi;
import ai.AshAiMM;

public class MMGameTest extends GameTest {
    @Override
    public AshAi createAI() {
        return new AshAiMM();
    }
}
