package com.sungwoo.boostcamp.photoalbumfilter;

import io.realm.RealmObject;

/**
 * Created by psw10 on 2017-10-24.
 */

public class CheckedRealmModel extends RealmObject {
    public CheckedRealmModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CheckedRealmModel() {
    }

    public String imageUrl;
}
