package com.example.hanakol_2ah.fragments;


import androidx.fragment.app.Fragment;

/**
 * Created by amra on 5/2/2018.
 */


public class BaseFragment extends Fragment {

    /**
     * @return true >> the fragment_map is added not replace  && false>> the fragment_map is Replaced
     * getSupportFragmentManager().beginTransaction().[[[[(true>add)//(false>replace)]]]](R.id.fragment_content, fragment_map, fargmentTag);
     */
    public boolean isFragmentAdded() {
        return false;
    }

    /**
     *
     * @return true if the fragment_map has editViews , and you need to show [discard/stay] alert onBackPress.
     *         otherwise return false
     */
    public boolean hasEditableFields(){
        return false;
    }
}