package org.eclipse.xtext.example.domainmodel.jvmmodel;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtend2.lib.StringConcatenationClient.TargetStringConcatenation;
import org.eclipse.xtext.common.types.JvmConstructor;
import org.eclipse.xtext.common.types.JvmField;
import org.eclipse.xtext.common.types.JvmFormalParameter;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmMember;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmParameterizedTypeReference;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.example.domainmodel.domainmodel.Entity;
import org.eclipse.xtext.example.domainmodel.domainmodel.Feature;
import org.eclipse.xtext.example.domainmodel.domainmodel.Operation;
import org.eclipse.xtext.example.domainmodel.domainmodel.Property;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer;
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor;
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor.IPostIndexingInitializing;
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class DomainmodelJvmModelInferrer extends AbstractModelInferrer {
  @Inject
  @Extension
  private JvmTypesBuilder _jvmTypesBuilder;
  
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  protected void _infer(final Entity entity, final IJvmDeclaredTypeAcceptor acceptor, final boolean prelinkingPhase) {
    QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(entity);
    JvmGenericType _class = this._jvmTypesBuilder.toClass(entity, _fullyQualifiedName);
    IPostIndexingInitializing<JvmGenericType> _accept = acceptor.<JvmGenericType>accept(_class);
    final Procedure1<JvmGenericType> _function = new Procedure1<JvmGenericType>() {
      public void apply(final JvmGenericType it) {
        String _documentation = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.getDocumentation(entity);
        DomainmodelJvmModelInferrer.this._jvmTypesBuilder.setDocumentation(it, _documentation);
        JvmParameterizedTypeReference _superType = entity.getSuperType();
        boolean _notEquals = (!Objects.equal(_superType, null));
        if (_notEquals) {
          EList<JvmTypeReference> _superTypes = it.getSuperTypes();
          JvmParameterizedTypeReference _superType_1 = entity.getSuperType();
          JvmTypeReference _cloneWithProxies = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.cloneWithProxies(_superType_1);
          DomainmodelJvmModelInferrer.this._jvmTypesBuilder.<JvmTypeReference>operator_add(_superTypes, _cloneWithProxies);
        }
        JvmTypeReference _newTypeRef = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.newTypeRef(it);
        final JvmTypeReference procedure = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.newTypeRef(entity, Procedure1.class, _newTypeRef);
        EList<JvmMember> _members = it.getMembers();
        final Procedure1<JvmConstructor> _function = new Procedure1<JvmConstructor>() {
          public void apply(final JvmConstructor it) {
          }
        };
        JvmConstructor _constructor = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.toConstructor(entity, _function);
        DomainmodelJvmModelInferrer.this._jvmTypesBuilder.<JvmConstructor>operator_add(_members, _constructor);
        EList<JvmMember> _members_1 = it.getMembers();
        final Procedure1<JvmConstructor> _function_1 = new Procedure1<JvmConstructor>() {
          public void apply(final JvmConstructor it) {
            EList<JvmFormalParameter> _parameters = it.getParameters();
            JvmFormalParameter _parameter = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.toParameter(entity, "initializer", procedure);
            DomainmodelJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
            StringConcatenationClient _client = new StringConcatenationClient() {
              @Override
              protected void appendTo(TargetStringConcatenation _builder) {
                _builder.append("initializer.apply(this);");
              }
            };
            DomainmodelJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _client);
          }
        };
        JvmConstructor _constructor_1 = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.toConstructor(entity, _function_1);
        DomainmodelJvmModelInferrer.this._jvmTypesBuilder.<JvmConstructor>operator_add(_members_1, _constructor_1);
        final ArrayList<JvmField> fields = CollectionLiterals.<JvmField>newArrayList();
        EList<Feature> _features = entity.getFeatures();
        for (final Feature f : _features) {
          boolean _matched = false;
          if (!_matched) {
            if (f instanceof Property) {
              _matched=true;
              String _name = ((Property)f).getName();
              JvmTypeReference _type = ((Property)f).getType();
              final JvmField field = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.toField(f, _name, _type);
              fields.add(field);
              EList<JvmMember> _members_2 = it.getMembers();
              DomainmodelJvmModelInferrer.this._jvmTypesBuilder.<JvmField>operator_add(_members_2, field);
              EList<JvmMember> _members_3 = it.getMembers();
              String _name_1 = ((Property)f).getName();
              JvmTypeReference _type_1 = ((Property)f).getType();
              JvmOperation _getter = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.toGetter(f, _name_1, _type_1);
              DomainmodelJvmModelInferrer.this._jvmTypesBuilder.<JvmOperation>operator_add(_members_3, _getter);
              EList<JvmMember> _members_4 = it.getMembers();
              String _name_2 = ((Property)f).getName();
              JvmTypeReference _type_2 = ((Property)f).getType();
              JvmOperation _setter = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.toSetter(f, _name_2, _type_2);
              DomainmodelJvmModelInferrer.this._jvmTypesBuilder.<JvmOperation>operator_add(_members_4, _setter);
            }
          }
          if (!_matched) {
            if (f instanceof Operation) {
              _matched=true;
              EList<JvmMember> _members_2 = it.getMembers();
              String _name = ((Operation)f).getName();
              JvmTypeReference _elvis = null;
              JvmTypeReference _type = ((Operation)f).getType();
              if (_type != null) {
                _elvis = _type;
              } else {
                JvmTypeReference _inferredType = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.inferredType();
                _elvis = ObjectExtensions.<JvmTypeReference>operator_elvis(_type, _inferredType);
              }
              final Procedure1<JvmOperation> _function_2 = new Procedure1<JvmOperation>() {
                public void apply(final JvmOperation it) {
                  String _documentation = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.getDocumentation(f);
                  DomainmodelJvmModelInferrer.this._jvmTypesBuilder.setDocumentation(it, _documentation);
                  EList<JvmFormalParameter> _params = ((Operation)f).getParams();
                  for (final JvmFormalParameter p : _params) {
                    EList<JvmFormalParameter> _parameters = it.getParameters();
                    String _name = p.getName();
                    JvmTypeReference _parameterType = p.getParameterType();
                    JvmFormalParameter _parameter = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.toParameter(p, _name, _parameterType);
                    DomainmodelJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
                  }
                  XExpression _body = ((Operation)f).getBody();
                  DomainmodelJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _body);
                }
              };
              JvmOperation _method = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.toMethod(f, _name, _elvis, _function_2);
              DomainmodelJvmModelInferrer.this._jvmTypesBuilder.<JvmOperation>operator_add(_members_2, _method);
            }
          }
        }
        EList<JvmMember> _members_2 = it.getMembers();
        JvmOperation _toStringMethod = DomainmodelJvmModelInferrer.this._jvmTypesBuilder.toToStringMethod(entity, it);
        DomainmodelJvmModelInferrer.this._jvmTypesBuilder.<JvmOperation>operator_add(_members_2, _toStringMethod);
      }
    };
    _accept.initializeLater(_function);
  }
  
  public void infer(final EObject entity, final IJvmDeclaredTypeAcceptor acceptor, final boolean prelinkingPhase) {
    if (entity instanceof Entity) {
      _infer((Entity)entity, acceptor, prelinkingPhase);
      return;
    } else if (entity != null) {
      _infer(entity, acceptor, prelinkingPhase);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(entity, acceptor, prelinkingPhase).toString());
    }
  }
}
