package com.garethevans.church.opensongtablet;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class PopUpLayoutFragment extends DialogFragment {

    static PopUpLayoutFragment newInstance() {
        PopUpLayoutFragment frag;
        frag = new PopUpLayoutFragment();
        return frag;
    }

    SeekBar setXMarginProgressBar;
    SeekBar setYMarginProgressBar;
    ToggleButton toggleAutoScaleButton;
    SeekBar setMaxFontSizeProgressBar;
    SeekBar setFontSizeProgressBar;
    TextView fontSizePreview;
    TextView maxfontSizePreview;
    Spinner presoFontSpinner;
    SeekBar presoTitleSizeSeekBar;
    SeekBar presoAuthorSizeSeekBar;
    SeekBar presoCopyrightSizeSeekBar;
    SeekBar presoAlertSizeSeekBar;
    Button closeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(getActivity().getResources().getString(R.string.settings));
        final View V = inflater.inflate(R.layout.popup_layout, container, false);

        setXMarginProgressBar = (SeekBar) V.findViewById(R.id.setXMarginProgressBar);
        setYMarginProgressBar = (SeekBar) V.findViewById(R.id.setYMarginProgressBar);
        toggleAutoScaleButton = (ToggleButton) V.findViewById(R.id.toggleAutoScaleButton);
        setMaxFontSizeProgressBar = (SeekBar) V.findViewById(R.id.setMaxFontSizeProgressBar);
        setFontSizeProgressBar = (SeekBar) V.findViewById(R.id.setFontSizeProgressBar);
        fontSizePreview = (TextView) V.findViewById(R.id.fontSizePreview);
        maxfontSizePreview = (TextView) V.findViewById(R.id.maxfontSizePreview);
        closeLayout = (Button) V.findViewById(R.id.closeLayout);
        presoFontSpinner = (Spinner) V.findViewById(R.id.presoFontSpinner);
        presoTitleSizeSeekBar = (SeekBar) V.findViewById(R.id.presoTitleSizeSeekBar);
        presoAuthorSizeSeekBar = (SeekBar) V.findViewById(R.id.presoAuthorSizeSeekBar);
        presoCopyrightSizeSeekBar = (SeekBar) V.findViewById(R.id.presoCopyrightSizeSeekBar);
        presoAlertSizeSeekBar = (SeekBar) V.findViewById(R.id.presoAlertSizeSeekBar);

        SetTypeFace.setTypeface();

        // Set the stuff up to what it should be from preferences
        fontSizePreview.setTypeface(FullscreenActivity.presofont);
        String newtext = (FullscreenActivity.presoFontSize) + " sp";
        fontSizePreview.setText(newtext);
        fontSizePreview.setTextSize(FullscreenActivity.presoFontSize);
        maxfontSizePreview.setTypeface(FullscreenActivity.presofont);
        newtext = (FullscreenActivity.presoMaxFontSize) + " sp";
        maxfontSizePreview.setText(newtext);
        maxfontSizePreview.setTextSize(FullscreenActivity.presoMaxFontSize);
        setXMarginProgressBar.setMax(200);
        setYMarginProgressBar.setMax(200);
        setMaxFontSizeProgressBar.setMax(70);
        setFontSizeProgressBar.setMax(70);
        presoTitleSizeSeekBar.setMax(32);
        presoAuthorSizeSeekBar.setMax(32);
        presoCopyrightSizeSeekBar.setMax(32);
        presoAlertSizeSeekBar.setMax(32);
        presoTitleSizeSeekBar.setProgress(FullscreenActivity.presoTitleSize);
        presoAuthorSizeSeekBar.setProgress(FullscreenActivity.presoAuthorSize);
        presoCopyrightSizeSeekBar.setProgress(FullscreenActivity.presoCopyrightSize);
        presoAlertSizeSeekBar.setProgress(FullscreenActivity.presoAlertSize);

        setXMarginProgressBar.setProgress(FullscreenActivity.xmargin_presentation);
        setYMarginProgressBar.setProgress(FullscreenActivity.ymargin_presentation);
        setFontSizeProgressBar.setProgress(FullscreenActivity.presoFontSize - 4);
        setMaxFontSizeProgressBar.setProgress(FullscreenActivity.presoMaxFontSize - 4);

        if (FullscreenActivity.presoAutoScale) {
            setFontSizeProgressBar.setEnabled(false);
            setFontSizeProgressBar.setAlpha(0.5f);
            setMaxFontSizeProgressBar.setEnabled(true);
            setMaxFontSizeProgressBar.setAlpha(1.0f);
            maxfontSizePreview.setAlpha(1.0f);
            toggleAutoScaleButton.setChecked(true);
            fontSizePreview.setAlpha(0.5f);
        } else {
            setFontSizeProgressBar.setEnabled(true);
            setFontSizeProgressBar.setAlpha(1.0f);
            setMaxFontSizeProgressBar.setEnabled(false);
            setMaxFontSizeProgressBar.setAlpha(0.5f);
            maxfontSizePreview.setAlpha(0.5f);
            toggleAutoScaleButton.setChecked(false);
            fontSizePreview.setAlpha(1.0f);
        }

        // Set up the font spinner
        ArrayList<String> font_choices = new ArrayList<>();
        font_choices.add(getResources().getString(R.string.font_default));
        font_choices.add(getResources().getString(R.string.font_monospace));
        font_choices.add(getResources().getString(R.string.font_sans));
        font_choices.add(getResources().getString(R.string.font_serif));
        font_choices.add(getResources().getString(R.string.font_firasanslight));
        font_choices.add(getResources().getString(R.string.font_firasansregular));
        font_choices.add(getResources().getString(R.string.font_kaushanscript));
        font_choices.add(getResources().getString(R.string.font_latolight));
        font_choices.add(getResources().getString(R.string.font_latoregular));
        font_choices.add(getResources().getString(R.string.font_leaguegothic));
        ArrayAdapter<String> choose_fonts = new ArrayAdapter<>(getActivity(), R.layout.my_spinner, font_choices);
        choose_fonts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        presoFontSpinner.setAdapter(choose_fonts);
        presoFontSpinner.setSelection(FullscreenActivity.mypresofontnum);

        // Set listeners
        presoFontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FullscreenActivity.mypresofontnum = position;
                SetTypeFace.setTypeface();
                fontSizePreview.setTypeface(FullscreenActivity.presofont);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        setXMarginProgressBar.setOnSeekBarChangeListener(new setMargin_Listener());
        setYMarginProgressBar.setOnSeekBarChangeListener(new setMargin_Listener());
        setMaxFontSizeProgressBar.setOnSeekBarChangeListener(new setMaxFontSizeListener());
        setFontSizeProgressBar.setOnSeekBarChangeListener(new setFontSizeListener());
        presoTitleSizeSeekBar.setOnSeekBarChangeListener(new presoSectionSizeListener());
        presoAuthorSizeSeekBar.setOnSeekBarChangeListener(new presoSectionSizeListener());
        presoCopyrightSizeSeekBar.setOnSeekBarChangeListener(new presoSectionSizeListener());
        presoAlertSizeSeekBar.setOnSeekBarChangeListener(new presoSectionSizeListener());

        closeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Grab the variables, save and close
                FullscreenActivity.xmargin_presentation = setXMarginProgressBar.getProgress();
                FullscreenActivity.ymargin_presentation = setYMarginProgressBar.getProgress();
                FullscreenActivity.presoAutoScale = toggleAutoScaleButton.isChecked();
                FullscreenActivity.presoFontSize = setFontSizeProgressBar.getProgress() + 4;
                FullscreenActivity.presoMaxFontSize = setMaxFontSizeProgressBar.getProgress() + 4;
                Preferences.savePreferences();
                dismiss();
            }
        });

        toggleAutoScaleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setFontSizeProgressBar.setEnabled(false);
                    setFontSizeProgressBar.setAlpha(0.5f);
                    fontSizePreview.setAlpha(0.5f);
                    setMaxFontSizeProgressBar.setEnabled(true);
                    setMaxFontSizeProgressBar.setAlpha(1.0f);
                    maxfontSizePreview.setAlpha(1.0f);

                    PresenterMode.autoscale = true;
                    FullscreenActivity.presoAutoScale = true;
                    Preferences.savePreferences();
                    //MyPresentation.doScale();
                    MyPresentation.resetFontSize();
                } else {
                    setFontSizeProgressBar.setEnabled(true);
                    setFontSizeProgressBar.setAlpha(1.0f);
                    fontSizePreview.setAlpha(1.0f);
                    setMaxFontSizeProgressBar.setEnabled(false);
                    setMaxFontSizeProgressBar.setAlpha(0.5f);
                    maxfontSizePreview.setAlpha(0.5f);
                    PresenterMode.autoscale = false;
                    FullscreenActivity.presoAutoScale = false;
                    Preferences.savePreferences();
                    MyPresentation.updateFontSize();
                }
            }
        });

        return V;
    }

    private class setMargin_Listener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            PresenterMode.tempxmargin = setXMarginProgressBar.getProgress();
            PresenterMode.tempymargin = setYMarginProgressBar.getProgress();
            MyPresentation.changeMargins();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {
            FullscreenActivity.xmargin_presentation = setXMarginProgressBar.getProgress();
            FullscreenActivity.ymargin_presentation = setYMarginProgressBar.getProgress();
            // Save preferences
            Preferences.savePreferences();
        }
    }

    private class setFontSizeListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            FullscreenActivity.presoFontSize = progress + 4;
            String newtext = (progress + 4) + " sp";
            fontSizePreview.setText(newtext);
            fontSizePreview.setTextSize(progress + 4);
            MyPresentation.updateFontSize();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {
            FullscreenActivity.presoFontSize = seekBar.getProgress() + 4;
            // Save preferences
            Preferences.savePreferences();
        }
    }

    private class setMaxFontSizeListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            FullscreenActivity.presoMaxFontSize = progress + 4;
            String newtext = (progress + 4) + " sp";
            maxfontSizePreview.setText(newtext);
            maxfontSizePreview.setTextSize(progress + 4);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {
            FullscreenActivity.presoMaxFontSize = seekBar.getProgress() + 4;
            // Save preferences
            Preferences.savePreferences();
        }
    }

    private class presoSectionSizeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            FullscreenActivity.presoTitleSize = presoTitleSizeSeekBar.getProgress();
            FullscreenActivity.presoAuthorSize = presoAuthorSizeSeekBar.getProgress();
            FullscreenActivity.presoCopyrightSize = presoCopyrightSizeSeekBar.getProgress();
            FullscreenActivity.presoAlertSize = presoAlertSizeSeekBar.getProgress();
            MyPresentation.updateFontSize();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Save the preferences
            Preferences.savePreferences();
        }
    }
}
