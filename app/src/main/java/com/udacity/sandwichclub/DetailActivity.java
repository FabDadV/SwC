package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private TextView mainNameTv;
    private TextView placeOfOriginTv;
    private TextView alsoKnownTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich s) {
        TextView mainNameTv = (TextView) findViewById(R.id.name_tv);
        TextView placeOfOriginTv = (TextView) findViewById(R.id.origin_tv);
        TextView alsoKnownTv = (TextView) findViewById(R.id.also_known_tv);
        TextView descriptionTv = (TextView) findViewById(R.id.description_tv);
        TextView ingredientsTv = (TextView) findViewById(R.id.ingredients_tv);

        mainNameTv.setText(s.getMainName());
        placeOfOriginTv.setText(s.getPlaceOfOrigin());
        List<String> list=s.getAlsoKnownAs();
        if (list != null) {
            for (String mString : list) alsoKnownTv.append((mString) + " ,");
        }
         descriptionTv.setText(s.getDescription());
        list=s.getIngredients();
        if (list != null) {
            for (String mString : list) ingredientsTv.append((mString) + " ,");
        }
    }
}
