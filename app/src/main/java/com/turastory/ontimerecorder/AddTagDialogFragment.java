package com.turastory.ontimerecorder;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.turastory.ontimerecorder.bus.Bus;
import com.turastory.ontimerecorder.bus.AddTagEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soldi on 2017-08-29.
 */

public class AddTagDialogFragment extends DialogFragment {

    @BindView(R.id.tag_edit_text)
    EditText tagEditText;
    @BindView(R.id.confirm_button)
    ImageButton confirmButton;

    public static AddTagDialogFragment newInstance() {
        return new AddTagDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_add_tag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        confirmButton.setOnClickListener(v -> {

            Bus.post(new AddTagEvent(tagEditText.getText().toString()));
            dismiss();
        });

//        tagEditText.getBackground().mutate().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
    }
}
