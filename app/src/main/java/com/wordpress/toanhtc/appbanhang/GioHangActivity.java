package com.wordpress.toanhtc.appbanhang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    Toolbar toolbardgiohang;
    ListView listViewGiohang;
    TextView thongBao;
    static TextView tongTien;
    Button thanhToan, tieptucMuahang;
    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            ActionToolbar();
            CheckData();
            EvenUlti();
            HoldingItemListview();
            ContinueShopping();
            Paying();
        }
        else
        {
            CheckConnection.showToast_Connect(getApplicationContext(), "Check your Internet");
        }

    }

    private void Paying() {
        thanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.manggiohang.size() <= 0 )
                {
                    CheckConnection.showToast_Connect(getApplicationContext(), "Giỏ hàng của bạn trống!");
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), ThongTinActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void ContinueShopping() {
        tieptucMuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void HoldingItemListview() {
       listViewGiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
               AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this, R.style.MyDialogTheme);
               builder.setCancelable(false);
               builder.setMessage("Bạn có muốn xóa sản phẩm này?");
               builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       if(MainActivity.manggiohang.size() <= 0)
                       {
                           thongBao.setVisibility(View.VISIBLE);
                       }
                       else {
                           MainActivity.manggiohang.remove(pos);
                           gioHangAdapter.notifyDataSetChanged();
                           EvenUlti();
                           if (MainActivity.manggiohang.size() <=0)
                           {
                               thongBao.setVisibility(View.VISIBLE);
                           }
                           else {
                               thongBao.setVisibility(View.INVISIBLE);
                               gioHangAdapter.notifyDataSetChanged();
                               EvenUlti();
                           }
                       }
                   }
               });
               builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       gioHangAdapter.notifyDataSetChanged();
                       EvenUlti();
                   }
               });
               AlertDialog alertDialog = builder.create();
               alertDialog.show();
               return true;
           }
       });
    }

    public static void EvenUlti() {
        long tongtien = 0;
        for(int i = 0; i< MainActivity.manggiohang.size(); i++)
        {
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongTien.setText(decimalFormat.format(tongtien) + " Đ");
    }

    private void CheckData() {
        if (MainActivity.manggiohang.size() <= 0)
        {
            gioHangAdapter.notifyDataSetChanged();
            thongBao.setVisibility(View.VISIBLE);
            listViewGiohang.setVisibility(View.INVISIBLE);
        }
        else
        {
            gioHangAdapter.notifyDataSetChanged();
            thongBao.setVisibility(View.INVISIBLE);
            listViewGiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardgiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardgiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbardgiohang = (Toolbar) findViewById(R.id.toolbarGiohang);
        listViewGiohang = (ListView) findViewById(R.id.listviewGiohang);
        thongBao = (TextView) findViewById(R.id.txtGiohangtrong);
        tongTien = (TextView) findViewById(R.id.txtTongGiaTri);
        thanhToan = (Button) findViewById(R.id.btnThanhtoangiohang);
        tieptucMuahang = (Button) findViewById(R.id.btnTieptucmuahang);
        gioHangAdapter = new GioHangAdapter(this, MainActivity.manggiohang);
        listViewGiohang.setAdapter(gioHangAdapter);
    }
}
