package GameTester;

import ai.AshAi;
import ai.AshAiDT;

public class DTGameTest extends GameTest {
    @Override
    public AshAi createAI() {
        return new AshAiDT();
    }
}
