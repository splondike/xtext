/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.serializer.acceptor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;

/**
 * @author Moritz Eysholdt - Initial contribution and API
 */
public class StringBufferSequenceAcceptor implements ISequenceAcceptor {

	protected StringBuilder buf = new StringBuilder();

	public void acceptAssignedCrossRefDatatype(RuleCall datatypeRC, String token, EObject value, int index,
			ICompositeNode node) {
		buf.append(token);
	}

	public void acceptAssignedCrossRefEnum(RuleCall enumRC, String token, EObject value, int index, ICompositeNode node) {
		buf.append(token);
	}

	public void acceptAssignedCrossRefKeyword(Keyword kw, String token, EObject value, int index, ILeafNode node) {
		buf.append(token);
	}

	public void acceptAssignedCrossRefTerminal(RuleCall terminalRC, String token, EObject value, int index,
			ILeafNode node) {
		buf.append(token);
	}

	public void acceptAssignedDatatype(RuleCall datatypeRC, String token, Object value, int index, ICompositeNode node) {
		buf.append(token);
	}

	public void acceptAssignedEnum(RuleCall enumRC, String token, Object value, int index, ICompositeNode node) {
		buf.append(token);
	}

	public void acceptAssignedKeyword(Keyword keyword, String token, Object value, int index, ILeafNode node) {
		buf.append(token);
	}

	public void acceptAssignedTerminal(RuleCall terminalRC, String token, Object value, int index, ILeafNode node) {
		buf.append(token);
	}

	public void acceptComment(AbstractRule rule, String token, ILeafNode node) {
		buf.append(token);
	}

	public void acceptUnassignedAction(Action action) {
	}

	public void acceptUnassignedDatatype(RuleCall datatypeRC, String token, ICompositeNode node) {
		buf.append(token);
	}

	public void acceptUnassignedEnum(RuleCall enumRC, String token, ICompositeNode node) {
		buf.append(token);
	}

	public void acceptUnassignedKeyword(Keyword keyword, String token, ILeafNode node) {
		buf.append(token);
	}

	public void acceptUnassignedTerminal(RuleCall terminalRC, String token, ILeafNode node) {
		buf.append(token);
	}

	public void acceptWhitespace(AbstractRule rule, String token, ILeafNode node) {
		buf.append(token);
	}

	public boolean enterAssignedAction(Action action, EObject semanticChild, ICompositeNode node) {
		return true;
	}

	public boolean enterAssignedParserRuleCall(RuleCall rc, EObject semanticChild, ICompositeNode node) {
		return true;
	}

	public void enterUnassignedParserRuleCall(RuleCall rc) {
	}

	public void finish() {
	}

	public void leaveAssignedAction(Action action, EObject semanticChild) {
	}

	public void leaveAssignedParserRuleCall(RuleCall rc, EObject semanticChild) {
	}

	public void leaveUnssignedParserRuleCall(RuleCall rc) {
	}

	@Override
	public String toString() {
		return buf.toString();
	}

}
