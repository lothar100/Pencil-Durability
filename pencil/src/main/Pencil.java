package main;

import test.PencilTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Pencil {

    public String Text = "";

    public static void main(String[] args) throws Exception {
        
        if(args[0].equals("Test")){
            Result result = JUnitCore.runClasses(PencilTest.class);

            int numTests = result.getRunCount();
            int numFailed = result.getFailureCount();

            if(!result.wasSuccessful()){
                System.out.println(numFailed + " Failed:");
                for(Failure failure : result.getFailures()){
                    System.out.println("\t" + failure.getDescription().getMethodName());
                }
            }else{
                System.out.println(numTests + " Passed Successfully");
            }
        }
    }

    public void Write(String newText){
        Text+=newText;
    }
}