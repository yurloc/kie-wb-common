/*
 * Copyright 2014 JBoss Inc
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

package org.kie.workbench.common.widgets.client.versionhistory;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.guvnor.common.services.shared.version.VersionService;
import org.guvnor.common.services.shared.version.events.RestoreEvent;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.kie.uberfire.client.callbacks.HasBusyIndicatorDefaultErrorCallback;
import org.kie.uberfire.client.common.BusyIndicatorView;
import org.kie.workbench.common.widgets.client.popups.file.CommandWithCommitMessage;
import org.kie.workbench.common.widgets.client.popups.file.SavePopup;
import org.kie.workbench.common.widgets.client.resources.i18n.CommonConstants;
import org.uberfire.backend.vfs.ObservablePath;
import org.uberfire.backend.vfs.Path;

public class RestorePopup {

    @Inject
    private BusyIndicatorView busyIndicatorView;

    @Inject
    private Caller<VersionService> versionService;

    @Inject
    private Event<RestoreEvent> restoreEvent;

    @Inject
    private RestoreUtil restoreUtil;

    public void show(final ObservablePath currentPath, final String currentVersionRecordUri) {

        new SavePopup(new CommandWithCommitMessage() {
            @Override
            public void execute(final String comment) {
                busyIndicatorView.showBusyIndicator(CommonConstants.INSTANCE.Restoring());
                versionService.call(getRestorationSuccessCallback(currentVersionRecordUri),
                        new HasBusyIndicatorDefaultErrorCallback(busyIndicatorView)).restore(currentPath, comment);
            }
        }).show();
    }

    private RemoteCallback<Path> getRestorationSuccessCallback(final String currentVersionRecordUri) {
        return new RemoteCallback<Path>() {
            @Override
            public void callback(final Path restored) {
                busyIndicatorView.hideBusyIndicator();
                restoreEvent.fire(new RestoreEvent(restoreUtil.createObservablePath(restored, currentVersionRecordUri)));
            }
        };
    }
}
