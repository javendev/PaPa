package com.javen.papa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.javen.papa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Javen on 2017/3/26.
 */

public class TestFragment extends Fragment {
    @BindView(R.id.id_tv)
    TextView idTv;
    Unbinder unbinder;
    private String mArgument;
    public static final String ARGUMENT = "argument";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            mArgument = bundle.getString(ARGUMENT);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_framelayout, null);
        unbinder = ButterKnife.bind(this, view);
        idTv.setText(mArgument);
        return view;
    }

    public static TestFragment newInstance(String argument) {

        Bundle args = new Bundle();
        args.putString(ARGUMENT, argument);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
