import java.io.File;
import java.io.IOException;
import java.io.Reader;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import xtc.type.*;
import xtc.lang.JavaFiveParser;

/**
 * A translator from (a subset of) Java to (a subset of) C++.
 *
 * @author Robert Grimm
 * @version $Revision$
 */
public class Translator extends xtc.util.Tool {
    
    public static final int
	CLASS_NAME = 1,
	METHOD_NAME = 3;
    
    /** Create a new translator. */
    public Translator() {
	// Nothing to do.
    }
    
    public String getName() {
	return "Java to C++ Translator";
    }
    
    public String getCopy() {
	return "My Group";
    }
    
    public void init() {
	super.init();
	
	runtime.
	    bool("printJavaAST", "printJavaAST", false, "Print Java AST.").
	    bool("outputAll", "outputAll", false, "Output everything");
    }
    
    public Node parse(Reader in, File file) throws IOException, ParseException {
      JavaFiveParser parser =
	  new JavaFiveParser(in, file.toString(), (int)file.length());
      Result result = parser.pCompilationUnit(0);
      
      return (Node)parser.value(result);
    }
    
    public void process(Node node) {
	if (runtime.test("printJavaAST")) {
	    runtime.console().format(node).pln().flush();
	}

	if (runtime.test("outputAll")) {

	    /**
	     * Okay... I think what we want to do here is create a class
	     * that harvests the necessary data for each compilation unit,
	     * and saves all necessary information as fields, which
	     * can be accessed by a higher level object, which will take care of sorting all
	     * of it out.
	     **/
	    new Visitor() {

		Output output;
		MethodT currentMethod;
		ClassUnit currentClass;

		public void visitCompilationUnit(GNode n){
		    output = new Output();

		    currentClass = new ClassUnit();
	      
		    visit(n);

		    for( MethodVisitor m : currentClass.methods){
			System.out.println("METHOD NAME: " + m.identifier);
			System.out.println("MODIFIERS:   " + m.modifiers);
			System.out.println("PARAMS:      " + m.parameters);
			System.out.println("Implementation: \n" + m.implementation);
		    }
		}

		public void visitClassDeclaration(GNode n){

		    visit(n);
		}

		public void visitMethodDeclaration(GNode n){
		    MethodVisitor mv = new MethodVisitor();
		    mv.dispatch(n);
		    currentClass.methods.add(mv);

		    visit(n);
		}

		public void visit(Node n){
		    for (Object o : n){
			if (o instanceof Node){
			    dispatch((Node)o);
			}
		    }
		}
	    }.dispatch(node);
	}
    }


    /**
     * Run the translator with the specified command line arguments.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
	/*
	  Run to create AST
	 */
	new Translator().run(args);
    }
}
