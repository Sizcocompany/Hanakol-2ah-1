package com.example.hanakol_2ah.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanakol_2ah.R;
import com.example.hanakol_2ah.models.Meals;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class SelectedItemFragment extends Fragment {

//    i changed the name of that fragment from single view fragment into selected item fragment to be more detailed;

    private ImageView selected_item_photo, favorites_icon, share_icon;
    private TextView selected_item_name, selected_item_steps, selected_item_ingredients, selected_item_owner_name, meal_creation_date;
    private RatingBar meal_rating_Bar;
    private List<ListMealsFragmentContainer> fragmentList;
    private String imageUri, child;
    public static final String EXTRA_NAME = "com.examples.hanakol-2ah.EXTRA_NAME";
    public static final String EXTRA_RATE = "com.examples.hanakol-2ah.EXTRA_RATE";
    public static final String EXTRA_STEPS = "com.examples.hanakol-2ah.EXTRA_STEPS";
    public static final String EXTRA_DESCRIPTION = "com.examples.hanakol-2ah.EXTRA_DESCRIPTION";
    public static final String EXTRA_IMAGE_URL = "com.examples.hanakol-2ah.EXTRA_IMAGE_URL";
    private MyFavoritesFragment myFavoritesFragment;
    private View view;
    private int MEAL_FAVORITES_CONDITION;

    DatabaseReference databaseRef;
    StorageReference storageRef;
    private CollectionReference notebookRef;


    Uri imageurl;
    Boolean isImageAdded = false;
    private int REQUEST_CODE_IMAGE = 101;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_selected_item, container, false);
        this.view = view;
        selected_item_photo = view.findViewById(R.id.selected_item_image_iv);
        selected_item_name = view.findViewById(R.id.selected_item_name_tv);
        selected_item_ingredients = view.findViewById(R.id.selected_item_ingreadents_tv);
        selected_item_steps = view.findViewById(R.id.selected_items_steps_tv);
        meal_rating_Bar = view.findViewById(R.id.meal_rating_Bar);
        selected_item_owner_name = view.findViewById(R.id.selected_item_owner_name_tv);
        favorites_icon = view.findViewById(R.id.favorites_icon);
        ImageView back_image_button = view.findViewById(R.id.back_click_image);
        meal_creation_date = view.findViewById(R.id.upload_date_txt);
        share_icon = view.findViewById(R.id.share_icon);

        myFavoritesFragment = new MyFavoritesFragment();


        Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            Picasso.get().load(bundle.getString("MEAL_IMAGE_URI")).into(selected_item_photo);
            selected_item_ingredients.setText(bundle.getString("MEAL_DESCRIPTION"));
            imageUri = bundle.getString("MEAL_IMAGE_URI");
            selected_item_name.setText(bundle.getString("MEAL_NAME"));
            selected_item_steps.setText(bundle.getString("MEAL_STEP"));
            meal_rating_Bar.setRating(Float.parseFloat(bundle.getString("MEAL_RATE")));
            selected_item_owner_name.setText(bundle.getString("MEAL_OWNER_EMAIL"));
            meal_creation_date.setText(bundle.getString("MEAL_CREATION_DATE"));
            MEAL_FAVORITES_CONDITION = bundle.getInt("MEAL_FAVORITES_CONDITION");
            child = bundle.getString("CHILD");

        }


        if (MEAL_FAVORITES_CONDITION == 0) {
            favorites_icon.setImageResource(R.drawable.ic_favorite_icon);
        } else if (MEAL_FAVORITES_CONDITION == 1) {
            favorites_icon.setImageResource(R.drawable.ic_favorite_done);
        }



        favorites_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                if (selected_item_name != null) {
                    handleFavoriteIcon(view);

                    addFavoritesItems(db, selected_item_name.getText().toString(), imageUri, selected_item_ingredients.getText().toString(), selected_item_steps.getText().toString()
                            , meal_rating_Bar.getRating(), selected_item_owner_name.getText().toString(), meal_creation_date.getText().toString() , 1 );

                }


            }
        });
        back_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListMealsFragmentContainer fragment = new ListMealsFragmentContainer(child);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.activity_home_container, fragment);
                fragmentTransaction.commit();
//                proceedWithBack();

            }
        });
        share_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Hey, I'm using Hanakol 2ah App* for sending awesome Meals \n \nDownload it now: https://play.google.com/store/apps/details?id=com.yourappurlhere";
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    //change the app package id as your wish for sharing content to the specific one, WhatsApp's package id is com.whatsapp, and for facebook is com.facebook.katana
                    // sharingIntent.setPackage("com.whatsapp");
                    startActivity(sharingIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Intent sharingIntent1 = new Intent(Intent.ACTION_SEND);
                    sharingIntent1.setType("text/plain");
                    String shareBody = "Hey, I'm using Hanakol 2ah App* for sending awesome Meals  \n \nDownload it now: https://play.google.com/store/apps/details?id=com.yourappurlhere";
                    String shareSubject = "Stickers Android App";
                    sharingIntent1.putExtra(Intent.EXTRA_TEXT, shareBody);
                    sharingIntent1.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                    startActivity(Intent.createChooser(sharingIntent1, "Share with friends"));
                }




            }
        });


        return view;
    }

    private void handleFavoriteIcon(View view) {
        MyFavoritesFragment myFavoritesFragment = new MyFavoritesFragment();
        myFavoritesFragment.favoriteImage_favorite_fragment = view.findViewById(R.id.favorites_icon);
        if (myFavoritesFragment.favoriteImage_favorite_fragment.getDrawable() != getResources().getDrawable(R.drawable.ic_favorite_icon)) {
            favorites_icon.setImageResource(R.drawable.ic_favorite_done);
            myFavoritesFragment.favoriteImage_favorite_fragment.setImageResource(R.drawable.ic_favorite_done);

        } else {
            favorites_icon.setImageResource(R.drawable.ic_favorite_icon);
            myFavoritesFragment.favoriteImage_favorite_fragment.setImageResource(R.drawable.ic_favorite_icon);
            // deleteFromFavirotes();

        }

    }
//    public void proceedWithBack() {
//        if (fragmentList.size() > 1) {
//
//            if (fragmentList.get( fragmentList.size() - 1 ).isFragmentAdded()) {
//                getFragmentManager().beginTransaction().remove( fragmentList.get( fragmentList.size() - 1 ) ).commit();
//                fragmentList.remove( fragmentList.size() - 1 );
//                if (getCurrentFragment() != null) {
//                    if (getCurrentFragment().getView() != null) {
//                        getCurrentFragment().getView().setVisibility( View.VISIBLE );
//                    }
//                }
//            } else {
//                fragmentList.remove( fragmentList.size() - 1 );
//                getFragmentManager().beginTransaction().replace( R.id.activity_home_container, fragmentList.get( fragmentList.size() - 1 ) ).commit();
//            }
//
//        } else {
//
//            if (getActivity() instanceof HomeActivity || getActivity() instanceof HomeActivity) {
//                getActivity().moveTaskToBack( true );
//            } else {
//                super.getActivity().onBackPressed();
//            }
//
//        }
//        }

    public ListMealsFragmentContainer getCurrentFragment() {
        if (fragmentList.size() > 0) {
            return fragmentList.get(fragmentList.size() - 1);
        } else {
            return null;
        }
    }


    private void addFavoritesItems(final FirebaseFirestore db, final String mealName, final String imageUri, final String mealDescription, final String mealSteps, final Float mealRate, final String mealOwnerName, final String mealDateCreation , final int favoriteCondition) {


        HashMap hashMap = new HashMap();
        hashMap.put("MealName", mealName);
        hashMap.put("Description", mealDescription);
        hashMap.put("Steps", mealSteps);
        hashMap.put("ImageURL", imageUri);
        hashMap.put("MealOwnerName", mealOwnerName);
        hashMap.put("MealRate", mealRate);
        hashMap.put("MealSenderEmail", onGetMealSenderEmail());
        hashMap.put("MealCreationDate", mealDateCreation);

        Meals meals = new Meals(mealDescription, imageUri, mealName, mealRate, mealSteps, mealOwnerName, mealDateCreation);
        meals.setMealSender(onGetMealSenderEmail());
        CollectionReference notebookRef = db.collection("favorites");

        notebookRef.document(mealName).set(meals).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getActivity(), "Data Added successfully in your favorites ", Toast.LENGTH_LONG).show();
            }
        });

    }

    private String onGetMealSenderEmail() {
        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;
        GoogleApiClient mGoogleApiClient;
        String mUsername = "UserName";
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity() /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();

            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser == null) {
                // Not signed in, launch the Sign In activity
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
            } else {
                mUsername = mFirebaseUser.getEmail();

            }
        } catch (Exception e) {
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser != null) {
                mUsername = mFirebaseUser.getEmail();

            }
        }

        return mUsername;
    }

    private void FragmentTransaction(Fragment fragment, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragment.setArguments(bundle);
    }
    public static String removeWord(String string, String word) {

        // Check if the word is present in string
        // If found, remove it using removeAll()
        if (string.contains(word)) {

            // To cover the case
            // if the word is at the
            // beginning of the string
            // or anywhere in the middle
            String tempWord = word + " ";
            string = string.replaceAll(tempWord, "");

            // To cover the edge case
            // if the word is at the
            // end of the string
            tempWord = " " + word;
            string = string.replaceAll(tempWord, "");
        }

        // Return the resultant string
        return string;
    }


}