import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathematicsJavaTest {

    @Test
    public void testAdd() {
        String givenExpression = "5987123123123123123628494+234342421231231328634234";
        String expected = "6221465544354354452262728";
        assertEquals(expected, MathematicsJava.SolveExpression(givenExpression));
    }

    @Test
    public void testSubtract() {
        String givenExpression = "5987123123123123123628494-234342421231231328634234";
        String expected = "5752780701891891794994260";
        assertEquals(expected, MathematicsJava.SolveExpression(givenExpression));
    }

    @Test
    public void testMultiply() {
        String givenExpression = "5987123123123123123628494*234342421231231328634234";
        String expected = "1403036928882164188328687902798969717451426263596";
        assertEquals(expected, MathematicsJava.SolveExpression(givenExpression));
    }
    @Test
    public void testDivide() {
        String givenExpression = "5987123123123123123628494/234";
        String expected = "2558599625266291933174005";
        assertEquals(expected, MathematicsJava.SolveExpression(givenExpression));
    }
}
