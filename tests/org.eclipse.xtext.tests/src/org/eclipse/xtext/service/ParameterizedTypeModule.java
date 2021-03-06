/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.service;

import java.util.Iterator;

import com.google.inject.Provider;

/**
 * @author Sven Efftinge - Initial contribution and API
 */
public class ParameterizedTypeModule extends AbstractGenericModule {
	public final X BIND_X = new X();
	public final X PROVIDE_X = new X();

	static class X implements Comparable<X>, Iterator<X>, Iterable<X> {
		public int compareTo(X o) {
			return 0;
		}

		public boolean hasNext() {
			return false;
		}

		public X next() {
			return null;
		}

		public void remove() {
		}

		public Iterator<X> iterator() {
			return null;
		}
	}
	
	public Class<? extends Comparable<X>> bindParameterizedType() {
		return X.class;
	}
	
	public Iterator<X> bindParameterizedType2() {
		return BIND_X;
	}
	
	public Provider<Iterable<X>> provideParameterizedType2() {
		return new Provider<Iterable<X>>(){
			public Iterable<X> get() {
				return PROVIDE_X;
			}
		};
	}
}
