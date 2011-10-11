import xtc.tree.Visitor;
import xtc.tree.Node;
import xtc.tree.GNode;

import java.util.*;

/**
* A visitor that stores relavent information for a particular method
* which will later be used to generate C++ vtable information
*/
public class MethodVisitor extends Visitor{

    public List<Map> parameters;
    public List<String> modifiers;
    public String identifier;
    public String implementation;

    public MethodVisitor(){
	super();

	modifiers = new ArrayList<String>();
	parameters = new ArrayList<Map>();
    }

    public void visit(Node n){
	for (Object o : n){
	    if (o instanceof Node){
		dispatch((Node)o);
	    }
	}
    }

    /**
     * Pull the method declaration apart, and get all the pieces for translation
     *
     **/
    public void visitMethodDeclaration(GNode n){

	for(Object o : n){
	    // If String, must be the method identifier
	    if (o instanceof String) {
		this.identifier = (String) o;
	    }

	    if (o instanceof Node) {		

		// If Block, its the method implementation
		if (((Node)o).getName() == "Block"){
		    ImplementationVisitor iv = new ImplementationVisitor();
		    iv.dispatch((Node)o);
		    this.implementation = iv.implementation;
		}

		// If modifiers... collect modifiers...
		if (((Node)o).getName() == "Modifiers"){
		    dispatch((Node)o);
		}
		
		dispatch((Node)o);
	    }
	}
    }

    public void visitFormalParameter(GNode n){
	HashMap param = new HashMap();

	for(Object o : n){
	    
	    // String indicates variable name
	    if (o instanceof String) {
		param.put("name", (String)o);
	    }

	    // FIXME: This needs to be able to handle qualified/primitive type
	    // plus Dimension if it is an array
	    if (o instanceof Node) {
		if (((Node)o).getName() == "Type"){
		    Node innerType = (Node)((Node)o).get(0);
		    String type = (String)innerType.get(0);
		    param.put("type", type);
		}

	    }
	}

	parameters.add(param);
    }

    public void visitType(GNode n){

    }

    public void visitQualifiedIdentifier(GNode n){

    }

    public void visitPrimitiveType(GNode n){

    }

    public void visitModifier(GNode n){
	for(Object o : n){
	    // If String, add to modifier list
	    if (o instanceof String) {
		modifiers.add((String)o);
	    }
	}
    }


}