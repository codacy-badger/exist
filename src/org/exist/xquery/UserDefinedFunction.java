/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2001-03 Wolfgang M. Meier
 *  wolfgang@exist-db.org
 *  http://exist.sourceforge.net
 *  
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 *  $Id$
 */
package org.exist.xquery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.exist.dom.QName;
import org.exist.xquery.util.ExpressionDumper;
import org.exist.xquery.value.Item;
import org.exist.xquery.value.Sequence;

/**
 * @author wolf
 */
public class UserDefinedFunction extends Function {

	private Expression body;
	
	private List parameters = new ArrayList(5);
	
	private Sequence[] currentArguments = null;
	
	private boolean inRecursion = false;
	
	public UserDefinedFunction(XQueryContext context, FunctionSignature signature) {
		super(context, signature);
	}
	
	public void setFunctionBody(Expression body) {
		this.body = body;
	}
	
	public void addVariable(String varName) throws XPathException {
		QName qname = QName.parse(context, varName, null);
		parameters.add(qname);
	}
	
	/* (non-Javadoc)
	 * @see org.exist.xquery.Function#setArguments(java.util.List)
	 */
	public void setArguments(Sequence[] args) throws XPathException {
		this.currentArguments = args;
	}
	
	/* (non-Javadoc)
	 * @see org.exist.xquery.Function#analyze(org.exist.xquery.Expression, int)
	 */
	public void analyze(Expression parent, int flags) throws XPathException {
		if(!inRecursion) {
			inRecursion = true;
			// Save the local variable stack
			LocalVariable mark = context.markLocalVariables(true);
			QName varName;
			LocalVariable var;
			for(Iterator i = parameters.iterator(); i.hasNext(); ) {
				varName = (QName)i.next();
				var = new LocalVariable(varName);
				context.declareVariableBinding(var);
			}
			
			body.analyze(this, flags);
			
			// restore the local variable stack
			context.popLocalVariables(mark);
			inRecursion = false;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.exist.xquery.Expression#eval(org.exist.dom.DocumentSet, org.exist.xquery.value.Sequence, org.exist.xquery.value.Item)
	 */
	public Sequence eval(
		Sequence contextSequence,
		Item contextItem)
		throws XPathException {
		// Save the local variable stack
		LocalVariable mark = context.markLocalVariables(true);
		
		QName varName;
		LocalVariable var;
		Sequence argSeq;
		int j = 0;
		for(Iterator i = parameters.iterator(); i.hasNext(); j++) {
			varName = (QName)i.next();
			var = new LocalVariable(varName);
			var.setValue(currentArguments[j]);
			context.declareVariableBinding(var);
		}
		Sequence result = body.eval(contextSequence, contextItem);
		
		// restore the local variable stack
		context.popLocalVariables(mark);
		return result;
	}
	
	/* (non-Javadoc)
     * @see org.exist.xquery.Function#dump(org.exist.xquery.util.ExpressionDumper)
     */
    public void dump(ExpressionDumper dumper) {
        FunctionSignature signature = getSignature();
        dumper.display(signature.getName());
        dumper.display('(');
        for(int i = 0; i < signature.getArgumentTypes().length; i++) {
			if(i > 0)
				dumper.display(", ");
			dumper.display(signature.getArgumentTypes()[i]);
		}
		dumper.display(") ");
        dumper.display(signature.getReturnType().toString());
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        FunctionSignature signature = getSignature();
        StringBuffer buf = new StringBuffer();
        buf.append(signature.getName());
        buf.append('(');
        for(int i = 0; i < signature.getArgumentTypes().length; i++) {
			if(i > 0)
				buf.append(", ");
			buf.append(signature.getArgumentTypes()[i]);
		}
        buf.append(')');
        return buf.toString();
    }
    
	/* (non-Javadoc)
	 * @see org.exist.xquery.functions.Function#getDependencies()
	 */
	public int getDependencies() {
		return Dependency.CONTEXT_SET + Dependency.CONTEXT_ITEM
			+ Dependency.CONTEXT_POSITION;
	}
	
	/* (non-Javadoc)
	 * @see org.exist.xquery.PathExpr#resetState()
	 */
	public void resetState() {
        //TODO ; understand this test. Why not reset even is not in recursion ?
		// Answer: would lead to an infinite loop if the function is recursive.
		if(!inRecursion) {
			inRecursion = true;
			body.resetState();
			inRecursion = false;
		}
	}
}
