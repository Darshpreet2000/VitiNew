package com.example.vitinew.ui;


import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitinew.R;
import com.example.vitinew.ui.Extras.bottomdialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Internships extends Fragment {

    Toolbar toolbar;
    public Internships() {
        // Required empty public constructor
    }

    BottomSheetDialogFragment botomSheetDialogFragment;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setuptoolbar();
        // Inflate the layout for this fragment
        //Find bottom Sheet I

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.internshipjump) {
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.action_internships_to_my_Interships);
                }
             else if(item.getItemId()==R.id.filter){
                    BottomSheetBehavior mBottomSheetBehavior;
                    View bottomSheet = getActivity().findViewById(R.id.bottom_sheet);
                    mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                    botomSheetDialogFragment= new bottomdialog();
                   botomSheetDialogFragment.show(getActivity().getSupportFragmentManager(),"Bottom Sheet Dialog Fragment");
                    mBottomSheetBehavior = BottomSheetBehavior.from(getActivity().findViewById(R.id.bottom_sheet));
                    mBottomSheetBehavior = BottomSheetBehavior.from(getActivity().findViewById(R.id.bottom_sheet));
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mBottomSheetBehavior.setPeekHeight(0);
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mBottomSheetBehavior.setPeekHeight(0);

                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                return true;
            }
        });
        return inflater.inflate(R.layout.fragment_internships, container, false);
    }
    private void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.intership_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }


}
