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
    
    private int sharpeningRate;

    public Pencil(){
        Text = "";
        initialDurability = 40000;
        pointDurability = initialDurability;
        bodyLength = 10;

        sharpeningRate = 40000;
    }

    public Pencil(String iText, int iDurability, int iBodyLength){
        Text = iText;
        initialDurability = iDurability;
        pointDurability = initialDurability;
        bodyLength = iBodyLength;

        sharpeningRate = 40000;
    }

    public static void main(String[] args) throws Exception {
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
        }
    }

    public void Write(String newText){
        Text+=newText;
        degradePoint(countDurabilityUse(newText));
    }

    public void Sharpen(){
        sharpenPoint(sharpeningRate);
    }

    public void Erase(String eraseText){
        
    }

    private void degradePoint(int lossDur){
        pointDurability = Math.max(0,pointDurability-lossDur);
    }

    private void sharpenPoint(int gainDur){
        if(bodyLength < 1) return;

        pointDurability = Math.min(initialDurability,pointDurability+gainDur);
        bodyLength--;
    }

    public int countDurabilityUse(String input){
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