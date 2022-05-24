/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.rel.rules.materialize;

import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.rel.core.Join;
import org.apache.calcite.rel.core.JoinRelType;
import org.apache.calcite.rel.core.RelFactories;

import org.immutables.value.Value;

/** Rule that matches Left Join. */
@Value.Enclosing
public class MaterializedViewOnlyLeftJoinRule
    extends MaterializedViewLeftJoinRule<MaterializedViewRule.Config> {

  /** Creates a MaterializedViewOnlyLeftJoinRule. */
  MaterializedViewOnlyLeftJoinRule(Config config) {
    super(config);
  }

  @Override public void onMatch(RelOptRuleCall call) {
    final Join join = call.rel(0);
    perform(call, null, join);
  }

  /** Rule configuration. */
  @Value.Immutable(singleton = false)
  public interface Config extends MaterializedViewRule.Config {
    ImmutableMaterializedViewOnlyLeftJoinRule.Config DEFAULT =
        ImmutableMaterializedViewOnlyLeftJoinRule.Config.builder()
        .withOperandSupplier(
            b -> b.operand(Join.class)
                .predicate(join -> join.getJoinType() == JoinRelType.LEFT)
                .anyInputs())
        .withRelBuilderFactory(RelFactories.LOGICAL_BUILDER)
        .withDescription("MaterializedViewLeftJoinRule(Join)")
        .withGenerateUnionRewriting(false)
        .withUnionRewritingPullProgram(null)
        .withFastBailOut(true)
        .withJoinType(JoinRelType.LEFT)
        .build();

    @Override default MaterializedViewOnlyLeftJoinRule toRule() {
      return new MaterializedViewOnlyLeftJoinRule(this);
    }
  }
}
