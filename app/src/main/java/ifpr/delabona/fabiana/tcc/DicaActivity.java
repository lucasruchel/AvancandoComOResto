package ifpr.delabona.fabiana.tcc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DicaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dicas);

        View button = findViewById(R.id.dicaInicial);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
