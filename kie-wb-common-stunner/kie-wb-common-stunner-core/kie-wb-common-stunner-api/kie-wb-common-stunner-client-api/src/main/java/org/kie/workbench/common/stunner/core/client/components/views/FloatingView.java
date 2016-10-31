/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.core.client.components.views;

public interface FloatingView<V> {

    void add( V item );

    FloatingView<V> setOffsetX( double ox );

    FloatingView<V> setOffsetY( double oy );

    FloatingView<V> setX( double x );

    FloatingView<V> setY( double y );

    FloatingView<V> show();

    FloatingView<V> hide();

    FloatingView<V> setTimeOut( int timeOut );

    FloatingView<V> clearTimeOut();

    void clear();

    void destroy();

}