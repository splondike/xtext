/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xbase.jvmmodel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.xtext.resource.ILocationInFileProviderExtension;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.xbase.resource.XbaseLocationInFileProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Jan Koehnlein - Initial contribution and API
 */
@Singleton
public class JvmLocationInFileProvider extends XbaseLocationInFileProvider {

	@Inject
	private IJvmModelAssociations jvmAssociations;
	
	@Override
	public ITextRegion getFullTextRegion(EObject element) {
		return super.getFullTextRegion(convertToSource(element));
	}

	@Override
	public ITextRegion getFullTextRegion(EObject owner, EStructuralFeature feature, int indexInList) {
		return super.getFullTextRegion(convertToSource(owner), feature, indexInList);
	}
	
	@Override
	public ITextRegion getSignificantTextRegion(EObject element) {
		return super.getSignificantTextRegion(convertToSource(element));
	}

	@Override
	public ITextRegion getSignificantTextRegion(EObject owner, EStructuralFeature feature, int indexInList) {
		return super.getSignificantTextRegion(convertToSource(owner), feature, indexInList);
	}
	
	@NonNullByDefault
	@Nullable
	@Override
	public ITextRegion getTextRegion(EObject object, EStructuralFeature feature, int indexInList,
			ILocationInFileProviderExtension.RegionDescription query) {
		return super.getTextRegion(convertToSource(object), feature, indexInList, query);
	}
	
	@NonNullByDefault
	@Nullable
	@Override
	public ITextRegion getTextRegion(EObject object, RegionDescription query) {
		return super.getTextRegion(convertToSource(object), query);
	}
	
	protected EObject convertToSource(EObject element) {
		if(element == null)
			return null;
		EObject sourceElements = jvmAssociations.getPrimarySourceElement(element);
		return sourceElements== null ? element : sourceElements;
	}
}
