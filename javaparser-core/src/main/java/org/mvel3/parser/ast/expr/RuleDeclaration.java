/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package org.mvel3.parser.ast.expr;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import org.mvel3.parser.ast.visitor.DrlGenericVisitor;
import org.mvel3.parser.ast.visitor.DrlVoidVisitor;
import org.mvel3.parser.ast.visitor.DrlVoidVisitorAdapter;

/**
 * Represents a rule declaration in a DRL (Drools Rule Language) file.
 * Note that this is not tested in mvel3 project. Will be tested in drlx-parser project.
 */
public class RuleDeclaration extends TypeDeclaration<RuleDeclaration> {

    private RuleBody ruleBody;

    // This is the original constructor
    public RuleDeclaration(TokenRange range, NodeList<AnnotationExpr> annotations, SimpleName name, RuleBody ruleBody) {
        this(range, new NodeList<>(), annotations, name, new NodeList<>(), ruleBody);
    }

    // This is added for generator
    @AllFieldsConstructor
    public RuleDeclaration(NodeList<Modifier> modifiers, NodeList<AnnotationExpr> annotations, SimpleName name, NodeList<BodyDeclaration<?>> members, RuleBody ruleBody) {
        this(null, modifiers, annotations, name, members, ruleBody);
    }

    // expected to be MainConstructor
    public RuleDeclaration(TokenRange range, NodeList<Modifier> modifiers, NodeList<AnnotationExpr> annotations, SimpleName name,  NodeList<BodyDeclaration<?>> members, RuleBody ruleBody) {
        super(range, modifiers, annotations, name, members);
        this.ruleBody = ruleBody;
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return ((DrlGenericVisitor<R, A>) v).visit(this, arg);
    }

    public static <A> VoidVisitor<A> getDrlVoidVisitor(VoidVisitor<A> v) {
        if (!(v instanceof DrlVoidVisitor)) {
            v = new DrlVoidVisitorAdapter<>(v);
        }
        return v;
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v = getDrlVoidVisitor(v);
        ((DrlVoidVisitor<A>) v).visit(this, arg);
    }

    public RuleBody getRuleBody() {
        return ruleBody;
    }

    @Override
    public ResolvedReferenceTypeDeclaration resolve() {
        return null;
    }
}
