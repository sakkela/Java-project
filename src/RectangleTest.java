import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RectangleTest{
    Rectangle myRec = new Rectangle(5,6);
    @Test
    public void testGetArea(){
    assertEquals(myRec.getArea(),30);
    }
    
      @Test
    public void testGetPerimeter(){
    assertEquals(myRec.getPerimeter(),22);
    }
    
      @Test
    public void testLength(){
    assertEquals(myRec.length,5);
    }
      @Test
    public void testWidth(){
    assertEquals(myRec.width,6);
    }
}