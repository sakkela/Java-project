public class Rectangulator {
 
  public static void main (String [] args){
    int length = Integer.parseInt(args[0]);
    int width  = Integer.parseInt(args[1]);
   
    Rectangle myRec = new Rectangle( length, width);
    
    String output = String.format("*** Your Rectangle *** \n\nLenght: %d\nWidth: %d\nArea: %d\nPerimeter: %d\n\n", myRec.length, myRec.width, myRec.getArea(), myRec.getPerimeter());
    
    System.out.println(output);
  }
 
}
