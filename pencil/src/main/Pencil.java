package main;

import test.PencilTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Pencil {

    public String Text;
    public int pointDurability;
    public int initialDurability;
    public int bodyLength;
    public int eraserDurability;
    
    private int sharpeningRate = 40000; // for future use, possibly different sharpening rates for different sharpeners

    private EraseData eData = null;

    //default
    public Pencil() {
        Text = "";
        pointDurability = 40000;
        initialDurability = pointDurability;
        bodyLength = 10;
        eraserDurability = 2000;
    }

    // Params: iText = text on a sheet of paper,
    //          iPoint = initial point durability
    //          iBodyLength = initial body length
    //          iEraser = initial eraser durability
    public Pencil(String iText, int iPoint, int iBodyLength, int iEraser) {
        Text = iText;
        pointDurability = iPoint;
        initialDurability = pointDurability;
        bodyLength = iBodyLength;
        eraserDurability = iEraser;
    }

    public static void main(String[] args) throws Exception {
        Pencil temp = new Pencil();

        if(args[0].equals("Test")){
            Result result = JUnitCore.runClasses(PencilTest.class);

            int numTests = result.getRunCount();
            int numFailed = result.getFailureCount();
            int numIgnored = result.getIgnoreCount();

            System.out.println((numTests - numFailed) + " Passed Successfully\n");

            if(!result.wasSuccessful()){
                System.out.println(numFailed + " Failed:");
                for(Failure failure : result.getFailures()){
                    System.out.print("\t" + failure.getDescription().getMethodName());
                    System.out.print(" - ");
                    System.out.println(failure.getMessage());
                }

                if(numIgnored > 0){
                    System.out.print("\n" + numIgnored + " Ignored");
                }
            }
            System.out.println("\nTest complete");
        }
        if(args[0].equals("Write")){
            if(args[1]!=null && !args[1].isEmpty()){
                temp.Write(args[1]);
            }
            System.out.println("\nWrite complete");
        }
        if(args[0].equals("Sharpen")){
            temp.Sharpen();
            System.out.println("\nSharpen complete");
        }
        if(args[0].equals("Erase")){
            if(args[1]!=null && !args[1].isEmpty()){
                temp.Erase(args[1]);
            }
            System.out.println("\nErase complete");
        }
        if(args[0].equals("Edit")){
            if(args[1]!=null && !args[1].isEmpty()){
                temp.Edit(args[1]);
            }
            System.out.println("\nEdit complete");
        }
        if(args[0].equals("Print")){
            temp.Print();
            System.out.println("\nPrint complete");
        }
        if(args[0].equals("ClearPage")){
            temp.ClearPage();
            System.out.println("\nClearPage complete");
        }
    }

    //[Command - Write]
    //Story: As a writer
    //      I want to be able use a pencil to write text on a sheet of paper
    //      so that I can better remember my thoughts
    public void Write(String newText) {
        Text+=newText;
        degradePoint(countDurabilityUse(newText));
    }

    //[Command - Sharpen]
    //Story: As a writer
    //      I want to be able to sharpen my pencil
    //      so that I can continue to write with it after it goes dull
    public void Sharpen() {
        sharpenPoint(sharpeningRate);
    }

    //[Command - Erase]
    //Story: As a writer
    //      I want to be able to erase previously written text
    //      so that I can remove my mistakes
    public void Erase(String eraseText) {
        if(eraserDurability<1) return;
        if(!Text.contains(eraseText)) return;

        int start = Text.lastIndexOf(eraseText);
        eData = new EraseData(eraseText,start);
        
        char[] characters = Text.toCharArray();
        char[] sequence = eraseText.toCharArray();
        for(int i=sequence.length; i > 0; i--){
            if(eraserDurability==0) break;// prevent erasing once durability reaches 0

            characters[i+start-1]=' ';
            degradeEraser();
        }
        Text = new String(characters);
    }

    //[Command - Edit]
    //Story: As a writer
    //      I want to be able to edit previously written text
    //      so that I can change my writing without starting over
    public void Edit(String newText) {
        if(eData==null) return;

        int start = eData.index;

        char[] characters = Text.toCharArray();
        char[] newChars = newText.toCharArray();
        for(int i=0; i < newChars.length; i++){

            if(i+start > characters.length-1) break;// prevent index out of bounds

            if(Character.isSpaceChar(characters[i+start])){
                characters[i+start]=newChars[i];
            }else{
                characters[i+start]='@';
            }

            if(!Character.isSpaceChar(newChars[i])){
                if(Character.isUpperCase(newChars[i])){
                    degradePoint(2);
                }else{
                    degradePoint(1);
                }
            }
        }

        Text = new String(characters);
        eData=null;
    }

    //[Extra Command - Print]
    //Story: As a writer
    //      I want to be able to see what is written on the my paper
    //      so that I can better review my thoughts
    public void Print() {
        System.out.println(Text);
    }

    //[Extra Command - ClearPage]
    //Story: As a writer
    //      I want to be able to write on a new piece of paper 
    //      so that I can better organize my thoughts
    //
    //  Use to clear text and remove erase data at the same time
    public void ClearPage() {
        Text="";
        eData=null;
    }

    //[Required - Eraser Degradation]
    //Story: As a pencil manufacturer
    //      I want a pencil eraser to eventually wear out
    //      so that I can sell more pencils
    private void degradeEraser() {
        eraserDurability--;
    }

    //[Required - Point Degradation]
    //Story: As a pencil manufacturer
    //      I want writing to cause a pencil point to go dull
    //      so that I can sell more pencils
    private void degradePoint(int lossDur) {
        pointDurability = Math.max(0,pointDurability-lossDur);
    }

    private void sharpenPoint(int gainDur) {
        if(bodyLength < 1) return;

        pointDurability = Math.min(initialDurability,pointDurability+gainDur);
        bodyLength--;
    }

    //Use to count the expected point durability use for a given input string
    //Rules: Uppercase characters = 2 durability, All other chars = 1 durability
    public int countDurabilityUse(String input) {
        int result = 0;
        char[] characters = input.replaceAll("\\s+","").toCharArray();

        for(int i=0; i<characters.length; i++){
            if(Character.isUpperCase(characters[i])){
                result++;
            }
            result++;
        }

        return result;
    }
}