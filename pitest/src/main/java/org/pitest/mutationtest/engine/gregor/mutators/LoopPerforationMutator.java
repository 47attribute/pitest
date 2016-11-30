/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.engine.gregor.mutators;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum LoopPerforationMutator implements MethodMutatorFactory {

  LOOPPERFORATION_MUTATOR;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new LoopPerforationMethodVisitor(this, context, methodVisitor);
  }

  @Override
  public String getGloballyUniqueId() {
    return this.getClass().getName();
  }

  @Override
  public String getName() {
    return name();
  }
}

class LoopPerforationMethodVisitor extends MethodVisitor {

  private final MethodMutatorFactory factory;
  private final MutationContext      context;

  private int lastLineNumber;
  private java.util.Set<Integer> loopLines = new java.util.HashSet<Integer>();

  LoopPerforationMethodVisitor(final MethodMutatorFactory factory,
      final MutationContext context, final MethodVisitor delegateMethodVisitor) {
    super(Opcodes.ASM5, delegateMethodVisitor);
    this.factory = factory;
    this.context = context;
  }

  @Override
  public void visitLineNumber(int line, Label start) {
    this.lastLineNumber = line;
    this.mv.visitLineNumber(line, start);
  }

  @Override
  public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
    if (owner.equals("edu/illinois/approximute/LoopLabel") && name.equals("label")) {
      this.loopLines.add(this.lastLineNumber);
      return;
    }
    this.mv.visitMethodInsn(opcode, owner, name, desc, itf);
  }

  @Override
  public void visitIincInsn(final int var, final int increment) {
    if (loopLines.contains(this.lastLineNumber) && (increment == 1 || increment == -1)) {
      final MutationIdentifier newId = this.context.registerMutation(
          this.factory, "Changed increment from " + increment + " to "
              + increment * 2);
      if (this.context.shouldMutate(newId)) {
        this.mv.visitIincInsn(var, increment * 2);
      } else {
        this.mv.visitIincInsn(var, increment);
      }
    } else {
      this.mv.visitIincInsn(var, increment);
    }
  }

}
