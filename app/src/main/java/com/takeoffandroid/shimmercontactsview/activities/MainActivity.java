package com.takeoffandroid.shimmercontactsview.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements ContactsView {


    @BindView(R.id.btn_bottom_sheet)
    Button btnBottomSheet;

    @BindView(R.id.btn_activity)
    Button btnActivity;

    private ShimmerRecyclerView recyclerViewContacts;

    private ContactsListAdapter mAdapter;
    private ContactsPresenter contactsPresenter;

    private BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }


    @OnClick(R.id.btn_activity)
    void openContactsList() {

        Intent activityIntent = new Intent(MainActivity.this, ContactsListActivity.class);
        startActivity(activityIntent);
    }


    @OnClick(R.id.btn_bottom_sheet)
    void showContactsBottomSheet() {

        if (Utils.isReadContactsPermissionEnabled(MainActivity.this)) {

            View view = getLayoutInflater().inflate(R.layout.activity_contacts_list, null);

            recyclerViewContacts = (ShimmerRecyclerView) view.findViewById(R.id.recycler_view_contacts);

            mAdapter = new ContactsListAdapter(MainActivity.this, new ArrayList<ContactDTO>());
            recyclerViewContacts.setHasFixedSize(true);
            recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerViewContacts.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerViewContacts.setItemAnimator(new DefaultItemAnimator());
            recyclerViewContacts.setAdapter(mAdapter);


            if (Utils.isDismissDialog(dialog)) {
                return;
            }

            contactsPresenter = new ContactsPresenterImpl(MainActivity.this, this);
            contactsPresenter.loadContacts(MainActivity.this);

            dialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
            dialog.setContentView(view);
            dialog.show();

        } else {

            MainActivityPermissionsDispatcher.requestContactsPermissionWithCheck(MainActivity.this);
        }
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
        }, 2000);

    }

    @Override
    public void onContactsLoadFailure(String message) {

        Logger.i("loading failure");

        if (message.equals(getString(R.string.permisson_error))) {

            MainActivityPermissionsDispatcher.requestContactsPermissionWithCheck(MainActivity.this);

        }
    }

    @Override
    public void onContactsLoadSuccess(ArrayList<ContactDTO> contactDTOArrayList) {

        Logger.i((contactDTOArrayList != null ? contactDTOArrayList.size() : 0) + " Loading success!");

        mAdapter.updateContacts(contactDTOArrayList);

    }


    @NeedsPermission({Manifest.permission.READ_CONTACTS})
    void requestContactsPermission() {
        showContactsBottomSheet();
    }


    @OnPermissionDenied(Manifest.permission.READ_CONTACTS)
    void deniedContactsPermission() {
        finish();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
