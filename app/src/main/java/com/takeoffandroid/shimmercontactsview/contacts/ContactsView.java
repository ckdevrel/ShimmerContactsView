package com.takeoffandroid.shimmercontactsview.contacts;


import java.util.ArrayList;

/**
 * Created by ennur on 6/25/16.
 */
public interface ContactsView {
    void onContactsLoading();

    void onContactsLoadingComplete();

    void onContactsLoadFailure(String message);

    void onContactsLoadSuccess(ArrayList<ContactDTO> contactDTOArrayList);

}
