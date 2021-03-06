/*
* generated by Xtext
*/
package org.eclipse.xtext.ui.tests.editor.contentassist.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import org.eclipse.xtext.ui.tests.editor.contentassist.services.DatatypeRuleTestLanguageGrammarAccess;

public class DatatypeRuleTestLanguageParser extends AbstractContentAssistParser {
	
	@Inject
	private DatatypeRuleTestLanguageGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected org.eclipse.xtext.ui.tests.editor.contentassist.ui.contentassist.antlr.internal.InternalDatatypeRuleTestLanguageParser createParser() {
		org.eclipse.xtext.ui.tests.editor.contentassist.ui.contentassist.antlr.internal.InternalDatatypeRuleTestLanguageParser result = new org.eclipse.xtext.ui.tests.editor.contentassist.ui.contentassist.antlr.internal.InternalDatatypeRuleTestLanguageParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getTypeAccess().getAlternatives(), "rule__Type__Alternatives");
					put(grammarAccess.getTypesAccess().getGroup(), "rule__Types__Group__0");
					put(grammarAccess.getSimpleTypeAccess().getGroup(), "rule__SimpleType__Group__0");
					put(grammarAccess.getCompositeTypeAccess().getGroup(), "rule__CompositeType__Group__0");
					put(grammarAccess.getTypeIdAccess().getGroup(), "rule__TypeId__Group__0");
					put(grammarAccess.getTypeIdAccess().getGroup_1(), "rule__TypeId__Group_1__0");
					put(grammarAccess.getTypeIdAccess().getGroup_1_2(), "rule__TypeId__Group_1_2__0");
					put(grammarAccess.getTypesAccess().getTypesAssignment_2(), "rule__Types__TypesAssignment_2");
					put(grammarAccess.getSimpleTypeAccess().getNameAssignment_1(), "rule__SimpleType__NameAssignment_1");
					put(grammarAccess.getCompositeTypeAccess().getNameAssignment_1(), "rule__CompositeType__NameAssignment_1");
					put(grammarAccess.getCompositeTypeAccess().getBaseTypeAssignment_3(), "rule__CompositeType__BaseTypeAssignment_3");
					put(grammarAccess.getCompositeTypeEntryAccess().getDataTypeAssignment(), "rule__CompositeTypeEntry__DataTypeAssignment");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			org.eclipse.xtext.ui.tests.editor.contentassist.ui.contentassist.antlr.internal.InternalDatatypeRuleTestLanguageParser typedParser = (org.eclipse.xtext.ui.tests.editor.contentassist.ui.contentassist.antlr.internal.InternalDatatypeRuleTestLanguageParser) parser;
			typedParser.entryRuleModel();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public DatatypeRuleTestLanguageGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(DatatypeRuleTestLanguageGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
