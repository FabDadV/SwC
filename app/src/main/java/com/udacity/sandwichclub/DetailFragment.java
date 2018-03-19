package com.udacity.sandwichclub;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailFragment extends Fragment {
    private TextView mainNameTv;
    private TextView placeOfOriginTv;
    private TextView alsoKnownTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;
    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getActivity().getIntent().getIntExtra(DetailActivity.EXTRA_POSITION, position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        View v = inflater.inflate(R.layout.fragment_detail,  container, false);
        ImageView ingredientsIv = v.findViewById(R.id.image_iv);
        Picasso.with(v.getContext())
                .load(sandwich.getImage())
                .into(ingredientsIv);

        TextView mainNameTv = (TextView) v.findViewById(R.id.name_tv);
        TextView placeOfOriginTv = (TextView) v.findViewById(R.id.origin_tv);
        TextView alsoKnownTv = (TextView) v.findViewById(R.id.also_known_tv);
        TextView descriptionTv = (TextView) v.findViewById(R.id.description_tv);
        TextView ingredientsTv = (TextView) v.findViewById(R.id.ingredients_tv);

        mainNameTv.setText(sandwich.getMainName());
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        List<String> list=sandwich.getAlsoKnownAs();
        if (list != null) {
            for (String mString : list) alsoKnownTv.append((mString) + " ,");
        }
        descriptionTv.setText(sandwich.getDescription());
        list=sandwich.getIngredients();
        if (list != null) {
            for (String mString : list) ingredientsTv.append((mString) + " ,");
        }
        return v;
    }
}
