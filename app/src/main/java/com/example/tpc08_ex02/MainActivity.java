package com.example.tpc08_ex02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.tpc08_ex02.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //region Attributes
    ActivityMainBinding binding;
    CountDown countDown;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //region Set tv_counter value with max
        binding.etMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.tvCounter.setText(binding.etMax.getText().toString());
            }
        });
        //endregion

        //region Handle btnStart
        binding.btnStart.setOnClickListener(view -> {
            int min = Integer.parseInt(binding.etMin.getText().toString());
            int max = Integer.parseInt(binding.etMax.getText().toString());
            int interval = Integer.parseInt(binding.etInterval.getText().toString());
            countDown = new CountDown();
            countDown.execute(min, max, interval);
        });
        //endregion
    }

    //region Class : CountDown
    class CountDown extends AsyncTask<Integer, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.btnStart.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int min = integers[0];
            int max = integers[1];
            int interval = integers[2];
            for (int i = max; i >= min; i --) {
                publishProgress(String.valueOf(i));
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            binding.tvCounter.setText(values[0]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            binding.btnStart.setEnabled(true);
        }
    }
    //endregion

}
