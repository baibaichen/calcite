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

import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Project;
import org.apache.calcite.rel.metadata.RelMetadataQuery;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.rex.RexTableInputRef;
import org.apache.calcite.tools.RelBuilder;

import com.google.common.collect.Multimap;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Set;

/** Materialized view rewriting for Left join.
 *
 * @param <C> Configuration type
 */
public abstract class MaterializedViewLeftJoinRule<C extends MaterializedViewRule.Config>
    extends MaterializedViewJoinRule<C> {

  /** Creates a MaterializedViewJoinRule. */
  MaterializedViewLeftJoinRule(C config) {
    super(config);
  }

  /** Not Support {@link MatchModality#QUERY_PARTIAL} yet. */
  @Override protected boolean compensatePartial(
      Set<RexTableInputRef.RelTableRef> sourceTableRefs,
      EquivalenceClasses sourceEC,
      Set<RexTableInputRef.RelTableRef> targetTableRefs,
      @Nullable Multimap<RexTableInputRef, RexTableInputRef> compensationEquiColumns) {
    return false;
  }

  /** Not Support {@link MatchModality#VIEW_PARTIAL} yet. */
  @Override protected @Nullable ViewPartialRewriting compensateViewPartial(
      RelBuilder relBuilder, RexBuilder rexBuilder, RelMetadataQuery mq, RelNode input,
      @Nullable Project topProject, RelNode node, Set<RexTableInputRef.RelTableRef> queryTableRefs,
      EquivalenceClasses queryEC,
      @Nullable Project topViewProject, RelNode viewNode,
      Set<RexTableInputRef.RelTableRef> viewTableRefs) {
    return null;
  }


}
