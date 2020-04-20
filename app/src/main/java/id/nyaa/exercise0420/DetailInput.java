package id.nyaa.exercise0420;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailInput extends AppCompatActivity {

    private DBHelper mydb;

    TextView email;
    TextView phone;
    TextView nama;
    TextView alamat;
    TextView title;

    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_input);
        email = (TextView) findViewById(R.id.InputEmail);
        nama = (TextView) findViewById(R.id.InputNama);
        phone = (TextView) findViewById(R.id.InputTelp);
        alamat = (TextView) findViewById(R.id.InputAlamat);
        title = (TextView) findViewById(R.id.title);

        Button b = (Button) findViewById(R.id.saveButton);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.

                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                final String namaS = rs.getString(rs.getColumnIndex(DBHelper.KNK_COLUMN_NAMA));
                final String phoneS = rs.getString(rs.getColumnIndex(DBHelper.KNK_COLUMN_PHONE));
                final String emailS = rs.getString(rs.getColumnIndex(DBHelper.KNK_COLUMN_EMAIL));
                final String alamatS = rs.getString(rs.getColumnIndex(DBHelper.KNK_COLUMN_ALAMAT));
                if (!rs.isClosed()) {
                    rs.close();
                }

                title.setText(R.string.profil);
                b.setVisibility(View.INVISIBLE);

                email.setText((CharSequence) emailS);
                email.setFocusable(false);
                email.setClickable(false);

                nama.setText((CharSequence) namaS);
                nama.setFocusable(false);
                nama.setClickable(false);

                phone.setText((CharSequence) phoneS);
                phone.setFocusable(false);
                phone.setClickable(false);

                alamat.setText((CharSequence) alamatS);
                alamat.setFocusable(false);
                alamat.setClickable(false);

            }
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!nama.getText().toString().isEmpty() &&
                        !phone.getText().toString().isEmpty() &&
                        !email.getText().toString().isEmpty() &&
                        !alamat.getText().toString().isEmpty()
                ) {
                    if (!isValidEmail(email.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Email salah", Toast.LENGTH_LONG).show();
                    } else {
                        mydb.insertContact(
                                nama.getText().toString(),
                                phone.getText().toString(),
                                email.getText().toString(),
                                alamat.getText().toString()
                        );
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Data harus diisi semua", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public final boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
