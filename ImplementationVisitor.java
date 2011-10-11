import xtc.tree.Visitor;
import xtc.tree.Node;
import xtc.tree.GNode;

/**
* A Visitor that visits and stores the implementation details
* of a method.
*/
public class ImplementationVisitor extends Visitor{

    public int indent;
    public String implementation;

    public ImplementationVisitor(){
	super();

	// Set indent at nothing initially
	indent = 0;

	// Implementation is empty initally
	implementation = "";
    }

    public void visit(Node n){
	for (Object o : n){
	    if (o instanceof Node){
		dispatch((Node)o);
	    }
	}
    }

    public void visitBlock(GNode n){
	addLn("{");
	indent++;
	visit(n);
	indent--;
	add(getIndent());
	addLn("}");
    }

    public void visitLogicalAndExpression(GNode n){
	dispatch((Node) n.get(0));
	add(" && ");
	dispatch((Node) n.get(1));
    }

    public void visitLogicalOrExpression(GNode n){
	dispatch((Node) n.get(0));
	add(" || ");
	dispatch((Node) n.get(1));
    }

    public void visitWhileStatement(GNode n){
	add(getIndent());
	add("while (");
	dispatch((Node)n.get(0)); // while condition
	add(")");
	dispatch((Node)n.get(1)); // attached block
    }

    public void visitForStatement(GNode n){
	add(getIndent());
	add("for");
	visit(n);
    }

    public void visitBasicForControl(GNode n){
	add("(");
	for(Object o: n){
	    if(o instanceof Node){
		dispatch((Node)o);
	    }
	}
	add(")");
    }
	  
    public void visitDeclarators(GNode n){
	visit(n);
    }


    public void visitRelationalExpression(GNode n){
	for(Object o : n){
	    if(o instanceof Node){
		dispatch((Node)o);
	    }else if(o instanceof String){
		add((String)o); // print operator
	    }
	}
    }

    public void visitExpressionList(GNode n){
	for(Object o : n){
	    if(o instanceof Node){
		dispatch((Node)o);
	    }else if(o instanceof String){
		add((String)o); // Print operator
	    }
	}
    }

    public void visitPostfixExpression(GNode n){
	for(Object o : n){
	    if(o instanceof Node){
		dispatch((Node)o);
	    }else if(o instanceof String){
		add((String)o); // Print operator
	    }
	}
    }

    public void visitExpressionStatement(GNode n){
	add(getIndent());
	visit(n);
	addLn(";");

    }

    public void visitExpression(GNode n){
	for(Object o : n){
	    if(o instanceof Node){
		dispatch((Node)o);
	    }else if(o instanceof String){
		add((String)o); // Print operator
	    }
	}
    }

    public void visitConditionalStatement(GNode n){
	add(getIndent());
	add("if (");
	add(" )");
	visit(n);
    }

    public void visitCallExpression(GNode n){
	add("SYSTEM.OUT.PRINT(SOMETHING)");
    }

    public void visitSelectionExpression(GNode n){
	// Visits the first part, prints it, puts the dot, then prints the rest
	visit(n);
	add(".");
	add((String)n.get(1));
    }

    public void visitThisExpression(GNode n){
	add("this");
	visit(n);
    }

    public void visitPrimaryIdentifier(GNode n){
	String word = (String)n.get(0);
	add(word);
	visit(n);
    }

    public void visitModifiers(GNode n){
	visit(n);
    }

    public void visitModifier(GNode n){
	for(Object o : n){
	    if(o instanceof String){
		add("[" + (String)o + "]");
	    }else if(o instanceof Node){
		dispatch((Node)o);
	    }
	}
    }

    public void visitVoidType(GNode n){
	add("void");
    }

    public void visitFieldDeclaration(GNode n){
	add(getIndent());
	visit(n);
	addLn(";");
    }

    public void visitType(GNode n){
	visit(n);
    }

    public void visitPrimitiveType(GNode n){
	add((String)n.get(0) + " ");
    }

    public void visitDeclarator(GNode n){
	String declaratorName = n.getString(0);
	add(declaratorName);
	add(" = ");
	visit(n);
    }

    public void visitIntegerLiteral(GNode n){
	add((String)n.get(0));
    }

    public String getIndent(){
	String indentOut = "";

	for(int i = 0; i < indent; i++){
	    indentOut += "   ";
	}
	return indentOut;
    }

    /**
     * Private method to add new part to implementation representation
     *
     **/
    private void add(String addition){
	implementation += addition;
    }

    private void addLn(String addition){
	add(addition + "\n");
    }

}