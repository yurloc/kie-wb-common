/**
 * Copyright 2012 JBoss Inc
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

package org.kie.workbench.common.screens.datamodeller.client;

import org.jboss.errai.bus.client.api.messaging.Message;
import org.kie.uberfire.client.callbacks.DefaultErrorCallback;
import org.kie.uberfire.client.common.BusyPopup;
import org.kie.uberfire.client.common.popups.errors.ErrorPopup;
import org.kie.workbench.common.screens.datamodeller.client.resources.i18n.Constants;

public class DataModelerErrorCallback extends DefaultErrorCallback {

    String localMessage = "";

    public DataModelerErrorCallback() {
    }

    public DataModelerErrorCallback(String localMessage) {
        this.localMessage = localMessage;
    }

    @Override
    public boolean error( final Message message,
            final Throwable throwable ) {

        BusyPopup.close();
        if (throwable.getClass().getName().startsWith( "org.uberfire.java.nio.file" )) {
            super.error( message, throwable );
        } else {
            ErrorPopup.showMessage( Constants.INSTANCE.modeler_callback_error( localMessage, throwable.getMessage() ) );
        }
        return false;
    }
}
