package com.batsw.anonimitychat.mainScreen.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.batsw.anonimitychat.R;
import com.batsw.anonimitychat.mainScreen.contactEditor.ContactEditorActivity;
import com.batsw.anonimitychat.mainScreen.entities.ContactEntity;
import com.batsw.anonimitychat.mainScreen.tabs.TabContacts;

import java.util.List;

/**
 * Created by tudor on 4/7/2017.
 */

public class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String LOG = ContactHolder.class.getSimpleName();

    private TabContacts mTabContActivity;
    private ContactEntity mContactEntity;
    private ImageView mEditImageView;
    private TextView mNameTextView;
    private List<ContactEntity> mContactEntitiesList;

    public ContactHolder(View itemView, List<ContactEntity> contactsList, TabContacts tcActivity) {
        super(itemView);

        mTabContActivity= tcActivity;

        mContactEntitiesList = contactsList;

        mEditImageView = (ImageView) itemView.findViewById(R.id.current_user_edit);
        mNameTextView = (TextView) itemView.findViewById(R.id.contact_name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG, "OnClickListener.onClick -> ENTER");
                int position = getLayoutPosition();
                //TODO: go to user's ChatList
                ContactEntity contactEntity = mContactEntitiesList.get(position);
                //From hete go to ChatList

                Log.i(LOG, "OnClickListener.onClick -> LEAVE contactEntity.name=" + contactEntity.getName());
            }

        });

        mEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: go to edit activity from here
                // bound to ContactEntity
                Intent editContactActivityIntent = ContactEditorActivity.makeIntent(mTabContActivity.getActivity());

//                editContactActivityIntent.putExtra(,);

                editContactActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mTabContActivity.getActivity().startActivity(editContactActivityIntent);

            }
        });
    }

    /**
     * Todo: This is not working as expected .... the above onClickListener is useful ....
     */
    @Override
    public void onClick(View view) {
        Log.i(LOG, "OnClickListener.onClick -> ENTER view=" + view);

        Log.i(LOG, "DO NOTHING yet !");

        Log.i(LOG, "OnClickListener.onClick -> LEAVE");
    }

    public void bindData(ContactEntity ce) {
        mContactEntity = ce;
        //TODO: to add custom user image
//        mImageView.setImageResource(getR.id.ic_earth_white);
        mNameTextView.setText(ce.getName());
    }

    public TextView getNameTextView() {
        return mNameTextView;
    }
}

