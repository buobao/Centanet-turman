package com.centanet.turman.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.centanet.turman.R;

/**
 * Created by diaoqf on 2016/6/30.
 */
public class HouseListFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frg_houselist,container,false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
