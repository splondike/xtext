package org.eclipse.xtext.xbase.tests.typesystem;

import com.google.common.collect.Iterators;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.xbase.XAbstractFeatureCall;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.XbasePackage.Literals;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.tests.AbstractXbaseTestCase;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
@SuppressWarnings("all")
public abstract class AbstractFeatureCallTypeTest extends AbstractXbaseTestCase {
  public abstract void resolvesFeatureCallsTo(final String expression, final String... types);
  
  private static Set<String> seenExpressions;
  
  @BeforeClass
  public static void createSeenExpressionsSet() {
    HashSet<String> _newHashSet = CollectionLiterals.<String>newHashSet();
    AbstractFeatureCallTypeTest.seenExpressions = _newHashSet;
  }
  
  @AfterClass
  public static void discardSeenExpressions() {
    AbstractFeatureCallTypeTest.seenExpressions = null;
  }
  
  protected List<XAbstractFeatureCall> findFeatureCalls(final CharSequence expression) {
    try {
      final XExpression xExpression = this.expression(expression, false);
      TreeIterator<EObject> _eAll = EcoreUtil2.eAll(xExpression);
      Iterator<XAbstractFeatureCall> _filter = Iterators.<XAbstractFeatureCall>filter(_eAll, XAbstractFeatureCall.class);
      final List<XAbstractFeatureCall> featureCalls = IteratorExtensions.<XAbstractFeatureCall>toList(_filter);
      final Function1<XAbstractFeatureCall,Integer> _function = new Function1<XAbstractFeatureCall,Integer>() {
          public Integer apply(final XAbstractFeatureCall it) {
            List<INode> _findNodesForFeature = NodeModelUtils.findNodesForFeature(it, Literals.XABSTRACT_FEATURE_CALL__FEATURE);
            INode _head = IterableExtensions.<INode>head(_findNodesForFeature);
            int _offset = _head.getOffset();
            return Integer.valueOf(_offset);
          }
        };
      return IterableExtensions.<XAbstractFeatureCall, Integer>sortBy(featureCalls, _function);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected XExpression expression(final CharSequence expression, final boolean resolve) throws Exception {
    XExpression _xblockexpression = null;
    {
      final String string = expression.toString();
      boolean _add = AbstractFeatureCallTypeTest.seenExpressions.add(string);
      boolean _not = (!_add);
      if (_not) {
        String _plus = ("Duplicate expression under test: " + expression);
        Assert.fail(_plus);
      }
      XExpression _expression = super.expression(expression, resolve);
      _xblockexpression = (_expression);
    }
    return _xblockexpression;
  }
  
  @Test
  public void testNumberLiteralInClosure_01() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList().map[42]", "ArrayList<Object>", "List<Integer>");
  }
  
  @Test
  public void testOverloadedMethods_01() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<CharSequence> list = null\n\t\t\tvar Object o = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloaded(list, o)\n\t\t}", "int", "List<CharSequence>", "Object");
  }
  
  @Test
  public void testOverloadedMethods_02() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<CharSequence> list = null\n\t\t\tvar String s = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloaded(list, s)\n\t\t}", "long", "List<CharSequence>", "String");
  }
  
  @Test
  public void testOverloadedMethods_03() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<? extends CharSequence> list = null\n\t\t\tvar Object o = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloaded(list, o)\n\t\t}", "int", "List<? extends CharSequence>", "Object");
  }
  
  @Test
  public void testOverloadedMethods_04() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<? extends CharSequence> list = null\n\t\t\tvar String s = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloaded(list, s)\n\t\t}", "int", "List<? extends CharSequence>", "String");
  }
  
  @Test
  public void testOverloadedMethods_05() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<CharSequence> list = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloaded(list, null)\n\t\t}", "long", "List<CharSequence>");
  }
  
  @Test
  public void testOverloadedMethods_06() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<? extends CharSequence> list = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloaded(list, null)\n\t\t}", "int", "List<? extends CharSequence>");
  }
  
  @Test
  public void testOverloadedMethods_07() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<CharSequence> list = null\n\t\t\tvar Object o = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, o)\n\t\t}", "int", "List<CharSequence>", "Object");
  }
  
  @Test
  public void testOverloadedMethods_08() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<CharSequence> list = null\n\t\t\tvar String s = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, s)\n\t\t}", "long", "List<CharSequence>", "String");
  }
  
  @Test
  public void testOverloadedMethods_09() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<? extends CharSequence> list = null\n\t\t\tvar Object o = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, o)\n\t\t}", "int", "List<? extends CharSequence>", "Object");
  }
  
  @Test
  public void testOverloadedMethods_10() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<? extends CharSequence> list = null\n\t\t\tvar String s = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, s)\n\t\t}", "long", "List<? extends CharSequence>", "String");
  }
  
  @Test
  public void testOverloadedMethods_11() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<CharSequence> list = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, null)\n\t\t}", "long", "List<CharSequence>");
  }
  
  @Test
  public void testOverloadedMethods_12() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tvar java.util.List<? extends CharSequence> list = null\n\t\t\torg::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::overloadedWithTypeParam(list, null)\n\t\t}", "long", "List<? extends CharSequence>");
  }
  
  @Test
  public void testOverloadedOperators_12() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval i = 1bi\n\t\t\tval s = \'\'\n\t\t\ts + i\n\t\t}", "String", "String", "BigInteger");
  }
  
  @Test
  public void testOverloadedOperators_14() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval i = 1bi\n\t\t\tval s = \'\'\n\t\t\ti + s\n\t\t}", "BigInteger", "String", "String");
  }
  
  @Test
  public void testForExpression_01() throws Exception {
    this.resolvesFeatureCallsTo("for(String x : new java.util.ArrayList<String>()) x.length", "String", "int");
    this.resolvesFeatureCallsTo("for(String x : newArrayList(\'foo\')) x.length", "ArrayList<String>", "String", "int");
    this.resolvesFeatureCallsTo("for(String x : <String>newArrayList()) x.length", "ArrayList<String>", "String", "int");
  }
  
  @Test
  public void testForExpression_02() throws Exception {
    this.resolvesFeatureCallsTo("for(x : new java.util.ArrayList<String>()) x.length", "String", "int");
    this.resolvesFeatureCallsTo("for(x : <String>newArrayList()) x.length", "ArrayList<String>", "String", "int");
    this.resolvesFeatureCallsTo("for(x : newArrayList(\'foo\')) x.length", "ArrayList<String>", "String", "int");
  }
  
  @Test
  public void testForExpression_03() throws Exception {
    this.resolvesFeatureCallsTo("for(String x : null as String[]) x.length", "String", "int");
  }
  
  @Test
  public void testForExpression_04() throws Exception {
    this.resolvesFeatureCallsTo("for(x : null as String[]) x.length", "String", "int");
  }
  
  @Test
  public void testMethodTypeParamInference_01() throws Exception {
    this.resolvesFeatureCallsTo("new java.util.ArrayList<String>().findFirst(e|e == \'foo\')", "String", "String", "boolean");
  }
  
  @Test
  public void testMethodTypeParamInference_02() throws Exception {
    this.resolvesFeatureCallsTo("new java.util.ArrayList<String>().<String>findFirst(e|e == \'foo\')", "String", "String", "boolean");
  }
  
  @Test
  public void testMethodTypeParamInference_04() throws Exception {
    this.resolvesFeatureCallsTo("$$IterableExtensions::findFirst(new java.util.ArrayList<String>) [e|e == \'foo\']", "String", "String", "boolean");
  }
  
  @Test
  public void testMethodTypeParamInference_05() throws Exception {
    this.resolvesFeatureCallsTo("$$IterableExtensions::<String>findFirst(new java.util.ArrayList<String>) [e|e == \'foo\']", "String", "String", "boolean");
  }
  
  @Test
  public void testTypeForVoidClosure() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'foo\',\'bar\').forEach []", "ArrayList<String>", "void");
  }
  
  @Test
  public void testFeatureCallWithArrayToIterableConversion() throws Exception {
    this.resolvesFeatureCallsTo("\'foo\'.toCharArray.iterator", "char[]", "Iterator<Character>");
  }
  
  @Test
  public void testReturnType_02() throws Exception {
    this.resolvesFeatureCallsTo("return try { if (true) \'foo\' else \'bar\' } finally { String::valueOf(\'zonk\') }", "String");
  }
  
  @Test
  public void testClosure_01() throws Exception {
    this.resolvesFeatureCallsTo("{ var closure = [|\'literal\']\n\t\t  new testdata.ClosureClient().invoke0(closure)\t}", "String", "()=>String");
  }
  
  @Test
  public void testClosure_03() throws Exception {
    String _plus = ("{\n" + 
      "  var java.util.List<? super String> list = null;\n");
    String _plus_1 = (_plus + 
      "  list.map(e|e)\n");
    String _plus_2 = (_plus_1 + 
      "}");
    this.resolvesFeatureCallsTo(_plus_2, "List<? super String>", "List<Object>", "Object");
  }
  
  @Test
  public void testClosure_04() throws Exception {
    String _plus = ("{\n" + 
      "  var java.util.List<? super String> list = null;\n");
    String _plus_1 = (_plus + 
      "  list.map(e|false)\n");
    String _plus_2 = (_plus_1 + 
      "}");
    this.resolvesFeatureCallsTo(_plus_2, "List<? super String>", "List<Boolean>");
  }
  
  @Test
  public void testClosure_07() throws Exception {
    this.resolvesFeatureCallsTo("[String x, String y| x + y ]", "String", "String", "String");
  }
  
  @Test
  public void testClosure_08() throws Exception {
    this.resolvesFeatureCallsTo("[x| x]", "Object");
  }
  
  @Test
  public void testClosure_09() throws Exception {
    this.resolvesFeatureCallsTo("[String x, String y| x.substring(y.length)]", "String", "String", "String", "int");
  }
  
  @Test
  public void testClosure_10() throws Exception {
    this.resolvesFeatureCallsTo("[ x | x.toString x ]", "Object", "String", "Object");
  }
  
  @Test
  public void testClosure_11() throws Exception {
    this.resolvesFeatureCallsTo("[Object x| x]", "Object");
  }
  
  @Test
  public void testClosure_12() throws Exception {
    this.resolvesFeatureCallsTo("[Object x| x.toString x ]", "Object", "String", "Object");
  }
  
  @Test
  public void testClosure_13() throws Exception {
    this.resolvesFeatureCallsTo("{ \n\t\t\tval mapper = [ x | x ]\n\t\t\tnewArrayList(1).map(mapper)\n\t\t}", "Integer", "ArrayList<Integer>", "List<Integer>", "(Integer)=>Integer");
  }
  
  @Test
  public void testClosure_15() throws Exception {
    this.resolvesFeatureCallsTo("{ \n\t\t\tval fun = [ x | x ]\n\t\t\tval String s = fun.apply(null)\n\t\t\tfun\n\t\t}", "String", "(String)=>String", "String", "(String)=>String");
  }
  
  @Test
  public void testClosure_27() throws Exception {
    this.resolvesFeatureCallsTo("{ \n\t\t\tval mapper = [ x | x ]\n\t\t\t$$ListExtensions::map(newArrayList(1), mapper)\n\t\t}", "Integer", "List<Integer>", "ArrayList<Integer>", "(Integer)=>Integer");
  }
  
  @Test
  public void testClosure_30() throws Exception {
    this.resolvesFeatureCallsTo("$$ListExtensions::map(null as java.util.List<? super String>) [e|e]", "List<Object>", "Object");
  }
  
  @Test
  public void testClosure_31() throws Exception {
    String _plus = ("{\n" + 
      "  var java.util.List<? super String> list = null;\n");
    String _plus_1 = (_plus + 
      "  $$ListExtensions::map(list) [e|e]\n");
    String _plus_2 = (_plus_1 + 
      "}");
    this.resolvesFeatureCallsTo(_plus_2, "List<Object>", "List<? super String>", "Object");
  }
  
  @Test
  public void testSwitchExpression_3() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval Object c = null\n\t\t\tswitch c {\n\t            CharSequence: 1\n\t    \t}\n\t\t}", "Object");
  }
  
  @Test
  public void testSwitchExpression_4() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval Comparable<Object> c = null\n\t\t\tswitch c {\n\t            CharSequence: switch(c) {\n                    Appendable: {\n                        c.charAt(1)\n                    }\n                }\n        \t}\n\t\t}", "Comparable<Object>", "Comparable<Object> & CharSequence", "Comparable<Object> & CharSequence & Appendable", "char");
  }
  
  @Test
  public void testSwitchExpression_5() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval Comparable<Object> c = null\n\t\t\tswitch c {\n\t            CharSequence: switch(c) {\n                    Appendable: {\n                        c.append(null)\n                    }\n                }\n        \t}\n\t\t}", "Comparable<Object>", "Comparable<Object> & CharSequence", "Comparable<Object> & CharSequence & Appendable", "Appendable");
  }
  
  @Test
  public void testSwitchExpression_6() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval Comparable<Object> c = null\n\t\t\tswitch c {\n\t            CharSequence: switch(c) {\n                    Appendable: {\n                        c.compareTo(null)\n                    }\n                }\n        \t}\n\t\t}", "Comparable<Object>", "Comparable<Object> & CharSequence", "Comparable<Object> & CharSequence & Appendable", "int");
  }
  
  @Test
  public void testTypeGuardedCase_0() throws Exception {
    this.resolvesFeatureCallsTo("switch s: new Object() { String: s StringBuffer: s}", "String", "StringBuffer");
  }
  
  @Test
  public void testTypeGuardedCase_1() throws Exception {
    this.resolvesFeatureCallsTo("switch s: \'\' as CharSequence { Cloneable: s String: s }", "CharSequence & Cloneable", "String");
  }
  
  @Test
  public void testBlockExpression_02() throws Exception {
    this.resolvesFeatureCallsTo("{val s = \'\' s}", "String");
  }
  
  @Test
  public void testBlockExpression_04() throws Exception {
    this.resolvesFeatureCallsTo("{val Object s = \'\' s}", "Object");
  }
  
  @Test
  public void testEMap_01() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val eMap = new org.eclipse.emf.common.util.BasicEMap<Integer, String>()\n\t\t  eMap.map[ getKey ].head\n         }", "BasicEMap<Integer, String>", "List<Integer>", "Integer", "Integer");
  }
  
  @Test
  public void testEMap_02() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val eMap = new org.eclipse.emf.common.util.BasicEMap<Integer, String>()\n\t\t  eMap.map[ getValue ].head\n         }", "BasicEMap<Integer, String>", "List<String>", "String", "String");
  }
  
  @Test
  public void testEMap_03() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val eMap = new org.eclipse.emf.common.util.BasicEMap<Integer, String>()\n\t\t  eMap.map[ it ].head\n         }", "BasicEMap<Integer, String>", "List<Entry<Integer, String>>", "Entry<Integer, String>", "Entry<Integer, String>");
  }
  
  @Test
  public void testEMap_04() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val eMap = new org.eclipse.emf.common.util.BasicEMap<Integer, String>()\n\t\t  eMap.map\n         }", "BasicEMap<Integer, String>", "Map<Integer, String>");
  }
  
  @Test
  public void testEMap_05() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? extends Integer, String> eMap = null\n\t\t  eMap.map[ key ].head\n         }", "BasicEMap<? extends Integer, String>", "List<Integer>", "Integer", "Integer");
  }
  
  @Test
  public void testEMap_06() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? extends Integer, String> eMap = null\n\t\t  eMap.map[ value ].head\n         }", "BasicEMap<? extends Integer, String>", "List<String>", "String", "String");
  }
  
  @Test
  public void testEMap_07() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? extends Integer, String> eMap = null\n\t\t  eMap.map[ it ].head\n         }", "BasicEMap<? extends Integer, String>", "List<Entry<? extends Integer, String>>", "Entry<? extends Integer, String>", "Entry<? extends Integer, String>");
  }
  
  @Test
  public void testEMap_08() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? extends Integer, String> eMap = null\n\t\t  eMap.map\n         }", "BasicEMap<? extends Integer, String>", "Map<? extends Integer, String>");
  }
  
  @Test
  public void testEMap_09() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? super Integer, String> eMap = null\n\t\t  eMap.map[ key ].head\n         }", "BasicEMap<? super Integer, String>", "List<Object>", "Object", "Object");
  }
  
  @Test
  public void testEMap_10() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? super Integer, String> eMap = null\n\t\t  eMap.map[ value ].head\n         }", "BasicEMap<? super Integer, String>", "List<String>", "String", "String");
  }
  
  @Test
  public void testEMap_11() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? super Integer, String> eMap = null\n\t\t  eMap.map[ it ].head\n         }", "BasicEMap<? super Integer, String>", "List<Entry<? super Integer, String>>", "Entry<? super Integer, String>", "Entry<? super Integer, String>");
  }
  
  @Test
  public void testEMap_12() throws Exception {
    this.resolvesFeatureCallsTo("{ \n          val org.eclipse.emf.common.util.BasicEMap<? super Integer, String> eMap = null\n\t\t  eMap.map\n         }", "BasicEMap<? super Integer, String>", "Map<? super Integer, String>");
  }
  
  @Test
  public void testConstructorTypeInference_02() throws Exception {
    this.resolvesFeatureCallsTo("<java.util.List<String>>newArrayList().add(new java.util.ArrayList())", "ArrayList<List<String>>", "boolean");
  }
  
  @Test
  public void testConstructorTypeInference_03() throws Exception {
    this.resolvesFeatureCallsTo("<java.util.List<String>>newArrayList().add(0, new java.util.ArrayList())", "ArrayList<List<String>>", "void");
  }
  
  @Test
  public void testConstructorTypeInference_04() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList.add(new java.util.ArrayList())", "ArrayList<ArrayList<Object>>", "boolean");
  }
  
  @Test
  public void testConstructorTypeInference_08() throws Exception {
    this.resolvesFeatureCallsTo("new testdata.GenericType2(new Integer(0), new Integer(0).doubleValue)", "double");
  }
  
  @Test
  public void testConstructorTypeInference_09() throws Exception {
    this.resolvesFeatureCallsTo("<Iterable<String>>newArrayList().add(new java.util.LinkedList)", "ArrayList<Iterable<String>>", "boolean");
  }
  
  @Test
  public void testConstructorTypeInference_10() throws Exception {
    this.resolvesFeatureCallsTo("<Iterable<String>>newArrayList().add(null)", "ArrayList<Iterable<String>>", "boolean");
  }
  
  @Test
  public void testConstructorTypeInference_11() throws Exception {
    this.resolvesFeatureCallsTo("<java.util.Map<Iterable<String>, Iterable<Integer>>>newArrayList().head", 
      "ArrayList<Map<Iterable<String>, Iterable<Integer>>>", "Map<Iterable<String>, Iterable<Integer>>");
  }
  
  @Test
  public void testMemberFeatureCall_03() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').get(0)", "ArrayList<String>", "String");
    this.resolvesFeatureCallsTo("<String>newArrayList().get(0)", "ArrayList<String>", "String");
  }
  
  @Test
  public void testFeatureCall_06() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map(s|s)", "ArrayList<String>", "List<String>", "String");
  }
  
  @Test
  public void testFeatureCall_06a() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map [it|it]", "ArrayList<String>", "List<String>", "String");
  }
  
  @Test
  public void testFeatureCall_06b() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map [it]", "ArrayList<String>", "List<String>", "String");
  }
  
  @Test
  public void testFeatureCall_06c() throws Exception {
    this.resolvesFeatureCallsTo("(null as Iterable<String>).map(s|s)", "Iterable<String>", "String");
  }
  
  @Test
  public void testFeatureCall_06_00() throws Exception {
    this.resolvesFeatureCallsTo("$$ListExtensions::map(newArrayList(\'\')) [s|s]", "List<String>", "ArrayList<String>", "String");
  }
  
  @Test
  public void testFeatureCall_06_01() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map(s|s).head", "ArrayList<String>", "List<String>", "String", "String");
  }
  
  @Test
  public void testFeatureCall_06_03() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map(s|1)", "ArrayList<String>", "List<Integer>");
  }
  
  @Test
  public void testFeatureCall_06_04() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map(s|1).head", "ArrayList<String>", "List<Integer>", "Integer");
  }
  
  @Test
  public void testFeatureCall_07() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map(s|s.length)", "ArrayList<String>", "List<Integer>", "String", "int");
  }
  
  @Test
  public void testFeatureCall_07_01() throws Exception {
    this.resolvesFeatureCallsTo("<String>newArrayList.map(s|s.length)", "ArrayList<String>", "List<Integer>", "String", "int");
  }
  
  @Test
  public void testFeatureCall_08() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map(s|s != null)", "ArrayList<String>", "List<Boolean>", "String", "boolean");
  }
  
  @Test
  public void testFeatureCall_11() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map(s|1).toList()", "ArrayList<String>", "List<Integer>", "List<Integer>");
  }
  
  @Test
  public void testFeatureCall_13_4() throws Exception {
    this.resolvesFeatureCallsTo("{ var it = newArrayList(\'\').map(s|1).toList() it }", "ArrayList<String>", "List<Integer>", "List<Integer>", "List<Integer>");
  }
  
  @Test
  public void testFeatureCall_13_5() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? extends Integer> it = null map(i|i+1) }", "List<Integer>", "Integer", "int");
  }
  
  @Test
  public void testFeatureCall_13_6() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? extends Integer> it = null map(i|i) }", "List<Integer>", "Integer");
  }
  
  @Test
  public void testFeatureCall_14() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(newArrayList(\'\').map(s|1))", "ArrayList<List<Integer>>", "ArrayList<String>", "List<Integer>");
  }
  
  @Test
  public void testFeatureCall_16_a() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map(s|1).map(i|1)", "ArrayList<String>", "List<Integer>", "List<Integer>");
  }
  
  @Test
  public void testFeatureCall_16_b() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map(s|1).map(i|1).head", "ArrayList<String>", "List<Integer>", "List<Integer>", "Integer");
  }
  
  @Test
  public void testFeatureCall_33() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.findFirst[String s | true]\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "String", "ArrayList<String>");
  }
  
  @Test
  public void testFeatureCall_34() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList.map[String s | s.substring(1,1) ]", "ArrayList<String>", "List<String>", "String", "String");
  }
  
  @Test
  public void testFeatureCall_35() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList.map[ s | s.toString ]", "ArrayList<Object>", "List<String>", "Object", "String");
  }
  
  @Test
  public void testFeatureCall_36() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.forEach[ s | s.toString ]\n\t\t}", "ArrayList<Object>", "ArrayList<Object>", "void", "Object", "String");
  }
  
  @Test
  public void testToList_01() throws Exception {
    this.resolvesFeatureCallsTo("{ val Iterable<? extends String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::fixedToList(iter) }", "List<? extends String>", "Iterable<? extends String>");
  }
  
  @Test
  public void testToList_02() throws Exception {
    this.resolvesFeatureCallsTo("{ val Iterable<? super String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::fixedToList(iter) }", "List<? super String>", "Iterable<? super String>");
  }
  
  @Test
  public void testToList_03() throws Exception {
    this.resolvesFeatureCallsTo("{ val Iterable<String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::fixedToList(iter) }", "List<String>", "Iterable<String>");
  }
  
  @Test
  public void testToList_04() throws Exception {
    this.resolvesFeatureCallsTo("{ val Iterable<? extends String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList(iter) }", "List<String>", "Iterable<? extends String>");
  }
  
  @Test
  public void testToList_05() throws Exception {
    this.resolvesFeatureCallsTo("{ val Iterable<? super String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList(iter) }", "List<Object>", "Iterable<? super String>");
  }
  
  @Test
  public void testToList_06() throws Exception {
    this.resolvesFeatureCallsTo("{ val Iterable<String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList(iter) }", "List<String>", "Iterable<String>");
  }
  
  @Test
  public void testToList_07() throws Exception {
    this.resolvesFeatureCallsTo("{ val Iterable<? extends String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList2(iter) }", "List<String>", "Iterable<? extends String>");
  }
  
  @Test
  public void testToList_08() throws Exception {
    this.resolvesFeatureCallsTo("{ val Iterable<? super String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList2(iter) }", "List<String>", "Iterable<? super String>");
  }
  
  @Test
  public void testToList_09() throws Exception {
    this.resolvesFeatureCallsTo("{ val Iterable<String> iter = null org::eclipse::xtext::xbase::tests::typesystem::TypeResolutionTestData::brokenToList2(iter) }", "List<String>", "Iterable<String>");
  }
  
  @Test
  public void testFeatureCall_Bug342134_00() throws Exception {
    this.resolvesFeatureCallsTo("(null as java.util.List<String>).map(e|newArrayList(e)).flatten", "List<ArrayList<String>>", "ArrayList<String>", "String", "Iterable<String>");
  }
  
  @Test
  public void testFeatureCall_Bug342134_04() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(\'\').map(e|newArrayList(e))", "ArrayList<String>", "List<ArrayList<String>>", "ArrayList<String>", "String");
  }
  
  @Test
  public void testFeatureCall_Bug342134_09() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(newArrayList(\'\')).flatten", "ArrayList<ArrayList<String>>", "ArrayList<String>", "Iterable<String>");
  }
  
  @Test
  public void testFeatureCall_Bug342134_10() throws Exception {
    this.resolvesFeatureCallsTo("<java.util.List<String>>newArrayList().flatten", "ArrayList<List<String>>", "Iterable<String>");
  }
  
  @Test
  public void testBounds_01() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<Integer> list = null list.get(0) }", "List<Integer>", "Integer");
  }
  
  @Test
  public void testBounds_02() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? extends Integer> list = null list.get(0) }", "List<? extends Integer>", "Integer");
  }
  
  @Test
  public void testBounds_03() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? super Integer> list = null list.get(0) }", "List<? super Integer>", "Object");
  }
  
  @Test
  public void testBounds_04() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<Integer> list = null list.subList(0, 1) }", "List<Integer>", "List<Integer>");
  }
  
  @Test
  public void testBounds_05() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? extends Integer> list = null list.subList(0, 1) }", "List<? extends Integer>", "List<? extends Integer>");
  }
  
  @Test
  public void testBounds_06() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? super Integer> list = null list.subList(0, 1) }", "List<? super Integer>", "List<? super Integer>");
  }
  
  @Test
  public void testBounds_07() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<Integer> list = null list.last }", "List<Integer>", "Integer");
  }
  
  @Test
  public void testBounds_08() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? extends Integer> list = null list.last }", "List<? extends Integer>", "Integer");
  }
  
  @Test
  public void testBounds_09() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? super Integer> list = null list.last }", "List<? super Integer>", "Object");
  }
  
  @Test
  public void testBounds_10() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<Iterable<Integer>> list = null list.last }", "List<Iterable<Integer>>", "Iterable<Integer>");
  }
  
  @Test
  public void testBounds_11() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<Iterable<Integer>> list = null list.last.last }", "List<Iterable<Integer>>", "Iterable<Integer>", "Integer");
  }
  
  @Test
  public void testBounds_12() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? extends Iterable<Integer>> list = null list.last }", "List<? extends Iterable<Integer>>", "Iterable<Integer>");
  }
  
  @Test
  public void testBounds_13() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? extends Iterable<Integer>> list = null list.last.last }", "List<? extends Iterable<Integer>>", "Iterable<Integer>", "Integer");
  }
  
  @Test
  public void testBounds_14() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<Iterable<? extends Integer>> list = null list.last }", "List<Iterable<? extends Integer>>", "Iterable<? extends Integer>");
  }
  
  @Test
  public void testBounds_15() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<Iterable<? extends Integer>> list = null list.last.last }", "List<Iterable<? extends Integer>>", "Iterable<? extends Integer>", "Integer");
  }
  
  @Test
  public void testBounds_16() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? extends Iterable<? extends Integer>> list = null list.last }", "List<? extends Iterable<? extends Integer>>", "Iterable<? extends Integer>");
  }
  
  @Test
  public void testBounds_17() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? extends Iterable<? extends Integer>> list = null list.last.last }", "List<? extends Iterable<? extends Integer>>", "Iterable<? extends Integer>", "Integer");
  }
  
  @Test
  public void testBounds_18() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<Iterable<? super Integer>> list = null list.last }", "List<Iterable<? super Integer>>", "Iterable<? super Integer>");
  }
  
  @Test
  public void testBounds_19() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<Iterable<? super Integer>> list = null list.last.last }", "List<Iterable<? super Integer>>", "Iterable<? super Integer>", "Object");
  }
  
  @Test
  public void testBounds_20() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? extends Iterable<? super Integer>> list = null list.last }", "List<? extends Iterable<? super Integer>>", "Iterable<? super Integer>");
  }
  
  @Test
  public void testBounds_21() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<? extends Iterable<? super Integer>> list = null list.last.last }", "List<? extends Iterable<? super Integer>>", "Iterable<? super Integer>", "Object");
  }
  
  @Test
  public void testPropertyAccess_01() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<Integer> it = null empty }", "boolean");
  }
  
  @Test
  public void testPropertyAccess_02() throws Exception {
    this.resolvesFeatureCallsTo("{ var java.util.List<Integer> list = null list.empty }", "List<Integer>", "boolean");
  }
  
  @Test
  public void testPropertyAccess_03() throws Exception {
    this.resolvesFeatureCallsTo("{ var Iterable<Integer> iterable = null iterable.empty }", "Iterable<Integer>", "boolean");
  }
  
  @Test
  public void testPropertyAccess_04() throws Exception {
    this.resolvesFeatureCallsTo("{ var Iterable<Integer> it = null empty }", "boolean");
  }
  
  @Test
  public void testPropertyAccess_05() throws Exception {
    this.resolvesFeatureCallsTo("{ var Iterable<Integer> iterable = null iterable.class }", "Iterable<Integer>", "Class<? extends Iterable>");
  }
  
  @Test
  public void testPropertyAccess_06() throws Exception {
    this.resolvesFeatureCallsTo("{ var Iterable<Integer> it = null class }", "Class<? extends Iterable>");
  }
  
  @Test
  public void testReceiverIsPartiallyResolved_01() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList.get(0)", "ArrayList<Object>", "Object");
  }
  
  @Test
  public void testReceiverIsPartiallyResolved_02() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList.toString", "ArrayList<Object>", "String");
  }
  
  @Test
  public void testTypeByTransitiveExpectation_02() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList.subList(1,1).subList(1,1).head", "ArrayList<Object>", "List<Object>", "List<Object>", "Object");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_002() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval String s = list.get(0)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "String", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_003() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval String s = list.head\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "String", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_004() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_005() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\t$$CollectionExtensions::addAll(list, null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "boolean", "ArrayList<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_006() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\t$$CollectionExtensions::addAll(list, \'\', \'\')\n\t\t\tlist\n\t\t}", "ArrayList<String>", "boolean", "ArrayList<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_007() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_010() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(null as java.util.Collection<String>)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_011() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(\'\', \'\', \'\')\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_014() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval Iterable<String> sublist = list.subList(1, 1)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "List<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_015() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tval java.util.Set<String> sublist = list.subList(1, 1)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "List<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_017() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tfor(String s: list.subList(1, 1)) {}\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "List<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_041() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(\'\')\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_047() throws Exception {
    this.resolvesFeatureCallsTo("println(newArrayList)", "ArrayList<Object>", "ArrayList<Object>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_068() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval String s = list.get(0)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "String", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_069() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval String s = list.head\n\t\t\tlist\n\t\t}", "ArrayList<String>", "String", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_070() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.add(\'\')\n\t\t\tlist\n\t\t}", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_071() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\t$$CollectionExtensions::addAll(list, null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "boolean", "ArrayList<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_072() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\t$$CollectionExtensions::addAll(list, \'\', \'\')\n\t\t\tlist\n\t\t}", "boolean", "ArrayList<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_073() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_074() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(new java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_075() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(newHashSet(\'\'))\n\t\t\tlist\n\t\t}", "ArrayList<String>", "boolean", "HashSet<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_076() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(null as java.util.Collection<String>)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_077() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(\'\', \'\', \'\')\n\t\t\tlist.head\n\t\t}", "ArrayList<String>", "boolean", "ArrayList<String>", "String");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_080() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval Iterable<String> sublist = list.subList(1, 1)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "List<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_081() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval java.util.Set<String> sublist = list.subList(1, 1)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "List<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_082() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval java.util.Iterator<String> sublist = list.subList(1, 1).iterator\n\t\t\tlist\n\t\t}", "ArrayList<String>", "List<String>", "Iterator<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_083() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tfor(String s: list.subList(1, 1)) {}\n\t\t\tlist\n\t\t}", "ArrayList<String>", "List<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_107() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(\'\')\n\t\t\tlist\n\t\t}", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_110() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(\'\', \'\', \'\')\n\t\t\tlist\n\t\t}", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_114() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tval String s = println(list.get(0))\n\t\t\tlist\n\t\t}", "String", "ArrayList<String>", "String", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_116() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tprintln(list).add(\'\')\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_117() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\t$$CollectionExtensions::addAll(println(list), null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "boolean", "ArrayList<String>", "ArrayList<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_119() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tprintln(list).addAll(null as java.util.ArrayList<String>)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_123() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tprintln(list).addAll(\'\', \'\', \'\')\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_134() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.map[String s| s]\n\t\t\tlist\n\t\t}", "ArrayList<String>", "List<String>", "String", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_138() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\t$$CollectionExtensions::<String>addAll(list, null, null)\n\t\t\tlist\n\t\t}", "ArrayList<String>", "boolean", "ArrayList<String>", "ArrayList<String>");
  }
  
  @Test
  public void testDeferredTypeArgumentResolution_158() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = newArrayList\n\t\t\tlist.addAll(null as String[])\n\t\t\tlist\n\t\t}", "ArrayList<String>", "ArrayList<String>", "boolean", "ArrayList<String>");
  }
  
  @Test
  public void testRecursiveTypeArgumentResolution_05() throws Exception {
    this.resolvesFeatureCallsTo("{\n\t\t\tval list = new java.util.ArrayList\n\t\t\tlist.addAll(list)\n\t\t\tlist\n\t\t}", "ArrayList<Object>", "boolean", "ArrayList<Object>", "ArrayList<Object>");
  }
  
  @Test
  public void testFeatureCallWithOperatorOverloading_2() throws Exception {
    this.resolvesFeatureCallsTo("new java.util.ArrayList<Byte>() += \'x\'.getBytes().iterator.next", "boolean", "byte[]", "Iterator<Byte>", "Byte");
  }
  
  @Test
  public void testFeatureCallWithOperatorOverloading_5() throws Exception {
    this.resolvesFeatureCallsTo("new java.util.ArrayList<Byte>() += \'x\'.getBytes()", "boolean", "byte[]");
  }
  
  @Test
  public void testFeatureCallOnIt() throws Exception {
    this.resolvesFeatureCallsTo("{ val it = \'foo\'; length == 3;}", "int", "boolean");
  }
  
  @Test
  public void testStaticMethods_03() throws Exception {
    this.resolvesFeatureCallsTo("newArrayList(newHashSet(\'\'))", "ArrayList<HashSet<String>>", "HashSet<String>");
  }
}