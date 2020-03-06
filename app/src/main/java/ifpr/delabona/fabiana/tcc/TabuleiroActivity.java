package ifpr.delabona.fabiana.tcc;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.zip.Inflater;

import ifpr.delabona.fabiana.tcc.tasks.ContadorTask;
import ifpr.delabona.fabiana.tcc.views.Casas;
import ifpr.delabona.fabiana.tcc.views.TabuleiroView;

public class TabuleiroActivity extends AppCompatActivity implements ContadorTask.CountTimerListener{
    private Random mRandomObject;
    private int mValorSorteado;
    private boolean jogouDados; //Variável para controlar se os dados foram jogados


    private ImageView mDado1;
    private ImageView mDado2;
    private Button mBtnVerifica;
    private EditText mValorDigitado;
    private TabuleiroView tabuleiro;
    private TextView cronometro;

    private int posicaoFinal; //Última posição, utilizada para verificar final do jogo

    private ContadorTask contadorTask;

    private MediaPlayer mediaPlayer;

    private ObjectAnimator colorAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabuleiro);

        tabuleiro = (TabuleiroView) findViewById(R.id.tabuleiro);

        String[] values = getResources().getStringArray(R.array.numeros);
        posicaoFinal = values.length-1;

        tabuleiro.setValues(values);


        Intent it = getIntent();

        Bundle bundle = it.getExtras();

        int imagePeao = bundle.getInt(MainActivity.PEAO_KEY);

        tabuleiro.setPlayerIcon(imagePeao);

        mRandomObject = new Random();

        mDado1 = (ImageView) findViewById(R.id.dado1);
        mDado2 = (ImageView) findViewById(R.id.dado2);

        mBtnVerifica = (Button) findViewById(R.id.verificarValor);
        mValorDigitado = (EditText) findViewById(R.id.valorDigitado);

        cronometro = (TextView) findViewById(R.id.cronometro);

        jogouDados = false;

        mediaPlayer = MediaPlayer.create(this,R.raw.somfundo);
        mediaPlayer.setLooping(true);


        //Cria objeto que altera cor de texto
        colorAnim = ObjectAnimator.ofInt(cronometro, "textColor",
                Color.RED, Color.BLUE,Color.MAGENTA);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatMode(ValueAnimator.RESTART);
        colorAnim.setRepeatCount(6);
        colorAnim.setDuration(10000);


    }

    @Override
    protected void onStart() {
        super.onStart();

        mediaPlayer.start();

    }

    private void alterarDados(ImageView dado, int valor){
        switch (valor){
            case 1:
                dado.setImageResource(R.drawable.d1);
                break;
            case 2:
                dado.setImageResource(R.drawable.d2);
                break;
            case 3:
                dado.setImageResource(R.drawable.d3);
                break;
            case 4:
                dado.setImageResource(R.drawable.d4);
                break;
            case 5:
                dado.setImageResource(R.drawable.d5);
                break;
            case 6:
                dado.setImageResource(R.drawable.d6);
                break;
        }
    }
    //Quando clica no botão de ajuda
    public void mostraDicas(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Entendi",null)
                .setTitle("Dicas")
                .setMessage("\n" +
                        "\n" +
                        "1ª DICA: Jogue os dados.\n" +
                        "\n" +
                        "2ª DICA: Some os números obtidos nos dados. \n" +
                        "\n" +
                        "3ª DICA: Divida o número da casa em que seu peão está pela soma obtida através dos dados.\n" +
                        "\n" +
                        "4ª DICA: Escreva o resto da divisão obtida no espaço adequado e clique no botão de verificar para testar se o resto da divisão está correta.\n" +
                        "\n" +
                        "5ª DICA: Se o resto da divisão estiver exata, seu peão andará a quantidade equivalente ao resto da divisão. \n" +
                        "\n" +
                        "6ª DICA: Caso esteja errado o resto da divisão, seu peão voltará três casas. \n" +
                        "\n" +
                        "7ª DICA: Você terá um tempo para responder e verificar o resto da divisão, caso estoure o tempo, seu peão voltará duas casas. \n" +
                        "\n" +
                        "8ª DICA: O objetivo é chegar exatamente na casa FIM, caso o resultado verificado utrapasse essa casa, o peão continuará avançando no início do tabuleiro");
        builder.show();
    }

    //Método chamado quando o botão de verificar é clicado
    public void verificar(View v){



        //EditText do layout
        String valorDigitado = mValorDigitado.getText().toString();

        //Verifica se usuário digitou valor
        if (valorDigitado.length() == 0){
            Toast.makeText(this, "Digite um valor!!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (jogouDados){
            jogouDados = false;
        }else{
            Toast.makeText(this, "Você deve jogar os dados!!", Toast.LENGTH_SHORT).show();
            return;
        }


        //Para cronômetro e animação
        contadorTask.cancel();
        cronometro.setText("00");
        colorAnim.pause();
        contadorTask = null;


        //Limpa valor de EditText
        mValorDigitado.setText("");
        //Pega posição atual do tabuleiro
        int posicao = tabuleiro.getPosicao();

        Casas casa = tabuleiro.getCasaAtual();

        //Inicializa valor digita
        int digitado;
        //inicializa valor da casa
        int value = 0;

        try{
            value = Integer.parseInt(casa.getmValue());
            digitado = Integer.parseInt(valorDigitado);
        }catch (NumberFormatException e){
            digitado = 0;
        }



        //Acerta resto da divisão
        if(value%mValorSorteado==digitado){
            //Resto da divisão é 0 - volta uma casa

            if (posicao+digitado>posicaoFinal){ //extrapola posição final
                    int movimento = (posicao+digitado) - posicaoFinal;
                    tabuleiro.setPosicao(movimento-1);
            }else {
                //Anda o número de casas referente ao resto da divisão
                tabuleiro.setPosicao(posicao+digitado);
            }
        }else { //Erra resto da divisão
            Toast.makeText(this, "Pense mais um pouco, tente na próxima!", Toast.LENGTH_SHORT).show();

            if (posicao-3>0)
                tabuleiro.setPosicao(posicao-3);
            else
                tabuleiro.setPosicao(0);
        }

        if (tabuleiro.getPosicao() == posicaoFinal){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);



            LayoutInflater inflater = getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.felicitacoes,null,false));

            builder.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton("Jogar Novamente", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tabuleiro.setPosicao(0);
                }
            });
            builder.setTitle("Você chegou ao fim do jogo!!");

            builder.show();
        }
    }

    public void sortearDados(View v){

        if (!jogouDados){
            jogouDados = true;
        }else {
            Toast.makeText(this, "Você ja jogou os dados!!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (contadorTask == null){
            contadorTask = new ContadorTask();
            contadorTask.setListener(this);
            contadorTask.start();
            colorAnim.start();
        }

        //Sorteia um número entre 1 e 6
        int randomValue = mRandomObject.nextInt(6)+1;


        alterarDados(mDado1,randomValue);
        mValorSorteado = randomValue;

        randomValue = mRandomObject.nextInt(6)+1;
        mValorSorteado += randomValue;

        alterarDados(mDado2,randomValue);
    }

    @Override
    public void updateCounter(long remainingTime) {
        cronometro.setText(String.valueOf(remainingTime));

        if (remainingTime==0){
            //Pega posição atual do tabuleiro
            int posicao = tabuleiro.getPosicao();

            Casas casa = tabuleiro.getCasaAtual();

            if (posicao-3>0)
                tabuleiro.setPosicao(posicao-2);
            else
                tabuleiro.setPosicao(0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mediaPlayer.release();
        mediaPlayer = null;
    }
}
