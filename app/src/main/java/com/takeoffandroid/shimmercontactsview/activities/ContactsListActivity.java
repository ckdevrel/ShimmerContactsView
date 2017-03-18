package com.takeoffandroid.shimmercontactsview.activities;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.takeoffandroid.shimmercontactsview.R;
import com.takeoffandroid.shimmercontactsview.adapter.ContactsListAdapter;
import com.takeoffandroid.shimmercontactsview.contacts.ContactDTO;
import com.takeoffandroid.shimmercontactsview.contacts.ContactsPresenter;
import com.takeoffandroid.shimmercontactsview.contacts.ContactsPresenterImpl;
import com.takeoffandroid.shimmercontactsview.contacts.ContactsView;
import com.takeoffandroid.shimmercontactsview.utils.Logger;
import com.takeoffandroid.shimmercontactsview.utils.Utils;
import com.takeoffandroid.shimmercontactsview.views.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ContactsListActivity extends BaseActivity implements ContactsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view_contacts)
    ShimmerRecyclerView recyclerViewContacts;

    private ContactsListAdapter mAdapter;
    private ContactsPresenter contactsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        ButterKnife.bind(this);

        initToolbar(toolbar);

        mAdapter = new ContactsListAdapter(ContactsListActivity.this,new ArrayList<ContactDTO>());
        recyclerViewContacts.setHasFixedSize(true);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewContacts.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewContacts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewContacts.setAdapter(mAdapter);

        contactsPresenter = new ContactsPresenterImpl(ContactsListActivity.this, this);

        contactsPresenter.loadContacts(ContactsListActivity.this);



    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }





    @Override
    public void onContactsLoading() {

        recyclerViewContacts.showShimmerAdapter();
    }

    @Override
    public void onContactsLoadingComplete() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerViewContacts.hideShimmerAdapter();
            }
        },2000);

    }

    @Override
    public void onContactsLoadFailure(String message) {

        Logger.i("loading failure");

        if(message.equals(getString(R.string.permisson_error))){

            ContactsListActivityPermissionsDispatcher.requestContactsPermissionWithCheck(ContactsListActivity.this);

        }

    }

    @Override
    public void onContactsLoadSuccess(ArrayList<ContactDTO> contactDTOArrayList) {

        Logger.i((contactDTOArrayList != null ? contactDTOArrayList.size() : 0 )+ " Loading success!");

        mAdapter.updateContacts(contactDTOArrayList);

    }


    @NeedsPermission({Manifest.permission.READ_CONTACTS})
    void requestContactsPermission(){

        contactsPresenter = new ContactsPresenterImpl(ContactsListActivity.this, this);

        contactsPresenter.loadContacts(ContactsListActivity.this);
    }


    @OnPermissionDenied(Manifest.permission.READ_CONTACTS)
    void deniedContactsPermission() {
        finish();
    }



//    @OnShowRationale({Manifest.permission.READ_CONTACTS})
//    void showRationaleForContact(PermissionRequest request) {
//        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
//        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
//        Utils.showRationaleDialog(ContactsListActivity.this,"Need permission to access contacts", request);
//    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        ContactsListActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}