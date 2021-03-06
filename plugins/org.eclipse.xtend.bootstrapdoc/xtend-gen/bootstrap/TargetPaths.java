package bootstrap;

import bootstrap.TargetPathAdapter;
import bootstrap.XdocExtensions;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xdoc.xdoc.AbstractSection;
import org.eclipse.xtext.xdoc.xdoc.Document;
import org.eclipse.xtext.xdoc.xdoc.Identifiable;

@SuppressWarnings("all")
public class TargetPaths {
  @Inject
  @Extension
  private XdocExtensions _xdocExtensions;
  
  public boolean splitAt(final Document document, final Function1<? super AbstractSection,? extends Boolean> predicate, final String prefix) {
    boolean _xblockexpression = false;
    {
      TargetPathAdapter _targetPathAdapter = new TargetPathAdapter();
      final TargetPathAdapter adapter = _targetPathAdapter;
      this.traverseChildren(document, adapter, prefix, "", predicate);
      Resource _eResource = document.eResource();
      ResourceSet _resourceSet = _eResource.getResourceSet();
      EList<Adapter> _eAdapters = _resourceSet.eAdapters();
      boolean _add = _eAdapters.add(adapter);
      _xblockexpression = (_add);
    }
    return _xblockexpression;
  }
  
  public String getTargetPath(final Identifiable element) {
    String _elvis = null;
    TargetPathAdapter _targetPathAdapter = this.getTargetPathAdapter(element);
    Map<Identifiable,String> _targetPaths = null;
    if (_targetPathAdapter!=null) {
      _targetPaths=_targetPathAdapter.getTargetPaths();
    }
    String _get = null;
    if (_targetPaths!=null) {
      _get=_targetPaths.get(element);
    }
    if (_get != null) {
      _elvis = _get;
    } else {
      _elvis = ObjectExtensions.<String>operator_elvis(_get, "");
    }
    return _elvis;
  }
  
  public boolean isTargetRootElement(final AbstractSection element) {
    boolean _xblockexpression = false;
    {
      TargetPathAdapter _targetPathAdapter = this.getTargetPathAdapter(element);
      List<AbstractSection> _targetFileRoots = null;
      if (_targetPathAdapter!=null) {
        _targetFileRoots=_targetPathAdapter.getTargetFileRoots();
      }
      final List<AbstractSection> targetFileRoots = _targetFileRoots;
      boolean _xifexpression = false;
      boolean _notEquals = (!Objects.equal(targetFileRoots, null));
      if (_notEquals) {
        boolean _contains = targetFileRoots.contains(element);
        _xifexpression = _contains;
      } else {
        _xifexpression = (element instanceof Document);
      }
      _xblockexpression = (_xifexpression);
    }
    return _xblockexpression;
  }
  
  public List<? extends AbstractSection> getTargetRoots(final Document document) {
    List<? extends AbstractSection> _elvis = null;
    TargetPathAdapter _targetPathAdapter = this.getTargetPathAdapter(document);
    List<AbstractSection> _targetFileRoots = null;
    if (_targetPathAdapter!=null) {
      _targetFileRoots=_targetPathAdapter.getTargetFileRoots();
    }
    if (_targetFileRoots != null) {
      _elvis = _targetFileRoots;
    } else {
      ArrayList<Document> _newArrayList = CollectionLiterals.<Document>newArrayList(document);
      _elvis = ObjectExtensions.<List<? extends AbstractSection>>operator_elvis(_targetFileRoots, _newArrayList);
    }
    return _elvis;
  }
  
  protected void traverseChildren(final Identifiable element, final TargetPathAdapter adapter, final String prefix, final String parentPath, final Function1<? super AbstractSection,? extends Boolean> predicate) {
    String thisPath = parentPath;
    if ((element instanceof AbstractSection)) {
      final AbstractSection resolved = this._xdocExtensions.resolve(((AbstractSection) ((AbstractSection)element)));
      boolean _notEquals = (!Objects.equal(resolved, element));
      if (_notEquals) {
        this.traverseChildren(resolved, adapter, prefix, parentPath, predicate);
        return;
      }
      Boolean _apply = predicate.apply(((AbstractSection) ((AbstractSection)element)));
      if ((_apply).booleanValue()) {
        String _xifexpression = null;
        List<AbstractSection> _targetFileRoots = adapter.getTargetFileRoots();
        boolean _isEmpty = _targetFileRoots.isEmpty();
        if (_isEmpty) {
          String _plus = (prefix + ".html");
          _xifexpression = _plus;
        } else {
          List<AbstractSection> _targetFileRoots_1 = adapter.getTargetFileRoots();
          int _size = _targetFileRoots_1.size();
          String _plus_1 = (prefix + Integer.valueOf(_size));
          String _plus_2 = (_plus_1 + ".html");
          _xifexpression = _plus_2;
        }
        thisPath = _xifexpression;
        List<AbstractSection> _targetFileRoots_2 = adapter.getTargetFileRoots();
        _targetFileRoots_2.add(((AbstractSection) ((AbstractSection)element)));
      }
    }
    Map<Identifiable,String> _targetPaths = adapter.getTargetPaths();
    _targetPaths.put(element, thisPath);
    EList<EObject> _eContents = element.eContents();
    Iterable<Identifiable> _filter = Iterables.<Identifiable>filter(_eContents, Identifiable.class);
    for (final Identifiable child : _filter) {
      this.traverseChildren(child, adapter, prefix, thisPath, predicate);
    }
  }
  
  protected TargetPathAdapter getTargetPathAdapter(final Identifiable element) {
    TargetPathAdapter _head = null;
    Iterable<TargetPathAdapter> _filter = null;
    Resource _eResource = null;
    if (element!=null) {
      _eResource=element.eResource();
    }
    ResourceSet _resourceSet = null;
    if (_eResource!=null) {
      _resourceSet=_eResource.getResourceSet();
    }
    EList<Adapter> _eAdapters = null;
    if (_resourceSet!=null) {
      _eAdapters=_resourceSet.eAdapters();
    }
    if (_eAdapters!=null) {
      _filter=Iterables.<TargetPathAdapter>filter(_eAdapters, TargetPathAdapter.class);
    }
    if (_filter!=null) {
      _head=IterableExtensions.<TargetPathAdapter>head(_filter);
    }
    return _head;
  }
}
