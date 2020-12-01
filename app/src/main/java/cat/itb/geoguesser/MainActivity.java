package cat.itb.geoguesser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageQuestion;
    TextView textQuestion, textProgressive;
    Button button1, button2, button3, button4;
    ProgressBar progressBar;
    AlertDialog.Builder builderDialog;
    QuizViewModel viewModel;
    QuestionModel [] questionModels  = {
        new QuestionModel(R.string.textQuestion1, R.string.button1_1, R.string.button1_2, R.string.button1_3, R.string.button1_4, R.mipmap.india_foreground,1),
        new QuestionModel(R.string.textQuestion1, R.string.button2_1, R.string.button2_2, R.string.button2_3, R.string.button2_4, R.mipmap.japan_foreground,3),
        new QuestionModel(R.string.textQuestion2, R.string.button3_1, R.string.button3_2, R.string.button3_3, R.string.button3_4, R.mipmap.monte_fuji_foreground,4),
        new QuestionModel(R.string.textQuestion3, R.string.button4_1, R.string.button4_2, R.string.button4_3, R.string.button4_4, R.mipmap.torre_tokio_foreground,3),
        new QuestionModel(R.string.textQuestion1,R.string.button5_1, R.string.button5_2, R.string.button5_3, R.string.button5_4, R.mipmap.united_states_foreground,2),
        new QuestionModel(R.string.textQuestion2,R.string.button6_1, R.string.button6_2, R.string.button6_3, R.string.button6_4, R.mipmap.statue_of_liberty_foreground,4),
        new QuestionModel(R.string.textQuestion3,R.string.button7_1, R.string.button7_2, R.string.button7_2, R.string.button7_4, R.mipmap.white_house_foreground,1),
        new QuestionModel(R.string.textQuestion1,R.string.button8_1, R.string.button8_2, R.string.button8_3, R.string.button8_4, R.mipmap.united_kingdoms,3),
        new QuestionModel(R.string.textQuestion2,R.string.button9_1, R.string.button9_2, R.string.button9_3, R.string.button9_4, R.mipmap.loch_ness_foreground,1),
        new QuestionModel(R.string.textQuestion3,R.string.button10_1, R.string.button10_2, R.string.button10_3, R.string.button10_4, R.mipmap.big_ben_foreground,2)
    };
    Button buttonHint;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textQuestion = findViewById(R.id.textQuestion);
        textProgressive = findViewById(R.id.questionTextView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        progressBar = findViewById(R.id.progressBar);
        imageQuestion = findViewById(R.id.imageQuestion);
        buttonHint = findViewById(R.id.hintButton);
        builderDialog = new AlertDialog.Builder(MainActivity.this);
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        builderDialog.setTitle("Congratulations, you finished the quiz!");
        builderDialog.setPositiveButton("Restar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buttonHint.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                viewModel.setNumHints(0);
                viewModel.setScore(0);
                viewModel.setOrder();
                sumarProgres(1);
            }
        });
        builderDialog.setNegativeButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        progressBar.setMax(10);

        if(viewModel.getIndex() == -1){
            viewModel.setOrder();
            sumarProgres(1);
        }else sumarProgres(viewModel.getIndex());
        if(viewModel.isHint())mostrarResposta();
        if(viewModel.getNumHints() > 2) buttonHint.setBackgroundColor(getResources().getColor(R.color.disableColor));

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResposta(1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResposta(2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResposta(3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarResposta(4);
            }
        });
        buttonHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getNumHints() < 3 && !viewModel.isHint()) {
                    viewModel.setHint(true);
                    mostrarResposta();
                    viewModel.setNumHints(viewModel.getNumHints()+1);
                    if (viewModel.getNumHints() > 2)
                        buttonHint.setBackgroundColor(getResources().getColor(R.color.disableColor));

                }
            }
        });
    }

    public void comprovarResposta(int resposta){
        if(questionModels[viewModel.getOrder()[viewModel.getIndex()-1]].getRespota() == resposta){
            Toast.makeText(MainActivity.this, "Cert", Toast.LENGTH_SHORT).show();
            if(!viewModel.isHint())viewModel.setScore(viewModel.getScore()+1);
        }else{
            Toast.makeText(MainActivity.this, "False", Toast.LENGTH_SHORT).show();
            viewModel.setScore(viewModel.getScore()-0.5);
        }

        viewModel.setHint(false);
        button1.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        button2.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        button3.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        button4.setBackgroundColor(getResources().getColor(R.color.buttonColor));

        if(progressBar.getProgress() == 10){
            builderDialog.setMessage("Score: "+(int)(viewModel.getScore()*10)+"/100\nWhat do you want do next?");
            builderDialog.show();
        }else sumarProgres(viewModel.getIndex()+1);
    }

    public void sumarProgres(int progress){
        viewModel.setIndex(progress);
        imageQuestion.setImageResource(questionModels[viewModel.getOrder()[viewModel.getIndex()-1]].idImage);
        progressBar.setProgress(viewModel.getIndex());
        textQuestion.setText(questionModels[viewModel.getOrder()[viewModel.getIndex()-1]].idQuestion);
        String text = "Question "+viewModel.getIndex()+" of 10";
        textProgressive.setText(text);
        button1.setText(questionModels[viewModel.getOrder()[viewModel.getIndex()-1]].idRespota1);
        button2.setText(questionModels[viewModel.getOrder()[viewModel.getIndex()-1]].idRespota2);
        button3.setText(questionModels[viewModel.getOrder()[viewModel.getIndex()-1]].idRespota3);
        button4.setText(questionModels[viewModel.getOrder()[viewModel.getIndex()-1]].idRespota4);
    }

    public void mostrarResposta() {
        button1.setBackgroundColor(getResources().getColor(R.color.disableColor));
        button2.setBackgroundColor(getResources().getColor(R.color.disableColor));
        button3.setBackgroundColor(getResources().getColor(R.color.disableColor));
        button4.setBackgroundColor(getResources().getColor(R.color.disableColor));
        switch (questionModels[viewModel.getOrder()[viewModel.getIndex()-1]].getRespota()){
            case 1:
                button1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 2:
                button2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 3:
                button3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            case 4:
                button4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
        }
    }
}