package com.javen.papa.fragment.girl;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.javen.papa.R;
import com.javen.papa.activity.ImageActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends Fragment {

    @BindView(R.id.id_girl_home_btn)
    Button idGirlHomeBtn;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.id_girl_home_btn)
    public void onViewClicked() {
        startActivity(new Intent(getActivity().getApplicationContext(), ImageActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
