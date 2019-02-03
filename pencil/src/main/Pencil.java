package main;

import test.PencilTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Pencil {

    public String Text;
    public int pointDurability;
    public int initialDurability;

    public Pencil(){
        Text = "";
        initialDurability = 40000;
        pointDurability = initialDurability;
    }

    public Pencil(String iText, int iDurability){
        Text = iText;
        initialDurability = iDurability;
        pointDurability = initialDurability;
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
        int numCharacters = newText.replaceAll("\\s+","").length();
        degradePoint(numCharacters);
    }

    public void Sharpen(){
        
    }

    private void degradePoint(int lossDur){
        pointDurability = Math.max(0,pointDurability-lossDur);
    }
}