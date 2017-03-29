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
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum DoubleToFloatMutator implements MethodMutatorFactory {

  DOUBLETOFLOAT_MUTATOR;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new DoubleToFloatMethodVisitor(this, context, methodVisitor);
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

class DoubleToFloatMethodVisitor extends MethodVisitor {

  private final MethodMutatorFactory factory;
  private final MutationContext      context;

  DoubleToFloatMethodVisitor(final MethodMutatorFactory factory,
      final MutationContext context, final MethodVisitor delegateMethodVisitor) {
    super(Opcodes.ASM5, delegateMethodVisitor);
    this.factory = factory;
    this.context = context;
  }

  @Override
  public void visitInsn(int opcode) {
    this.mv.visitInsn(opcode);
    if (opcode == Opcodes.DADD || opcode == Opcodes.DSUB
        || opcode == Opcodes.DMUL || opcode == Opcodes.DDIV) {
        final MutationIdentifier newId = this.context.registerMutation(this.factory, "Casted double to float");
        if (this.context.shouldMutate(newId)) {
            this.mv.visitInsn(Opcodes.D2F);
            this.mv.visitInsn(Opcodes.F2D);
        }
    }
  }
}