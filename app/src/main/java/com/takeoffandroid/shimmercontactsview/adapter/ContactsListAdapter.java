package com.takeoffandroid.shimmercontactsview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.takeoffandroid.shimmercontactsview.R;
import com.takeoffandroid.shimmercontactsview.contacts.ContactDTO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.MyViewHolder> {



    private ArrayList<ContactDTO> mContactsList;
//    private static OnItemClickListener mItemClickListener;

    private Context mContext;


    public void updateContacts(ArrayList<ContactDTO> contactDTOArrayList) {
        this.mContactsList = contactDTOArrayList;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_contact_name)
        TextView txtContactName;

        @BindView(R.id.txt_contact_mobile)
        TextView txtContactMobile;

        @BindView(R.id.img_user)
        ImageView imgUser;

        public MyViewHolder(View view) {
            super(view);


            ButterKnife.bind(this, view);

            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

//            mItemClickListener.onItemClick(v, getAdapterPosition(), mContactsList.get(getAdapterPosition()));

        }


    }


    public ContactsListAdapter(Context context, ArrayList<ContactDTO> contactDTOArrayList) {
        this.mContactsList = contactDTOArrayList;
        this.mContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_contacts, parent, false);


        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ContactDTO contactDTO = mContactsList.get(position);
        holder.txtContactName.setText(contactDTO.getName());
        holder.txtContactMobile.setText(contactDTO.getNumber());

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT

        // declare the builder object once.
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .endConfig()
                .round();

        // reuse the builder specs to create multiple drawables
        TextDrawable textDrawable = builder.build(contactDTO.getName().substring(0,1), generator.getRandomColor());

        holder.imgUser.setImageDrawable(textDrawable);

    }

    @Override
    public int getItemCount() {
        return mContactsList.size();
    }


//    public interface OnItemClickListener {
//        void onItemClick(View view, int position, ContactDTO contactDTO);
//    }
//
//    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
//        this.mItemClickListener = mItemClickListener;
//    }


}