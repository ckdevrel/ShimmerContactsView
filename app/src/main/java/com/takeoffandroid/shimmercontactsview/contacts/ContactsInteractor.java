package com.takeoffandroid.shimmercontactsview.contacts;

import android.content.Context;

import java.util.ArrayList;

public interface ContactsInteractor {

    interface OnContactLoadFinishedListener {

        void onContactsLoadSuccess(ArrayList<ContactDTO> contactDTOArrayList);

        void onContactsLoadFaiure(String message);

    }

    void loadContacts(Context context, OnContactLoadFinishedListener listener);

}