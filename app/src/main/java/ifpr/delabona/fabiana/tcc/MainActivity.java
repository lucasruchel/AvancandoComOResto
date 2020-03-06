package ifpr.delabona.fabiana.tcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imgPeao;

    public static final String PEAO_KEY = "PEAO";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botao = (Button) findViewById(R.id.button);



        botao.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void escolherPeao(View v){
        if (imgPeao != null){
            imgPeao.setAlpha(0.60f);
        }
        imgPeao = (ImageView) v;
        imgPeao.setAlpha(1.0f);


    }

    @Override
    public void onClick(View v) {
        if (imgPeao == null){
            Toast.makeText(this, "Selecione um peão", Toast.LENGTH_SHORT).show();

            return;
        }

        Bundle bundle = new Bundle();

        int imageId = imgPeao.getId();
        int imageResource = 0;

        switch (imageId){
            case R.id.iCoelho:
                imageResource = R.drawable.coelho;
                break;
            case R.id.iGato:
                imageResource = R.drawable.gato;
                break;
            case  R.id.iGirafa:
                imageResource = R.drawable.girafa;
                break;
            case R.id.iOvelha:
                imageResource = R.drawable.ovelha;
                break;
            case R.id.iTigre:
                imageResource = R.drawable.tigre;
                break;
            case R.id.iPanda:
                imageResource = R.drawable.panda;
                break;
        }


        bundle.putInt(PEAO_KEY,imageResource);


        Intent it = new Intent(this, TabuleiroActivity.class);
        it.putExtras(bundle);

        Intent itDica = new Intent(this, DicaActivity.class);



        TaskStackBuilder builder;
        builder = TaskStackBuilder.create(this);

        builder.addParentStack(this);


        builder.addNextIntentWithParentStack(it);
        builder.addNextIntent(itDica);




        builder.startActivities();
    }
}
