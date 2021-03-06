package com.garethevans.church.opensongtablet;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopUpEditSongFragment extends DialogFragment implements PopUpSongFolderCreateFragment.MyInterface,
    PopUpPresentationOrderFragment.MyInterface {

    static PopUpEditSongFragment newInstance() {
        PopUpEditSongFragment frag;
        frag = new PopUpEditSongFragment();
        return frag;
    }

    // The General views available
    EditText edit_song_title;
    EditText edit_song_author;
    EditText edit_song_copyright;
    Spinner edit_song_key;
    EditText edit_song_duration;
    SeekBar predelay_SeekBar;
    TextView predelay_TextView;
    SeekBar edit_song_tempo;
    TextView tempo_text;
    Spinner edit_song_timesig;
    Spinner edit_song_capo;
    Spinner edit_song_capo_print;
    EditText edit_song_presentation;
    EditText edit_song_notes;
    EditText edit_song_lyrics;

    // Advanced
    EditText edit_song_CCLI;
    EditText edit_song_aka;
    EditText edit_song_key_line;
    EditText edit_song_hymn;
    EditText edit_song_user1;
    EditText edit_song_user2;
    EditText edit_song_user3;
    Spinner edit_song_pad_file;
    EditText edit_song_midi;
    EditText edit_song_midi_index;
    EditText edit_song_restrictions;
    EditText edit_song_books;
    EditText edit_song_pitch;
    CheckBox edit_song_theme_christ_attributes;
    CheckBox edit_song_theme_christ_birth;
    CheckBox edit_song_theme_christ_death_atonement;
    CheckBox edit_song_theme_christ_power_majesty;
    CheckBox edit_song_theme_christ_love_mercy;
    CheckBox edit_song_theme_christ_resurrection;
    CheckBox edit_song_theme_christ_second_coming;
    CheckBox edit_song_theme_christ_victory;
    CheckBox edit_song_theme_church_commitment_obedience;
    CheckBox edit_song_theme_church_country;
    CheckBox edit_song_theme_church_eternal_life_heaven;
    CheckBox edit_song_theme_church_evangelism;
    CheckBox edit_song_theme_church_family_fellowship;
    CheckBox edit_song_theme_church_fellowship_w_god;
    CheckBox edit_song_theme_church_purity_holiness;
    CheckBox edit_song_theme_church_renewal;
    CheckBox edit_song_theme_church_repentance_salvation;
    CheckBox edit_song_theme_church_service_ministry;
    CheckBox edit_song_theme_church_spiritual_hunger;
    CheckBox edit_song_theme_fruit_faith_hope;
    CheckBox edit_song_theme_fruit_humility_meekness;
    CheckBox edit_song_theme_fruit_joy;
    CheckBox edit_song_theme_fruit_love;
    CheckBox edit_song_theme_fruit_patience_kindness;
    CheckBox edit_song_theme_fruit_peace_comfort;
    CheckBox edit_song_theme_god_attributes;
    CheckBox edit_song_theme_god_creator_creation;
    CheckBox edit_song_theme_god_father;
    CheckBox edit_song_theme_god_guidance_care;
    CheckBox edit_song_theme_god_holiness;
    CheckBox edit_song_theme_god_holy_spirit;
    CheckBox edit_song_theme_god_love_mercy;
    CheckBox edit_song_theme_god_power_majesty;
    CheckBox edit_song_theme_god_promises;
    CheckBox edit_song_theme_god_victory;
    CheckBox edit_song_theme_god_word;
    CheckBox edit_song_theme_worship_assurance_trust;
    CheckBox edit_song_theme_worship_call_opening;
    CheckBox edit_song_theme_worship_celebration;
    CheckBox edit_song_theme_worship_declaration;
    CheckBox edit_song_theme_worship_intimacy;
    CheckBox edit_song_theme_worship_invitation;
    CheckBox edit_song_theme_worship_praise_adoration;
    CheckBox edit_song_theme_worship_prayer_devotion;
    CheckBox edit_song_theme_worship_provision_deliverance;
    CheckBox edit_song_theme_worship_thankfulness;
    static LinearLayout generalSettings;
    static LinearLayout advancedSettings;

    // Buttons
    static Button cancelEdit;
    static Button saveEdit;
    static Button toggleGeneralAdvanced;

    static int temposlider;
    static View V;

    @Override
    public void refreshAll() {
        // Called when the user creates a new folder
        // Refresh the folder spinner and select the new one by default
    }

    @Override
    public void updatePresentationOrder() {
        edit_song_presentation.setText(FullscreenActivity.mPresentation);
    }

    public interface MyInterface {
        void refreshAll();
    }

    private MyInterface mListener;

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (MyInterface) activity;
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(getActivity().getResources().getString(R.string.options_song_edit));
        V = inflater.inflate(R.layout.popup_editsong, container, false);

        // Initialise the basic views
        edit_song_title = (EditText) V.findViewById(R.id.edit_song_title);
        edit_song_author = (EditText) V.findViewById(R.id.edit_song_author);
        edit_song_copyright = (EditText) V.findViewById(R.id.edit_song_copyright);
        edit_song_key = (Spinner) V.findViewById(R.id.edit_song_key);
        edit_song_duration = (EditText) V.findViewById(R.id.edit_song_duration);
        predelay_SeekBar = (SeekBar) V.findViewById(R.id.predelay_SeekBar);
        predelay_TextView = (TextView) V.findViewById(R.id.predelay_TextView);
        edit_song_tempo = (SeekBar) V.findViewById(R.id.edit_song_tempo);
        tempo_text = (TextView) V.findViewById(R.id.tempo_text);
        edit_song_timesig = (Spinner) V.findViewById(R.id.edit_song_timesig);
        edit_song_capo = (Spinner) V.findViewById(R.id.edit_song_capo);
        edit_song_capo_print = (Spinner) V.findViewById(R.id.edit_song_capo_print);
        edit_song_presentation = (EditText) V.findViewById(R.id.edit_song_presentation);
        edit_song_presentation.setFocusable(false);
        edit_song_presentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = PopUpPresentationOrderFragment.newInstance();
                newFragment.show(getFragmentManager(), "dialog");
                dismiss();
            }
        });
        edit_song_notes = (EditText) V.findViewById(R.id.edit_song_notes);
        edit_song_lyrics = (EditText) V.findViewById(R.id.edit_song_lyrics);
        edit_song_lyrics.setHorizontallyScrolling(true);
        cancelEdit = (Button) V.findViewById(R.id.cancel_edit);
        saveEdit = (Button) V.findViewById(R.id.save_edit);
        toggleGeneralAdvanced = (Button) V.findViewById(R.id.show_general_advanced);
        generalSettings = (LinearLayout) V.findViewById(R.id.general_settings);

        // Initialise the advanced views
        edit_song_CCLI = (EditText) V.findViewById(R.id.edit_song_ccli);
        edit_song_aka = (EditText) V.findViewById(R.id.edit_song_aka);
        edit_song_key_line = (EditText) V.findViewById(R.id.edit_song_keyline);
        edit_song_hymn = (EditText) V.findViewById(R.id.edit_song_hymn);
        edit_song_user1 = (EditText) V.findViewById(R.id.edit_song_user1);
        edit_song_user2 = (EditText) V.findViewById(R.id.edit_song_user2);
        edit_song_user3 = (EditText) V.findViewById(R.id.edit_song_user3);
        edit_song_pad_file = (Spinner) V.findViewById(R.id.edit_pad_file);
        edit_song_midi = (EditText) V.findViewById(R.id.edit_song_midi);
        edit_song_midi_index = (EditText) V.findViewById(R.id.edit_song_midi_index);
        edit_song_restrictions = (EditText) V.findViewById(R.id.edit_song_restrictions);
        edit_song_books = (EditText) V.findViewById(R.id.edit_song_books);
        edit_song_pitch = (EditText) V.findViewById(R.id.edit_song_pitch);
        edit_song_theme_christ_attributes = (CheckBox) V.findViewById(R.id.edit_song_theme_christ_attributes);
        edit_song_theme_christ_birth = (CheckBox) V.findViewById(R.id.edit_song_theme_christ_birth);
        edit_song_theme_christ_death_atonement = (CheckBox) V.findViewById(R.id.edit_song_theme_christ_death_atonement);
        edit_song_theme_christ_power_majesty = (CheckBox) V.findViewById(R.id.edit_song_theme_christ_power_majesty);
        edit_song_theme_christ_love_mercy = (CheckBox) V.findViewById(R.id.edit_song_theme_christ_love_mercy);
        edit_song_theme_christ_resurrection = (CheckBox) V.findViewById(R.id.edit_song_theme_christ_resurrection);
        edit_song_theme_christ_second_coming = (CheckBox) V.findViewById(R.id.edit_song_theme_christ_second_coming);
        edit_song_theme_christ_victory = (CheckBox) V.findViewById(R.id.edit_song_theme_christ_victory);
        edit_song_theme_church_commitment_obedience = (CheckBox) V.findViewById(R.id.edit_song_theme_church_commitment_obedience);
        edit_song_theme_church_country = (CheckBox) V.findViewById(R.id.edit_song_theme_church_country);
        edit_song_theme_church_eternal_life_heaven = (CheckBox) V.findViewById(R.id.edit_song_theme_church_eternal_life_heaven);
        edit_song_theme_church_evangelism = (CheckBox) V.findViewById(R.id.edit_song_theme_church_evangelism);
        edit_song_theme_church_family_fellowship = (CheckBox) V.findViewById(R.id.edit_song_theme_church_family_fellowship);
        edit_song_theme_church_fellowship_w_god = (CheckBox) V.findViewById(R.id.edit_song_theme_church_fellowship_w_god);
        edit_song_theme_church_purity_holiness = (CheckBox) V.findViewById(R.id.edit_song_theme_church_purity_holiness);
        edit_song_theme_church_renewal = (CheckBox) V.findViewById(R.id.edit_song_theme_church_renewal);
        edit_song_theme_church_repentance_salvation = (CheckBox) V.findViewById(R.id.edit_song_theme_church_repentance_salvation);
        edit_song_theme_church_service_ministry = (CheckBox) V.findViewById(R.id.edit_song_theme_church_service_ministry);
        edit_song_theme_church_spiritual_hunger = (CheckBox) V.findViewById(R.id.edit_song_theme_church_spiritual_hunger);
        edit_song_theme_fruit_faith_hope = (CheckBox) V.findViewById(R.id.edit_song_theme_fruit_faith_hope);
        edit_song_theme_fruit_humility_meekness = (CheckBox) V.findViewById(R.id.edit_song_theme_fruit_humility_meekness);
        edit_song_theme_fruit_joy = (CheckBox) V.findViewById(R.id.edit_song_theme_fruit_joy);
        edit_song_theme_fruit_love = (CheckBox) V.findViewById(R.id.edit_song_theme_fruit_love);
        edit_song_theme_fruit_patience_kindness = (CheckBox) V.findViewById(R.id.edit_song_theme_fruit_patience_kindness);
        edit_song_theme_fruit_peace_comfort = (CheckBox) V.findViewById(R.id.edit_song_theme_fruit_peace_comfort);
        edit_song_theme_god_attributes = (CheckBox) V.findViewById(R.id.edit_song_theme_god_attributes);
        edit_song_theme_god_creator_creation = (CheckBox) V.findViewById(R.id.edit_song_theme_god_creator_creation);
        edit_song_theme_god_father = (CheckBox) V.findViewById(R.id.edit_song_theme_god_father);
        edit_song_theme_god_guidance_care = (CheckBox) V.findViewById(R.id.edit_song_theme_god_guidance_care);
        edit_song_theme_god_holiness = (CheckBox) V.findViewById(R.id.edit_song_theme_god_holiness);
        edit_song_theme_god_holy_spirit = (CheckBox) V.findViewById(R.id.edit_song_theme_god_holy_spirit);
        edit_song_theme_god_love_mercy = (CheckBox) V.findViewById(R.id.edit_song_theme_god_love_mercy);
        edit_song_theme_god_power_majesty = (CheckBox) V.findViewById(R.id.edit_song_theme_god_power_majesty);
        edit_song_theme_god_promises = (CheckBox) V.findViewById(R.id.edit_song_theme_god_promises);
        edit_song_theme_god_victory = (CheckBox) V.findViewById(R.id.edit_song_theme_god_victory);
        edit_song_theme_god_word = (CheckBox) V.findViewById(R.id.edit_song_theme_god_word);
        edit_song_theme_worship_assurance_trust = (CheckBox) V.findViewById(R.id.edit_song_theme_worship_assurance_trust);
        edit_song_theme_worship_call_opening = (CheckBox) V.findViewById(R.id.edit_song_theme_worship_call_opening);
        edit_song_theme_worship_celebration = (CheckBox) V.findViewById(R.id.edit_song_theme_worship_celebration);
        edit_song_theme_worship_declaration = (CheckBox) V.findViewById(R.id.edit_song_theme_worship_declaration);
        edit_song_theme_worship_intimacy = (CheckBox) V.findViewById(R.id.edit_song_theme_worship_intimacy);
        edit_song_theme_worship_invitation = (CheckBox) V.findViewById(R.id.edit_song_theme_worship_invitation);
        edit_song_theme_worship_praise_adoration = (CheckBox) V.findViewById(R.id.edit_song_theme_worship_praise_adoration);
        edit_song_theme_worship_prayer_devotion = (CheckBox) V.findViewById(R.id.edit_song_theme_worship_prayer_devotion);
        edit_song_theme_worship_provision_deliverance = (CheckBox) V.findViewById(R.id.edit_song_theme_worship_provision_deliverance);
        edit_song_theme_worship_thankfulness = (CheckBox) V.findViewById(R.id.edit_song_theme_worship_thankfulness);
        advancedSettings = (LinearLayout) V.findViewById(R.id.advanced_settings);

        edit_song_title.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        // Listeners for the buttons
        cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load the song back up with the default values
                try {
                    LoadXML.loadXML();
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });

        toggleGeneralAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if (generalSettings.getVisibility() == View.VISIBLE) {
                    // Slide the general settings left
                    generalSettings.startAnimation(AnimationUtils.loadAnimation(
                            V.getContext(), R.anim.slide_out_left));
                    // Wait 300ms before hiding the general settings and unhiding the advanced settings
                    Handler delayfadeinredraw = new Handler();
                    delayfadeinredraw.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            generalSettings.setVisibility(View.GONE);
                        }
                    }, 300); // 300ms

                    // Wait 300ms before sliding in the advanced settings
                    Handler delayfadeinredraw2 = new Handler();
                    delayfadeinredraw2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            advancedSettings.startAnimation(AnimationUtils
                                    .loadAnimation(V.getContext(),
                                            R.anim.slide_in_right));
                            advancedSettings.setVisibility(View.VISIBLE);
                        }
                    }, 600); // 300ms

                } else {
                    // Slide the advanced settings right
                    advancedSettings.startAnimation(AnimationUtils.loadAnimation(
                            V.getContext(), R.anim.slide_out_right));
                    // Wait 300ms before hiding the advanced settings and unhiding the general settings
                    Handler delayfadeinredraw = new Handler();
                    delayfadeinredraw.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            advancedSettings.setVisibility(View.GONE);
                        }
                    }, 300); // 300ms

                    // Wait 300ms before sliding in the general settings
                    Handler delayfadeinredraw2 = new Handler();
                    delayfadeinredraw2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            generalSettings.startAnimation(AnimationUtils
                                    .loadAnimation(V.getContext(),
                                            R.anim.slide_in_left));
                            generalSettings.setVisibility(View.VISIBLE);
                        }
                    }, 600); // 300ms
                }
            }
        });

        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Go through the fields and save them
                // Get the variables
                // Set the newtext to the FullscreenActivity variables
                FullscreenActivity.mTitle = edit_song_title.getText().toString();
                FullscreenActivity.mAuthor = edit_song_author.getText().toString();
                FullscreenActivity.mCopyright = edit_song_copyright.getText().toString();
                FullscreenActivity.mLyrics = edit_song_lyrics.getText().toString();
                FullscreenActivity.mPresentation = edit_song_presentation.getText().toString();
                FullscreenActivity.mHymnNumber = edit_song_hymn.getText().toString();
                FullscreenActivity.mCCLI = edit_song_CCLI.getText().toString();
                FullscreenActivity.mUser1 = edit_song_user1.getText().toString();
                FullscreenActivity.mUser2 = edit_song_user2.getText().toString();
                FullscreenActivity.mUser3 = edit_song_user3.getText().toString();
                FullscreenActivity.mAka = edit_song_aka.getText().toString();
                FullscreenActivity.mKeyLine = edit_song_key_line.getText().toString();
                FullscreenActivity.mKey = edit_song_key.getItemAtPosition(edit_song_key.getSelectedItemPosition()).toString();
                FullscreenActivity.mDuration = edit_song_duration.getText().toString();
                int predelayval = predelay_SeekBar.getProgress();
                if (predelayval==0) {
                    FullscreenActivity.mPreDelay = "";
                } else {
                    FullscreenActivity.mPreDelay = ""+(predelayval-1);
                }
                FullscreenActivity.mBooks = edit_song_books.getText().toString();
                FullscreenActivity.mMidi = edit_song_midi.getText().toString();
                FullscreenActivity.mMidiIndex = edit_song_midi_index.getText().toString();
                FullscreenActivity.mPitch = edit_song_pitch.getText().toString();
                FullscreenActivity.mRestrictions = edit_song_restrictions.getText().toString();
                FullscreenActivity.mNotes = edit_song_notes.getText().toString();
                FullscreenActivity.mPadFile = edit_song_pad_file.getItemAtPosition(edit_song_pad_file.getSelectedItemPosition()).toString();

                FullscreenActivity.mCapo = edit_song_capo.getItemAtPosition(edit_song_capo.getSelectedItemPosition()).toString();
                int tempmCapoPrint = edit_song_capo_print.getSelectedItemPosition();
                if (tempmCapoPrint==1) {
                    FullscreenActivity.mCapoPrint="true";
                } else if (tempmCapoPrint==2) {
                    FullscreenActivity.mCapoPrint="false";
                } else {
                    FullscreenActivity.mCapoPrint="";
                }
                int valoftempobar = edit_song_tempo.getProgress() + 39;
                if (valoftempobar>39) {
                    FullscreenActivity.mTempo = ""+valoftempobar;
                } else {
                    FullscreenActivity.mTempo = "";
                }
                FullscreenActivity.mTimeSig = edit_song_timesig.getItemAtPosition(edit_song_timesig.getSelectedItemPosition()).toString();

                FullscreenActivity.mTheme = "";

                if (edit_song_theme_christ_attributes.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_christ_attributes.getText().toString() + "; ";
                }
                if (edit_song_theme_christ_birth.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_christ_birth.getText().toString() + "; ";
                }
                if (edit_song_theme_christ_death_atonement.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_christ_death_atonement.getText().toString() + "; ";
                }
                if (edit_song_theme_christ_love_mercy.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_christ_love_mercy.getText().toString() + "; ";
                }
                if (edit_song_theme_christ_power_majesty.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_christ_power_majesty.getText().toString() + "; ";
                }
                if (edit_song_theme_christ_resurrection.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_christ_resurrection.getText().toString() + "; ";
                }
                if (edit_song_theme_christ_second_coming.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_christ_second_coming.getText().toString() + "; ";
                }
                if (edit_song_theme_christ_victory.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_christ_victory.getText().toString() + "; ";
                }

                if (edit_song_theme_church_commitment_obedience.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_church_commitment_obedience.getText().toString() + "; ";
                }
                if (edit_song_theme_church_country.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_church_country.getText().toString() + "; ";
                }
                if (edit_song_theme_church_eternal_life_heaven.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_church_eternal_life_heaven.getText().toString() + "; ";
                }
                if (edit_song_theme_church_evangelism.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_church_evangelism.getText().toString() + "; ";
                }
                if (edit_song_theme_church_family_fellowship.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_church_family_fellowship.getText().toString() + "; ";
                }
                if (edit_song_theme_church_fellowship_w_god.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_church_fellowship_w_god.getText().toString() + "; ";
                }
                if (edit_song_theme_church_purity_holiness.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_church_purity_holiness.getText().toString() + "; ";
                }
                if (edit_song_theme_church_renewal.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_church_renewal.getText().toString() + "; ";
                }
                if (edit_song_theme_church_repentance_salvation.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_church_repentance_salvation.getText().toString() + "; ";
                }
                if (edit_song_theme_church_service_ministry.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_church_service_ministry.getText().toString() + "; ";
                }
                if (edit_song_theme_church_spiritual_hunger.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_church_spiritual_hunger.getText().toString() + "; ";
                }

                if (edit_song_theme_fruit_faith_hope.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_fruit_faith_hope.getText().toString() + "; ";
                }
                if (edit_song_theme_fruit_humility_meekness.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_fruit_humility_meekness.getText().toString() + "; ";
                }
                if (edit_song_theme_fruit_joy.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_fruit_joy.getText().toString() + "; ";
                }
                if (edit_song_theme_fruit_love.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_fruit_love.getText().toString() + "; ";
                }
                if (edit_song_theme_fruit_patience_kindness.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_fruit_patience_kindness.getText().toString() + "; ";
                }
                if (edit_song_theme_fruit_peace_comfort.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_fruit_peace_comfort.getText().toString() + "; ";
                }

                if (edit_song_theme_god_attributes.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_god_attributes.getText().toString() + "; ";
                }
                if (edit_song_theme_god_creator_creation.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_god_creator_creation.getText().toString() + "; ";
                }
                if (edit_song_theme_god_father.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_god_father.getText().toString() + "; ";
                }
                if (edit_song_theme_god_guidance_care.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_god_guidance_care.getText().toString() + "; ";
                }
                if (edit_song_theme_god_holiness.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_god_holiness.getText().toString() + "; ";
                }
                if (edit_song_theme_god_holy_spirit.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_god_holy_spirit.getText().toString() + "; ";
                }
                if (edit_song_theme_god_love_mercy.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_god_love_mercy.getText().toString() + "; ";
                }
                if (edit_song_theme_god_power_majesty.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_god_power_majesty.getText().toString() + "; ";
                }
                if (edit_song_theme_god_promises.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_god_promises.getText().toString() + "; ";
                }
                if (edit_song_theme_god_victory.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_god_victory.getText().toString() + "; ";
                }
                if (edit_song_theme_god_word.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_god_word.getText().toString() + "; ";
                }

                if (edit_song_theme_worship_assurance_trust.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_worship_assurance_trust.getText().toString() + "; ";
                }
                if (edit_song_theme_worship_call_opening.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_worship_call_opening.getText().toString() + "; ";
                }
                if (edit_song_theme_worship_celebration.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_worship_celebration.getText().toString() + "; ";
                }
                if (edit_song_theme_worship_declaration.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_worship_declaration.getText().toString() + "; ";
                }
                if (edit_song_theme_worship_intimacy.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_worship_intimacy.getText().toString() + "; ";
                }
                if (edit_song_theme_worship_invitation.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_worship_invitation.getText().toString() + "; ";
                }
                if (edit_song_theme_worship_praise_adoration.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_worship_praise_adoration.getText().toString() + "; ";
                }
                if (edit_song_theme_worship_prayer_devotion.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_worship_prayer_devotion.getText().toString() + "; ";
                }
                if (edit_song_theme_worship_provision_deliverance.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_worship_provision_deliverance.getText().toString() + "; ";
                }
                if (edit_song_theme_worship_thankfulness.isChecked()) {
                    FullscreenActivity.mTheme += edit_song_theme_worship_thankfulness.getText().toString() + "; ";
                }

                // Set the AltTheme to the same as the Theme?
                FullscreenActivity.mAltTheme = FullscreenActivity.mTheme;

                // Prepare the new XML file
                prepareSongXML();

                // Makes sure all & are replaced with &amp;
                FullscreenActivity.mynewXML = FullscreenActivity.mynewXML.replace("&amp;","&");
                FullscreenActivity.mynewXML = FullscreenActivity.mynewXML.replace("&","&amp;");

                // Now write the modified song
                try {
                    FileOutputStream overWrite;
                    if (FullscreenActivity.whichSongFolder.equals(FullscreenActivity.mainfoldername)) {
                        overWrite = new FileOutputStream(
                                FullscreenActivity.dir + "/" + FullscreenActivity.songfilename,
                                false);
                    } else {
                        overWrite = new FileOutputStream(
                                FullscreenActivity.dir + "/" + FullscreenActivity.whichSongFolder + "/" + FullscreenActivity.songfilename,
                                false);
                    }
                    overWrite.write(FullscreenActivity.mynewXML.getBytes());
                    overWrite.flush();
                    overWrite.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FullscreenActivity.mynewXML = "";

                // Save the preferences
                Preferences.savePreferences();

                // Prepare the message
                FullscreenActivity.myToastMessage = getResources().getString(R.string.edit_save) + " - " +
                        getResources().getString(R.string.ok);

                // Now tell the main page to refresh itself with this new song
                // Don't need to reload the XML as we already have all its values
                mListener.refreshAll();

                // Now dismiss this popup
                dismiss();
            }
        });

        // Fill in the current values
        // Start with the simple EditTexts
        edit_song_title.setText(FullscreenActivity.mTitle);
        edit_song_author.setText(FullscreenActivity.mAuthor);
        edit_song_copyright.setText(FullscreenActivity.mCopyright);
        edit_song_presentation.setText(FullscreenActivity.mPresentation);
        edit_song_duration.setText(FullscreenActivity.mDuration);
        if (FullscreenActivity.mPreDelay.isEmpty()) {
            predelay_SeekBar.setProgress(0);
            predelay_TextView.setText("");
        } else {
            int val=Integer.parseInt(FullscreenActivity.mPreDelay.replaceAll("[\\D]",""));
            if (val<0) {
                val=0;
            }
            String text = val + " s";
            predelay_SeekBar.setProgress(val+1);
            predelay_TextView.setText(text);
        }
        edit_song_notes.setText(FullscreenActivity.mNotes);
        edit_song_duration.setText(FullscreenActivity.mDuration);
        edit_song_lyrics.setTypeface(Typeface.MONOSPACE);
        // Get the lyrics into a temp string (so we can get rid of rubbish tabs, etc)
        String editBoxLyrics = FullscreenActivity.mLyrics;
        editBoxLyrics = editBoxLyrics.replaceAll("\r\n", "\n");
        editBoxLyrics = editBoxLyrics.replaceAll("\n\r", "\n");
        editBoxLyrics = editBoxLyrics.replaceAll("\t", "    ");
        editBoxLyrics = editBoxLyrics.replaceAll("\b", "    ");
        editBoxLyrics = editBoxLyrics.replaceAll("\f", "    ");
        edit_song_lyrics.setText(editBoxLyrics);
        edit_song_CCLI.setText(FullscreenActivity.mCCLI);
        edit_song_aka.setText(FullscreenActivity.mAka);
        edit_song_key_line.setText(FullscreenActivity.mKeyLine);
        edit_song_hymn.setText(FullscreenActivity.mHymnNumber);
        edit_song_user1.setText(FullscreenActivity.mUser1);
        edit_song_user2.setText(FullscreenActivity.mUser2);
        edit_song_user3.setText(FullscreenActivity.mUser3);
        edit_song_midi.setText(FullscreenActivity.mMidi);
        edit_song_midi_index.setText(FullscreenActivity.mMidiIndex);
        edit_song_restrictions.setText(FullscreenActivity.mRestrictions);
        edit_song_books.setText(FullscreenActivity.mBooks);
        edit_song_pitch.setText(FullscreenActivity.mPitch);

        // Now the checkboxes
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_christ_attributes))) {
            edit_song_theme_christ_attributes.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_christ_birth))) {
            edit_song_theme_christ_birth.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_christ_death_atonement))) {
            edit_song_theme_christ_death_atonement.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_christ_love_mercy))) {
            edit_song_theme_christ_love_mercy.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_christ_power_majesty))) {
            edit_song_theme_christ_power_majesty.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_christ_resurrection))) {
            edit_song_theme_christ_resurrection.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_christ_second_coming))) {
            edit_song_theme_christ_second_coming.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_christ_victory))) {
            edit_song_theme_christ_victory.setChecked(true);
        }

        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_church_commitment_obedience))) {
            edit_song_theme_church_commitment_obedience.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_church_country))) {
            edit_song_theme_church_country.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_church_eternal_life_heaven))) {
            edit_song_theme_church_eternal_life_heaven.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_church_evangelism))) {
            edit_song_theme_church_evangelism.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_church_family_fellowship))) {
            edit_song_theme_church_family_fellowship.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_church_fellowship_w_god))) {
            edit_song_theme_church_fellowship_w_god.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_church_purity_holiness))) {
            edit_song_theme_church_purity_holiness.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_church_renewal))) {
            edit_song_theme_church_renewal.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_church_repentance_salvation))) {
            edit_song_theme_church_repentance_salvation.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_church_service_ministry))) {
            edit_song_theme_church_service_ministry.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_church_spiritual_hunger))) {
            edit_song_theme_church_spiritual_hunger.setChecked(true);
        }

        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_fruit_faith_hope))) {
            edit_song_theme_fruit_faith_hope.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_fruit_humility_meekness))) {
            edit_song_theme_fruit_humility_meekness.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_fruit_joy))) {
            edit_song_theme_fruit_joy.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_fruit_love))) {
            edit_song_theme_fruit_love.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_fruit_patience_kindness))) {
            edit_song_theme_fruit_patience_kindness.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_fruit_peace_comfort))) {
            edit_song_theme_fruit_peace_comfort.setChecked(true);
        }

        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_god_attributes))) {
            edit_song_theme_god_attributes.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_god_creator_creation))) {
            edit_song_theme_god_creator_creation.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_god_father))) {
            edit_song_theme_god_father.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_god_guidance_care))) {
            edit_song_theme_god_guidance_care.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_god_holiness))) {
            edit_song_theme_god_holiness.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_god_holy_spirit))) {
            edit_song_theme_god_holy_spirit.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_god_love_mercy))) {
            edit_song_theme_god_love_mercy.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_god_power_majesty))) {
            edit_song_theme_god_power_majesty.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_god_promises))) {
            edit_song_theme_god_promises.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_god_victory))) {
            edit_song_theme_god_victory.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_god_word))) {
            edit_song_theme_god_word.setChecked(true);
        }

        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_worship_assurance_trust))) {
            edit_song_theme_worship_assurance_trust.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_worship_call_opening))) {
            edit_song_theme_worship_call_opening.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_worship_celebration))) {
            edit_song_theme_worship_celebration.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_worship_declaration))) {
            edit_song_theme_worship_declaration.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_worship_intimacy))) {
            edit_song_theme_worship_intimacy.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_worship_invitation))) {
            edit_song_theme_worship_invitation.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_worship_praise_adoration))) {
            edit_song_theme_worship_praise_adoration.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_worship_prayer_devotion))) {
            edit_song_theme_worship_prayer_devotion.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_worship_provision_deliverance))) {
            edit_song_theme_worship_provision_deliverance.setChecked(true);
        }
        if (FullscreenActivity.mTheme.contains(getResources().getString(
                R.string.theme_worship_thankfulness))) {
            edit_song_theme_worship_thankfulness.setChecked(true);
        }

        // Now the Spinners
        // The Key
        ArrayAdapter<CharSequence> song_key = ArrayAdapter.createFromResource(getActivity(),
                R.array.key_choice,
                R.layout.my_spinner);
        song_key.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_song_key.setAdapter(song_key);
        // Where is the key in the available array
        int index = -1;
        List<String> key_choice = Arrays.asList(getResources().getStringArray(R.array.key_choice));
        for (int w=0;w<key_choice.size();w++) {
            if (FullscreenActivity.mKey.equals(key_choice.get(w))) {
                index = w;
            }
        }
        edit_song_key.setSelection(index);

        // The time sig
        ArrayAdapter<CharSequence> time_sigs = ArrayAdapter.createFromResource(getActivity(),
                R.array.timesig,
                R.layout.my_spinner);
        time_sigs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_song_timesig.setAdapter(time_sigs);
        // Where is the key in the available array
        index = -1;
        List<String> timesig = Arrays.asList(getResources().getStringArray(R.array.timesig));
        for (int w=0;w<timesig.size();w++) {
            if (FullscreenActivity.mTimeSig.equals(timesig.get(w))) {
                index = w;
            }
        }
        edit_song_timesig.setSelection(index);

        // The capo fret
        ArrayAdapter<CharSequence> capo_fret = ArrayAdapter.createFromResource(getActivity(),
                R.array.capo,
                R.layout.my_spinner);
        capo_fret.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_song_capo.setAdapter(capo_fret);
        // Where is the key in the available array
        index = -1;
        List<String> capo_choice = Arrays.asList(getResources().getStringArray(R.array.capo));
        for (int w=0;w<capo_choice.size();w++) {
            if (FullscreenActivity.mCapo.equals(capo_choice.get(w))) {
                index = w;
            }
        }
        edit_song_capo.setSelection(index);

        // The capo print
        ArrayAdapter<CharSequence> capo_print = ArrayAdapter.createFromResource(getActivity(),
                R.array.capoprint,
                R.layout.my_spinner);
        capo_print.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_song_capo_print.setAdapter(capo_print);
        // Where is the key in the available array
        // List<String> capoprint_choice = Arrays.asList(getResources().getStringArray(R.array.capoprint));
        switch (FullscreenActivity.mCapoPrint) {
            case "true":
                edit_song_capo_print.setSelection(1);
                break;
            case "false":
                edit_song_capo_print.setSelection(2);
                break;
            default:
                edit_song_capo_print.setSelection(0);
                break;
        }

        // The pad file
        // Currently only auto or off
        ArrayList<String> pad_option = new ArrayList<>();
        pad_option.add(getResources().getString(R.string.pad_auto));
        pad_option.add(getResources().getString(R.string.link_audio));
        pad_option.add(getResources().getString(R.string.off));
        ArrayAdapter<String> pad_file;
        pad_file = new ArrayAdapter<>(getActivity(), R.layout.my_spinner, pad_option);
        pad_file.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_song_pad_file.setAdapter(pad_file);
        // Only allow auto for now (first index)
        edit_song_pad_file.setSelection(0);


        // Now the seekbars
        String temp_tempo = FullscreenActivity.mTempo;
        temp_tempo = temp_tempo.replace("Very Fast", "140");
        temp_tempo = temp_tempo.replace("Fast", "120");
        temp_tempo = temp_tempo.replace("Moderate", "100");
        temp_tempo = temp_tempo.replace("Slow", "80");
        temp_tempo = temp_tempo.replace("Very Slow", "60");
        temp_tempo = temp_tempo.replaceAll("[\\D]", "");
        // Get numerical value for slider
        try {
            temposlider = Integer.parseInt(temp_tempo);
        } catch(NumberFormatException nfe) {
            temposlider = 39;
        }
        temposlider = temposlider - 39;

        String newtext = getResources().getString(R.string.notset);
        if (temposlider<1) {
            temposlider=0;
        } else {
            newtext = temp_tempo + " " + getResources().getString(R.string.bpm);
        }
        tempo_text.setText(newtext);
        edit_song_tempo.setProgress(temposlider);
        edit_song_tempo.setOnSeekBarChangeListener(new seekBarListener());

        predelay_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress==0) {
                    predelay_TextView.setText("");
                } else {
                    String text = (progress-1)+"s";
                    predelay_TextView.setText(text);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        return V;
    }

    private class seekBarListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            String newtext = getResources().getString(R.string.notset);
            temposlider = edit_song_tempo.getProgress()+39;
            if (temposlider>39) {
                newtext = temposlider+" "+getResources().getString(R.string.bpm);
            }
            tempo_text.setText(newtext);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}
    }

    public static void prepareBlankSongXML() {
        // Prepare the new XML file
        String myNEWXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        myNEWXML += "<song>\n";
        myNEWXML += "  <title>" + FullscreenActivity.songfilename + "</title>\n";
        myNEWXML += "  <author></author>\n";
        myNEWXML += "  <copyright></copyright>\n";
        myNEWXML += "  <presentation></presentation>\n";
        myNEWXML += "  <hymn_number></hymn_number>\n";
        myNEWXML += "  <capo print=\"\"></capo>\n";
        myNEWXML += "  <tempo></tempo>\n";
        myNEWXML += "  <time_sig></time_sig>\n";
        myNEWXML += "  <duration></duration>\n";
        myNEWXML += "  <predelay></predelay>\n";
        myNEWXML += "  <ccli></ccli>\n";
        myNEWXML += "  <theme></theme>\n";
        myNEWXML += "  <alttheme></alttheme>\n";
        myNEWXML += "  <user1></user1>\n";
        myNEWXML += "  <user2></user2>\n";
        myNEWXML += "  <user3></user3>\n";
        myNEWXML += "  <key></key>\n";
        myNEWXML += "  <aka></aka>\n";
        myNEWXML += "  <key_line></key_line>\n";
        myNEWXML += "  <books></books>\n";
        myNEWXML += "  <midi></midi>\n";
        myNEWXML += "  <midi_index></midi_index>\n";
        myNEWXML += "  <pitch></pitch>\n";
        myNEWXML += "  <restrictions></restrictions>\n";
        myNEWXML += "  <notes></notes>\n";
        myNEWXML += "  <lyrics>[V]\n</lyrics>\n";
        myNEWXML += "  <linked_songs></linked_songs>\n";
        myNEWXML += "  <pad_file></pad_file>\n";
        myNEWXML += "  <custom_chords></custom_chords>\n";
        myNEWXML += "  <link_youtube></link_youtube>\n";
        myNEWXML += "  <link_web></link_web>\n";
        myNEWXML += "  <link_audio></link_audio>\n";
        myNEWXML += "  <loop_audio>false</loop_audio>\n";
        myNEWXML += "  <link_other></link_other>\n";
        myNEWXML += "</song>";
        FullscreenActivity.mynewXML = myNEWXML;
    }

    public static void prepareSongXML() {
        // Prepare the new XML file
        String myNEWXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        myNEWXML += "<song>\n";
        myNEWXML += "  <title>" + parseToHTMLEntities(FullscreenActivity.mTitle.toString()) + "</title>\n";
        myNEWXML += "  <author>" + parseToHTMLEntities(FullscreenActivity.mAuthor.toString()) + "</author>\n";
        myNEWXML += "  <copyright>" + parseToHTMLEntities(FullscreenActivity.mCopyright.toString()) + "</copyright>\n";
        myNEWXML += "  <presentation>" + parseToHTMLEntities(FullscreenActivity.mPresentation) + "</presentation>\n";
        myNEWXML += "  <hymn_number>" + parseToHTMLEntities(FullscreenActivity.mHymnNumber) + "</hymn_number>\n";
        myNEWXML += "  <capo print=\"" + parseToHTMLEntities(FullscreenActivity.mCapoPrint) + "\">" + parseToHTMLEntities(FullscreenActivity.mCapo) + "</capo>\n";
        myNEWXML += "  <tempo>" + parseToHTMLEntities(FullscreenActivity.mTempo) + "</tempo>\n";
        myNEWXML += "  <time_sig>" + parseToHTMLEntities(FullscreenActivity.mTimeSig) + "</time_sig>\n";
        myNEWXML += "  <duration>" + parseToHTMLEntities(FullscreenActivity.mDuration) + "</duration>\n";
        myNEWXML += "  <predelay>" + parseToHTMLEntities(FullscreenActivity.mPreDelay) + "</predelay>\n";
        myNEWXML += "  <ccli>" + parseToHTMLEntities(FullscreenActivity.mCCLI) + "</ccli>\n";
        myNEWXML += "  <theme>" + parseToHTMLEntities(FullscreenActivity.mTheme) + "</theme>\n";
        myNEWXML += "  <alttheme>" + parseToHTMLEntities(FullscreenActivity.mAltTheme) + "</alttheme>\n";
        myNEWXML += "  <user1>" + parseToHTMLEntities(FullscreenActivity.mUser1) + "</user1>\n";
        myNEWXML += "  <user2>" + parseToHTMLEntities(FullscreenActivity.mUser2) + "</user2>\n";
        myNEWXML += "  <user3>" + parseToHTMLEntities(FullscreenActivity.mUser3) + "</user3>\n";
        myNEWXML += "  <key>" + parseToHTMLEntities(FullscreenActivity.mKey) + "</key>\n";
        myNEWXML += "  <aka>" + parseToHTMLEntities(FullscreenActivity.mAka) + "</aka>\n";
        myNEWXML += "  <key_line>" + parseToHTMLEntities(FullscreenActivity.mKeyLine) + "</key_line>\n";
        myNEWXML += "  <books>" + parseToHTMLEntities(FullscreenActivity.mBooks) + "</books>\n";
        myNEWXML += "  <midi>" + parseToHTMLEntities(FullscreenActivity.mMidi) + "</midi>\n";
        myNEWXML += "  <midi_index>" + parseToHTMLEntities(FullscreenActivity.mMidiIndex) + "</midi_index>\n";
        myNEWXML += "  <pitch>" + parseToHTMLEntities(FullscreenActivity.mPitch) + "</pitch>\n";
        myNEWXML += "  <restrictions>" + parseToHTMLEntities(FullscreenActivity.mRestrictions) + "</restrictions>\n";
        myNEWXML += "  <notes>" + parseToHTMLEntities(FullscreenActivity.mNotes) + "</notes>\n";
        myNEWXML += "  <lyrics>" + parseToHTMLEntities(FullscreenActivity.mLyrics) + "</lyrics>\n";
        myNEWXML += "  <linked_songs>" + parseToHTMLEntities(FullscreenActivity.mLinkedSongs) + "</linked_songs>\n";
        myNEWXML += "  <pad_file>" + parseToHTMLEntities(FullscreenActivity.mPadFile) + "</pad_file>\n";
        myNEWXML += "  <custom_chords>" + parseToHTMLEntities(FullscreenActivity.mCustomChords) + "</custom_chords>\n";
        myNEWXML += "  <link_youtube>" + parseToHTMLEntities(FullscreenActivity.mLinkYouTube) + "</link_youtube>\n";
        myNEWXML += "  <link_web>" + parseToHTMLEntities(FullscreenActivity.mLinkWeb) + "</link_web>\n";
        myNEWXML += "  <link_audio>" + parseToHTMLEntities(FullscreenActivity.mLinkAudio) + "</link_audio>\n";
        myNEWXML += "  <loop_audio>" + parseToHTMLEntities(FullscreenActivity.mLoopAudio) + "</loop_audio>\n";
        myNEWXML += "  <link_other>" + parseToHTMLEntities(FullscreenActivity.mLinkOther) + "</link_other>\n";

        if (!FullscreenActivity.mExtraStuff1.isEmpty()) {
            myNEWXML += "  " + FullscreenActivity.mExtraStuff1 + "\n";
        }
        if (!FullscreenActivity.mExtraStuff2.isEmpty()) {
            myNEWXML += "  " + FullscreenActivity.mExtraStuff2 + "\n";
        }
        myNEWXML += "</song>";

        FullscreenActivity.mynewXML = myNEWXML;
    }

    public static void justSaveSongXML() throws IOException {
/*
        // NOW DONE SEPARATELY FOR EACH FIELD TO COPE WITH ALL HTML ENTITIES
        // Makes sure all & are replaced with &amp;
        FullscreenActivity.mynewXML = FullscreenActivity.mynewXML.replace("&amp;","&");
        FullscreenActivity.mynewXML = FullscreenActivity.mynewXML.replace("&","&amp;");
*/

        // Now write the modified song
        String filename;
        if (FullscreenActivity.whichSongFolder.equals(FullscreenActivity.mainfoldername) || FullscreenActivity.whichSongFolder.isEmpty()) {
            filename = FullscreenActivity.dir + "/" + FullscreenActivity.songfilename;
        } else {
            filename = FullscreenActivity.dir + "/" + FullscreenActivity.whichSongFolder + "/" + FullscreenActivity.songfilename;
        }

        FileOutputStream overWrite = new FileOutputStream(filename,	false);
        overWrite.write(FullscreenActivity.mynewXML.getBytes());
        overWrite.flush();
        overWrite.close();
    }

    public static String parseToHTMLEntities(String val) {
        // Make sure all vals are unencoded to start with
        //val = val.replace("&amp;","&");
        // Now HTML encode everything
        val = val.replace("<","&lt;");
        val = val.replace(">","&gt;");
        //val = val.replace("'","&apos;");
        //val = val.replace("\"","&quote;");
        val = val.replace("&","&amp;");


        return val;
    }

    @Override
    public void onStart() {
        super.onStart();

        // safety check
        if (getDialog() == null) {
            return;
        }
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}