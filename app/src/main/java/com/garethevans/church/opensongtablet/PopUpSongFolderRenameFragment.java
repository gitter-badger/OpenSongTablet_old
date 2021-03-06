package com.garethevans.church.opensongtablet;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PopUpSongFolderRenameFragment extends DialogFragment {
    // This is a quick popup to enter a new song folder name, or rename a current one
    // Once it has been completed positively (i.e. ok was clicked) it sends a refreshAll() interface call

    static String myTask;
    static ArrayList<String> oldtempfolders;

    static PopUpSongFolderRenameFragment newInstance(String message) {
        myTask = message;
        PopUpSongFolderRenameFragment frag;
        frag = new PopUpSongFolderRenameFragment();
        return frag;
    }

    public interface MyInterface {
        void refreshAll();
    }

    private MyInterface mListener;

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        mListener = (MyInterface) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    Spinner oldFolderNameSpinner;
    EditText newFolderNameEditText;
    Button newFolderCancelButton;
    Button newFolderOkButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(getActivity().getResources().getString(R.string.options_song_rename));
        View V = inflater.inflate(R.layout.popup_songfoldercreate, container, false);

        // Initialise the views
        oldFolderNameSpinner = (Spinner) V.findViewById(R.id.oldFolderNameSpinner);
        newFolderNameEditText = (EditText) V.findViewById(R.id.newFolderNameEditText);
        newFolderCancelButton = (Button) V.findViewById(R.id.newFolderCancelButton);
        newFolderOkButton = (Button) V.findViewById(R.id.newFolderOkButton);

        // Set up the folderspinner
        // Set up the spinner
        // Populate the list view with the current song folders
        // Reset to the main songs folder, so we can list them
        FullscreenActivity.currentFolder = FullscreenActivity.whichSongFolder;
        FullscreenActivity.newFolder = FullscreenActivity.whichSongFolder;
        //FullscreenActivity.whichSongFolder = "";
        ListSongFiles.listSongFolders();

        // The song folder
        Log.d("d", "FullscreenActivity.currentFolder=" + FullscreenActivity.currentFolder);
        oldtempfolders = new ArrayList<>();
        for (int e=0;e<FullscreenActivity.mSongFolderNames.length;e++) {
            if (FullscreenActivity.mSongFolderNames[e]!=null &&
                    !FullscreenActivity.mSongFolderNames[e].equals(FullscreenActivity.mainfoldername)) {
                oldtempfolders.add(FullscreenActivity.mSongFolderNames[e]);
            }
        }
        ArrayAdapter<String> folders = new ArrayAdapter<>(getActivity(), R.layout.my_spinner, oldtempfolders);
        folders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oldFolderNameSpinner.setAdapter(folders);

        // Select the current folder as the preferred one
        oldFolderNameSpinner.setSelection(0);
        for (int w=0;w<oldtempfolders.size();w++) {
            if (FullscreenActivity.whichSongFolder.equals(oldtempfolders.get(w))) {
                oldFolderNameSpinner.setSelection(w);
                FullscreenActivity.currentFolder = oldtempfolders.get(w);
            }
        }

        if (myTask.equals("create")) {
            // Hide the spinner
            oldFolderNameSpinner.setVisibility(View.GONE);
        }

        // Set the oldFolderNameSpinnerListener
        oldFolderNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FullscreenActivity.currentFolder = oldtempfolders.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set the button listeners
        newFolderCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Just close this view
                dismiss();
            }
        });

        newFolderOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the variables
                String tempNewFolder = newFolderNameEditText.getText().toString().trim();
                File checkExists = new File(FullscreenActivity.dir + "/" + tempNewFolder);

                if (!tempNewFolder.equals("") && !tempNewFolder.isEmpty() && !tempNewFolder.contains("/") &&
                        !checkExists.exists() &&
                        !tempNewFolder.equals(FullscreenActivity.mainfoldername)) {
                    FullscreenActivity.newFolder = tempNewFolder;
                    String tempOldFolder = FullscreenActivity.currentFolder;

                    if (myTask.equals("rename")) {
                        // Try to rename
                        File from = new File(FullscreenActivity.dir + "/" + tempOldFolder);
                        File to = new File(FullscreenActivity.dir + "/" + tempNewFolder);
                        if (from.renameTo(to)) {
                            FullscreenActivity.myToastMessage = getResources().getString(R.string.renametitle) + " - " + getResources().getString(R.string.ok);
                        } else {
                            FullscreenActivity.myToastMessage = getResources().getString(R.string.renametitle) + " - " + getResources().getString(R.string.createfoldererror);
                        }

                        FullscreenActivity.whichSongFolder = tempNewFolder;
                        // Prepare the message

                    } else if (myTask.equals("create")) {
                        File newfoldertomake = new File(FullscreenActivity.dir + "/" + tempNewFolder);
                        if (newfoldertomake.mkdirs()) {
                            FullscreenActivity.myToastMessage = getResources().getString(R.string.newfolder) + " - " + getResources().getString(R.string.ok);
                        } else {
                            FullscreenActivity.myToastMessage = getResources().getString(R.string.newfolder) + " - " + getResources().getString(R.string.createfoldererror);
                        }

                    }

                    // Load the songs and the folders
                    ListSongFiles.listSongFolders();
                    ListSongFiles.listSongs();

                    try {
                        LoadXML.loadXML();
                    } catch (XmlPullParserException | IOException e) {
                        e.printStackTrace();
                    }

                    // Get the song indexes
                    ListSongFiles.getCurrentSongIndex();

                    // Save preferences
                    Preferences.savePreferences();

                    if (myTask.equals("create")) {
                        mListener.refreshAll();
                    }

                    dismiss();
               }
            }
        });
        return V;
    }
}
