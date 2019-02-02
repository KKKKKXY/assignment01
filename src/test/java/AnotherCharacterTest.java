import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.input.KeyCode;
import model.Direction;
import model.Character2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import controller.DrawingLoop;
import controller.GameLoop;
import model.Character;
import view.Platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static junit.framework.Assert.*;
import static view.Platform.GROUND;
import static view.Platform.HEIGHT;

@RunWith(JfxRunner.class)
public class AnotherCharacterTest{
    private Character2 floatingCharacter2;
    private Character2 onGroundCharacter2;
    private Character onGroundCharacter1;

    private DrawingLoop drawingLoopUnderTest;
    private Platform platformUnderTest;
    private GameLoop gameLoopUnderTest;
    private Method updateMethod;
    private Method checkDrawingCollisionMethod;
    private Method paintMethod;

    private KeyCode leftKey = KeyCode.A;
    private KeyCode rightKey = KeyCode.D;
    private KeyCode upKey = KeyCode.W;
    private KeyCode downKey = KeyCode.S;
    @Before
    public void setUp() throws NoSuchMethodException {
        floatingCharacter2 = new Character2(50,100);
        onGroundCharacter2 = new Character2(1300, GROUND + ((HEIGHT - GROUND) / 2) - 50);
        onGroundCharacter1 = new Character(50, GROUND+((HEIGHT-GROUND)/2)-50);


        drawingLoopUnderTest = new DrawingLoop(platformUnderTest);
        platformUnderTest = new Platform();
        gameLoopUnderTest = new GameLoop(platformUnderTest);
        updateMethod = GameLoop.class.getDeclaredMethod("update2", Character2.class);
        updateMethod.setAccessible(true);
        checkDrawingCollisionMethod = DrawingLoop.class.getDeclaredMethod("checkDrawCollisions2",Character2.class,ArrayList.class);
        checkDrawingCollisionMethod.setAccessible(true);
        paintMethod = DrawingLoop.class.getDeclaredMethod("paint2",Character2.class,ArrayList.class);
        paintMethod.setAccessible(true);
    }
    @Test
    public void whenLeftKeyIsPressedThenCharacterMoveLeft() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(leftKey);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter2);
        Direction direction = onGroundCharacter2.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.A, direction);
    }
    @Test
    public void whenRightKeyIsPressedThenCharacterMoveRight() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(rightKey);

        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter2);
        Direction direction = onGroundCharacter2.getDirection();
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.D, direction);

    }
    @Test
    public void whenUPKeyIsPressedThenCharacterMoveUP() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(upKey);
        ArrayList<Character2> c = new ArrayList<>();
        c.add(floatingCharacter2);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter2);
        Direction direction = onGroundCharacter2.getDirection();
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.W, direction);

    }
    @Test
    public void whenDownKeyIsPressedThenCharacterMoveDown() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(downKey);
        ArrayList<Character2> c = new ArrayList<>();
        c.add(floatingCharacter2);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter2);
        Direction direction = onGroundCharacter2.getDirection();
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.S, direction);

    }

    @Test
    public void whenNoneKeyIsPressedThenCharacterNotMove() throws IllegalAccessException,InvocationTargetException {
        ArrayList<Character2> c = new ArrayList<>();
        c.add(floatingCharacter2);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter2);
        Direction direction = onGroundCharacter2.getDirection();
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.NONE, direction);
    }
    @Test
    public void ifCharacterIsOnGroundAndUpKeyIsPressedThenCharacterMoveUp() throws InvocationTargetException, IllegalAccessException {
        int start_y = onGroundCharacter2.getY();
        ArrayList<Character> c = new ArrayList<>();
        c.add(onGroundCharacter1);
        checkDrawingCollisionMethod.invoke(drawingLoopUnderTest,onGroundCharacter2,c);
        platformUnderTest.getKeys().add(upKey);
        Direction direction = onGroundCharacter2.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(upKey));
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter2);
        paintMethod.invoke(drawingLoopUnderTest,onGroundCharacter2,c);
        assertNotSame(Direction.W, direction);
        assertTrue(onGroundCharacter2.getY() < start_y);
    }
    @Test
    public void ifCharacterIsOnGroundAndDownKeyIsPressedThenCharacterMoveDown() throws InvocationTargetException, IllegalAccessException {
        int start_y = onGroundCharacter2.getY();
        ArrayList<Character> c = new ArrayList<>();
        c.add(onGroundCharacter1);
        checkDrawingCollisionMethod.invoke(drawingLoopUnderTest,onGroundCharacter2,c);
        platformUnderTest.getKeys().add(downKey);
        Direction direction = onGroundCharacter2.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(downKey));
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter2);
        paintMethod.invoke(drawingLoopUnderTest,onGroundCharacter2,c);
        assertNotSame(Direction.S, direction);
        assertFalse(onGroundCharacter2.getY() < start_y);
    }
    @Test
    public void ifCharacterIsNotOnGroundAndUpKeyIsPressedThenCharacterWillNotUp() throws InvocationTargetException, IllegalAccessException {
        int start_y = floatingCharacter2.getY();
        ArrayList<Character> c = new ArrayList<>();
        c.add(onGroundCharacter1);
        checkDrawingCollisionMethod.invoke(drawingLoopUnderTest,floatingCharacter2,c);
        platformUnderTest.getKeys().add(upKey);
        Direction direction = floatingCharacter2.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(upKey));
        updateMethod.invoke(gameLoopUnderTest,floatingCharacter2);
        paintMethod.invoke(drawingLoopUnderTest,floatingCharacter2,c);
        assertNotSame(Direction.W, direction);
        assertFalse(floatingCharacter2.getY() < start_y);
    }
    @Test
    public void ifCharacterIsNotOnGroundAndUpKeyIsPressedThenCharacterWillNotDown() throws InvocationTargetException, IllegalAccessException {
        int start_y = floatingCharacter2.getY();
        ArrayList<Character> c = new ArrayList<>();
        c.add(onGroundCharacter1);
        checkDrawingCollisionMethod.invoke(drawingLoopUnderTest,floatingCharacter2,c);
        platformUnderTest.getKeys().add(downKey);
        Direction direction = floatingCharacter2.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(downKey));
        updateMethod.invoke(gameLoopUnderTest,floatingCharacter2);
        paintMethod.invoke(drawingLoopUnderTest,floatingCharacter2,c);
        assertNotSame(Direction.S, direction);
        assertFalse(floatingCharacter2.getY() < start_y);
    }
}