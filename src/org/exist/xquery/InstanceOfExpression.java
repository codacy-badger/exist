/*
 *  eXist Open Source Native XML Database
 *  Copyright (C) 2001-04 Wolfgang M. Meier
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

import org.exist.dom.DocumentSet;
import org.exist.xquery.util.ExpressionDumper;
import org.exist.xquery.value.BooleanValue;
import org.exist.xquery.value.Item;
import org.exist.xquery.value.Sequence;
import org.exist.xquery.value.SequenceIterator;
import org.exist.xquery.value.SequenceType;
import org.exist.xquery.value.Type;

/**
 * Implements the XQuery "instance of" operator.
 * 
 * @author wolf
 */
public class InstanceOfExpression extends AbstractExpression {

	private Expression expression;
	private SequenceType type;
	
	/**
	 * @param context
	 */
	public InstanceOfExpression(XQueryContext context, Expression expr, SequenceType type) {
		super(context);
		this.expression = expr;
		this.type = type;
	}

	/* (non-Javadoc)
     * @see org.exist.xquery.Expression#analyze(org.exist.xquery.Expression)
     */
    public void analyze(Expression parent, int flags) throws XPathException {
        expression.analyze(this, flags);
    }
    
	/* (non-Javadoc)
	 * @see org.exist.xquery.Expression#eval(org.exist.xquery.value.Sequence, org.exist.xquery.value.Item)
	 */
	public Sequence eval(Sequence contextSequence, Item contextItem) throws XPathException {
        if (context.getProfiler().isEnabled()) {
            context.getProfiler().start(this);       
            context.getProfiler().message(this, Profiler.DEPENDENCIES, "DEPENDENCIES", Dependency.getDependenciesName(this.getDependencies()));
            if (contextSequence != null)
                context.getProfiler().message(this, Profiler.START_SEQUENCES, "CONTEXT SEQUENCE", contextSequence);
            if (contextItem != null)
                context.getProfiler().message(this, Profiler.START_SEQUENCES, "CONTEXT ITEM", contextItem.toSequence());
        }
        
        Sequence result = BooleanValue.TRUE;
		Sequence seq = expression.eval(contextSequence, contextItem);
        
		int items = seq.getLength();
		int requiredCardinality = type.getCardinality();
		if (items > 0 && requiredCardinality == Cardinality.EMPTY)
            result = BooleanValue.FALSE;
        else if (items == 0 && (requiredCardinality & Cardinality.ZERO) == 0)
            result = BooleanValue.FALSE;
		else if (items > 1 && (requiredCardinality & Cardinality.MANY) == 0)
            result = BooleanValue.FALSE;
        else {
    		for(SequenceIterator i = seq.iterate(); i.hasNext(); ) {
    			Item next = i.nextItem();
    			if(!type.checkType(next)) {
                    result = BooleanValue.FALSE;   
                    break;
                }
    				
    		}
        }		
        
        if (context.getProfiler().isEnabled())           
            context.getProfiler().end(this, "", result);    
        
        return result;
	}

	/* (non-Javadoc)
     * @see org.exist.xquery.Expression#dump(org.exist.xquery.util.ExpressionDumper)
     */
    public void dump(ExpressionDumper dumper) {
        expression.dump(dumper);
        dumper.display(" instance of ");
        dumper.display(type.toString());
    }
    
    public String toString() {
    	StringBuffer result = new StringBuffer();
    	result.append(expression);
    	result.append(" instance of ");
    	result.append(type.toString());
    	return result.toString();
    }    
    
	/* (non-Javadoc)
	 * @see org.exist.xquery.Expression#returnsType()
	 */
	public int returnsType() {
		return Type.BOOLEAN;
	}

	/* (non-Javadoc)
	 * @see org.exist.xquery.AbstractExpression#getCardinality()
	 */
	public int getCardinality() {
		return Cardinality.EXACTLY_ONE;
	}
	
	/* (non-Javadoc)
	 * @see org.exist.xquery.AbstractExpression#getDependencies()
	 */
	public int getDependencies() {
		return Dependency.CONTEXT_SET + Dependency.CONTEXT_ITEM;
	}
	
	/* (non-Javadoc)
	 * @see org.exist.xquery.Expression#resetState()
	 */
	public void resetState() {
		super.resetState();
		expression.resetState();
	}

	public void setContextDocSet(DocumentSet contextSet) {
		super.setContextDocSet(contextSet);
		expression.setContextDocSet(contextSet);
	}
}
