package com.example.utog;
import com.example.utog.DjangoApi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button mUploadButton;
    private Interpreter interpreter;
    private ArrayList<String> labels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);
        ImageView imageView = findViewById(R.id.imageView);

        button.setOnClickListener(viewClickListener);



        imageView.setOnClickListener(viewClickListener);
        //mUploadButton = findViewById(R.id.button_upload);
        //mUploadButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        uploadFoto();
        //    }
        //});



        // Настройка кнопки выбора изображения
        Button btnSelectImage = findViewById(R.id.button_upload);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создание интента для открытия галереи изображений
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        try {
            // Загрузка модели из папки assets
            AssetFileDescriptor fileDescriptor = getAssets().openFd("tfl_model_class8_acc98_val50.h5.tflite");
            FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
            FileChannel fileChannel = inputStream.getChannel();
            long startOffset = fileDescriptor.getStartOffset();
            long declaredLength = fileDescriptor.getDeclaredLength();
            MappedByteBuffer modelBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);

            // Инициализация интерпретатора с помощью модели и параметров
            Interpreter.Options options = new Interpreter.Options();
            interpreter = new Interpreter(modelBuffer, options);

            // Загрузка меток классов из папки assets
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("mush_8cl.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                labels.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    View.OnClickListener viewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }
    };

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popmenu);

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                Intent intent = new Intent(MainActivity.this, amanita_muscar1.class);
                                startActivity(intent);

                                return true;
                            case R.id.menu2:
                                Intent intent1 = new Intent(MainActivity.this, amanita_muscar2.class);
                                startActivity(intent1);
                                return true;
                            case R.id.menu3:
                                Intent intent2 = new Intent(MainActivity.this, amanita_muscar3.class);
                                startActivity(intent2);
                                return true;
                            case R.id.menu4:
                                Intent intent3 = new Intent(MainActivity.this, amanita_muscar4.class);
                                startActivity(intent3);
                                return true;
                            case R.id.menu5:
                                Intent intent4 = new Intent(MainActivity.this, amanita_muscar5.class);
                                startActivity(intent4);
                                return true;
                            case R.id.menu6:
                                Intent intent5 = new Intent(MainActivity.this, amanita_muscar6.class);
                                startActivity(intent5);
                                return true;
                            case R.id.menu7:
                                Intent intent6 = new Intent(MainActivity.this, amanita_muscar7.class);
                                startActivity(intent6);
                                return true;
                            case R.id.menu8:
                                Intent intent7 = new Intent(MainActivity.this, amanita_muscar8.class);
                                startActivity(intent7);
                                return true;
                            default:
                                return false;
                        }
                    }
                });


        popupMenu.show();
    }
//    private void uploadFoto() {
//
//        String image_path = "/storage/emulated/DCIM/Camera/img_test1";
//        File imageFile = new File(image_path);
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(DjangoApi.DJANGO_SITE)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        DjangoApi postApi= retrofit.create(DjangoApi.class);
//
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"), imageFile);
//        MultipartBody.Part multiPartBody = MultipartBody.Part
//                .createFormData("model_pic", imageFile.getName(), requestBody);
//
//
//
//        Call<RequestBody> call = postApi.uploadFile(multiPartBody);
//
//        call.enqueue(new Callback<RequestBody>() {
//            @Override
//            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
//                Log.d("good", "good");
//
//            }
//            @Override
//            public void onFailure(Call<RequestBody> call, Throwable t) {
//                Log.d("fail", "fail");
//            }
//        });
//
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Получаем URI выбранного изображения
            Uri selectedImage = data.getData();

            try {
                // Загружаем выбранное изображение в объект Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                // Меняем размер изображения до 128x128
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, true);

                // Преобразуем изображение в ByteBuffer
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(128 * 128 * 3 * 4);
                byteBuffer.order(ByteOrder.nativeOrder());
                int[] pixels = new int[128 * 128];
                resizedBitmap.getPixels(pixels, 0, 128, 0, 0, 128, 128);
                for (int pixel : pixels) {
                    byteBuffer.putFloat((Color.red(pixel) - 127.5f) / 127.5f);
                    byteBuffer.putFloat((Color.green(pixel) - 127.5f) / 127.5f);
                    byteBuffer.putFloat((Color.blue(pixel) - 127.5f) / 127.5f);
                }
                byteBuffer.rewind();

                // Запускаем вывод на основе ByteBuffer
                float[][] output = new float[1][8];
                interpreter.run(byteBuffer, output);

                // Получаем предсказанный класс и вероятность для каждого класса
                List<Prediction> predictionList = new ArrayList<>();
                for (int i = 0; i < output[0].length; i++) {
                    predictionList.add(new Prediction(i, labels.get(i), output[0][i]));
                }

                // Сортируем список предсказаний в порядке убывания вероятности
                Collections.sort(predictionList, new Comparator<Prediction>() {
                    @Override
                    public int compare(Prediction p1, Prediction p2) {
                        return Float.compare(p2.getProbability(), p1.getProbability());
                    }
                });

                // Выводим три наиболее вероятных предсказания в TextView
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 3; i++) {
                    Prediction prediction = predictionList.get(i);
                    sb.append(prediction.getClassName());
                    sb.append(": ");
                    sb.append(String.format("%.2f", prediction.getProbability() * 100));
                    sb.append("%");
                    if (i != 2) {
                        sb.append("\n");
                    }
                }
                TextView textView = findViewById(R.id.textView);
                textView.setText(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Prediction {
        private int classId;
        private String className;
        private float probability;

        public Prediction(int classId, String className, float probability) {
            this.classId = classId;
            this.className = className;
            this.probability = probability;
        }

        public int getClassId() {
            return classId;
        }

        public String getClassName() {
            return className;
        }

        public float getProbability() {
            return probability;
        }
    }

}