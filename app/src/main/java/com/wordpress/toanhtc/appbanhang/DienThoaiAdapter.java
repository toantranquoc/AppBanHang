package com.wordpress.toanhtc.appbanhang;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayDienthoai;

    public DienThoaiAdapter(Context context, ArrayList<SanPham> arrayDienthoai) {
        this.context = context;
        this.arrayDienthoai = arrayDienthoai;
    }

    @Override
    public int getCount() {
        return arrayDienthoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayDienthoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView tendienthoai, giadienthoai, motadienthoai;
        public ImageView hinhanhdienthoai;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dienthoai, null);
            viewHolder.tendienthoai = (TextView) view.findViewById(R.id.txtTenDienthoai);
            viewHolder.giadienthoai = (TextView) view.findViewById(R.id.txtGiaDienthoai);
            viewHolder.motadienthoai = (TextView) view.findViewById(R.id.txtMotaDienthoai);
            viewHolder.hinhanhdienthoai = (ImageView) view.findViewById(R.id.imageviewDienthoai);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.tendienthoai.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat  = new DecimalFormat("###,###,###");
        viewHolder.giadienthoai.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham())+ " Đ");
        viewHolder.motadienthoai.setMaxLines(2);
        viewHolder.motadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.motadienthoai.setText(sanPham.getMotasanpham());
        Picasso.with(context).load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.noimageicon)
                .error(R.drawable.erroricon)
                .into(viewHolder.hinhanhdienthoai);
        return view;
    }
}
