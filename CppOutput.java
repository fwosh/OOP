public class CppOutput{

    //TODO: move this to constants class or something
    private final static String
	NEWLINE = "\n";

    private String outputContents;
    private String filename;

    public CppOutput(){
	this.filename = null;
	this.outputContents = "";
    }

    public CppOutput(String filename){
	this.filename = filename;
	this.outputContents = ""; // Empty string
    }

    public String getOutputContents() {
	return outputContents;
    }

    public String getFilename() {
	return filename;
    }

    public void setFilename(String filename){
	this.filename = filename;
    }

    public void attachInclude(){
	String include = "#include 'java_lang.h'" + NEWLINE;
	this.outputContents += include;
    }

    public void addLn(String newAddition){
	add(newAddition);
	this.outputContents += NEWLINE;
    }

    public void add(String newAddition){
	this.outputContents += newAddition;
    }




}