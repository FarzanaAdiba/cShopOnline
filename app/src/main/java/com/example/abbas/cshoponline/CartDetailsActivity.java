package com.example.abbas.cshoponline;


import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartDetailsActivity extends AppCompatActivity {
    DatabaseManager databaseManager;
    ListView  cartList;
    ArrayList<String> listCart;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);

        cartList = findViewById(R.id.cartList);
        listCart = new ArrayList<>();
        adapter = new ArrayAdapter<>(CartDetailsActivity.this,android.R.layout.simple_list_item_1,listCart);
        cartList.setAdapter(adapter);
        android.support.v7.widget.Toolbar toolbar=findViewById(R.id.cartbar);
        setSupportActionBar(toolbar);

        getAllCartData();

        cartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(CartDetailsActivity.this);
                builder.setMessage("Choose an Action");
                builder.setCancelable(false);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseManager.deleteCartData(String.valueOf(position));
                        Toast.makeText(CartDetailsActivity.this,"Deleted",Toast.LENGTH_LONG).show();
                        //databaseManager.getAllCartItem();
                        //loginActivity will be added here
                    }
                });
                builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });


}

    private void getAllCartData() {
        databaseManager = new DatabaseManager(this);
        Cursor cursor = databaseManager.getAllCartItem();
        if (!cursor.moveToNext()){
            Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();
        }else {
do {
    listCart.add(cursor.getString(1)+"  Qtt: "+cursor.getString(2)+"  Price: "+cursor.getString(3));

}while (cursor.moveToNext());
    }
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.cart_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.checkOut:
                break;

        }


        return super.onOptionsItemSelected(item);
    }

}
