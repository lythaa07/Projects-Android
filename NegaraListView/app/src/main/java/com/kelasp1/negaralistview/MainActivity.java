package com.kelasp1.negaralistview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.kelasp1.negaralistview.NegaraAdapter;
import com.kelasp1.negaralistview.R;

public class MainActivity extends AppCompatActivity {

    ListView lvNegara;
    String[] namaNegara = {
            "Amerika", "Belanda", "Canada", "China", "Filipina",
            "India", "Spanyol", "Jerman", "Laos", "Portugal"
    };

    int[] Bendera = {
            R.drawable.amerika, R.drawable.belanda, R.drawable.canada,
            R.drawable.china, R.drawable.filipina, R.drawable.india,
            R.drawable.spanyol, R.drawable.jerman, R.drawable.laos,
            R.drawable.portugal
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNegara = findViewById(R.id.lvNegara);

        NegaraAdapter myAdapter = new NegaraAdapter(this, namaNegara, Bendera);

        lvNegara.setAdapter(myAdapter);
    }

}