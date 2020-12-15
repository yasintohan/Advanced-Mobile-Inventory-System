package com.tohandesign.data_project_mobile;

import android.view.View;
import android.widget.TextView;

class ViewHolder {
    TextView mTextView;
    TextView reqTextView;
    ViewHolder(View view) {
        mTextView = view.findViewById(R.id.textView);
        reqTextView = view.findViewById(R.id.reqText);
    }
}
