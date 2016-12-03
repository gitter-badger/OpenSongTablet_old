package com.garethevans.church.opensongtablet;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PopUpStickyFragment extends DialogFragment {

    static PopUpStickyFragment newInstance() {
        PopUpStickyFragment frag;
        frag = new PopUpStickyFragment();
        return frag;
    }

    public interface MyInterface {
        void openFragment();
        void pageButtonAlpha(String s);
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

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null && getDialog() != null) {
            PopUpSizeAndAlpha.decoratePopUp(getActivity(), getDialog());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(getActivity().getResources().getString(R.string.stickynotes));
        getDialog().setCanceledOnTouchOutside(true);
        mListener.pageButtonAlpha("sticky");

        View V = inflater.inflate(R.layout.popup_page_sticky, container, false);

        // Initialise the views
        TextView mySticky = (TextView) V.findViewById(R.id.mySticky);
        Button editsticky = (Button) V.findViewById(R.id.editsticky);
        Button closesticky = (Button) V.findViewById(R.id.closesticky);

        // Add the stickynotes
        mySticky.setText(FullscreenActivity.mNotes);

        // Set the button listeners
        editsticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullscreenActivity.whattodo = "editnotes";
                mListener.openFragment();
                dismiss();
            }
        });
        closesticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return V;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        if (mListener!=null) {
            mListener.pageButtonAlpha("");
        }
    }
}