package id.nyaa.exercise0420;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UpdateData extends AppCompatActivity {

    private DBHelper mydb;

    TextView email;
    TextView phone;
    TextView nama;
    TextView alamat;
    TextView title;
    Cursor rs;

    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        email = (TextView) findViewById(R.id.InputEmail);
        nama = (TextView) findViewById(R.id.InputNama);
        phone = (TextView) findViewById(R.id.InputTelp);
        alamat = (TextView) findViewById(R.id.InputAlamat);
        title = (TextView) findViewById(R.id.title);

        Button update = (Button) findViewById(R.id.update_button);
        Button cancel = (Button) findViewById(R.id.cancel_button);
        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            rs = mydb.getData(Value);
            id_To_Update = Value;
            rs.moveToFirst();

            String namaS = rs.getString(rs.getColumnIndex(DBHelper.KNK_COLUMN_NAMA));
            String phoneS = rs.getString(rs.getColumnIndex(DBHelper.KNK_COLUMN_PHONE));
            String emailS = rs.getString(rs.getColumnIndex(DBHelper.KNK_COLUMN_EMAIL));
            String alamatS = rs.getString(rs.getColumnIndex(DBHelper.KNK_COLUMN_ALAMAT));
            String i = rs.getString(rs.getColumnIndex(DBHelper.KNK_COLUMN_ID));
            System.out.println("v");
            if (!rs.isClosed()) {
                rs.close();
            }

            email.setText((CharSequence) emailS);
            nama.setText((CharSequence) namaS);

            phone.setText((CharSequence) phoneS);
            alamat.setText((CharSequence) alamatS);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.updateContact(
                        String.valueOf(id_To_Update),
                        nama.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString(),
                        alamat.getText().toString()
                );
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
