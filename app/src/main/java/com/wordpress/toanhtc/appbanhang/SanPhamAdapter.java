package com.wordpress.toanhtc.appbanhang;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham> arraysanpham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        SanPham sanPham = arraysanpham.get(position);
        holder.txttensanpham.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat  = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham())+ " Đ");
        Picasso.with(context).load(sanPham.getHinhanhsanpham())
                .placeholder(R.drawable.noimageicon)
                .error(R.drawable.erroricon)
                .into(holder.imghinhsanpham);

    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsanpham;
        public TextView txttensanpham, txtgiasanpham;

        public ItemHolder(View itemView) {
            super(itemView);
            imghinhsanpham = (ImageView) itemView.findViewById(R.id.imageviewsanpham);
            txttensanpham = (TextView) itemView.findViewById(R.id.txttensanpham);
            txtgiasanpham = (TextView) itemView.findViewById(R.id.txtgiasanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent inforsp = new Intent(context, ChiTietSanPhamActivity.class);
                    inforsp.putExtra("thongtinsp", arraysanpham.get(getPosition()));
                    inforsp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Toast.makeText(context,arraysanpham.get(getPosition()).getTensanpham(),Toast.LENGTH_SHORT).show();
                    context.startActivity(inforsp);
                }
            });
        }
    }

}
