/*
 * Copyright 2015 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.workbench.common.screens.datamodeller.client.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.services.shared.validation.ValidationService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import org.uberfire.ext.editor.commons.client.validation.ValidatorCallback;
import org.uberfire.mocks.CallerMock;


@RunWith(MockitoJUnitRunner.class)
public class JavaFileNameValidatorTest {

    @Mock
    private ValidatorCallback callback;
    @Mock
    private ValidationService service;
//    @Spy
//    private CallerMock<ValidationService> validationService = new CallerMock<ValidationService>( service );

//    @InjectMocks
    private JavaFileNameValidator validator;
//    private JavaFileNameValidator validator;// = new JavaFileNameValidator();


    @Before
    public void setUp(){
        validator = new JavaFileNameValidator(new CallerMock<ValidationService>( service ));
    }

    @Test
    public void testValidationSuccess() {
        when( service.isJavaFileNameValid( anyString() ) ).thenReturn( Boolean.TRUE );
        validator.validate( "", callback );
        verify( callback ).onSuccess();
    }

    @Test
    public void testValidationFailure() {
        when( service.isJavaFileNameValid( anyString() ) ).thenReturn( Boolean.FALSE );
        validator.validate( "", callback );
        verify( callback ).onFailure();
    }
}
