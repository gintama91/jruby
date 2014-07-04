/***** BEGIN LICENSE BLOCK *****
 * Version: EPL 1.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Eclipse Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/epl-v10.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2007 Charles O Nutter <headius@headius.com>
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the EPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the EPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/
package org.jruby.runtime;

import org.jruby.runtime.builtin.IRubyObject;

/**
 * A Block implemented using a Java-based BlockCallback implementation
 * rather than with an ICallable. For lightweight block logic within
 * Java code.
 */
public class CompiledSharedScopeBlock extends CompiledBlockLight {
    public static Block newCompiledSharedScopeClosure(ThreadContext context, IRubyObject self, Arity arity, DynamicScope dynamicScope,
            CompiledBlockCallback callback, boolean hasMultipleArgsHead, int argumentType) {
        Binding binding = context.currentBinding(self, Visibility.PUBLIC, dynamicScope);
        BlockBody body = new CompiledSharedScopeBlock(arity, dynamicScope, callback, hasMultipleArgsHead, argumentType);
        
        return new Block(body, binding);
    }

    private CompiledSharedScopeBlock(Arity arity, DynamicScope containingScope, CompiledBlockCallback callback, boolean hasMultipleArgsHead, int argumentType) {
        super(arity, containingScope.getStaticScope(), callback, hasMultipleArgsHead, argumentType);
    }
    
    @Override
    protected Frame pre(ThreadContext context, Binding binding) {
        return context.preForBlock(binding);
    }
    
    @Override
    public Block cloneBlock(Binding binding) {
        return new Block(this, binding);
    }
}
