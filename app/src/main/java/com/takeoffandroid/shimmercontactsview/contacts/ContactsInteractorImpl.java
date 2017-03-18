package com.takeoffandroid.shimmercontactsview.contacts;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;


import com.takeoffandroid.shimmercontactsview.R;
import com.takeoffandroid.shimmercontactsview.utils.Logger;
import com.takeoffandroid.shimmercontactsview.utils.Utils;

import java.util.ArrayList;

public class ContactsInteractorImpl implements ContactsInteractor {


    @Override
    public void loadContacts(Context context, OnContactLoadFinishedListener listener) {

        getContactsList(context, listener);

    }


    private void getContactsList(Context context, OnContactLoadFinishedListener listener) {

        if (Utils.isReadContactsPermissionEnabled(context)) {
            ArrayList<ContactDTO> allContacts = new ArrayList();

            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER;
            Cursor cursor = context.getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.Contacts._ID}, selection, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {

                ContactDTO contactDTO = new ContactDTO();

                String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

//                String contactsImage = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID));

                int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                int contactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                contactDTO.setName(contactName);
                contactDTO.setNumber(contactNumber);

                contactDTO.setContactID(contactID);
//                contactDTO.setImage(contactsImage);

//                contactDTO.setPhoto(ContactUtils.getPhotoFromContacts(context, contactID));
                allContacts.add(contactDTO);

                cursor.moveToNext();
            }


            cursor.close();
            cursor = null;


            listener.onContactsLoadSuccess(allContacts);

        } else {

            listener.onContactsLoadFaiure(context.getString(R.string.permisson_error));
        }

    }
}