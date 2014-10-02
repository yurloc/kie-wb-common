/*
 * Copyright 2014 JBoss by Red Hat.
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
package org.kie.workbench.common.widgets.client.popups.file;

import java.util.HashMap;
import java.util.Map;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.base.TextNode;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwtmockito.GwtMockito;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.gwtmockito.WithClassesToStub;
import com.google.gwtmockito.fakes.FakeProvider;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.services.shared.validation.Validator;
import org.kie.workbench.common.services.shared.validation.ValidatorCallback;
import org.kie.workbench.common.widgets.client.resources.i18n.CommonConstants;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.uberfire.backend.vfs.Path;

import static org.junit.Assert.*;

@RunWith( GwtMockitoTestRunner.class )
@WithClassesToStub( {
    TextNode.class,
    FlexTable.class,
    HTMLTable.class
} )
public class CopyPopupTest {

    private static final String NAME_TEXT = "copy name";
    private static final String COMMENT_TEXT = "hello world";
    private static final String PATH = "dir/file.ext";
    private Map<String, Button> buttonMap = new HashMap<String, Button>();
    @Captor
    private ArgumentCaptor<FileNameAndCommitMessage> msgCaptor;
    @Captor
    private ArgumentCaptor<ClickHandler> clickHandlerCaptor;

    @Mock
    private Path path;
    @Mock
    private CommandWithFileNameAndCommitMessage cmd;
    @Mock
    private Validator successValidator;
    @Mock
    private Validator failureValidator;

    @Before
    public void setUp() {

        // stub return values
        Mockito.when( path.getFileName() ).thenReturn( PATH );

        // stub validation results
        Mockito.doAnswer( new Answer<Void>() {
            @Override
            public Void answer( InvocationOnMock invocation ) throws Throwable {
                ValidatorCallback callback = (ValidatorCallback) invocation.getArguments()[1];
                callback.onSuccess();
                return null;
            }
        } ).when( successValidator ).validate( Mockito.any( String.class ), Mockito.any( ValidatorCallback.class ) );

        Mockito.doAnswer( new Answer<Void>() {
            @Override
            public Void answer( InvocationOnMock invocation ) throws Throwable {
                ValidatorCallback callback = (ValidatorCallback) invocation.getArguments()[1];
                callback.onFailure();
                return null;
            }
        } ).when( failureValidator ).validate( Mockito.any( String.class ), Mockito.any( ValidatorCallback.class ) );

        GwtMockito.useProviderForType( Button.class, new FakeProvider<Button>() {
            @Override
            public Button getFake( Class<?> type ) {
                Button mock = Mockito.mock( Button.class );
                Mockito.doAnswer( new Answer<Void>() {
                    @Override
                    public Void answer( InvocationOnMock invocation ) throws Throwable {
                        String text = (String) invocation.getArguments()[0];
                        buttonMap.put( text, (Button) invocation.getMock() );
                        return null;
                    }
                } ).when( mock ).setText( Mockito.any( String.class ) );
                return mock;
            }
        } );
    }

    private Button copyButton() {
        return buttonMap.get( CommonConstants.INSTANCE.CopyPopupCreateACopy() );
    }

    private Button cancelButton() {
        return buttonMap.get( CommonConstants.INSTANCE.Cancel() );
    }

    private void createPopup( Validator validator ) {
        CopyPopup copyPopup = new CopyPopup( path, validator, cmd );
        Mockito.when( copyPopup.nameTextBox.getText() ).thenReturn( NAME_TEXT );
        Mockito.when( copyPopup.checkInCommentTextBox.getText() ).thenReturn( COMMENT_TEXT );
    }

    @Test
    public void testValidationSuccessful() {
        // create copy popup
        createPopup( successValidator );
        Mockito.verify( copyButton() ).addClickHandler( clickHandlerCaptor.capture() );
        // simulate submitting the popup
        clickHandlerCaptor.getValue().onClick( new ClickEvent() {
        } );
        // verify validation was invoked
        Mockito.verify( successValidator ).validate( Mockito.any( String.class ), Mockito.any( ValidatorCallback.class ) );
        // verify command was executed
        Mockito.verify( cmd ).execute( msgCaptor.capture() );
        // verify the message passed to the command
        assertThat( msgCaptor.getValue().getNewFileName(), CoreMatchers.equalTo( NAME_TEXT ) );
        assertThat( msgCaptor.getValue().getCommitMessage(), CoreMatchers.equalTo( COMMENT_TEXT ) );
    }

    @Test
    public void testValidationFailed() {
        // create copy popup
        createPopup( failureValidator );
        Mockito.verify( copyButton() ).addClickHandler( clickHandlerCaptor.capture() );
        // simulate submitting the popup
        clickHandlerCaptor.getValue().onClick( new ClickEvent() {
        } );
        // verify validation was invoked
        Mockito.verify( failureValidator ).validate( Mockito.any( String.class ), Mockito.any( ValidatorCallback.class ) );

        // verify command was NOT executed
        Mockito.verify( cmd, Mockito.never() ).execute( Mockito.any( FileNameAndCommitMessage.class ) );

        // cannot verify validation failure message because it is presented using static method Window.alert()
    }

    @Test
    public void testPopupCanceled() {
        // create copy popup
        createPopup( successValidator );
        Mockito.verify( cancelButton() ).addClickHandler( clickHandlerCaptor.capture() );
        // simulate submitting the popup
        clickHandlerCaptor.getValue().onClick( new ClickEvent() {
        } );
        // verify validation was NOT invoked
        Mockito.verify( successValidator, Mockito.never() ).validate( Mockito.any( String.class ), Mockito.any( ValidatorCallback.class ) );

        // verify command was NOT executed
        Mockito.verify( cmd, Mockito.never() ).execute( Mockito.any( FileNameAndCommitMessage.class ) );
    }
}
