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
package org.kie.workbench.common.screens.projecteditor.client.forms;

import org.guvnor.m2repo.service.M2RepoService;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class DependencySelectorPopupTest {

    @Mock
    private DependencySelectorPopupView view;
    @Mock
    private Caller<M2RepoService> m2RepoServiceCaller;
    @Mock
    private M2RepoService m2RepoService;

    @InjectMocks
    private DependencySelectorPopup popup;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // wire M2RepoService mock with it's caller
        Mockito.when(m2RepoServiceCaller.call(Mockito.any(RemoteCallback.class))).thenReturn(m2RepoService);

        popup.init();
    }

    @Test
    public void testSomeMethod() {
        popup.show();
    }

}
