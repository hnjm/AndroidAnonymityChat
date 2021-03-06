package com.batsw.anonimitychat.mainScreen.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.batsw.anonimitychat.R;
import com.batsw.anonimitychat.appManagement.AppController;
import com.batsw.anonimitychat.mainScreen.adapters.ChatsAdapter;
import com.batsw.anonimitychat.mainScreen.entities.ChatEntity;
import com.batsw.anonimitychat.persistence.entities.DBChatEntity;
import com.batsw.anonimitychat.persistence.util.IDbEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tudor on 3/29/2017.
 */

public class TabChats extends Fragment {
    private static final String LOG = TabChats.class.getSimpleName();

    private List<ChatEntity> mChatsList;

    private ChatsAdapter mChatsAdapter;
    private RecyclerView mChatsRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(LOG, "onCreate -> ENTER");

        final List<IDbEntity> chatList = AppController.getInstanceParameterized(null).getChatList();

        mChatsList = new ArrayList<>();
        for (IDbEntity dbEntity : chatList) {
            DBChatEntity chatEntity = (DBChatEntity) dbEntity;

            ChatEntity contactEntity = new ChatEntity(chatEntity.getSessionId(), chatEntity.getChatName(), false);
            mChatsList.add(contactEntity);
        }

        Log.i(LOG, "onCreate -> LEAVE");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(LOG, "onCreateView -> ENTER");
        View rootView = inflater.inflate(R.layout.chats_tab, container, false);

        Log.i(LOG, "onCreateView -> LEAVE");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(LOG, "onViewCreated -> ENTER");
        super.onViewCreated(view, savedInstanceState);

        mChatsRecyclerView = (RecyclerView) view.findViewById(R.id.chats_list);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mChatsRecyclerView.setLayoutManager(linearLayoutManager);

        mChatsAdapter = new ChatsAdapter(mChatsList, this);
        mChatsRecyclerView.setAdapter(mChatsAdapter);

        AppController.getInstanceParameterized(null).setChatsTab(this);

        Log.i(LOG, "onViewCreated -> LEAVE");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(LOG, "onActivityCreated -> ENTER");
        super.onActivityCreated(savedInstanceState);

        Log.i(LOG, "onActivityCreated -> LEAVE");
    }

    public void addChatToList(ChatEntity chatEntity) {
        Log.i(LOG, "addContactToList -> ENTER chatEntity=" + chatEntity);
        mChatsList.add(chatEntity);
        mChatsAdapter.notifyItemRangeChanged(mChatsList.size() / 2, 1);
//        mChatsAdapter.notifyDataSetChanged();
        Log.i(LOG, "addContactToList -> LEAVE");
    }

    public void updateContactList(ChatEntity chatEntity) {
        Log.i(LOG, "updateContactList -> ENTER chatEntity=" + chatEntity);

        int i = 0;
        for (ChatEntity ce : mChatsList) {
            if (ce.getSessionId() == chatEntity.getSessionId()) {
                ce.setContactName(chatEntity.getContactName());
            } else {
                i++;
            }
        }

        mChatsAdapter.notifyItemChanged(i);
        Log.i(LOG, "updateContactList -> LEAVE");
    }
}
