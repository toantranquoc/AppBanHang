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
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
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
        return view;

    }
}
