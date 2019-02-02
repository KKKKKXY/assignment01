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
public class CharacterTest {
    private Character floatingCharacter;
    private Character onGroundCharacter;
    private Character2 onGroundCharacter2;

    private DrawingLoop drawingLoopUnderTest;
    private Platform platformUnderTest;
    private GameLoop gameLoopUnderTest;
    private Method updateMethod;
    private Method checkDrawingCollisionMethod;
    private Method paintMethod;

    private KeyCode leftKey = KeyCode.LEFT;
    private KeyCode rightKey = KeyCode.RIGHT;
    private KeyCode upKey = KeyCode.UP;
    private KeyCode downKey = KeyCode.DOWN;
    @Before
    public void setUp() throws NoSuchMethodException {
        floatingCharacter = new Character(50,100);
        onGroundCharacter = new Character(50, GROUND+((HEIGHT-GROUND)/2)-50);
        onGroundCharacter2 = new Character2(1300, GROUND + ((HEIGHT - GROUND) / 2) - 50);


        drawingLoopUnderTest = new DrawingLoop(platformUnderTest);
        platformUnderTest = new Platform();
        gameLoopUnderTest = new GameLoop(platformUnderTest);
        updateMethod = GameLoop.class.getDeclaredMethod("update", Character.class);
        updateMethod.setAccessible(true);
        checkDrawingCollisionMethod = DrawingLoop.class.getDeclaredMethod("checkDrawCollisions",Character.class,ArrayList.class);
        checkDrawingCollisionMethod.setAccessible(true);
        paintMethod = DrawingLoop.class.getDeclaredMethod("paint",Character.class,ArrayList.class);
        paintMethod.setAccessible(true);
    }
    @Test
    public void whenLeftKeyIsPressedThenCharacterMoveLeft() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(leftKey);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter);
        Direction direction = onGroundCharacter.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.LEFT, direction);
    }
    @Test
    public void whenRightKeyIsPressedThenCharacterMoveRight() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(rightKey);

        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter);
        Direction direction = onGroundCharacter.getDirection();
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.RIGHT, direction);

    }
    @Test
    public void whenUPKeyIsPressedThenCharacterMoveUP() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(upKey);
        ArrayList<Character> c = new ArrayList<>();
        c.add(floatingCharacter);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter);
        Direction direction = onGroundCharacter.getDirection();
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.UP, direction);

    }
    @Test
    public void whenDownKeyIsPressedThenCharacterMoveDown() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(downKey);
        ArrayList<Character> c = new ArrayList<>();
        c.add(floatingCharacter);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter);
        Direction direction = onGroundCharacter.getDirection();
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.DOWN, direction);

    }
    @Test
    public void whenRightDownKeyIsPressedThenCharacterMoveDown() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(downKey);
        platformUnderTest.getKeys().add(rightKey);
        ArrayList<Character> c = new ArrayList<>();
        c.add(floatingCharacter);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter);
        Direction direction = onGroundCharacter.getDirection();
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.DOWNRIGHT, direction);

    }
    @Test
    public void whenRightUpKeyIsPressedThenCharacterMoveUp() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(upKey);
        platformUnderTest.getKeys().add(rightKey);
        ArrayList<Character> c = new ArrayList<>();
        c.add(floatingCharacter);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter);
        Direction direction = onGroundCharacter.getDirection();
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.UPRIGHT, direction);

    }
    @Test
    public void whenLeftDownKeyIsPressedThenCharacterMoveDown() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(downKey);
        platformUnderTest.getKeys().add(leftKey);
        ArrayList<Character> c = new ArrayList<>();
        c.add(floatingCharacter);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter);
        Direction direction = onGroundCharacter.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.DOWNLEFT, direction);
    }
    @Test
    public void whenLeftUpKeyIsPressedThenCharacterMoveUp() throws IllegalAccessException,InvocationTargetException {
        platformUnderTest.getKeys().add(upKey);
        platformUnderTest.getKeys().add(leftKey);
        ArrayList<Character> c = new ArrayList<>();
        c.add(floatingCharacter);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter);
        Direction direction = onGroundCharacter.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(true, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.UPLEFT, direction);
    }
    @Test
    public void whenNoneKeyIsPressedThenCharacterNotMove() throws IllegalAccessException,InvocationTargetException {
        ArrayList<Character> c = new ArrayList<>();
        c.add(floatingCharacter);
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter);
        Direction direction = onGroundCharacter.getDirection();
        assertEquals(false, platformUnderTest.getKeys().isPressed(leftKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(rightKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(upKey));
        assertEquals(false, platformUnderTest.getKeys().isPressed(downKey));
        assertEquals(Direction.NONE, direction);
    }
    @Test
    public void ifCharacterIsOnGroundAndUpKeyIsPressedThenCharacterMoveUp() throws InvocationTargetException, IllegalAccessException {
        int start_y = onGroundCharacter.getY();
        ArrayList<Character2> c = new ArrayList<>();
        c.add(onGroundCharacter2);
        checkDrawingCollisionMethod.invoke(drawingLoopUnderTest,onGroundCharacter,c);
        platformUnderTest.getKeys().add(upKey);
        Direction direction = onGroundCharacter.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(upKey));
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter);
        paintMethod.invoke(drawingLoopUnderTest,onGroundCharacter,c);
        assertNotSame(Direction.UP, direction);
        assertTrue(onGroundCharacter.getY() < start_y);
    }
    @Test
    public void ifCharacterIsOnGroundAndDownKeyIsPressedThenCharacterMoveDown() throws InvocationTargetException, IllegalAccessException {
        int start_y = onGroundCharacter.getY();
        ArrayList<Character2> c = new ArrayList<>();
        c.add(onGroundCharacter2);
        checkDrawingCollisionMethod.invoke(drawingLoopUnderTest,onGroundCharacter,c);
        platformUnderTest.getKeys().add(downKey);
        Direction direction = onGroundCharacter.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(downKey));
        updateMethod.invoke(gameLoopUnderTest,onGroundCharacter);
        paintMethod.invoke(drawingLoopUnderTest,onGroundCharacter,c);
        assertNotSame(Direction.DOWN, direction);
        assertFalse(onGroundCharacter.getY() < start_y);
    }
    @Test
    public void ifCharacterIsNotOnGroundAndUpKeyIsPressedThenCharacterWillNotUp() throws InvocationTargetException, IllegalAccessException {
        int start_y = floatingCharacter.getY();
        ArrayList<Character2> c = new ArrayList<>();
        c.add(onGroundCharacter2);
        checkDrawingCollisionMethod.invoke(drawingLoopUnderTest,floatingCharacter,c);
        platformUnderTest.getKeys().add(upKey);
        Direction direction = floatingCharacter.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(upKey));
        updateMethod.invoke(gameLoopUnderTest,floatingCharacter);
        paintMethod.invoke(drawingLoopUnderTest,floatingCharacter,c);
        assertNotSame(Direction.UP, direction);
        assertFalse(floatingCharacter.getY() < start_y);
    }
    @Test
    public void ifCharacterIsNotOnGroundAndUpKeyIsPressedThenCharacterWillNotDown() throws InvocationTargetException, IllegalAccessException {
        int start_y = floatingCharacter.getY();
        ArrayList<Character2> c = new ArrayList<>();
        c.add(onGroundCharacter2);
        checkDrawingCollisionMethod.invoke(drawingLoopUnderTest,floatingCharacter,c);
        platformUnderTest.getKeys().add(downKey);
        Direction direction = floatingCharacter.getDirection();
        assertEquals(true, platformUnderTest.getKeys().isPressed(downKey));
        updateMethod.invoke(gameLoopUnderTest,floatingCharacter);
        paintMethod.invoke(drawingLoopUnderTest,floatingCharacter,c);
        assertNotSame(Direction.DOWN, direction);
        assertFalse(floatingCharacter.getY() < start_y);
    }
}