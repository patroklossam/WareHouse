/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 *
 * Copyright ownership: Patroklos Samaras
 */
package com.storage.mywarehouse;

import java.util.Observable;

/**
 *
 * @author Patroklos
 */
class MyObservable extends Observable {

    MyObservable() {
        super();
    }

    void changeData(Object data) {
        setChanged(); // the two methods of Observable class
        notifyObservers(data);
    }
}
