package com.wordpress.toanhtc.appbanhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrayGiohang;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrayGiohang) {
        this.context = context;
        this.arrayGiohang = arrayGiohang;
    }

    @Override
    public int getCount() {
        return arrayGiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayGiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView tengiohang, giagiohang;
        public ImageView hinhanhgiohang;
        public Button tru, giatri, cong;
    }
    ViewHolder  viewHolder = null;
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.tengiohang = view.findViewById(R.id.txtTenGiohang);
            viewHolder.giagiohang = view.findViewById(R.id.txtGiagiohang);
            viewHolder.hinhanhgiohang = view.findViewById(R.id.imgviewGiohang);
            viewHolder.tru = view.findViewById(R.id.btnTru);
            viewHolder.cong = view.findViewById(R.id.btnCong);
            viewHolder.giatri = view.findViewById(R.id.btnValue);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        viewHolder.tengiohang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.giagiohang.setText(decimalFormat.format(gioHang.getGiasp())+ " Đ");
        Picasso.with(context).load(gioHang.getHinhanhsp())
                .placeholder(R.drawable.noimageicon)
                .error(R.drawable.erroricon)
                .into(viewHolder.hinhanhgiohang);
        viewHolder.giatri.setText(gioHang.getSoluongsp()+ "");
        int soluong = Integer.parseInt(viewHolder.giatri.getText().toString());
        if (soluong >=10)
        {
            viewHolder.cong.setVisibility(View.INVISIBLE);
            viewHolder.tru.setVisibility(View.VISIBLE);
        }
        else
            if (soluong <= 1)
            {
                viewHolder.cong.setVisibility(View.VISIBLE);
                viewHolder.tru.setVisibility(View.INVISIBLE);
            }
            else
            {
                viewHolder.cong.setVisibility(View.VISIBLE);
                viewHolder.tru.setVisibility(View.VISIBLE);
            }
            viewHolder.cong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   int slmoinhat = Integer.parseInt(viewHolder.giatri.getText().toString()) + 1;
                   int slhientai = MainActivity.manggiohang.get(i).getSoluongsp();
                   long giahientai = MainActivity.manggiohang.get(i).getGiasp();
                   MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                   long giamoinhat = (giahientai * slmoinhat) / slhientai;
                   MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    viewHolder.giagiohang.setText(decimalFormat.format(giamoinhat)+ " Đ");
                    GioHangActivity.EvenUlti();
                    if (slmoinhat > 9)
                    {
                        viewHolder.cong.setVisibility(View.INVISIBLE);
                        viewHolder.tru .setVisibility(View.VISIBLE);
                        viewHolder.giatri.setText(String.valueOf(MainActivity.manggiohang.get(i).getSoluongsp()));
                    }
                    else
                    {
                        viewHolder.cong.setVisibility(View.VISIBLE);
                        viewHolder.tru .setVisibility(View.VISIBLE);
                        viewHolder.giatri.setText(String.valueOf(MainActivity.manggiohang.get(i).getSoluongsp()));
                    }
                }
            });
        viewHolder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(viewHolder.giatri.getText().toString()) - 1;
                int slhientai = MainActivity.manggiohang.get(i).getSoluongsp();
                long giahientai = MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.giagiohang.setText(decimalFormat.format(giamoinhat)+ " Đ");
                GioHangActivity.EvenUlti();
                if (slmoinhat < 2)
                {
                    viewHolder.cong.setVisibility(View.VISIBLE);
                    viewHolder.tru .setVisibility(View.INVISIBLE);
                    viewHolder.giatri.setText(String.valueOf(MainActivity.manggiohang.get(i).getSoluongsp()));
                }
                else
                {
                    viewHolder.cong.setVisibility(View.VISIBLE);
                    viewHolder.tru .setVisibility(View.VISIBLE);
                    viewHolder.giatri.setText(String.valueOf(MainActivity.manggiohang.get(i).getSoluongsp()));
                }
            }
        });
        return view;

    }
}
