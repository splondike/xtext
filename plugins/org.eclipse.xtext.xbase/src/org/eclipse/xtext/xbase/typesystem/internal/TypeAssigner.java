/*******************************************************************************
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xbase.typesystem.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.xtext.common.types.JvmIdentifiableElement;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeAssigner;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 * TODO JavaDoc, toString
 */
@NonNullByDefault
public class TypeAssigner implements ITypeAssigner {
	private final AbstractTypeComputationState state;

	protected TypeAssigner(AbstractTypeComputationState state) {
		this.state = state;
	}

	public AbstractTypeComputationState getForkedState() {
		return state;
	}

	public void assignType(JvmIdentifiableElement element, @Nullable JvmTypeReference declaredType, @Nullable JvmTypeReference expectedType) {
		// TODO validation messages
		if (declaredType != null)
			state.getResolvedTypes().setType(element, declaredType);
		else
			state.getResolvedTypes().setType(element, expectedType);
		state.addLocalToCurrentScope(element);
	}

	public void assignType(JvmIdentifiableElement element, @Nullable JvmTypeReference declaredType) {
		state.getResolvedTypes().setType(element, declaredType);
		state.addLocalToCurrentScope(element);
	}
}