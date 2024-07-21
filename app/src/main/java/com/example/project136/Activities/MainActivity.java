package com.example.project136.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.project136.Adapters.CategoryAdapter;
import com.example.project136.Adapters.PupolarAdapter;
import com.example.project136.Domains.CategoryDomain;
import com.example.project136.Domains.PopularDomain;
import com.example.project136.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterPopular, adapterCat;
    private RecyclerView recyclerViewPopular, recyclerViewCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("Selong Belanak", "Lombok Tengah", "Pantai Selong Belanak terletak di Desa Selong Belanak, Kecamatan Praya Barat, Lombok Tengah, NTB. Pantai ini terletak sekitar 2 kilometer dari Bandara Internasional Lombok dengan waktu tempuh kurang lebih selama 40 menit hingga 1 jam perjalanan.",
                2, true, 4.8, "pic4", true, "1.000.000"));
        items.add(new PopularDomain("Pantai Pink", "Lombok Timur", "Pantai Pink Lombok terletak di Dusun Temeak, Desa Serewe, Kecamatan Jerowaru, Lombok Timur, Provinsi Nusa Tenggara Barat. Pantai Pink Lombok atau Pink Beach Lombok juga disebut Pantai Tangsi",
                2, false, 5, "pic5", false, "2.561.500"));
        items.add(new PopularDomain("Bukit Merese", "Lombok Tengah", "This 2 bed /1 bath home boasts an enormous," +
                " open-living plan, accented by striking " +
                "architectural features and high-end finishes." +
                " Feel inspired by open sight lines that" +
                " embrace the outdoors, crowned by stunning" +
                " coffered ceilings. ", 3, true, 4.3, "pic6", true, "30000"));
        items.add(new PopularDomain("Batu Payung", "Lombok Tengah", "This 2 bed /1 bath home boasts an enormous," +
                " open-living plan, accented by striking " +
                "architectural features and high-end finishes." +
                " Feel inspired by open sight lines that" +
                " embrace the outdoors, crowned by stunning" +
                " coffered ceilings. ", 3, true, 4, "pic7", true, "30000"));
        items.add(new PopularDomain("Pantai Senggigi", "Lombok Barat", "This 2 bed /1 bath home boasts an enormous," +
                " open-living plan, accented by striking " +
                "architectural features and high-end finishes." +
                " Feel inspired by open sight lines that" +
                " embrace the outdoors, crowned by stunning" +
                " coffered ceilings. ", 3, true, 4, "pic10", true, "30000"));
        items.add(new PopularDomain("Gunung Rinjani", "Lombok Timur", "This 2 bed /1 bath home boasts an enormous," +
                " open-living plan, accented by striking " +
                "architectural features and high-end finishes." +
                " Feel inspired by open sight lines that" +
                " embrace the outdoors, crowned by stunning" +
                " coffered ceilings. ", 3, true, 4, "pic11", true, "30000"));
        items.add(new PopularDomain("Gili Sudak", "Lombok Barat", "This 2 bed /1 bath home boasts an enormous," +
                " open-living plan, accented by striking " +
                "architectural features and high-end finishes." +
                " Feel inspired by open sight lines that" +
                " embrace the outdoors, crowned by stunning" +
                " coffered ceilings. ", 3, true, 4, "pic12", true, "30000"));

        recyclerViewPopular = findViewById(R.id.view_pop);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterPopular = new PupolarAdapter(items);
        recyclerViewPopular.setAdapter(adapterPopular);


        ArrayList<CategoryDomain> catsList = new ArrayList<>();
        catsList.add(new CategoryDomain("Beaches", "cat1"));
        catsList.add(new CategoryDomain("Camps", "cat2"));
        catsList.add(new CategoryDomain("Forest", "cat3"));
        catsList.add(new CategoryDomain("Desert", "cat4"));
        catsList.add(new CategoryDomain("Mountain", "cat5"));


        recyclerViewCategory = findViewById(R.id.view_cat);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterCat = new CategoryAdapter(catsList);
        recyclerViewCategory.setAdapter(adapterCat);


    }
}