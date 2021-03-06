/*
 * Copyright 2014 JBoss, by Red Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.workbench.common.screens.datamodeller.model.index;

import java.util.ArrayList;
import java.util.List;

import org.kie.workbench.common.screens.datamodeller.model.index.terms.valueterms.ValueFieldNameIndexTerm;
import org.kie.workbench.common.screens.datamodeller.model.index.terms.valueterms.ValueFieldTypeIndexTerm;
import org.kie.workbench.common.services.refactoring.model.index.IndexElementsGenerator;
import org.uberfire.commons.data.Pair;
import org.uberfire.commons.validation.PortablePreconditions;

public class FieldType implements IndexElementsGenerator {

    ValueFieldNameIndexTerm fieldNameTerm;

    ValueFieldTypeIndexTerm fieldTypeTerm;

    public FieldType( final ValueFieldNameIndexTerm fieldNameTerm, final ValueFieldTypeIndexTerm fieldTypeTerm ) {
        this.fieldNameTerm = PortablePreconditions.checkNotNull( "fieldNameTerm", fieldNameTerm );
        this.fieldTypeTerm = PortablePreconditions.checkNotNull( "fieldTypeTerm", fieldTypeTerm );
    }

    @Override public List<Pair<String, String>> toIndexElements() {
        List<Pair<String, String>> values = new ArrayList<Pair<String, String>>( );

        final List<Pair<String, String>> indexElements = new ArrayList<Pair<String, String>>();
        indexElements.add( new Pair<String, String>( fieldTypeTerm.getTerm() + ":" + fieldNameTerm.getValue(), fieldTypeTerm.getValue()));
        return indexElements;

    }
}
