import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CppWriter{

    private String outputFile;
    private String outputContents;

    public CppWriter(String outputFile, String outputContents){
	this.outputFile = outputFile;
	this.outputContents = outputContents;
    }

    public CppWriter(CppOutput o){
	this.outputFile = o.getFilename();
	this.outputContents = o.getOutputContents();
    }

    public void write(){
	try{
	    String filename = outputFile + ".cpp";

	    BufferedWriter out = new BufferedWriter(new FileWriter(filename));

	    // Now write the actual output contents
	    out.write(outputContents);
	    
	    out.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }



    public static void main(String[] args){
	CppWriter w = new CppWriter("Output", "This is testoutput");
	w.write();

    }

}