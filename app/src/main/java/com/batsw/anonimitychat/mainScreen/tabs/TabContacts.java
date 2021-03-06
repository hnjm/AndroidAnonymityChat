package com.batsw.anonimitychat.mainScreen.tabs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.batsw.anonimitychat.R;
import com.batsw.anonimitychat.appManagement.AppController;
import com.batsw.anonimitychat.mainScreen.adapters.ContactsAdapter;
import com.batsw.anonimitychat.mainScreen.addContact.ContactAddActivity;
import com.batsw.anonimitychat.mainScreen.entities.ContactEntity;
import com.batsw.anonimitychat.persistence.entities.DBContactEntity;
import com.batsw.anonimitychat.persistence.util.IDbEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tudor on 3/29/2017.
 */

public class TabContacts extends Fragment {

    private static final String LOG = TabContacts.class.getSimpleName();

    private List<ContactEntity> mContactsList;

    private ContactsAdapter mContactsAdapter;
    private RecyclerView mContactsRecyclerView;

    private FloatingActionButton mFloatingAddButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(LOG, "onCreate -> ENTER");

        final List<IDbEntity> contactList = AppController.getInstanceParameterized(null).getContactList();
        mContactsList = new ArrayList<>();

        for (IDbEntity dbEntity : contactList) {
            DBContactEntity dbContactEntity = (DBContactEntity) dbEntity;

            ContactEntity contactEntity = new ContactEntity(dbContactEntity.getName(), dbContactEntity.getSessionId());
            mContactsList.add(contactEntity);
        }

        Log.i(LOG, "onCreate -> LEAVE");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(LOG, "onCreateView -> ENTER");
        View rootView = inflater.inflate(R.layout.contacts_tab, container, false);

        Log.i(LOG, "onCreateView -> LEAVE");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(LOG, "onViewCreated -> ENTER");
        super.onViewCreated(view, savedInstanceState);

        mContactsRecyclerView = (RecyclerView) view.findViewById(R.id.contacts_list);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppController.getInstanceParameterized(null).getCurrentActivityContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContactsRecyclerView.setLayoutManager(linearLayoutManager);

        mContactsAdapter = new ContactsAdapter(mContactsList, this);
        mContactsRecyclerView.setAdapter(mContactsAdapter);

        AppController.getInstanceParameterized(null).setContactsTab(this);

        mFloatingAddButton = (FloatingActionButton) view.findViewById(R.id.contacts_tab_floating_add_button);
        mFloatingAddButton.show();
        mFloatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG, "mFloatingAddButton.onClick -> ENTER");

                Intent addContactActivityIntent = ContactAddActivity.makeIntent(getActivity());
                addContactActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(addContactActivityIntent);

                Log.i(LOG, "mFloatingAddButton.onClick  -> LEAVE");
            }
        });

        Log.i(LOG, "onViewCreated -> LEAVE");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(LOG, "onActivityCreated -> ENTER");
        super.onActivityCreated(savedInstanceState);

//        mContactsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        mContactsAdapter = new ContactsAdapter(mContactsTestList, getActivity());
//        mContactsRecyclerView.setAdapter(mContactsAdapter);

        Log.i(LOG, "onActivityCreated -> LEAVE");

//        mContactsRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });

    }

    public void addContactToList(ContactEntity contactEntity) {
        Log.i(LOG, "addContactToList -> ENTER contactEntity=" + contactEntity);
        mContactsList.add(contactEntity);
        mContactsAdapter.notifyItemRangeChanged(mContactsList.size() / 2, 1);
//        mContactsAdapter.notifyDataSetChanged();
        Log.i(LOG, "addContactToList -> LEAVE");
    }

    public void removeContactFromList(ContactEntity contactEntity) {
        Log.i(LOG, "removeContactFromList -> ENTER contactEntity=" + contactEntity);

        for (ContactEntity ce : mContactsList) {
            if (ce.getSessionId() == contactEntity.getSessionId()) {

                int contactIndex = mContactsList.indexOf(ce);
                Log.i(LOG, "contactIndex = " + contactIndex);

                mContactsList.remove(ce);
                Log.i(LOG, "removed from contacts tab");

                mContactsAdapter.notifyItemRemoved(contactIndex);

                break;
            }
        }

//        mContactsAdapter.notifyItemRangeChanged(mContactsList.size(), 1);
//        mContactsAdapter.notifyDataSetChanged();
        Log.i(LOG, "removeContactFromList -> LEAVE");
    }

    public void updateContactList(ContactEntity contactEntity) {
        Log.i(LOG, "updateContactList -> ENTER contactEntity=" + contactEntity);

        int i = 0;
        for (ContactEntity ce : mContactsList) {
            if (ce.getSessionId() == contactEntity.getSessionId()) {
                ce.setName(contactEntity.getName());
            } else {
                i++;
            }
        }

        mContactsAdapter.notifyItemChanged(i);
        Log.i(LOG, "updateContactList -> LEAVE");
    }

    public FloatingActionButton getFloatingAddButton() {
        return mFloatingAddButton;
    }
}

