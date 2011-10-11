import java.util.List;
import java.util.ArrayList;

/**
* Represents one class unit. Stores relavent class information such as name identifier and 
* methods.
*/
public class ClassUnit{

    public List<MethodVisitor> methods;
    public String name;

    public ClassUnit(){
	methods = new ArrayList<MethodVisitor>();
    }

    

}