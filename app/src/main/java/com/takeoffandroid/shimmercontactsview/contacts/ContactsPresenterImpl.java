package com.takeoffandroid.shimmercontactsview.contacts;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by f22labs on 17/03/17.
 */

public class ContactsPresenterImpl implements ContactsPresenter, ContactsInteractor.OnContactLoadFinishedListener {

    private Context mContext;
    private ContactsView view;
    private ContactsInteractor contactsInteractor;




    public ContactsPresenterImpl(Context context,ContactsView view) {

        this.mContext = context;
        this.view = view;
        this.contactsInteractor = new ContactsInteractorImpl();
    }



    @Override
    public void onContactsLoadSuccess(ArrayList<ContactDTO> contactDTOArrayList) {

        view.onContactsLoadingComplete();

        view.onContactsLoadSuccess(contactDTOArrayList);
    }

    @Override
    public void onContactsLoadFaiure(String message) {

        view.onContactsLoadingComplete();

        view.onContactsLoadFailure(message);
    }

    @Override
    public void loadContacts(Context context) {

        view.onContactsLoading();
        contactsInteractor.loadContacts(mContext, this);

    }
}
