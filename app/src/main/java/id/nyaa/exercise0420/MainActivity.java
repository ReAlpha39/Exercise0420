package id.nyaa.exercise0420;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;
    ListAdapter listAdapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DBHelper(this);
        obj = (ListView) findViewById(R.id.listView1);
        ArrayList<Contact> contactArrayList = mydb.getAllData();
        listAdapter = new ListAdapter(contactArrayList, this);
        obj.setAdapter(listAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);
                Intent intent = new Intent(getApplicationContext(), DetailInput.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
        obj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                int idData = position;
                final Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", idData);
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, obj);
                popupMenu.inflate(R.menu.menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.editButton) {
                            int id_To_Search = position + 1;

                            Bundle dataBundle = new Bundle();
                            dataBundle.putInt("id", id_To_Search);
                            Intent intent = new Intent(getApplicationContext(), UpdateData.class);
                            intent.putExtras(dataBundle);
                            startActivity(intent);
                        } else if (item.getItemId() == R.id.deleteButton) {
                            showDialog(dataBundle);
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailInput.class);
                startActivity(intent);
            }
        });
    }

    private void showDialog(Bundle bundle) {
        int value = bundle.getInt("id");
        Cursor cursor = mydb.getData(value);
        cursor.moveToFirst();
        final String id = cursor.getString(cursor.getColumnIndex((DBHelper.KNK_COLUMN_ID)));
        final String nama = cursor.getString(cursor.getColumnIndex(DBHelper.KNK_COLUMN_NAMA));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah anda yakin ingin menghapus data " + nama + " ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mydb = new DBHelper(getApplicationContext());
                        mydb.deleteContact(id);

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    public void refresh(){
        SQLiteDatabase db = mydb.getReadableDatabase();
        cursor = db.rawQuery("select * from kontak", null);

    }

}